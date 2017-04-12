package uk.co.bbc.gol;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

/**
 * Created by harper05 on 12/04/2017.
 */

class Life {
    private Set<Cell> livingCells;

    public Life(Set<Cell> livingCells) {
        this.livingCells = livingCells;
    }

    public boolean isCellAlive(Cell cell) {
        return livingCells.contains(cell);
    }

    public Life evolve() {
        Set<Cell> cellsThatShouldDie = cellsThatShouldDie();
        Set<Cell> remainingLivingCells = livingCells.stream().filter(livingCell -> !cellsThatShouldDie.contains(livingCell)).collect(Collectors.toSet());
        return new Life(concat(remainingLivingCells.stream(), cellsThatShouldBeBorn().stream()).collect(Collectors.toSet()));
    }

    private Set<Cell> cellsThatShouldBeBorn() {
        return livingCells.stream().flatMap(it -> deadNeighboursOf(it).stream()).filter(it -> livingNeighboursOf(it).size() == 3).collect(Collectors.toSet());
    }

    private Set<Cell> cellsThatShouldDie() {
        return livingCells.stream().filter(it -> (livingNeighboursOf(it).size() < 2 || livingNeighboursOf(it).size() > 3)).collect(Collectors.toSet());
    }

    private Set<Cell> livingNeighboursOf(Cell cell) {
        return neighboursOf(cell).stream().filter(it -> isCellAlive(it)).collect(Collectors.toSet());
    }

    private Set<Cell> deadNeighboursOf(Cell cell) {
        return neighboursOf(cell).stream().filter(it -> !isCellAlive(it)).collect(Collectors.toSet());
    }

    private Set<Cell> neighboursOf(Cell cell) {

        return IntStream.rangeClosed(-1, 1)
                        .mapToObj(x ->
                                IntStream.rangeClosed(-1, 1)
                                        .mapToObj(y -> new Cell(cell.getX() + x, cell.getY() + y)))
                        .reduce(Stream.empty(), (a, b) -> concat(a, b))
                        .filter(it -> !it.equals(cell))
                        .collect(Collectors.toSet());
    }

    public Set<Cell> getLivingCells() {
        return livingCells;
    }

    @Override
    public String toString() {
        return livingCells.stream().map(cell -> "(" + cell.getX() + ", " + cell.getY() + ")").reduce("", (a, b) -> a + b);
    }
}
