import java.util.Objects;

public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int capacity;
    private int nextFirst=0;
    private int nextLast=1;
    private int  RESIZE_FACTOR = 2;
    private double USAGE_RATIO = 0.25;
    private int CAPACITY_BASE = 16;

    /** Construct an empty ArrayDeque. */
    public ArrayDeque() {
        this(8);
    }

    /** Creates an empty array deque.
     *  The starting size of your array should be 8.
     *  Construct an empty ArrayDeque with given capacity. */
    private ArrayDeque(int capacity) {
        items = (T[]) new Object[capacity];
        size = 0;
        this.capacity = capacity;
    }

    /** Creates a deep copy of other. */
    public ArrayDeque(ArrayDeque other) {
        this(other.size() * 2);
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item){
        if(isFull()){
            expandArray(RESIZE_FACTOR);
        }
        items[nextFirst] = item;
        size++;
        nextFirst = IndexAdd(nextFirst, -1);
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        if (isFull()) {
            expandArray(RESIZE_FACTOR);
        }
        items[nextLast] = item;
        size++;
        nextLast = IndexAdd(nextLast, 1);
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque.
     *  Must take constant time.
     */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     *  Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null.
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = IndexAdd(nextLast, -1);
        T last = items[nextLast];
        items[nextLast] = null;
        size--;
        // Check to shrink ArrayDeque
        if (isSparse()) {
            shrinkArray();
        }
        return last;
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null.
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = IndexAdd(nextFirst, 1);
        T first = items[nextFirst];
        items[nextFirst] = null;
        size--;
        // Check to shrink ArrayDeque
        if (isSparse()) {
            shrinkArray();
        }
        return first;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null.
     *  Must not alter the deque and must take constant time.
     */
    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            return items[IndexAdd(nextFirst, 1 + index)];
        }
    }



    private boolean isFull(){
        return size == capacity;
    }

    private boolean isSparse(){
        return (size < CAPACITY_BASE) && (size > capacity * USAGE_RATIO );
    }


    /** Resize ArrayDeque to have factor*capacity. */
    private void expandArray(int factor){
        reshapeItems(capacity* factor);
        capacity = capacity * factor;
    }

    /** Halve the capacity of ArrayDeque to save memory. */
    private void shrinkArray(){
        reshapeItems(capacity/2);
        capacity = capacity / 2;
    }

    private void reshapeItems(int newCapacity){
        T[] newItems = (T[]) new Object[newCapacity];
        for(int i =0; i < size; i++){
            newItems[i] = items[IndexAdd(nextFirst, 1 + i)];
        }
        items = newItems;
        nextFirst = newCapacity - 1;
        nextLast = size;
    }

    /** Circulate index. */
    private int IndexAdd(int index, int num){
        index = index + num;
        if(index < 0){
            index = index + capacity;
        }
        if(index >= capacity){
            index = index - capacity;
        }
        return index;
    }

}
