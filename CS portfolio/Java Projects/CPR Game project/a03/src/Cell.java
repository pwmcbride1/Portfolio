import java.io.Serializable;

public class Cell implements Serializable {
    private int x, y, resourceNum;
    private boolean isAlive;

    public Cell(int x, int y, boolean isAlive, int resourceNum) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
        this.resourceNum = resourceNum;
    }

    public Cell(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
        this.resourceNum = cell.resourceNum;
    }

    public int getX() { return this.x; }

    public int getY() { return this.y; }

    public int getResourceNum() {return resourceNum; }

    public void setResourceNum(int num) {this.resourceNum = num; }

    @Override
    public String toString() {
        return "Cell [x=" + x + ", y=" + y + ", isAlive=" + isAlive
                + "]";
    }
}