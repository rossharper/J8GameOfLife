package uk.co.bbc.gol;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExampleTest {

    @Test
    public void testName() throws Exception {
        List<Integer> list = Arrays.asList(1,2,3,4);
        int actual = list.stream().map(i -> i * 2).reduce(Integer::sum).orElse(-1);
        assertEquals(20, actual);
    }
}