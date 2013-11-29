package com.troissat;

public class Clause {
	
	private Litteral[] listeLitteral;
	
	
	/* pour chaque Litteral, on utilise une boolean pour savoir si il y a un ! avant le litteral*/
	/* Si il y a un ! le valeur de boolean est true*/
	
	public Clause(Litteral l1, Litteral l2, Litteral l3,boolean lb1,boolean lb2,boolean lb3)
	{
		this.listeLitteral[0] = l1;
		this.listeLitteral[1] = l2;
		this.listeLitteral[2] = l3;
		if(lb1){
			this.listeLitteral[0].setValeur( !this.listeLitteral[0].isValeur() );
		}
		if(lb2){
			this.listeLitteral[1].setValeur( !this.listeLitteral[1].isValeur() );
		}
		if(lb3){
			this.listeLitteral[2].setValeur( !this.listeLitteral[2].isValeur() );
		}

	}
	
	public boolean calculerVrai(){
		return (this.listeLitteral[0].isValeur() ||this.listeLitteral[1].isValeur() ||this.listeLitteral[2].isValeur());
	}
	
}

