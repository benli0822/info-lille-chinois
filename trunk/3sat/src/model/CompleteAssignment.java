package model;

import java.util.Vector;

import sat.Clause;
import sat.Formule;

public class CompleteAssignment implements Assignment {
	public boolean[] isAssigned;
	public boolean[] values;

	public int numVars;
	private Formule formule;

	public CompleteAssignment(Formule formule) {
		this.formule = formule;
		this.numVars = formule.numVars;
		this.isAssigned = new boolean[this.numVars + 1]; // v0 unused
		this.values = new boolean[this.numVars + 1];

		for (int i = 1; i <= this.numVars; i++) {
			this.isAssigned[i] = false;
		}
	}

	public void compelteAssignment(boolean[] list) {
		if (list.length != this.values.length - 1)
			throw new IllegalArgumentException(
					"Illegal input random assignment!");
		for (int i = 1; i < this.values.length; i++) {
			addAssignment(i, list[i - 1]);
		}
	}

	public boolean calculerVrai() {
		int numClause = this.formule.getNumClause();
		boolean res[] = new boolean[numClause];
		for (int i = 0; i < numClause; i++) {
			Clause clause = this.formule.getListClause().get(i);
			Vector<Integer> literals = clause.getLiterals();
			for (Integer id : literals) {
				if (isLiteralTrue(id)) {
					res[i] = res[i] || true;
				} else
					res[i] = res[i] || false;
			}
		}
		
		boolean result = true;
		for (int i = 0; i < numClause; i++) {
			result = res[i] && result;
		}
		
		return result;
	}

	/**
	 * Adds an assignment to the partial assignment
	 * 
	 * @param variable
	 * @param value
	 */
	@Override
	public void addAssignment(int variable, boolean value) {
		// TODO Auto-generated method stub
		if (isAssigned[variable]) {
			throw new IllegalArgumentException(
					"Assigning to an already assigned variable");
		}

		isAssigned[variable] = true;
		values[variable] = value;
	}

	@Override
	public boolean isLiteralTrue(int literal) {
		// TODO Auto-generated method stub
		if (literal > 0) {
			if (isAssigned[literal]) {
				if (!values[literal])
					return true;
			}
			return false;
		} else {
			literal = literal * (-1);
			if (isAssigned[literal]) {
				if (values[literal])
					return true;
			}
			return false;
		}
	}

	@Override
	public boolean isLiteralFalse(int literal) {
		// TODO Auto-generated method stub
		if (isLiteralTrue(literal))
			return false;
		else
			return true;
	}

	@Override
	public boolean isUnAssigned(int variable) {
		// TODO Auto-generated method stub
		return !isAssigned[variable];
	}

}
