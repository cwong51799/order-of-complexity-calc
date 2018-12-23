//CSE 214
// Christopher Wong (#111386693)
public class CodeBlock {
	public static final String[] BLOCK_TYPE = new String[]{"def","for","while","if","elif","else"};
	private static final int DEF = 0;
	private static final int FOR = 1;
	private static final int WHILE = 2;
	private static final int IF = 3;
	private static final int ELIF = 4;
	private static final int ELSE = 5;
	Complexity blockComplexity = new Complexity();
	Complexity highestSubComplexity = new Complexity();
	String name = "1", loopVariable;
	/** Getters and setters for variables.
	 */
	public Complexity getBlockComplexity() {
		return blockComplexity;
	}
	public void setBlockComplexity(Complexity blockComplexity) {
		this.blockComplexity = blockComplexity;
	}
	public Complexity getHighestSubComplexity() {
		return highestSubComplexity;
	}
	public void setHighestSubComplexity(Complexity highestSubComplexity) {
		this.highestSubComplexity = highestSubComplexity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoopVariable() {
		return loopVariable;
	}
	public void setLoopVariable(String loopVariable) {
		this.loopVariable = loopVariable;
	}
	/**
	 * Default constructor for CodeBlock.
	 */
	public CodeBlock() {
	
	}

	/** Constructor with parameters for a new CodeBlock
	 * 
	 * @param blockComplexity
	 * @param highestSubComplexity
	 */
	public CodeBlock(Complexity blockComplexity, Complexity highestSubComplexity) {
		this.blockComplexity = blockComplexity;
		this.highestSubComplexity = highestSubComplexity;
	}
	/** 
	 * 
	 * @return true if the instance of CodeBlock contains a loopVariable, false if not.
	 */
	public boolean isWhileLoop() {
		if (loopVariable != null) {
			return true;
		}
		else
			return false;
	}
}
