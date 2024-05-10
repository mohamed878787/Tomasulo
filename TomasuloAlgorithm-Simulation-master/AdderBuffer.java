
public class AdderBuffer {
	AdderR A1 = new AdderR("A1");
	AdderR A2 = new AdderR("A2");
	
	public AdderBuffer() {
		
	}
	
	public boolean canIssue() {
		if(A1.busy && A2.busy)
			return false;
		else
			return true;
	}
	
	public void print() {
		A1.print();
		System.out.println();
		A2.print();
		System.out.println();
	}
}
