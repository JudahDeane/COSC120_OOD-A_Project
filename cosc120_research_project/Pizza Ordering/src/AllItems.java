
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class AllItems {
    public final List<Item> allItems = new ArrayList<>();

    /**
     * adds an item to the array
     * @param item the item to add
     */
    public void addItem(Item item){
        allItems.add(item);
    }

    /**
     * gets an item from the array based on the name of the item
     * @param name the name of the item to find
     * @return the item in the array with the provided name
     */
    public Item getItem(String name){
        for (Item item:allItems){
            if (item.Name().equals(name)){
                return item;
            }
        }
        return null;
    }

    /**
     * get all the values for a preference in the allitems array
     * @param preference the preference to look for like Preference.CHEESE
     * @return a set of all the preferences
     */
    public Set<String> getAllPreference(Preferences preference){
        Set<String> Set = new LinkedHashSet<>();
        List<Item> allItems = new ArrayList<>(this.allItems);
        Set.add("NA");

        for(Item item:allItems){

            // I did not come up with the code to check cast a set, but I fully understand it
            Object preferenceObj = item.dreamItem().getPreference(preference);
            if (preferenceObj instanceof Set<?> preferenceSet) {
                for (Object element : preferenceSet) {
                    if (element instanceof String str) {
                        if (!str.isEmpty()) {
                            Set.add(str);
                        }
                    }
                }
            }


        }

        return Set;
    }
    public List<Item> getList(){

        return new ArrayList<>(allItems);
    }

    /**
     * credit to Dr Andreas Shepley for the code
     * It uses the dreamitem match method to compare everything in the set and returns a list of matches
     * @param dreamItem the item to compare to
     * @return a list of items that match the criteria
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
