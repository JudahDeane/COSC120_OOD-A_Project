
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


    public float getMaxPrice() {
        return maxPrice;
    }

    public String getDescription(){
        String desc = "";
        for(Preferences key: preferences.keySet()){
            desc += key + " : "+getPreference(key);
        }
        return  desc;
    }
    public boolean compareDreamItem(DreamItem preference){
        for (Preferences key: preferences.keySet()){
            if (preference.getPreference(key).equals("NA")) {return true;}
            if(preferences.get(key).equals(preference.getPreference(key))){return true;}
        }
        return false;
    }

}
