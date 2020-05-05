public enum Instruction{
    //x - pirmas operandas, y - antras operandas, z -...
    ADD (0), //Sudeda 2 pirmas steko reiksmes, jas isima ir ideda suma
    SUB (1), //Atima 2 pirmas steko reiksmes (pirma-antra), jas isima ir ideda skirtuma
    MUL (2), //Sudaugina 2 pirmas steko reiksmes, jas isima ir ideda sandauga
    DIV(3), //Atlieka dalyba 2 steko reiksmem (pirma/antra), jas isima ir ideda dalmeni
    HALT (4), //programos sutabdymas,
    PUSH (5, 1), //Ideda x reiksme i steka
    WRITE (6), //Isimti reiksme is steko virsunes ir ja atspausdinti
    LD(7, 2), //Is duomenu srities (16 * x + y adresu) reiksme ideti i steka
    WD (8, 2), //Is steko ideti reiksme i duomenu sriti (16 * x + y adresu);
    SLD (9, 2), //Is bendros atminties (16 * x + y adresu) ir italpina i steko virsune
    SWD (10, 2), //I bendra atminti (16 * x + y adresu) italpina is steko virsunes reiksme
    JMP (11, 1), //Pereiti i x lokacija kodo plote.
    JE (12, 1), //Jei steko viršūnėje yra simbolis 0, pereiti į x lokaciją kodo plote.
    JG (13, 1), //Jei steko viršūnėje yra 2, pereiti į x lokaciją kodo plote.
    JL (14, 1), //Jei steko viršūnėje yra 1, pereiti į x lokaciją kodo plote.
    PRINT (15), //Isvesti reiksme, esancia steko virsuneje
    READ (16), //Nuskaityti vartotojo pateikta reiksme ir iraso i steko virsune
    CMP (17); //Palygina 2 reiksmes steko virsuneje. Isimti abi reiksmes ir patalpinti i steko virsune:
                    // 0, jei reiksmes lygios, 2 jei antra didesne, 1 jei pirma didesne

	// C:\Users\kristupas.lunskas\Desktop\Universitetas\OS\RealAndVirtualMachine\power.txt
	
    private final int code;
    private final int operandCount;

    Instruction(int code)
    {
        this(code, 0);
    }

    Instruction(int code, int operandCount)
    {
        this.code = code;
        this.operandCount = operandCount;
    }

    public int getCode() {
        return code;
    }

    public int getOperandCount() {
        return operandCount;
    }
}
