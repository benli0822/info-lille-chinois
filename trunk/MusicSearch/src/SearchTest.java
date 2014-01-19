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
	
	//a list to save the orignal text
	ArrayList<String> text_list;
	
	//a variable to decide wherther in debug mode 
	boolean debug;
	
	public SearchTest(){
		this.motif_list = new ArrayList<String>();
		this.text_list = new ArrayList<String>();
		
		debug = false;
		
	}
	public void ReadDataFromTxt() throws IOException {
		// read motif.txt
		BufferedReader br = new BufferedReader(new InputStreamReader(
				getClass().getClassLoader().getResourceAsStream(
			            "motif.txt")));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			String everything = sb.toString();
			// Save all the words dans the list
			//String[] resultat = everything.split("\\s|[\\s]|[^\\S]");
			String[] resultat = everything.split(" ");
			for (String s : resultat) {

				this.motif_list.add(s);
				// System.out.println(s);

			}
		} finally {
			br.close();
		}
		// read text.txt
		//br = new BufferedReader(new InputStreamReader(new FileInputStream(
		//		"data/negative.txt"), "Latin1"));
		br = new BufferedReader(new InputStreamReader(
				getClass().getClassLoader().getResourceAsStream(
			            "text.txt")));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			String everything = sb.toString();
			// Save all the words in the list
			String[] resultat = everything.split(" ");
			for (String s : resultat) {
				// System.out.println(s);
				this.text_list.add(s);

			}
			if ( this.text_list.size() <= 2) {
				System.out
						.println("Read database error");
			}
		} finally {
			br.close();
		}

	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SearchTest test = new SearchTest();
		test.ReadDataFromTxt();
		
		if(test.debug){
		for(String s : test.text_list){
			System.out.println(s);

		}
	
		System.out.println("test total size: "+test.text_list.size());
		}
		
		KMP kmp_test = new KMP(test.motif_list,test.text_list);
		kmp_test.KMPMatcher();
	}

}
