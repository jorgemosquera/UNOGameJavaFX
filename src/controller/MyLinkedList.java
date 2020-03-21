
package controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyLinkedList<E> implements List<E> {
    
    private int size;            // keeps track of the number of elements
    private Node head;           // reference to the first node
    private Node tail;          // keeps track of the last node
    private boolean clockwise;
    private int currentIndex;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
        clockwise = true;
        currentIndex = -1;
    }



    @Override
    public boolean add(E element) {
        if (head == null) {
            head = new Node(element);
            tail = head;
        } else {
            Node tmp = new Node(element);
            tail.next = tmp;
            tmp.before = tail;
            tail = tmp;
        }
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = head;
        for (int i = 0 ; i < index; i++) {
            node = node.next;
        }
        Node tmp = new Node(element);
        Node before = node.before;
        tmp.next = node;
        tmp.before = before;
        before.next = tmp;
        node.before = tmp;
        size++;

    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean flag = true;
        for (E element: collection) {
            flag &= add(element);
        }
        return flag;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object obj: collection) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public E get(int index) {
        Node node = getNode(index);
        return (E) node.data;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = head;
        for (int i=0; i<index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public int indexOf(Object target) {
        Node node = head;
        for(int i=0; i < size; i++){
            if(equals(target, node.data)) {
                return i;
            }
			else {
                node = node.next;
            }
        }
        return -1;
    }

    private boolean equals(Object target, Object element) {
        if (target == null) {
            return element == null;
        } else {
            return target.equals(element);
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        E[] array = (E[]) toArray();
        return Arrays.asList(array).iterator();
    }

    @Override
    public int lastIndexOf(Object target) {
        Node node = head;
        int index = -1;
        for (int i=0; i<size; i++) {
            if (equals(target, node.data)) {
                index = i;
            }
            node = node.next;
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public boolean remove(Object obj) {
        int index = indexOf(obj);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public E remove(int index) {
        Node node = head;
        for(int i = 0; i < index; i++)
            node = node.next;
        Node before = node.before;
        Node next = node.next;
        before.next = next;
        next.before = before;
        node.before = null;
        node.next = null;
        size--;

        return null;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean flag = true;
        for (Object obj: collection) {
            flag &= remove(obj);
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) {
        Node node = getNode(index);
        E old = (E) node.data;
        node.data = element;
        return old;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        // TODO: classify this and improve it.
        int i = 0;
        MyLinkedList<E> list = new MyLinkedList<E>();
        for (Node node=head; node != null; node = node.next) {
            if (i >= fromIndex && i <= toIndex) {
                list.add((E) node.data);
            }
            i++;
        }
        return list;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (Node node=head; node != null; node = node.next) {
            // System.out.println(node);
            array[i] = node.data;
            i++;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    //
    public Node nextNode() {
        if(currentIndex == -1) {
            currentIndex = 0;
            return head;
        }
        else {
            Node node = getNode(currentIndex);
            if(clockwise) {
                if(node == tail){
                    currentIndex = 0;
                    return head;
                }
                else {
                    currentIndex++;
                    return node.next;
                }
            }
            else{
                if(node == head) {
                    currentIndex = size - 1;
                    return tail;
                }
                else {
                    currentIndex--;
                    return node.before;
                }
            }
        }
    }

    public void reverse() {
        clockwise = !clockwise;
    }

    public void skip() {
        this.nextNode();
        this.nextNode();
    }

    public void setHead(Object target) {
        Node tmp = getNode(indexOf(target));
        tail.next = head;
        head.before = tail;

        head = tmp;
        tail = tmp.before;

        head.before = null;
        tail.next = null;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
    
    public void setCurrentIndex(int index){
        this.currentIndex = index;
    }
    
    public boolean isClockWise(){
        return this.clockwise;
    }
}
