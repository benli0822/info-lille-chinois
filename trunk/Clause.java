package com.troissat;

import java.util.ArrayList;

public class Clause {
	
	private ArrayList<Litteral> listeLitteral;
	
	
	/* pour chaque Litteral, on utilise une boolean pour savoir si il y a un ! avant le litteral*/
	/* Si il y a un ! le valeur de boolean est true*/
	
	public Clause(Litteral l1, Litteral l2, Litteral l3,boolean lb1,boolean lb2,boolean lb3)
	{
		this.listeLitteral = new ArrayList<Litteral>();
	

		this.listeLitteral.add(l1);
		this.listeLitteral.add(l2);
		this.listeLitteral.add(l3);
		
		if(lb1){
			this.listeLitteral.get(0).setValeur( !this.listeLitteral.get(0).isValeur());
		}
		
		if(lb2){
			this.listeLitteral.get(1).setValeur( !this.listeLitteral.get(1).isValeur());
		}
		if(lb3){
			this.listeLitteral.get(2).setValeur( !this.listeLitteral.get(2).isValeur());
		}
		
	}
	
	public boolean calculerVrai(){
		return (this.listeLitteral.get(0).isValeur() ||this.listeLitteral.get(1).isValeur() ||this.listeLitteral.get(2).isValeur());
	}
	
}

