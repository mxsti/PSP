public interface SumUp {

    /**Liefert die Summe der übergebenen Ganzzahlen ab dem angegebenen Index.
     * Beispiele: 
     * sumUp(new int[]{1,2,3,4,5,6,7,8,9}, 0) liefert 45.
     * sumUp(new int[]{1,2,3,4,5,6,7,8,9}, 5) liefert 30.
    */
    long sumUp(int numbers[], int fromIndex);

}
