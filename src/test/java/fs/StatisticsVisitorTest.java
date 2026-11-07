package fs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StatisticsVisitorTest {

    @Test
    void countsAndDepthForNestedTree() {
        TestTree tree = new TestTree();
        StatisticsVisitor visitor = new StatisticsVisitor();
        tree.root.accept(visitor);
        assertEquals(5, visitor.getFileCount());
        assertEquals(4, visitor.getDirectoryCount());
        assertEquals(3, visitor.getMaxDepth());
    }

    @Test
    void emptyRootDirectoryCountsItselfWithDepthZero() {
        StatisticsVisitor visitor = new StatisticsVisitor();
        new DirectoryNode("empty").accept(visitor);
        assertEquals(0, visitor.getFileCount());
        assertEquals(1, visitor.getDirectoryCount());
        assertEquals(0, visitor.getMaxDepth());
    }

    @Test
    void sameVisitorRunTwiceDoesNotMultiplyCounters() {
        TestTree tree = new TestTree();
        StatisticsVisitor visitor = new StatisticsVisitor();
        tree.root.accept(visitor);
        tree.root.accept(visitor);
        assertEquals(5, visitor.getFileCount());
        assertEquals(4, visitor.getDirectoryCount());
        tree.archive.accept(visitor);
        assertEquals(1, visitor.getFileCount());
        assertEquals(1, visitor.getDirectoryCount());
        assertEquals(1, visitor.getMaxDepth());
    }
}