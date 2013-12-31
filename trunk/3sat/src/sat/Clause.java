package sat;

import java.util.*;

import model.PartialAssignment;

/**
 * Represents a clause in a CNF expression
 * 
 * NOTE: The literals of a clauses is stored as an Integer array in the
 * following way: Positive literals are represented as the variable number, A
 * literal with a negation is represented as -1* (variable number)
 * 
 * NOTE: For simplicity, the constructor removes redundant duplicate literals.
 */
public class Clause {
	Vector<Integer> literals; // a vector containing integers encoding the
								// literals in a clause
	// varibles in a clause will be identified by integers, as in the input file
	// a negative literal will be represented by a negation of the integer
	// representing the corresponding variable
	// for example, the clause (1,-9,3) will just be represented by a vector of
	// 3 integers : 1,-9,3

	public Vector<Integer> initLits; // stored in case we want to write it
										// unoptimized to a file etc.

	/**
	 * Creates a clause from a vector of literals removing duplicate literals
	 * (ie x1 AND x1) trivially satisfied clauses are removed in the later
	 * before the DPLL step
	 * 
	 * @param lits
	 */
	@SuppressWarnings("unchecked")
	public Clause(Vector<Integer> lits) {
		initLits = (Vector<Integer>) lits.clone();
		HashSet<Integer> s = new HashSet<Integer>();
		s.addAll(lits);
		literals = new Vector<Integer>();
		literals.addAll(s);
	}

	/**
	 * Checks whether this clause would be true under any assignment
	 * 
	 * @return
	 */
	public boolean isTriviallyTrue() {
		HashSet<Integer> lits = new HashSet<Integer>();
		lits.addAll(literals);
		for (int i = 0; i < literals.size(); i++) {
			Integer literal = literals.elementAt(i);
			if (lits.contains(-literal.intValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method for displaying a clause
	 */
	public String toString() {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("[CLAUSE:( ");
		for (int i = 0; i < size(); i++) {
			sbuf.append(literals.get(i) + ",");
		}
		sbuf.append(")]");
		return sbuf.toString();
	}

	/**
	 * Checks whether the clause resolves to true under the current assignment.
	 * 
	 * @param model
	 * @return true if the clause is satisfied, false otherwise(Does not mean
	 *         its false!)
	 */
	public boolean isSatisfied(PartialAssignment model) {
		// given an assignment for the variables, this functions returns true
		// when the model satisfies the clause, otherwise false
		for (int i = 0, isize = literals.size(); i < isize; i++) {
			int literal = ((Integer) literals.elementAt(i)).intValue();
			if (model.isLiteralTrue(literal))
				return true;

		}
		return false;
	}

	/**
	 * Count the number of literals whose value is yet undetermined in the
	 * clause assignment.
	 * 
	 * @param model
	 * @return a count of literals who are undetermined in this clause
	 */
	public int countFreeLiterals(PartialAssignment model) {
		int count = 0;
		// given an assignment for the variables, this functions returns true
		// when the model satisfies the clause, otherwise false
		for (int i = 0, isize = literals.size(); i < isize; i++) {
			int literal = ((Integer) literals.elementAt(i)).intValue();
			int var = (literal < 0) ? -1 * literal : literal;
			if (model.isUnAssigned(var)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Find the literals whose value is yet undetermined in the clause
	 * assignment, and returns the set of thenm.
	 * 
	 * @param model
	 * @return a set of literals who are undetermined in this clause
	 */
	public Set<Integer> returnFreeLiterals(PartialAssignment model) {
		HashSet<Integer> set = new HashSet<Integer>();

		for (int i = 0, isize = literals.size(); i < isize; i++) {
			int literal = ((Integer) literals.elementAt(i)).intValue();
			int var = (literal < 0) ? -1 * literal : literal;
			if (model.isUnAssigned(var)) {
				set.add(var);
			}
		}
		return set;
	}

	/**
	 * Convienience method for retrieving literals
	 * 
	 * @param index
	 * @return
	 */
	public int getLiteral(int index) {
		return ((Integer) (literals.get(index))).intValue();
	}

	/**
	 * Retrieves the vector of Integers representing the literals
	 * 
	 * @return
	 */
	public Vector<Integer> getLiterals() {
		// this function just returns the literals that form the clause
		return literals;
	}

	/**
	 * Return the number of literals in the clause
	 */
	public int size() {
		// return the size of the clause
		return literals.size();
	}

	/**
	 * Returns the index symbol if it's contained in normal or negated form, -1
	 * if it's not contained.
	 * 
	 * @param symbol
	 *            the symbol to look for.
	 * @return -1 if it does not contain the symbol, the index of the symbol
	 *         otherwise.
	 */
	public int contains(int symbol) {
		for (int i = 0, isize = literals.size(); i < isize; i++) {
			int literal = ((Integer) literals.elementAt(i)).intValue();
			int var = (literal < 0) ? -1 * literal : literal;
			if (symbol == var) {
				return i;
			}
		}

		return -1;
	}

	public boolean unpureSymbol(int symbol) {
		for (int i = 0, isize = literals.size(); i < isize; i++) {
			if (((Integer) literals.elementAt(i)).intValue() == -symbol) {
				return true;
			}
		}

		return false;
	}

}