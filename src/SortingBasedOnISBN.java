import java.util.Comparator;
import java.util.Map.Entry;

// This class contains a compare method for sorting by ISBN
public class SortingBasedOnISBN implements Comparator<Entry<String, Integer>> {
    public int compare(Entry<String, Integer> a0, Entry<String, Integer> a1) {
        int out = a0.getKey().compareTo(a1.getKey());
        return out;
    }
}
