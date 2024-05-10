
public class RegisterFile2 {
	
	int[] R = {0, 5, 0, 6, 1, 4, 7};
	boolean[] vR = {false, false, false, false, false, false, false};
	String[] RR = {"", "", "", "", "", "", ""};
	
	public RegisterFile2() {
		
	}
	
	public boolean isValid(String R) {
		int r = getIndex(R);
		return vR[r];
	}
	
	public int getR(String R) {
		int r = getIndex(R);
		return this.R[r];
	}
	
	public String getRR(String R) {
		int r = getIndex(R);
		return RR[r];
	}
	
	public void setRR(String R, String value) {
		int r = getIndex(R);
		RR[r] = value;
	}
	
	public void setvR(String R, boolean value) {
		int r = getIndex(R);
		vR[r] = value;
	}
	
	public static int getIndex(String R) {
		String r = "";
		for(int i=0; i < R.length(); i++) {
			String c = R.charAt(i) + "";
			if(!c.equals("R")) {
				r += c;
			}
		}
		return Integer.parseInt(r);
	}
	
	public void print() {
		System.out.println("Reg | RegValue | Qi");
		System.out.println("--------------------------");
		for(int i=0; i < vR.length; i++) {
			System.out.print("R["+i+"]     ");
			if(vR[i]) {
				System.out.print(RR[i]);
			}
			else {
				System.out.print(R[i]);
			}
			System.out.print("        " + vR[i]);
			System.out.println();
		}
		
		System.out.println();
	}
}
