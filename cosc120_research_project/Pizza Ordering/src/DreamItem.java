/**
 * code created by Judah Deane (Judahdeane2@gmail.com)
 * for TRIMESTER 2 2026 COSC120 Object Oriented Programming
 * @github https://github.com/JudahDeane/COSC120_OOD-A_Project.git
 *
 */

import javax.management.ObjectName;
import java.lang.reflect.Array;
import java.util.*;

/**
 * this is the search template which contains the objects to search by
 */
public class DreamItem {
    private final Map<Preferences,List<Object>> preferences;

    private float maxPrice;

    /**
     *  a constructor for when asking for preferences
     * @param preferences a map with the list of  preferences
     * @param maxPrice a max price from the user
     */

    public DreamItem(Map<Preferences, List<Object>> preferences, float maxPrice) {
        this.preferences = preferences;
        this.maxPrice = maxPrice;
    }

    /**
     * a constructor for a real item
     * @param preferences A constructor for a real item
     */
    public DreamItem(Map<Preferences, Object> preferences) {
        this.preferences = new HashMap<>();
        for (Preferences pref: preferences.keySet()){
            List<Object> list = new ArrayList<>();
            Object value = preferences.get(pref);
            // If the value is already a list (like cheese, meat, sauce), add its contents
            if (value instanceof Collection) {
                list.addAll((Collection<?>) value);
            }
            // If the value is a single object (like your boolean allergens), add it directly
            else {
                list.add(value);
            }

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
     * get a descriptions of the preferences in the map
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
     * compares two dream items and returns true if the object parsed is contained in the dream item where this method is called on
     * @param preference a dream item to compare this item to
     * @return True or false if they match their preferences
     */
    public boolean compareDreamItem(DreamItem preference){
        for (Preferences key: preferences.keySet()){



            for(Object obj: preference.getPreference(key)) {
                for(Object thisObj:preferences.get(key)) {
                    List<Object> thisPreference = new ArrayList<>(preference.getPreference(key));

                    if (thisPreference.contains("NA")){
                        continue;
                    }
                    List<Preferences> typeCheck = new ArrayList<>();
                    typeCheck.add(Preferences.CHEESE);
                    typeCheck.add(Preferences.INGREDIENTS);
                    typeCheck.add(Preferences.MEAT);
                    typeCheck.add(Preferences.SAUCE);
                    // checks if it has multiple items like a List if so do an inverted check
                    if(typeCheck.contains(key)) {


                        // invert the check because all list items are inverted as well
                        if (!thisObj.equals(obj)) {
                            continue;
                        }
                    }else {
                        // this is for the boolean types like vegan
                        if (obj.equals(false)) {
                            continue;
                        }



                        if ("NA".equals(obj)) {
                            continue;
                        }

                        if (thisObj.equals(obj)) {
                            continue;
                        }
                    }

                    return false;
                }

            }

        }
        return true;

    }

}
