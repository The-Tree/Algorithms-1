import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;

// uses part of doubly linked list queue implementation from https://gist.github.com/VitalyStakhov/4970950
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;
    
    public Deque() {
        size = 0;
    } // construct an empty deque
    
    public boolean isEmpty() {
        return size == 0;
    } // is the deque empty?
    
    public int size() {
        return size;
    } // return the number of items on the deque
    
    public void addFirst(Item item) {
        if ( item == null ) {
            throw new IllegalArgumentException("addFirst arguement is null");
        }
        else {
            Node newFirst = new Node();
            newFirst.item = item;
            
            if (first != null) {
                newFirst.next = first;
                first.previous = newFirst;
            }
            first = newFirst;
            if (last == null) {
                last = first;
            }
            
            size++;
        }
    } // add the item to the front
    
    public void addLast(Item item) {
         if ( item == null ) {
            throw new IllegalArgumentException("addLast arguement is null");
        }
        else {
            Node newLast = new Node();
            newLast.item = item;
            
            if (last != null) {
                newLast.previous = last;
                last.next = newLast;
            }
            last = newLast;
            if (first == null) first = last;
            
            size++;
        }
    } // add the item to the end
    
    public Item removeFirst() {
        if ( first == null ) {
            throw new NoSuchElementException("removeFirst there is no first element");
        }
        else {
            Node oldFirst = first;
            first = first.next;
            
            if (first == null) {
                last = null;
            }
            else {
                first.previous = null;
            }
            
            size--;  
            return oldFirst.item;
        }
    } // remove and return the item from the front
    
    public Item removeLast() {
        if ( first == null ) {
            throw new NoSuchElementException("removeLast there is no first element, and if there is no first one there is also no last one");
        }
        else {
            Node oldLast = last;
            last = oldLast.previous;
            
            if (last == null)
                first = null;
            else
                last.next = null;
            
            size--;
            return oldLast.item;
        }
    } // remove and return the item from the end
    
    public Iterator<Item> iterator() {
        return new ItemsIterator();
    } // return an iterator over items in order from front to end
    
    private class ItemsIterator implements Iterator<Item> {
        private Node current;

        public ItemsIterator() {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException("next in itemsIterator there is no current");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("itemsiterator remove I do not want to be able to remove things from the iterator");
        }
    }
    
    private class Node {
        private Node previous;
        private Node next;
        private Item item;
    }
}