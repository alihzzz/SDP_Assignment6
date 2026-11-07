package fs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryNode extends Node {

    private final List<Node> children = new ArrayList<>();

    public DirectoryNode(String name) {
        super(name);
    }
    public void add(Node child) {
        if (child == null) {
            throw new IllegalArgumentException("Child must not be null");
        }
        if (child.getParent() != null) {
            throw new IllegalStateException("'" + child.getName() + "' already has a parent");
        }
        if (isSelfOrAncestorOf(child)) {
            throw new IllegalArgumentException("Adding '" + child.getName() + "' would create a cycle");
        }
        if (hasChildNamed(child.getName())) {
            throw new IllegalArgumentException("Duplicate name in directory: " + child.getName());
        }
        children.add(child);
        child.setParent(this);
    }

    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    private boolean isSelfOrAncestorOf(Node child) {
        DirectoryNode current = this;
        while (current != null) {
            if (current == child) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    private boolean hasChildNamed(String name) {
        for (Node existing : children) {
            if (existing.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}