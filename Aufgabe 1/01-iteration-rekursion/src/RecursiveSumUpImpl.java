public class RecursiveSumUpImpl implements SumUp {

    public long sumUp(final int numbers[],final int fromIndex){
        if (fromIndex == numbers.length) {
            return 0;
        }else{
            return numbers[fromIndex] + sumUp(numbers, fromIndex + 1);
        }
    }

}
