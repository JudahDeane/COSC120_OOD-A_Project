
import java.util.*;

public class DreamItem {
    private final Map<Preferences,Object> preferences;

    private float maxPrice;

    /**
     *  a constructer for when asking for prefrences
     * @param preferences a map with the list of  preferences
     * @param maxPrice a max prcie from the user
     */

    public DreamItem(Map<Preferences, Object> preferences, float maxPrice) {
        this.preferences = preferences;
        this.maxPrice = maxPrice;
    }

    /**
     * a constructor for the normal
     * @param preferences A constructor for a real item
     */
    public DreamItem(Map<Preferences, Object> preferences) {
        this.preferences = preferences;

    }
    /**
     * a getter for the preferences
     * @return a map of the preference
     */
    public Map<Preferences, Object> getItemMap() {
        return new HashMap<>(preferences);
    }

    /**
     * gets the value of a specified preference
     * @param key a preference enum of the value you want returned
     * @return
     */
    public Object getPreference(Preferences key) {
        return getItemMap().get(key);
    }

    /**
     * gets a list of the preferences of the dream item
     * @return a String contaning a list of preferences
     */
    public String getPreferencesString(){
        StringBuilder out = new StringBuilder();
        for (Preferences key: preferences.keySet()){
            out.append(key.toString());
            out.append(" : ");
            out.append(preferences.get(key).toString()).append("\n");
        }
        return out.toString();
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
        String desc = "";
        for(Preferences key: preferences.keySet()){
            desc += key + " : "+getPreference(key);
        }
        return  desc;
    }

    /**
     * compares two dream items and returns true if they are the same
     * @param preference a dream item to compare this item to
     * @return True or false if they match their preferences
     */
    public boolean compareDreamItem(DreamItem preference){
        for (Preferences key: preferences.keySet()){
          if ("NA".equals(preference.getPreference(key))) {return true;}
            if(preferences.get(key).equals(preference.getPreference(key))){return true;}
        }
        return false;
    }

}
