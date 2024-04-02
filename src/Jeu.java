import java.util.List;
import java.util.ArrayList;

class Jeu {
    static Grille grille;
    
    static int tour = 0;
    
    // Méthode statique pour démarrer le jeu
    private static void demarrer() {
        List<int[]> cellulesDepart = new ArrayList<int[]>();

        cellulesDepart.add(new int[]{1,2});
        cellulesDepart.add(new int[]{3,2});

        /*cellulesDepart.addAll(Motifs.doubleCarre(1,3));
        cellulesDepart.addAll(Motifs.barreVerticale(7,2));*/

        // Pour lever une exception s'il y a un problème de coordonnées en dehors de la grille
        try {
            // Initialiser la grille et créer les cellules de départ
            grille = new Grille(10, 10, cellulesDepart);
        } catch (Exception e) {
            throw e;
        }
    }
    
    // Methode statique pour avancer d'un tour dans le jeu
	private static boolean avancerTour() {
        if (tour == 0) {
            demarrer();
            System.out.println("Grille de départ");
            grille.afficher();
        } else {
            grille.evoluerCellules();
            System.out.println("Tour " + tour);
            grille.afficher(true);
            
            // Vérifier si la grille est vide
            if (grille.estGrilleVide()) {
                System.out.println("Toutes les cellules sont mortes. Arrêt du jeu.");
                return false;
            }
            
            // Vérifier si la grille se répète
            if (grille.grilleSeRepete()) {
                System.out.println("Le jeu s'arrête car la grille se répète.");
                return false;
            }
        }
        tour += 1;
        return true;
    }


    // Méthode statique principale du projet
    public static void main(String[] args) {
        // Le jeu s'arrête lorsqu'une répétition est détectée ou que la grille est vide
        while(avancerTour());
    }
}

