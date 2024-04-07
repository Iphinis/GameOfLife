import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Motif {
    private String nom;
    private int periodicite;

    private List<int[]> listeVivantes = new ArrayList<>();

    private int rotation;
    private int miroir;

    public Motif(String nom, int periodicite, List<int[]> listeVivantes, int rotation, int miroir) {
        this.nom = nom;
        this.periodicite = periodicite;

        this.listeVivantes = listeVivantes;

        this.rotation = rotation;
        this.miroir = miroir;
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

    public int getRotation() {
        return rotation;
    }

    public int getMiroir() {
        return miroir;
    }

    public void afficher() {
        for (int[] coos : listeVivantes) {
            System.out.println(coos[0] + "," + coos[1]);
        }
    }

    public boolean equals(Object o) {
        if(o == null || o.getClass() != this.getClass()) return false;

        Motif autreMotif = (Motif) o;

        List<int[]> autreListeVivantes = autreMotif.getListeVivantes();
        if(this.listeVivantes.size() != autreListeVivantes.size()) return false;

        for (int[] coos : listeVivantes) {
            boolean trouve = false;
            for (int[] coos2 : autreListeVivantes) {
                if (Arrays.equals(coos, coos2) ||
                    Arrays.equals(Arrays.stream(coos).map(x -> Math.abs(x)).toArray(), coos2) ||
                    Arrays.equals(coos, Arrays.stream(coos2).map(x -> Math.abs(x)).toArray())) {
                    trouve = true;
                    break;
                }
            }
            if (!trouve) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return this.getNom() + ((this.getRotation() != 0) ? " (rot: " + this.getRotation() + ")" : "") + ((this.getMiroir() != 0) ? " (mir: " + this.getMiroir() + ")" : "");
    }
}