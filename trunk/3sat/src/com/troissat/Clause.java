package com.troissat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Clause {

	// private Litteral[] listeLitteral;
	//
	// /*
	// * pour chaque Litteral, on utilise une boolean pour savoir si il y a un !
	// * avant le litteral
	// */
	// /* Si il y a un ! le valeur de boolean est true */
	//
	//
	// public Clause(Litteral l1, Litteral l2, Litteral l3, boolean lb1,
	// boolean lb2, boolean lb3) {
	// this.listeLitteral[0] = l1;
	// this.listeLitteral[1] = l2;
	// this.listeLitteral[2] = l3;
	// if (lb1) {
	// this.listeLitteral[0].setValeur(!this.listeLitteral[0].isValeur());
	// }
	// if (lb2) {
	// this.listeLitteral[1].setValeur(!this.listeLitteral[1].isValeur());
	// }
	// if (lb3) {
	// this.listeLitteral[2].setValeur(!this.listeLitteral[2].isValeur());
	// }
	//
	// }
	//
	// public boolean calculerVrai() {
	// return (this.listeLitteral[0].isValeur()
	// || this.listeLitteral[1].isValeur() || this.listeLitteral[2]
	// .isValeur());
	// }

	private Map<Litteral, Boolean> mapLitteral = new HashMap<Litteral, Boolean>();

	public Clause(Litteral l1, Litteral l2, Litteral l3, boolean not1,
			boolean not2, boolean not3) {
		this.mapLitteral.put(l1, not1);
		this.mapLitteral.put(l2, not2);
		this.mapLitteral.put(l3, not3);
	}

	public boolean calculerVrai() {
		Iterator<Litteral> it = this.mapLitteral.keySet().iterator();
		Litteral key = (Litteral) it.next(); // key
		boolean res = key.isValeur();
		if (this.mapLitteral.get(key))
			;
		else
			res = !res;
		while (it.hasNext()) {
			Litteral key1 = (Litteral) it.next(); // key
			if (this.mapLitteral.get(key1))
				res = key1.isValeur() || res;
			else
				res = (!key1.isValeur()) || res;
		}
		return res;
	}
	
//	private ArrayList<Litteral> listeLitteral;
//	
//	
//	/* pour chaque Litteral, on utilise une boolean pour savoir si il y a un ! avant le litteral*/
//	/* Si il y a un ! le valeur de boolean est true*/
//	
//	public Clause(Litteral l1, Litteral l2, Litteral l3,boolean lb1,boolean lb2,boolean lb3)
//	{
//		this.listeLitteral = new ArrayList<Litteral>();
//	
//
//		this.listeLitteral.add(l1);
//		this.listeLitteral.add(l2);
//		this.listeLitteral.add(l3);
//		
//		if(lb1){
//			this.listeLitteral.get(0).setValeur( !this.listeLitteral.get(0).isValeur());
//		}
//		
//		if(lb2){
//			this.listeLitteral.get(1).setValeur( !this.listeLitteral.get(1).isValeur());
//		}
//		if(lb3){
//			this.listeLitteral.get(2).setValeur( !this.listeLitteral.get(2).isValeur());
//		}
//		
//	}
//	
//	public boolean calculerVrai(){
//		return (this.listeLitteral.get(0).isValeur() ||this.listeLitteral.get(1).isValeur() ||this.listeLitteral.get(2).isValeur());
//	}
	
}
