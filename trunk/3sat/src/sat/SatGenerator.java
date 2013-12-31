package sat;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * Various utility functions for generating random SAT instances in CNF
 */
class SatGenerator {

	public static Random rg = new Random();

	/**
	 * Takes three command line parameters: num_vars and "r" the m/n ratio and a
	 * filename Outputs a corresponding random instance to given file or to the
	 * console if no file specified
	 */
	public static void main(String[] args) throws Exception {
		int num_vars = Integer.parseInt(args[0]);
		double r = Double.parseDouble(args[1]);

		System.out.println("Creating file: " + args[2]);
		if (args.length == 3)
			generateCNFFile(num_vars, r, new PrintStream(new FileOutputStream(
					args[2])));
		else
			generateCNFFile(num_vars, r, System.out);
	}

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

		pOut.println("c CNF generator v0.9");
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

	/**
	 * Generates a random SAT instance in memory
	 * 
	 * @param numVars
	 * @param numClauses
	 * @return SAT Instance
	 */
	public static Instance generateSATInstance(int numVars, int numClauses) {
		Vector clauses = new Vector(numClauses);
		Vector lits = new Vector(3);
		for (int i = 0; i < numClauses; i++) {
			lits.clear();
			for (int j = 0; j < 3; j++) {
				int var = rg.nextInt(numVars) + 1;
				if (rg.nextBoolean()) {
					lits.add(new Integer(var));
				} else {
					lits.add(new Integer(-1 * var));
				}
			}
			clauses.add(new Clause(lits));
		}
		Instance ins = new Instance(clauses, numVars);
		// System.out.println(clauses);
		return ins;
	}
}
