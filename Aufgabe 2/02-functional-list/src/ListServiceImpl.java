public class ListServiceImpl implements ListService {

    public List prepend (final int value, final List list){
        return new List(value,list);
    }

    public List append (final int value, final List list) {
        if (list == null){
            return new List(value,null);
        }
        return new List(list.head, append(value, list.tail));
    }

}
