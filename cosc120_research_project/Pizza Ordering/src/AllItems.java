
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class AllItems {
    public final List<Item> allItems = new ArrayList<>();

    public void addItem(Item item){
        allItems.add(item);
    }
    public Item getItem(String name){
        for (Item item:allItems){
            if (item.Name().equals(name)){
                return item;
            }
        }
        return null;
    }


    public Set<String> getAllPreference(Preferences preference){
        Set<String> cheese = new TreeSet<>();
        List<Item> allItems = new ArrayList<>(this.allItems);

        for(Item item:allItems){

            // I did not come up with the code to check cast a set, but I fully understand it
            Object preferenceObj = item.dreamItem().getPreference(preference);
            if (preferenceObj instanceof Set<?> preferenceSet) {
                for (Object element : preferenceSet) {
                    if (element instanceof String str) {
                        cheese.add(str);
                    }
                }
            }
                ;

        }
        cheese.add("NA");
        return cheese;
    }
    public List<Item> getList(){

        return new ArrayList<>(allItems);
    }

    /**
     * credit to andreases for the code
     * @param dreamItem
     * @return
     */
    public List<Item> getMatches(DreamItem dreamItem) {
        List<Item> matches = new ArrayList<>();
        for (Item item: allItems){
            if (!item.dreamItem().compareDreamItem(dreamItem)) {continue;}
            if (dreamItem.getMaxPrice()<item.price()){continue;}

            matches.add(item);
        }

        return matches;
    }
}
