import java.util.ArrayList;
import java.util.List;

/**
 * Represents a directory in the file system tree. A directory can contain other directories and
 * files as children.
 */
public class FolderNode extends FileSystemNode {

    private List<FileSystemNode> children;

    public FolderNode(String name, FolderNode parent) {
        super(name, parent);
        this.children = new ArrayList<>();
    }


    @Override
    public boolean isFolder() {
        return true;
    }

    /**
     * Returns a list view of the children contained directly inside this directory. Modifying the
     * returned list is not required to be supported.
     */
    public List<FileSystemNode> getChildren() {
        List<FileSystemNode> list = new ArrayList<>();
        children.forEach(c -> list.add(c));
        return list;
    }

    /**
     * Searches the children of this directory for a node whose name matches the input. Only direct
     * children are considered, not deeper descendants.
     */
    public FileSystemNode getChildByName(String childName) {
        for (var node : children) {
            if (node.getName() == childName) {
                return node;
            }
        }
        return null;
    }

    /**
     * Creates a new file directly inside this directory with the given name and size. If a child
     * with the same name already exists, no file is created and false is returned. Otherwise the
     * new file is added and true is returned.
     */
    public boolean addFile(String fileName, int size) {
        for (var node : children)
            if (node.getName() == fileName)
                return false;
        children.add(new FileNode(fileName, this, size));
        return true;
    }

    /**
     * Creates a new subdirectory directly inside this directory with the given name. If a child
     * with the same name already exists, no folder is created and false is returned. Otherwise the
     * new folder is added and true is returned.
     */
    public boolean addFolder(String folderName) {
        for (var node : children)
            if (node.getName() == folderName)
                return false;
        children.add(new FolderNode(folderName, this));
        return true;
    }

    /**
     * Searches this directory and all of its descendants for nodes whose name matches the input.
     * When a match is found, its full path can be printed by the caller using toString().
     */
    public boolean containsNameRecursive(String searchName) {
        for (var node : children)
            if (searchName == node.getName())
                return true;
            else if (node.getClass() == FolderNode.class)
                if (((FolderNode) node).containsNameRecursive(searchName))
                    return true;
        return false;
    }

    @Override
    public int getHeight() {
        var max = 0;
        for (var node : children)
            max = Math.max(node.getHeight() + 1, max);
        return max;
    }

    @Override
    public int getSize() {
        var loc = 0;
        for (var child : children)
            loc += child.getSize();
        return loc;
    }

    @Override
    public int getTotalNodeCount() {
        var loc = 1;
        for (var child : children)
            loc += child.getTotalNodeCount();
        return loc;
    }
}
