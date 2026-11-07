package fs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TreeIntegrityTest {

    @Test
    void negativeFileSizeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new FileNode("a.txt", -1));
    }

    @Test
    void duplicateSiblingNameIsRejected() {
        DirectoryNode dir = new DirectoryNode("dir");
        dir.add(new FileNode("a.txt", 1));
        assertThrows(IllegalArgumentException.class, () -> dir.add(new FileNode("a.txt", 2)));
        assertEquals(1, dir.getChildren().size());
    }

    @Test
    void secondParentIsRejected() {
        DirectoryNode first = new DirectoryNode("first");
        DirectoryNode second = new DirectoryNode("second");
        FileNode file = new FileNode("a.txt", 1);
        first.add(file);
        assertThrows(IllegalStateException.class, () -> second.add(file));
        assertEquals(0, second.getChildren().size());
    }

    @Test
    void directoryCannotContainItself() {
        DirectoryNode dir = new DirectoryNode("dir");
        assertThrows(IllegalArgumentException.class, () -> dir.add(dir));
    }

    @Test
    void cycleThroughAncestorIsRejected() {
        DirectoryNode a = new DirectoryNode("a");
        DirectoryNode b = new DirectoryNode("b");
        DirectoryNode c = new DirectoryNode("c");
        a.add(b);
        b.add(c);
        assertThrows(IllegalArgumentException.class, () -> c.add(a));
    }

    @Test
    void validDeepNestingIsSupported() {
        DirectoryNode a = new DirectoryNode("a");
        DirectoryNode b = new DirectoryNode("b");
        FileNode file = new FileNode("f.txt", 5);
        a.add(b);
        b.add(file);
        assertSame(b, file.getParent());
        assertSame(a, b.getParent());
    }

    @Test
    void childrenListCannotBeModifiedFromOutside() {
        DirectoryNode dir = new DirectoryNode("dir");
        assertThrows(UnsupportedOperationException.class,
                () -> dir.getChildren().add(new FileNode("a.txt", 1)));
    }
}