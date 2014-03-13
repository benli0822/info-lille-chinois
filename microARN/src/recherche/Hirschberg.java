package recherche;

/**
 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening
 * 
 *         Find the longest common character using Hirschberg algorithm
 */
public class Hirschberg
{
	/**
	 * Function of getting the longest common charcter
	 * 
	 * @param m
	 * @param n
	 * @param a
	 * @param b
	 * @return
	 */
	public String getLCS(int m, int n, String a, String b)
	{
		String lcs = "";

		if (n == 0)
		{
			lcs = "-"; // no match then replace with -
		}
		else if (m == 1)
		{
			for (int j = 0; j < n; j++)
			{
				if (a.charAt(0) == b.charAt(j))
				{
					lcs = "-" + a.charAt(0); // match
					break;
				}
				else
				{
					lcs = "-"; // no match then replace with -
				}
			}
		}
		else
		{
			// Divide and conquer function part
			int i = m / 2;
			final int[] l1 = populateRow(i, n, a.substring(0, i), b);
			final int[] l2 = populateRow(m - i, n,
					reverseString(a.substring(i)), reverseString(b));
			final int k = findR(l1, l2, n);
			final String c1 = getLCS(i, k, a.substring(0, i), b.substring(0, k));
			final String c2 = getLCS(m - i, n - k, a.substring(i),
					b.substring(k));
			lcs = c1 + c2;
		}
		return lcs;
	}

	/**
	 * update matrix information with Divide and conquer
	 * 
	 * @param m
	 * @param n
	 * @param a
	 * @param b
	 * @return
	 */
	private int[] populateRow(int m, int n, String a, String b)
	{

		final int[][] row = new int[2][n + 1];
		for (int j = 0; j <= n; j++)
		{
			row[1][j] = 0;
		}

		for (int i = 1; i <= m; i++)
		{
			for (int j = 0; j <= n; j++)
			{
				row[0][j] = row[1][j];
			}

			for (int j = 1; j <= n; j++)
			{
				if (a.charAt(i - 1) == b.charAt(j - 1))
				{
					row[1][j] = row[0][j - 1] + 1;
				}
				else
				{
					row[1][j] = max(row[1][j - 1], row[0][j]);
				}
			}
		}

		return row[1];

	}

	/**
	 * Max function
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private int max(int x, int y)
	{
		return (x > y) ? x : y;
	}

	/**
	 * Reverse the given String
	 * 
	 * @param source
	 * @return
	 */
	private static String reverseString(String source)
	{
		int len = source.length();
		StringBuffer dest = new StringBuffer(len);
		for (int i = (len - 1); i >= 0; i--)
			dest.append(source.charAt(i));
		return dest.toString();
	}

	/**
	 * Find the next reference point in Divide and conquer
	 * 
	 * @param l1
	 * @param l2
	 * @param n
	 * @return
	 */
	private int findR(int[] l1, int[] l2, int n)
	{
		int m = 0;
		int r = 0;
		for (int j = 0; j <= n; j++)
		{
			if (m < (l1[j] + l2[n - j]))
			{
				m = l1[j] + l2[n - j];
				r = j;
			}
		}
		return r;
	}

	public static void main(String[] args)
	{
		String seqB = "UUAAAAUAUAUUUUCAAUCCUCAUUGGCUGAAUUUGUGGAUAGGGAACCCGCAGAUAUG";
		String seqA = "GGGAAACCCUUCCGGGUGUGUGCUUGGUGGGGGUGGGGGUGGGGGUGGGGGUGGGGGUG";

		Hirschberg h = new Hirschberg();
		String lcs = h.getLCS(seqA.length(), seqB.length(), seqA, seqB);
		System.out.println(lcs);

	}

}
