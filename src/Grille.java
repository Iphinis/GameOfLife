import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

class Grille {
    public int colonnes;
    public int lignes;
    public int periodiciteMax;
    public Cellule[][] grille;
    public List<boolean[][]> grillesPrecedentes;

    public Grille(int lignes, int colonnes, int periodiciteMax) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.periodiciteMax = periodiciteMax;
        this.grille = new Cellule[lignes][colonnes];
        this.grillesPrecedentes = new ArrayList<>();
        initialiserGrille();
    }

    public boolean estDansGrille(int l, int c) {
        return !(l < 0 || l >= lignes || c < 0 || c >= colonnes);
    }

    public void naitreCellule(int l, int c) throws IllegalArgumentException {
        if (!estDansGrille(l, c)) {
            throw new IllegalArgumentException("Coordonnées en dehors des limites de la grille.");
        } else {
            grille[l][c].setEnVie(true);
        }
    }

    public void insererMotif(Motif motif, int[] origine) throws IllegalArgumentException {
        for (int[] coos : motif.getListeVivantes()) {
            naitreCellule(origine[0] + coos[0], origine[1] + coos[1]);
        }
    }

    public void tuerCellule(int l, int c) throws IllegalArgumentException {
        if (!estDansGrille(l, c)) {
            throw new IllegalArgumentException("Coordonnées en dehors des limites de la grille.");
        } else {
            grille[l][c].setEnVie(false);
        }
    }

    public void initialiserGrille() {

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                grille[i][j] = new Cellule();
                grille[i][j].setEnVie(false);
                grille[i][j].setProchainEtat(false);
                grille[i][j].setPosition(new int[] { i, j });
            }
        }
        // Initialiser les voisins des cellules
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                // Côtés
                if (i > 0) {
                    grille[i][j].addVoisin(grille[i - 1][j]);
                }
                if (i < lignes - 1) {
                    grille[i][j].addVoisin(grille[i + 1][j]);
                }
                if (j > 0) {
                    grille[i][j].addVoisin(grille[i][j - 1]);
                }
                if (j < colonnes - 1) {
                    grille[i][j].addVoisin(grille[i][j + 1]);
                }
                // Diagonales
                if (i > 0 && j > 0) {
                    grille[i][j].addVoisin(grille[i - 1][j - 1]);
                }
                if (i > 0 && j < colonnes - 1) {
                    grille[i][j].addVoisin(grille[i - 1][j + 1]);
                }
                if (i < lignes - 1 && j > 0) {
                    grille[i][j].addVoisin(grille[i + 1][j - 1]);
                }
                if (i < lignes - 1 && j < colonnes - 1) {
                    grille[i][j].addVoisin(grille[i + 1][j + 1]);
                }
            }
        }
    }

    // Méthode pour vérifier si la grille est vide
    public boolean estGrilleVide() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                if (grille[i][j].getEnVie()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Méthode pour faire évoluer les grille
    public void evoluerGrille(int choix) {
     // Déterminer le prochain état des grille à l'étape suivante
        switch (choix) {
            case 0:
                for (int i = 0; i < lignes; i++) {
                    for (int j = 0; j < colonnes; j++) {
                        grille[i][j].determinerProchainEtatClassique();
                    }
                }
                break;
            case 1:
                for (int i = 0; i < lignes; i++) {
                    for (int j = 0; j < colonnes; j++) {
                        grille[i][j].determinerProchainEtatHighlife();
                    }
                }
                break;
            case 2:
                for (int i = 0; i < lignes; i++) {
                    for (int j = 0; j < colonnes; j++) {
                        grille[i][j].determinerProchainEtatReplicator();
                    }
                }
                break;
            case 3:
                for (int i = 0; i < lignes; i++) {
                    for (int j = 0; j < colonnes; j++) {
                        grille[i][j].determinerProchainEtatDayAndNight();
                    }
                }
                break;
            case 4:
                for (int i = 0; i < lignes; i++) {
                    for (int j = 0; j < colonnes; j++) {
                        grille[i][j].determinerProchainEtatLifeWithoutDeath();
                    }
                }
                break;
            case 5:
                for (int i = 0; i < lignes; i++) {
                    for (int j = 0; j < colonnes; j++) {
                        grille[i][j].determinerProchainEtatAmoeba();
                    }
                }
                break;
            default:
                for (int i = 0; i < lignes; i++) {
                    for (int j = 0; j < colonnes; j++) {
                        grille[i][j].determinerProchainEtatClassique();
                    }
                }
                break;
        }

        // Faire évoluer les cellules
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                grille[i][j].evoluer();
            }
        }
    }

    // Méthode pour afficher la grille contenant les cellules
    public void afficher() {
        String bordure = "";

        String res = "";
        String etat = "";

        // faire évoluer les grille et les afficher
        for (int i = 0; i < colonnes + 2; i++) {
            bordure = bordure.concat("-");
        }

        System.out.println(bordure);

        for (int i = 0; i < lignes; i++) {
            res = res.concat("|");
            for (int j = 0; j < colonnes; j++) {

                if (grille[i][j].getEnVie())
                    etat = "*";
                else
                    etat = " ";

                res = res.concat(etat);
            }
            res = res.concat("|\n");
        }
        System.out.print(res);

        System.out.println(bordure);
    }

    // Méthode pour afficher la grille contenant les cellules
    public void afficher(boolean debug) {
        String bordure = "";

        String res = "";
        String etat = "";

        // faire évoluer les grille et les afficher
        for (int i = 0; i < colonnes + 2; i++) {
            bordure = bordure.concat("-");
        }

        System.out.println(bordure);

        // Pour respecter la convention (x,y) et l'affichage ligne par ligne on parcourt
        // l'axe y et puis la l'axe x
        for (int i = 0; i < lignes; i++) {
            res = res.concat("|");
            for (int j = 0; j < colonnes; j++) {

                if (debug) {
                    // Pour debug le nombre de voisins des grille
                    etat = Integer.toString(grille[i][j].voisinsVivants());//Integer.toString(grille[i][j].getVoisins().size());
                } else {
                    if (grille[i][j].getEnVie())
                        etat = "*";
                    else
                        etat = " ";
                }

                res = res.concat(etat);
            }
            res = res.concat("|\n");
        }
        System.out.print(res);

        System.out.println(bordure);
    }

    public void detecterMotifs(int tour) {
        List<Motif> listeMotifs = Motifs.getMotifs();

        HashMap<String, Integer> motifsDetectes = new HashMap<>();

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {

                for (Motif motif : listeMotifs) {
                    boolean valide = true;
                    
                    if (motif.getPeriodicite() != 0 && tour % motif.getPeriodicite() != 0)
                        continue;
                    
                    for (int[] coos : motif.getListeVivantes()) {
                        int l = i + coos[0];
                        int c = j + coos[1];

                        if (!estDansGrille(l, c)) {
                            valide = false;
                            break;
                        }

                        Cellule celluleMotif = grille[l][c];

                        if (!celluleMotif.getEnVie()) {
                            valide = false;
                            break;
                        }

                        for (Cellule voisin : celluleMotif.getVoisins()) {
                            if (voisin.equals(celluleMotif) && !voisin.getEnVie()) {
                                valide = false;
                                break;
                            }
                        }
                    }
                    if (!valide)
                        continue;

                    if (motifsDetectes.containsKey(motif.getNom()))
                        motifsDetectes.put(motif.getNom(), motifsDetectes.get(motif.getNom()) + 1);
                    else
                        motifsDetectes.put(motif.getNom(), 1);

                    break;
                }
            }
        }
        
        if (!motifsDetectes.isEmpty())
            System.out.println("Motifs détectés :");
            for (String name : motifsDetectes.keySet()) {
                String value = motifsDetectes.get(name).toString();
                System.out.println(name + " " + value);
            }
    }

    // Méthode pour tester si la grille se répète
    public boolean grilleSeRepete() {
        // Parcourir les états précédents pour détecter une répétition
        for(int k=0; k < grillesPrecedentes.size() - 1; k++) {
            boolean egal = true;
            for (int i = 0; i < lignes; i++) {
                for (int j = 0; j < colonnes; j++) {
                    if(grille[i][j].getEnVie() != grillesPrecedentes.get(k)[i][j]) {
                        egal = false;
                        break;
                    }
                }
            }

            if (egal) {
                // Une répétition a été détectée
                System.out.println("La grille se répète. Il y a une périodicité de " + (grillesPrecedentes.size() - k - 2));
                return true;
            }
        }
        return false;
    }

    // Méthode pour sauvegarder l'état actuel de la grille
    public void sauvegarderEtat() {
        boolean[][] autreGrille = new boolean[lignes][colonnes];

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                autreGrille[i][j] = grille[i][j].getEnVie();
            }
        }

        if (grillesPrecedentes.size() == periodiciteMax) {
            grillesPrecedentes.remove(0);
        }

        grillesPrecedentes.add(autreGrille);
    }

}
