package com.troissat;

import java.util.HashSet;
import java.util.Vector;

public class Clause {

	private Vector<Integer> literals;

	/**
	 * Constructor for sat problem, using vector to present the clause, positive
	 * value present the id of literal, negative value present the id and the
	 * negation of literal
	 * 
	 * Using the characteristic of Set to remove all duplicate literal
	 * 
	 * @param literals
	 */
	public Clause(Vector<Integer> literals) {
		HashSet<Integer> s = new HashSet<Integer>();
		s.addAll(literals); // delete all duplicate literals
		this.literals = new Vector<Integer>();
		this.literals.addAll(s);
	}

	/**
	 * Checks whether this clause would be true under any assignment e.x (v[1])
	 * && (!v[1])
	 * 
	 * @return
	 */
	public Boolean isTriviallyTrue() {
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

	public boolean unpureSymbol(int symbol) {
		for (int i = 0, isize = literals.size(); i < isize; i++) {
			if (((Integer) literals.elementAt(i)).intValue() == -symbol) {
				return true;
			}
		}
		return false;
	}

}
