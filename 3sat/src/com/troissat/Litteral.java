package com.troissat;

public class Litteral {

	private boolean valeur;
	private int id;

	public Litteral(int id) {
		this.id = id;
	}

	public Litteral(int id, boolean valeur) {
		this.id = id;
		this.valeur = valeur;
	}

	public boolean isValeur() {
		return valeur;
	}

	public void setValeur(boolean valeur) {
		this.valeur = valeur;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
