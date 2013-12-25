package com.troissat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ListerToutesPoss {

	/*
	 * (v[1] || !v[2] || v[4]) && (!v[1] || v[3] || v[4]) && (!v[3] || !v[4] ||
	 * v[2])
	 */

	public void lister(int n) {
		List<Litteral> l = new ArrayList<Litteral>();
		for (int i = 1; i <= n; i++) {
			l.add(new Litteral(Integer.toString(i)));
		}
		Clause c1 = new Clause(l.get(0), l.get(1), l.get(3), true, false, true);
		Clause c2 = new Clause(l.get(0), l.get(2), l.get(3), false, true, true);
		Clause c3 = new Clause(l.get(2), l.get(3), l.get(1), false, false, true);

		ArrayList<Clause> certificat = new ArrayList<Clause>();

		certificat.add(c1);
		certificat.add(c2);
		certificat.add(c3);

		Formule verification = new Formule(certificat);

		Combination c = new Combination(4);

		do {
			boolean[] listB = c.getOnePossibite();
			for (int i = 0; i < listB.length; i++) {
				System.out.print(listB[i] + " ");
			}
			System.out.print("=== ");
			for (int i = 0; i < n; i++) {
				l.get(i).setValeur(listB[i]);
			}
			Boolean res = verification.calculerVrai();
			System.out.println(res);
			c.increOne();
		} while (!c.terminate());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			String s = null;
			while ((s = stdin.readLine()).matches("[1-9][0-9]*")) {
				int n = Integer.parseInt(s);
				System.out.println("n = " + n);
				ListerToutesPoss ltp = new ListerToutesPoss();
				ltp.lister(n);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
