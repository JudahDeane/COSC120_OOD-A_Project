/**
 * code created by Judah Deane (Judahdeane2@gmail.com)
 * for TRIMESTER 2 2026 COSC120 Object Oriented Programming
 * @github https://github.com/JudahDeane/COSC120_OOD-A_Project.git
 *
 */

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
     * get every preference key in all items filtered by the type
     * @param type the type
     * @return a set of every preference
     */
    public Set<Preferences> getAllPreferences(Type type){
        Set<Item> setType = new LinkedHashSet<>();
        Set<Preferences> set = new LinkedHashSet<>();
        for(Item itemType:allItems){
            if (itemType.dreamItem().getPreference(Preferences.TYPE).getFirst().equals(type)){
                setType.add(itemType);
            }
        }
        for(Item item: setType){

            for (Preferences preference:item.dreamItem().getItemMap().keySet()) {
                Object firstVal = item.dreamItem().getPreference(preference).getFirst();

                // this code bellow to check the value of nested collections was made with help by Google Gemini


                if (firstVal instanceof Collection<?> col && !col.isEmpty()) {
                    firstVal = col.iterator().next();
                }

                if (firstVal != null && !"NA".equals(firstVal.toString())) {
                    set.add(preference);
                }
            }
        }
        return new LinkedHashSet<>(set);
    }
    /**
     * get all the values for a preference in the allitems array
     * @param preference the preference to look for like Preference.CHEESE
     * @return a set of all the preferences
     */
    public Set<String> getAllPreferenceValues(Preferences preference,Type type){
        Set<String> Set = new LinkedHashSet<>();
        List<Item> allItems = new ArrayList<>(this.allItems);
        Set.add("Don't Mind");
        Set<Item> typeList = new LinkedHashSet<>();
        for(Item item:allItems){
            if(item.dreamItem().getPreference(Preferences.TYPE).getFirst().equals(type)){
                typeList.add(item);
            }
        }
        for(Item item:typeList){
            for(Object obj: item.dreamItem().getPreference(preference)){
                if(obj.equals("NA")){
                    continue;
                }
                Set.add((String) obj);
            }

        }

        return Set;
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
