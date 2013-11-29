package com.troissat;

public class Clause {
	
	private boolean litteral[];
	
	public Clause(boolean litteral[])
	{
		this.litteral = litteral;
	}
	
	public boolean calculerVrai(){
		return (litteral[0])||(litteral[1])||(litteral[2]) ;
	}
	
}
