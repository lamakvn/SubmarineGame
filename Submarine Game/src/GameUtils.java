public class GameUtils {
    // バトルフィールドのサイズを指定
    public static final int SIZE = 5;

    // 座標を解析して行と列のインデックスに変換するメソッド
    public static int[] parseCoordinate(String coordinate) {
        if (coordinate == null || coordinate.length() < 2) {
            return new int[]{-1, -1}; // 無効な座標の場合
        }
        char rowChar = Character.toUpperCase(coordinate.charAt(0));
        int col = Character.getNumericValue(coordinate.charAt(1)) - 1; // '1' から '5' への変換
        int row = rowChar - 'A'; // 'A' から 'E' への変換
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return new int[]{-1, -1}; // 無効なインデックスの場合
        }
        return new int[]{row, col};
    }

    // 行と列のインデックスから座標の文字列を作成するメソッド
    public static String formatCoordinate(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return "不正"; // 無効なインデックスの場合
        }
        return "" + (char) ('A' + row) + (col + 1);
    }
}
