package recherche;

import java.util.ArrayList;
import java.util.List;

import simulateur.SimulateurMain;

/**
 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening
 * 
 *         Find all Palindrome in gemomics sequences
 */
public class Palindrome
{

	private String res; // Resource sequence
	private ArrayList<String> lib; // Palindrome words
	private int n; // Length of res
	boolean check[][];
	// register array for checked or not the correspondence of index i and j
	boolean value[][];
	// register array for the checked value of correspondence index i and j
	boolean find[][];
	private boolean debug = false;

	// register array for all substring view in the check and generate method

	public Palindrome(String res, boolean debug)
	{
		this.res = res;
		this.n = res.length();
		this.debug = debug;
		// initialize all check value as false
		check = new boolean[n][n];
		for (int i = 0; i < this.n; i++)
		{
			for (int j = 0; j < this.n; j++)
			{
				check[i][j] = false;
			}
		}
		// initialize all value as false
		value = new boolean[n][n];
		for (int i = 0; i < this.n; i++)
		{
			for (int j = 0; j < this.n; j++)
			{
				value[i][j] = false;
			}
		}

		// initialize the upper case find value as false, becausse it's
		// not necessary to check for substring(i, j) when i > j
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
		// begin the process
		fingAndGenerate(res);
	}

	/**
	 * Check for Palindrome for substring of resource Algorithm: check the first
	 * character and the last character for certain rules If correct, pass to
	 * the next section, which means the substring of current string who has
	 * removed the first and the last correspond letter, recursively call the
	 * function itself and when the length of substring become 0 or 1, stop and
	 * give the right answer
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean check(String toFind, int i, int j)
	{
		int length = toFind.length();
		if (this.debug)
			System.out.println("Now checking for string: " + toFind
					+ ", length = " + length);

		if (!check[i][j])
		{
			if (this.debug)
				System.out
						.println("Not yet checked, now begin to check for i = "
								+ i + ", j = " + j);
			check[i][j] = true;
			if (length == 0 || length == 1)
			{
				// if length =0 OR 1 then it is
				value[i][j] = true;
				if (this.debug)
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
					|| (beginChar == 'U' && endChar == 'G'))
			{
				// check for first and last char of String:
				// if they are same then do the same thing for a substring
				// with first and last char removed. and carry on this
				// until you string completes or condition fails
				if (this.debug)
					System.out.println("Success with [" + i + "][" + j
							+ "], Entering next section");
				value[i][j] = true;
				if (length >= 3 && length <= 8)
					return true;
				else
					return check(toFind.substring(1, length - 1), i + 1, j - 1);
			}

			value[i][j] = false;
			if (this.debug)
				System.out.println("Failure with [" + i + "][" + j + "]");
			// if its not the case than string is not.
			return false;
		}
		else
		{
			if (this.debug)
				System.out.println("Already checked for i = " + i + ", j = "
						+ j);
			if (value[i][j])
			{
				if (length == 0 || length == 1)
				{
					if (this.debug)
						// if length =0 OR 1 then it is
						System.out.println("Success with [" + i + "][" + j
								+ "]");
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

	/**
	 * Find and generate the stem-loop library in the memory Algorithm: for the
	 * give string as enter, check for all possible substring with the
	 * Palindrome algorithm and using the dynamic programming, which represent
	 * by the find array
	 * 
	 * @param f
	 */
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
						{
							boolean include = false;
							// for (String s : this.lib)
							// {
							// if (s.toUpperCase().contains(
							// toFind.toUpperCase())
							// || toFind.toUpperCase().contains(
							// s.toUpperCase()))
							// include = true;
							// }
							if (!include)
								this.lib.add(toFind);
						}
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

	/**
	 * Return the register array Find
	 * 
	 * @return
	 */
	public boolean[][] getFind()
	{
		return find;
	}

	/**
	 * Return the register array Check
	 * 
	 * @return
	 */
	public boolean[][] getCheck()
	{
		return check;
	}

	/**
	 * Return the register array Value
	 * 
	 * @return
	 */
	public boolean[][] getValue()
	{
		return value;
	}

	/**
	 * Printing method for display the boolean array
	 * 
	 * @param memo
	 * @return
	 */
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

	public static void test(boolean debug)
	{
		long start = System.nanoTime();
		SimulateurMain res = new SimulateurMain();
		res.generateBoucleTerminale();
		res.generateAppariements();
		ArrayList<String> nucleique = res.getNucliqueList();
		// System.out.println(nucleique.size());
		int nucSize = nucleique.size();
		String nuc = "";

		if (nucSize >= 100)
		{
			for (int i = 0; i < 100; i++)
			{
				nuc += nucleique.get(i);
			}
		}
		else
		{
			for (String s : nucleique)
			{
				nuc += s;
			}
		}

		System.out.println("Test generated by simulater: " + nuc);
		System.out.println("Length: " + nuc.length());

		Palindrome p = null;
		if (debug)
		{
			p = new Palindrome(nuc, true);
		}
		else
		{
			p = new Palindrome(nuc, false);
		}
		// boolean[][] c = p.getCheck();
		// System.out.println(memoToString(c));
		// boolean[][] v = p.getValue();
		// System.out.println(memoToString(v));
		// boolean[][] f = p.getFind();
		// System.out.println(memoToString(f));

		List<String> l = p.getLib();
		System.out.println("Generated library size: " + l.size());
		System.out.println("Content: ");
		for (String s : l)
		{
			System.out.print(s + "  ");
		}
		System.out.println();

		long elapsedTime = System.nanoTime() - start;
		double seconds = (double) elapsedTime / 1000000000.0;
		System.out.println("Taking " + seconds + " to build the library");
	}
}
