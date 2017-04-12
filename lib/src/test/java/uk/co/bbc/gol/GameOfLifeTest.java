package uk.co.bbc.gol;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameOfLifeTest {

    @Test
    public void testName() throws Exception {
        List<Integer> list = Arrays.asList(1,2,3,4);
        int actual = list.stream().map(i -> i * 2).reduce(Integer::sum).orElse(-1);
        assertEquals(20, actual);
    }

    @Test
    public void thatInitialLivingCellsAreAliveBeforeEvolution() {
        final Life life = new Life(new HashSet<>(Arrays.asList(new Cell(1, 0), new Cell(0, 1))));

        assertTrue(life.isCellAlive(new Cell(1, 0)));
        assertTrue(life.isCellAlive(new Cell(0, 1)));
    }

    @Test
    public void thatInitialDeadCellsAreDeadBeforeEvolution() {
        final Life life = new Life(new HashSet<>(Arrays.asList(new Cell(1, 0), new Cell(0, 1))));

        assertFalse(life.isCellAlive(new Cell(0, 0)));
        assertFalse(life.isCellAlive(new Cell(1, 1)));
    }

    @Test
    public void thatLiveCellWithFewerThanTwoNeighboursShouldDie() {
        final Life life = new Life(new HashSet<>(Arrays.asList(new Cell(1, 0), new Cell(0, 1))));

        final Life evolvedLife = life.evolve();

        assertFalse(evolvedLife.isCellAlive(new Cell(1, 0)));
        assertFalse(evolvedLife.isCellAlive(new Cell(0, 1)));
    }

    @Test
    public void thatLiveCellWithTwoLiveNeighboursShouldSurvive() {
        final Life life = new Life(new HashSet<>(Arrays.asList(new Cell(1, 0), new Cell(0, 1), new Cell(2, 1))));

        final Life evolvedLife = life.evolve();

        assertTrue(evolvedLife.isCellAlive(new Cell(1, 0)));
    }

    @Test
    public void thatLiveCellWithThreeLiveNeighboursShouldSurvive() {
        final Life life = new Life(new HashSet<>(Arrays.asList(new Cell(1, 0), new Cell(0, 1), new Cell(2, 1), new Cell(2, 0))));

        final Life evolvedLife = life.evolve();

        assertTrue(evolvedLife.isCellAlive(new Cell(1, 0)));
    }


    @Test
    public void thatLiveCellWithMoreThanThreeLiveNeighboursShouldDie() {
        final Life life = new Life(new HashSet<>(Arrays.asList(new Cell(1, 0), new Cell(0, 1),new Cell(2, 1), new Cell(2, 0), new Cell(1, 1))));

        final Life evolvedLife = life.evolve();

        assertFalse(evolvedLife.isCellAlive(new Cell(1, 0)));
    }

    @Test
    public void thatNewCellIsBornWhenThreeLiveNeighbours() {
        final Life life = new Life(new HashSet<>(Arrays.asList(new Cell(0, 0), new Cell(1, 0), new Cell(2, 0))));

        final Life evolvedLife = life.evolve();

        assertTrue(evolvedLife.isCellAlive(new Cell(1, 1)));
    }

    @Test
    public void thatNoLifeEvolvedResultsInNoLife() {
        final Life life = new Life(new HashSet<>());

        final Life evolvedLife = life.evolve();

        Stream<Cell> cells = IntStream
                .rangeClosed(0, 50)
                .mapToObj(
                        x -> IntStream.rangeClosed(0, 50)
                                .mapToObj(y -> new Cell(x, y)))
                .reduce(Stream.empty(), (a, b) -> concat(a, b));

        long numberOfLiveCells = cells.filter(it -> evolvedLife.isCellAlive(it)).count();

        assertEquals(0, numberOfLiveCells);
    }

    @Test
    public void testBlinkerOscillator() {
        Set<Cell> verticalBlinker = new HashSet<>(Arrays.asList(new Cell(1, 0), new Cell(1, 1), new Cell(1, 2)));
        Set<Cell> horizontalBlinker = new HashSet<>(Arrays.asList(new Cell(0, 1), new Cell(1, 1), new Cell(2, 1)));

        Life generation0 = new Life(horizontalBlinker);

        Life generation1 = generation0.evolve();

        assertTrue(generation1.getLivingCells().equals(verticalBlinker));

        Life generation2 = generation1.evolve();

        assertTrue(generation2.getLivingCells().equals(horizontalBlinker));
    }
}