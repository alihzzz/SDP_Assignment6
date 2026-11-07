package fs;

public class TotalSizeVisitor implements Visitor {

    private long totalSize = 0;
    private int level = 0;

    @Override
    public void visit(FileNode file) {
        if (level == 0) {
            totalSize = 0;
        }
        totalSize += file.getSize();
    }

    @Override
    public void visit(DirectoryNode directory) {
        if (level == 0) {
            totalSize = 0;
        }
        level++;
        for (Node child : directory.getChildren()) {
            child.accept(this);
        }
        level--;
    }

    public long getTotalSize() {
        return totalSize;
    }
}