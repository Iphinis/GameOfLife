import java.util.List;
import java.util.ArrayList;

public class Motif {
    private String nom;
    private int periodicite;

    private List<int[]> listeVivantes = new ArrayList<>();

    public Motif(String nom, int periodicite, List<int[]> listeVivantes) {
        this.nom = nom;
        this.periodicite = periodicite;

        this.listeVivantes = listeVivantes;
    }

    public String getNom() {
        return nom;
    }

    public int getPeriodicite() {
        return periodicite;
    }

    public List<int[]> getListeVivantes() {
        return listeVivantes;
    }

    public void addListeVivantes(int[] coo) {
        listeVivantes.add(coo);
    }

    public void afficher() {
        for (int[] coos : listeVivantes) {
            System.out.println(coos[0] + "," + coos[1]);
        }
        System.out.println("");
    }
}
