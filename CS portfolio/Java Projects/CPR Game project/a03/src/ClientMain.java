import java.rmi.Remote;
import java.rmi.Naming;

public class ClientMain {

    public static void main(String[] args) {
        try {
            Remote remoteObject = Naming.lookup("//localhost/Team01Model");
            ModelInterface model = (ModelInterface) remoteObject;

            int playerId = model.registerNewPlayer();

            Cell[][] cells = model.getCells(playerId);
            Player[] players = model.getPlayers(playerId);

            View view = new View(cells,players,playerId);

            while (true) {
                model.movePlayer(playerId, view.dx, view.dy);
                view.dx = 0;
                view.dy = 0;

                cells = model.getCells(playerId);
                players = model.getPlayers(playerId);
                view.updateView(cells, players);

                Thread.sleep(50);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
