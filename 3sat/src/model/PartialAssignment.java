package model;

import com.troissat.Formule;

public class PartialAssignment implements Assignment {

	public boolean[] isAssigned;
	public boolean[] values;

	public int numVars;
	
	public PartialAssignment(Formule formule) {
		this.numVars = formule.numVars;
		this.isAssigned = new boolean[this.numVars + 1]; // v0 unused
		this.values = new boolean[this.numVars + 1];

		for (int i = 1; i <= this.numVars + 1; i++) {
			this.isAssigned[i] = false;
		}
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
		if(isLiteralTrue(literal))
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
