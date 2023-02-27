import java.util.AbstractList;
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.Iterator;

/*
 * Name: Aidan Rosen
 * Email: acrosen@ucsd.edu
 * PID: A17297146
 * Sources used: Lecture slides,
 * //https://stackoverflow.com/questions/56974/keyword-for-the-outer-class-from-an-anonymous-inner-class 
 * ^^^ For accessing inner classes 
 * 
 * This file recreates the LinkedList Java class as a new class, 
 * and has the nodes of the LinkedList as an inbuilt class
 */

 /**
     * The MyLinkedList class takes a generic E for the Node type
     * This is where LinkedList is recreated
*/
public class MyLinkedList<E> extends AbstractList<E> {

    int size;
    Node head;
    Node tail;

    /**
     * A Node class that holds data and references to previous and next Nodes.
     */
    protected class Node {
        E data;
        Node next;
        Node prev;

        /** 
         * Constructor to create singleton Node 
         * @param element Element to add, can be null	
         */
        public Node(E element) {
            // Initialize the instance variables
            this.data = element;
            this.next = null;
            this.prev = null;
        }

        /** 
         * Set the parameter prev as the previous node
         * @param prev new previous node
         */
        public void setPrev(Node prev) {
            this.prev = prev;		
        }

        /** 
         * Set the parameter next as the next node
         * @param next new next node
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /** 
         * Set the parameter element as the node's data
         * @param element new element 
         */
        public void setElement(E element) {
            this.data = element;
        }

        /** 
         * Accessor to get the next Node in the list 
         * @return the next node
         */
        public Node getNext() {
            return this.next;
        }
        /** 
         * Accessor to get the prev Node in the list
         * @return the previous node  
         */
        public Node getPrev() {
            return this.prev;
        } 
        /** 
         * Accessor to get the Nodes Element 
         * @return this node's data
         */
        public E getElement() {
            return this.data;
        } 
    }


    
    //  Implementation of the MyLinkedList Class
    /** 
     * Only 0-argument constructor is defined 
     * This constructor creates the linked list with sentinel nodes and a size of 0
     * */
    public MyLinkedList() {
        /* Add your implementation here */
      

        this.head = new Node(null);
        this.tail = new Node(null);

        this.head.setNext(this.tail);
        this.tail.setPrev(this.head);
        this.size = 0;
    }

    /**
     * A getter method that returns the size field 
     */
    @Override
    public int size() {
        // need to implement the size method
        return this.size; 
    }


    /**
     * Gets the element at the node at the stated index 
     * This get method treats the first element after the head node and before tail as the 0 index
     * Also, there's no need to worry about the empty LinkedList because the IndexOutOfBounds exception prevents getting from the empty list 
     */
    @Override
    public E get(int index) {

        if (index < 0 || index > this.size || index == this.size){

            throw new IndexOutOfBoundsException();
        }


        Node currNode = this.head.getNext();//head.getNext must exist because, if index == 0 and size == 0, then an exception will be thrown
        int currIndex = 0; //Check in tests if this runs as expected 
        E toReturn = null; 

        while (currNode != this.tail){ //Safer, use currNoe != this.tail instead of less than index size 

            if (currIndex == index){

                toReturn = currNode.getElement();
                break; 

            } else {


                currNode = currNode.getNext();
                currIndex += 1;

            }
        }

        return (E) toReturn; 
    }

    /**
     * This add class inserts a node at the stated index
     * Note that to insert to the empty list, this method treats the head node as the 0 index SO the first element inserted is considered at index 1 
     */
    @Override
    public void add(int index, E data) {
        if (index < 0 || index > this.size){

            throw new IndexOutOfBoundsException();
        }

        if ( data == null){

            throw new NullPointerException(); 
        }

        Node currNode = this.head;
        Node toInsert = new Node(data);
        int currIndex = 0; //Check in tests if this runs as expected 

        while (currNode != this.tail){ //Safer, use currNode != this.tail instead of less than index size 

            if (currIndex == index){

               
                currNode.getNext().setPrev(toInsert);
                toInsert.setNext(currNode.getNext());
                currNode.setNext(toInsert);
                toInsert.setPrev(currNode);
                this.size += 1; 
                break; 
            } else {


                currNode = currNode.getNext();
                currIndex += 1;

            }



            
        }


       
    }

    /**
     * Adds the node with the given data to the left of the tail node.
     */
    public boolean add(E data) {

        if (data == null){

            throw new NullPointerException();
    
        }

        Node toAdd = new Node(data);
        toAdd.setNext(this.tail);
        toAdd.setPrev(this.tail.getPrev());
        this.tail.getPrev().setNext(toAdd);
        this.tail.setPrev(toAdd);
        this.size += 1;

        return true; 
    }

    /**
     * This sets the element at the node at the inputted index to the provided data 
     * This time, the node after the head node is treated as the zero index 
     * This is to avoid setting data in the empty list
     */
    public E set(int index, E data) {

        if (index < 0 || index > this.size || index == this.size){

            throw new IndexOutOfBoundsException();
        }

        if ( data == null){

            throw new NullPointerException(); 
        }

        Node currNode = this.head.getNext();//head.getNext must exist because, if index == 0 and size == 0, then an exception will be thrown
        int currIndex = 0; //Check in tests if this runs as expected 
        E toReturn = null; 

        while (currNode != this.tail){ //Safer, use currNoe != this.tail instead of less than index size 

            if (currIndex == index){

                toReturn = currNode.getElement();
                currNode.setElement(data);
                break; 

            } else {


                currNode = currNode.getNext();
                currIndex += 1;

            }
        }

        return (E) toReturn; 
    }


    /**
     * Removes the index at the given index by breaking it's connections to the node before and after it
     * And then rerouting the nodes before and after it to connect to each other
     */
    public E remove(int index) {

        if (index < 0 || index > this.size || index == this.size){

            throw new IndexOutOfBoundsException();
        }

        Node currNode = this.head.getNext();//head.getNext must exist because, if index == 0 and size == 0, then an exception will be thrown
        int currIndex = 0; //Check in tests if this runs as expected 
        E toReturn = null; 

        while (currNode != this.tail){ //Safer, use currNoe != this.tail instead of less than index size 

            if (currIndex == index){

                toReturn = currNode.getElement();
                currNode.getNext().setPrev(currNode.getPrev());
                currNode.getPrev().setNext(currNode.getNext());
                currNode.setNext(null);
                currNode.setPrev(null); 
                this.size -= 1;
                break; 

            } else {


                currNode = currNode.getNext();
                currIndex += 1;

            }
        }

        return (E) toReturn; 
      
    }

    /**
     * Clears the linked list by removing from the first non-sentinel node until only sentinels are left
     */
    public void clear() {
       
        //CANNOT do a this.size loop because this.size changes over time 

        while (this.head.getNext() != this.tail && this.size > 0){

            this.remove(0); //Continuously remove the first node after the head one by one 
        }
    }

    /**
     * Checks if the list is empty with size and the sentinel nodes 
     */
    public boolean isEmpty() {
        
        if (this.size == 0 && this.head.getNext() == this.tail && this.tail.getPrev() == this.head){

            return true;
        } else {

            return false; 
        }
    }

    /**
     * Similar to the get method, except it returns the node at the index instead of the data at the node at the index
     */
    protected Node getNth(int index) {

        if (index < 0 || index > this.size || index == this.size){

            throw new IndexOutOfBoundsException();
        }


        Node currNode = this.head.getNext();//head.getNext must exist because, if index == 0 and size == 0, then an exception will be thrown
        int currIndex = 0; //Check in tests if this runs as expected 
        Node toReturn = null; 

        while (currNode != this.tail){ //Safer, use currNoe != this.tail instead of less than index size 

            if (currIndex == index){

                toReturn = currNode;
                break; 

            } else {


                currNode = currNode.getNext();
                currIndex += 1;

            }
        }

        return toReturn; 
    }

        /**
     * A constructor to create the ListIterator inside the MyLinkedList class
     */
    public ListIterator<E> listIterator() {
        return new MyListIterator();
    }
    
            /**
     * A constructor to create the ListIterator inside the MyLinkedList class
     */
    public Iterator<E> iterator() {
        return new MyListIterator();
    }



     /**
     * The MyListIterator class takes a generic E for the Node type
     * This is where LinkedList is recreated
    */
    protected class MyListIterator implements ListIterator<E> {

        // class variables here
        Node left, right; 
        int idx;
        boolean forward; 
        boolean canRemoveOrSet;

        /**
        * Initializes MyListIterator
        Sets index to 0 because the element after the head is
        the first element
        */
        public MyListIterator(){

            left = MyLinkedList.this.head; 
            right = MyLinkedList.this.head.getNext(); 
            this.idx = 0; //Index is the index of the right node
            this.forward = true;
            this.canRemoveOrSet = false; 
        }
        
                /**
     * A checker method to ensure that 
     * a node exists after the right node of the iterator
     * Important for the method Next()
     */
        public boolean hasNext() {
            
            //this.right.getNext() == null
            if (this.right == MyLinkedList.this.tail){//|| this.right == MyLinkedList.this.tail

                return false;
            } else {

                return true;
            }
        }
        

        /**
        * Moves the iterator to the node after the current right node
        * Returns the previous right node
        */
        public E next(){

            if (this.hasNext()){ //something feels off here...

                E toReturn = this.right.data; 
                this.left = this.right;
                this.right = this.right.getNext();
                this.idx += 1;
                this.forward = true;
                this.canRemoveOrSet = true; 
                return toReturn; 

            } else {

                throw new NoSuchElementException(); 
            }
        }

        /**
     * A checker method to ensure that 
     * a node exists before the left node of the iterator
     * Important for the method Previous()
        */
        public boolean hasPrevious(){

            //this.left.getPrev() == null
            if (this.left == MyLinkedList.this.head){ //|| this.left == MyLinkedList.this.head

                return false;
            } else {

                return true;
            }

        }

        /**
        * Moves the iterator to the node before the current left node
        * Returns the previous left node
        */
        public E previous(){

            if (this.hasPrevious()){

                E toReturn = this.left.data; 
                this.right = this.left; //Start backward movement
                this.left = this.left.getPrev();
                //How did I know to fix the getNext to getPrev? By noticing that left is NOT this.head nor this.head.getNext(), 
                //So it must have been changing in a way that was totally backwards <-- knew to compare to this.head.getNext()
                //Becuase of earlier tests of this.left being initialized but not changing
                this.idx -= 1;
                this.canRemoveOrSet = true; 
                this.forward = false;
                return toReturn; 

            } else {

                throw new NoSuchElementException(); 
            }

        }

                /**
        * Returns the index of the right node
        Since the right node is the one that'd be returned 
        by next()
        */
        public int nextIndex(){

            return this.idx;
        }

        /**
        * Returns the index of the left node
        Since the left node is the one that'd be returned 
        by previous()
        */
        public int previousIndex(){

            return this.idx - 1;

        }

        /**
        * Inserts an element to the Linked List
        By reconnecting the left node and right node
        to the added node in between both
        */
        public void add(E element){

            //Node newNode = new Node(element); 
            // MyLinkedList.this.add(this.previousIndex(), element); //left and right have a node in between them now

            if (element == null){

                throw new NullPointerException();
            }

            Node newNode = new Node(element);
            this.right.setPrev(newNode);
            this.left.setNext(newNode);
            newNode.setPrev(this.left);
            newNode.setNext(this.right);
            this.left = newNode; 
            this.canRemoveOrSet = false; 
            this.idx += 1;

            //Need to implement addOnEmptyList and addOnEndIndex
            //Go to office hours about style.
        }

        /**
        * Based on the most recent movement
        of the iterator, this method 
        sets either the left or right node 
        to the inputted element
        */
        public void set(E element){


            if (element == null){

                throw new NullPointerException();
            }

            if (canRemoveOrSet == false){

                throw new IllegalStateException();
            }
            if (this.forward == true){ //If the iterator moved forward and returned what is now the left node

                this.left.setElement(element);
            } else {

                this.right.setElement(element);
            }
        }

                /**
        * Similar to set,
        but removes the node and 
        reconnects the remaining iterator
        node with the remainder of the Linked
        List
        */
        public void remove(){

            if (canRemoveOrSet == false){

                throw new IllegalStateException();
            }

            if (this.forward == true){ //If the iterator moved forward and returned what is now the left node

               // MyLinkedList.this.remove(this.idx - 1); //Idx points to the element at the right

                //I'll do the reconnect manually instead of with a function call to avoid errors SO THAT the iterator updates!
                //It's wiser to do it directly with the iterator than with the MyLinkedList class because it heavily reduces complications

                this.right.setPrev(this.left.getPrev());
                this.left.setNext(null);
                this.left = this.left.getPrev();
                this.left.setNext(this.right);
                this.idx -= 1;
            } else {

                this.left.setNext(this.right.getNext());
                this.right.setPrev(null);
                this.right = this.right.getNext();
                this.right.setPrev(this.left);
            }

            this.canRemoveOrSet = false;

        }
    }
}
