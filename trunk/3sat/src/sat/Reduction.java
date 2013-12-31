package sat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

public class Reduction {
	/* reduciton de sat a 3sat */
	public int numVars;
	private Formule formule;

	public Reduction(Formule formule, int numVars) {
		this.formule = formule;
		this.numVars = numVars;
	}

	// sat to 3sat
	public Formule reduction() {
		Vector<Clause> newListClause = new Vector<Clause>();

		if (this.numVars == 1) {
			// size = 1, we add two variable
			HashSet<Integer> literals = new HashSet<Integer>();
			Vector<Clause> listClause = this.formule.getListClause();
			// read all clause and put all variable into one
			for (Clause c : listClause) {
				literals.addAll(c.getLiterals());
			}

			Vector<Integer> newLiterals1 = new Vector<Integer>();
			newLiterals1.addAll(literals);
			newLiterals1.add(2); // i,1
			newLiterals1.add(3); // i,2
			Vector<Integer> newLiterals2 = new Vector<Integer>();
			newLiterals2.addAll(literals);
			newLiterals2.add(2);
			newLiterals2.add(-3);
			Vector<Integer> newLiterals3 = new Vector<Integer>();
			newLiterals3.addAll(literals);
			newLiterals3.add(-2);
			newLiterals3.add(3);
			Vector<Integer> newLiterals4 = new Vector<Integer>();
			newLiterals4.addAll(literals);
			newLiterals4.add(-2);
			newLiterals4.add(-3);

			Clause newClause1 = new Clause(newLiterals1);
			Clause newClause2 = new Clause(newLiterals2);
			Clause newClause3 = new Clause(newLiterals3);
			Clause newClause4 = new Clause(newLiterals4);

			newListClause.add(newClause1);
			newListClause.add(newClause2);
			newListClause.add(newClause3);
			newListClause.add(newClause4);

			return new Formule(newListClause, 3);
		} else if (this.numVars == 2) {
			// size = 2, we add one
			HashSet<Integer> literals = new HashSet<Integer>();
			Vector<Clause> listClause = this.formule.getListClause();
			for (Clause c : listClause) {
				literals.addAll(c.getLiterals());
			}

			Vector<Integer> newLiterals1 = new Vector<Integer>();
			newLiterals1.addAll(literals);
			newLiterals1.add(3); // i,2
			Vector<Integer> newLiterals2 = new Vector<Integer>();
			newLiterals2.addAll(literals);
			newLiterals2.add(-3);

			Clause newClause1 = new Clause(newLiterals1);
			Clause newClause2 = new Clause(newLiterals2);

			newListClause.add(newClause1);
			newListClause.add(newClause2);

			return new Formule(newListClause, 3);
		} else if (this.numVars == 3) {
			// size = 3, nothing to add
			HashSet<Integer> literals = new HashSet<Integer>();
			Vector<Clause> listClause = this.formule.getListClause();
			for (Clause c : listClause) {
				literals.addAll(c.getLiterals());
			}
			Vector<Integer> newLiterals = new Vector<Integer>();
			newLiterals.addAll(literals);
			Clause newClause = new Clause(newLiterals);
			newListClause.add(newClause);
			return new Formule(newListClause, 3);
		} else if (this.numVars > 3) {
			// size > 3, most difficult situation
			ArrayList<Integer> literals = new ArrayList<Integer>();
			Vector<Clause> listClause = this.formule.getListClause();
			for (Clause c : listClause) {
				literals.addAll(c.getLiterals());
			}
			Vector<Integer> newLiterals1 = new Vector<Integer>();
			newLiterals1.add(literals.get(0));
			newLiterals1.add(literals.get(1));
			newLiterals1.add(this.numVars + 1);
			Clause newClause1 = new Clause(newLiterals1);
			newListClause.add(newClause1);

			int index = 2;
			int counter = 2;
			int indexVariable = this.numVars + 1;
			while (counter < this.numVars - 2) {
				Vector<Integer> newLiterals = new Vector<Integer>();
				newLiterals1.add((this.numVars + index) * (-1));
				newLiterals.add(literals.get(index));
				newLiterals1.add((this.numVars + index + 1));
				Clause newClause = new Clause(newLiterals);
				newListClause.add(newClause);
				indexVariable = this.numVars + index + 1;
				index++;
				counter++;

			}

			Vector<Integer> newLiterals2 = new Vector<Integer>();
			newLiterals1.add((indexVariable + 1) * (-1));
			newLiterals1.add(literals.get(this.numVars - 2));
			newLiterals1.add(literals.get(this.numVars - 1));
			Clause newClause2 = new Clause(newLiterals2);
			newListClause.add(newClause2);

			return new Formule(newListClause, indexVariable + 1);
		} else {
			// exception
			throw new IllegalArgumentException(
					"unexpected formule of reduction!");
		}
	}

}
