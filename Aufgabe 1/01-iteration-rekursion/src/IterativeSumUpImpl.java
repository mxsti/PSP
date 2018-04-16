public class IterativeSumUpImpl implements SumUp {

     public long sumUp(final int numbers[], final int fromIndex){
        long sum = 0;
        for (int i = fromIndex; i < numbers.length; i++) {
            sum += numbers[i];
        }
        return sum;
    }

}
