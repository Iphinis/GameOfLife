import java.util.List;
import java.util.ArrayList;

class Jeu {
    static Grille grille;
    
    static int tour = 0;
    
    public static void main(String[] args) {
        int nb_tours = 4;
        for(int i=0; i < 10; i++) {
            avancerTour();
        }
    }
    
    private static void demarrer() {
        List<int[]> cellulesDepart = new ArrayList<int[]>();
        cellulesDepart.add(new int[]{2,2});
        cellulesDepart.add(new int[]{2,3});
        cellulesDepart.add(new int[]{2,4});

        // Initialiser grille et créer cellules de départ
        grille = new Grille(10, 10, cellulesDepart);
    }
    
    private static void avancerTour() {
        System.out.println("Tour " + tour);
        tour += 1;

        if(tour == 1) {
            demarrer();
            grille.afficher(false);
        }
        else {
            grille.afficher(true);
        }
    }
}
