import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Motifs {

    private static List<Motif> listeMotifs = new ArrayList<>();

    public static List<Motif> getMotifs() {
        return listeMotifs;
    }

    private static Motif creerMotif(String nom, String cheminFichier)  {
        int periodicite = 0;

        List<int[]> vivantes = new ArrayList<>();

        try {
			// Création d'un fileReader pour lire le fichier
			FileReader fileReader = new FileReader(cheminFichier);
			
			// Création d'un bufferedReader qui utilise le fileReader
			BufferedReader reader = new BufferedReader(fileReader);
			
			// lecture de la première ligne
			String line = reader.readLine();
            periodicite = Integer.parseInt(line);

            line = reader.readLine();
			
			while (line != null) {
                // Enregistrement des coordonnées dans un tableau de Strings
                String tmp[] = line.split(",");

                // Conversion en coordonnées entières
                int[] coords = new int[2];
                coords[0] = Integer.parseInt(tmp[0]);
                coords[1] = Integer.parseInt(tmp[1]);

                // ajout des coordonnées au motif
                vivantes.add(coords);

				// lecture de la prochaine ligne
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return new Motif(nom, periodicite, vivantes, 0, 0);
    }

    // Pour créer un motif alternatif à l'original
    public static Motif creerMotifAlternatif(Motif motif, int rotation, int miroir) throws Exception {
        rotation = Math.abs(rotation % 360);

        // Gestion des erreurs de rotation
        if(rotation != 0 && rotation != 90 && rotation != 180 && rotation != 270) {
            throw new Exception("Rotation de " + rotation + " degrés (sens trigonométrique, en valeur absolue) invalide (parmi 0, 90, 180, 270)");
        }

        // Gestion des erreurs de miroir
        if(miroir != 0 && miroir != 1 && miroir != 2) {
            throw new Exception("Miroir selon l'axe " + miroir + " invalide (parmi 0=identité, 1=lignes, 2=colonnes)");
        }

        List<int[]> vivantes = motif.getListeVivantes();
        List<int[]> vivantesBis = new ArrayList<>();
        
        for(int[] coos : vivantes) {
            int[] coosBis = new int[2];
            System.arraycopy(coos, 0, coosBis, 0, 2);

            if(rotation == 90) {
                int tmp = coosBis[0];
                coosBis[0] = -coosBis[1];
                coosBis[1] = tmp;
            }
            else if(rotation == 180) {
                coosBis[0] = -coosBis[0];
                coosBis[1] = -coosBis[1];
            }
            else if(rotation == 270) {
                int tmp = coosBis[0];
                coosBis[0] = coosBis[1];
                coosBis[1] = -tmp;
            }

            if(miroir == 1) {
                coosBis[1] = -coosBis[1];
            }
            else if(miroir == 2) {
                coosBis[0] = -coosBis[0];
            }

            vivantesBis.add(coosBis);
        }

        return new Motif(motif.getNom(), motif.getPeriodicite(), vivantesBis, rotation, miroir);
    }

    public static void initialiserMotifs(String cheminDossier) throws Exception {
        File directoryPath = new File(cheminDossier);

        File filesList[] = directoryPath.listFiles();

        for(File file : filesList) {
            String nomMotif = file.getName().substring(0, file.getName().lastIndexOf("."));

            Motif motif = creerMotif(nomMotif, cheminDossier+file.getName());
            listeMotifs.add(motif);

            // Création des motifs alternatifs (sans doublon)
            for (int rotation = 0; rotation <= 270; rotation += 90) {
                for (int miroir = 0; miroir <= 2; miroir++) {
                    Motif motif_alt = creerMotifAlternatif(motif, rotation, miroir);
                    if(!motif.equals(motif_alt) && !listeMotifs.contains(motif_alt)) listeMotifs.add(motif_alt);
                }
            }
        }
    }

    public static int getPeriodiciteMax() {
        int maxMotifs = listeMotifs.stream()
            .mapToInt(motif -> motif.getListeVivantes().size())
            .max()
            .orElse(20);
        return Math.max(maxMotifs, 20);
    }
}