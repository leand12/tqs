import java.util.LinkedList;

public class DListStack<T> implements ITqsStack<T> {
    private LinkedList<T> elementsCollection = new LinkedList<>();
    private Integer maxSize;

    public DListStack() {}

    public DListStack(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean isEmpty() {
        return elementsCollection.isEmpty();
    }

    @Override
    public int size() {
        return elementsCollection.size();
    }

    @Override
    public void push(T element) {
        if (maxSize != null && elementsCollection.size() >= maxSize)
            throw new IllegalStateException();
        elementsCollection.addFirst(element);
    }

    @Override
    public T pop() {
        return elementsCollection.removeFirst();
    }

    @Override
    public T peek() {
        return elementsCollection.getFirst();
    }
}
