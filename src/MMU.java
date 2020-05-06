public class MMU { //does the mapping between physical and virtual address
    private Memory memory;
    final int pageSize = Configuration.FRAME_SIZE; //the size of the page is 256 bytes or 64 words: 4 pages for each VM

    public MMU(Memory memory){
        this.memory = memory;
    }

    public int convertAddressToPhysical(int virtualAddress, int ptr){
        int pageNumber = virtualAddress/pageSize;
        int offset = virtualAddress%pageSize;
        int frameNumber = getFrameNumber(pageNumber, ptr);

        return frameNumber * pageSize + offset;
    }

    public void addToMemory(int value, int address, int ptr) {
        int realAddress = convertAddressToPhysical(address, ptr);
        RealMachine.addToMemory(realAddress, value);
    }

    public int getFromMemory(int address, int ptr, int mode) {
//    	if (mode == 1)
//    		return memory.getValueFromMemory(address);
        int realAddress = convertAddressToPhysical(address, ptr);
        return RealMachine.getFromMemory(realAddress);
    }

    public void freePagesFromMemory(int ptr) {
        int pagingTableIndex = ptr * Configuration.FRAME_SIZE;
        final int pageTableFrameCount = (Configuration.FRAMES_IN_MEMORY - 1) / Configuration.FRAME_SIZE + 1;
        final int pageTableLength = pageTableFrameCount * Configuration.FRAME_SIZE;
        final int lastPageTableAddress = pagingTableIndex + pageTableLength - 1;

        // Deallocate virtual machine
        for(; pagingTableIndex <= lastPageTableAddress; pagingTableIndex++) {
            int page = memory.getValueFromMemory(pagingTableIndex);
            if (page == 0) {
                break;
            }
            memory.freeFrame(page);
        }

        // Deallocate page table
        for (int i = 0; i < pageTableFrameCount; i++) {
            memory.freeFrame(ptr + i);
        }
    }

    public void createFrameMapping(int[] frames, int ptr) {
        int pagingTableIndex = ptr * Configuration.FRAME_SIZE;
        final int pagingTableLength = ((Configuration.FRAMES_IN_MEMORY - 1) / Configuration.FRAME_SIZE + 1) * Configuration.FRAME_SIZE;
        final int lastPageTableAddress = pagingTableIndex + pagingTableLength - 1;
        for(int f : frames) {
            memory.addValueToMemory(pagingTableIndex++, f);
        }

        // Add a 0 to indicate page table end
        if(pagingTableIndex <= lastPageTableAddress) {
            memory.addValueToMemory(pagingTableIndex, 0);
        }
    }

    private int getFrameNumber(int virtualPageNumber, int ptr) {
        final int pagingTableStart = ptr * Configuration.FRAME_SIZE;
        return memory.getValueFromMemory(pagingTableStart + virtualPageNumber);
    }
}
