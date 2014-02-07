package simulateur;

public class Nucleique {
	public int id;

	public String getNucleiqueNom(int id) {
		switch (id) {
		case 1:
			return "A";
		case 2:
			return "U";
		case 3:
			return "G";
		case 4:
			return "C";
		default: // situation error
			return "E";
		}
	}

	public int getComplementaireID(int id) {
		switch (id) {
		case 1:
			return 2;
		case 2:
			return 1;
		case 3:
			return 4;
		case 4:
			return 3;
		default: // situation error
			return -1;
		}
	}

}
