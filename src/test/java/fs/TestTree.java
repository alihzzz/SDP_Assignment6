package fs;

class TestTree {

    final DirectoryNode root = new DirectoryNode("root");
    final DirectoryNode docs = new DirectoryNode("docs");
    final DirectoryNode archive = new DirectoryNode("archive");

    TestTree() {
        archive.add(new FileNode("old_report.txt", 300));
        docs.add(new FileNode("report.txt", 100));
        docs.add(archive);
        docs.add(new FileNode("notes.txt", 20));
        root.add(new FileNode("photo.png", 500));
        root.add(docs);
        root.add(new FileNode("readme.txt", 10));
        root.add(new DirectoryNode("empty"));
    }
}