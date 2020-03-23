public class Memory {
    PageTable pageTable = new PageTable();
    int[] memory;

    public Memory(int words, int wordLength) {
        memory = new int[words * wordLength];
    }

    public void addValueToMemory(int address, int value) {
        memory[address] = value;
    }

    public int getValueFromMemory(int address) {
        return memory[address];
    }
}
