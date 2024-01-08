import java.util.Random;

public class PlayerActionHandler {
    private Battlefield battlefield;
    private Random random;

    public PlayerActionHandler(Battlefield battlefield) {
        this.battlefield = battlefield;
        this.random = new Random();
    }

    public void performAction() {
        String target = determineTarget();
        if (target != null) {
            String result = battlefield.attack(target);
            System.out.println("自軍が " + target + " に攻撃しました " );
        } else {
            System.out.println("攻撃可能なターゲットがありません。");
        }
    }
    
    private void updatePotentialScoresAfterAttack(String target, String attackResult) {
        int[] pos = GameUtils.parseCoordinate(target);
        switch (attackResult) {
            case "命中":
                // 命中した場合、対象のセルのスコアを更新
                battlefield.getCell(pos[0], pos[1]).setPotentialScore(10);
                break;
            case "ハズレ":
                // ハズレの場合、周囲のセルのスコアを更新
                battlefield.updatePotentialScoresAround(pos[0], pos[1], false);
                break;
            // 他のケースについても適宜処理を追加
        }
    }
    

    private String determineTarget() {
        int highestScore = -1;
        String target = null;

        for (int i = 0; i < Battlefield.SIZE; i++) {
            for (int j = 0; j < Battlefield.SIZE; j++) {
                Cell cell = battlefield.getCell(i, j);
                if (cell.getPotentialScore() > highestScore && !cell.hasSubmarine()) {
                    highestScore = cell.getPotentialScore();
                    target = GameUtils.formatCoordinate(i, j);
                }
            }
        }

        
        target = selectRandomTarget();

        return target;
    }

    private String selectRandomTarget() {
        int x, y;
        do {
            x = random.nextInt(Battlefield.SIZE);
            y = random.nextInt(Battlefield.SIZE);
        } while (battlefield.getCell(x, y).hasSubmarine());

        return GameUtils.formatCoordinate(x, y);
    }
}
