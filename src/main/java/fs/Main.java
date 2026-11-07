package fs;

public class Main {

    public static void main(String[] args) {
        DirectoryNode root = buildDemoTree();

        TotalSizeVisitor sizeVisitor = new TotalSizeVisitor();
        root.accept(sizeVisitor);
        System.out.println("Total size: " + sizeVisitor.getTotalSize());

        SearchVisitor searchVisitor = new SearchVisitor("report", 150);
        root.accept(searchVisitor);
        System.out.println("Search (name contains 'report', size >= 150):");
        for (String path : searchVisitor.getResults()) {
            System.out.println("  " + path);
        }

        StatisticsVisitor statisticsVisitor = new StatisticsVisitor();
        root.accept(statisticsVisitor);
        System.out.println("Files: " + statisticsVisitor.getFileCount());
        System.out.println("Directories: " + statisticsVisitor.getDirectoryCount());
        System.out.println("Max depth: " + statisticsVisitor.getMaxDepth());
    }

    private static DirectoryNode buildDemoTree() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode docs = new DirectoryNode("docs");
        DirectoryNode archive = new DirectoryNode("archive");

        archive.add(new FileNode("old_report.txt", 300));
        docs.add(new FileNode("report.txt", 100));
        docs.add(archive);
        docs.add(new FileNode("notes.txt", 20));

        root.add(new FileNode("photo.png", 500));
        root.add(docs);
        root.add(new FileNode("readme.txt", 10));
        root.add(new DirectoryNode("empty"));
        return root;
    }
}