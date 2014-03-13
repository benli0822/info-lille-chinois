package recherche;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import simulateur.SimulateurMain;

/**
 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening
 * 
 *         Find the secondary structure in gemomics sequences
 *         using Nussinov Jacobson algorithm
 */
public class Nussinov
{

	// ---------------------------------------------------------------
	/** A string of RNA sequence */
	private String seq;
	/** The number of characters in the RNA sequence */
	private int n;
	/** A matrix for computing max number of pairs */
	private int[][] B;
	/** An auxiliary matrix for traceback */
	private int[][] P;

	private String result;

	// ----------------------------------------------------------------

	/**
	 * Construct an instance with a given string of RNA sequence.
	 */
	public void init(String sequence)
	{
		// Initialize the instance variables
		this.seq = sequence;
		this.n = seq.length();
		this.B = new int[this.n][this.n];
		this.P = new int[this.n][this.n];
	}

	/**
	 * Check G-C and A-U pairs forming in a RNA sequence.
	 * 
	 * @param xi
	 *            , xj - two characters to be checked if they are paired.
	 * @return 1 if they are paired, otherwise 0.
	 */
	public int isPaired(char xi, char xj)
	{
		if ((xi == 'G' && xj == 'C') || (xi == 'C' && xj == 'G'))
			return 1;
		else if ((xi == 'A' && xj == 'U') || (xi == 'U' && xj == 'A'))
			return 1;
		else if ((xi == 'G' && xj == 'U') || (xi == 'U' && xj == 'G'))
			return 1;
		else
			return 0;
	}

	/**
	 * With Dynamic Programming, compute the possible max number of pairs in a
	 * given RNA sequence.
	 * 
	 * @return max the maximum number of valid pairs.
	 */
	public int findMaxPairs()
	{
		int max, temp;
		for (int i = this.n - 1; i >= 0; i--)
		{
			for (int j = 0; j < this.n; j++)
			{
				max = 0;
				// --------------------------------------------------------------
				// The algorithm looks at each recursive term to see whether it
				// gives the max num of pair. If the max. pair occurs in a
				// particular term, the max. num will be stored in a matrix with
				// the respective position indexed by i,j, and k. While doing
				// that,
				// the algorithm also places a mark, such as -3, -2, -1, and k,
				// on the repective entry in a traceback matrix.
				// --------------------------------------------------------------

				// If i >= j - 4, then B[i,j] = 0. Since the matrix B is
				// initialized
				// in the constructor, nothing needs to be done.
				if (i >= j - 8)
				{
					this.P[i][j] = -3;
				}
				else
				{ // If i < j - 4, then it's valid for folding.
					// Case1: i,j paired together or none of i and j is paired
					temp = this.B[i + 1][j - 1]
							+ isPaired(this.seq.charAt(i), this.seq.charAt(j));
					if (temp > max)
					{
						max = temp;
						this.P[i][j] = -1
								- isPaired(this.seq.charAt(i),
										this.seq.charAt(j));
					}
					// Case2: i, j paired with k such that 1 <= k < j
					for (int k = i; k < j; k++)
					{
						temp = this.B[i][k] + this.B[k + 1][j];
						if (temp > max)
						{
							max = temp;
							this.P[i][j] = k;
						}
					}
					this.B[i][j] = max;
				}
			}
		}
		return this.B[0][this.n - 1]; // holds the maximum number of pairs
	}

	/**
	 * Trace back the possible maximum number of pairs, then consctruct the
	 * structure of folding using parens.
	 */
	public StringBuilder traceBack(int i, int j, StringBuilder sb)
	{

		if (this.P[i][j] == -3)
		{ // Not paired
			for (int d = 1; d <= j - i + 1; d++)
			{
				sb.append('.');
			}
		}
		else if (this.P[i][j] == -2)
		{ // i and j paired together
			sb.append('(');
			traceBack(i + 1, j - 1, sb);
			sb.append(')');
		}
		else if (this.P[i][j] == -1)
		{ // i and j are not paired together
			sb.append('.');
			traceBack(i + 1, j - 1, sb);
			sb.append('.');
		}
		else
		{ // i and j could be possibly paired with k such that i <= k < j
			int k = this.P[i][j];
			if (i <= k && k + 1 <= j)
			{
				traceBack(i, k, sb);
				traceBack(k + 1, j, sb);
			}
		}
		return sb;
	}

	/**
	 * Display a structure of RNA folding, having the max number of pairs.
	 */
	public String execute(String sequence)
	{
		// log.info("RNA sequence : " + seq);
		init(sequence);
		this.findMaxPairs();
		StringBuilder sb = traceBack(0, n - 1, new StringBuilder());
		// Trace back and construct the max num folding
		this.result = sb.toString();
		// log.info("Folding structure : "+result);
		return this.result;
		// printMatrix();

	}

	/**
	 * Print the entire matrix which shows how the computation of the max number
	 * of pairs is processed.
	 */
	public void printMatrix()
	{
		for (int i = 0; i < this.B.length; i++)
		{
			for (int j = 0; j < this.B.length; j++)
			{
				if (this.B[i][j] > 9)
				{
					System.out.print(" " + B[i][j]);
				}
				else
				{
					System.out.print("  " + B[i][j]);
				}
			}
			System.out.println();
		}
	}

	public void printMatrix2()
	{
		for (int i = 0; i < this.P.length; i++)
		{
			for (int j = 0; j < this.P.length; j++)
			{
				if (this.P[i][j] < 0 || this.P[i][j] > 10)
				{
					System.out.print(" " + P[i][j]);
				}
				else
				{
					System.out.print("  " + P[i][j]);
				}
			}
			System.out.println();
		}
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public static void main(String[] args) throws IOException
	{
		long start = System.nanoTime();
		SimulateurMain res = new SimulateurMain();
		res.generateBoucleTerminale();
		res.generateAppariements();
		ArrayList<String> nucleique = res.getNucliqueList();
		ArrayList<String> sym = res.getSymboleList();
		// System.out.println(nucleique.size());
		int nucSize = nucleique.size();
		String nuc = "";
		String symbole = "";

		if (nucSize >= 100)
		{
			for (int i = 0; i < 100; i++)
			{
				nuc += nucleique.get(i);
				symbole += sym.get(i);
			}
		}
		else
		{
			for (String s : nucleique)
			{
				nuc += s;
			}
			for (String s : sym)
			{
				symbole += s;
			}
		}

		System.out.println(nuc);
		System.out.println(nuc.length());
	
		Nussinov n = new Nussinov();
		n.execute(nuc);
		n.printMatrix();
		System.out.println();
		n.printMatrix2();

	
		String res1 = n.getResult();
		System.out.println(res1);
		System.out.println(symbole);

		long elapsedTime = System.nanoTime() - start;
		double seconds = (double) elapsedTime / 1000000000.0;
		System.out.println("Taking " + seconds + " to build the library");
	}

}
