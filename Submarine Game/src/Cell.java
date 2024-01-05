public class Cell {
    private boolean hasSubmarine;
    private int hp;
    private int potentialScore;

    public Cell() {
        this.hasSubmarine = false;
        this.hp = 0;
        this.potentialScore = 0;
    }

    // hasSubmarineのゲッターとセッター
    public boolean hasSubmarine() {
        return hasSubmarine;
    }

    public void setHasSubmarine(boolean hasSubmarine) {
        this.hasSubmarine = hasSubmarine;
    }

    // HPのゲッターとセッター
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    // potentialScoreのゲッターとセッター
    public int getPotentialScore() {
        return potentialScore;
    }

    public void setPotentialScore(int potentialScore) {
        this.potentialScore = potentialScore;
    }
}
