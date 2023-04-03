public class TreeSetRunner {
    public TreeSetRunner() {
        

    }

    public static void main(String[] args) {
        TreeSetRunner app = new TreeSetRunner();
    }

    public class TreeSet<E extends Comparable<E>> {
        TreeNode<E> root;
        int size;
        String output;
        TreeNode<E> parent;
        int time = 1;

        public TreeSet() {
            root = null;
            size = 0;
        }

        public void add(E value) {
            TreeNode<E> temp = new TreeNode<E>(value);
            if (size == 0 || root == null) {
                root = temp;
                size = 1;
            } else {
                add(root, temp);
            }

        }

        public void add(TreeNode<E> t, TreeNode<E> value) {
            if (t.getValue().compareTo(value.getValue()) > 0) {
                if (t.getLeft() == null) {
                    t.setLeft(value);
                    size++;
                } else {
                    add(t.getLeft(), value);
                }
            } else if (t.getValue().compareTo(value.getValue()) < 0) {
                if (t.getRight() == null) {
                    t.setRight(value);
                    size++;
                } else {
                    add(t.getRight(), value);
                }
            }
        }

        public void remove(E value) {
            if (root.getValue().equals(value) && root.getLeft() == null && root.getRight() == null) {
                size = 0;
                root = null;
            } else {
                remove(root, null, value);
            }
        }

        public void remove(TreeNode<E> current, TreeNode<E> node, E value) {
            if (current != null) {
                if (value.compareTo(current.getValue()) > 0) {
                    node = current;
                    remove(current.getRight(), node, value);
                } else if (value.compareTo(current.getValue()) < 0) {
                    node = current;
                    remove(current.getLeft(), node, value);
                } else {
                    if (current.getRight() == null || current.getLeft() == null) {
                        if (node.getRight() != null) {
                            if (node.getRight().equals(current)) {
                                if(current.getRight() != null)
                                    node.setRight(current.getRight());
                                else if(current.getLeft() != null) {
                                    node.setRight(current.getLeft());
                                }
                                else 
                                    node.setRight(null);
                            }
                        } 
                        if (node.getLeft() != null) {
                            if (node.getLeft().equals(current)) {
                                if(current.getLeft() != null)
                                    node.setLeft(current.getLeft());
                                else if(current.getRight() != null)
                                    node.setLeft(current.getRight());
                                else 
                                    node.setLeft(null);
                            }
                        }
                        
                    } else {
                        
                        parent = current;
                        System.out.println(parent);
                        TreeNode<E> succ = recursiveSuccessor(current.getRight(), value);
                        System.out.println("Succ" + succ);
                        System.out.println("Node" + node);
                        
                        succ.setLeft(current.getLeft());
                        

                        if (node == null) {
                            root = succ;
                            if(parent.getLeft().equals(succ) && succ.getRight() == null) {
                                parent.setLeft(null);
                            } else if (succ.getRight() != null) {
                                parent.setLeft(succ.getRight());
                            }
                            root.setRight(parent);
                        } else {
                            System.out.println(current + " " + node + " " + succ);
                            current = succ;

                            if(succ.getValue().compareTo(node.getValue()) < 0)
                                node.setLeft(succ);
                            else {
                                node.setRight(succ);
                            }
                        } 
                    }
                    size--;
                }
            }
        }

        public TreeNode<E> recursiveSuccessor(TreeNode<E> current, E value) {
            
            if (current.getLeft() != null) {
                parent = current;
                return recursiveSuccessor(current.getLeft(), value);
            } else
                return current;
            
            
        }

        public String preOrder(TreeNode<E> temp) {

            if(size == 0) {
                return "[]";
            }

            if (temp != null) {
                output += temp.getValue() + ", ";
                preOrder(temp.getLeft());
                preOrder(temp.getRight());
            }

            return output.substring(0, output.length() - 2) + "]";
        }

        public String preOrder() {
            output = "[";
            return preOrder(root);
        }

        public String inOrder(TreeNode<E> temp) {

            if(size == 0) {
                return "[]";
            }

            if (temp != null) {
                inOrder(temp.getLeft());
                output += temp.getValue() + ", ";
                inOrder(temp.getRight());
            }

            return output.substring(0, output.length() - 1) + "]";
        }

        public String inOrder() {
            output = "[";
            return inOrder(root);
        }

        public String postOrder(TreeNode<E> temp) {

            if(size == 0) {
                return "[]";
            }

            if (temp != null) {
                postOrder(temp.getLeft());
                postOrder(temp.getRight());
                output += temp.getValue() + ", ";
            }

            return output.substring(0, output.length() - 1) + "]";
        }

        public String postOrder() {
            output = "[";
            return postOrder(root);
        }

        public void rotateLeft(){
            if(root.getRight() != null) {
                rotateLeft(root.getRight());
            }
        }

        public void rotateLeft(TreeNode<E> value) {
            if(value.getLeft() == null)
                root.setRight(null);
            else 
                root.setRight(value.getLeft());
            
            value.setLeft(root);
            root = value;

        }

        public void rotateRight(){
            if(root.getLeft() != null) {
                rotateRight(root.getLeft());
            }
        }

        public void rotateRight(TreeNode<E> value) {
            if(value.getRight() == null)
                root.setLeft(null);
            else 
                root.setLeft(value.getRight());
            
            value.setRight(root);
            root = value;
        }

        public int size() {
            return size;
        }

    }

    public class TreeNode<E extends Comparable<E>> {
        TreeNode<E> left, right;
        E value;

        public TreeNode(E val) {
            value = val;
            left = null;
            right = null;
        }

        public TreeNode<E> getRight() {
            return right;
        }

        public TreeNode<E> getLeft() {
            return left;
        }

        public void setRight(TreeNode<E> right) {
            this.right = right;
        }

        public void setLeft(TreeNode<E> left) {
            this.left = left;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public E getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value + "";
        }
    }
}
