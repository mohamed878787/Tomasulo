
public class StoreR {
	String name;
	String operation;
	double v;
	String q;
	boolean busy;
	String instruction;
	int execCycles;
	
	public StoreR(String name) {
		this.name = name;
		this.operation = "";
		this.v = 0.0;
		this.q = "";
		this.busy = false;
		this.instruction = "";
		this.execCycles = 0;
	}
	
	public double exec() {
		return v;
	}
	
	public void reset() {
		this.operation = "";
		this.v = 0.0;
		this.q = "";
		this.busy = false;
		this.instruction = "";
		this.execCycles = 0;
	}
	
	public void print() {
		System.out.println("name: "+ name + ", operation: " + operation + ", v: " + v + ", q: " + q + ", busy: " + busy);
	}
}
