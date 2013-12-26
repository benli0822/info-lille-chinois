package com.troissat;

import java.util.ArrayList;
import java.util.Vector;

import model.CompleteAssignment;

/**/
/*(v[1] || !v[2] || v[4]) && (!v[1]  || v[3] || v[4]) && (!v[3] || !v[4] || v[2])*/
public class AlgoVerfication {
	public Formule TestFormule;

	private static ArrayList<Boolean> res;

	public AlgoVerfication(Formule testFormule) {
		this.TestFormule = testFormule;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AleatoireCertificats ac = new AleatoireCertificats(4);
		res = ac.getResultat();

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

		boolean[] listB = new boolean[res.size()];
		int i = 0;
		for (Boolean b : res) {
			listB[i] = b;
			i++;
		}
		for (i = 0; i < listB.length; i++) {
			System.out.print(listB[i] + " ");
		}
		System.out.print("=== ");
		CompleteAssignment ca = new CompleteAssignment(verification);
		ca.compelteAssignment(listB);
		Boolean res = ca.calculerVrai();
		System.out.println(res);
	}
}
