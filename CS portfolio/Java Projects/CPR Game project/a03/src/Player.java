import java.io.Serializable;

public class Player implements Serializable {
    int id;
    int resourcesCollected = 0;
    int x, y;

    boolean canSeePlayers;
    boolean canSeeResources;

    Player(int id) {
        this.id = id;
        this.x = 125;
        this.y = 125;
    }

    int getX() { return this.x; }
    int getY() { return this.y; }

    void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    void resourceCollected() { this.resourcesCollected += 1; }
    int getResourcesCollected() { return this.resourcesCollected; }

    @Override
    public String toString() {
        return "Player [id=" + id + "x=" + x + ", y=" + y + ", resourcesCollected=" + resourcesCollected
                + "]";
    }
}