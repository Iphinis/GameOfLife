import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Jeu {
    static Grille grille;
    static int lignes = 10;
    static int colonnes = 20;
    static int tour = 1;
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

    private static void initialiserMotifs() {
        // Initialiser les motifs
        try {
            Motifs.initialiserMotifs("data/motifs/");
            motifs = Motifs.getMotifs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initialiserGrille() {
        // Initialiser la grille
        grille = new Grille(lignes, colonnes, Motifs.getPeriodiciteMax());
    }

    private static void naitreCellules(Scanner scanner) {
        int nb, k, l, c;

        System.out.print("Combien de cellules vivantes insérer ? ");
        nb = scanner.nextInt();
        scanner.nextLine();

        for (k = 0; k < nb; k++) {
            System.out.print("Cellule n°" + (k + 1) + "\n");
            do {
                System.out.print("Saisir ligne entre 0 et " + (lignes - 1) + " : ");
                l = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Saisir colonne entre 0 et " + (colonnes - 1) + " : ");
                c = scanner.nextInt();
                scanner.nextLine();
            } while (!grille.estDansGrille(l, c));
            grille.naitreCellule(l, c);
        }
    }

    private static void naitreMotif(Scanner scanner) {
        if (motifs == null || motifs.size() == 0) {
            System.out.println("Aucun motif disponible");
            return;
        }
        int nb, l, c;

        System.out.println("Motifs disponibles :");
        for (int i = 0; i < motifs.size(); i++) {
            Motif motif = motifs.get(i);
            System.out.println(i + ". " + motif);
        }
        System.out.println("Quel motif voulez vous insérer ?");
        do {
            nb = scanner.nextInt();
        } while (nb < 0 || nb >= motifs.size());

        do {
            System.out.print("Saisir ligne entre 0 et " + (lignes - 1) + " : ");
            l = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Saisir colonne entre 0 et " + (colonnes - 1) + " : ");
            c = scanner.nextInt();
            scanner.nextLine();
        } while (!grille.estDansGrille(l, c));

        grille.insererMotif(motifs.get(nb), new int[] { l, c });
    }

    private static void tuerCellules(Scanner scanner) {
        int nb, k, l, c;

        System.out.print("Combien de cellules tuer ? ");
        nb = scanner.nextInt();
        scanner.nextLine();

        for (k = 0; k < nb; k++) {
            System.out.print("Cellule n°" + (k + 1) + "\n");
            do {
                System.out.print("Saisir ligne entre 0 et " + (lignes - 1) + " : ");
                l = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Saisir colonne entre 0 et " + (colonnes - 1) + " : ");
                c = scanner.nextInt();
                scanner.nextLine();
            } while (!grille.estDansGrille(l, c));
            grille.tuerCellule(l, c);
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
        methodeEvolution = choix - 1;
    }

    public static void sauvegarderGrille(String nomFichier) {
        if(grille != null) {
            try {
                // Création d'un FileWriter pour écrire dans le fichier spécifié
                FileWriter fileWriter = new FileWriter(nomFichier, false);

                // Création d'un BufferedWriter qui utilise le FileWriter
                BufferedWriter writer = new BufferedWriter(fileWriter);

                // Sauvegarde du nombre de lignes et de colonnes dans le fichier
                writer.write(String.valueOf(grille.lignes));
                writer.newLine();
                writer.write(String.valueOf(grille.colonnes));
                writer.newLine();

                // Sauvegarde de la grille dans le fichier
                for (int i = 0; i < lignes; i++) {
                    for (int j = 0; j < colonnes; j++) {

                        if (grille.grille[i][j].getEnVie()) {
                            writer.write("*");
                        } else {
                            writer.write("_");
                        }
                    }
                    writer.newLine();
                }
                // Fermeture du BufferedWriter
                writer.close();
                System.out.println("Grille sauvegardée !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.err.println("grille n'est pas initialisée, impossible de sauvegarder");
        }
    }

    public static void chargerGrille(String nomFichier) {
        try {
            // Création d'un FileReader pour lire à partir du fichier spécifié
            FileReader fileReader = new FileReader(nomFichier);

            // Création d'un BufferedReader qui utilise le FileReader
            BufferedReader reader = new BufferedReader(fileReader);

            // Lecture des lignes et des colonnes depuis le fichier
            lignes = Integer.parseInt(reader.readLine());
            colonnes = Integer.parseInt(reader.readLine());

            // Initialisation de la grille avec les dimensions lues
            initialiserGrille();

            // Lecture de la grille depuis le fichier
            for (int i = 0; i < lignes; i++) {
                String ligne = reader.readLine();
                for (int j = 0; j < colonnes; j++) {
                    // Initialisation des cellules de la grille en fonction des caractères lus
                    if (ligne.charAt(j) == '*') {
                        grille.grille[i][j].setEnVie(true);
                    } else {
                        grille.grille[i][j].setEnVie(false);
                    }
                }
            }

            // Fermeture du BufferedReader
            reader.close();
            System.out.println("Grille chargée !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void avancerTour(int n) {
        grille.sauvegarderEtat();

        for (int t = 1; t <= n; t++, tour++) {
            System.out.println("Tour " + tour);

            grille.evoluerGrille(methodeEvolution);
            grille.afficher();
            grille.sauvegarderEtat();

            //grille.detecterMotifs(tour);

            // Vérifier si la grille est vide
            if (grille.estGrilleVide()) {
                System.out.println("Toutes les cellules sont mortes. Arrêt du jeu.\n");
                break;
            }

            // Vérifier si la grille se répète
            if (grille.grilleSeRepete()) {
                System.out.println("Le jeu s'arrête car la grille se répète.\n");
                break;
            }
            System.out.println();
        }
    }

    private static void menuTour(Scanner scanner) {
        int choix;
        do {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Grille actuelle");
            grille.afficher();
            //grille.detecterMotifs(tour);
            System.out.println();
            System.out.println("1. Réinitialiser la grille");
            System.out.println("2. Insérer cellule(s)");
            System.out.println("3. Insérer motif");
            System.out.println("4. Tuer cellule(s)");
            System.out.println("5. Avancer de n tours");
            System.out.println("6. Changer le mode de jeu (actuel: " + modes[methodeEvolution] + ")");
            System.out.println("7. Sauvegarder la grille");
            System.out.println("8. Quitter");
            System.out.print("Choix : \n");

            choix = scanner.nextInt();
            switch (choix) {
                case 1:
                    initialiserGrille();
                    break;
                case 2:
                    naitreCellules(scanner);
                    break;
                case 3:
                    naitreMotif(scanner);
                    break;
                case 4:
                    tuerCellules(scanner);
                    break;
                case 5:
                    System.out.print("Entrez le nombre d'iterations à effectuer : ");
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
                    sauvegarderGrille("data/grilles/save_grille.txt");
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

    private static String menuChoixFichier(Scanner scanner, File[] fichiers) {
        boolean fichierValide;
        String nomFichier;

        System.out.println("Liste des fichiers dans le répertoire :");
        for (File fichier : fichiers) {
            System.out.println("- " + fichier.getName());
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

    private static void menuGrille(Scanner scanner) {
        int choix;
        boolean grilleInit = false;
        File repertoire = new File("data/grilles/");

        String nomFichier;

        do {
            System.out.println("GAME OF LIFE BY SAAD X ROJAN:");
            System.out.println("1. Nouvelle partie");
            System.out.println("2. Charger une partie");
            System.out.println("3. Quitter");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    do {
                        System.out.println("Nombre de lignes de la grille : ");
                        lignes = scanner.nextInt();
                        scanner.nextLine();
                    }
                    while (lignes <= 0);

                    do {
                        System.out.println("Nombre de colonnes de la grille : ");
                        colonnes = scanner.nextInt();
                        scanner.nextLine();
                    }
                    while (colonnes <= 0);

                    initialiserGrille();
                    grilleInit = true;
                    break;
                case 2:
                    if (repertoire.isDirectory()) {
                        File[] fichiers = repertoire.listFiles();
                        if (fichiers != null && fichiers.length > 0) {
                            nomFichier = menuChoixFichier(scanner, fichiers);
                            // System.out.println("OK TEST CHOIX NOM FICHIER");
                            chargerGrille("data/grilles/" + nomFichier);
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

    public static void main(String[] args) {
        initialiserMotifs();

        Scanner scanner = new Scanner(System.in);
        menuGrille(scanner);
        menuTour(scanner);
        scanner.close();
    }
}
