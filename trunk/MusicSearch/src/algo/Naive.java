package algo;
import java.util.ArrayList;

public class Naive {
	/**
	 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening methode naive
	 */
	private String text;
	private String pattern;

	public Naive(ArrayList<String> motif, ArrayList<String> text) {
		this.text = "";
		for (String line : text) {
			this.text += line;
		}

		if (SearchTest.debug == true) {

		}
		this.pattern = "";
		for (String pa : motif) {
			pa.trim();
			this.pattern += pa;

		}

		
	}

	public boolean isEqual(int index) {
		for (int i = 0; i < this.pattern.length(); i++) {
			if ((int) this.text.charAt(i + index) != (int) this.pattern
					.charAt(i))
				return false;
		}
		return true;
	}

	public int[] Naive_algo() {
		int maxnum = this.text.length() / this.pattern.length();

		int[] result = new int[maxnum];
		
		if (SearchTest.debug == true) {
			System.out.println("max result "+maxnum);
			
		}
		
		result[0] = 0;

		for (int i = 0; i < this.text.length() - this.pattern.length(); i++) {

			if (isEqual(i) == true) {
				result[0]++;
				result[result[0]] = i;
			}
		}

		return result;
	}

	public void NaiveMatcher() {
		int[] result = this.Naive_algo();
		System.out.println("-------------------------------------");
		System.out.println("Naive result:");
		if (result[0] > 0) {
			System.out.println("Total match times " + result[0]);
			for (int i = 1; i < result[0] + 1; i++) {
				System.out.println("position of the " + i + "eme match : "
						+ result[i]);
			}
		} else {

			System.out.println("There is no matcher!");

		}

	}
}
