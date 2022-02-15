package main.board;

import main.board.proto.BoardColoring;
import main.tools.TextColor;
import main.tools.Util;

import java.util.ArrayList;

public class Board {


    // Horizontal line
    private final String hl;

    private final ArrayList<Location> spaces;
    private final int size;

    public Board(int size) {
        this.size = size;

        spaces = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++)
                spaces.add(new Location(i, j));
        }

        hl = " " + "=".repeat((Location.RENDER_SIZE + 1) * size + 1);
    }

    public int getSize() {
        return size;
    }

    public Location getSpace(int rang, int col) {
        return spaces.get(col * size + rang);
    }

    public void render(BoardColoring bci) {
        // Print column numbering
        System.out.print("   ");
        for(int i = 0; i < size; i++) {
            String spacer = " ".repeat((i + 1 > 9) ? 1 : 2);
            System.out.print(" " + spacer + colI(i) + "  ");
        }

        System.out.println("\n  " + hl);
        for(int i = 0; i < size; i++) {
            // Print row numbering
            String spacer = " ".repeat(i+1 > 9 ? 0 : 1);
            System.out.print(spacer + colI(i) + " |");
            for (int j = 0; j < size; j++) {
                // Print box content
                System.out.print(getSpace(i, j).render(bci) + "|");
            }
            System.out.println("\n  " + hl);
        }
    }

    private String colI(int i) {
        return Util.genColorString(""+ (i + 1), TextColor.RED);
    }
}
