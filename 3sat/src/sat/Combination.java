package sat;

/**
 * Description: input: size of boolean variable, output: all combination of boolean 
 * for n = 2, (true, false),(true, true),(false, true),(false, false).
 *
 * Algorithm design???
 * array boolean[n] for store all boolean value, 1 for true, 0 for false,
 * 
 * for input n = 3, it should be:
 * 000 -> 001 -> 010 -> 011 -> 100 -> 101 -> 110 -> 111
 *
 * Detail:
 * Start with the last element of array???
 * ???  if b[n] = 0, then put to 1 without carrying a bit;
 * ???  if b[n] = 1, then put to 0 and carry a bit one by one from 1 to 0;
 *    stop with b[x] is assigned from 0 to 1;
 *   
 * Ex??? 011 :
 * ???  last 1 to 0 and add a bit;
 * ???  second 1 to 0 and add a bit;
 * ???  first 0 to 1, end result is 100;
 *
 *
 * Note???
 * ??? 1 and 0 for true and false
 *
 * ??? endflag for 1..1 -> 0..0; endflag = true;
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Combination {

	private boolean[] combination;
	private long count;
	private boolean endflag;

	public Combination(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("Input must be postive");
		if (combination == null) {
			combination = new boolean[n];
			count = 0;
			endflag = false;
		}
	}

	/**
	 * Give all combination
	 * 
	 */
	public void solution() {
		System.out.println("n = " + combination.length + " ***** All combination??? ");
		do {
			System.out.println(getOneTuple());
			count++;
			increOne();
		} while (!terminate());
		System.out.println("Total Combination ??? " + count);
	}

	/**
	 * add one by one
	 * 
	 */
	public void increOne() {
		int i;
		for (i = 0; i < combination.length; i++) {
			// if 0 then 1
			if (combination[i] == false) {
				combination[i] = true;
				break;
			} else {
				// if 1 then 0 and add one by one
				combination[i] = false;
			}
		}
		// when 1..1 -> 0..0, endflag = true;
		if (i == combination.length) {
			endflag = true;
		}
	}

	/**
	 * from array of boolean to string
	 * 
	 */
	private String getOneTuple() {
		StringBuilder tuple = new StringBuilder("(");
		for (int i = 0; i < combination.length; i++) {
			tuple.append(combination[i]);
			tuple.append(",");
		}
		// remove ","
		tuple.deleteCharAt(tuple.length() - 1);
		tuple.append(")");
		return tuple.toString();
	}
	
	public boolean[] getOnePossibite() {
		return combination;
	}

	/**
	 * End with endflag = true;
	 * 
	 */
	public boolean terminate() {
		return endflag == true;
	}

	public static void main(String[] args) {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			String s = null;
			while ((s = stdin.readLine()).matches("[1-9][0-9]*")) {
				int n = Integer.parseInt(s);
				System.out.println("n = " + n);
				Combination c = new Combination(n);
				c.solution();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		// Combination c = new Combination(3);
		// c.solution();
	}

}