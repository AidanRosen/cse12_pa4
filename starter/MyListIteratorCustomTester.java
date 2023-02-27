// DO NOT CHANGE THE METHOD NAMES

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.*;

public class MyListIteratorCustomTester {


    private MyLinkedList listLen0, listLen1, listLen2;
    private MyLinkedList.MyListIterator listLen0iter, listLen1Iter, listLen2Iter;


    /**
     * This sets up the test fixture. JUnit invokes this method before
     * every testXXX method. The @Before tag tells JUnit to run this method
     * before each test.
     */
    @Before
    public void setUp() throws Exception {

        listLen0 = new MyLinkedList();
        listLen0iter = listLen0.new MyListIterator();
        
        listLen1 = new MyLinkedList();
        listLen1.add("Testing node");

        listLen1Iter = listLen1.new MyListIterator();

        listLen2 = new MyLinkedList();
        listLen2.add("First node");
        listLen2.add("Second node");

        listLen2Iter = listLen2.new MyListIterator(); 
    }

    /**
     * Aims to test the next() method when iterator is at end of the list 
     */
    @Test
    public void testNextEnd() {

        assertThrows(NoSuchElementException.class, () -> {listLen0iter.next();});
        assertEquals("Testing node", listLen1Iter.next());
        assertThrows(NoSuchElementException.class, () -> {listLen1Iter.next();});

        
    }

    /**
     * Aims to test the previous() method when iterator is at the start of the 
     * list 
     */
    @Test
    public void testPreviousStart() {

        assertThrows(NoSuchElementException.class, () -> {listLen0iter.previous();});
        assertThrows(NoSuchElementException.class, () -> {listLen1Iter.previous();});

        assertEquals("First node", listLen2Iter.next());
        assertEquals("First node", listLen2Iter.previous());
        assertThrows(NoSuchElementException.class, () -> {listLen2Iter.previous();});

    }

    /**
     * Aims to test the add(E e) method when an invalid element is added
     */
    @Test
    public void testAddInvalid() {

        assertThrows(NullPointerException.class, () -> {listLen1Iter.add(null);});
        Object element = null; 
        assertThrows(NullPointerException.class, () -> {listLen1Iter.add(element);});
        
    }

    /**
     * Aims to test the set(E e) method when canRemoveOrSet is false
     */
    @Test
    public void testCantSet() {

        listLen2Iter.canRemoveOrSet = false;
        assertThrows(IllegalStateException.class, () -> {listLen1Iter.set("testing");});

    }


    /**
     * Aims to test the set(E e) method when an invalid element is set
     */
    @Test
    public void testSetInvalid() {

        listLen1Iter.forward = false; 
        assertThrows(NullPointerException.class, () -> {listLen1Iter.set(null);});
        Object element = null; 
        listLen2Iter.forward = false; //Check this later, going in one direction could be troublesome 
        assertThrows(NullPointerException.class, () -> {listLen1Iter.set(element);});

    }

    /**
     * Aims to test the remove() method when canRemoveOrSet is false
     */
    @Test
    public void testCantRemove() {

        listLen2Iter.canRemoveOrSet = false;
        listLen2Iter.forward = false;
        assertThrows(IllegalStateException.class, () -> {listLen1Iter.remove();});

    }

    /**
     * Aims to tests the hasNext() method at the end of a list
     */
    @Test
    public void testHasNextEnd() {

        assertEquals(false, listLen0iter.hasNext());
        listLen1Iter.next();
        assertEquals(false, listLen1Iter.hasNext());
    }

    /**
     * Aims to test the hasPrevious() method at the start of a list
     */
    @Test
    public void testHasPreviousStart() {

        assertEquals(false, listLen0iter.hasPrevious());
    }

    /**
     * Aims to test the previousIndex() method at the start of a list
     */
    @Test
    public void testPreviousIndexStart() {

        assertEquals(-1, listLen0iter.previousIndex()); 
    }

    /**
     * Aims to test the nextIndex() method at the end of a list
     */
    @Test
    public void testNextIndexEnd() {

        assertEquals(0, listLen0iter.nextIndex()); 
        assertEquals(0, listLen1Iter.nextIndex());
        listLen1Iter.next();//send Len1Iter to end of the list 
        assertEquals(1, listLen1Iter.nextIndex());
    }
}
