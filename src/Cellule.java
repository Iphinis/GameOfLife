import java.util.List;
import java.util.ArrayList;

class Cellule {
	public boolean enVie = false;

	public List<Cellule> voisins = new ArrayList<Cellule>();

	private int voisinsVivants() {
		int s = 0;
		int n = voisins.size();
		for(int i=0; i < n; i++) {
			if(voisins.get(i).enVie) s += 1;
		}
		return s;
	}

	public void evoluer() {
		int v = voisinsVivants();

		switch(v) {
			case 3:
				enVie = true;
				break;
			case 2:
				break;
			default:
				enVie = false;
				break;
		}
	}
}
