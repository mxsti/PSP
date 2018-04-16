/**
 * @author Christoph Knabe
 * @since 2017-04-12
 */
public interface ListService {

    /**Liefert eine neue List mit dem übergebenen value vor der übergebenen list.*/
    List prepend(int value, List list);

    /**Liefert eine neue List mit dem übergebenen value hinter der übergebenen list.*/
    List append(int value, List list);

}
