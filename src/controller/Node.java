
package controller;

public class Node<E> {
    
    public E data;
        public Node next;
        public Node before;


        public Node(E data) {
            this.data = data;
            this.next = null;
            this.before = null;
        }

        @SuppressWarnings("unused")
        public Node(E data, Node next, Node before) {
            this.data = data;
            this.next = next;
            this.before = before;
        }
    @Override
        public String toString() {
            return "Node(" + data.toString() + ")";
        }

        public E getData() {
            return data;
        }
    
}
