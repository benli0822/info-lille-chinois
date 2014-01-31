package algo;
import java.util.ArrayList;
/**
 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening
 *	Boyer-Moore
 */
public class BM {
	private String text;
	private String pattern;
	
	public BM(ArrayList<String> motif, ArrayList<String> text){
		this.text = "";
    	for(String line: text){
    		this.text += line;
    	}
    	this.pattern = "";
    	for(String pa : motif){
    		this.pattern += pa;
    		
    	}
    	
	}
	public int[] BadShiftTable() {
		int[] Table = new int[128];

		for (int i = 0; i < Table.length; i++) {
			Table[i] = this.pattern.length();
		}

		for (int i = 0; i < this.pattern.length(); i++) {
			Table[this.pattern.charAt(i)] = this.pattern.length() - 1 - i;
		}

		return Table;
	}

	public int[] BetterShiftTable() {
		int[] Table = new int[this.pattern.length()];
		for (int k = 1; k < this.pattern.length(); k++) {
			int flag1 = 0;// flag whether find another suff(k)
			int flag2 = 0;// flag wherther find longest prefix
			int suff = this.pattern.length() - k;
			int j = this.pattern.length() - k - 1;
			int i = j;
			while (j >= 0) {
				if (this.pattern.charAt(i) == this.pattern.charAt(suff)) {
					i++;
					suff++;
				} else {
					j--;
					suff = this.pattern.length() - k;
					i = j;
					continue;
				}

				if (suff == this.pattern.length()) {
					Table[k] = this.pattern.length() - i;
					flag1 = 1;
					break;
				}
			}
			// another suff(k) not find so try to find the longet prefix
			if (flag1 == 0) {
				for (int m = 1; m < k; m++) {
					suff = this.pattern.length() - k + m;
					i = 0;

					while (i < k - m) {
						if (this.pattern.charAt(i) == this.pattern.charAt(suff)) {
							i++;
							suff++;

						} else {
							i = 0;
							break;
						}
						if (i == k - m) {
							Table[k] = this.pattern.length() - i;
							flag2 = 1;
						}
					}
				}
			}

			if (flag1 == 0 & flag2 == 0) {
				Table[k] = this.pattern.length();
			}
		}
		return Table;
	}

	public int d(int k, int bc, int d2) {
		int d1 = (bc - k) > 1 ? (bc - k) : 1;
		int d = d1 > d2 ? d1 : d2;

		if (k == 0) {
			return d1;
		}
		return d;
	}

	public int[] BM_algo() {
		int maxnum = this.text.length() / this.pattern.length();

		int[] result = new int[maxnum];
		int[] BadShiftTable = this.BadShiftTable();
		int[] BetterShiftTable = this.BetterShiftTable();

		int m = this.pattern.length();
		int n = this.text.length();
		int i = m - 1;
		int result_nb = 0;
		while (i <= n - 1) {
			int k = 0; // match nb
			while (k < m
					&& this.pattern.charAt(m - 1 - k) == this.text
							.charAt(i - k)) {
				k = k + 1;
			}
			if (k == m) {
				result_nb++;
				result[result_nb] = i - m + 1;
				i = i + this.pattern.length();
			} else {
				char c = this.text.charAt(i - k);
				i = i + d(k, BadShiftTable[c], BetterShiftTable[k]);
			}

		}

		result[0] = result_nb;
		return result;
	}

	public void BMMatcher() {
		int[] result = this.BM_algo();
		System.out.println("-------------------------------------");
		System.out.println("BM result:");
		if(result[0]>0){
		System.out.println("Total match times " + result[0]);
		for (int i = 1; i < result[0]+1; i++) {
			System.out.println("position of the " + i + "eme match : " + result[i]);
		}
		}
		else{
			 
		            System.out.println("There is no matcher!"); 
		     
		}
		
	}
}
