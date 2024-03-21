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

/*
Une cellule vivante avec moins de 2 voisins vivants meurt par sous-population.
Une cellule vivante avec 2 ou 3 voisins vivants reste vivante.
Une cellule vivante avec plus de 3 voisins vivants meurt par surpopulation.
Une cellule morte avec exactement 3 voisins vivants devient vivante par reproduction.
*/
