import java.util.List;
import java.util.ArrayList;

class Jeu {
    static Grille grille;
    
    static int tour = 0;
    
    // Méthode statique pour démarrer le jeu
    private static void demarrer() {
        List<int[]> cellulesDepart = new ArrayList<int[]>();

        cellulesDepart.addAll(Motifs.doubleCarre(1,3));
        cellulesDepart.addAll(Motifs.barreVerticale(7,2));

        // Pour lever une exception s'il y a un problème de coordonnées en dehors de la grille
        try {
            // Initialiser grille et créer cellules de départ
            grille = new Grille(10, 10, cellulesDepart);
        } catch (Exception e) {
            throw e;
        }
    }
    
// Methode statique pour avancer d'un tour dans le jeu
	private static void avancerTour() {
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
            return;
        }
        
        // Vérifier si la grille se répète
        if (grille.grilleSeRepete()) {
            System.out.println("Le jeu s'arrête car la grille se répète.");
            return;
        }
    }
    tour += 1;
}


    // Méthode statique principale du projet
    public static void main(String[] args) {
        // Ne fixez pas un nombre prédéfini de tours, laissez le jeu s'arrêter lorsqu'une répétition est détectée
        while(true) {
            avancerTour();
        }
    }
}

