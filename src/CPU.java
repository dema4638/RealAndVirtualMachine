public class CPU {
    public CPU(Memory memory)
    {
        mmu = new MMU(memory);
    }

    private int PTR;
    private int PC;
    private int SP;
    private int SM;
    private int SI;
    private int PI;
    private int MODE;
    private int TI;
    private int CH1;
    private int CH2;
    private int CH3;

    private MMU mmu;

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
}
