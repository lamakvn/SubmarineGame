import java.util.Scanner;

public class Game {
    private Battlefield battlefield;
    private PlayerActionHandler playerActionHandler;
    private EnemyActionHandler enemyActionHandler;
    private Scanner scanner;

    public Game() {
        battlefield = new Battlefield();
        playerActionHandler = new PlayerActionHandler(battlefield);
        enemyActionHandler = new EnemyActionHandler(battlefield);
        scanner = new Scanner(System.in);
    }

    public void startGame() {
        while (!battlefield.isAllSubmarinesSunk()) {
            playerActionHandler.performAction();
            enemyActionHandler.performAction();
        }
        System.out.println("ゲーム終了。全ての潜水艦が撃沈されました。");
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}
