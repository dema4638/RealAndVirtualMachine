public class CPU {

    public CPU(MMU mmu)
    {
        this.mmu = mmu;
    }
    
    public void initGUIReference(GUI gui)
    {
    	this.gui = gui;
    }

    private int PTR;
    private int PC;
    private int SP;
    private int SM;
    private int SI=0; //Saugo supervizorinio pertraukimo komandos indeksa
    private int PI=0;  //Saugo programinio pertraukimo komandos indeksa
    private int MODE = 1; //SUPERVISOR = 1; USER = 0;
    private int TI = 15;
    private int CH1;
    private int CH2;
    private int CH3;

    private MMU mmu;
	private GUI gui;

    public void execute(Instruction instruction, int[]operands){
        int value;
        int mode = getMODE();
        System.out.println(instruction);
        switch(instruction) {
            case ADD:
                value = mmu.getFromMemory(SP-1, PTR, mode) + mmu.getFromMemory(SP, PTR, mode);
                mmu.addToMemory(value, --SP, PTR);
                break;
            case SUB:
                value = mmu.getFromMemory(SP-1, PTR, mode) - mmu.getFromMemory(SP, PTR, mode);
                mmu.addToMemory(value, --SP, PTR);
                break;
            case MUL:
                value = mmu.getFromMemory(SP-1, PTR, mode) * mmu.getFromMemory(SP, PTR, mode);
                mmu.addToMemory(value, --SP, PTR);
                break;
            case DIV:
                value = mmu.getFromMemory(SP-1, PTR, mode) / mmu.getFromMemory(SP, PTR, mode);
                mmu.addToMemory(value, --SP, PTR);
                break;
            case HALT:
                setSI(1);
                break;
            case PUSH:
                mmu.addToMemory(operands[0], ++SP, PTR);
                break;
            case WRITE:
                System.out.println(mmu.getFromMemory(SP, PTR, mode));
                SP--;
                break;
            case LD:
                value = mmu.getFromMemory(16*operands[0]+operands[1], PTR, mode);
                mmu.addToMemory(value, ++SP, PTR);
                break;
            case WD:
                mmu.addToMemory(mmu.getFromMemory(SP, PTR, mode),16*operands[0]+operands[1], PTR);
                break;
//            case SLD:v b
//                stack[++SP] = memory.getValueFromMemory(16*operands[0]+operands[1]);
//                SP++;
//                break;
//            case SWD:
//                memory.addValueToMemory(16*operands[0]+operands[1],stack[SP]);
//                break;
            case JMP:
                PC = operands[0];
                break;
            case JE:
                if (mmu.getFromMemory(SP, PTR, mode) == 0) {
                    PC = operands[0];
                }
                break;
            case JG:
                if (mmu.getFromMemory(SP, PTR, mode) == 2) {
                    PC = operands[0];
                }
                break;
            case JL:
                if (mmu.getFromMemory(SP, PTR, mode) == 1) {
                    PC = operands[0];
                }
                break;
            case PRINT:
            	setSI(3);
                break;
            case READ:
            	setSI(2);
                break;
            case CMP:
                if (mmu.getFromMemory(SP, PTR, mode) == mmu.getFromMemory(SP - 1, PTR, mode)){
                    mmu.addToMemory(0, --SP, PTR);
                }
                else if (mmu.getFromMemory(SP, PTR, mode) < mmu.getFromMemory(SP - 1, PTR, mode)){
                    mmu.addToMemory(1, --SP, PTR);
                }
                else{
                    mmu.addToMemory(2, --SP, PTR);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + instruction);
                //PI=1;
        }
    }
    

    public void resetInterrupts(){
        SI=0;
        PI=0;
    }
    public void resetTimer(){
        TI=15;
    }

    public int getPTR() {
        return PTR;
    }

    public void setPTR(int PTR) {
        this.PTR = PTR;
    }

    public int getPC() {
        return PC;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public int getSP() {
        return SP;
    }

    public void setSP(int SP) {
        this.SP = SP;
    }

    public int getSM() {
        return SM;
    }

    public void setSM(int SM) {
        this.SM = SM;
    }

    public int getSI() {
        return SI;
    }

    public void setSI(int SI) {
        this.SI = SI;
    }

    public int getPI() {
        return PI;
    }

    public void setPI(int PI) {
        this.PI = PI;
    }

    public int getMODE() {
        return MODE;
    }

    public void setMODE(int MODE) {
        this.MODE = MODE;
    }

    public int getTI() {
        return TI;
    }

    public void setTI(int TI) {
        this.TI = TI;
    }

    public int getCH1() {
        return CH1;
    }

    public void setCH1(int CH1) {
        this.CH1 = CH1;
    }

    public int getCH2() {
        return CH2;
    }

    public void setCH2(int CH2) {
        this.CH2 = CH2;
    }

    public int getCH3() {
        return CH3;
    }

    public void setCH3(int CH3) {
        this.CH3 = CH3;
    }

    public MMU getMmu() {
        return mmu;
    }

    public int getAndIncrementPC() {
        return PC++;
    }
}
