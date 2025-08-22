import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CMA { // Continuous Memory Allocator
    private int memorySize;
    private ArrayList<Block> blocks = null;

    public CMA() {
        this.memorySize = 1024;
        this.blocks = new ArrayList<>();
    }

    public void setMemorySize(int size) {
        this.memorySize = size;
        this.blocks.clear();

        Block b = new Block(null, 0, this.memorySize);
        this.blocks.add(b);
    }

    public boolean firstFitRequest(String name, int size) {
        for (Block block : blocks) {
            if (block.getProcessName() == null && block.getSize() >= size) {
                if (block.getSize() >= size) {
                    blocks.add(new Block(null, block.getStart() + size, block.getSize() - size));
                    block.setSize(size);
                }
                block.setProcessName(name);
                return true;
            }
        }
        return false;
    }

    public boolean bestFitRequest(String name, int size) {
        Block bestBlock = null;

        for (Block block : blocks) {
            if (block.getProcessName() == null && block.getSize() >= size) {
                if (bestBlock == null || block.getSize() < bestBlock.getSize()) {
                    bestBlock = block;
                }
            }
        }

        if (bestBlock != null) {
            bestBlock.setProcessName(name);

            if (bestBlock.getSize() > size) {
                blocks.add(new Block(null, bestBlock.getStart() + size, bestBlock.getSize() - size));
                bestBlock.setSize(size);
            }

            return true;
        }
        return false;
    }

    public boolean worstFitRequest(String name, int size) {
        Block worstBlock = null;

        for (Block block : blocks) {
            if (block.getProcessName() == null && block.getSize() >= size) {
                if (worstBlock == null || block.getSize() > worstBlock.getSize()) {
                    worstBlock = block;
                }
            }
        }

        if (worstBlock != null) {
            worstBlock.setProcessName(name);
            if (worstBlock.getSize() > size) {
                blocks.add(new Block(null, worstBlock.getStart() + size, worstBlock.getSize() - size));
                worstBlock.setSize(size);
            }
            return true;
        }

        return false;
    }

    private void sumFreeBlocks() {
        Collections.sort(blocks, new Comparator<Block>() {
            public int compare(Block block1, Block block2) {
                return block1.getStart() - block2.getStart();
            }
        });

        for (int i = 0; i < blocks.size() - 1; i++) {
            Block currentBlock = blocks.get(i);
            Block nextBlock = blocks.get(i + 1);

            if (currentBlock.getProcessName() == null && nextBlock.getProcessName() == null) {
                int combinedSize = currentBlock.getSize() + nextBlock.getSize();
                currentBlock.setSize(combinedSize);

                blocks.remove(i + 1);

                i--;
            }
        }
    }

    public void compact() {
        sumFreeBlocks();

        int address = 0;

        for (Block block : blocks) {
            if (block.getProcessName() != null) {
                block.setStart(address);
                address += block.getSize();
            }
        }
    }

    public boolean release(String name) {
        for (Block block : blocks) {
            if (block.getProcessName() != null && block.getProcessName().equals(name)) {
                block.setProcessName(null);
                sumFreeBlocks();
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String res = "";
        for (Block block : blocks) {
            res += block.toString() + "\n";
        }
        return res;
    }
}

class Block {
    private String processName; // null: Free
    private int start;
    private int size;

    public Block(String processName, int start, int size) {
        this.processName = processName;
        this.start = start;
        this.size = size;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        if (this.processName == null)
            return "Block [" + this.start + ":" + (this.start + this.size - 1) + "] Unused";
        else
            return "Block [" + this.start + ":" + (this.start + this.size - 1) + " " + this.processName;
    }
}
