public class MMU { //does the mapping between physical and virtual address
    private Memory memory;
    final int pageSize = 16; //the size of the page is 256 bytes or 64 words: 4 pages for each VM

    public MMU(Memory memory){
        this.memory = memory;
    }
    public int convertAddressToPhysical(int virtualAddress, PageTable pageTable){
        int pageNumber = virtualAddress/pageSize;
        int offset = virtualAddress%pageSize;
        int frameNumber = pageTable.getFrameNumber(pageNumber);

        return frameNumber * pageSize + offset;
    }
}
