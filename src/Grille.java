import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


class Grille {
	private int largeur;
	private int hauteur;
	private int periodiciteMax;
	private Cellule[][] grille;
	private List<Cellule[][]> etatsPrecedents;
	
	public Grille(int lignes, int colonnes, int periodiciteMax, Cellule[][] grilleDepart) {
		this.hauteur = lignes;
		this.largeur = colonnes;
		this.periodiciteMax = periodiciteMax;
		this.grille = new Cellule[lignes][colonnes];
		this.etatsPrecedents = new ArrayList<>();
		initialiserGrille(grilleDepart);
	}
	
	public void naitreCellule(int x, int y) throws IllegalArgumentException {
        	if (x >= hauteur || y >= largeur || x < 0 || y < 0) {
          	  	throw new IllegalArgumentException("Coordonnées en dehors des limites de la grille.");
 	       }else{
			grille[x][y].setEnVie(true);
 	       } 
 	}
 	
 	public void tuerCellule(int x, int y) throws IllegalArgumentException {
        	if (x >= hauteur || y >= largeur || x < 0 || y < 0) {
          	  	throw new IllegalArgumentException("Coordonnées en dehors des limites de la grille.");
 	       }else{
			grille[x][y].setEnVie(false);
 	       } 
 	}
    
	
	public void initialiserGrille(Cellule[][] grilleDepart){
    
        	for (int i = 0; i < hauteur; i++) {
            		for (int j = 0; j < largeur; j++) {
                		grille[i][j] = new Cellule();
            		}
        	}
        	// Initialiser les voisins des cellules
		for (int i = 0; i < hauteur; i++) {
		    for (int j = 0; j < largeur; j++) {
		        // Côtés
		        if (i > 0) {
		            grille[i][j].addVoisin(grille[i - 1][j]);
		        }
		        if (i < hauteur - 1) {
		            grille[i][j].addVoisin(grille[i + 1][j]);
		        }
		        if (j > 0) {
		            grille[i][j].addVoisin(grille[i][j - 1]);
		        }
		        if (j < largeur - 1) {
		            grille[i][j].addVoisin(grille[i][j + 1]);
		        }
		        // Diagonales
		        if (i > 0 && j > 0) {
		            grille[i][j].addVoisin(grille[i - 1][j - 1]);
		        }
		        if (i > 0 && j < largeur - 1) {
		            grille[i][j].addVoisin(grille[i - 1][j + 1]);
		        }
		        if (i < hauteur - 1 && j > 0) {
		            grille[i][j].addVoisin(grille[i + 1][j - 1]);
		        }
		        if (i < hauteur - 1 && j < largeur - 1) {
		            grille[i][j].addVoisin(grille[i + 1][j + 1]);
		        }
		    }
		}
	    }

	// Méthode pour vérifier si la grille est vide
	public boolean estGrilleVide() {
		for(int i = 0; i < hauteur; i++) {
		    for (int j = 0; j < largeur; j++) {
			if (grille[i][j].getEnVie()) {
			    return false;
			}
		    }
		}
		return true;
	}


	// Méthode pour faire évoluer les grille
	public void evoluerGrille(int methodeEvolution, int choix) {
		// Vérifier si la grille est vide
		if (estGrilleVide()) {
		    System.out.println("Toutes les grille sont mortes. Arrêt du jeu.");
		    return;
		}

		// Déterminer le prochain état des grille à l'étape suivante
		
		    	switch (choix) {
			    case 1:
				 for(int i = 0; i < hauteur; i++) {
		   			 for (int j = 0; j < largeur; j++) {
						grille[i][j].determinerProchainEtatHighlife();
		    			}
				}
				break;
			    case 2:
				for(int i = 0; i < hauteur; i++) {
		   			 for (int j = 0; j < largeur; j++) {
						grille[i][j].determinerProchainEtatReplicator();
		    			}
				}
				break;
			    case 3:
				for(int i = 0; i < hauteur; i++) {
		   			 for (int j = 0; j < largeur; j++) {
						grille[i][j].determinerProchainEtatDayAndNight();
		    			}
				}
				break;
			    case 4:
				for(int i = 0; i < hauteur; i++) {
		   			 for (int j = 0; j < largeur; j++) {
						grille[i][j].determinerProchainEtatLifeWithoutDeath();
		    			}
				}
				break;
			    case 5:
				for(int i = 0; i < hauteur; i++) {
		   			 for (int j = 0; j < largeur; j++) {
						grille[i][j].determinerProchainEtatAmoeba();
		    			}
				}
				break;
			    default:
				for(int i = 0; i < hauteur; i++) {
		   			 for (int j = 0; j < largeur; j++) {
						grille[i][j].determinerProchainEtatClassique();
		    			}
				}
				break;
			}
		    

		// Faire évoluer les cellules
		for(int i = 0; i < hauteur; i++) {
		    for (int j = 0; j < largeur; j++) {
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
		for(int i = 0; i < largeur + 2; i++) {
		    bordure = bordure.concat("-");
		}

		System.out.println(bordure);

		for(int i = 0; i < hauteur; i++) {
		    res = res.concat("|");
		    for (int j = 0; j < largeur; j++) {
			
			if(grille[i][j].getEnVie()) etat = "*";
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

		// faire évoluer les grille et les afficher
		for(int i = 0; i < largeur + 2; i++) {
		    bordure = bordure.concat("-");
		}

		System.out.println(bordure);

		// Pour respecter la convention (x,y) et l'affichage ligne par ligne on parcourt l'axe y et puis la l'axe x
		for(int i = 0; i < hauteur; i++) {
		    res = res.concat("|");
		    for (int j = 0; j < largeur; j++) {

			if(debug) {
			    // Pour debug le nombre de voisins des grille
			    etat = Integer.toString(grille[i][j].voisinsVivants());
			}
			else {
			    if(grille[i][j].getEnVie()) etat = "*";
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
		    if (Arrays.deepEquals(grille, etatsPrecedents.get(i))) {
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
			copie[i][j] = grille[i][j];
		    }
		}
		if(etatsPrecedents.size() == periodiciteMax) {
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
		writer.write(this.hauteur);
		writer.write(this.largeur);
		for (int i = 0; i < hauteur; i++) {
		    for (int j = 0; j < largeur; j++) {
			if (grille[i][j].getEnVie()) writer.write("O");
			else writer.write("_");
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

		// Lecture de la hauteur et de la largeur depuis le fichier
		this.hauteur = Integer.parseInt(reader.readLine());
		this.largeur = Integer.parseInt(reader.readLine());

		// Initialisation de la grille avec les dimensions lues
		this.grille = new Cellule[this.hauteur][this.largeur];

		// Lecture de la grille depuis le fichier
		for (int i = 0; i < hauteur; i++) {
		    String ligne = reader.readLine();
		    for (int j = 0; j < largeur; j++) {
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
}
