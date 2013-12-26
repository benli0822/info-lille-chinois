package model;

public interface Assignment {
	
	/**
	 * Interface of assignment for literal
	 * There will exist two possibilities:
	 * 	1.	Complete assignment for violent test, 
	 * 		usually verified with small input size, 
	 * 		or small number of literals,
	 * 		but also exist great limitation, can't handle big problem
	 * 	2.	Partial assignment for DPLL algorithm
	 * 			
	 */
	
	public void addAssignment(int variable, boolean in_value);
	
	public boolean isLiteralTrue(int literal);
	
	public boolean isLiteralFalse(int literal);
	
	public boolean isUnAssigned(int variable);
}
