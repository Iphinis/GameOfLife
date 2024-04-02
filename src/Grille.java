import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;


class Grille {
    int largeur;
    int hauteur;

    int periodiciteMax;
    
    Cellule[][] cellules;
    List<Cellule[][]> etatsPrecedents = new ArrayList<>();

    
    // Constructeur
    public Grille(int hauteur, int largeur, int periodiciteMax, List<int[]> cellulesDepart) throws ExceptionInInitializerError {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.periodiciteMax = periodiciteMax;

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
                // Côtés
                if (i > 0) {
                    cellules[i][j].voisins.add(cellules[i - 1][j]);
                }
                if (i < hauteur - 1) {
                    cellules[i][j].voisins.add(cellules[i + 1][j]);
                }
                if (j > 0) {
                    cellules[i][j].voisins.add(cellules[i][j - 1]);
                }
                if (j < largeur - 1) {
                    cellules[i][j].voisins.add(cellules[i][j + 1]);
                }
                // Diagonales
                if (i > 0 && j > 0) {
                    cellules[i][j].voisins.add(cellules[i - 1][j - 1]);
                }
                if (i > 0 && j < largeur - 1) {
                    cellules[i][j].voisins.add(cellules[i - 1][j + 1]);
                }
                if (i < hauteur - 1 && j > 0) {
                    cellules[i][j].voisins.add(cellules[i + 1][j - 1]);
                }
                if (i < hauteur - 1 && j < largeur - 1) {
                    cellules[i][j].voisins.add(cellules[i + 1][j + 1]);
                }
            }
        }

        // Faire naître les cellules de départ
        int length = cellulesDepart.size();
        for(int i=0; i < length; i++) {
            int y = cellulesDepart.get(i)[0];
            int x = cellulesDepart.get(i)[1];

            if(x < 0 || x >= largeur || y < 0 || y >= hauteur) {
                throw new ExceptionInInitializerError("Coordonnées (" + x + "," + y + ") en dehors de la grille " + largeur + "x" + hauteur);
            }

            cellules[y][x].enVie = true;
            cellules[y][x].prochainEtat = true;
        }
        
        sauvegarderEtat();
    }


    // Méthode pour vérifier si la grille est vide
    public boolean estGrilleVide() {
        for(int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (cellules[i][j].enVie) {
                    return false;
                }
            }
        }
        return true;
    }


    // Méthode pour faire évoluer les cellules
    public void evoluerCellules() {
        // Vérifier si la grille est vide
        if (estGrilleVide()) {
            System.out.println("Toutes les cellules sont mortes. Arrêt du jeu.");
            return;
        }

        // Déterminer le prochain état des cellules à l'étape suivante
        for(int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                cellules[i][j].determinerProchainEtat();
            }
        }

        // Faire évoluer les cellules
        for(int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                cellules[i][j].evoluer();
            }
        }

        // Sauvegarder l'état actuel
        sauvegarderEtat();
    }
    

    // Méthode pour afficher la grille contenant les cellules
    public void afficher() {
        String bordure = "";
        
        String res = "";
        String etat = "";

        // faire évoluer les cellules et les afficher
        for(int i = 0; i < largeur + 2; i++) {
            bordure = bordure.concat("-");
        }

        System.out.println(bordure);

        for(int i = 0; i < hauteur; i++) {
            res = res.concat("|");
            for (int j = 0; j < largeur; j++) {
                
                if(cellules[i][j].enVie) etat = "*";
                else etat = " ";

                res = res.concat(etat);
            }
            res = res.concat("|\n");
        }
        System.out.print(res);

        System.out.println(bordure + "\n");
    }


    // Méthode pour afficher la grille contenant les cellules
    public void afficher(boolean debug) {
        String bordure = "";
        
        String res = "";
        String etat = "";

        // faire évoluer les cellules et les afficher
        for(int i = 0; i < largeur + 2; i++) {
            bordure = bordure.concat("-");
        }

        System.out.println(bordure);

        // Pour respecter la convention (x,y) et l'affichage ligne par ligne on parcourt l'axe y et puis la l'axe x
        for(int i = 0; i < hauteur; i++) {
            res = res.concat("|");
            for (int j = 0; j < largeur; j++) {

                if(debug) {
                    // Pour debug le nombre de voisins des cellules
                    etat = Integer.toString(cellules[i][j].voisinsVivants());
                }
                else {
                    if(cellules[i][j].enVie) etat = "*";
                    else etat = " ";
                }

                res = res.concat(etat);
            }
            res = res.concat("|\n");
        }
        System.out.print(res);

        System.out.println(bordure + "\n");
    }

    
    // Méthode pour vérifier si la grille se répète
    public boolean grilleSeRepete() {
        // Parcourir les états précédents pour détecter une répétition
        for (int i = 0; i < etatsPrecedents.size(); i++) {
            if (Arrays.deepEquals(cellules, etatsPrecedents.get(i))) {
                // Calculer la périodicité
                int periode = etatsPrecedents.size() - i;
                System.out.println("La grille se répète après " + i + " itérations.");
                System.out.println("Périodicité : " + periode);
                return true;
            }
        }
        return false;
    }

    // Méthode pour sauvegarder l'état actuel de la grille
    private void sauvegarderEtat() {
        Cellule[][] copie = new Cellule[largeur][hauteur];
        for (int i = 0; i < hauteur; i++){
            for (int j = 0; j < largeur; j++){
                copie[i][j] = cellules[i][j];
            }
        }
        if(etatsPrecedents.size() == periodiciteMax) {
            etatsPrecedents.remove(0);
        }
        etatsPrecedents.add(copie);
    }
    
}
