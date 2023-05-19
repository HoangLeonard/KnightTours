import Illu.KnightToursIllustration;
import Illu.KnightToursIllustration.Board.Cell;

public class Main {
    public static void main(String[] args) {
        KnightToursIllustration i = new KnightToursIllustration(8,10);
        KnightToursIllustration.Board tmp = i.getBoard();
        Cell[][] cells = tmp.getCells();
        cells[0][0].displayImage(true);
        cells[1][1].displayNumber(true);
        cells[3][3].displayOverlay(true);
        cells[2][2].setProperties(true,true,true);
        cells[4][4].setNumber(200);
        cells[4][4].setProperties(true, false, true);
        cells[5][5].displayOverlay(120,123,203,100,true);
    }
}