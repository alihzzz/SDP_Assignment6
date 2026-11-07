package fs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class SearchVisitorTest {

    @Test
    void combinesNameAndSizeConditions() {
        TestTree tree = new TestTree();
        SearchVisitor visitor = new SearchVisitor("report", 150);
        tree.root.accept(visitor);
        assertEquals(List.of("docs/archive/old_report.txt"), visitor.getResults());
    }

    @Test
    void thresholdIsInclusive() {
        TestTree tree = new TestTree();
        SearchVisitor visitor = new SearchVisitor("report", 100);
        tree.root.accept(visitor);
        assertEquals(List.of("docs/archive/old_report.txt", "docs/report.txt"), visitor.getResults());
    }

    @Test
    void sizeConditionAloneFiltersByName() {
        TestTree tree = new TestTree();
        SearchVisitor visitor = new SearchVisitor(".png", 0);
        tree.root.accept(visitor);
        assertEquals(List.of("photo.png"), visitor.getResults());
    }

    @Test
    void pathsAreSortedAndRelativeToVisitedRoot() {
        TestTree tree = new TestTree();
        SearchVisitor visitor = new SearchVisitor(".txt", 0);
        tree.docs.accept(visitor);
        assertEquals(List.of("archive/old_report.txt", "notes.txt", "report.txt"), visitor.getResults());
    }

    @Test
    void noMatchGivesEmptyList() {
        TestTree tree = new TestTree();
        SearchVisitor visitor = new SearchVisitor("xyz", 0);
        tree.root.accept(visitor);
        assertTrue(visitor.getResults().isEmpty());
    }

    @Test
    void emptyDirectoryGivesEmptyList() {
        SearchVisitor visitor = new SearchVisitor("", 0);
        new DirectoryNode("empty").accept(visitor);
        assertTrue(visitor.getResults().isEmpty());
    }

    @Test
    void sameVisitorRunTwiceDoesNotDuplicateResults() {
        TestTree tree = new TestTree();
        SearchVisitor visitor = new SearchVisitor("report", 0);
        tree.root.accept(visitor);
        tree.root.accept(visitor);
        assertEquals(2, visitor.getResults().size());
    }
}