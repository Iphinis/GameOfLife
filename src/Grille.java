import java.util.List;

class Grille {
    int largeur;
    int hauteur;
    
    Cellule[][] cellules;
    
    // Constructeur
    public Grille(int largeur, int hauteur, List<int[]> cellulesDepart) throws ExceptionInInitializerError {
        this.largeur = largeur;
        this.hauteur = hauteur;

        cellules = new Cellule[largeur][hauteur];

        // Initialiser les cellules
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                cellules[i][j] = new Cellule();
            }
        }
        
        // Initialiser les voisins des cellules
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
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
            int x = cellulesDepart.get(i)[0];
            int y = cellulesDepart.get(i)[1];

            if(x <= 0 || x > largeur || y <= 0 || y > hauteur) {
                throw new ExceptionInInitializerError("Coordonnées (" + x + "," + y + ") en dehors de la grille " + largeur + "x" + hauteur);
            }

            cellules[x-1][y-1].enVie = true;
            cellules[x-1][y-1].prochainEtat = true;
        }
    }

    // Méthode pour faire évoluer les cellules
    public void evoluerCellules() {
        // Déterminer le prochain état des cellules à l'étape suivante
        for(int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                cellules[i][j].determinerProchainEtat();
            }
        }

        for(int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                cellules[i][j].evoluer();
            }
        }
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

        // Pour respecter la convention (x,y) et l'affichage ligne par ligne on parcourt l'axe y et puis la l'axe x
        for(int i = 0; i < hauteur; i++) {
            res = res.concat("|");
            for (int j = 0; j < largeur; j++) {
                
                if(cellules[j][i].enVie) etat = "*";
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
                    if(cellules[j][i].enVie) etat = "*";
                else etat = " ";
                }
                else {
                    // Pour debug le nombre de voisins des cellules
                    etat = Integer.toString(cellules[j][i].voisinsVivants());
                }

                res = res.concat(etat);
            }
            res = res.concat("|\n");
        }
        System.out.print(res);

        System.out.println(bordure + "\n");
    }
    
    
}
