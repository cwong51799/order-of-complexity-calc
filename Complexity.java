//CSE 214
// Christopher Wong (#111386693)
public class Complexity {
	private int nPower = 0, logPower = 0;
	/** 
	 * Getters and setters for variables
	 */
	public void setnPower(int nPower) {
		this.nPower = nPower;
	}
	public void setLogPower(int logPower) {
		this.logPower = logPower;
	}
	public int getnPower() {
		return nPower;
	}
	public int getLogPower() {
		return logPower;
	}
	/** 
	 * Prints human-readable Big-Oh notation by reading nPower and logPower.
	 */
	public String toString() {
		String s = "O(";
		switch (nPower) {
		case(0):
			break;
		case(1):				
			s += "n";
			break;
		default:
			s += ("n^" + nPower);
			break;
		}
		if (nPower != 0 && logPower != 0)
			s+= " * ";
		switch (logPower) {
		case(0):
			break;
		case(1):
			s += "log(n)";
			break;
		default:
			s += ("log(n)^" + logPower);
		}
		if (nPower == 0 && logPower == 0) {
			s+= "1";
		}
		s += ")";
		return s;
	}
	public boolean compareComplexity(Complexity b) { // returns true if the comparing object is higher complexity
		if (nPower > b.nPower) 
			return false;
		if (nPower < b.nPower) 
			return true;
		if (nPower == b.nPower) {
			if (logPower > b.logPower) 
				return false;
			if (logPower < b.logPower)
				return true;
			if (logPower == b.logPower) {
				System.out.println("They're the same");
				return false; // this means they are the same
			}
		}
		return false;
	}
}