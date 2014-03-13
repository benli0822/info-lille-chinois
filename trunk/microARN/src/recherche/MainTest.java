package recherche;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening
 * 
 *         Find the secondary structure in gemomics sequences using Nussinov
 *         Jacobson algorithm
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
		MainTest mt1 = new MainTest();
		String s1 = mt1.readFile("chromosome13_NT_009952.14.fasta");
		System.out.println(s1);

		MainTest mt2 = new MainTest();
		String s2 = mt2.readFile("ARNmessager-1.fasta");
		System.out.println(s2);

		char[] seq1 = s1.toCharArray();
		char[] seq2 = s2.toCharArray();

		char[] tempSeq1 = new char[3933];
		char[] tempSeq2 = new char[3933];

		int count = 0;
		while (count < 3933)
		{
			tempSeq1[count] = seq1[count];
			tempSeq2[count] = seq2[count];
			count++;
		}
		// System.out.println(seq2.length);

		// NeedlemanWunsch nw = new NeedlemanWunsch();
		// nw.init(seq1, seq2);
		// nw.process();
		// nw.backtrack();
		//
		// nw.printMatrix();
		// nw.printScoreAndAlignments();

		SmithWaterman sw = new SmithWaterman();
		sw.init(tempSeq1, tempSeq2);
		sw.process();
		sw.backtrack();

		// sw.printMatrix();
		sw.printScoreAndAlignments();
	}
}
