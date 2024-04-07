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
			if (voisins.get(i).getEnVie())
				s += 1;
		}
		return s;
	}

	public void evoluer() {
		setEnVie(getProchainEtat());
	}

	public void determinerProchainEtatClassique() {
		int voisinsVivants = voisinsVivants();
		if (enVie) {
			if (voisinsVivants < 2 || voisinsVivants > 3) {
				setProchainEtat(false); // Mort par solitude ou surpopulation
			} else {
				setProchainEtat(true); // Survie
			}
		} else {
			if (voisinsVivants == 3) {
				setProchainEtat(true); // Naissance
			}
		}
	}

	public void determinerProchainEtatHighlife() {
		int voisinsVivants = voisinsVivants();
		if (enVie) {
			if (voisinsVivants < 2 || voisinsVivants > 3) {
				setProchainEtat(false);// Mort par solitude ou surpopulation
			} else {
				setProchainEtat(true); // Survie
			}
		} else {
			if (voisinsVivants == 3 || voisinsVivants == 6) {
				setProchainEtat(true); // Naissance
			}
		}
	}

	public void determinerProchainEtatReplicator() {
		int voisinsVivants = voisinsVivants();
		if (enVie) {
			if (voisinsVivants == 1 || voisinsVivants == 3 || voisinsVivants == 5 || voisinsVivants == 7) {
				setProchainEtat(true); // Survie
			} else {
				setProchainEtat(false); // Mort
			}
		} else {
			if (voisinsVivants == 1 || voisinsVivants == 3 || voisinsVivants == 5 || voisinsVivants == 7) {
				setProchainEtat(true); // Naissance
			}
		}
	}

	public void determinerProchainEtatDayAndNight() {
		int voisinsVivants = voisinsVivants();
		if (enVie) {
			if (voisinsVivants == 3 || voisinsVivants == 4 || voisinsVivants == 6 || voisinsVivants == 7) {
				setProchainEtat(true); // Survie
			} else {
				setProchainEtat(false); // Mort
			}
		} else {
			if (voisinsVivants == 3 || voisinsVivants == 6 || voisinsVivants == 7 || voisinsVivants == 8) {
				setProchainEtat(true); // Naissance
			}
		}
	}

	public void determinerProchainEtatLifeWithoutDeath() {
		int voisinsVivants = voisinsVivants();
		if (voisinsVivants == 3 || enVie) {
			setProchainEtat(true);
		} else {
			setProchainEtat(false);
		}
	}

	public void determinerProchainEtatAmoeba() {
		int voisinsVivants = voisinsVivants();
		if (enVie) {
			if (voisinsVivants == 1 || voisinsVivants == 3 || voisinsVivants == 5 || voisinsVivants == 8
					|| voisinsVivants == 9) {
				setProchainEtat(true); // Survie
			} else {
				setProchainEtat(false); // Mort
			}
		} else {
			if (voisinsVivants == 3 || voisinsVivants == 5 || voisinsVivants == 6 || voisinsVivants == 7
					|| voisinsVivants == 8) {
				setProchainEtat(true); // Naissance
			}
		}
	}

	/*public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		}

		Cellule cellule = (Cellule) o;
		return this.getEnVie() == cellule.getEnVie() && this.position[0] == cellule.position[0] && this.position[1] == cellule.position[1];
	}

	public int hashCode() {
		if (getEnVie()) {
			return 5;
		} else {
			return 3;
		}
	}*/

}
