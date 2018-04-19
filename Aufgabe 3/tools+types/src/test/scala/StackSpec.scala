//Beispiel von http://www.scalatest.org/quick_start

import org.scalatest._
import java.util.Stack
import java.util.EmptyStackException

class StackSpec extends FlatSpec with Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() shouldBe 2
    stack.pop() shouldBe 1
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[Int]
    intercept[EmptyStackException] {
      emptyStack.pop()
    } 
  }
  
}