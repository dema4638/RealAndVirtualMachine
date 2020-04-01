public class PageTable {
    int[] pageTable;

    public PageTable(int[] frames) {
        int virtualIndex = 0;
        pageTable = new int[frames.length];
        for (int fr : frames) {
            pageTable[virtualIndex++] = fr;
        }
    }

    public int[] getPageTable() {
        return pageTable;
    }

    public int getFrameNumber(int virtualPageNumber){
        return pageTable[virtualPageNumber];
    }

}
