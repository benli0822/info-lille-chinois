package com.troissat;

import java.util.ArrayList;

/**/
/*(v[1] || !v[2] || v[4]) && (!v[1]  || v[3] || v[4]) && (!v[3] || !v[4] || v[2])*/
public class AlgoVerfication {
	public Formule TestFormule;

	public AlgoVerfication(Formule testFormule) {
		this.TestFormule = testFormule;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Litteral v1 = new Litteral(1, true);
		Litteral v2 = new Litteral(2, false);
		Litteral v3 = new Litteral(3, false);
		Litteral v4 = new Litteral(4, false);

		Clause c1 = new Clause(v1, v2, v4, true, false, true);
		Clause c2 = new Clause(v1, v3, v4, false, true, true);
		Clause c3 = new Clause(v3, v4, v2, false, false, true);

		Formule f = new Formule();
		f.adjouteDansLeList(c1);
		f.adjouteDansLeList(c2);
		f.adjouteDansLeList(c3);

		System.out.println(f.calculerVrai());
	}
}
