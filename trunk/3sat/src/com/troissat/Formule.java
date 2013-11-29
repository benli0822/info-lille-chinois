package com.troissat;

import java.util.ArrayList;

public class Formule {
	
	private ArrayList<Clause> listClause;

	public Formule(ArrayList<Clause> listClause)
	{
		this.listClause = listClause;
	}
	
	public boolean calculerVrai(){
		int n = this.listClause.size();
		boolean bool = false;
		
		for(int i=0; i<n; i++)
		{
			bool = (this.listClause.get(i).calculerVrai()) && bool;
		}
		
		return bool;
	}
}
