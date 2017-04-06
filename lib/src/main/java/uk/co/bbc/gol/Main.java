package uk.co.bbc.gol;

public class Main {

    public static void main(String[] args) {

        final Game g = Game.create(
                Cell.at(3,3),
                Cell.at(4,3),
                Cell.at(5,3),
                Cell.at(2,4),
                Cell.at(3,4),
                Cell.at(4,4)
        );

        g.draw();
        System.out.println();
        run(g, 10);

    }

    private static void run(Game g, int i) {
        if(i!=0) {
            final Game g2 = g.evolve();
            g2.draw();
            System.out.println();
            run(g2, i-1);
        }
    }

}
