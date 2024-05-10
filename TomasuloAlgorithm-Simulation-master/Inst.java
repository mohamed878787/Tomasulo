
public class Inst {
	String instruction;
	int issue;
	int execComplete;
	int writeResult;
	
	public Inst(String instruction) {
		this.instruction = instruction;
		this.issue = 0;
		this.execComplete = 0;
		this.writeResult = 0;
	}
	
	public void print() {
		System.out.println("instruction: "+ instruction + ", issue: " + issue + ", execComplete: " + execComplete + ", writeResult: " + writeResult);
	}
}
