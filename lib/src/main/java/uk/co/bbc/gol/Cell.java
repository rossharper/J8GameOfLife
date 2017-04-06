package uk.co.bbc.gol;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

class Cell {
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Cell at(int x, int y) {
        return new Cell(x,y);
    }

    public List<Cell> allNeighbours() {
        return new ArrayList<>(asList(
                new Cell(x - 1, y - 1),
                new Cell(x,     y - 1),
                new Cell(x + 1, y - 1),
                new Cell(x - 1, y    ),
                new Cell(x + 1, y    ),
                new Cell(x - 1, y + 1),
                new Cell(x,     y + 1),
                new Cell(x + 1, y + 1)));

    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        if (x != cell.x) return false;
        return y == cell.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
