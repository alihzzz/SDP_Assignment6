package fs;

public class StatisticsVisitor implements Visitor {

    private int fileCount = 0;
    private int directoryCount = 0;
    private int maxDepth = 0;
    private int level = 0;

    @Override
    public void visit(FileNode file) {
        if (level == 0) {
            reset();
        }
        fileCount++;
        updateMaxDepth();
    }

    @Override
    public void visit(DirectoryNode directory) {
        if (level == 0) {
            reset();
        }
        directoryCount++;
        updateMaxDepth();
        level++;
        for (Node child : directory.getChildren()) {
            child.accept(this);
        }
        level--;
    }

    public int getFileCount() {
        return fileCount;
    }
    public int getDirectoryCount() {
        return directoryCount;
    }
    public int getMaxDepth() {
        return maxDepth;
    }

    private void updateMaxDepth() {
        maxDepth = Math.max(maxDepth, level);
    }

    private void reset() {
        fileCount = 0;
        directoryCount = 0;
        maxDepth = 0;
    }
}