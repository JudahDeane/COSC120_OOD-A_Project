
import java.util.*;

public class DreamItem {
    private final Map<Preferences,List<Object>> preferences;

    private float maxPrice;

    /**
     *  a constructer for when asking for prefrences
     * @param preferences a map with the list of  preferences
     * @param maxPrice a max prcie from the user
     */

    public DreamItem(Map<Preferences, List<Object>> preferences, float maxPrice) {
        this.preferences = preferences;
        this.maxPrice = maxPrice;
    }

    /**
     * a constructor for the normal
     * @param preferences A constructor for a real item
     */
    public DreamItem(Map<Preferences, Object> preferences) {
        this.preferences = new HashMap<>();
        for (Preferences pref: preferences.keySet()){
            List<Object> list = new ArrayList<>();
            list.add(preferences.get(pref));
            this.preferences.put(pref,list);
        }



    }
    /**
     * a getter for the preferences
     * @return a map of the preference
     */
    public Map<Preferences, List<Object>> getItemMap() {
        return new HashMap<>(preferences);
    }

    /**
     * gets the value of a specified preference
     * @param key a preference enum of the value you want returned
     * @return the value of the preference in the map
     */
    public List<Object> getPreference(Preferences key) {
        return getItemMap().get(key);
    }
    /**
     * getter for max price
     * @return the max price variable
     */
    public float getMaxPrice() {
        return maxPrice;
    }

    /**
     * get a descriptions of the item
     * @return a string of the list of descriptions
     */
    public String getDescription(){
        StringBuilder desc = new StringBuilder();
        for(Preferences key: preferences.keySet()){
            desc.append( key).append(" : ").append(getPreference(key).toString().replace("[","").replace("]","")).append("\n");
        }
        return  desc.toString();
    }

    /**
     * compares two dream items and returns true if they are the same
     * @param preference a dream item to compare this item to
     * @return True or false if they match their preferences
     */
    public boolean compareDreamItem(DreamItem preference){
        for (Preferences key: preferences.keySet()){
            for(Object obj: preference.getPreference(key)) {
                if ("NA".equals(obj)){
                    continue;
                }
                if (preferences.get(key).equals(preference.getPreference(key))) {
                    return true;
                }
            }
        }
        return false;
    }

}
