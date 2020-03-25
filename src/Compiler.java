import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Compiler {


    public ArrayList<String> readFile(){
        ArrayList<String> program = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(("program1.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String line = reader.readLine();
            while(line!=null){
                program.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return program;
    }

    public int[] getCode(ArrayList<String> program){
        List<Integer> programCode = new ArrayList<>();
        for (String line : program) {
            String[] splittedString = line.split("\\s+");
            int code = getInstructionCode(splittedString[0]);
            programCode.add(code);
            for (int i = 1; i<splittedString.length; i++){
                programCode.add(Integer.parseInt(splittedString[i]));
            }
        }
        int[] arrOfCode = new int[programCode.size()];
        return Arrays.stream(programCode.toArray()).mapToInt(x -> (int)x).toArray();
    }

    public int getInstructionCode(String instruction){
        switch (instruction) {
            case "ADD":
                return Instruction.ADD.getCode();
            case "SUB":
                return Instruction.SUB.getCode();
            case "MUL":
                return Instruction.MUL.getCode();
            case "DIV":
                return Instruction.DIV.getCode();
            case "HALT":
                return Instruction.HALT.getCode();
            case "PUSH":
                return Instruction.PUSH.getCode();
            case "WRITE":
                return Instruction.WRITE.getCode();
            case "LD":
                return Instruction.LD.getCode();
            case "WD":
                return Instruction.WD.getCode();
            case "SLD":
                return Instruction.SLD.getCode();
            case "SWD":
                return Instruction.SWD.getCode();
            case "JMP":
                return Instruction.JMP.getCode();
            case "JE":
                return Instruction.JE.getCode();
            case "JG":
                return Instruction.JG.getCode();
            case "JL":
                return Instruction.JL.getCode();
            case "PRINT":
                return Instruction.PRINT.getCode();
            case "READ":
                return Instruction.READ.getCode();
            case "CMP":
                return Instruction.CMP.getCode();
        }
            throw new IllegalArgumentException();
    }

}
