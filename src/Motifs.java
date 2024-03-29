import java.util.List;

public class Motifs {
    // Les coordonnées x et y de chaque fonction sont celles en haut à gauche du motif en question

    public static List<int[]> doubleCarre(int x, int y) {
        return List.of(new int[]{x, y}, new int[]{x, y+1}, new int[]{x+1, y}, new int[]{x+1, y+1}, new int[]{x+2, y+2}, new int[]{x+2, y+3}, new int[]{x+3, y+2}, new int[]{x+3, y+3});
    }

    public static List<int[]> barreVerticale(int x, int y) {
        return List.of(new int[]{x, y}, new int[]{x, y+1}, new int[]{x, y+2});
    }
}
