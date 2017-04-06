package uk.co.bbc.gol;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Game {

    private final Set<Cell> aliveCells;
    private final Function<Cell, Stream<Cell>> allNeighbours = cell -> cell.allNeighbours().stream();
    private final Collector<Cell, ?, Map<Cell, Long>> asCellToCountMap = Collectors.groupingBy(cell -> cell, Collectors.counting());
    private final Predicate<Map.Entry<Cell, Long>> shouldBirth;
    private final Predicate<? super Map.Entry<Cell, Long>> shouldSurvive;

    private Game(Set<Cell> aliveCells) {
        this.aliveCells = aliveCells;

        this.shouldBirth = entry -> !aliveCells.contains(entry.getKey()) && entry.getValue() == 3;

        this.shouldSurvive = entry -> {
            Cell cell = entry.getKey();
            Long neighbourCount = entry.getValue();
            return aliveCells.contains(cell) && ( neighbourCount == 2 || neighbourCount == 3 );
        };
    }

    public static Game create(Cell... lives) {
        return new Game(new HashSet<>(Arrays.asList(lives)));
    }

    public Game evolve() {
        final Set<Map.Entry<Cell, Long>> entries = aliveCells.stream()
                .flatMap(allNeighbours)
                .collect(asCellToCountMap).entrySet();
        final Stream<Cell> survivors = entries.stream().filter(shouldSurvive).map(Map.Entry::getKey);
        final Stream<Cell> newBorns = entries.stream().filter(shouldBirth).map(Map.Entry::getKey);
        final Set<Cell> aliveCells = Stream.concat(newBorns, survivors).collect(Collectors.toSet());
        return new Game(aliveCells);
    }

    public void draw() {
        final int width = aliveCells.stream().map(Cell::getX).reduce(Integer::max).orElse(-1)+1;
        final int height = aliveCells.stream().map(Cell::getY).reduce(Integer::max).orElse(-1)+1;

        IntStream.range(0, height).forEach(y -> {
            IntStream.range(0, width).forEach(x -> printGrid(y, x));
            System.out.println();
        });
    }

    private void printGrid(int y, int x) {
        System.out.print( aliveCells.contains(new Cell(x,y)) ? "#" : " " );
    }

}
