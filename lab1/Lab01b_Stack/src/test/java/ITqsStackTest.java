import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;


public class ITqsStackTest {
    private ITqsStack<String> newStack;
    private ITqsStack<String> stack3elems;
    private ITqsStack<String> fullBoundedStack;

    @BeforeEach
    public void setUp() {
        newStack = new DListStack<>();
        stack3elems = new DListStack<>();
        fullBoundedStack = new DListStack<>(3);

        stack3elems.push("Aveiro");
        stack3elems.push("Braga");
        stack3elems.push("Coimbra");

        fullBoundedStack.push("Aveiro");
        fullBoundedStack.push("Braga");
        fullBoundedStack.push("Coimbra");
    }

    @AfterEach
    public void tearDown() {
    }

    @DisplayName("A stack is empty on construction")
    @Test
    public void testIsEmpty() {
        DListStack<String> stack = new DListStack<>();
        assertTrue(stack.isEmpty(), "empty stack should report empty");
    }

    @DisplayName("A stack has size 0 on construction")
    @Test
    public void testSize() {
        DListStack<String> stack = new DListStack<>();
        assertEquals(0, stack.size(), "empty stack reports 0 elements");
    }

    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
    @Test
    public void testPush3elems() {
        newStack.push("Aveiro");
        newStack.push("Braga");
        newStack.push("Coimbra");

        assertEquals(3, newStack.size());
        assertFalse(newStack.isEmpty(), () -> "non-empty stack reporting as empty!");
    }

    @DisplayName("If one pushes x then pops, the value popped is x")
    @Test
    public void testPop() {
        assertEquals("Coimbra", stack3elems.pop());
        assertEquals(2, stack3elems.size());
    }

    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
    @Test
    public void testPeek() {
        assertEquals("Coimbra", stack3elems.peek());
        assertEquals(3, stack3elems.size());
    }

    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
    @Test
    public void testSizeAfterPop() {
        int s = stack3elems.size();
        for (int n = 0; n < s; n++)
            stack3elems.pop();
        assertTrue(stack3elems.isEmpty());
        assertEquals(0, stack3elems.size());
    }

    @DisplayName("Popping from an empty stack does throw a NoSuchElementException")
    @Test
    public void testPopOnEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> newStack.pop());
    }

    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException")
    @Test
    public void testPeekOnEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> newStack.peek());
    }

    @DisplayName("For bounded stacks only, pushing onto a full stack does throw an IllegalStateException")
    @Test
    public void testPushOnFullStack() {
        assertThrows(IllegalStateException.class, () -> fullBoundedStack.push("Aveiro"));
    }

}

