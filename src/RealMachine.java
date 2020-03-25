import javax.print.attribute.standard.PrinterStateReason;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RealMachine {

    //Puslapiavimo mechanizmas
    private static CPU cpu = new CPU();
    private static Memory memory = new Memory(16, 16);
    private static Compiler compiler = new Compiler();

   // private static final int[] code;
   /* {
           //X to the power of Y
           Instruction.READ.getCode(),
            Instruction.WD.getCode(),0,0,
           Instruction.READ.getCode(),
            Instruction.WD.getCode(),0,1,
            Instruction.PUSH.getCode(),1,
            Instruction.WD.getCode(),0,2,
            Instruction.LD.getCode(),0,1,
            Instruction.JE.getCode(),36,
            Instruction.PUSH.getCode(),1,
            Instruction.SUB.getCode(),
            Instruction.WD.getCode(),0,1,
            Instruction.LD.getCode(),0,2,
            Instruction.LD.getCode(),0,0,
            Instruction.MUL.getCode(),
            Instruction.WD.getCode(),0,2,
            Instruction.JMP.getCode(),13,
            Instruction.LD.getCode(),0,2,
            Instruction.PRINT.getCode(),
            Instruction.HALT.getCode()


    };*/


    private static final int[] data = new int[10];
    private static final int stackSize = 1000;

    //private static VirtualMachine currentVm = new VirtualMachine(code, data, stackSize, memory);
    private static VirtualMachine currentVm;

    public static void main(String[] args) {
        ArrayList<String> program = compiler.readFile();
        currentVm = new VirtualMachine(compiler.getCode(program), data, stackSize, memory);
        while(true) {
            if (currentVm != null) {
                if (currentVm.execute() != 0) {
                    currentVm = null;
                }
            }
        }
    }



}
