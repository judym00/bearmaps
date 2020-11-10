package bearmaps.utils.pq;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;
    // implementing the more optimized version)

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        return 2 * index ;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        return 2 * index + 1;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        if (index > 1) {return index / 2; }
        return -1 ;
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        if (contents.size() <= index1) {
            return index2;
        } else if (contents.size() <= index2) {
            return index1;
        } else if (contents.get(index1).compareTo(contents.get(index2)) >= 0) {
            return index2;
        } else if (contents.get(index1).compareTo(contents.get(index2)) < 0) {
            return index1;
        }
        return -1;
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        if (size > 0) {
            return contents.get(1);
        } else {
            throw new NoSuchElementException();
        }
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        E curr = contents.get(index);
        if (index == 1 || index == 0) {
            return;
        } else if (curr.compareTo(contents.get(getParentOf(index))) < 0 ) {
            swap(index,getParentOf(index));
            bubbleUp(index / 2);
        } else {
            return;
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        if (Math.min(getLeftOf(index),getRightOf(index)) >= contents.size()) {
            return;
        } else {
            E curr = contents.get(index);
            int smallerChild = min(getLeftOf(index), getRightOf(index));
            if (curr.compareTo(contents.get(smallerChild)) > 0){
                swap(index, smallerChild);
                bubbleDown(smallerChild);
            } else {
                return;
            }
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        if (contents.contains(element)) {
            throw new IllegalArgumentException();
        } else {
            int curr = contents.size();
            setElement(curr,element);
            bubbleUp(curr);
            size ++;
        }
    }

    /* Returns and removes the smallest element in the MinHeap. */
    public E removeMin() {
        if (size >= 1) {
            E Min = findMin();
            int last = contents.size() - 1;
            swap(1, last);
            contents.remove(last);
            bubbleDown(1);
            size --;
            return Min;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        if (!contains(element)) {
            throw new NoSuchElementException();
        } else {
            int index = 0;
            for (int i = 1; i < contents.size(); i++) {
                if (contents.get(i).equals(element)) {
                    setElement(i, element);
                    index = i;
                    break;
                }
            }
            bubbleUp(index);
            bubbleDown(index) ;
            return;
        }
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        return contents.contains(element);
    }
}
