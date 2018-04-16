import static org.junit.Assert.*;

import org.junit.Test;


/**Testtreiber für die Implementierung des Interfaces {@link ListService}.*/
public class ListTest {

    private final ListService testee = new ListServiceImpl();

    @Test public void toString1(){
        assertEquals("1", new List(1, null).toString());
    }

    @Test public void toString3(){
        final List list = new List(1, new List(2, new List(3,null)));
        final String result = list.toString();
        assertEquals("1 2 3", result);
    }

    @Test public void prependTo0(){
        final List result = testee.prepend(1, null);
        final String actual = result.toString();
        assertEquals("1", actual);
    }
    
    @Test public void prependTo3(){
        final List list1 = new List(1, new List(2, new List(3,null)));
        final List result = testee.prepend(4, list1);
        final String actual = result.toString();
        assertEquals("4 1 2 3", actual);
    }
    
    @Test public void appendTo0(){
        final List result = testee.append(1, null);
        final String actual = result.toString();
        assertEquals("1", actual);
    }
    
    @Test public void appendTo3(){
        final List list1 = new List(1, new List(2, new List(3,null)));
        final List result = testee.append(4, list1);
        final String actual = result.toString();
        assertEquals("1 2 3 4", actual);
    }

}
