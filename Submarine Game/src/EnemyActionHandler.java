import java.util.Scanner;

public class EnemyActionHandler {
    private Battlefield battlefield;
    private Scanner scanner;
    private String lastHitCoordinate = null; // 最後に命中した座標

    public EnemyActionHandler(Battlefield battlefield) {
        this.battlefield = battlefield;
        this.scanner = new Scanner(System.in);
    }

    public void performAction() {
        System.out.println("敵の状態を教えてください（命中:1, ハズレ:2, 波高し:3, 命中！撃沈:0）:");
        int status = scanner.nextInt();
        scanner.nextLine(); // 改行文字をクリア

        switch (status) {
            case 1:
                break;
            case 2:
                break;
            default:
                System.out.println("無効なアクションです。");
                break;
        }
        System.out.println("敵のアクションを選んでください（攻撃：1，移動：2）:");
        int action = scanner.nextInt();
        scanner.nextLine(); // 改行文字をクリア

        switch (action) {
            case 1:
                handleAttack();
                break;
            case 2:
                handleMove();
                break;
            default:
                System.out.println("無効なアクションです。");
                break;
        }

        displaySubmarineStatus();
    }

    private void handleAttack() {
        System.out.println("攻撃座標を入力してください（例：A1, B3, C5）:");
        String coordinate = scanner.nextLine();
        String result = battlefield.attack(coordinate);
        System.out.println("敵が" + coordinate + "に攻撃し、結果は " + result + " でした。");
        if (result.equals("命中")) {
            lastHitCoordinate = coordinate;
        }
    }
    public void processEnemyAttackResult(String attackCoordinate) {
    // 自軍の攻撃に対する敵の状態を判定
    String result = battlefield.attack(attackCoordinate);

    switch (result) {
        case "命中":
            System.out.println(attackCoordinate + " に命中！");
            if (battlefield.getCell(attackCoordinate).getHp() == 0) {
                System.out.println(attackCoordinate + " 命中！撃沈！");
                updatePotentialScores(attackCoordinate, 0); // 撃沈した場合のポテンシャルスコアをリセット
            } else {
                updatePotentialScores(attackCoordinate, 10); // 命中した場合のポテンシャルスコア更新
            }
            break;
        case "ハズレ":
            System.out.println(attackCoordinate + " はハズレ！");
            updatePotentialScoresAround(attackCoordinate, 1); // ハズレの場合のポテンシャルスコア更新
            break;
        case "波高し":
            System.out.println(attackCoordinate + " は波高し！");
            updatePotentialScoresAround(attackCoordinate, 1); // 波高しの場合のポテンシャルスコア更新
            break;
        default:
            System.out.println("無効な結果です。");
            break;
    }
}
    private void handleMove() {
        System.out.println("移動方向を選んでください（東：1，西：2，南：3，北：4）:");
        int directionCode = scanner.nextInt();
        scanner.nextLine(); // 改行文字をクリア
        System.out.println("移動距離を選んでください（1/2）:");
        int distance = scanner.nextInt();
        scanner.nextLine(); // 改行文字をクリア

        String direction = convertDirectionCodeToText(directionCode);
        System.out.println("敵が" + direction + "に" + distance + "マス移動しました。");

        if (lastHitCoordinate != null) {
            String newCoordinate = calculateNewCoordinateBasedOnMove(lastHitCoordinate, direction, distance);
            battlefield.attack(newCoordinate);
            System.out.println("追撃攻撃を " + newCoordinate + " に実行しました。");
        }

        resetPotentialScores();
        lastHitCoordinate = null; // 命中座標をリセット
    }

    private String calculateNewCoordinateBasedOnMove(String coordinate, String direction, int distance) {
        int[] pos = GameUtils.parseCoordinate(coordinate);
        int row = pos[0];
        int col = pos[1];

        switch (direction) {
            case "東":
                col = Math.min(col + distance, Battlefield.SIZE - 1);
                break;
            case "西":
                col = Math.max(col - distance, 0);
                break;
            case "南":
                row = Math.min(row + distance, Battlefield.SIZE - 1);
                break;
            case "北":
                row = Math.max(row - distance, 0);
                break;
        }

        return GameUtils.formatCoordinate(row, col);
    }

    private void resetPotentialScores() {
        for (int i = 0; i < Battlefield.SIZE; i++) {
            for (int j = 0; j < Battlefield.SIZE; j++) {
                battlefield.getCell(i, j).setPotentialScore(0);
            }
        }
    }

    private String convertDirectionCodeToText(int directionCode) {
        switch (directionCode) {
            case 1: return "東";
            case 2: return "西";
            case 3: return "南";
            case 4: return "北";
            default: return "不明";
        }
    }

    private void displaySubmarineStatus() {
        System.out.println("自軍の潜水艦の状態:");
        for (int i = 0; i < Battlefield.SIZE; i++) {
            for (int j = 0; j < Battlefield.SIZE; j++) {
                Cell cell = battlefield.getCell(i, j);
                if (cell != null && cell.hasSubmarine()) {
                    String coordinate = GameUtils.formatCoordinate(i, j);
                    int hp = cell.getHp();
                    System.out.println("潜水艦 " + coordinate + " - HP: " + hp);
                }
            }
        }
    }
}
