public class TreeSetRunner {
    public TreeSetRunner() {
        TreeSet<Integer> tree = new TreeSet<Integer>();

        tree.add(10);
        tree.add(6);
        tree.add(12);
        tree.add(3);
        tree.add(7);
        tree.add(15);
        tree.add(4);
        tree.add(5);
        tree.add(10);
        tree.add(11);
        tree.add(19);
        tree.add(19);

        System.out.println(tree.size());
        //tree.remove(19);
        System.out.println(tree.preOrder());
    }

    public static void main(String[] args) {
        TreeSetRunner app = new TreeSetRunner();
    }

    public class TreeSet<E extends Comparable<E>> {
        TreeNode<E> root;
        int size;
        String output;

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

        /*
         * public void remove(E value){
            TreeNode<E> temp = new TreeNode<E>(value);
            remove(root, temp);
        }

        public void remove(TreeNode<E> t, TreeNode<E> value){
            System.out.println(t + " " + value);

            if(t != null) {
                if(t.getValue().compareTo(value.getValue()) == 0) {
                    if(t.getLeft() == null && t.getRight() == null) {
                        t = null;
                    } else if (t.getLeft() != null) {
                        //parent.setLeft(t.getLeft());
                    } else if(t.getRight() != null) {
                        //parent.setRight(t.getLeft());
                    } else { //if ()
                        
                    }
                } else {
                    remove(t.getLeft(), value);
                    remove(t.getRight(), value);
                }
            }
        }
         * 
         */
        

        public String preOrder(TreeNode<E> temp) {

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

        public E getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value + "";
        }
    }
}
