package uk.co.bbc.gol;

/**
 * Created by harper05 on 12/04/2017.
 */

class Cell {
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hashCode = x * 31 + y;
        return hashCode;
    }

    @Override
    public boolean equals(Object object) {
        Cell cell = (Cell)object;
        return cell.x == this.x && cell.y == this.y;
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
