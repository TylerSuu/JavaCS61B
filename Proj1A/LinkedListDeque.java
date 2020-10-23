public class LinkedListDeque<T> {
    private DequeNode sentinel;
    private int size;

    //Nested class
    private class DequeNode{
        private T item;
        private DequeNode prev;
        private DequeNode next;

        private DequeNode(T i, DequeNode p, DequeNode n ){
            item = i;
            prev = p;
            next = n;
        }
        //private DequeNode() {this(null, null, null); }
    }

    /** Creates an empty linked list deque. */
    public LinkedListDeque(){
        size = 0;
        sentinel = new DequeNode(null,null,null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    /** Creates a deep copy of other. */
    public LinkedListDeque(LinkedListDeque other){
        sentinel = new DequeNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item){
        sentinel.next.prev = new DequeNode(item, sentinel, sentinel.next);
        sentinel.next = sentinel.next.prev;
        size= size +1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item){
        sentinel.prev.next = new DequeNode(item, sentinel.prev, sentinel);
        sentinel.prev = sentinel.prev.next;
        size = size +1;
    }

   /** Returns true if deque is empty, false otherwise.*/
   public boolean isEmpty(){
      return (size == 0);
   }

    /** Returns the number of items in the deque.(return the size of deque)
     *  Must take constant time.
     */
    public int size(){
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     *  Once all the items have been printed, print out a new line.
     */
    public void printDeque(){
       DequeNode first = sentinel.next;
       while(first != sentinel){
           System.out.print(first.item + " ");
           first = first.next;
       }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null.
     */
    public T removeFirst(){
        if(isEmpty()){
            return null;
        }
        DequeNode first = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.next.prev = sentinel;
        size = size -1;
        return first.item;
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null.
     */
    public T removeLast(){
        if(isEmpty()){
            return null;
        }
        DequeNode last = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.prev.next = sentinel;
        size = size -1;
        return last.item;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null.
     *  Must not alter the deque and must use iteration.
     */
    public T get(int index){
        if(index >size){
            return null;
        }
        DequeNode currentNode = sentinel.next;

        for(int i=0; i< index; i++){
            currentNode = currentNode.next;
        }
        return currentNode.item;
    }

    /** Same as get, but uses recursion. */
    public T getRecursive(int index) {
        if(index > size){
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }
    private T getRecursiveHelper(int index, DequeNode node){
        if(index ==0){
            return node.item;
        }else{
            node = node.next;
            return getRecursiveHelper(index-1,node);
        }
    }

}


