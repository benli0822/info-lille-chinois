package simulateur;

import java.util.ArrayList;

/**
 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening main test class of
 *         simulateur
 */
public class SimulateurMain {

	ArrayList<String> list_Symbole;
	ArrayList<String> list_nucleque;

	/**
	 * nb nucleotide total
	 */
	private int nb_nucleotide;

	/*
	 * nb appariements : au moin 24 appariements
	 */
	private int nb_appariements;
	Nucleique nucleique_parser;

	public SimulateurMain() {
		super();
		this.list_nucleque = new ArrayList<String>();
		this.list_Symbole = new ArrayList<String>();
		nucleique_parser = new Nucleique();
		this.nb_nucleotide = 0;

	}

	public int getRandomNumber(int max, int min) {

		return (int) Math.round(Math.random() * (max - min) + min);
	}

	public void printARN() {
		System.out.println(this.list_nucleque.toString());
		System.out.println(this.list_Symbole.toString());
	}

	public void generateBoucleTerminale() {
		/**
		 * nb de boucle terminale au plus 8
		 */
		int nb_nucleotide_temp = getRandomNumber(8, 1);
		this.nb_nucleotide += nb_nucleotide_temp;

		for (int i = 0; i < nb_nucleotide_temp; i++) {
			this.list_nucleque.add(nucleique_parser
					.getNucleiqueNom(getRandomNumber(4, 1)));
			this.list_Symbole.add(".");
		}
	}

	public void generateAppariements() {

		/**
		 * generater parathese ouvert
		 */

		/**
		 * au moins 24 groupe de nuclieoide dont pour un groupe de nucleotide il
		 * y a au moins trois nucleotide successifs.
		 * 
		 */
		int nb_nucleotide_reste = 100 - this.nb_nucleotide;
		int nb_max_appratiement = nb_nucleotide_reste / 2;
		int nb_appratiement = getRandomNumber(nb_max_appratiement, 24);
		int nb_max_point = nb_nucleotide_reste - nb_appratiement * 2;
		int nb_point_rest = nb_max_point;
		int nb_appratiement_gauche_reste = nb_appratiement;
		int nb_appratiement_droit_reste = nb_appratiement;
		System.out.println("nb_parathese_gauche : "
				+ nb_appratiement_gauche_reste);

		while (this.nb_nucleotide <= 100) {

			// la premier avant le boucle terminale ne doit pas etre point donc
			// c'est un cas particuliere
			// generate the premiere avant boucle termianle
			/*
			 * int nb_parathese_ouvert = getRandomNumber(5, 3);
			 * 
			 * for (int i = 1; i < nb_parathese_ouvert; i++) {
			 * this.list_Symbole.add(0, "("); this.nb_nucleotide++;
			 * nb_appratiement_gauche_reste--; } int
			 * nb_premier_paranthese_ouvert = getRandomNumber(5, 3);
			 */

			while (nb_appratiement_gauche_reste > 0) {
				// get the nb_parathese_ouvert,minimal succesifs : 3, maximal 10

				int nb_parathese_ouvert = getRandomNumber(5, 3);

				for (int i = 1; i < nb_parathese_ouvert; i++) {
					if (this.nb_nucleotide > 100) {
						return;
					}
					this.list_Symbole.add(0, "(");
					this.nb_nucleotide++;
					nb_appratiement_gauche_reste--;
				}

				int nb_point = getRandomNumber(3, 0);
				for (int i = 1; i < nb_point; i++) {
					if (nb_point_rest < 0) {
						break;
					}
					this.list_Symbole.add(0, ".");
					nb_point_rest--;
					this.nb_nucleotide++;
				}

				nb_parathese_ouvert = getRandomNumber(5, 3);

				for (int i = 1; i < nb_parathese_ouvert; i++) {
					if (this.nb_nucleotide > 100) {
						return;
					}
					this.list_Symbole.add(0, "(");
					this.nb_nucleotide++;
					nb_appratiement_gauche_reste--;
				}
			}

			/*
			 * for (int i = 1; i < nb_premier_paranthese_ouvert; i++) { if
			 * (this.nb_nucleotide > 100) { break; } this.list_Symbole.add(0,
			 * "("); this.nb_nucleotide++; nb_appratiement_gauche_reste--; }
			 */

			/*
			 * //generate la partie droit
			 * 
			 * int nb_premier_paranthese_ferme = getRandomNumber(5, 3); int
			 * nb_dernier_paranthese_ferme = getRandomNumber(5, 3);
			 * 
			 * for (int i = 1; i < nb_premier_paranthese_ferme ; i++) { if
			 * (this.nb_nucleotide > 100) { break; }
			 * this.list_Symbole.add(this.nb_nucleotide, ")");
			 * this.nb_nucleotide++; nb_appratiement_droit_reste--; }
			 */

			while (nb_appratiement_droit_reste > 0) {
				// get the nb_parathese_ferme,minimal succesifs : 3, maximal 10

				int nb_parathese_ferme = getRandomNumber(5, 3);

				for (int i = 1; i < nb_parathese_ferme; i++) {
					if (this.nb_nucleotide > 100) {
						break;

					}
					this.list_Symbole.add(this.nb_nucleotide, ")");
					this.nb_nucleotide++;
					nb_appratiement_droit_reste--;
				}

				int nb_point = getRandomNumber(3, 0);
				for (int i = 1; i < nb_point; i++) {
					if (nb_point_rest < 0) {
						break;
					}
					this.list_Symbole.add(this.nb_nucleotide, ".");
					nb_point_rest--;
					this.nb_nucleotide++;
				}

				 nb_parathese_ferme = getRandomNumber(5, 3);

				for (int i = 1; i < nb_parathese_ferme; i++) {
					if (this.nb_nucleotide > 100) {
						break;

					}
					this.list_Symbole.add(this.nb_nucleotide, ")");
					this.nb_nucleotide++;
					nb_appratiement_droit_reste--;
				}
			}

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SimulateurMain test = new SimulateurMain();
		test.generateBoucleTerminale();
		test.generateAppariements();
		test.printARN();

	}

}
