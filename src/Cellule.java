import java.util.List;
import java.util.ArrayList;

class Cellule {
    public boolean enVie = false;

    public List<Cellule> voisins = new ArrayList<Cellule>();

    private int voisinsVivants() {
        int s = 0;
        int n = voisins.size();
        for (int i = 0; i < n; i++) {
            if (voisins.get(i).enVie) s += 1;
        }
        return s;
    }

    public void evoluer() {
        int v = voisinsVivants();

        if (enVie) {
            // Règles pour une cellule vivante
            if (v < 2 || v > 3) {
                enVie = false; // Mort par sous-population ou surpopulation
            }
        } else {
            // Règles pour une cellule morte
            if (v == 3) {
                enVie = true; // Naissance
            }
        }
    }
}

