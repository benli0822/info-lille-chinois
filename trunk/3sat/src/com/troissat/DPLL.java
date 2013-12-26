package com.troissat;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

public class DPLL {
	/*
	 * F’ = propager_clauses_unitaires(F) Si F’ contient une clause vide (donc
	 * falsifiée), retourner Faux F’’ = propager_littéraux_purs(F’) Si F’’ est
	 * la formule vide, retourner Vrai Choisir un littéral l de F’’ et retourner
	 * DPLL(F’’ ∧ l) || DPLL(F’’ ∧ ¬l)
	 */

	// si'l existe une clause vide, retourne -1
	public boolean existeVide(Formule formule) {
		Vector<Clause> listClause = formule.getListClause();
		for (Clause c : listClause) {
			if (c.getLiterals() == null)
				return true;
		}
		return false;
	}

	// find all unit litteral
	public Formule propager_clauses_unitaires(Formule formule) {
		Vector<Clause> listClause = formule.getListClause();
		Vector<Clause> newListClause = new Vector<Clause>();
		int newNumVar = 0;
		for (Clause c : listClause) {
			if (c.getLiterals().size() == 1) {
				newListClause.add(c);
				newNumVar++;
			}
		}
		Formule newf = new Formule(newListClause, newNumVar);
		return newf;
	}

	public boolean isFormVide(Formule formule) {
		if (formule.getListClause() == null)
			return true;
		else
			return false;
	}

	// find all pure litteral
	public Formule propager_clauses_purs(Formule formule) {
		HashSet<Integer> listLiteral = new HashSet<Integer>();
		Vector<Clause> listClause = formule.getListClause();
		Vector<Clause> newClause = new Vector<Clause>();

		for (Clause c : listClause) {
			listLiteral.addAll(c.getLiterals());
			// 现在的问题是，要找出pure的litteral，满足的条件必须是，l出现的次数里面，not相互不能抵消
		}

		int counter = 0;
		for (Clause c : listClause) {
			for (Iterator<Integer> iter = listLiteral.iterator(); iter
					.hasNext();) {
				int symbol = iter.next();
				if (!c.unpureSymbol(symbol)) {
					Vector<Integer> pureLiterals = new Vector<Integer>();
					pureLiterals.add(symbol);
					Clause newC = new Clause(pureLiterals);
					newClause.add(newC);
					counter++;
				}
			}
		}
		Formule newf = new Formule(newClause, counter);
		return newf;
	}

	public boolean DPLLSolver(Formule formule) {
		Formule firstFormule = propager_clauses_unitaires(formule);
		if (existeVide(firstFormule)) {
			return false;
		} else {
			Formule secondFormule = propager_clauses_purs(firstFormule);

			if (isFormVide(secondFormule)) {
				return true;
			} else {
				Vector<Clause> listClause = secondFormule.getListClause();
				Clause choix = listClause.get(0);
				Vector<Integer> choixLiteral = choix.getLiterals();

				int choixSymbol = choixLiteral.get(0);
				Vector<Integer> newLiteral1 = new Vector<Integer>();
				newLiteral1.add(choixSymbol);
				Vector<Integer> newLiteral2 = new Vector<Integer>();
				newLiteral2.add(choixSymbol * (-1));
				Clause newClause1 = new Clause(newLiteral1);
				Clause newClause2 = new Clause(newLiteral2);
				Vector<Clause> newListClause1 = listClause;
				newListClause1.add(newClause1);
				Vector<Clause> newListClause2 = listClause;
				newListClause2.add(newClause2);
				return DPLLSolver(new Formule(newListClause1,
						secondFormule.getNumVars() + 1))
						|| DPLLSolver(new Formule(newListClause2,
								secondFormule.getNumVars() + 1));
			}

		}
	}

	public static void main(String[] args) {

	}
}
