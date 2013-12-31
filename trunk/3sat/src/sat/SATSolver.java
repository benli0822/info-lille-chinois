package sat;

/**
 * This simply runs DPLL on a specified instance contained in a file. Depending
 * on user input it creates a SAT Instance based on either an existing file or
 * generates a random file.
 * 
 */
class SATSolver {

	public static void main(String[] args) throws Exception {
		System.out.println("** Welcome to the SAT Solver **");

		if (args.length < 1) {
			System.out.println("Parameters should be <filename>|[varnum rate]");
			System.exit(-1);
		} else if (args.length == 2) {
			String[] nargs = { "" + args[0], "" + args[1], "temp.cnf", };
			SatGenerator.main(nargs);
			args[0] = "temp.cnf";
		}

		Instance instance = new Instance(args[0]);

		System.out.println("Used CNF file: " + args[1]);

		DPLLSolver dpll = new DPLLSolver(instance);
		long t0 = System.currentTimeMillis();
		if (dpll.solve(true).result) {
			System.out.println("Took " + (System.currentTimeMillis() - t0)
					+ " ms");
			System.out.println("The input was SATISFIABLE");
			System.exit(0);
		} else {
			System.out.println("Took " + (System.currentTimeMillis() - t0)
					+ " ms");
			System.out.println("The input was UNSATISFIABLE");
			System.exit(0);
		}

	}

}