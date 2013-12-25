package com.troissat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DPLL {
	/*
	 * F’ = propager_clauses_unitaires(F) Si F’ contient une clause vide (donc
	 * falsifiée), retourner Faux F’’ = propager_littéraux_purs(F’) Si F’’ est
	 * la formule vide, retourner Vrai Choisir un littéral l de F’’ et retourner
	 * DPLL(F’’ ∧ l) || DPLL(F’’ ∧ ¬l)
	 */

	private Formule f;

	// le constructeur prend un formule comme l'entree
	public DPLL(Formule f) {
		this.f = f;
	}

	// si'l existe une clause vide, retourne -1
	public int existeVide(Formule formule) {
		ArrayList<Clause> listClause = formule.getListClause();
		for (Clause c : listClause) {
			if (c.getMapLitteral() == null)
				return -1;
		}
		return 0;
	}

	// find all unit litteral
	public Formule propager_clauses_unitaires(Formule formule) {
		ArrayList<Clause> listClause = formule.getListClause();
		ArrayList<Clause> newListClause = new ArrayList<Clause>();
		for (Clause c : listClause) {
			Map<Litteral, Boolean> newClause = new HashMap<Litteral, Boolean>();
			if (c.getMapLitteral().size() == 1) {
				for (Map.Entry<Litteral, Boolean> entry : c.getMapLitteral()
						.entrySet()) {
					newClause.put(entry.getKey(), entry.getValue());
				}
				Clause newc = new Clause(newClause);
				newListClause.add(newc);
			}
		}
		Formule newf = new Formule(newListClause);
		return newf;
	}

	// find all pure litteral
	public Formule propager_clauses_purs(Formule formule) {
		ArrayList<Clause> listClause = formule.getListClause();
		Map<String, Integer> purLitteral = new HashMap<String, Integer>();
		for (Clause c : listClause) {
			for (Map.Entry<Litteral, Boolean> entry : c.getMapLitteral()
					.entrySet()) {
				Litteral l = entry.getKey();
				Boolean not = entry.getValue();
				// 现在的问题是，要找出pure的litteral，满足的条件必须是，l出现的次数里面，not相互不能抵消
			}
		}
		return null;
	}

	public static void main(String[] args) {

	}
}
