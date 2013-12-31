package sat;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.util.Vector;

import model.CompleteAssignment;

public class ListerToutesPoss {

	/*
	 * (v[1] || !v[2] || v[4]) && (!v[1] || v[3] || v[4]) && (!v[3] || !v[4] ||
	 * v[2])
	 */

	public void lister(int n) {
		Vector<Integer> clause1 = new Vector<Integer>();
		clause1.add(1);
		clause1.add(-2);
		clause1.add(4);

		Vector<Integer> clause2 = new Vector<Integer>();
		clause2.add(-1);
		clause2.add(3);
		clause2.add(4);

		Vector<Integer> clause3 = new Vector<Integer>();
		clause3.add(-3);
		clause3.add(-4);
		clause3.add(2);

		Clause c1 = new Clause(clause1);
		Clause c2 = new Clause(clause2);
		Clause c3 = new Clause(clause3);

		Vector<Clause> certificat = new Vector<Clause>();

		certificat.add(c1);
		certificat.add(c2);
		certificat.add(c3);

		Formule verification = new Formule(certificat, 4);

		Combination c = new Combination(4);

		do {
			boolean[] listB = c.getOnePossibite();
			for (int i = 0; i < listB.length; i++) {
				System.out.print(listB[i] + " ");
			}
			System.out.print("=== ");
			CompleteAssignment ca = new CompleteAssignment(verification);
			ca.compelteAssignment(listB);
			Boolean res = ca.calculerVrai();
			System.out.println(res);
			c.increOne();
		} while (!c.terminate());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// BufferedReader stdin = new BufferedReader(new InputStreamReader(
		// System.in));
		try {
			// String s = null;
			// while ((s = stdin.readLine()).matches("[1-9][0-9]*")) {
			// int n = Integer.parseInt(s);
			int n = 4;
			System.out.println("n = " + n);
			ListerToutesPoss ltp = new ListerToutesPoss();
			ltp.lister(n);
			// }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
