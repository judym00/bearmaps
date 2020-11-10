package bearmaps.utils.pq;
import org.junit.Test;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MinHeapPQTest {

    @Test
    public void testSwim() {
        MinHeapPQ<String> minHeap = new MinHeapPQ<String>();
        minHeap.insert("a", 2.0);
        minHeap.insert("b", 3.0);
        minHeap.insert("c", 1.0);
        assertEquals(minHeap.peek(), "c");
    }

    @Test
    public void testremoveSmallest() {
        MinHeapPQ<String> x = new MinHeapPQ<String>();
        x.insert("love", 70.0);
        x.insert("aove", 10.0);
        x.insert("bove", 20.0);
        x.insert("cove", 90.0);
        x.insert("dove", 11.0);
        assertEquals(x.poll(), "aove");
    }

    @Test(expected = NoSuchElementException.class)
    public void testChangePriority() {
        MinHeapPQ<String> minHeaps = new MinHeapPQ<String>();
        minHeaps.insert("love", 70.0);
        minHeaps.changePriority("helllo", -19.0);
        minHeaps.changePriority("hiii", 30);
        minHeaps.insert("vivid", -6000);
        minHeaps.changePriority("vivid", 5000);
        minHeaps.changePriority("name", 2.0);
        minHeaps.poll();
    }

    @Test(expected = NoSuchElementException.class)
    public void testChangePriority1() {
        MinHeapPQ<String> minHeaps = new MinHeapPQ<String>();
        minHeaps.poll();
    }

    @Test(expected = NoSuchElementException.class)
    public void testChangePriority2() {
        MinHeapPQ<String> minHeaps = new MinHeapPQ<String>();
        minHeaps.changePriority("helllo", -19.0);
    }

    @Test(expected = NoSuchElementException.class)
    public void testSink() {
        MinHeapPQ<String> minHeaps = new MinHeapPQ<String>();
        minHeaps.poll();
        minHeaps.poll();
    }

    @Test
    public void testitemnothere() {
        MinHeapPQ<String> minHeaps = new MinHeapPQ<String>();
        minHeaps.size();

    }

    @Test
    public void testcontains() {
        MinHeapPQ<String> minHeaps = new MinHeapPQ<String>();
        minHeaps.insert("vivid", -100);
        minHeaps.contains("vivid");
        assertTrue(minHeaps.contains("vivid"));
    }
}
