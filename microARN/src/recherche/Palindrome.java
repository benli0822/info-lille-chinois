package recherche;

import java.util.ArrayList;
import java.util.List;

import simulateur.SimulateurMain;

/**
 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening Find all Palindrome in
 *         gemomics sequences
 */
public class Palindrome
{

	private String res; // Resource sequence
	private ArrayList<String> lib; // Palindrome words
	private int n; // Length of res
	boolean check[][];
	boolean value[][];
	boolean find[][];

	public Palindrome(String res)
	{
		this.res = res;
		this.n = res.length();
		check = new boolean[n][n];
		for (int i = 0; i < this.n; i++)
		{
			for (int j = 0; j < this.n; j++)
			{
				check[i][j] = false;
			}
		}
		value = new boolean[n][n];
		for (int i = 0; i < this.n; i++)
		{
			for (int j = 0; j < this.n; j++)
			{
				value[i][j] = false;
			}
		}

		find = new boolean[n][n];
		for (int i = 0; i < this.n; i++)
		{
			for (int j = 0; j < this.n; j++)
			{
				if (i > j)
					find[i][j] = true;
				else
					find[i][j] = false;
			}
		}
		fingAndGenerate(res);
	}

	/**
	 * Check for Palindrome for substring of resource
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean check(String toFind, int i, int j)
	{
		int length = toFind.length();
		System.out.println("Now checking for string: " + toFind + ", length = "
				+ length);

		if (!check[i][j])
		{
			System.out.println("Not yet checked, now begin to check for i = "
					+ i + ", j = " + j);
			check[i][j] = true;
			if (length == 0 || length == 1)
			{
				// if length =0 OR 1 then it is
				value[i][j] = true;
				System.out.println("Success with [" + i + "][" + j + "]");
				return true;
			}

			char beginChar = toFind.charAt(0);
			char endChar = toFind.charAt(length - 1);

			if ((beginChar == 'A' && endChar == 'U')
					|| (beginChar == 'U' && endChar == 'A')
					|| (beginChar == 'C' && endChar == 'G')
					|| (beginChar == 'G' && endChar == 'C')
					|| (beginChar == 'G' && endChar == 'U')
					|| (beginChar == 'U' && endChar == 'G')
					|| (beginChar == endChar))
			{
				// check for first and last char of String:
				// if they are same then do the same thing for a substring
				// with first and last char removed. and carry on this
				// until you string completes or condition fails
				System.out.println("Success with [" + i + "][" + j
						+ "], Entering next section");
				value[i][j] = true;
				return check(toFind.substring(1, length - 1), i + 1, j - 1);
			}

			value[i][j] = false;
			System.out.println("Failure with [" + i + "][" + j + "]");
			// if its not the case than string is not.
			return false;
		}
		else
		{
			System.out.println("Already checked for i = " + i + ", j = " + j);
			if (value[i][j])
			{
				if (length == 0 || length == 1)
				{
					// if length =0 OR 1 then it is
					System.out.println("Success with [" + i + "][" + j + "]");
					return true;
				}
				else
					return check(toFind.substring(1, length - 1), i + 1, j - 1);
			}
			else
			{
				return false;
			}
		}

	}

	public void fingAndGenerate(String f)
	{
		this.lib = new ArrayList<String>();
		for (int i = 0; i < this.n; i++)
		{
			for (int j = 0; j < this.n; j++)
			{
				if (i <= j && !find[i][j])
				{
					String toFind = res.substring(i, j + 1);
					if (check(toFind, i, j))
					{
						if (!this.lib.contains(toFind) && toFind.length() >= 3)
							this.lib.add(toFind);
					}
					find[i][j] = true;
				}
			}
		}

	}

	/**
	 * Return the Palindrome words libray
	 * 
	 * @return
	 */
	public ArrayList<String> getLib()
	{
		return lib;
	}

	public static String memoToString(boolean[][] memo)
	{
		String result = "";
		for (int i = 0; i < memo.length; ++i)
		{
			for (int j = 0; j < memo[i].length; ++j)
			{
				result += memo[i][j] + "  ";
			}
			result += "\n";
		}

		return result;
	}

	public static void main(String[] args)
	{
		long start = System.nanoTime();
		SimulateurMain res = new SimulateurMain();
		res.generateBoucleTerminale();
		res.generateAppariements();
		ArrayList<String> nucleique = res.getNucliqueList();
		String nuc = "";

		for (String s : nucleique)
		{
			nuc += s;
		}

		System.out.println(nuc);

		Palindrome p = new Palindrome(nuc);
		// boolean[][] c = p.getCheck();
		// System.out.println(memoToString(c));
		// boolean[][] v = p.getValue();
		// System.out.println(memoToString(v));
		// boolean[][] f = p.getFind();
		// System.out.println(memoToString(f));

		List<String> l = p.getLib();
		System.out.println(l.size());

		long elapsedTime = System.nanoTime() - start;
		double seconds = (double) elapsedTime / 1000000000.0;
		System.out.println("Taking " + seconds + " to build the library");
	}

	public boolean[][] getFind()
	{
		return find;
	}

	public boolean[][] getCheck()
	{
		return check;
	}

	public boolean[][] getValue()
	{
		return value;
	}

}
