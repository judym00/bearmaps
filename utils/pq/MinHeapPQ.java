package bearmaps.utils.pq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/* A PriorityQueue class that uses a min heap to maintain ordering. */
public class MinHeapPQ<T> implements PriorityQueue<T> {

    private MinHeap<PriorityItem> heap;

    public MinHeapPQ() {
        heap = new MinHeap<PriorityItem>();
    }

    @Override
    public void insert(T item, double priority) {
        PriorityItem i = new PriorityItem(item, priority);
        heap.insert(i);
    }

    @Override
    public boolean contains(T item) {
        return heap.contains(new PriorityItem(item, 0));
    }

    @Override
    public T peek() {
        return heap.findMin().item();
    }

    @Override
    public T poll() {
        T returnT = peek();
        heap.removeMin();
        return returnT ;
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        PriorityItem i = new PriorityItem(item, priority);
        heap.update(i);
    }

    public boolean isEmpty() {
        return heap.size() == 0;
    }

    public class PriorityItem implements Comparable<PriorityItem> {
        private T item;
        private double priorityValue;

        private PriorityItem(T item, double priorityValue) {
            this.item = item;
            this.priorityValue = priorityValue;
        }

        public T item() {
            return this.item;
        }

        public double priorityValue() {
            return this.priorityValue;
        }

        @Override
        public String toString() {
            return "(PriorityItem: " + this.item.toString() + ", "
                    + this.priorityValue + ")";
        }

        @Override
        public int compareTo(PriorityItem o) {
            double diff = this.priorityValue - o.priorityValue;
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            } else {
                return 0;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            } else if (getClass() == o.getClass()) {
                PriorityItem p = (PriorityItem) o;
                return p.item.equals(item);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }

}
