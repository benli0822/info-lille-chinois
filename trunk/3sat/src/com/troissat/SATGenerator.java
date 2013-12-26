package com.troissat;

import java.io.PrintStream;
import java.util.Random;

public class SATGenerator {

	/**
	 * Generates a random SAT instance as a text file
	 * 
	 * @param num_vars
	 *            the number of variables
	 * @param r
	 *            the ratio num_clauses/num_vars
	 * @param pOut
	 */
	public static void generateCNFFile(int num_vars, double r, PrintStream pOut) {
		int num_clauses = (int) (num_vars * r);

		pOut.println("c CNF");
		pOut.println("p cnf " + num_vars + " " + num_clauses);

		Random rg = new Random();

		for (int i = 0; i < num_clauses; i++) {
			for (int j = 0; j < 3; j++) {
				int var = rg.nextInt(num_vars) + 1;
				if (rg.nextBoolean()) {
					pOut.print(var + " ");
				} else {
					pOut.print("-" + var + " ");
				}
			}

			pOut.println("0 ");
			pOut.flush();
		}
	}
}
