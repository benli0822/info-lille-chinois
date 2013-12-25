package com.troissat;

import java.util.ArrayList;

public class Reduction {
	/* reduciton de sat a 3sat */
	private ArrayList<Litteral> listeLitteral;
	private ArrayList<Clause> resClause;

	public Reduction(ArrayList<Litteral> listeLitteral) {
		this.listeLitteral = listeLitteral;
	}

	// sat to 3sat
	public ArrayList<Clause> reduction() {
		int size = this.listeLitteral.size();
		this.resClause = new ArrayList<Clause>();

		if (size == 1) {
			// size = 1, we add two variable 
			Litteral y1 = new Litteral("i,1", true);
			Litteral y2 = new Litteral("i,2", true);
			Litteral z1 = this.listeLitteral.get(0);
			this.resClause.add(new Clause(z1, y1, y2, true, true, true));
			this.resClause.add(new Clause(z1, y1, y2, true, true, false));
			this.resClause.add(new Clause(z1, y1, y2, true, false, true));
			this.resClause.add(new Clause(z1, y1, y2, true, false, false));
		} else if (size == 2) {
			// size = 2, we add one
			Litteral z1 = this.listeLitteral.get(0);
			Litteral z2 = this.listeLitteral.get(1);
			Litteral y1 = new Litteral("i,1", true);
			this.resClause.add(new Clause(z1, z2, y1, true, true, true));
			this.resClause.add(new Clause(z1, z2, y1, true, true, false));
		} else if (size == 3) {
			// size = 3, nothing to add
			Litteral z1 = this.listeLitteral.get(0);
			Litteral z2 = this.listeLitteral.get(1);
			Litteral z3 = this.listeLitteral.get(2);
			this.resClause.add(new Clause(z1, z2, z3, true, true, true));
		} else if (size > 3) {
			// size > 3, most difficult situation
			ArrayList<Litteral> newLitteral = new ArrayList<Litteral>();
			for (int i = 1; i <= size - 3; i++) {
				newLitteral.add(new Litteral("i," + i, true));
			}
			Litteral z1 = this.listeLitteral.get(0);
			Litteral z2 = this.listeLitteral.get(1);
			Litteral y1 = newLitteral.get(0);
			this.resClause.add(new Clause(z1, z2, y1, true, true, true));
			for (int i = 2; i < size - 2; i++) {
				Litteral yj = newLitteral.get(i - 2);
				Litteral zi = this.listeLitteral.get(i);
				Litteral yk = newLitteral.get(i - 1);
				this.resClause.add(new Clause(yj, zi, yk, false, true, true));
			}
			Litteral yk3 = newLitteral.get(size - 4);
			Litteral zk1 = this.listeLitteral.get(size - 2);
			Litteral zk = this.listeLitteral.get(size - 1);
			this.resClause.add(new Clause(yk3, zk1, zk, false, true, true));
		} else {
			// exception
			System.out.println("unexpected formule of reduction!");
		}
		
		return this.resClause;
	}
	
	
}
