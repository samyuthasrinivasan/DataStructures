public class SuperList<E> {
    ListNode<E> root, end;
    int size;

    public SuperList() {
        root = null;
        end = null;
        size = 0;
    }

    public void add(E value) {
        ListNode<E> temp = new ListNode<E>(value);
        if (size == 0 || root == null) {
            root = temp;
        } else {
            end.setNext(temp);
            temp.setPrevious(end);
        }
        end = temp;
        size++;
    }

    public void add(int index, E value) {
        if(index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            if (index == size || index == size-1) {
                add(value);
            } else if (index == 0) {
                ListNode<E> temp = new ListNode<E>(value);
                temp.setNext(root);
                root = temp;
                size++;
            } else {
                ListNode<E> temp = new ListNode<E>(value);
                ListNode<E> n = root.getNext();
                ListNode<E> p = root;
                for (int i = 1; i < index; i++) {
                    n = n.getNext();
                    p = n.getPrevious();
                }
                temp.setNext(n);
                temp.setPrevious(p);

                n.setPrevious(temp);
                if(p == null)
                    p = root;
                p.setNext(temp);
                size++;
            }
        } 
    }

    public void push(E value) {
        add(value);
    }

    public ListNode<E> pop() {
        ListNode<E> temp = end;
        
        if(temp == null)
        return null;
        
        if(size > 1) {
            end = temp.getPrevious();
            temp.getPrevious().setNext(null);
            end.setNext(null);
            size--;
            return temp;
        }
        size--;
        end = null;
        root = null;
        return temp;
    }

    public ListNode<E> poll() {
        ListNode<E> temp = root;
        if(temp == null)
        return null;
        temp.setPrevious(null);
        root = temp.getNext();
        size--;
        
        return temp;
    }

    public ListNode<E> stackPeek() {
        return end;
    }

    public ListNode<E> queuePeek() {
        return root;
    }

    public void clear() {
        root = null;
        end = null;
        size = 0;
    }

    public ListNode<E> get(int index) {
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            ListNode<E> temp = root;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            return temp;
        } 
    }

    public void set(int index, E value) {
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            if (index == 0) {
                ListNode<E> current = root;
                root = new ListNode<E>(value);
                root.setNext(current.getNext());
                current.getNext().setPrevious(root);
            } else if (index == size - 1) {
                ListNode<E> current = end;
                end = new ListNode<E>(value);
                end.setPrevious(current.getPrevious());
                current.getPrevious().setNext(end);
            } else {
                ListNode<E> temp = new ListNode<E>(value);
                ListNode<E> current = root;
                ListNode<E> n = root.getNext();
                ListNode<E> p = null;
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                    n = current.getNext();
                    p = current.getPrevious();
                }
                current = temp;
                current.setNext(n);
                current.setPrevious(p);
                n.setPrevious(current);
                p.setNext(current);
            }

        }
    }

    public ListNode<E> remove(int index) {
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            if (index == 0) {
                return poll();
            } else if (index == size - 1) {
                return pop();
            } else {
                ListNode<E> current = root;
                ListNode<E> n = root.getNext();
                ListNode<E> p = null;
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                    n = current.getNext();
                    p = current.getPrevious();
                }
                n.setPrevious(p);
                p.setNext(n);
                size--;
                return current;
            }
        }
    }

    public boolean contains(E value) {
        ListNode<E> current = root;
        ListNode<E> c = new ListNode<E>(value);
        for (int i = 0; i < size(); i++) {
            if (current.value == c.value) {
                return true;
            } else {
                current = current.getNext();
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public String toString() {
        String s = "[";
        ListNode<E> temp = root;
        for (int i = 0; i < size; i++) {
            s += temp.getValue();
            if (temp.hasNext()) {
                s += ",";
                temp = temp.getNext();
            }
        }
        return s + "]";
    }

    public class ListNode<E> {
        E value;
        ListNode<E> next, previous;

        public ListNode(E value) {
            this.value = value;
            next = null;
            previous = null;
        }

        public E getValue() {
            return value;
        }

        public void setPrevious(ListNode<E> val) {
            previous = val;
        }

        public ListNode<E> getPrevious() {
            return previous;
        }

        public void setNext(ListNode<E> val) {
            next = val;
        }

        public ListNode<E> getNext() {
            return next;
        }

        public boolean hasNext() {
            return next != null;
        }

        public boolean hasPrevious() {
            return previous != null;
        }

        public String toString(){
            return getValue() + "";
        }
    }

    public static class Runner {
        SuperList<Integer> arrayList, stack, queue;
        public Runner(){

            /* 
            arrayList = new SuperList<Integer>();
            stack = new SuperList<Integer>();
            queue = new SuperList<Integer>();

            int ran = 0;
            for(int i = 0; i < 30; i++) {
                ran = (int)(Math.random()*1000) + 1;
                arrayList.add(ran);
            }

            System.out.println(arrayList);
            System.out.println("Size: " + arrayList.size());

            int i = arrayList.size() - 1;
            while(i >= 0) {
                stack.push(arrayList.get(i).getValue());
                arrayList.remove(i);
                i--;
            }

            System.out.println(stack);

            while(!stack.isEmpty()){
                queue.add(stack.stackPeek().getValue());
                stack.pop();
            }
            
            System.out.println(queue);
            
            while(!queue.isEmpty()){
                if(arrayList.isEmpty() || arrayList.size() == 1) {
                    ran = 0;
                } else 
                    ran = (int)(Math.random()*arrayList.size());

                arrayList.add(ran, queue.poll().getValue());
            } 

            System.out.println(arrayList);
            
            int sum = 0;
            for(int j = 0; j < arrayList.size(); j++) {
                sum += arrayList.get(j).getValue();
            }
            System.out.println("SUM: " + sum);

            sum = 0;
            for(int j = 0; j < arrayList.size(); j+=2) {
                sum += arrayList.get(j).getValue();
            }
            System.out.println("EVEN INDEX SUM: " + sum);

            sum = 0;
            for(int j = 1; j < arrayList.size(); j+=2) {
                sum += arrayList.get(j).getValue();
            }
            System.out.println("ODD INDEX SUM: " + sum);

            int s = arrayList.size();
            for(int j = 0; j < s; j++) {
                if(arrayList.get(j).getValue()%2 == 0) {
                    arrayList.add(arrayList.get(j).getValue());
                }
            }

            System.out.println(arrayList);

            s = arrayList.size();
            for(int j = s-1; j > -1; j--) {
                if(arrayList.get(j).getValue()%3 == 0) {
                    arrayList.remove(j);
                }
            }

            System.out.println(arrayList);

            arrayList.add(3, 55555);
            System.out.println(arrayList);

            s = arrayList.size();
            for (int j = 1; j < s; ++j) {
                int key = arrayList.get(j).getValue();
                int n = j - 1;
    
                while (n >= 0 && arrayList.get(n).getValue() > key) {
                    arrayList.set(n + 1, arrayList.get(n).getValue());
                    n = n - 1;
                }
                arrayList.set(n + 1, key);
            }

            System.out.println(arrayList);

            double pos = arrayList.size()/2.0;
            double median = 0;
            if(String.valueOf(pos).contains(".")) {
                int n1 = (int)pos - 1;
                int n2 = (int)pos;
                median = (arrayList.get(n1).getValue() + arrayList.get(n2).getValue())/2.0;
            } else {
                median = arrayList.get((int)pos).getValue();
            }
            System.out.println("MEDIAN: " + median);
            */

            SuperList<String> list = new SuperList<String>();
            String string = "The Guinea fowl flies through the air with all the grace of a turtle.";
            while(true){
                if(string.contains(" ")) {
                    list.add(string.substring(0, string.indexOf(" ")));
                    string = string.substring(string.indexOf(" ") + 1);
                } else {
                    list.add(string);
                    break;
                }
            }

            System.out.println(list);

            for(int j = list.size() - 1; j >= 0; j--) {
                if(list.get(j).getValue().length() <= 3) {
                    list.remove(j);
                }
            }

            System.out.println(list);
            int s;
            s = list.size();
            for (int j = 1; j < s; ++j) {
                int key = list.get(j).getValue().length();
                String k = list.get(j).getValue();
                int n = j - 1;
    
                while (n >= 0 && list.get(n).getValue().length() > key) {
                    list.set(n + 1, list.get(n).getValue());
                    n = n - 1;
                }
                list.set(n + 1, k);
            }

            System.out.println(list);

            int lengths = 0;
            for(int j = 0; j < list.size(); j++) {
                lengths += list.get(j).getValue().length();
            }

            double avg = lengths/list.size();
            System.out.println("AVERAGE WORD LENGTH: " + avg);

        }
    }


    public static void main(String[] args) {
        Runner r = new Runner();

    }
}

