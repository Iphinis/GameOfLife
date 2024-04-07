import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Scanner;

class Jeu {
    static Grille grille;
    static int lignes = 10;
    static int colonnes = 20;
    static int tour = 0;
    static int methodeEvolution = 0;
    static List<Motif> motifs;

    static enum ModeJeu {
        CLASSIQUE,
        HIGHLIFE,
        REPLICATOR,
        DAY_AND_NIGHT,
        LIFE_WITHOUT_DEATH,
        AMOEBA
    }

    static ModeJeu[] modes = ModeJeu.values();

    private static void initialiserGrille() {
        // Initialiser les motifs
        try {
            Motifs.initialiserMotifs("data/motifs/");
            motifs = Motifs.getMotifs();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialiser la grille
        grille = new Grille(colonnes, lignes, Motifs.getPeriodiciteMax());
    }

    private static void naitreCellules(Scanner scanner) {
        int nb, k, x, y;

        System.out.print("Combien de cellules vivantes insérer ? ");
        nb = scanner.nextInt();
        scanner.nextLine();

        for (k = 0; k < nb; k++) {
            System.out.print("Cellule n°" + (k + 1) + "\n");
            do {
                System.out.print("Saisir x entre 0 et " + (colonnes - 1) + " : ");
                x = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Saisir y entre 0 et " + (lignes - 1) + " : ");
                y = scanner.nextInt();
                scanner.nextLine();
            } while (!grille.estDansGrille(x, y));
            grille.naitreCellule(x, y);
        }
    }

    private static void naitreMotif(Scanner scanner) {
        int nb, x, y;

        System.out.println("Motifs disponibles :");
        for (int i=0; i < motifs.size(); i++) {
            System.out.println(i + ". " + motifs.get(i).getNom());
        }
        System.out.println("Quel motif voulez vous faire naître ?");
        do {
            nb = scanner.nextInt();
        } while (nb < 0 || nb >= motifs.size());

        do {
            System.out.print("Saisir x entre 0 et " + (colonnes - 1) + " : ");
            x = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Saisir y entre 0 et " + (lignes - 1) + " : ");
            y = scanner.nextInt();
            scanner.nextLine();
        } while (!grille.estDansGrille(x, y));

        grille.insererMotif(motifs.get(nb),new int[]{x,y});
    }

    private static void tuerCellules(Scanner scanner) {
        int nb, k, x, y;

        System.out.print("Combien de cellules tuer ? ");
        nb = scanner.nextInt();
        scanner.nextLine();

        for (k = 0; k < nb; k++) {
            System.out.print("Cellule n°" + (k + 1) + "\n");
            do {
                System.out.print("Saisir x entre 0 et " + (colonnes - 1) + " : ");
                x = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Saisir y entre 0 et " + (lignes - 1) + " : ");
                y = scanner.nextInt();
                scanner.nextLine();
            } while (!grille.estDansGrille(x, y));
            grille.tuerCellule(x, y);
        }
    }

    private static void changerModeJeu(Scanner scanner) {
        int choix;

        System.out.println("Modes de jeu disponibles :");
        System.out.println("1. Classique");
        System.out.println("2. Highlife");
        System.out.println("3. Replicator");
        System.out.println("4. Day and Night");
        System.out.println("5. Life Without Death");
        System.out.println("6. Amoeba");

        do {
            System.out.print("Veuillez choisir un mode de jeu : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le retour à la ligne
        } while (choix < 1 || choix > 6); // Répéter tant que le choix n'est pas valide
        methodeEvolution = choix-1;
    }

    private static void menuTour(Scanner scanner) {
        int choix;
        do {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Grille actuelle");
            grille.afficher();
            System.out.println("1. Réinitialiser la grille");
            System.out.println("2. Insérer cellule(s)");
            System.out.println("3. Insérer motif");
            System.out.println("4. Tuer cellule(s)");
            System.out.println("5. Avancer de n tours");
            System.out.println("6. Changer le mode de jeu : actuel = " + modes[methodeEvolution]);
            System.out.println("7. Sauvegarder la grille");
            System.out.println("8. Quitter");
            System.out.print("Choix : ");
            System.out.println(grille.grille[5][9].getEnVie());
	    System.out.println(grille.grille[5][9].voisins.size());
            choix = scanner.nextInt();
            switch (choix) {
                case 1:
                    initialiserGrille();
                    System.out.print("OK REINITIASALITION GRILLE\n");
                    System.out.print("COLONNES = " + grille.colonnes + "\n");
                    System.out.print("LIGNES = " + grille.lignes + "\n");
                    System.out.print("PERIODMAX = " + grille.periodiciteMax + "\n");
                    break;
                case 2:
                    // System.out.print("x = " + grille.colonnes);
                    // System.out.print("y = " + grille.lignes);
                    // System.out.print("OK COORDONNEES X Y\n");
                    naitreCellules(scanner);
                    System.out.print("OK NAISSANCE CELLULES\n");
                    break;
                case 3:
                    naitreMotif(scanner);
                    System.out.print("OK NAISSANCE MOTIF\n");
                    break;
                case 4:
                    tuerCellules(scanner);
                    System.out.print("OK TUER CELLULES\n");
                    break;
                case 5:
                    System.out.print("Entrez le nombre d'iterations a effectuer : ");
                    int nbTours;
                    if (scanner.hasNextInt()) {
                        nbTours = scanner.nextInt();
                        avancerTour(nbTours);
                    } else {
                        System.out.println("Nombre de tours invalide.");
                        scanner.nextLine();
                    }
                    break;
                case 6:
                    changerModeJeu(scanner);
                    break;
                case 7:
                    grille.sauvegarderGrille("data/grilles/nouvelle_grille.txt");
                    break;
                case 8:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez choisir une option valide.");
                    break;
            }
        } while (choix != 8);
        scanner.close(); // Fermer le scanner après avoir terminé la boucle
    }

    // Methode statique pour avancer d'un tour dans le jeu
    private static void avancerTour(int n) {
        int tour = 0;
        boolean valide = true;
        grille.sauvegarderEtat();
        
        while ((valide) && (tour < n)) {
	    tour += 1;
            System.out.println("nb etats enregistres = " + grille.etatsPrecedents.size());
            System.out.println("Tour " + tour);
            grille.evoluerGrille(methodeEvolution);
            

            grille.afficher();

            HashMap<String, Integer> motifsDetectes = grille.detecterMotifs(tour);
            if (!motifsDetectes.isEmpty())
                System.out.println("Motifs détectés :");
            for (String name : motifsDetectes.keySet()) {
                String value = motifsDetectes.get(name).toString();
                System.out.println(name + " " + value);
            }
            
            // Vérifier si la grille est vide
            if (grille.estGrilleVide()) {
                System.out.println("Toutes les cellules sont mortes. Arrêt du jeu.\n");
                valide = false;
            }

            // Vérifier si la grille se répète
            if ((tour > 2) && (grille.grilleSeRepete())) {
                System.out.println("Le jeu s'arrête car la grille se répète.\n");
                valide = false;
            }

            System.out.println();
            grille.sauvegarderEtat();

        }
    }

    private static void menuInitGrille(Scanner scanner) {
        int choix;
        boolean grilleInit = false;
        File repertoire = new File("data/grilles/");

        String nomFichier;

        do {
            System.out.println("GOF BY SAAD X ROJAN:");
            System.out.println("1. Nouvelle partie");
            System.out.println("2. Charger une partie");
            System.out.println("3. Quitter");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    initialiserGrille();
                    System.out.print("OK INITIASALITION GRILLE\n");
                    System.out.print("COLONNES = " + grille.colonnes + "\n");
                    System.out.print("LIGNES = " + grille.lignes + "\n");
                    System.out.print("PERIODMAX = " + grille.periodiciteMax + "\n");
                    grilleInit = true;
                    break;
                case 2:
                    initialiserGrille();
                    if (repertoire.isDirectory()) {
                        File[] fichiers = repertoire.listFiles();
                        if (fichiers != null && fichiers.length > 0) {
                            nomFichier = menuChoixFichier(scanner, fichiers);
                            //System.out.println("OK TEST CHOIX NOM FICHIER");
                            grille.chargerGrille("data/grilles/" + nomFichier);
                            System.out.print("OK CHARGEMENT GRILLE\n");
                    System.out.print("COLONNES = " + grille.colonnes + "\n");
                    System.out.print("LIGNES = " + grille.lignes + "\n");
                    System.out.print("PERIODMAX = " + grille.periodiciteMax + "\n");
                            grilleInit = true;
                        } else {
                            System.out.println("Le répertoire est vide.");
                        }
                    } else {
                        System.out.println("Le chemin spécifié ne pointe pas vers un répertoire.");
                    }
                    break;
                case 3:
                    System.out.println("Au revoir !");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez choisir une option valide.");
                    break;
            }

        } while ((choix != 3) && (grilleInit == false));
    }

    private static String menuChoixFichier(Scanner scanner, File[] fichiers) {
        boolean fichierValide;
        String nomFichier;

        System.out.println("Liste des fichiers dans le répertoire :");
        for (File fichier : fichiers) {
            System.out.println(fichier.getName());
        }
        do {
            System.out.println("Veuillez choisir le nom du fichier à charger : ");
            nomFichier = scanner.nextLine();
            fichierValide = false;

            for (File fichier : fichiers) {
                if (fichier.getName().equals(nomFichier)) {
                    fichierValide = true;
                    break;
                }
            }
            if (!fichierValide) {
                System.out.println("Nom de fichier invalide. Veuillez choisir un fichier valide.");
            }
        } while (!fichierValide);
        return nomFichier;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        menuInitGrille(scanner);
        menuTour(scanner);
        scanner.close();
    }
}
