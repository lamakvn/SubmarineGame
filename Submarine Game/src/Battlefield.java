import java.util.Random;

public class Battlefield {
    private static final int SIZE = 5; // 戦域のサイズ
    private Cell[][] grid = new Cell[SIZE][SIZE];
    private static final String[][] INITIAL_CONFIGS = {
        {"A1", "B4", "D2", "E5"},
        {"A5", "B2", "D4", "E1"}
    };

    public Battlefield() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public void initializeSubmarines() {
        Random random = new Random();
        String[] config = INITIAL_CONFIGS[random.nextInt(INITIAL_CONFIGS.length)];
        for (String coord : config) {
            int[] pos = parseCoordinate(coord);
            grid[pos[0]][pos[1]].setHasSubmarine(true);
            grid[pos[0]][pos[1]].setHp(3);
        }
    }

    private int[] parseCoordinate(String coordinate) {
        int row = coordinate.charAt(0) - 'A';
        int col = Character.getNumericValue(coordinate.charAt(1)) - 1;
        return new int[]{row, col};
    }

    public String attack(String coordinate) {
        int[] position = parseCoordinate(coordinate);
        Cell targetCell = grid[position[0]][position[1]];
    
        if (targetCell.hasSubmarine()) {
            targetCell.setHp(targetCell.getHp() - 1);
            updatePotentialScores(position[0], position[1], "hit");
            return "hit";
        } else if (isNearMiss(position[0], position[1])) {
            updatePotentialScores(position[0], position[1], "nearMiss");
            return "nearMiss";
        } else {
            updatePotentialScores(position[0], position[1], "miss");
            return "miss";
        }
    }
    
    public void moveEnemySubmarine(String direction, int distance) {
        // 敵潜水艦の移動処理
        // この例ではランダムに潜水艦を選択し、指定された方向と距離に移動させる
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!grid[x][y].hasSubmarine() || grid[x][y].getHp() <= 0);

        // 指定された方向に応じて新しい位置を計算
        int newX = x, newY = y;
        switch (direction) {
            case "東":
                newY = Math.min(y + distance, SIZE - 1);
                break;
            case "西":
                newY = Math.max(y - distance, 0);
                break;
            case "南":
                newX = Math.min(x + distance, SIZE - 1);
                break;
            case "北":
                newX = Math.max(x - distance, 0);
                break;
        }

        // 潜水艦を新しい位置に移動
        grid[newX][newY].setHasSubmarine(true);
        grid[newX][newY].setHp(grid[x][y].getHp());
        grid[x][y].setHasSubmarine(false);
        grid[x][y].setHp(0);
    }
}

    private void updatePotentialScores(int row, int col, String result) {
        switch (result) {
            case "hit":
                // 命中したマスのポテンシャルスコアを増加
                grid[row][col].setPotentialScore(grid[row][col].getPotentialScore() + 10);
                break;
            case "nearMiss":
                // 周囲のマスのポテンシャルスコアを増加
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) continue; // 中心のマスはスキップ
                        int checkRow = row + i;
                        int checkCol = col + j;
                        if (isValidPosition(checkRow, checkCol)) {
                            grid[checkRow][checkCol].setPotentialScore(grid[checkRow][checkCol].getPotentialScore() + 1);
                        }
                    }
                }
                break;
            case "miss":
                // 攻撃されたマスと周囲のマスのポテンシャルスコアをリセット
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int checkRow = row + i;
                        int checkCol = col + j;
                        if (isValidPosition(checkRow, checkCol)) {
                            grid[checkRow][checkCol].setPotentialScore(0);
                        }
                    }
                }
                break;
        }
    }    
    
    private boolean isNearMiss(int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    // 攻撃されたマス自体はチェックしない
                    continue;
                }
                int checkRow = row + i;
                int checkCol = col + j;
                if (isValidPosition(checkRow, checkCol) && grid[checkRow][checkCol].hasSubmarine()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }
    
    

    public void moveSubmarineRandomly() {
        Random random = new Random();
        // 移動する潜水艦をランダムに選択し、ランダムな方向に移動させるロジック
        // ...
    }
    
    public void updateAfterEnemyHit() {
        // 命中された場合の戦域の更新処理
    }

    public void updateAfterEnemyMiss() {
        // ハズレの場合の戦域の更新処理
    }

    public void updateAfterEnemyNearMiss() {
        // 波高しの場合の戦域の更新処理
    }

    public void updateAfterEnemyShink() {
        // 撃沈された場合の戦域の更新処理
    }
    
}
