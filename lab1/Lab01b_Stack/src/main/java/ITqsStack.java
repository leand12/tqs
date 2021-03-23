public interface ITqsStack<T> {
    boolean isEmpty();

    int size();

    void push(T element);

    T pop();

    T peek();
}
