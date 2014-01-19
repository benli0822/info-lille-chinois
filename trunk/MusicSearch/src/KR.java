import java.util.ArrayList;

public class KR {
	/**
	 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening Karp-Rabin
	 */
	private String text;
	private String pattern;

	public KR(ArrayList<String> motif, ArrayList<String> text) {
		this.text = "";
		for (String line : text) {
			this.text += line;
		}
		this.pattern = "";
		for (String pa : motif) {
			this.pattern += pa;

		}

	}

	public int[] KR_algo() {
		int maxnum = this.text.length() / this.pattern.length();

		int[] result = new int[maxnum];
		
		
		int text_hash = 0, pattern_hash = 0;
		for (int i = 0; i < this.pattern.length(); i++) {
			text_hash = (text_hash << 1) + this.text.charAt(i);
			pattern_hash = (pattern_hash << 1) + this.pattern.charAt(i);
		}

		int result_nb = 0;

		for (int i = 0; i < this.text.length() - this.pattern.length(); i++) {
			if (text_hash == pattern_hash) {
				boolean flag = true;

				for (int j = 0; j < this.pattern.length(); j++) {

					if ((int) (this.text.charAt(i + j)) != (int) (this.pattern
							.charAt(j))) {
						flag = false;
						break;
					}
				}
				if (flag == true)
					result_nb++;
					result[result_nb] = i;

			}
			text_hash = ((text_hash - (this.text.charAt(i) << (this.pattern
					.length() - 1))) << 1)
					+ this.text.charAt(i + this.pattern.length());
		}

		result[0] = result_nb;
		return result;
	}

	public void KRMatcher() {
		int[] result = this.KR_algo();
		System.out.println("-------------------------------------");
		System.out.println("KR result:");
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
