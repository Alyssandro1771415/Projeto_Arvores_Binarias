package Algoritmos;

import java.util.Stack;

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

    private void leftRotate(Node node) {
        rotationCount++;
        Node parent = node.parent;
        Node left = node.left;
        node.left = parent;
        parent.right = left;
        if (left != null) {
            left.parent = parent;
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

    private void rightRotate(Node node) {
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

    public void splay(Node node) {
        while (node.parent != null) {
            if (node.parent.parent == null) { 
                if (node.parent.left == node) {
                    rightRotate(node);
                } else {
                    leftRotate(node);
                }
            } else if (node.parent.left == node && node.parent.parent.left == node.parent) { 
                rightRotate(node.parent);
                rightRotate(node);
            } else if (node.parent.right == node && node.parent.parent.right == node.parent) { 
                leftRotate(node.parent);
                leftRotate(node);
            } else if (node.parent.left == node && node.parent.parent.right == node.parent) { 
                rightRotate(node);
                leftRotate(node);
            } else { // Zig-Zag step
                leftRotate(node);
                rightRotate(node);
            }
        }
        root = node;
    }

    public void insert(double data) {
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
                    break;
                }
                temp = temp.left;
            } else {
                if (temp.right == null) {
                    temp.right = node;
                    node.parent = temp;
                    break;
                }
                temp = temp.right;
            }
        }
        splay(node);
    }

    public Node search(double data) {
        if (root == null) {
            return null;
        }

        Node temp = root;
        while (temp != null) {
            if (temp.data == data) {
                splay(temp);
                return temp;
            }
            if (temp.data > data) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        return null;
    }

    public Node findMin(Node node) {
        if (node == null) {
            return null;
        }
        Node min = node;
        while (min.left != null) {
            min = min.left;
        }
        splay(min);
        return min;
    }

    public void delete(double data) {
        Node node = search(data);
        if (node == null) {
            return;
        }

        splay(node);

        if (node.left != null) {
            Node leftSubtree = node.left;
            leftSubtree.parent = null;
            Node maxNode = leftSubtree;
            while (maxNode.right != null) {
                maxNode = maxNode.right;
            }
            splay(maxNode);
            maxNode.right = node.right;
            if (node.right != null) {
                node.right.parent = maxNode;
            }
            root = maxNode;
        } else {
            root = node.right;
            if (root != null) {
                root.parent = null;
            }
        }
    }

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void display() {
        System.out.print("Tree's PreOrder Traversal: ");
        preOrder(root);
        System.out.println();
    }

    public int countRotations() {
        return rotationCount;
    }

    public int getHeight() {
        return calculateHeight(root);
    }

    private int calculateHeight(Node node) {
        if (node == null) {
            return 0;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(node);

        int height = 0;
        Node prev = null;

        while (!stack.isEmpty()) {
            Node current = stack.peek();

            if (prev == null || prev.left == current || prev.right == current) {
                if (current.left != null) {
                    stack.push(current.left);
                } else if (current.right != null) {
                    stack.push(current.right);
                }
            } else if (current.left == prev) {
                if (current.right != null) {
                    stack.push(current.right);
                }
            } else {
                stack.pop();
            }

            prev = current;

            height = Math.max(height, stack.size());
        }

        return height;
    }
    
    private boolean verifyTree(Node node) {
        if (node == null) {
            return true;
        }
        if (node.left != null && node.left.parent != node) {
            return false;
        }
        if (node.right != null && node.right.parent != node) {
            return false;
        }
        return verifyTree(node.left) && verifyTree(node.right);
    }

    public boolean verifyTreeStructure() {
        return verifyTree(root);
    }

}
