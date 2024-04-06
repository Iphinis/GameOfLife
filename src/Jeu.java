import java.util.HashMap;
import java.util.List;

class Jeu {
    static Grille grille;
    static int lignes = 10;
    static int colonnes = 20;

    static int tour = 0;
    static int methodeEvolution = 0;

    static List<Motif> motifs;

    private static void initialiserGrille() {
        // Initialiser les motifs
        try {
            Motifs.initialiserMotifs("data/motifs/");
            motifs = Motifs.getMotifs();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialiser la grille
        grille = new Grille(lignes, colonnes, Motifs.getPeriodiciteMax());

        // Exemple d'insertion de motif (exemple à ne pas garder ici)
        grille.insererMotif(motifs.get(0),new int[]{3,3});

        // Pour charger depuis un fichier
        //grille.chargerGrille("data/grilles/grille.txt");
    }

    // Methode statique pour avancer d'un tour dans le jeu
    private static boolean avancerTour() {
        System.out.println("Tour " + tour);
        if(tour != 0) grille.evoluerGrille(methodeEvolution);
        else initialiserGrille();

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
            return false;
        }

        // Vérifier si la grille se répète
        if (grille.grilleSeRepete()) {
            System.out.println("Le jeu s'arrête car la grille se répète.\n");
            return false;
        }

        tour += 1;
        System.out.println();
        return true;
    }

    // Méthode statique principale du projet
    public static void main(String[] args) {
        // Le jeu s'arrête lorsqu'une répétition est détectée ou que la grille est vide
        while(avancerTour());

        //grille.sauvegarderGrille("data/grilles/nouvelle_grille.txt");
    }
}
