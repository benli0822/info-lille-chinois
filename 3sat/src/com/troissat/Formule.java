package com.troissat;

import java.util.Vector;

public class Formule {

	public int numVars;
	public int numClause;
	public Vector<Clause> listClause;
	public Vector<Clause> initClause;

	public Formule(Vector<Clause> listClause, int numVars) {
		this.numVars = numVars;
		this.listClause = new Vector<Clause>();
		this.initClause = listClause;
		for (int i = 0; i < this.initClause.size(); i++) {
			Clause c = this.initClause.get(i);
			if (!c.isTriviallyTrue()) {
				this.listClause.add(c);
//				System.out.println(c);
			}
		}
		this.numClause = this.listClause.size();
	}

	public void adjouteDansLeList(Clause c) {
		this.listClause.add(c);
	}

	public int getNumVars() {
		return numVars;
	}

	public int getNumClause() {
		return numClause;
	}

	public Clause getClause(int index) {
		return (Clause) listClause.elementAt(index);
	}

	public Vector<Clause> getListClause() {
		return listClause;
	}

}
