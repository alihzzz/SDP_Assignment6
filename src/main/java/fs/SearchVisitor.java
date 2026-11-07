package fs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchVisitor implements Visitor {

    private final String nameContains;
    private final long minSize;

    private final List<String> results = new ArrayList<>();
    private final List<String> currentPath = new ArrayList<>();
    private int level = 0;

    public SearchVisitor(String nameContains, long minSize) {
        if (nameContains == null) {
            throw new IllegalArgumentException("Name criterion must not be null");
        }
        this.nameContains = nameContains;
        this.minSize = minSize;
    }

    @Override
    public void visit(FileNode file) {
        if (level == 0) {
            results.clear();
        }
        if (matches(file)) {
            results.add(buildPath(file.getName()));
        }
    }

    @Override
    public void visit(DirectoryNode directory) {
        boolean isRoot = (level == 0);
        if (isRoot) {
            results.clear();
        } else {
            currentPath.add(directory.getName());
        }
        level++;
        for (Node child : directory.getChildren()) {
            child.accept(this);
        }
        level--;
        if (!isRoot) {
            currentPath.remove(currentPath.size() - 1);
        }
    }

    public List<String> getResults() {
        List<String> sorted = new ArrayList<>(results);
        Collections.sort(sorted);
        return sorted;
    }

    private boolean matches(FileNode file) {
        return file.getName().contains(nameContains) && file.getSize() >= minSize;
    }

    private String buildPath(String fileName) {
        if (currentPath.isEmpty()) {
            return fileName;
        }
        return String.join("/", currentPath) + "/" + fileName;
    }
}