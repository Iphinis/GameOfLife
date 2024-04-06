import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Grille {
    private int colonnes;
    private int lignes;
    private int periodiciteMax;
    private Cellule[][] grille;
    private List<Cellule[][]> etatsPrecedents;

    public Grille(int lignes, int colonnes, int periodiciteMax) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.periodiciteMax = periodiciteMax;
        this.grille = new Cellule[lignes][colonnes];
        this.etatsPrecedents = new ArrayList<>();
        initialiserGrille();
    }

    private boolean estDansGrille(int c, int l) {
        return !(l < 0 || l >= lignes || c < 0 || c >= colonnes);
    }

    public void naitreCellule(int c, int l) throws IllegalArgumentException {
        if (!estDansGrille(c, l)) {
            throw new IllegalArgumentException("Coordonnées en dehors des limites de la grille.");
        } else {
            grille[l][c].setEnVie(true);
        }
    }

    public void insererMotif(Motif motif, int[] origine) throws IllegalArgumentException {
        for (int[] coos : motif.getListeVivantes()) {
            naitreCellule(origine[0]+coos[0], origine[1]+coos[1]);
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
                grille[i][j].setPosition(new int[]{i, j});
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
        // Vérifier si la grille est vide
        if (estGrilleVide()) {
            System.out.println("Toutes les grille sont mortes. Arrêt du jeu.");
            return;
        }

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
        // Sauvegarder l'état actuel
        sauvegarderEtat();
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
                    etat = Integer.toString(grille[i][j].voisinsVivants());
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

        System.out.println(bordure + "\n");
    }

    // Méthode pour vérifier si la grille se répète
    public boolean grilleSeRepete() {
    	int i;
    	int periode;
        // Parcourir les états précédents pour détecter une répétition
        for (i = 0; i < etatsPrecedents.size(); i++) {
            if (equalsGrille(etatsPrecedents.get(i))) {
                // Calculer la périodicité
                periode = etatsPrecedents.size() - i + 1;
                System.out.println("La grille se répète après " + (i + 1) + " itération(s).");
                System.out.println("Périodicité : " + periode);
                return true;
            }for (i = 0; i < etatsPrecedents.size(); i++) 
        }
        return false;
    }

    // Méthode pour sauvegarder l'état actuel de la grille
    private void sauvegarderEtat() {
        Cellule[][] copie = new Cellule[lignes][colonnes];
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                copie[i][j] = grille[i][j];
            }
        }
        if (etatsPrecedents.size() == periodiciteMax) {
            etatsPrecedents.remove(0);
        }
        etatsPrecedents.add(copie);
    }

    public void sauvegarderGrille(String nomFichier) {
        try {
            // Création d'un FileWriter pour écrire dans le fichier spécifié
            FileWriter fileWriter = new FileWriter(nomFichier, false);

            // Création d'un BufferedWriter qui utilise le FileWriter
            BufferedWriter writer = new BufferedWriter(fileWriter);

            // Sauvegarde de la grille dans le fichier
            writer.write(this.lignes);
            writer.write(this.colonnes);
            for (int i = 0; i < lignes; i++) {
                for (int j = 0; j < colonnes; j++) {
                    if (grille[i][j].getEnVie())
                        writer.write("O");
                    else
                        writer.write("_");
                }
                writer.newLine();
            }
            // Fermeture du BufferedWriter
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chargerGrille(String nomFichier) {
        try {
            // Création d'un FileReader pour lire à partir du fichier spécifié
            FileReader fileReader = new FileReader(nomFichier);

            // Création d'un BufferedReader qui utilise le FileReader
            BufferedReader reader = new BufferedReader(fileReader);

            // Lecture de la lignes et de la colonnes depuis le fichier
            this.lignes = Integer.parseInt(reader.readLine());
            this.colonnes = Integer.parseInt(reader.readLine());

            // Initialisation de la grille avec les dimensions lues
            this.grille = new Cellule[this.lignes][this.colonnes];

            // Lecture de la grille depuis le fichier
            for (int i = 0; i < lignes; i++) {
                String ligne = reader.readLine();
                for (int j = 0; j < colonnes; j++) {
                    // Initialisation des grille en fonction des caractères lus
                    this.grille[i][j] = new Cellule();
                    if (ligne.charAt(j) == 'O') {
                        this.grille[i][j].setEnVie(true);
                    } else {
                        this.grille[i][j].setEnVie(false);
                    }
                }
                etatsPrecedents.add(grille);
            }

            // Fermeture du BufferedReader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> detecterMotifs(int tour) {
        List<Motif> listeMotifs = Motifs.getMotifs();

        HashMap<String, Integer> motifsDetectes = new HashMap<>();

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                for (Motif motif : listeMotifs) {
                    boolean trouve = true;
                    if(tour % motif.getPeriodicite() != 0) break;
                    for (int[] coos : motif.getListeVivantes()) {
                        if (!estDansGrille(i + coos[0], j + coos[1])) {
                            trouve = false;
                            break;
                        }

                        // Vérifier que les cellules vivantes correspondent
                        Cellule cellule = grille[i + coos[0]][j + coos[1]];

                        if (!cellule.getEnVie()) {
                            trouve = false;
                            break;
                        }

                        // Vérifier les voisins de la cellule vivantes du motif pour vérifier que c'est
                        // bien le motif et pas un autre
                        for (Cellule voisin : cellule.getVoisins()) {
                            if (voisin.getPosition().equals(coos) && !voisin.getEnVie()) {
                                trouve = false;
                                break;
                            }
                        }
                    }
                    if (!trouve)
                        continue;

                    if (motifsDetectes.containsKey(motif.getNom()))
                        motifsDetectes.put(motif.getNom(), motifsDetectes.get(motif.getNom()) + 1);
                    else
                        motifsDetectes.put(motif.getNom(), 1);
                }
            }
        }
        return motifsDetectes;
    }
    
    	public boolean equalsGrille(Cellule[][] grille1) {
		for (int i = 0; i < this.lignes; i++) {
            		for (int j = 0; j < this.colonnes; j++) {
            			if (!this.grille[i][j].equals(grille1[i][j])) {
    					return false;
				}
            		}
            	}
            	return true;
	}
	
}
