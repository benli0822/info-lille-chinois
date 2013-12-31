package sat;

/**
 * Convinience object for returning a variable together with a value
 */
class SymbolValuePair {
	private int variable;
	private boolean value;

	public SymbolValuePair(int x, boolean y) {
		variable = x;
		value = y;
	}

	public int getVar() {
		return variable;
	}

	public boolean getValue() {
		return value;
	}

	public void setVar(int var) {
		variable = var;
	}

	public void setValue(boolean v) {
		value = v;
	}

}
