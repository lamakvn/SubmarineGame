import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Battlefield battlefield;
    private Scanner scanner;

    public Game() {
        this.battlefield = new Battlefield();
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        battlefield.initializeSubmarines(); // 潜水艦の初期配置

        while (!checkGameOver()) {
            System.out.println("敵のアクションを教えてください（hit/miss/near/shink/attack/move）:");
            String enemyAction = scanner.nextLine();

            switch (enemyAction) {
                case "attack":
                    handleEnemyAttack();
                    break;
                case "move":
                    handleEnemyMove();
                    break;
                case "hit":
                case "miss":
                case "near":
                case "shink":
                    handleEnemyResult(enemyAction);
                    break;
                default:
                    System.out.println("無効なアクションです。");
                    continue;
            }

            performAction(); // 自軍のアクションを決定し実行
            battlefield.displayBattlefield(); // 戦域の状態を表示
        }

        System.out.println("ゲーム終了");
    }

    private void handleEnemyAttack() {
        System.out.println("攻撃座標を入力してください（例：a2, b3, d6...）:");
        String coordinate = scanner.nextLine();
        String result = battlefield.attack(coordinate);
    
        switch (result) {
            case "hit":
                System.out.println(coordinate + " に命中しました！");
                break;
            case "miss":
                System.out.println(coordinate + " はハズレました。");
                break;
            case "near":
                System.out.println(coordinate + " は波高し！");
                break;
            default:
                System.out.println("無効な入力です。");
                break;
        }
    }    

    private void handleEnemyMove() {
        System.out.println("どの方向に移動？（東/西/南/北）:");
        String direction = scanner.nextLine();
        System.out.println("何マス移動？（1/2）:");
        int distance = Integer.parseInt(scanner.nextLine());
    
        battlefield.moveEnemySubmarine(direction, distance);
    }
    

    private void handleEnemyResult(String result) {
        switch (result) {
            case "hit":
                // 命中された場合の処理
                battlefield.updateAfterEnemyHit();
                break;
            case "miss":
                // ハズレの場合の処理
                battlefield.updateAfterEnemyMiss();
                break;
            case "near":
                // 波高しの場合の処理
                battlefield.updateAfterEnemyNearMiss();
                break;
            case "shink":
                // 撃沈された場合の処理
                battlefield.updateAfterEnemyShink();
                break;
            default:
                System.out.println("無効な結果です。");
                break;
        }
    }
    

    private void performAction() {
        // 最適な攻撃ターゲットを決定
        String targetCoordinate = determineTarget();
        
        // 決定されたターゲットに攻撃
        if (targetCoordinate != null) {
            battlefield.attack(targetCoordinate);
            System.out.println("攻撃を " + targetCoordinate + " に実行しました。");
        }
    
        // 追加の戦略が必要な場合は、ここで処理を行う
    }
    
   private String determineTarget() {
    int highestScore = -1;
    List<String> highestScoreCells = new ArrayList<>();
    Random random = new Random();

    for (int i = 0; i < Battlefield.SIZE; i++) {
        for (int j = 0; j < Battlefield.SIZE; j++) {
            Cell cell = battlefield.getCell(i, j);
            int currentScore = cell.getPotentialScore();
            if (currentScore > highestScore) {
                highestScore = currentScore;
                highestScoreCells.clear();
                highestScoreCells.add("" + (char)('A' + i) + (j + 1));
            } else if (currentScore == highestScore) {
                highestScoreCells.add("" + (char)('A' + i) + (j + 1));
            }
        }
    }

    if (!highestScoreCells.isEmpty()) {
        return highestScoreCells.get(random.nextInt(highestScoreCells.size()));
    }

    return null; // もしくはデフォルトのターゲットを返す
}
    

    private boolean checkGameOver() {
        // ゲーム終了条件のチェック
        return false;
    }

    // その他の必要なメソッド...
}
