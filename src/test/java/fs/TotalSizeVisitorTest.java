package fs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TotalSizeVisitorTest {

    @Test
    void sumsFilesAcrossNestingLevels() {
        TestTree tree = new TestTree();
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        tree.root.accept(visitor);
        assertEquals(930, visitor.getTotalSize());
    }

    @Test
    void subtreeCountsOnlyItsOwnFiles() {
        TestTree tree = new TestTree();
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        tree.docs.accept(visitor);
        assertEquals(420, visitor.getTotalSize());
    }

    @Test
    void emptyDirectoryHasSizeZero() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        new DirectoryNode("empty").accept(visitor);
        assertEquals(0, visitor.getTotalSize());
    }

    @Test
    void sameVisitorRunTwiceGivesSameResult() {
        TestTree tree = new TestTree();
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        tree.root.accept(visitor);
        tree.root.accept(visitor);
        assertEquals(930, visitor.getTotalSize());
        tree.docs.accept(visitor);
        assertEquals(420, visitor.getTotalSize());
    }
}