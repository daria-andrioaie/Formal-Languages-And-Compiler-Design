import java.util.Objects;

class Node<T> {

    int id;
    T key;
    int code;
    Node<?> left;
    Node<?> right;

    public Node(T key, int code){
        this.key = key;
        this.code = code;
        left = right = null;
    }
}

class BSTree {
    Node<?> root;
    int nextAvailableCode;

    BSTree() {
        root = null;
        nextAvailableCode = 0;
    }

    public <T> void add(T key) {
        root = addRecursive(root, key);
    }

    private <T> Node<?> addRecursive(Node<?> root, T key) {
        if(root == null) {
             root = new Node<>(key, nextAvailableCode);
             incrementCode();
             return root;
        }
        //traverse the tree
        if (key.toString().compareTo(root.key.toString()) < 0 )     //insert in the left subtree
            root.left = addRecursive(root.left, key);
        else if (key.toString().compareTo(root.key.toString()) > 0)    //insert in the right subtree
            root.right = addRecursive(root.right, key);
        // return pointer
        return root;
    }

    <T> boolean search(T key)  {
        return search_Recursive(root, key);
    }

    //recursive insert function
    <T> boolean search_Recursive(Node<?> root, T key)  {
        // Base Cases: root is null or key is present at root
        if (root==null || Objects.equals(root.key.toString(), key.toString()))
            return true;
        // val is greater than root's key
        if (root.key.toString().compareTo(key.toString()) > 0)
            return search_Recursive(root.left, key);
        // val is less than root's key
        return search_Recursive(root.right, key);
    }


    private void incrementCode() {
        this.nextAvailableCode++;
    }

    public String sorted() {
        return inOrderTraversal(root);
    }

    private String inOrderTraversal(Node<?> root) {
        if(root != null) {
            return inOrderTraversal(root.left) + root.key + " " + root.code + "\n" + inOrderTraversal(root.right);
        }
        return "";
    }
}


public class SymbolTable {
    BSTree data = new BSTree();

    public SymbolTable() {}

    public <T> void add(T key) {
        data.add(key);
    }

    public <T> boolean search(T key)  {
        return data.search(key);
    }

    public String sorted() {
        return data.sorted();
    }
}

