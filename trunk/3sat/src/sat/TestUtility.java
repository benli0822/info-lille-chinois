package sat;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * Runs various benchmarks etc. see main method for details.
 * 
 * @author petert
 */
public class TestUtility {
	/**
	 * If BENCHMARK is specified, DPLL will be tested on the "hard" instances in
	 * \benchmark . If VERIFY is specified the same will be done with the much
	 * faster resolved instances in verify(this can be used to verify that any
	 * changes you make to the DPLL algorithm are correct and doesnt result in
	 * invalid answers). (see runBenchmarkSuite method for details) Finally not
	 * specifying any paramaters will plot runtime for randomly generated SAT
	 * instances for DPLL. (see plotRuntimes method for more details).
	 * 
	 * @param args
	 * @throws IOException
	 */

	public static String separator = "/"; /* Folder separator at Linux: / */

	// public static String separator = "\\"; /* Folder separator at Windows: \
	// */

	public static void main(String[] args) throws IOException {
		if (args.length > 0 && args[0].equals("PLOT_UNSAT")) {
			double[] ratios = { 1.0f, 2.0f, 3.0f, 4.0f, 4.5f, 5.0f, 5.5f, 6.0f,
					6.5f, 7.0f, 7.5f };
			plotUnsat(20, ratios, 100);
		} else if (args.length > 0 && args[0].equals("PLOT_DPLL")) {
			plotRuntimesPregen("satinstances", false); // skips WalkSat
		} else if (args.length > 0 && args[0].equals("BENCHMARK")) {
			runBenchmarkSuite("." + separator + "benchmark" + separator,
					"benchmark");
		} else if (args.length > 0 && args[0].equals("VERIFY")) {
			runBenchmarkSuite("." + separator + "verify" + separator,
					"benchmark");
		} else {
			System.out
					.println("You should use one of the following parameters:");
			System.out
					.println("PLOT_UNSAT:\nGenerates random instances and writes a table with the ratio of unsatisfiable");
			System.out
					.println("PLOT_DPLL:\nPlots the runtimes/splits of DPLL on the pregen. data in the folder satinstances");
			System.out
					.println("VERIFY:\nRuns DPLL on the verification instances(use this to check any changes you make to the algorithm)");
		}
	}

	/**
	 * This will run the current implementation of the DPLL algorithm on the
	 * benchmark instances found in the indicated director, with the indicated
	 * filePrefix.
	 * 
	 * @param dirName
	 * @param filePrefix
	 * @throws IOException
	 */
	public static void runBenchmarkSuite(String dirName, String filePrefix)
			throws IOException {
		File dir = new File(dirName);
		if (!dir.exists()) {
			System.out.println(dir + " does not exist !?");
			System.exit(1);
		}
		String[] files = dir.list(new BenchmarkCNFFileFilter(filePrefix));

		long totalTimeOnSolving = 0;
		Arrays.sort(files);
		for (int i = 0; i < files.length; i++) {
			Instance ins = new Instance(dirName + separator + files[i]);
			System.out.println("Solving : " + files[i]);
			DPLLSolver solver = new DPLLSolver(ins);
			long t0 = System.currentTimeMillis();
			boolean result = solver.solve(false).result;
			long t1 = System.currentTimeMillis();
			boolean realResult = !files[i].contains("UNSAT");
			if (result != realResult) {
				System.out.println("Benchmark on " + files[i]
						+ " detected an error: Got result SAT=" + result
						+ " according to file name result shoudl be "
						+ realResult);
				System.exit(-1);
			}
			totalTimeOnSolving += (t1 - t0);
			System.out.println("-> Solved as SAT=" + result
					+ " (which is correct) in " + (t1 - t0) + " ms");

		}
		System.out.println("Benchmark completed, used " + totalTimeOnSolving
				+ " ms for solving");
	}

	/**
	 * Tests satisfiable pregenerated instances at different clause/var ratios,
	 * recording time required by DPLL , as well as the percentage of unsat
	 * instances generated at each clause/var ratio. The result is output into a file called
	 * timing_report_pregen.html which can be open by excel and imported by
	 * OpenOffice Calc.
	 */
	public static void plotRuntimesPregen(String folderName, boolean doWalkSat)
			throws IOException {
		FileWriter fw = new FileWriter("timing_report_pregen.html");
		fw.write("<html><head><body>");
		final String TR = "<tr>";
		final String eTR = "</tr>";
		final String TD = "<td>";
		final String eTD = "</td>";
		final String TABLE = "<table border = 2>";
		final String eTABLE = "</table>";
		fw.write(TABLE + "\n");
		fw.write(TR + TD + "Ratio" + eTD + TD + "Median. ms. for DPLL" + eTD
				+ TD + "Median. splits. for DPLL" + eTD + eTR);

		long start = System.currentTimeMillis();

		File folder = new File(folderName);
		System.out.println("Finding file to run test on");
		File[] files = folder.listFiles(new CNFFileFilter());
		System.out.println("Found " + files.length + " files");

		int numVars = -1;

		HashMap DPLLTimes = new HashMap();
		HashMap DPLLSplits = new HashMap();
		HashMap WSTimes = new HashMap();

		for (int i = 0; i < files.length; i++) {
			Instance ins = new Instance(files[i].getAbsolutePath());

			if (numVars == -1) {
				numVars = ins.num_vars;
			}
			if (ins.num_vars != ins.num_vars) {
				throw new IOException(
						"Pregen instances are for different number of variables!");
			}

			int numClauses = ins.initialClauses.size();

			System.gc();

			DPLLSolver p = new DPLLSolver(ins);
			long t0 = System.nanoTime();
			Stats s = p.solve(false);
			int splits = s.splits;
			boolean sat = s.result;
			long DPLLTime = System.nanoTime() - t0;
			if (!sat) {
				throw new IllegalArgumentException(
						"Pregenerated instances are supposed to be satisfiable!");
			} else {
				// DPLL runtime
				Object o = DPLLTimes.get(new Integer(numClauses));
				if (o == null) {
					System.out.println("\nFirst instance with "
							+ ins.initialClauses.size() + " clauses");
					Vector v = new Vector();
					v.add(new Long(DPLLTime));
					DPLLTimes.put(new Integer(numClauses), v);
				} else {
					Vector v = (Vector) o;
					v.add(new Long(DPLLTime));
				}
				// DPLL split count
				o = DPLLSplits.get(new Integer(numClauses));
				if (o == null) {
					Vector v = new Vector();
					v.add(new Long(splits));
					DPLLSplits.put(new Integer(numClauses), v);
				} else {
					Vector v = (Vector) o;
					v.add(new Long(splits));
				}

				// if walksat used, walksat runtime
				if (doWalkSat) {
					// This option unavailable in this version of
					// implementation.
				} else {
					o = WSTimes.get(new Integer(numClauses));
					if (o == null) { // dummy value if walksat not used
						Vector v = new Vector();
						v.add(new Long(-1));
						WSTimes.put(new Integer(numClauses), v);
					}
					System.out.println("DPLL: " + (DPLLTime / 1000000f));
				}
			}
		}
		Object[] okeys = (Object[]) DPLLTimes.keySet().toArray();
		Integer[] keys = new Integer[okeys.length];
		for (int k = 0; k < okeys.length; k++) {
			keys[k] = ((Integer) okeys[k]);
		}
		Arrays.sort(keys);
		System.out.println("Saw " + keys.length + " different ratios");
		long DPLLmedian = 0;
		long WSmedian = 0;
		long SplitsMedian = 0;
		for (int j = 0; j < keys.length; j++) {
			Vector dv = (Vector) DPLLTimes.get(keys[j]);
			Vector wv = (Vector) WSTimes.get(keys[j]);
			Vector dSplits = (Vector) DPLLSplits.get(keys[j]);
			Collections.sort(dv);
			Collections.sort(wv);
			Collections.sort(dSplits);
			DPLLmedian = ((Long) dv.get((dv.size() + 1) / 2 - 1)).longValue();
			WSmedian = ((Long) wv.get((wv.size() + 1) / 2 - 1)).longValue();
			SplitsMedian = ((Long) dSplits.get((dSplits.size() + 1) / 2 - 1))
					.longValue();
			String row = TR + TD + (keys[j].intValue() / (double) numVars)
					+ eTD + TD + (DPLLmedian / 1000000f) + eTD + TD
					+ SplitsMedian + eTD + eTR + "\n";
			row = row.replace(".", ",");
			fw.write(row);
			fw.flush();
		}

		fw.write(eTABLE + "\n");
		fw.write("</body></head></html>");
		System.out.println("Finished, took "
				+ (System.currentTimeMillis() - start)
				+ " ms, output file is timing_report_pregen.html");
		System.out.flush();
		fw.flush();
		fw.close();
	}

	/**
	 * Generates a lot of instances and count how big a ratio is unsatisfiable.
	 * 
	 */
	public static void plotUnsat(int numVars, double[] ratios, int numInstances)
			throws IOException {
		FileWriter fw = new FileWriter("unsat_report_" + numVars + ".html");
		fw.write("<html><head><body>");
		final String TR = "<tr>";
		final String eTR = "</tr>";
		final String TD = "<td>";
		final String eTD = "</td>";
		final String TABLE = "<table border = 2>";
		final String eTABLE = "</table>";
		fw.write(TABLE + "\n");
		fw.write(TR + TD + "Ratio" + eTD + TD + "Median. ms. for DPLL" + eTD
				+ TD + "Avg. ms. for DPLL" + eTD + TD
				+ "unsat ratio among generated" + eTD + eTR);
		long start = System.currentTimeMillis();
		for (int i = 0; i < ratios.length; i++) {
			int numClauses = (int) (numVars * ratios[i]);
			System.out.println("Creating instances with " + numClauses
					+ " clauses and " + numVars + " variables , ratio: "
					+ (numClauses / (double) numVars));
			System.out.flush();
			int satCount = 0;
			int count = 0;
			int unSatCount = 0;
			long timeSpentDPLL = 0;
			double[] DPLLTimes = new double[numInstances];

			while (count < numInstances) {
				DPLLSolver p = new DPLLSolver(SatGenerator.generateSATInstance(
						numVars, numClauses));
				System.gc();
				count++;
				long t0 = System.nanoTime();
				boolean solved = p.solve(false).result;
				long timeDPLL = System.nanoTime() - t0;
				if (solved) {
					satCount++;
					timeSpentDPLL += timeDPLL;
					DPLLTimes[satCount - 1] = timeDPLL;
				} else {
					unSatCount++;
				}
			}

			Arrays.sort(DPLLTimes);

			double DPLLmedian = DPLLTimes[(numInstances + 1) / 2 - 1];

			double DPLLavg = timeSpentDPLL / (numInstances * (double) 1000000);

			String row = TR + TD + ratios[i] + eTD + TD
					+ (DPLLmedian / 1000000) + eTD + TD + DPLLavg + eTD + TD
					+ (unSatCount) / (double) (count) + eTD + eTR + "\n";
			// to make import in danish locale office excel/openoffice calc
			// easier
			// if you need '.' as decimal seperator, remove the replace call
			fw.write(row.replace('.', ','));
			fw.flush();
			System.out.flush();
		}
		fw.write(eTABLE + "\n");
		fw.write("</body></head></html>");
		System.out.println("Finished, took "
				+ (System.currentTimeMillis() - start) + " ms");
		System.out.flush();
		fw.flush();
		fw.close();
	}

	static class BenchmarkCNFFileFilter implements FilenameFilter {
		public BenchmarkCNFFileFilter(String prefix) {
			this.prefix = prefix;
		}

		String prefix;

		public boolean accept(File dir, String name) {
			if (name.startsWith(prefix) && name.endsWith("SAT.cnf")) { // files
																		// *UNSAT.cnf
																		// also
																		// ends
																		// with
																		// SAT.CNF
																		// :)
				return true;
			} else {
				return false;
			}
		}
	}

	static class CNFFileFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return !name.equalsIgnoreCase("CVS");

			/*
			 * if(name.endsWith(".cnf")){ return true; } else{ return false; }
			 */
		}
	}

	/**
	 * Writes an instance to file
	 * 
	 * @param ins
	 * @param fileName
	 * @throws IOException
	 */
	public static void writeInstance(Instance ins, String fileName)
			throws IOException {
		FileWriter fw = new FileWriter(fileName);
		Vector clauses = ins.initialClauses;
		// System.out.println(clauses);
		Iterator it = clauses.iterator();
		StringBuffer sbuf = new StringBuffer();
		fw.write("c CNF generator v0.9\n");
		fw.write("p cnf " + ins.num_vars + " " + ins.initialClauses.size()
				+ "\n");
		while (it.hasNext()) {
			Clause c = (Clause) it.next();
			// System.out.println(c.initLits);
			for (int i = 0; i < c.initLits.size(); i++) {
				Integer lit = (Integer) c.initLits.get(i);
				sbuf.append(lit + " ");
			}
			sbuf.append("0\n");
		}
		fw.write(sbuf.toString());
		fw.close();
		// System.out.println("Wrote " + fileName);
	}

}

class SamplePoint {
	public int samples;
	public double point;

	public SamplePoint(int samples, double point) {
		this.point = point;
		this.samples = samples;
	}
}
