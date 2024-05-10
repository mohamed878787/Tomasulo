
public class LoadR {
	String name;
	String operation;
	int vI;
	int vJ;
	String qI;
	String qJ;
	boolean busy;
	String instruction;
	int execCycles;
	
	public LoadR(String name) {
		this.name = name;
		this.operation = "";
		this.vI = 0;
		this.vJ = 0;
		this.qI = "";
		this.qJ = "";
		this.busy = false;
		this.instruction = "";
		this.execCycles = 0;
	}
	
	public int exec() {
		return vI + vJ;
	}
	
	public void reset() {
		this.operation = "";
		this.vI = 0;
		this.vJ = 0;
		this.qI = "";
		this.qJ = "";
		this.busy = false;
		this.instruction = "";
		this.execCycles = 0;
	}
	
	public void print() {
		System.out.println("name: "+ name + ", operation: " + operation + ", vI: " + vI + ", vJ: " + vJ + ", qI: " + qI + ", qJ: " + qJ + ", busy: " + busy);
	}
}
