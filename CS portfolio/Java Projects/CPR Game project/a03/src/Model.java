import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.io.File;

public class Model extends UnicastRemoteObject implements ModelInterface {

    private static final long serialVersionUID = 1L;
    public Cell[][] cells;
    private Player[] players;
    final int gridSize = 256;
    File file;
    Scanner reader;
    private static Model model;
    boolean resourcesVisible = true;
    PlayerFactory playerFactory;

    public static Model getInstance() {
        if(null == model) {
            try {
                model = new Model();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return model;
    }

    private Model() throws RemoteException, java.io.IOException {
        super();

        file = new File("./input.txt");
        reader = new Scanner(file);
        playerFactory = new PlayerFactory();

        // Initialize the cells
        this.cells = new Cell[gridSize][gridSize];
        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                int num = reader.nextInt();
                cells[r][c] = (new Cell(r, c, (num == 1), num));
            }
            if (r < (gridSize - 1)) {
                reader.nextLine();
            }
        }


        // init players
        this.players = new Player[0];

    }

    public int getNeighbors(Cell cell) {
        int neighbors = 0;
        int[] ith = {0, 1, 1, -1, 0, -1, -1, 1};
        int[] jth = {1, 0, 1, 0, -1, -1, 1, -1};
        // All neighbours of cell
        for (int k = 0; k < 8; k++) {
            if (inBounds(cell.getX() + ith[k], cell.getY() + jth[k])) {
                if (cells[cell.getX() + ith[k]][cell.getY() + jth[k]].getResourceNum() > 0) {
                    neighbors += 1;
                }
            }
        }
        return neighbors;
    }

    private boolean inBounds(int i, int j) {
        if (i < 0 || j < 0 || i >= this.cells.length || j >= this.cells.length)
            return false;
        return true;
    }

    // This is called after creating the model to start the game
    public void start(boolean resourcesVisible) throws RemoteException {
        this.resourcesVisible = resourcesVisible;
        long starttime = System.currentTimeMillis();
        long endtime = starttime+(6*60*1000);
        while (System.currentTimeMillis()<endtime) {
                Cell[][] newCells = new Cell[256][256];
                for (int r = 0; r < cells.length; r++) {
                    for (int c = 0; c < cells[r].length; c++) {

                        Cell newCell = new Cell(cells[r][c]);
                        int neighbors = getNeighbors(cells[r][c]);

                        double random = Math.random();

                        if (random < (((float)0.95 * neighbors) / 8)) {
                            if (newCell.getResourceNum() < 7) {
                                newCell.setResourceNum(newCell.getResourceNum() + 1);
                            }
                        }
                        newCells[r][c] = newCell;
                    }
                }
                this.cells = newCells;

                //Collect resources
                for (Player player: players){
                    int id  = player.id;
                    int res = playerCollectsResource(id,player.getX(),player.getY());
                    System.out.println(res);
                    System.out.println(this.cells[player.getX()][player.getY()].getResourceNum());
                }
                System.out.println("updated cells");

                try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Game Over");
        for (Player player: players){
            System.out.println("Player ID: "+player.id+" Score: "+player.getResourcesCollected());
        }
    }

    @Override
    public Cell[][] getCells(int id) throws RemoteException {
        if (id >= players.length || id < 0)
            throw new RemoteException("Player ID not valid.");

        if(!players[id].canSeeResources) {
            Cell[][] dummyCells = new Cell[256][256];
            for(int r = 0; r < 256; r++) {
                for(int c = 0; c < 256; c++) {
                    dummyCells[r][c] = new Cell(r, c, false, 0);
                }
            }
            return dummyCells;
        }

        return cells;
    }

    @Override
    public Player[] getPlayers(int id) throws RemoteException {
        if (id >= players.length || id < 0)
            throw new RemoteException("Player ID not valid.");
        return players;
    }

    @Override
    public int registerNewPlayer() throws RemoteException {
        int id = players.length;

        Player[] newPlayers = new Player[id + 1];
        for (int i = 0; i < id; i++) {
            newPlayers[i] = players[i];
        }
        newPlayers[id] = playerFactory.createPlayer(resourcesVisible);
        players = newPlayers;

        return id;
    }

    @Override
    public void movePlayer(int id, int dx, int dy) throws RemoteException {
        if (id >= players.length || id < 0)
            throw new RemoteException("Player ID not valid.");
        if (inBounds(players[id].getX() + dx, players[id].getY() + dy)) {
            players[id].move(dx, dy);
        }
    }

    @Override
    public int playerCollectsResource(int id, int x, int y) throws RemoteException {
        if (id >= players.length || id < 0)
            throw new RemoteException("Player ID not valid.");

        int currentresource = cells[x][y].getResourceNum();

        if (currentresource > 0){
            cells[x][y].setResourceNum(currentresource-1);
            players[id].resourceCollected();
        }

        return players[id].getResourcesCollected();
    }

    @Override
    public int getPlayerResourceCount(int id) throws RemoteException {
        if (id >= players.length || id < 0)
            throw new RemoteException("Player ID not valid.");
        return players[id].getResourcesCollected();
    }

    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());
        try {
            Model model = Model.getInstance();
            Naming.rebind("//localhost/Team01Model", model);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Would you like resources to be visible? (y/n): ");
            String answer = scanner.next();
            System.out.print("Press [ENTER] to start game.");
            scanner.nextLine();
            model.start(answer.toLowerCase().equals("y"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
