package recherche;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening Find all Palindrome in
 *         gemomics sequences
 */
public class Palindrome {

	private String res; // Resource sequence
	private ArrayList<String> lib; // Palindrome words
	private int n; // Length of res
	int memo[][]; // Matrix of Palindrome

	public Palindrome(String res) {
		this.res = res;
		this.n = res.length();
		memo = new int[n][n];
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				memo[i][j] = -1;
			}
		}
		// all word is the Palindrome of itself
		for (int i = 0; i < this.n; i++) {
			memo[i][i] = 1;
		}
		find(0, this.n - 1);
	}

	/**
	 * Check for Palindrome for substring of resource
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean check(int i, int j) {
		String toFind = res.substring(i, j + 1);
		char[] g = toFind.toCharArray();
		int length = g.length;
		if (length == 2) {
			switch (g[0]) {
			case 'A':
				if (g[1] != 'U')
					return false;
				break;
			case 'C':
				if (g[1] != 'G')
					return false;
				break;
			case 'G':
				if (g[1] != 'U')
					return false;
				break;
			}
		} else {
			for (int count = 0; count < length / 2; count++) {
				switch (g[count]) {
				case 'A':
					if (g[length - count - 1] != 'U')
						return false;
					break;
				case 'C':
					if (g[length - count - 1] != 'G')
						return false;
					break;
				case 'G':
					if (g[length - count - 1] != 'U')
						return false;
					break;
				}
			}
		}
		return true;
	}

	/**
	 * Iterator for checking the substring using dynamic programming with matrix
	 * of palindrome
	 * 
	 * @param i
	 * @param j
	 */
	public void find(int i, int j) {
		if (i <= j) {
			if (memo[i][j] == -1)
				if (check(i, j)) {
					memo[i][j] = j - i + 1;
				} else {
					memo[i][j] = 0;
				}
			if (i + 1 < this.n && j - 1 >= 0) {
				find(i + 1, j); // left tree
				find(i, j - 1); // right tree
			}
		}
	}

	/**
	 * Generate Palindrome words library from Palindrome matrix
	 */
	public void generateLib() {
		this.lib = new ArrayList<String>();
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++)
				if (this.memo[i][j] > 0)
					this.lib.add(res.substring(i, j + 1));
		}
	}

	/**
	 * Return the Palindrome words libray
	 * 
	 * @return
	 */
	public ArrayList<String> getLib() {
		return lib;
	}

	/**
	 * Print the Palindrome matrix
	 * 
	 * @param memo
	 * @return
	 */
	public static String memoToString(int[][] memo) {
		String result = "";
		for (int i = 0; i < memo.length; ++i) {
			for (int j = 0; j < memo[i].length; ++j) {
				if (memo[i][j] == -1 || memo[i][j] >= 10)
					result += memo[i][j] + "  ";
				else
					result += memo[i][j] + "   ";
			}
			result += "\n";
		}

		return result;
	}

	/**
	 * Get the Palindrome matrix
	 * 
	 * @return
	 */
	public int[][] getMemo() {
		return memo;
	}

	public static void main(String[] args) {
		Palindrome p = new Palindrome("AGGGACUAUGG");
		int[][] a = p.getMemo();
		System.out.println(memoToString(a));
		p.generateLib();
		List<String> l = p.getLib();
		for (String s : l) {
			System.out.println(s);
		}
	}

}
