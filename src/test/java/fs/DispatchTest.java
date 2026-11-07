package fs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DispatchTest {

    private static class RecordingVisitor implements Visitor {
        final List<String> calls = new ArrayList<>();

        @Override
        public void visit(FileNode file) {
            calls.add("file:" + file.getName());
        }

        @Override
        public void visit(DirectoryNode directory) {
            calls.add("directory:" + directory.getName());
        }
    }

    @Test
    void fileCallsFileOverload() {
        RecordingVisitor visitor = new RecordingVisitor();
        new FileNode("a.txt", 1).accept(visitor);
        assertEquals(List.of("file:a.txt"), visitor.calls);
    }

    @Test
    void directoryCallsDirectoryOverload() {
        RecordingVisitor visitor = new RecordingVisitor();
        new DirectoryNode("dir").accept(visitor);
        assertEquals(List.of("directory:dir"), visitor.calls);
    }
}