import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Handles interactive navigation of the file system This class reads commands from standard input,
 * interprets them, and invokes operations on the current directory node.
 */
public class Navigator {

    private final FileSystemTree fileSystem;
    private FolderNode currentDirectory;
    private boolean shouldExit;

    /**
     * Constructs a navigator for a given file system tree. The starting location is the root
     * directory.
     */
    public Navigator(FileSystemTree fst) {
        this.fileSystem = fst;
        this.currentDirectory = fst.getRoot();
    }

    /**
     * Starts a command loop that repeatedly reads a line of input, interprets it as a command with
     * arguments, and executes it until a request to terminate is received.
     */
    public void run() {
        shouldExit = false;
        Scanner kb = new Scanner(System.in);

        while (!shouldExit) {
            // Prompt can be customized to show current path if desired.
            String line = kb.nextLine();
            processUserInputString(line);
        }

        kb.close();
    }

    /**
     * Changes the current directory based on a single path argument. Behavior should mirror typical
     * Unix shells: - "." refers to the current directory (no change). - ".." moves to the parent
     * directory (if one exists). - Paths starting with "/" are interpreted from the root directory.
     * - Other paths are interpreted relative to the current directory.
     */
    private void cd(String[] args) {
        if (args.length == 0)
            return;
        var directory = args[0];
        var dirs = new ArrayList<String>();
        var tempDir = this.currentDirectory;
        if (directory.length() > 0 && directory.charAt(0) == '/') {
            tempDir = fileSystem.getRoot();
            directory = directory.substring(1);
        }
        var curr = new StringBuilder();
        var i = 0;
        while (i < directory.length()) {
            while (i < directory.length() && directory.charAt(i) != '/')
                curr.append(directory.charAt(i++));
            if (curr.length() > 0) {
                dirs.add(curr.toString());
                curr = new StringBuilder();
            }
            i++;
        }
        var res = cd_rec(dirs, tempDir);
        if (res != null) {
            this.currentDirectory = res;
        }
    }

    private FolderNode cd_rec(ArrayList<String> full_dir, FolderNode temp_curr) {
        if (full_dir.size() == 0)
            return temp_curr;
        var next = full_dir.get(0);
        if (next.equals(".")) {
            full_dir = new ArrayList<String>(full_dir.subList(1, full_dir.size()));
            return cd_rec(full_dir, temp_curr);
        }
        if (next.equals("..")) {
            if (temp_curr == this.fileSystem.getRoot()) {
                System.out.println("cannot go back from root");
                return null;
            }
            return cd_rec(new ArrayList<String>(full_dir.subList(1, full_dir.size())),
                    temp_curr.getParent());
        }
        boolean found = false;
        for (var child : temp_curr.getChildren()) {
            if (child.getName().equals(next)) {
                if (child.getClass() == FileNode.class) {
                    System.out.println("cannot cd into a file");
                    return null;
                }
                temp_curr = (FolderNode) child;
                full_dir.remove(0);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Could not cd into such directory");
            return null;
        }
        if (full_dir.size() == 0)
            return temp_curr;
        return cd_rec(full_dir, temp_curr);
    }

    /**
     * Lists all items contained directly in the current directory. Output formatting can mirror
     * typical file system listings.
     */
    private void ls(String[] args) {
        if (args.length == 0)
            args = new String[] {"./"};
        var directory = args[0];
        var dirs = new ArrayList<String>();
        var tempDir = this.currentDirectory;
        if (directory.length() > 0 && directory.charAt(0) == '/') {
            tempDir = fileSystem.getRoot();
            directory = directory.substring(1);
        }
        var curr = new StringBuilder();
        var i = 0;
        while (i < directory.length()) {
            while (i < directory.length() && directory.charAt(i) != '/')
                curr.append(directory.charAt(i++));
            if (curr.length() > 0) {
                dirs.add(curr.toString());
                curr = new StringBuilder();
            }
            i++;
        }
        var res = cd_rec(dirs, tempDir);
        for (var child : res.getChildren()) {
            if (child.isFolder())
                System.out.println(child.getName() + "/");
            else
                System.out.println(child.getName());

        }
    }

    /**
     * Creates a new directory inside the current directory using the provided name.
     */
    private void mkdir(String[] args) {
        if (args.length == 0)
            return;
        var directory = args[0];
        var dirs = new ArrayList<String>();
        var tempDir = this.currentDirectory;
        if (directory.length() > 0 && directory.charAt(0) == '/') {
            tempDir = fileSystem.getRoot();
            directory = directory.substring(1);
        }
        var curr = new StringBuilder();
        var i = 0;
        while (i < directory.length()) {
            while (i < directory.length() && directory.charAt(i) != '/')
                curr.append(directory.charAt(i++));
            if (curr.length() > 0) {
                dirs.add(curr.toString());
                curr = new StringBuilder();
            }
            i++;
        }
        var to_be_made = dirs.remove(dirs.size() - 1);
        var res = cd_rec(dirs, tempDir);
        for (var child : res.getChildren()) {
            if (child.getName().equals(args[0])) {
                System.out.println("duplicate directory: " + args[0]);
                return;
            }
        }
        res.addFolder(to_be_made);
    }

    /**
     * Creates a new file inside the current directory with a given name and size.
     */
    private void touch(String[] args) {
        if (args.length < 2) {
            System.out.println("expected 2 arguments");
            return;
        }
        
        var directory = args[0];
        var dirs = new ArrayList<String>();
        var tempDir = this.currentDirectory;
        if (directory.length() > 0 && directory.charAt(0) == '/') {
            tempDir = fileSystem.getRoot();
            directory = directory.substring(1);
        }
        var curr = new StringBuilder();
        var i = 0;
        while (i < directory.length()) {
            while (i < directory.length() && directory.charAt(i) != '/')
                curr.append(directory.charAt(i++));
            if (curr.length() > 0) {
                dirs.add(curr.toString());
                curr = new StringBuilder();
            }
            i++;
        }
        var size = Integer.parseInt(args[1]);
        var to_be_made = dirs.remove(dirs.size() - 1);
        var res = cd_rec(dirs, tempDir);
        res.addFile(to_be_made, size);
    }

    /**
     * Searches the current directory and its descendants for nodes with a given name and prints
     * their paths.
     */
    private void find(String[] args) {
        if (args.length == 0)
            return;
        var name = args[0];
        var ress = new ArrayList<FileSystemNode>();
        for (var child : this.currentDirectory.getChildren()) {
            if (child.getName().equals(name))
                ress.add(child);
            if (child.isFolder()) {
                var n = find_rec((FolderNode) child, name);
                if (n.size() > 0)
                    ress.addAll(n);
            }
        }

        var saved_dir = this.currentDirectory;
        for (var node : ress) {
            if (node.isFolder()) {
                this.currentDirectory = (FolderNode) node;
                pwd(new String[] {});
            } else {
                var temp_file = node.getName();
                this.currentDirectory = node.getParent();
                pwd(args);
                System.out.print("/" + temp_file);
                System.out.println();
            }
        }
        this.currentDirectory = saved_dir;
    }

    private ArrayList<FileSystemNode> find_rec(FolderNode node, String name) {
        var nodes_matching = new ArrayList<FileSystemNode>();
        for (var child : node.getChildren()) {
            if (child.getName().equals(name))
                nodes_matching.add(child);
            if (child.isFolder()) {
                var n = find_rec((FolderNode) child, name);
                if (!n.isEmpty())
                    nodes_matching.addAll(n);
            }
        }
        return nodes_matching;
    }

    /**
     * Prints the absolute path of the current directory, from the root to this node.
     */
    private void pwd(String[] args) {
        var curr = this.currentDirectory;
        if (curr == this.fileSystem.getRoot()) {
            System.out.println("/");
            return;
        }
        var wd = new StringBuilder();
        while (curr != this.fileSystem.getRoot()) {
            wd.insert(0, "/" + curr.getName());
            curr = curr.getParent();
        }
        // wd.insert(0, "/");
        if (args.length > 0)
            System.out.print(wd);
        else
            System.out.println(wd);
    }

    // ├── │ └──
    private void tree(String[] args) {
        var held_dir = this.currentDirectory;
        cd(args);

        var tree = new ArrayList<String>();
        for (int i = 0; i < ((FolderNode) this.currentDirectory).getChildren().size(); i++) {
            var child = this.currentDirectory.getChildren().get(i);
            tree_rec(child, tree, 0, i == this.currentDirectory.getChildren().size() - 1);
        }
        tree.forEach(System.out::println);
        this.currentDirectory = held_dir;
    }

    private void tree_rec(FileSystemNode node, ArrayList<String> tree, int depth,
            boolean is_last_in_folder) {
        tree.add(node_and_depth_to_string(node, depth, is_last_in_folder));
        if (node.isFolder()) {
            var children = ((FolderNode) node).getChildren();
            var num_children = children.size();
            for (int i = 0; i < children.size(); i++) {
                var child = children.get(i);
                tree_rec(child, tree, depth + 1, i == num_children - 1);
            }
        }
    }

    private String node_and_depth_to_string(FileSystemNode node, int depth,
            boolean is_last_in_folder) {
        var str = new StringBuilder();
        if (depth > 0)
            for (int i = 0; i < depth; i++) {
                if (i == 0)
                    str.append("│   ");
                else
                    str.append("    ");
            }
        if (is_last_in_folder)
            str.append("└──");
        else
            str.append("├──");
        str.append(" " + node.getName());
        if (node.isFolder())
            str.append("/");
        return str.toString();
    }

    /**
     * Displays the contents of the current directory as a tree, optionally respecting flags or
     * depth limits if provided by the arguments.
     */
    // private void tree(String[] args) {

    // var held_dir = this.currentDirectory;
    // cd(args);
    // var dirs = new String[this.currentDirectory.getTotalNodeCount()];
    // var children = this.currentDirectory.getChildren();
    // var u = 0;
    // for (int i = 0; i < children.size(); i++) {
    // var child = children.get(i);
    // var added = "";
    // if (child.isFolder())
    // added = "/";
    // if (i == children.size() - 1) {
    // dirs[u++] = "└──" + child.getName() + added;
    // for (int j = u; j < u + child.getTotalNodeCount() - 1; j++) {
    // dirs[j] = " ";
    // }
    // if (child.isFolder())
    // u = tree_rec(child, dirs, u) + 1;
    // break;
    // }
    // var n = u;
    // dirs[u++] = "├──" + child.getName() + added;
    // for (int j = u; j < u + child.getTotalNodeCount(); j++) {
    // dirs[j] = "│ ";
    // }
    // if (child.isFolder())
    // u = tree_rec(child, dirs, n + 1) + 1;
    // }
    // this.currentDirectory = held_dir;
    // (new ArrayList<>(Arrays.asList(dirs))).forEach(dir -> {
    // if (dir != null)
    // System.out.println(dir);
    // });
    // }

    // private int tree_rec(FileSystemNode node, String[] dirs, int u) {
    // if (u == dirs.length)
    // return u;
    // if (dirs[u] == null)
    // dirs[u] = "";
    // if (node.isFolder()) {
    // var children = ((FolderNode) node).getChildren();
    // for (int i = 0; i < children.size(); i++) {
    // var child = children.get(i);
    // var added = "";
    // if (child.isFolder())
    // added = "/";
    // if (i == children.size() - 1) {
    // dirs[u++] += "└──" + child.getName() + added;
    // for (int j = u; j < u + child.getTotalNodeCount(); j++) {
    // dirs[j] += " ";
    // }
    // if (child.isFolder()) {
    // u = tree_rec(child, dirs, u) + 1;
    // }
    // break;
    // }
    // dirs[u++] += "├──" + child.getName() + added;
    // for (int j = u; j < u + child.getTotalNodeCount(); j++) {
    // dirs[j] += " ";
    // }
    // if (child.isFolder())
    // u = tree_rec(child, dirs, u) + 1;
    // }
    // } else {
    // dirs[u++] += node.getName();
    // }
    // return u - 1;
    // }

    /**
     * Prints how many nodes (files and folders) exist in the current directory and all of its
     * subdirectories.
     */
    private void count(String[] args) {
        System.out.println(this.currentDirectory.getTotalNodeCount() - 1);
    }

    /**
     * Prints the total size of all files reachable from the current directory.
     */
    private void size(String[] args) {
        System.out.println(this.currentDirectory.getSize());
    }

    /**
     * Prints the depth of the current directory, defined as the number of edges from the root
     * directory down to this directory.
     */
    private void depth(String[] args) {
        System.out.println(this.currentDirectory.getDepth());
    }

    /**
     * Prints the height of the current directory, defined as the longest downward distance from
     * this directory to any file or subdirectory beneath it. An empty directory has value 0.
     */
    private void height(String[] args) {
        System.out.println(this.currentDirectory.getHeight());
    }

    /**
     * Signals that the interactive loop should terminate after the current command.
     */
    private void quit(String[] args) {
        shouldExit = true;
    }

    /**
     * Interprets a line of user input by splitting it into a command and arguments, then forwarding
     * control to the appropriate helper method.
     *
     * Example inputs and how they are interpreted: "ls" -> command: "ls" args: []
     *
     * "mkdir docs" -> command: "mkdir" args: ["docs"]
     *
     * "touch notes.txt 100" -> command: "touch" args: ["notes.txt", "100"]
     *
     * "cd .." -> command: "cd" args: [".."]
     */
    public void processUserInputString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return;
        }

        String[] parts = line.trim().split("\\s+");
        String command = parts[0];
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);

        switch (command) {
            case "cd":
                cd(args);
                break;
            case "ls":
                ls(args);
                break;
            case "mkdir":
                mkdir(args);
                break;
            case "touch":
                touch(args);
                break;
            case "find":
                find(args);
                break;
            case "pwd":
                pwd(args);
                break;
            case "tree":
                tree(args);
                break;
            case "count":
                count(args);
                break;
            case "size":
                size(args);
                break;
            case "depth":
                depth(args);
                break;
            case "height":
                height(args);
                break;
            case "quit":
                quit(args);
                break;
            default:
                // Unknown commands can be reported back to the user.
                System.out.println("Unrecognized command: " + command);
        }
    }
}
