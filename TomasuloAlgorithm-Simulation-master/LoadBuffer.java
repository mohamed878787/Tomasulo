
public class LoadBuffer {
	LoadR L1 = new LoadR("L1");
	LoadR L2 = new LoadR("L2");
	
	public LoadBuffer() {
		
	}
	
	public boolean canIssue() {
		if(L1.busy && L2.busy)
			return false;
		else
			return true;
	}
	
	public void print() {
		L1.print();
		System.out.println();
		L2.print();
		System.out.println();
	}
}
