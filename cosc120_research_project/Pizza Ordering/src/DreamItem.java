
import java.util.*;

public class DreamItem {
    private final Map<Preferences,Object> preferences;
    private final float minPrice;
    private final float maxPrice;

    /**
     *  a constructer for when asking for prefrences
     * @param preferences a map with the list of  preferences
     * @param minPrice a min price from the user
     * @param maxPrice a max prcie from the user
     */

    public DreamItem(Map<Preferences, Object> preferences, float minPrice, float maxPrice) {
        this.preferences = preferences;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    /**
     * a getter for the preferences
     * @return a map of the preference
     */
    public Map<Preferences, Object> getPreferences() {
        return preferences;
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
}
