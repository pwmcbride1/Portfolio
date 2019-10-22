import java.rmi.RemoteException;

public interface ModelInterface extends java.rmi.Remote {
    Cell[][] getCells(int id) throws RemoteException;
    // above func checks player.canSeeResources before returning

    Player[] getPlayers(int id) throws RemoteException;
    // above func checks player.canSeePlayers before returning

    int registerNewPlayer() throws RemoteException; // inits new player, returns id

    void movePlayer(int id, int dx, int dy) throws RemoteException;

    int playerCollectsResource(int id, int x, int y) throws RemoteException;

    int getPlayerResourceCount(int id) throws RemoteException;
}