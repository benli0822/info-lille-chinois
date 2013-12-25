package com.troissat;

public class Litteral {

	private boolean valeur;
	private String id;

	public Litteral(String id) {
		this.id = id;
	}

	public Litteral(String id, boolean valeur) {
		this.id = id;
		this.valeur = valeur;
	}

	public boolean isValeur() {
		return valeur;
	}

	public void setValeur(boolean valeur) {
		this.valeur = valeur;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
