/**
 * Represents a file in the file system tree.
 * A file is a leaf node that has a fixed size and no children.
 */
public class FileNode extends FileSystemNode {

    private int size;

    /**
     * Constructs a file with a given parent, name, and size in bytes (or units).
     */
    public FileNode(FolderNode parent, String name, int size) {
        super(name, parent);
        this.size = size;
    }

    @Override
    public boolean isFolder() {
        return false;
    }

    /**
     * Returns the stored size for this file.
     */
    public int getFileSize() {
        return size;
    }

    @Override
    public int getHeight() {
        // Files are leaf nodes and therefore have value 0.
        return 0;
    }

    @Override
    public int getSize() {
        // The total contribution of a file is its own size.
        return 0;
    }

    @Override
    public int getTotalNodeCount() {
        // A single file contributes exactly one node.
        return 0;
    }
}
