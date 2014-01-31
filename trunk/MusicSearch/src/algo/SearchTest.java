package algo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	// How many times we can divide for this motif
	int motif_duree;
	
	// a variable to decide wherther in debug mode
	public static boolean debug;

	public SearchTest() {
		this.motif_list = new ArrayList<String>();
		this.text_list = new ArrayList<String>();
		
		debug = true;

	}
	
	public void ReadDataFromTxt() throws IOException {
		// read motif.txt
		BufferedReader br = new BufferedReader(new InputStreamReader(getClass()
				.getClassLoader().getResourceAsStream("2-motif1")));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				//sb.append('\n');
				line = br.readLine();
			}
			String everything = sb.toString();
			// Save all the words in the list
			// String[] resultat = everything.split("\\s|[\\s]|[^\\S]");
			String[] resultat = everything.split(" ");
			for (String s : resultat) {

				this.motif_list.add(s);
				// System.out.println(s);

			}
		} finally {
			br.close();
		}
		// read text.txt
		// br = new BufferedReader(new InputStreamReader(new FileInputStream(
		// "data/negative.txt"), "Latin1"));
		br = new BufferedReader(new InputStreamReader(getClass()
				.getClassLoader().getResourceAsStream("2-texte70000")));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				//sb.append('\n');
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
		}
	}
	public ArrayList<String> getDureeList(int duree){
		ArrayList<String> newList = new ArrayList<String>();
		int duree_old, duree_new;
		for(String s : this.motif_list){
			duree_old = Character.getNumericValue(s.charAt(1));
			duree_new = duree_old / 2;
			
		}
		return newList;
	}
	/**
	 * change motif for given frequence
	 * @param frequence
	 *            change the frequence of melody
	 */
	public ArrayList<String> setMotifList(int level) {
		/* construction of ton libray */
		ArrayList<Character> database = new ArrayList<Character>();
		for (char letter = 'A'; letter < 'H'; letter++) {
			database.add(letter);
		}
		database.add('r');
		
		/* for each element of motif list, get the first char of element, 
		 * add the level with index to cycle in the database,
		 * ex. ABCD BCDF
		 */
		int i = 0;
		for (String s : this.motif_list) {
			char ton = s.charAt(0);
			if (database.contains(ton)) {
				int index = database.indexOf(ton);
				char newTon = database.get((index + level) % (database.size()));
				String newS = "" + newTon + s.charAt(1);
				this.motif_list.set(i, newS);
			} else {
				/* if the format can't be recognized, we treat it as a exception */
				throw new IllegalArgumentException("unkown melody format at"
						+ s);
			}
			i++;
		}
		return this.motif_list;
	}
	
	public int getMotifDuree(){
		int minDuree = 1000;
		for(String line: this.motif_list){
			char dureeatmotif = line.charAt(1);
			int duree = Character.getNumericValue(dureeatmotif);
			if(duree <= minDuree){
				minDuree = duree;
			}
		}
		if(minDuree <= 0){
			return -1;
		}
		else{
			minDuree =  (int) (Math.log(minDuree)/Math.log(2));
		}
		return minDuree;
	}
	/*public ArrayList<String> changeHauteur(){
		
	}*/
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SearchTest test = new SearchTest();
		test.ReadDataFromTxt();
		
		

		
		
		if (test.debug) {
			System.out.println("Min duree: "+test.getMotifDuree());
			//System.out.print("at the last position:"+test.motif_list.get(test.motif_list.size()-1));
			System.out.println("motif size: "+test.motif_list.size());
			System.out.println("texte size: "+test.text_list.size());
		}

		Naive naive_test = new Naive(test.motif_list, test.text_list);
		long startTime = System.nanoTime();
		naive_test.NaiveMatcher();
		long endTime = System.nanoTime();
		System.out.println("Naive time " + (endTime - startTime) + " ns");

		ShiftOr shiftOr_test = new ShiftOr(test.motif_list, test.text_list);
		startTime = System.nanoTime();
		shiftOr_test.ShiftOrMatcher();
		endTime = System.nanoTime();
		System.out.println("shiftOr time " + (endTime - startTime) + "ns");

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
		System.out.println("KMP time: " + (endTime - startTime) + " ns");

	}

}
