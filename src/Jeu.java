import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

class Jeu {
    static Grille grille;
    static int lignes = 10;
    static int colonnes = 20;
    static int tour = 0;
    static int methodeEvolution = 1;
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

        // Exemple d'insertion de motif (exemple à ne pas garder ici)
        //grille.insererMotif(motifs.get(0),new int[]{3,3});

        // Pour charger depuis un fichier
        //grille.chargerGrille("data/grilles/grille.txt");
    }
    

	private static void naitreCellules() {
		Scanner scanner = new Scanner(System.in);
		int nb, k, x, y;

		System.out.print("Combien de cellules vivantes insérer ? ");
		nb = scanner.nextInt();
		scanner.nextLine();

		for (k = 0; k < nb; k++) {
		System.out.print("Cellule n°" + (k+1) +"\n");
			do {
				System.out.print("Saisir x entre 0 et " + (colonnes - 1) + " : ");
				x = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Saisir y entre 0 et " + (lignes - 1) + " : ");
				y = scanner.nextInt();
				scanner.nextLine();
			} while (!grille.estDansGrille(x,y));
		grille.naitreCellule(x,y);
		//scanner.close();
		}
	}
	
	private static void tuerCellules() {
		Scanner scanner = new Scanner(System.in);
		int nb, k, x, y;

		System.out.print("Combien de cellules tuer ? ");
		nb = scanner.nextInt();
		scanner.nextLine();

		for (k = 0; k < nb; k++) {
		System.out.print("Cellule n°" + (k+1) +"\n");
			do {
				System.out.print("Saisir x entre 0 et " + (colonnes - 1) + " : ");
				x = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Saisir y entre 0 et " + (lignes - 1) + " : ");
				y = scanner.nextInt();
				scanner.nextLine();
			} while (!grille.estDansGrille(x,y));
		grille.tuerCellule(x,y);
		//scanner.close();
		}
	}
	

	private static void changerModeJeu() {
	Scanner scanner = new Scanner(System.in);
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
	methodeEvolution = choix;
	//scanner.close();
	}


	private static void menuTour() {
    int choix;
    Scanner scanner = new Scanner(System.in); // Créer le scanner en dehors de la boucle
    do {
    	System.out.println("Grille actuelle");
        grille.afficher();
        System.out.println("1. Réinitialiser la grille");
        System.out.println("2. Insérer cellule(s)");
        System.out.println("3. Tuer cellule(s)");
        System.out.println("4. Avancer de n tours");
        System.out.println("5. Changer le mode de jeu : actuel = " + modes[methodeEvolution - 1]);
        System.out.println("6. Sauvegarder la grille");
        System.out.println("7. Quitter");
        System.out.print("Choix : ");

        
            choix = scanner.nextInt();
            switch (choix) {
                case 1:
                    initialiserGrille();
                    System.out.print("OK REINITIALISATION GRILLE\n");
                    break;
                case 2:
                   //System.out.print("x = " + grille.colonnes);
                   //System.out.print("y = " + grille.lignes);
                   //System.out.print("OK COORDONNEES X Y\n");
                    naitreCellules();
                    System.out.print("OK NAISSANCE CELLULES\n");
                    break;
                case 3:
                    tuerCellules();
                    System.out.print("OK TUER CELLULES\n");
                    break;
                case 4:
                    System.out.print("Entrez le nombre de tours à avancer : ");
                    int nbTours;
                    if (scanner.hasNextInt()) {
                        nbTours = scanner.nextInt();
                        avancerTour(nbTours);
                    } else {
                        System.out.println("Nombre de tours invalide.");
                        scanner.nextLine(); // Consume the invalid input
                    }
                    break;
                case 5:
                    changerModeJeu();
                    break;
                case 6:
                    grille.sauvegarderGrille("data/grilles/nouvelle_grille.txt");
                    break;
                case 7:
                    System.out.println("Au revoir !");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez choisir une option valide.");
                    break;
            }
    } while (choix != 7);
    scanner.close(); // Fermer le scanner après avoir terminé la boucle
}



        
     // Methode statique pour avancer d'un tour dans le jeu
    private static void avancerTour(int n) {
    	int tour=1;
    	boolean valide=true;
    	grille.sauvegarderEtat();
    	while((valide) && (tour<n)){
    		
    		System.out.println("nb etats enregistres = " + grille.etatsPrecedents.size());
		System.out.println("Tour " + tour);
		grille.evoluerGrille(methodeEvolution);
		tour += 1;

		grille.afficher();

		HashMap<String, Integer> motifsDetectes = grille.detecterMotifs(tour);
		if(!motifsDetectes.isEmpty()) System.out.println("Motifs détectés :");
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
		if ((tour>2) && (grille.grilleSeRepete())) {
		    System.out.println("Le jeu s'arrête car la grille se répète.\n");
		    valide = false;
		}

		
		System.out.println();
		grille.sauvegarderEtat();
        }
    }
    
    
        private static void menuInitGrille() {
    int choix;
    boolean grilleInit = false;
    File repertoire = new File("data/grilles/");

    Scanner scanner = new Scanner(System.in);
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
                grilleInit = true;
                break;
            case 2:
                initialiserGrille();
                if (repertoire.isDirectory()) {
                    File[] fichiers = repertoire.listFiles();
                    if (fichiers != null && fichiers.length > 0) {
                        nomFichier = menuChoixFichier(fichiers);
                        System.out.println("OK TEST CHOIX NOM FICHIER");
                        grille.chargerGrille("data/grilles/" + nomFichier);
                        System.out.println("OK TEST CHARGEMENT GRILLE");
                        System.out.println("Grille chargée");
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

	
	private static String menuChoixFichier(File[] fichiers) {
	boolean fichierValide;
        Scanner scanner = new Scanner(System.in);
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
       // scanner.close();
        return nomFichier;
    }
    
    
    public static void main(String[] args) {
        menuInitGrille();
        menuTour();
    }
}
