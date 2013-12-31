package sat;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * Represents a SAT instance
 */
public class Instance{
	public int num_vars; // number of variables in the instance,
				  //if (num_vars==n), then there are "n" variables numbered by integers starting from 1
	int num_clauses; //number of clauses in the instance
	Vector<Clause> clauses;
	public Vector<Clause> initialClauses; // stored in case we want to write the original clauses incl. the trivial ones

	public Instance(String filename) throws IOException{
		clauses=new Vector<Clause>();
		initialClauses = new Vector<Clause>();
		readInputFile(filename);
	}
	
	/**
	 * Creates the Instance, based on the given CNF,.
	 * NOTE :  Removes trivially satisfied clauses
	 * @param initClauses
	 * @param numVars
	 */
	public Instance(Vector<Clause> initClauses, int numVars){
		this.num_vars = numVars;
		clauses  = new Vector<Clause>();
		initialClauses = initClauses;
		// this steps removes trivially satisfied clauses
		// like (x1 OR (NOT x1) OR x2)
		// duplicate literals have already been removed
		// by the Clause constructor
		for(int i = 0; i < initClauses.size(); i++){
			Clause c = initClauses.get(i);
			if(!c.isTriviallyTrue()){
				clauses.add(c);
			}
		}
		num_clauses = clauses.size();
	}

	private	void readInputFile(String filename) throws IOException{
			@SuppressWarnings("resource")
			LineNumberReader ln = new LineNumberReader(new FileReader(filename));
			String  line = ln.readLine();

			ln.mark(4);
			while(ln.read()=='c'){
				ln.readLine();
				ln.mark(4);
			}
			ln.reset();

		    line = ln.readLine();

			StringTokenizer stk = new StringTokenizer(line);

			if (!(stk.hasMoreTokens()
				&& stk.nextToken().equals("p")
				&& stk.hasMoreTokens()
				&& stk.nextToken().equals("cnf"))) {
				System.out.println("Error, wrong input file!!");
			}

			// reads the number of variables in the input file instance
			num_vars = Integer.parseInt(stk.nextToken());
			// reads the number of clauses
			num_clauses = Integer.parseInt(stk.nextToken());
			//System.out.println("Clauses " + num_clauses);
			//System.out.flush();

			for(int i=0;i<num_clauses;i++){
				Vector<Integer> literals=new Vector<Integer>();
				 line = ln.readLine();
				 stk = new StringTokenizer(line);
				int literal = Integer.parseInt(stk.nextToken());
				while (literal!=0){
					literals.addElement(literal);
					literal = Integer.parseInt(stk.nextToken());
				}
				Clause cl=new Clause(literals);
				if(!cl.isTriviallyTrue()){
					clauses.addElement(cl);
				}
				initialClauses.add(cl);
			}
			num_clauses = clauses.size();
			//System.out.println("Now " + clauses.size() + " loaded : "+ initialClauses.size());
	}

	public int getNumVars(){
		return num_vars;
	}

	public int getNumClauses(){
		return num_clauses;
	}

	public Clause getClause(int index){
		return (Clause)clauses.elementAt(index);
	}

	public Vector getClauses(){
		return clauses;
	}

}