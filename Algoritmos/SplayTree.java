package Algoritmos;

public class SplayTree {

    static class Node {
        double data;
        Node left;
        Node right;
        Node parent;

        public Node(double data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    static Node root;
    private static int rotationCount = 0;

    private static void leftRotate(Node node) {
        System.out.println("Rotating left : " + node.data);
        rotationCount++;
        Node parent = node.parent;
        Node left = node.left;
        node.left = parent;
        parent.right = left;
        if (left != null) {
            left.parent = parent;
        }
        Node gp = node.parent.parent;
        parent.parent = node;
        node.parent = gp;

        if (gp == null) {
            root = node;
        } else {
            if (gp.left == parent) {
                gp.left = node;
            } else {
                gp.right = node;
            }
        }
    }

    private static void rightRotate(Node node) {
        System.out.println("Rotating right : " + node.data);
        rotationCount++;
        Node parent = node.parent;
        Node right = node.right;
        node.right = parent;
        parent.left = right;
        if (right != null) {
            right.parent = parent;
        }
        Node gp = parent.parent;
        node.parent = gp;
        parent.parent = node;

        if (gp == null) {
            root = node;
        } else {
            if (gp.left == parent) {
                gp.left = node;
            } else {
                gp.right = node;
            }
        }
    }

    private static void splay(Node node) {
        System.out.println("Splaying node : " + node.data);

        if (node.parent == null) {
            root = node;
            return;
        }

        if (node.parent.parent == null) {
            if (node.parent.right == node) {
                leftRotate(node);
                root = node;
            } else {
                rightRotate(node);
                root = node;
            }
            return;
        }

        if (node.parent.right == node && node.parent.parent.left == node.parent) {
            leftRotate(node);
            rightRotate(node);
            splay(node);
            return;
        }

        if (node.parent.left == node && node.parent.parent.right == node.parent) {
            rightRotate(node);
            leftRotate(node);
            splay(node);
            return;
        }

        if (node.parent.right == node && node.parent.parent.right == node.parent) {
            leftRotate(node.parent);
            leftRotate(node);
            splay(node);
            return;
        }

        if (node.parent.left == node && node.parent.parent.left == node.parent) {
            rightRotate(node.parent);
            rightRotate(node);
            splay(node);
            return;
        }
    }

    // Function to add new elements in the tree.
    public static void insert(double data) {
        System.out.println("Inserting data : " + data);
        Node node = new Node(data);
        if (root == null) {
            root = node;
            return;
        }

        Node temp = root;

        while (temp != null) {
            if (temp.data > data) {
                if (temp.left == null) {
                    temp.left = node;
                    node.parent = temp;
                    splay(node);
                    return;
                }
                temp = temp.left;
            }

            if (temp.data < data) {
                if (temp.right == null) {
                    temp.right = node;
                    node.parent = temp;
                    splay(node);
                    return;
                }
                temp = temp.right;
            }
        }
    }

    public static Node search(double data) {
        System.out.println("Searching for node : " + data);
        if (root == null) {
            System.out.println("Empty Tree.");
            return null;
        }

        Node temp = root;
        while (temp != null) {
            if (temp.data == data) {
                System.out.println("Found node : " + data);
                splay(temp);
                return temp;
            }
            if (temp.data > data) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        System.out.println("Node not found.");
        return null;
    }

    public static Node findMin(Node node) {
        if (node == null) {
            System.out.println("Empty Tree.");
            return null;
        }
        System.out.println("Finding Minimum.");
        Node min = node;
        while (min.left != null) {
            min = min.left;
        }
        splay(min);
        System.out.println("Minimum node : " + min.data);
        return min;
    }

    public static void delete(double data) {
        System.out.println("Deleting node : " + data);
        Node node = search(data);
        if (node == null) {
            System.out.println("Node not present in the tree.");
            return;
        }

        Node min = findMin(node.right);
        if (min == null) {
            root = root.left;
            if (root != null) {
                root.parent = null;
            }
            return;
        }
        root.left = root.left.left;
        if (root.left != null) {
            root.left.parent = root;
        }
    }

    // Function for PreOrder Traversal of the tree.
    private static void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public static void display() {
        System.out.print("Tree's PreOrder Traversal : ");
        preOrder(root);
        System.out.println();
    }

    // Method to return the number of rotations
    public static int countRotations() {
        return rotationCount;
    }

    // Method to calculate the height of the tree
    public static int getHeight() {
        return calculateHeight(root);
    }

    private static int calculateHeight(Node node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = calculateHeight(node.left);
        int rightHeight = calculateHeight(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

}
