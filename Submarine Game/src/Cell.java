public class Cell {
    private boolean hasSubmarine; // このセルに潜水艦が存在するかどうか
    private int hp; // このセルの潜水艦のHP
    private int potentialScore; // このセルのポテンシャルスコア

    // コンストラクタ
    public Cell() {
        this.hasSubmarine = false;
        this.hp = 0;
        this.potentialScore = 0;
    }

    // このセルに潜水艦が存在するかどうかを返す
    public boolean hasSubmarine() {
        return hasSubmarine;
    }

    // このセルに潜水艦を設定する（存在するかどうか）
    public void setHasSubmarine(boolean hasSubmarine) {
        this.hasSubmarine = hasSubmarine;
    }

    // このセルの潜水艦のHPを返す
    public int getHp() {
        return hp;
    }

    // このセルの潜水艦のHPを設定する
    public void setHp(int hp) {
        this.hp = hp;
    }

    // このセルのポテンシャルスコアを返す
    public int getPotentialScore() {
        return potentialScore;
    }

    // このセルのポテンシャルスコアを設定する
    public void setPotentialScore(int potentialScore) {
        this.potentialScore = potentialScore;
    }
}
