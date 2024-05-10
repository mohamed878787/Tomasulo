
public class RegisterFile {
//	double R1;
//	double R2;
//	double R3;
//	double R4;
//	double R5;
//	double R6;
//	double R7;
//	
//	boolean vR1;
//	boolean vR2;
//	boolean vR3;
//	boolean vR4;
//	boolean vR5;
//	boolean vR6;
//	boolean vR7;
	
	double[] R = {0.0, 3.0, 1.1, 4.5, 6.2, 1.5, 66.0};
	boolean[] vR = {false, false, false, false, false, false, false};
	String[] RR = {"", "", "", "", "", "", ""};
	
	public RegisterFile() {
//		this.R1 = 0.0;
//		this.R2 = 0.0;
//		this.R3 = 0.0;
//		this.R4 = 0.0;
//		this.R5 = 0.0;
//		this.R6 = 0.0;
//		this.R7 = 0.0;
//		
//		this.vR1 = false;
//		this.vR2 = false;
//		this.vR3 = false;
//		this.vR4 = false;
//		this.vR5 = false;
//		this.vR6 = false;
//		this.vR7 = false;
		
	}
	
	public boolean isValid(String R) {
		int r = getIndex(R);
		return vR[r];
	}
	
	public double getR(String R) {
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
			if(!c.equals("F")) {
				r += c;
			}
		}
		return Integer.parseInt(r);
	}
	
	public void print() {
		System.out.println("Reg | RegValue | Qi");
		System.out.println("--------------------------");
		for(int i=0; i < vR.length; i++) {
			System.out.print("F["+i+"]     ");
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
