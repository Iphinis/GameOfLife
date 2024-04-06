import java.util.List;
import java.util.ArrayList;

class Cellule {

	private boolean enVie;
	private boolean prochainEtat;
	private int[] position;
	private List<Cellule> voisins;

	public Cellule() {
		this.enVie = false;
		this.prochainEtat = false;
		this.voisins = new ArrayList<Cellule>();
	}

	public boolean getEnVie() {
		return enVie;
	}

	public void setEnVie(boolean enVie) {
		this.enVie = enVie;
	}

	public boolean getProchainEtat() {
		return prochainEtat;
	}

	public void setProchainEtat(boolean prochainEtat) {
		this.prochainEtat = prochainEtat;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public int[] getPosition() {
		return position;
	}

	public List<Cellule> getVoisins() {
		return voisins;
	}

	public void addVoisin(Cellule voisin) {
		this.voisins.add(voisin);
	}

	// Renvoie le nombre de voisins vivants
	public int voisinsVivants() {
		int s = 0;
		int n = voisins.size();
		for (int i = 0; i < n; i++) {
			if (voisins.get(i).enVie)
				s += 1;
		}
		return s;
	}

	public void evoluer() {
		enVie = prochainEtat;
	}

	public void determinerProchainEtatClassique() {
		int voisinsVivants = voisinsVivants();
		if (enVie) {
			if (voisinsVivants < 2 || voisinsVivants > 3) {
				prochainEtat = false; // Mort par solitude ou surpopulation
			} else {
				prochainEtat = true; // Survie
			}
		} else {
			if (voisinsVivants == 3) {
				prochainEtat = true; // Naissance
			}
		}
	}

	public void determinerProchainEtatHighlife() {
		int voisinsVivants = voisinsVivants();
		if (enVie) {
			if (voisinsVivants < 2 || voisinsVivants > 3) {
				prochainEtat = false; // Mort par solitude ou surpopulation
			} else {
				prochainEtat = true; // Survie
			}
		} else {
			if (voisinsVivants == 3 || voisinsVivants == 6) {
				prochainEtat = true; // Naissance
			}
		}
	}

	public void determinerProchainEtatReplicator() {
		int voisinsVivants = voisinsVivants();
		if (enVie) {
			if (voisinsVivants == 1 || voisinsVivants == 3 || voisinsVivants == 5 || voisinsVivants == 7) {
				prochainEtat = true; // Survie
			} else {
				prochainEtat = false; // Mort
			}
		} else {
			if (voisinsVivants == 1 || voisinsVivants == 3 || voisinsVivants == 5 || voisinsVivants == 7) {
				prochainEtat = true; // Naissance
			}
		}
	}

	public void determinerProchainEtatDayAndNight() {
		int voisinsVivants = voisinsVivants();
		if (enVie) {
			if (voisinsVivants == 3 || voisinsVivants == 4 || voisinsVivants == 6 || voisinsVivants == 7) {
				prochainEtat = true; // Survie
			} else {
				prochainEtat = false; // Mort
			}
		} else {
			if (voisinsVivants == 3 || voisinsVivants == 6 || voisinsVivants == 7 || voisinsVivants == 8) {
				prochainEtat = true; // Naissance
			}
		}
	}

	public void determinerProchainEtatLifeWithoutDeath() {
		int voisinsVivants = voisinsVivants();
		if (voisinsVivants == 3 || enVie) {
			prochainEtat = true;
		} else {
			prochainEtat = false;
		}
	}

	public void determinerProchainEtatAmoeba() {
		int voisinsVivants = voisinsVivants();
		if (enVie) {
			if (voisinsVivants == 1 || voisinsVivants == 3 || voisinsVivants == 5 || voisinsVivants == 8
					|| voisinsVivants == 9) {
				prochainEtat = true; // Survie
			} else {
				prochainEtat = false; // Mort
			}
		} else {
			if (voisinsVivants == 3 || voisinsVivants == 5 || voisinsVivants == 6 || voisinsVivants == 7
					|| voisinsVivants == 8) {
				prochainEtat = true; // Naissance
			}
		}
	}
	
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		}
	
		Cellule cellule = (Cellule) o;
		return cellule.getEnVie()==this.getEnVie();
	}
	
	public int hashCode() {
		if (getEnVie()) {
    			return 5;
		} else {
    			return 3;
		}
	}

}
