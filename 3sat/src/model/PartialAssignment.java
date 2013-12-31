package model;

import sat.Instance;

/**
 * This class represents the model/partial assignment used in the DPLL algorithm
 *
 */
public class PartialAssignment
{
	protected boolean[]	isAssigned; // an entry for each variable, true implies that the variable has been assigned a value
	protected boolean[]	values;		// for each true entry in isAssigned array, its corresponding entry here will
									//give the value  assigned to the variable
	protected int numVars;
	private Instance instance;

	/**
	 * Creates an empty assignment for the given
	 * SAT instance
	 * @param ins
	 */
	public PartialAssignment(Instance ins){
		instance = ins;
		numVars = ins.num_vars;
		isAssigned = new boolean[numVars+1]; //  num_vars+1, as the 0 place is left unused
		values 	= new boolean[numVars+1];

		//initially the model will be empty without any assignment
		for(int i=1;i<=numVars;i++){
			isAssigned[i]=false;
		}
	}

	/**
	 * Copy constructor, creates itself as a clone of a previous partial assignment
	 * @param m the part. assignment to clone
	 */
	public PartialAssignment(PartialAssignment m){
		numVars = m.numVars;
		isAssigned = new boolean[numVars+1]; //  num_vars+1, as the 0 place is left unused
		values 	= new boolean[numVars+1];
		this.instance = m.instance;

		for(int i=1;i<=numVars;i++){
			if(!m.isUnAssigned(i)){
				isAssigned[i]=true;
				if(m.isLiteralTrue(i)){
					values[i]=true;
				}
				else{
					values[i]=false;
				}
			}
			else{
				isAssigned[i]=false;
			}
		}
	}
	
	/**
	 * Adds an assignment to the partial assignment
	 * @param variable the variable to set
	 * @param in_value the value to set the variable to
	 */
	public void addAssignment(int variable, boolean in_value){
		if(isAssigned[variable]){
			throw new IllegalArgumentException("Assigning to an already assigned variable");
		}

		isAssigned[variable]=true;
		values[variable]=in_value;
	}

	/**
	 * Determines whether the given literal is true under the current model
	 * @param literal a literal(not a variable) to check
	 * @return true if the literal is true, false if its false OR if it is unsatisfied
	 */
	public boolean isLiteralTrue(int literal){
		//return true is the input literal is satisfied by the model
		if(literal>0){
			//positive literal
			if(isAssigned[literal]){
				if(values[literal])
					return true;
			}
			return false;
		}
		else{
			//negative literal
			literal=literal*(-1);
			if(isAssigned[literal]){
				if(!values[literal])
					return true;
			}
			return false;
		}

	}

	/**
	 * Determines whether the given literal is false under the current model
	 * @param literal Takes a literal(not a variable) as input
	 * @return true if the literal is false, false otherwise
	 */
	public boolean isLiteralFalse(int literal){
		//return true is the input literal is unsatisfied by the model
		if(literal>0){
			//positive literal
			if(isAssigned[literal]){
				if(!values[literal])
					return true;
			}
			return false;
		}
		else{
			//negative literal
			literal=literal*(-1);
			if(isAssigned[literal]){
				if(values[literal])
					return true;
			}
			return false;
		}
	}
	
	/**
	 * Determines whether or not the given variable is unassigned in the 
	 * partial assignment
	 * @param variable
	 * @return true if the variable is unassigned, false otherwise
	 */
	public boolean isUnAssigned(int variable){
		return !isAssigned[variable];
	}

}

