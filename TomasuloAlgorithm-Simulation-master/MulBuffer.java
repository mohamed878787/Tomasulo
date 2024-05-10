
public class MulBuffer {
	MulR M1 = new MulR("M1");
	MulR M2 = new MulR("M2");
	
	public MulBuffer() {
		
	}
	
	public boolean canIssue() {
		if(M1.busy && M2.busy)
			return false;
		else
			return true;
	}
	
	public void print() {
		M1.print();
		System.out.println();
		M2.print();
		System.out.println();
	}
}
