/**
 * Einfach vorwärts verkettete Liste von Ganzzahlen à la LISP.
 * Eine Liste mit den Elementen 1, 2 und 3 kann gebildet werden durch new List(1, new List(2, new List(3,null))).
 * Eine leere Liste wird durch null dargestellt.
 * @author Christoph Knabe
 * @since 2011-10-07
 * @version 2018-04-06
 */
public class List {
    
    /**Inhalt eines Listenknotens*/
    public final int head;
    
    /**Vorwärtsreferenz zum nächsten Listenknoten. Das Listenende wird durch null markiert.*/
    public final List tail;
    
    /**Dieser Konstruktor entspricht der CONS-Operation von LISP.*/
    public List(final int head, final List tail){
        this.head = head;
        this.tail = tail;
    }
    
    /**Liefert die String-Darstellung einer aus Paaren gebildeten, über die Referenz tail vorwärts verketteten Liste.
     * Man sieht hier den rekursiven Charakter der Implementierung.
     * @implNote Da Java keine rein funktionale Sprache ist, gibt es keine sparsame Implementierung von (End-)Rekursion.
     * Daher kommt es bei zu langen Listen in dieser Methode zu {@link StackOverflowError}.
     * */
    public String toString(){
        final String headString = Integer.toString(head);
        return  tail==null ? headString : headString + " " + tail.toString();
    }
    
}
