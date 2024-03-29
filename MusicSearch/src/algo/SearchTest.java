package algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening
 * 
 */
public class SearchTest {

	// a list to save the motif
	ArrayList<String> motif_list;

	// a list to save the orignal text
	ArrayList<String> text_list;

	// a list to save different frequence motif list which will be generate from
	// generateDifferentFrequenceAlgo(int level)
	ArrayList<ArrayList<String>> differentFrequenceMotifList;

	// a list to save different duree motif list
	ArrayList<ArrayList<String>> differentDureeMotifList;

	// a list to save all of the motif(except the orginal list) which have been
	// changed the frequence and duree.
	// e.x if the original motif is A12 C2 E2 G2 B8
	// in this list you can find the lists like B12 D2 F2 A8, B6 D1 F1 A4 or B6
	// D1 F1 A4 or the other changed motif
	ArrayList<ArrayList<String>> differentFrequenceAndDuree_Motif_List;

	// How many times we can divide for this motif
	int motif_duree_down;
	// How many times we can multiple for this motif
	int motif_duree_up;

	// a variable to decide wherther in debug mode
	public static boolean debug;

	private String motif_filename;

	private String texte_filename;

	public SearchTest(String motif_filename, String texte_filename) {
		this.motif_list = new ArrayList<String>();
		this.text_list = new ArrayList<String>();
		this.differentDureeMotifList = new ArrayList<ArrayList<String>>();
		this.differentFrequenceMotifList = new ArrayList<ArrayList<String>>();
		this.differentFrequenceAndDuree_Motif_List = new ArrayList<ArrayList<String>>();
		this.motif_filename = motif_filename;
		this.texte_filename = texte_filename;
		debug = false;

	}

	public void ReadDataFromTxt() throws IOException {
		// read motif.txt
		FileReader in_motif = new FileReader(this.motif_filename);
		BufferedReader br = new BufferedReader(in_motif);
		//BufferedReader br = new BufferedReader(new InputStreamReader(getClass()
			//	.getClassLoader().getResourceAsStream("1-motif")));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				// sb.append('\n');
				line = br.readLine();
			}
			String everything = sb.toString();
			// Save all the words in the list

			String[] resultat = everything.split(" ");
			for (String s : resultat) {

				this.motif_list.add(s);
				// System.out.println(s);

			}
		} finally {
			br.close();
			in_motif.close();
		}
		FileReader in_texte = new FileReader(this.texte_filename);
		br = new BufferedReader(in_texte);
		
		//br = new BufferedReader(new InputStreamReader(getClass()
			//	.getClassLoader().getResourceAsStream("1-texte")));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				// sb.append('\n');
				line = br.readLine();
			}
			String everything = sb.toString();
			// Save all the words in the list
			String[] resultat = everything.split(" ");
			for (String s : resultat) {
				// System.out.println(s);
				this.text_list.add(s);

			}
			if (this.text_list.size() <= 2) {
				System.out.println("Read database error");
			}
		} finally {
			br.close();
			in_texte.close();
		}
	}

	// a methode to dicider how many times the duree can be divided

	public int getMotifDureeDown() {
		int minDuree = 1000;
		for (String line : this.motif_list) {
			char dureeatmotif = line.charAt(1);
			int duree = Character.getNumericValue(dureeatmotif);
			if (duree <= minDuree) {
				minDuree = duree;
			}
		}
		if (minDuree <= 0) {
			return -1;
		} else {
			minDuree = (int) (Math.log(minDuree) / Math.log(2));
		}
		return minDuree;
	}

	// a methode to dicider how many times the duree can be do the
	// multiplication
	// e.x. if we have A2 B16 C16 we can only multiplicate 1 times because 16 *
	// 2 = 32, is the biggest duree
	// so we need to use this algo to know the max duree in the motif.

	public int getMotifDureeUp() {
		int maxDuree = 1;
		for (String line : this.motif_list) {
			char dureeatmotif = line.charAt(1);
			int duree = Character.getNumericValue(dureeatmotif);
			if (duree >= maxDuree) {
				maxDuree = duree;
			}
		}
		switch (maxDuree) {
		case 1:
			return 5;
		case 2:
			return 4;
		case 4:
			return 3;
		case 8:
			return 2;
		case 16:
			return 1;
		default:
			return 0;
		}

	}

	public ArrayList<String> generateDownDureeAlgo(int duree,
			ArrayList<String> motifList) {
		ArrayList<String> newList = new ArrayList<String>();
		int duree_old, duree_new;
		for (String s : motifList) {
			duree_old = Character.getNumericValue(s.charAt(1));
			duree_new = duree_old / duree;

			char ton = s.charAt(0);
			String newTon = "" + ton + Integer.toString(duree_new);
			newList.add(newTon);

		}
		return newList;
	}

	public ArrayList<String> generateUpDureeAlgo(int duree,
			ArrayList<String> motifList) {
		ArrayList<String> newList = new ArrayList<String>();
		int duree_old, duree_new;
		for (String s : motifList) {
			duree_old = Character.getNumericValue(s.charAt(1));
			duree_new = duree_old * duree;

			char ton = s.charAt(0);
			String newTon = "" + ton + Integer.toString(duree_new);
			newList.add(newTon);

		}
		return newList;
	}

	/**
	 * change motif for given frequence
	 * 
	 * @param frequence
	 *            change the frequence of melody
	 */
	public ArrayList<String> generateDifferentFrequenceAlgo(int level) {
		/* construction of ton libray */
		ArrayList<Character> database = new ArrayList<Character>();
		for (char letter = 'A'; letter < 'H'; letter++) {
			database.add(letter);
		}
		// database.add('r');

		/*
		 * for each element of motif list, get the first char of element, add
		 * the level with index to cycle in the database, ex. ABCD BCDF
		 */
		//int i = 0;
		ArrayList<String> newlist = new ArrayList<String>();
		for (String s : this.motif_list) {
			char ton = s.charAt(0);
			if (database.contains(ton)) {
				int index = database.indexOf(ton);
				char newTon = 0;
				if (ton != 'r') {
					newTon = database.get((index + level) % (database.size()));
				} else {
					if (ton == 'r') {
						newTon = 'r';
					}
				}

				String newS = "" + newTon + s.charAt(1);
				// this.motif_list.set(i, newS);
				newlist.add(newS);
			} else {
				/* if the format can't be recognized, we treat it as a exception */
				throw new IllegalArgumentException("unkown melody format at"
						+ s);
			}
			//i++;
		}
		return newlist;
	}

	public void setNewFrequenceList() {

		for (int i = 1; i < 7; i++) {
			this.differentFrequenceMotifList.add(this
					.generateDifferentFrequenceAlgo(i));
		}
	}

	public void setNewDureeList() {

		this.motif_duree_down = this.getMotifDureeDown();
		this.motif_duree_up = this.getMotifDureeUp();

		// firstly set duree down for the original motif
		for (int i = this.motif_duree_down, j = 2; i > 0; i--, j = j * 2) {

			this.differentDureeMotifList.add(this.generateDownDureeAlgo(j,
					this.motif_list));

		}
		// then set duree up for the original motif
		for (int i = this.motif_duree_up, j = 2; i > 0; i--, j = j * 2) {

			this.differentDureeMotifList.add(this.generateUpDureeAlgo(j,
					this.motif_list));

		}

	}

	public void setNewFrequenceAndDureeList() {

		for (ArrayList<String> duree : this.differentDureeMotifList) {
			this.differentFrequenceAndDuree_Motif_List.add(duree);

		}
		for (ArrayList<String> frequence : this.differentFrequenceMotifList) {
			this.differentFrequenceAndDuree_Motif_List.add(frequence);

		}

		// change the duree for all new frequence list
		for (ArrayList<String> frequence_new : this.differentFrequenceMotifList) {

			for (int i = this.motif_duree_down, j = 2; i > 0; i--, j = j * 2) {
				this.differentFrequenceAndDuree_Motif_List.add(this
						.generateDownDureeAlgo(j, frequence_new));

			}

			for (int i = this.motif_duree_up, j = 2; i > 0; i--, j = j * 2) {
				this.differentFrequenceAndDuree_Motif_List.add(this
						.generateUpDureeAlgo(j, frequence_new));
			}

		}

	}

	public void prepareForMotifList() {
		this.setNewFrequenceList();
		this.setNewDureeList();
		this.setNewFrequenceAndDureeList();
		if (debug) {
			for (ArrayList<String> list : this.differentFrequenceAndDuree_Motif_List)
				System.out.println("changed motif total nb : "
						+ list.toString());
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		try {

			if (args.length != 2) {
				// input error
				System.out.println("args input error");
				System.out.println("args[0] not find");
				System.out
						.println("Please try to add a argument after commande");
				System.out
						.println("Commande exemple : java -jar MusicSearch.jar 1-motif 1-texte");
			} else {
				SearchTest test = new SearchTest(args[0], args[1]);
				test.ReadDataFromTxt();
				test.prepareForMotifList();
				//if (test.debug) {
					// System.out.println("Min duree: " +
					// test.getMotifDureeDown());
					// System.out.println("Max duree: " +
					// test.getMotifDureeUp());
					// System.out.print("at the last position:"+test.motif_list.get(test.motif_list.size()-1));
					// System.out.println("motif size: " +
					// test.motif_list.size());
					// System.out.println("texte size: " +
					// test.text_list.size());

				//}
				System.out
						.println("Rechercher la motif orignal ----------------------------");
				Naive naive_test = new Naive(test.motif_list, test.text_list);
				long startTime = System.nanoTime();
				naive_test.NaiveMatcher();
				long endTime = System.nanoTime();
				System.out.println("Naive time " + (endTime - startTime)
						+ " ns");

				/*
				 * ShiftOr shiftOr_test = new ShiftOr(test.motif_list,
				 * test.text_list); startTime = System.nanoTime();
				 * shiftOr_test.ShiftOrMatcher(); endTime = System.nanoTime();
				 * System.out.println("shiftOr time " + (endTime - startTime) +
				 * "ns");
				 */
				KR kr_test = new KR(test.motif_list, test.text_list);
				startTime = System.nanoTime();
				kr_test.KRMatcher();
				endTime = System.nanoTime();
				System.out.println("KR time: " + (endTime - startTime) + " ns");

				BM bm_test = new BM(test.motif_list, test.text_list);
				startTime = System.nanoTime();
				bm_test.BMMatcher();
				endTime = System.nanoTime();
				System.out.println("BM time: " + (endTime - startTime) + " ns");

				KMP kmp_test = new KMP(test.motif_list, test.text_list);
				startTime = System.nanoTime();

				kmp_test.KMPMatcher();
				endTime = System.nanoTime();
				System.out
						.println("KMP time: " + (endTime - startTime) + " ns");
				System.out
						.println("Fin de la rechercher la motif orignal ----------------------------");
				System.out.println("");
				System.out.println("");

				// search for the changed motifs in the list of
				// defferentFreauenceAndDuree_Motif
				System.out
						.println(" Rechercher la motif equivalente ----------------------------");
				startTime = System.nanoTime();
				for (ArrayList<String> changedmotif : test.differentFrequenceAndDuree_Motif_List) {
					Naive naive_test_1 = new Naive(changedmotif, test.text_list);

					naive_test_1.NaiveMatcher_changed();

				}
				endTime = System.nanoTime();
				System.out.println("Naive time " + (endTime - startTime)
						+ " ns");

				startTime = System.nanoTime();
				for (ArrayList<String> changedmotif : test.differentFrequenceAndDuree_Motif_List) {
					BM BM_test_1 = new BM(changedmotif, test.text_list);
					BM_test_1.BMMatcher_changed();

				}
				endTime = System.nanoTime();
				System.out.println("BM time: " + (endTime - startTime) + " ns");

				System.out
						.println(" Fin de la rechercher la motif equivalente ----------------------------");
			}

		} catch (Exception t) {
			t.printStackTrace();
		}
	}

}
