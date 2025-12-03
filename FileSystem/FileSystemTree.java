/**
 * Represents an entire file system hierarchy.
 * This class owns the root directory and can provide access to it.
 */
public class FileSystemTree {

    private FolderNode root;

    /**
     * Constructs a file system with a single root directory.
     * The root has no parent and typically uses a fixed name such as "/".
     */
    public FileSystemTree() {
        this.root = new FolderNode("/", null); // or equivalent
    }

    /**
     * Provides the root of the directory tree so callers can navigate from the top.
     */
    public FolderNode getRoot() {
        return root;
    }
}
