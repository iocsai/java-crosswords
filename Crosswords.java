package crosswords;
public class Crosswords {
    
    private enum Direction {
        N, E, S, W, NE, NW, SE, SW;
    }

    private final char[][] wordChars;
    private char[][] output;
    
    public static void main(String[] args) {
        final String[] CROSSWORDS = {
            "MANGOTZA", 
            "YSEKSNNZ", 
            "JSGMLWZA", 
            "SXNGOOVP", 
            "PWAWLHEP", 
            "SWRJWNDL", 
            "HNOLPAHE", 
            "XANANABU"};
        final String[] SEARCH_ARRAY = {"BANANA","MANGO","ORANGE","APPLE","WOW"};
        Crosswords run = new Crosswords(CROSSWORDS);
        run.search(SEARCH_ARRAY);
        System.out.println(run.toString());
    }

    private Crosswords(String[] crosswords) {
        this.output = new char[crosswords.length][crosswords[0].length()];
        this.wordChars = new char[crosswords.length][crosswords[0].length()];
        for (int i = 0; i < crosswords.length; i++) {
            for (int j = 0; j < crosswords[i].length(); j++) {
                wordChars[i][j] = crosswords[i].charAt(j);
            }
        }
    }

    @Override
    public String toString() {
        final String NL = "\n";
        String result = "";
        for (char[] line : output) {
            for (char c : line) {
                result += c;
            }
            result += NL;
        }
        return result.substring(0, result.length() - 1);
    }

    private void search(String[] array) {
        for (String word : array) {
            this.search(word);
        }
    }

    private int search(String word) {
        int count = 0;
        for (int i = 0; i < wordChars.length; i++) {
            for (int j = 0; j < wordChars[i].length; j++) {
                if (wordChars[i][j] == word.charAt(0)) {
                    this.search(i, j, word);
                }
            }
        }
        return count;
    }

    private void search(int row, int column, String word) {
        char nextChar = word.charAt(1);
        boolean east = column + word.length() <= wordChars[row].length;
        boolean west = word.length() <= column;
        boolean north = word.length() <= row;
        boolean south = row + word.length() <= wordChars.length;
        if (east && wordChars[row][column + 1] == nextChar) {
            this.search(row, column, word, Direction.E);
        }
        if (west && wordChars[row][column - 1] == nextChar) {
            this.search(row, column, word, Direction.W);
        }
        if (north && wordChars[row - 1][column] == nextChar) {
            this.search(row, column, word, Direction.N);
        }
        if (south && wordChars[row + 1][column] == nextChar) {
            this.search(row, column, word, Direction.S);
        }
        if (south && east && wordChars[row + 1][column + 1] == nextChar) {
            this.search(row, column, word, Direction.SE);
        }
        if (south && west && wordChars[row + 1][column - 1] == nextChar) {
            this.search(row, column, word, Direction.SW);
        }
        if (north && east && wordChars[row - 1][column + 1] == nextChar) {
            this.search(row, column, word, Direction.NE);
        }
        if (north && west && wordChars[row - 1][column - 1] == nextChar) {
            this.search(row, column, word, Direction.NW);
        }
    }

    private void search(int row, int column, String word, Direction arrow) {
        char[][] temp = copyOutput();
        int[] vector = setArrow(arrow);
        int index = 0;
        while (index < word.length()) {
            int rowCoord = row + (index * vector[0]);
            int colCoord = column + (index * vector[1]);
            if (word.charAt(index) == wordChars[rowCoord][colCoord]) {
                temp[rowCoord][colCoord] = word.charAt(index++);
            } else {
                break;
            }
        }
        if (index >= word.length()) {
            output = temp;
        }
    }

    private int[] setArrow(Direction arrow) {
        switch (arrow) {
            case N:
                return new int[]{-1, 0};
            case E:
                return new int[]{0, 1};
            case S:
                return new int[]{1, 0};
            case W:
                return new int[]{0, -1};
            case NE:
                return new int[]{-1, 1};
            case NW:
                return new int[]{-1, -1};
            case SE:
                return new int[]{1, 1};
            case SW:
                return new int[]{1, -1};
            default:
                return new int[2];
        }
    }

    private char[][] copyOutput() {
        char[][] result = new char[output.length][output[1].length];
        for (int i = 0; i < result.length; i++) {
            System.arraycopy(output[i], 0, result[i], 0, result[i].length);
        }
        return result;
    }
}