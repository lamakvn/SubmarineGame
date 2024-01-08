import java.util.Random;

public class Battlefield {
    public static final int SIZE = 5;
    private Cell[][] grid = new Cell[SIZE][SIZE];
    private Random random = new Random();
    private static final String[][] INITIAL_CONFIGS = {
        {"A1", "B4", "D2", "E5"},
        {"A5", "B2", "D4", "E1"}
    };

    public Battlefield() {
        initializeGrid();
        placeSubmarines();
    }

    private void initializeGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void placeSubmarines() {
        String[] config = INITIAL_CONFIGS[random.nextInt(INITIAL_CONFIGS.length)];
        for (String coord : config) {
            int[] pos = GameUtils.parseCoordinate(coord);
            if (pos == null || pos[0] == -1 || pos[1] == -1) {
                // 無効な座標をスキップ
                continue;
            }
            Cell cell = grid[pos[0]][pos[1]];
            cell.setHasSubmarine(true);
            cell.setHp(3);
        }
    }
    


    public String attack(String coordinate) {
        int[] pos = GameUtils.parseCoordinate(coordinate);
        if (isValidPosition(pos[0], pos[1])) {
            Cell target = grid[pos[0]][pos[1]];
            if (target.hasSubmarine()) {
                target.setHp(target.getHp() - 1);
                return "命中"; // 命中した場合の文字列を返す
            } else {
                return "ハズレ"; // ハズレた場合の文字列を返す
            }
        } else {
            return "無効な座標"; // 無効な座標の場合の文字列を返す
        }
    }

    public void updatePotentialScoresAround(int x, int y, boolean hit) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = x + i;
                int newY = y + j;
                if (isValidPosition(newX, newY)) {
                    if (hit) {
                        grid[newX][newY].setPotentialScore(10);
                    } else {
                        grid[newX][newY].setPotentialScore(1);
                    }
                }
            }
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    public boolean isAllSubmarinesSunk() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j].hasSubmarine() && grid[i][j].getHp() > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public Cell getCell(int x, int y) {
        if (isValidPosition(x, y)) {
            return grid[x][y];
        }
        return null;
    }
}
