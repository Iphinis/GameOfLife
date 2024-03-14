import java.util.List;
import java.util.ArrayList;

class Grille {
    int largeur;
    int hauteur;
    
    Cellule[][] cellules;
    
    public Grille(int largeur, int hauteur, List<int[]> cellulesDepart) {
        this.largeur = largeur;
        this.hauteur = hauteur;

        cellules = new Cellule[hauteur][largeur];

        // Initialiser les cellules
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                cellules[i][j] = new Cellule();
            }
        }
        
        // Initialiser les voisins des cellules
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                // Voisin en haut
                if (i > 0) {
                    cellules[i][j].voisins.add(cellules[i - 1][j]);
                }
                // Voisin en bas
                if (i < hauteur - 1) {
                    cellules[i][j].voisins.add(cellules[i + 1][j]);
                }
                // Voisin à gauche
                if (j > 0) {
                    cellules[i][j].voisins.add(cellules[i][j - 1]);
                }
                // Voisin à droite
                if (j < largeur - 1) {
                    cellules[i][j].voisins.add(cellules[i][j + 1]);
                }
                // Voisin en haut à gauche
                if (i > 0 && j > 0) {
                    cellules[i][j].voisins.add(cellules[i - 1][j - 1]);
                }
                // Voisin en haut à droite
                if (i > 0 && j < largeur - 1) {
                    cellules[i][j].voisins.add(cellules[i - 1][j + 1]);
                }
                // Voisin en bas à gauche
                if (i < hauteur - 1 && j > 0) {
                    cellules[i][j].voisins.add(cellules[i + 1][j - 1]);
                }
                // Voisin en bas à droite
                if (i < hauteur - 1 && j < largeur - 1) {
                    cellules[i][j].voisins.add(cellules[i + 1][j + 1]);
                }
            }
        }

        // Faire naître les cellules de départ
        int length = cellulesDepart.size();
        for(int i=0; i < length; i++) {
            cellules[cellulesDepart.get(i)[0]][cellulesDepart.get(i)[1]].enVie = true;
        }
    }

    public void afficher(boolean evolution) {
        String res = "";
        String etat = "";
        String bordure = " ";

        // faire évoluer les cellules et les afficher
        for(int i = 0; i < largeur; i++) {
            bordure = bordure.concat("-");
        }
        bordure = bordure.concat(" ");

        System.out.println(bordure);

        for(int i = 0; i < hauteur; i++) {
            res = res.concat("|");
            for (int j = 0; j < largeur; j++) {
                
                if(cellules[i][j].enVie) etat = "o";
                else etat = " ";

                if(evolution) cellules[i][j].evoluer();

                res = res.concat(etat);
            }
            res = res.concat("|\n");
        }
        System.out.print(res);

        System.out.println(bordure + "\n");
    }
    
    
}
