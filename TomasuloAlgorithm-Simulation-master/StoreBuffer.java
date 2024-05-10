
public class StoreBuffer {
	StoreR S1 = new StoreR("S1");
	StoreR S2 = new StoreR("S2");
	
	public StoreBuffer() {
		
	}
	
	public boolean canIssue() {
		if(S1.busy && S2.busy)
			return false;
		else
			return true;
	}
	
	public void print() {
		S1.print();
		System.out.println();
		S2.print();
		System.out.println();
	}
}
