import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

// uses parts of the resizable array queue implementation from http://algs4.cs.princeton.edu/13stacks/ResizingArrayQueue.java.html
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int size;
    private int last;
    
    public RandomizedQueue() {
        array = (Item[]) new Object[2];
        size = 0;
        last = 0;
    } // construct an empty randomized queue
    
    public boolean isEmpty() {
        return size == 0;
    } // is the randomized queue empty?
    
    public int size() {
        return size;
    } // return the number of items on the randomized queue
    
    public void enqueue(Item item) {
        if ( item == null ) {
            throw new IllegalArgumentException("enqueue arguement is null");
        }
        else {
            if ( size == array.length ) { 
                resize(2*array.length);
            }
            
            array[last++] = item;
            
            size++;
        }
    } // add the item
    
    public Item dequeue() { 
        if ( isEmpty() ) {
            throw new NoSuchElementException("dequeue this is an empty queue");
        }
        else {
            int rand;
            Item item = null;
            
            if( last!=0 && size > 0 ) {
                rand = StdRandom.uniform(0,last);
                
                item = array[rand];
                array[rand] = array[last-1];
                array[last-1] = null;
                
                size--;
                last--;
            }
            else if( size > 0 ) {
                rand = last;
                
                item = array[rand];
                array[rand] = null;
                
                size--;
            }
            
            if (size > 0 && size == array.length/4) {
                resize(array.length/2); 
            }
            return item;
        }
    } // remove and return a random item
    
    public Item sample() {
        if ( isEmpty() ) {
            throw new NoSuchElementException("sample this is an empty queue");
        }
        else {
            int rand;
            if(last!=0)
                rand = StdRandom.uniform(0,last);
            else
                rand = last;
            
            Item item = array[rand];
            
            return item;
        }
    } // return a random item (but do not remove it)
    
    public Iterator<Item> iterator() {
        return new RandomIterator();
    } // return an independent iterator over items in random order
    
    private class RandomIterator implements Iterator<Item> {
        private Item[] arr;
        private int currI;
        
        public RandomIterator() {
            arr = (Item[]) new Object[size];
            
            for( int i = 0; i < size; i++ ) {
                arr[i] = array[i];
            }
            currI = 0;
            for( int i = 0; i < arr.length; i++ ){
                enqueue(arr[i]);
            }
        }
        
        public boolean hasNext() { 
            return currI >= size;                              
        }
        
        public void remove() { 
            throw new UnsupportedOperationException();  
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return arr[currI++];
        }
    } 
    
    private void resize(int capacity) {
        assert capacity >= size;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = array[i % array.length];
        }
        array = temp;
        last = size;
    }
    
    private int getLast() {
        return this.last;
    }
    
    public static void main(String[] args) {
        RandomizedQueue<String> test = new RandomizedQueue<String>();
        test.enqueue("a");
        test.enqueue("b");
        test.enqueue("c");
        test.enqueue("d");
        
        StdOut.println(test.size());
        //StdOut.println(test.getLast());
        StdOut.println(test.dequeue());
        StdOut.println(test.size());
        //StdOut.println(test.getLast());
        StdOut.println(test.dequeue());
        StdOut.println(test.size());
        //StdOut.println(test.getLast());
        StdOut.println(test.dequeue());
        StdOut.println(test.size());
        //StdOut.println(test.getLast());
        StdOut.println(test.dequeue());
        StdOut.println(test.size());
        //StdOut.println(test.getLast());
    }
}