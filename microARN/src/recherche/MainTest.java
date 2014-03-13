package recherche;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening
 * 
 *         Main test function
 */
public class MainTest
{
	public String readFile(String args) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(args));
		String everything = "";
		try
		{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			line = br.readLine();

			while (line != null)
			{
				sb.append(line);
				// sb.append(System.getProperty("line.separator"));
				line = br.readLine();
			}
			everything = sb.toString();
			// System.out.println(everything);
		}
		finally
		{
			br.close();
		}
		return everything;
	}

	public static void main(String[] args) throws IOException
	{
		// MainTest mt1 = new MainTest();
		// String s1 = mt1.readFile("chromosome13_NT_009952.14.fasta");
		// System.out.println(s1);

		// MainTest mt2 = new MainTest();
		// String s2 = mt2.readFile("ARNmessager-1.fasta");
		// System.out.println(s2);

		if (args.length != 0)
		{
			String s1 = args[0];
			String s2 = args[1];

			char[] seq1 = s1.toCharArray();
			char[] seq2 = s2.toCharArray();

			System.out.println("Testing for " + args[0] + " " + args[1]);
			long start1 = System.nanoTime();
			NeedlemanWunsch nw = new NeedlemanWunsch();
			nw.init(seq1, seq2);
			nw.process();
			nw.backtrack();
			nw.printMatrix();
			nw.printScoreAndAlignments();
			long elapsedTime1 = System.nanoTime() - start1;
			double seconds1 = (double) elapsedTime1 / 1000000000.0;
			System.out.println("Taking " + seconds1 + "s to global alignment");
			System.out.println();

			System.out.println("Testing for " + args[0] + " " + args[1]);
			long start2 = System.nanoTime();
			SmithWaterman sw = new SmithWaterman();
			sw.init(seq1, seq2);
			sw.process();
			sw.backtrack();
			sw.printMatrix();
			sw.printScoreAndAlignments();
			long elapsedTime2 = System.nanoTime() - start2;
			double seconds2 = (double) elapsedTime2 / 1000000000.0;
			System.out.println("Taking " + seconds2 + "s to local alignment");
			System.out.println();

			System.out.println("Testing for " + args[0] + " " + args[1]);
			long start3 = System.nanoTime();
			Hirschberg h = new Hirschberg();
			String lcs = h.getLCS(seq1.length, seq2.length, s1, s2);
			System.out.println("Result: " + lcs);
			long elapsedTime3 = System.nanoTime() - start3;
			double seconds3 = (double) elapsedTime3 / 1000000000.0;
			System.out.println("Taking " + seconds3
					+ "s to find the longest common character");
			System.out.println();

			Nussinov n = new Nussinov();
			n.test();

			Palindrome.test(false);
		}
		else
		{
			System.out.println("Usage: test with two sequence as parameter.");
		}
	}
}
