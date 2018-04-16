import org.junit.Assert;
import org.junit.Test;

/**Testet die beiden Implementierungen des Interfaces SumUp.
 * @author Christoph Knabe
 * @since 2011-09-23
 */
public class SumUpTest extends Assert {
    
    /**Quantity of random numbers to be summed up. Try to modify!*/
    private static final int _quantity = 5000; //May result in StackOverflow!

    private static final int _average = 100;
    private static final int _count = 100000;
    private static final int[] _numbers = _produceNumbers(_quantity, _average);
    private static final int[] _1to9 = {1,2,3,4,5,6,7,8,9};

    @Test public void iterativeSumUp(){
        _massTest(new IterativeSumUpImpl());     
    }
    
    @Test public void recursiveSumUp(){
        _massTest(new RecursiveSumUpImpl());     
    }

    /**Returns even n or n-1 numbers with a given average.*/
    private static int[] _produceNumbers(int n, int average) {
        final int middleI = n/2;
        final int[] result = new int[middleI*2];
        result[middleI] = average;
        for(int i=0; i<middleI; i++){
            final int modify = (int)Math.round(Math.random()*1000);
            result[i] = average+modify;
            result[result.length-1-i] = average-modify;            
        }
        return result;
    }

    /**Tests sumUp with sample data, and with mass data summing up _numbers.*/
    private void _massTest(final SumUp sumUp) {    
        assertEquals(45, sumUp.sumUp(_1to9, 0));
        assertEquals(30, sumUp.sumUp(_1to9, 5));
        for(int i=0; i<_count; i++){
            assertEquals(_average*_numbers.length, sumUp.sumUp(_numbers,0));
        }
    }
    
    
}
