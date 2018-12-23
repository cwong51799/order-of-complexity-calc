//CSE 214
// Christopher Wong (#111386693)
import java.io.*;
import java.util.*;
public class PythonTracer {
	public static final int SPACE_COUNT = 4;
	/** traceFile reads line by line through a file to determine the order of
	 * complexity of each block and eventually the whole file.
	 * traceFile prints out its progress in steps.
	 * @param a filename which is ideally a .txt version of a Python program
	 * @returns the highest sub complexity of the entire program
	 * @throws FileNotFound if file does not exist in the directory
	 * 
	 */
	public static Complexity traceFile(String filename){
		int [] nameKeeper = new int[] {0,0,0,0,0,0,0,0};
		String s = "1";
		Stack<CodeBlock> stack = new Stack<CodeBlock>();
		try {
			FileInputStream fis = new FileInputStream(filename);	
			InputStreamReader inStream = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(inStream);
			int indents = 0;
			CodeBlock oldTop;
			Complexity oldTopComplexity;
			String line;
			while ((line = reader.readLine()) != null) {
				CodeBlock newBlock = new CodeBlock();
				if (!line.trim().isEmpty() && line.trim().charAt(0) != '#') {
					indents = 0;
					int spaces = 0;
					for (int i=0;line.charAt(i) == ' ';i++) {
						spaces++;
					}
					indents = spaces / SPACE_COUNT;
				while (indents < stack.size()) {
					if (indents == 0) {
						reader.close();
						return (stack.peek().getBlockComplexity());
					}
					else {
						oldTop = stack.pop();
						oldTopComplexity = oldTop.getBlockComplexity();
						oldTopComplexity.setLogPower(oldTop.getHighestSubComplexity().getLogPower() + oldTop.getBlockComplexity().getLogPower());
						oldTopComplexity.setnPower(oldTop.getHighestSubComplexity().getnPower() + oldTop.getBlockComplexity().getnPower());
						if (oldTopComplexity.compareComplexity(stack.peek().getHighestSubComplexity()) == false)  { 
							CodeBlock temp = stack.pop();
							temp.setHighestSubComplexity(oldTopComplexity);
							stack.push(temp);
						}
						System.out.println("Leaving block " + oldTop.getName() + " updating block " + stack.peek().getName());
						System.out.println("    BLOCK " + stack.peek().getName() +":    block complexity = " +stack.peek().getBlockComplexity().toString() + "    highest sub-complexity = " +stack.peek().getHighestSubComplexity().toString());
					}
				}
					for (int i=0;i<CodeBlock.BLOCK_TYPE.length;i++) { // checking if line contains keyword
						Complexity newComplexity = new Complexity();
						if (line.contains(CodeBlock.BLOCK_TYPE[i])) {
							s = "1";
							nameKeeper[indents]++;
							for (int j=0;j<indents;j++) {
							s += ("." + nameKeeper[indents]);
							}
							newBlock.setName(s);
							String keyword = CodeBlock.BLOCK_TYPE[i];
							if (keyword == "for") {
								if (line.charAt(line.indexOf('N')-1) == ' '){
									newComplexity.setnPower(1);
									newBlock.setBlockComplexity(newComplexity);
									stack.push(newBlock);
								}
								else if (line.charAt(line.indexOf('N')-1) == '_') {
									newComplexity.setLogPower(1);
									newBlock.setBlockComplexity(newComplexity);
									stack.push(newBlock);
								}
							}
							else if (keyword == "while") {
								String s1 = Character.toString(line.trim().charAt(7)); 
								newBlock.setLoopVariable(s1);
								newBlock.setBlockComplexity(newComplexity);
								stack.push(newBlock);
							}
							else { // if its the def or anything else
								newBlock.setBlockComplexity(newComplexity);
								stack.push(newBlock);
							}
							System.out.println("Entering block " + newBlock.getName() + " '" + keyword + "':");
							System.out.println("    BLOCK: " + newBlock.getName() + "    block complexity = " + newBlock.getBlockComplexity().toString() + "    highest sub-complexity: "+newBlock.getHighestSubComplexity().toString()); 
						}
					else if (stack.peek().isWhileLoop() && line.contains(stack.peek().getLoopVariable())) { // and line updates stack.tops loop variable
						if (line.contains("+") || line.contains("-")) {
							Complexity nComplexity = new Complexity();
							nComplexity.setnPower(1);
							CodeBlock temp = stack.pop();
							temp.setBlockComplexity(nComplexity);
							stack.push(temp);
							System.out.println("Found update statement, updating block " + temp.getName());
							System.out.println("    BLOCK: " + temp.getName() + "    block complexity = " + temp.getBlockComplexity().toString() + "    highest sub-complexity: "+newBlock.getHighestSubComplexity().toString()); 
							break;
						}
						else if (line.contains("*") || line.contains("/")){
							Complexity LogNComplexity = new Complexity();
							LogNComplexity.setLogPower(1);
							CodeBlock temp = stack.pop();
							temp.setBlockComplexity(LogNComplexity);
							stack.push(temp);
							System.out.println("Found update statement, updating block " + temp.getName());
							System.out.println("    BLOCK: " + temp.getName() + "    block complexity = " + temp.getBlockComplexity().toString() + "    highest sub-complexity: "+ temp.getHighestSubComplexity().toString()); 
							break;
						}
					}
					}
				}
				else { // ignore line
					continue;
				}
			}
			reader.close();
			while (stack.size() > 1) { // should gather the highest complexity
				oldTop = stack.pop();
				oldTopComplexity = oldTop.getBlockComplexity();
				if (!oldTopComplexity.compareComplexity(stack.peek().getHighestSubComplexity())){ // if the complexity of the block is higher than the next, set the next ones complexity to highest
					stack.peek().setHighestSubComplexity(oldTopComplexity);
				}
			}
		}
		catch(FileNotFoundException ex){
			System.out.println("File was not found");
			return new Complexity();
		}
		catch(IOException ex) {
		}
		catch (Exception ex) {
			
		}
		finally {
			if (stack.size()>0)
				System.out.println("Leaving block 1.");
			System.out.println("Overall complexity of " + filename + ": " + stack.peek().getHighestSubComplexity().toString());
			return stack.pop().getBlockComplexity();
		}
	}
	/** Test client for the program
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean cont = true;
		while (cont) {
			System.out.print("Please enter a file name (or 'quit' to quit): ");
			String answer = input.nextLine();
			if (answer.equals("quit")) {
				System.out.println("Program terminating successfully. . . . .");
				return;
			}
			if (!answer.contains(".txt")) {
				System.out.println("Enter a file with a valid file type");
				continue;
			}
			try {
				traceFile(answer);
				continue;
			}
			catch (Exception ex) {
				System.out.println("Please try another file.");
				continue;
			}
		}
		input.close();
	}
}
	
