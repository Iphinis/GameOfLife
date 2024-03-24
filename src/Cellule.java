import java.util.List;
import java.util.ArrayList;

class Cellule {
    public boolean enVie = false;
    public boolean prochainEtat = false;

    public List<Cellule> voisins = new ArrayList<Cellule>();

    // Fonction qui renvoie le nombre de voisins vivants de la cellule
    public int voisinsVivants() {
        int s = 0;
        int n = voisins.size();
        for (int i = 0; i < n; i++) {
            if (voisins.get(i).enVie) s += 1;
        }
        return s;
    }

    // Fonction qui détermine le prochain état de la cellule
    public void determinerProchainEtat() {
        int v = voisinsVivants();

        if (enVie) {
            // Règles pour une cellule vivante
            if (v < 2 || v > 3) {
                prochainEtat = false; // Mort par sous-population ou surpopulation
            }
        } else {
            // Règles pour une cellule morte
            if (v == 3) {
                prochainEtat = true; // Naissance
            }
        }
    }

    // Fonction qui fait évoluer la cellule vers son prochain état
    public void evoluer() {
        enVie = prochainEtat;
    }
}