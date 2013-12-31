package sat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Vector;

import model.PartialAssignment;

class DPLLSolver {
	int num_vars;

	int num_clauses;

	Instance satInstance;

	public DPLLSolver(Instance inputInstance) {
		satInstance = inputInstance;
		num_vars = satInstance.getNumVars();
		num_clauses = satInstance.getNumClauses();
	}

	public Instance getInstance() {
		return satInstance;
	}

	/**
	 * Solves the SAT instance given in the constructor.
	 * 
	 * @return true if the SAT instance is satisfiable, false otherwise.
	 */
	public Stats solve(boolean outputToConsole) {
		pure = 0;
		unit = 0;
		splits = 0;
		total = 0;
		LinkedHashSet<Integer> symbols = new LinkedHashSet<Integer>();
		for (int i = 1; i <= num_vars; i++) {
			// NOTE: 0 is not entered into the array as there is no variable
			// indexed by it
			symbols.add(i);
		}

		PartialAssignment model = new PartialAssignment(satInstance);

		Vector clauses = satInstance.getClauses();

		boolean result = DPLL(clauses, symbols, model);
		if (outputToConsole) {
			System.out.println("# of pure symbols found during search : "
					+ pure);
			System.out.println("# of unit clauses found during search : "
					+ unit);
			System.out.println("# of times split/branched on variable "
					+ splits);
			System.out.println("Total # of calls to DPLL " + total);
		}
		Stats res = new Stats();
		res.pure = pure;
		res.result = result;
		res.unit = unit;
		res.splits = splits;
		res.total = total;
		return res;
	}

	int pure = 0;
	int unit = 0;
	int splits = 0;
	int total = 0;

	/**
	 * The recursive part of the DPLL algorithm.
	 * 
	 * @param clauses
	 * @param symbols
	 * @param model
	 * @return
	 */
	private boolean DPLL(Vector<Clause> clauses,
			LinkedHashSet<Integer> symbols, PartialAssignment model) {
		total++;
		if (areAllClausesSatisfied(clauses, model)) {
			return true;
		}

		if (anyClauseFalse(clauses, model)) {
			return false;
		}

		SymbolValuePair puresymbolpair;

		puresymbolpair = findPureSymbol(symbols, clauses, model);
		if (puresymbolpair != null) {
			// found a pure symbol
			pure++;
			symbols.remove(new Integer(puresymbolpair.getVar()));
			model.addAssignment(puresymbolpair.getVar(),
					puresymbolpair.getValue());
			return DPLL(clauses, symbols, model);
		}

		SymbolValuePair unitSymbol = findUnitClause(symbols, clauses, model);
		if (unitSymbol != null) {
			unit++;
			model.addAssignment(unitSymbol.getVar(), unitSymbol.getValue());
			symbols.remove(new Integer(unitSymbol.getVar()));
			return DPLL(clauses, symbols, model);
		}

		int P = chooseAndRemoveSplitSymbol(clauses, symbols, model);

		LinkedHashSet new_symbols = (LinkedHashSet) symbols.clone();
		splits++;

		PartialAssignment true_model = new PartialAssignment(model);
		PartialAssignment false_model = new PartialAssignment(model);
		true_model.addAssignment(P, true);
		false_model.addAssignment(P, false);
		return (DPLL(clauses, symbols, true_model) || DPLL(clauses,
				new_symbols, false_model));
	}

	/**
	 * Looks for a pure symbol
	 * 
	 * @param symbols
	 * @param clauses
	 * @param model
	 * @return
	 */
	private SymbolValuePair findPureSymbol(LinkedHashSet symbols,
			Vector<Clause> clauses, PartialAssignment model) {
		Iterator ii = symbols.iterator();
		SYMBOL_LOOP: while (ii.hasNext()) {
			// dont know if this is needed but doesnt hurt to check an extra
			// time.
			// (since symbols that are assigned should be removed from the list
			// already.
			int symbol = ((Integer) ii.next()).intValue();
			if (model.isUnAssigned(symbol)) // should always be true, because
											// symbols is supposed to only have
											// unasinged symbols.
			{
				boolean foundSymbolFirstTime = false;
				for (Clause c : clauses) {
					if (!c.isSatisfied(model)) {
						if (!foundSymbolFirstTime) {
							int index = c.contains(symbol);
							if (index != -1) {
								foundSymbolFirstTime = true;
								symbol = c.getLiteral(index); // could be
																// negated.
							}
						}

						if (foundSymbolFirstTime && c.unpureSymbol(symbol)) {
							continue SYMBOL_LOOP;
						}
					}
				}

				if (foundSymbolFirstTime) {
					if (symbol > 0) {
						return new SymbolValuePair(symbol, true);
					} else {
						return new SymbolValuePair(-symbol, false);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Looks for a unit clause, if none is found it should return null
	 * 
	 * @param symbols
	 *            the list of free symbols
	 * @param clauses
	 * @param model
	 *            the partial assignment
	 * @return null or a SymbolValuePair giving the symbol and its value
	 */
	private SymbolValuePair findUnitClause(LinkedHashSet symbols,
			Vector<Clause> clauses, PartialAssignment model) {
		for (Clause c : clauses) {
			if (c.countFreeLiterals(model) == 1) {
				// ok we know know that there is one unassigned litteral
				// in this clause so search further.
				boolean IsNotUnitClause = false;
				boolean IsUnitClauseSymbolNegated = false;
				boolean IsCurrentSymbolNegated = false;
				int CurrentSymbol = 0;
				int UnitClauseSymbol = 0;
				for (Integer P : c.literals) {
					if (P > 0) {
						CurrentSymbol = P;
						IsCurrentSymbolNegated = false;
					} else {
						CurrentSymbol = (P * -1);
						IsCurrentSymbolNegated = true;
					}
					if (model.isUnAssigned(CurrentSymbol)) {
						UnitClauseSymbol = CurrentSymbol;
						IsUnitClauseSymbolNegated = IsCurrentSymbolNegated;
					} else if (((model.isLiteralFalse(CurrentSymbol)) && (IsCurrentSymbolNegated))
							|| ((model.isLiteralTrue(CurrentSymbol)) && (!IsCurrentSymbolNegated))) {
						// another litteral in this clause is true so dont
						// search further.
						IsNotUnitClause = true;
						break;
					}
				}
				if (!IsNotUnitClause) {
					// found unit clause.
					SymbolValuePair UnitSymbol;
					UnitSymbol = new SymbolValuePair(UnitClauseSymbol,
							!IsUnitClauseSymbolNegated);
					return UnitSymbol;
				}
			}
		}
		return null;
	}

	/**
	 * Chooses the variable to branch/split on. the first variable in the symbol
	 * list. NOTE: This method (should) also remove the chosen variable from
	 * symbols.
	 * 
	 * @param clauses
	 * @param symbols
	 * @param model
	 * @return the variable to split on
	 */
	private int chooseAndRemoveSplitSymbol(Vector<Clause> clauses,
			LinkedHashSet<Integer> symbols, PartialAssignment model) {
		return maxFreeChooseAndRemoveSplitSymbol(clauses, symbols, model);
	}

	private int maxFreeChooseAndRemoveSplitSymbol(Vector<Clause> clauses,
			LinkedHashSet<Integer> symbols, PartialAssignment model) {
		int maxNum = 0;
		Integer maxSym = 0;
		HashMap<Integer, Integer> countingHash = new HashMap<Integer, Integer>();
		for (Clause c : clauses) {
			if (!c.isTriviallyTrue()) {
				for (Integer sym : c.returnFreeLiterals(model)) {
					if (countingHash.containsKey(sym)) {
						countingHash.put(sym, countingHash.get(sym) + 1);
					} else {
						countingHash.put(sym, 1);
					}
					if (countingHash.get(sym) > maxNum) {
						maxNum = countingHash.get(sym);
						maxSym = sym;
					}
				}
			}
		}

		symbols.remove(maxSym);

		return maxSym;
	}

	/**
	 * Checks whether all clauses are satisfied under the current assignment.
	 * 
	 * @param clauses
	 * @param model
	 * @return true if all clauses are satisfied, false otherwise
	 */
	private boolean areAllClausesSatisfied(Vector clauses,
			PartialAssignment model) {
		// returns true when all the clauses are satisfied by the model
		// remember clause not satisfied by a model does not mean the model
		// cannot be extended, such that the clause is satisfied
		int size = clauses.size();
		for (int i = 0; i < size; i++) {
			Vector literals = (Vector) ((Clause) clauses.elementAt(i))
					.getLiterals();
			int clause_size = literals.size();
			boolean clause_not_satisfied = true;
			for (int j = 0; j < clause_size; j++) {
				int literal = ((Integer) literals.elementAt(j)).intValue();
				if (model.isLiteralTrue(literal)) {
					clause_not_satisfied = false;
					break;
				}
			}

			if (clause_not_satisfied)
				return false;
		}
		return true;
	}

	/**
	 * Checks whether any clause is false under the given model
	 * 
	 * @param clauses
	 * @param model
	 * @return true if a clause was found to be false, false otherwise
	 */
	private boolean anyClauseFalse(Vector clauses, PartialAssignment model) {
		// checks whether all the literals in a clause are unsatisified
		int size = clauses.size();
		for (int i = 0; i < size; i++) {
			Vector literals = (Vector) ((Clause) clauses.elementAt(i))
					.getLiterals();
			int clause_size = literals.size();
			boolean clauseFalse = true;

			// if we find any literal(not variable) that is not false
			// this clause is not false, it is either satisfied or still
			// have free variables
			for (int j = 0; j < clause_size; j++) {
				int literal = ((Integer) literals.elementAt(j)).intValue();
				if (!model.isLiteralFalse(literal)) {
					clauseFalse = false;
					break;
				}
			}

			if (clauseFalse)
				return true;

		}
		return false;
	}

}

class Stats {
	public int pure = 0;
	public int unit = 0;
	public int splits = 0;
	public int total = 0;
	public boolean result = false;
}
