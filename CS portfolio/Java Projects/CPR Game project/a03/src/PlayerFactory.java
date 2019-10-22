public class PlayerFactory {
    int count;

    public PlayerFactory() {
        count = 0;
    }

    public Player createPlayer(boolean canSeeResources) {
        Player p = new Player(count++);
        p.canSeeResources = canSeeResources;
        return p;
    }
}
