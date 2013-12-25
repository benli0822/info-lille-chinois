package com.troissat;

import java.util.ArrayList;

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
		
		Litteral v1 = new Litteral(Integer.toString(1), res.get(0));
		Litteral v2 = new Litteral(Integer.toString(2), res.get(1));
		Litteral v3 = new Litteral(Integer.toString(3), res.get(2));
		Litteral v4 = new Litteral(Integer.toString(4), res.get(3));

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
