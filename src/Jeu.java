import java.util.List;
import java.util.ArrayList;

class Jeu {
    static Grille grille;
    
    static int tour = 0;
    
    // Méthode statique pour démarrer le jeu
    private static void demarrer() {
        List<int[]> cellulesDepart = new ArrayList<int[]>();
        cellulesDepart.add(new int[]{2,2});
        cellulesDepart.add(new int[]{2,3});
        cellulesDepart.add(new int[]{2,4});

        // Initialiser grille et créer cellules de départ
        grille = new Grille(10, 10, cellulesDepart);
    }
    
    // Methode statique pour avancer d'un tour dans le jeu
    private static void avancerTour() {
        if(tour == 0) {
            demarrer();
            System.out.println("Grille de départ");
            grille.afficher();
        }
        else {
            grille.evoluerCellules();
            System.out.println("Tour " + tour);
            grille.afficher();
        }
        tour += 1;
    }

    // Méthode statique principale du projet
    public static void main(String[] args) {
        int nb_tours = 5;
        for(int i=0; i < nb_tours; i++) {
            avancerTour();
        }
    }
}
