package com.troissat;

public class Litteral {
	private int id;
	private boolean valeur;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isValeur() {
		return valeur;
	}

	public void setValeur(boolean valeur) {
		this.valeur = valeur;
	}

	
	
	public Litteral(int id, boolean valeur){
		this.id = id;
		this.valeur = valeur;
	}
	public Litteral(int id){
		this.id = id; 
	}
	
	
	
}
