package manager;

import java.util.LinkedList;
import java.util.List;

public class ProducerConsumer<T> {
    private static final int BUFFER_MAX_SIZE = 1;
    private final List<T> buffer = new LinkedList<>();

    public synchronized void produce(T value) throws InterruptedException {
        while (buffer.size() == BUFFER_MAX_SIZE) {
            wait();
        }
        buffer.add(value);
        notify();
    }

    public synchronized T consume() throws InterruptedException {
        while (buffer.size() == 0) {
            wait();
        }
        T result = buffer.remove(0);
        notify();
        return result;
    }
}
