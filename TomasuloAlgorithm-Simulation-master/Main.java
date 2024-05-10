import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static int cycle = 1;
	
//	public static String[] instructions = {"L.D F1,R1,R3", "ADD.D F1,F2,F3", "MUL.D F4,F1,F3", "DIV.D F6,F4,F1", "S.D F1,R1,R3"};
	public static ArrayList<String> instructions = new ArrayList<String>();
	public static int currentInstruction = 0;
	public static int instructionsExecuted = 0;
	
	public static double A1pub = 0.0;
	public static double A2pub = 0.0;
	public static double M1pub = 0.0;
	public static double M2pub = 0.0;
	public static int L1pub = 0;
	public static int L2pub = 0;
//	public static int S1pub = 0;
//	public static int S2pub = 0;
	
	public static boolean A1v = false;
	public static boolean A2v = false;
	public static boolean M1v = false;
	public static boolean M2v = false;
	public static boolean L1v = false;
	public static boolean L2v = false;
	public static boolean S1v = false;
	public static boolean S2v = false;
	
	public static int A1vCycle = 0;
	public static int A2vCycle = 0;
	public static int M1vCycle = 0;
	public static int M2vCycle = 0;
	public static int L1vCycle = 0;
	public static int L2vCycle = 0;
	public static int S1vCycle = 0;
	public static int S2vCycle = 0;
	
	public static int A1issueCycle = 0;
	public static int A2issueCycle = 0;
	public static int M1issueCycle = 0;
	public static int M2issueCycle = 0;
	public static int L1issueCycle = 0;
	public static int L2issueCycle = 0;
	public static int S1issueCycle = 0;
	public static int S2issueCycle = 0;
	
	public static boolean A1FinishedExec = false;
	public static boolean A2FinishedExec = false;
	public static boolean M1FinishedExec = false;
	public static boolean M2FinishedExec = false;
	public static boolean L1FinishedExec = false;
	public static boolean L2FinishedExec = false;
	public static boolean S1FinishedExec = false;
	public static boolean S2FinishedExec = false;
	
	public static boolean didIssue = false;
	
	public static RegisterFile regFile = new RegisterFile();
	public static RegisterFile2 regFile2 = new RegisterFile2();
	public static AdderBuffer aBuffer = new AdderBuffer();
	public static MulBuffer mBuffer = new MulBuffer();
	public static LoadBuffer lBuffer = new LoadBuffer();
	public static StoreBuffer sBuffer = new StoreBuffer();
	
	public static boolean takeInput = true;
	
	public static ArrayList<Inst> instructions2 = new ArrayList<Inst>();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(takeInput) {
			System.out.println("Enter instruction(or 0 if done): ");
			String input = sc.nextLine();
			
			if(input.equals("0")) {
				takeInput = false;
			}
			else {
				instructions.add(input);
				
				Inst inst = new Inst(input);
				instructions2.add(inst);
			}
		}
		System.out.println();
		sc.close();
		
		while(true) {
			if(instructionsExecuted == instructions.size()) {
				break;
			}
			
			if(currentInstruction < instructions.size()) {
				String currentInst = instructions.get(currentInstruction);
				String operation = getOperation(currentInst);
				String dest = getDest(currentInst);
				String op1 = getOp1(currentInst);
				String op2 = getOp2(currentInst);
				
				if(operation.equals("ADD.D") || operation.equals("SUB.D")) {
					if(aBuffer.canIssue()) {
						issueAdder(currentInst, operation,dest,op1,op2);
						didIssue = true;
						iInst(currentInst, cycle);
					}
						
				}
				else if(operation.equals("MUL.D") || operation.equals("DIV.D")) {
					if(mBuffer.canIssue()) {
						issueMul(currentInst, operation,dest,op1,op2);
						didIssue = true;
						iInst(currentInst, cycle);
					}
						
				}
				else if(operation.equals("L.D")) {
					if(lBuffer.canIssue()) {
						issueLoad(currentInst, operation,dest,op1,op2);
						didIssue = true;
						iInst(currentInst, cycle);
					}
						
				}
				else if(operation.equals("S.D")) {
					if(sBuffer.canIssue()) {
						issueStore(currentInst, operation,dest,op1,op2);
						didIssue = true;
						iInst(currentInst, cycle);
					}
						
				}
			}
			
			if(aBuffer.A1.busy) {
				if(aBuffer.A1.qI.equals("") && aBuffer.A1.qJ.equals("") && A1issueCycle < cycle && !A1FinishedExec) {
					if(aBuffer.A1.execCycles == 1) {
						A1pub = aBuffer.A1.exec();
//						aBuffer.A1.reset();
						A1v = true;
						A1vCycle = cycle;
						A1FinishedExec = true;
						eInst(aBuffer.A1.instruction, cycle);
					}
					else {
						aBuffer.A1.execCycles--;
					}
				}
			}
			if(aBuffer.A2.busy) {
				if(aBuffer.A2.qI.equals("") && aBuffer.A2.qJ.equals("") && A2issueCycle < cycle && !A2FinishedExec) {
					if(aBuffer.A2.execCycles == 1) {
						A2pub = aBuffer.A2.exec();
//						aBuffer.A2.reset();
						A2v = true;
						A2vCycle = cycle;
						A2FinishedExec = true;
						eInst(aBuffer.A2.instruction, cycle);
					}
					else {
						aBuffer.A2.execCycles--;
					}
				}
			}
			
			if(mBuffer.M1.busy) {
				if(mBuffer.M1.qI.equals("") && mBuffer.M1.qJ.equals("") && M1issueCycle < cycle && !M1FinishedExec) {
					if(mBuffer.M1.execCycles == 1) {
						M1pub = mBuffer.M1.exec();
//						mBuffer.M1.reset();
						M1v = true;
						M1vCycle = cycle;
						M1FinishedExec = true;
						eInst(mBuffer.M1.instruction, cycle);
					}
					else {
						mBuffer.M1.execCycles--;
					}
				}
			}
			if(mBuffer.M2.busy) {
				if(mBuffer.M2.qI.equals("") && mBuffer.M2.qJ.equals("") && M2issueCycle < cycle && !M2FinishedExec) {
					if(mBuffer.M2.execCycles == 1) {
						M2pub = mBuffer.M2.exec();
//						mBuffer.M2.reset();
						M2v = true;
						M2vCycle = cycle;
						M2FinishedExec = true;
						eInst(mBuffer.M2.instruction, cycle);
					}
					else {
						mBuffer.M2.execCycles--;
					}
				}
			}
			if(lBuffer.L1.busy) {
				if(lBuffer.L1.qI.equals("") && lBuffer.L1.qJ.equals("") && L1issueCycle < cycle && !L1FinishedExec) {
					if(lBuffer.L1.execCycles == 1) {
						L1pub = lBuffer.L1.exec();
						L1v = true;
						L1vCycle = cycle;
						L1FinishedExec = true;
						eInst(lBuffer.L1.instruction, cycle);
					}
					else {
						lBuffer.L1.execCycles--;
					}
				}
			}
			if(lBuffer.L2.busy) {
				if(lBuffer.L2.qI.equals("") && lBuffer.L2.qJ.equals("") && L2issueCycle < cycle && !L2FinishedExec) {
					if(lBuffer.L2.execCycles == 1) {
						L2pub = lBuffer.L2.exec();
						L2v = true;
						L2vCycle = cycle;
						L2FinishedExec = true;
						eInst(lBuffer.L2.instruction, cycle);
					}
					else {
						lBuffer.L2.execCycles--;
					}
				}
			}
			if(sBuffer.S1.busy) {
				if(sBuffer.S1.q.equals("") && S1issueCycle < cycle && !S1FinishedExec) {
					if(sBuffer.S1.execCycles == 1) {
//						S1pub = sBuffer.S1.exec();
						S1v = true;
						S1vCycle = cycle;
						S1FinishedExec = true;
						eInst(sBuffer.S1.instruction, cycle);
					}
					else {
						sBuffer.S1.execCycles--;
					}
				}
			}
			if(sBuffer.S2.busy) {
				if(sBuffer.S2.q.equals("") && S2issueCycle < cycle && !S2FinishedExec) {
					if(sBuffer.S2.execCycles == 1) {
//						S2pub = sBuffer.S2.exec();
						S2v = true;
						S2vCycle = cycle;
						S2FinishedExec = true;
						eInst(sBuffer.S2.instruction, cycle);
					}
					else {
						sBuffer.S2.execCycles--;
					}
				}
			}
			
			if(A1v && A1vCycle < cycle) {
				pubValue("A1", A1pub);
				wInst(aBuffer.A1.instruction, cycle);
				aBuffer.A1.reset();
				A1v = false;
				A1FinishedExec = false;
				instructionsExecuted++;
			}
			else if(A2v && A2vCycle < cycle) {
				pubValue("A2", A2pub);
				wInst(aBuffer.A2.instruction, cycle);
				aBuffer.A2.reset();
				A2v = false;
				A2FinishedExec = false;	//true
				instructionsExecuted++;
			}
			else if(M1v && M1vCycle < cycle) {
				pubValue("M1", M1pub);
				wInst(mBuffer.M1.instruction, cycle);
				mBuffer.M1.reset();
				M1v = false;
				M1FinishedExec = false;
				instructionsExecuted++;
			}
			else if(M2v && M2vCycle < cycle) {
				pubValue("M2", M2pub);
				wInst(mBuffer.M2.instruction, cycle);
				mBuffer.M2.reset();
				M2v = false;
				M2FinishedExec = false;
				instructionsExecuted++;
			}
			else if(L1v && L1vCycle < cycle) {
				pubValue("L1", L1pub);
				wInst(lBuffer.L1.instruction, cycle);
				lBuffer.L1.reset();
				L1v = false;
				L1FinishedExec = false;
				instructionsExecuted++;
			}
			else if(L2v && L2vCycle < cycle) {
				pubValue("L2", L2pub);
				wInst(lBuffer.L2.instruction, cycle);
				lBuffer.L2.reset();
				L2v = false;
				L2FinishedExec = false;
				instructionsExecuted++;
			}
			
			if(S1v && S1vCycle < cycle) {
//				pubValue("S1", S1pub);
//				wInst(sBuffer.S1.instruction, cycle);
				sBuffer.S1.reset();
				S1v = false;
				S1FinishedExec = false;
				instructionsExecuted++;
			}
			if(S2v && S2vCycle < cycle) {
//				pubValue("S2", S2pub);
//				wInst(sBuffer.S2.instruction, cycle);
				sBuffer.S2.reset();
				S2v = false;
				S2FinishedExec = false;
				instructionsExecuted++;
			}
			
			printCycle();	
			if(didIssue) {
				currentInstruction++;
			}
			cycle++;
			didIssue = false;
			System.out.println("-----------------------------------------------------------------------------------------------------------------------");
		}
		
		
	}
	
//	public static void issue(String operation, String dest, String op1, String op2) {	
//		if(operation.equals("ADD.D") || operation.equals("SUB.D")) {
//			issueAdder(operation,dest,op1,op2);
//		}
//		else if(operation.equals("MUL.D") || operation.equals("DIV.D")) {
//			issueMul(operation,dest,op1,op2);
//		}
//	}
	
	public static void issueAdder(String currentInst, String operation, String dest, String op1, String op2) {
		if(!aBuffer.A1.busy) {
			aBuffer.A1.operation = operation;
			if(!regFile.isValid(op1)) {
				aBuffer.A1.vI = regFile.getR(op1);
			}
			else {
				aBuffer.A1.qI = regFile.getRR(op1);
			}
			
			if(!regFile.isValid(op2)) {
				aBuffer.A1.vJ = regFile.getR(op2);
			}
			else {
				aBuffer.A1.qJ = regFile.getRR(op2);
			}
			
			regFile.setRR(dest, aBuffer.A1.name);
			regFile.setvR(dest, true);
			aBuffer.A1.busy = true;
			
			A1issueCycle = cycle;
			aBuffer.A1.instruction = currentInst;
			aBuffer.A1.execCycles = 2;
		}
		else if(!aBuffer.A2.busy) {
			aBuffer.A2.operation = operation;
			if(!regFile.isValid(op1)) {
				aBuffer.A2.vI = regFile.getR(op1);
			}
			else {
				aBuffer.A2.qI = regFile.getRR(op1);
			}
			
			if(!regFile.isValid(op2)) {
				aBuffer.A2.vJ = regFile.getR(op2);
			}
			else {
				aBuffer.A2.qJ = regFile.getRR(op2);
			}
			
			regFile.setRR(dest, aBuffer.A2.name);
			regFile.setvR(dest, true);
			aBuffer.A2.busy = true;
			
			A2issueCycle = cycle;
			aBuffer.A2.instruction = currentInst;
			aBuffer.A2.execCycles = 2;
		}
	}

	public static void issueMul(String currentInst, String operation, String dest, String op1, String op2) {
		if(!mBuffer.M1.busy) {
			mBuffer.M1.operation = operation;
			if(!regFile.isValid(op1)) {
				mBuffer.M1.vI = regFile.getR(op1);
			}
			else {
				mBuffer.M1.qI = regFile.getRR(op1);
			}
			
			if(!regFile.isValid(op2)) {
				mBuffer.M1.vJ = regFile.getR(op2);
			}
			else {
				mBuffer.M1.qJ = regFile.getRR(op2);
			}
			
			regFile.setRR(dest, mBuffer.M1.name);
			regFile.setvR(dest, true);
			mBuffer.M1.busy = true;
			
			M1issueCycle = cycle;
			mBuffer.M1.instruction = currentInst;
			if(mBuffer.M1.operation.equals("MUL.D")) {
				mBuffer.M1.execCycles = 5;
			}
			else {
				mBuffer.M1.execCycles = 10;
			}
		}
		else if(!mBuffer.M2.busy) {
			mBuffer.M2.operation = operation;
			if(!regFile.isValid(op1)) {
				mBuffer.M2.vI = regFile.getR(op1);
			}
			else {
				mBuffer.M2.qI = regFile.getRR(op1);
			}
			
			if(!regFile.isValid(op2)) {
				mBuffer.M2.vJ = regFile.getR(op2);
			}
			else {
				mBuffer.M2.qJ = regFile.getRR(op2);
			}
			
			regFile.setRR(dest,mBuffer.M2.name);
			regFile.setvR(dest, true);
			mBuffer.M2.busy = true;
			
			M2issueCycle = cycle;
			mBuffer.M2.instruction = currentInst;
			if(mBuffer.M2.operation.equals("MUL.D")) {
				mBuffer.M2.execCycles = 5;
			}
			else {
				mBuffer.M2.execCycles = 10;
			}
		}
	}
	
	public static void issueLoad(String currentInst, String operation, String dest, String op1, String op2) {
		if(!lBuffer.L1.busy) {
			lBuffer.L1.operation = operation;
			if(!regFile2.isValid(op1)) {
				lBuffer.L1.vI = regFile2.getR(op1);
			}
			else {
				lBuffer.L1.qI = regFile2.getRR(op1);
			}
			
			if(!regFile2.isValid(op2)) {
				lBuffer.L1.vJ = regFile2.getR(op2);
			}
			else {
				lBuffer.L1.qJ = regFile2.getRR(op2);
			}
			
			regFile.setRR(dest, lBuffer.L1.name);
			regFile.setvR(dest, true);
			lBuffer.L1.busy = true;
			
			L1issueCycle = cycle;
			lBuffer.L1.instruction = currentInst;
			lBuffer.L1.execCycles = 2;
		}
		else if(!lBuffer.L2.busy) {
			lBuffer.L2.operation = operation;
			if(!regFile2.isValid(op1)) {
				lBuffer.L2.vI = regFile2.getR(op1);
			}
			else {
				lBuffer.L2.qI = regFile2.getRR(op1);
			}
			
			if(!regFile2.isValid(op2)) {
				lBuffer.L2.vJ = regFile2.getR(op2);
			}
			else {
				lBuffer.L2.qJ = regFile2.getRR(op2);
			}
			
			regFile.setRR(dest, lBuffer.L2.name);
			regFile.setvR(dest, true);
			lBuffer.L2.busy = true;
			
			L2issueCycle = cycle;
			lBuffer.L2.instruction = currentInst;
			lBuffer.L2.execCycles = 2;
		}
	}
	
	public static void issueStore(String currentInst, String operation, String dest, String op1, String op2) {
		if(!sBuffer.S1.busy) {
			sBuffer.S1.operation = operation;
			if(!regFile.isValid(dest)) {
				sBuffer.S1.v = regFile.getR(dest);
			}
			else {
				sBuffer.S1.q = regFile.getRR(dest);
			}
			
			if(!regFile.isValid(dest)) {
				sBuffer.S1.v = regFile.getR(dest);
			}
			else {
				sBuffer.S1.q = regFile.getRR(dest);
			}
			
//			regFile.setRR(dest, lBuffer.L1.name);
//			regFile.setvR(dest, true);
			sBuffer.S1.busy = true;
			
			S1issueCycle = cycle;
			sBuffer.S1.instruction = currentInst;
			sBuffer.S1.execCycles = 1;
		}
		else if(!sBuffer.S2.busy) {
			sBuffer.S2.operation = operation;
			if(!regFile.isValid(dest)) {
				sBuffer.S2.v = regFile.getR(dest);
			}
			else {
				sBuffer.S2.q = regFile.getRR(dest);
			}
			
			if(!regFile.isValid(dest)) {
				sBuffer.S2.v = regFile.getR(dest);
			}
			else {
				sBuffer.S2.q = regFile.getRR(dest);
			}
			
//			regFile.setRR(dest, lBuffer.L2.name);
//			regFile.setvR(dest, true);
			sBuffer.S2.busy = true;
			
			S2issueCycle = cycle;
			sBuffer.S2.instruction = currentInst;
			sBuffer.S1.execCycles = 1;
		}
	}

	public static String getOperation(String inst) {
		String op = "";
		for(int i=0; i < inst.length(); i++) {
			String c = inst.charAt(i) + "";
			if(c.equals(" ")) {
				break;
			}
			else {
				op += c;
			}
		}
		return op;
	}

	public static String getDest(String inst) {
		String op = "";
		int start = 0;
		int start2 = 0;
		boolean flag = true;
		for(int i=0; i < inst.length(); i++) {
			flag = true;
			String c = inst.charAt(i) + "";
			if(c.equals(" ")) {
				start++;
				flag = false;
			}
			if(c.equals(",")) {
				start2++;
			}
			
			if(start2 == 1) {
				break;
			}
			else if(start == 1 && flag) {
				op += c;
			}
		}
		return op;
	}
	
	public static String getOp1(String inst) {
		String op = "";
		int start = 0;
		int start2 = 0;
		boolean flag = true;
		for(int i=0; i < inst.length(); i++) {
			flag = true;
			String c = inst.charAt(i) + "";
			if(c.equals(" ")) {
				start++;
			}
			if(c.equals(",")) {
				start2++;
				flag = false;
			}
			
			if(start2 == 2) {
				break;
			}
			else if(start == 1 && start2 == 1 && flag) {
				op += c;
			}
		}
		return op;
	}
	
	public static String getOp2(String inst) {
		String op = "";
		int start = 0;
		int start2 = 0;
		for(int i=0; i < inst.length(); i++) {
			String c = inst.charAt(i) + "";
			if(c.equals(" ")) {
				start++;
			}
			if(c.equals(",")) {
				start2++;
			}
			
			else if(start == 1 && start2 == 2) {
				op += c;
			}
			

		}
		return op;
	}

	
	public static void pubValue(String name, double value) {
		if(aBuffer.A1.qI.equals(name)) {
			aBuffer.A1.vI = value;
			aBuffer.A1.qI = "";
		}
		if(aBuffer.A2.qI.equals(name)) {
			aBuffer.A2.vI = value;
			aBuffer.A2.qI = "";
		}
		if(mBuffer.M1.qI.equals(name)) {
			mBuffer.M1.vI = value;
			mBuffer.M1.qI = "";
		}
		if(mBuffer.M2.qI.equals(name)) {
			mBuffer.M2.vI = value;
			mBuffer.M2.qI = "";
		}
		if(lBuffer.L1.qI.equals(name)) {
			lBuffer.L1.vI = (int)value;
			lBuffer.L1.qI = "";
		}
		if(lBuffer.L2.qI.equals(name)) {
			lBuffer.L2.vI = (int)value;
			lBuffer.L2.qI = "";
		}
		if(sBuffer.S1.q.equals(name)) {
			sBuffer.S1.v = value;
			sBuffer.S1.q = "";
		}
		if(sBuffer.S2.q.equals(name)) {
			sBuffer.S2.v = value;
			sBuffer.S2.q = "";
		}
		
		if(aBuffer.A1.qJ.equals(name)) {
			aBuffer.A1.vJ = value;
			aBuffer.A1.qJ = "";
		}
		if(aBuffer.A2.qJ.equals(name)) {
			aBuffer.A2.vJ = value;
			aBuffer.A2.qJ = "";
		}
		if(mBuffer.M1.qJ.equals(name)) {
			mBuffer.M1.vJ = value;
			mBuffer.M1.qJ = "";
		}
		if(mBuffer.M2.qJ.equals(name)) {
			mBuffer.M2.vJ = value;
			mBuffer.M2.qJ = "";
		}
		if(lBuffer.L1.qJ.equals(name)) {
			lBuffer.L1.vJ = (int)value;
			lBuffer.L1.qJ = "";
		}
		if(lBuffer.L2.qJ.equals(name)) {
			lBuffer.L2.vJ = (int)value;
			lBuffer.L2.qJ = "";
		}
//		if(sBuffer.S1.q.equals(name)) {
//			sBuffer.S1.v = value;
//			sBuffer.S1.q = "";
//		}
//		if(sBuffer.S2.q.equals(name)) {
//			sBuffer.S2.v = value;
//			sBuffer.S2.q = "";
//		}
		
		for(int i=0; i < regFile.vR.length; i++) {
			if(regFile.vR[i] && regFile.RR[i].equals(name)) {
				regFile.R[i] = value;
				regFile.vR[i] = false;
				regFile.RR[i] = "";
			}
		}
	}
	
	public static void iInst(String instruction, int cycle) {
		for(int i=0; i < instructions2.size(); i++) {
			Inst current = instructions2.get(i);	
			if(current.instruction.equals(instruction)) {
				current.issue = cycle;
			}
		}
	}
	public static void eInst(String instruction, int cycle) {
		for(int i=0; i < instructions2.size(); i++) {
			Inst current = instructions2.get(i);	
			if(current.instruction.equals(instruction)) {
				current.execComplete = cycle;
			}
		}
	}
	public static void wInst(String instruction, int cycle) {
		for(int i=0; i < instructions2.size(); i++) {
			Inst current = instructions2.get(i);	
			if(current.instruction.equals(instruction)) {
				current.writeResult = cycle;
			}
		}
	}
	
	public static void printInsts() {
		for(int i=0; i < instructions2.size(); i++) {
			Inst current = instructions2.get(i);
			current.print();
		}
	}
	
	public static void printCycle() {
		System.out.println("Cycle: " + cycle);
		System.out.println();
		printInsts();
		System.out.println();
		regFile.print();
		System.out.println("_________________________________________________________________");
		regFile2.print();
		System.out.println("_________________________________________________________________");
		System.out.println("Add/Sub reservation stations");
		System.out.println("_________________________________________________________________");
		aBuffer.print();
		System.out.println("_________________________________________________________________");
		System.out.println("Mul/Div reservation stations");
		System.out.println("_________________________________________________________________");
		mBuffer.print();
		System.out.println("_________________________________________________________________");
		System.out.println("Load Buffer");
		System.out.println("_________________________________________________________________");
		lBuffer.print();
		System.out.println("_________________________________________________________________");
		System.out.println("Store Buffer");
		System.out.println("_________________________________________________________________");
		sBuffer.print();
	}
	
}



//Enter instruction(or 0 if done): 
//L.D F6,R0,R2
//Enter instruction(or 0 if done): 
//L.D F2,R0,R3
//Enter instruction(or 0 if done): 
//L.D F1,R1,R4
//Enter instruction(or 0 if done): 
//MUL.D F0,F2,F4
//Enter instruction(or 0 if done): 
//SUB.D F3,F6,F2
//Enter instruction(or 0 if done): 
//DIV.D F5,F0,F6
//Enter instruction(or 0 if done): 
//ADD.D F6,F3,F2
//Enter instruction(or 0 if done): 
//S.D F1,R1,R3
//Enter instruction(or 0 if done):
//0