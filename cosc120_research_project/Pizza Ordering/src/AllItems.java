
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
    public Object[] getAllMeat(){
        List<String> meat = new ArrayList<>();
        for(Item item:allItems){
            meat.add(item.dreamItem().getPreference(Preferences.MEAT).toString());
        }
        meat.add("NA");
        return meat.toArray();
    }

    public Set<String> getAllCheese(){
        Set<String> cheese = new TreeSet<>();
        for(Item item:allItems){


            cheese.addAll((Collection<? extends String>) item.dreamItem().getPreference(Preferences.CHEESE));
        }
        cheese.add("NA");
        return cheese;
    }

    public Object[] getAllIngredients(){
        Set<String> ingredients = new TreeSet<>();
        for(Item item:allItems){
            ingredients.add(item.dreamItem().getPreference(Preferences.INGREDIENTS).toString());
        }
        ingredients.add("NA");
        return ingredients.toArray();
    }
    public Object[] getAllSauce(){
        List<String> sauce = new ArrayList<>();
        for(Item item:allItems){
            sauce.add(item.dreamItem().getPreference(Preferences.SAUCE).toString());
        }
        sauce.add("NA");
        return sauce.toArray();
    }
    public Object[] getAllAllergens(){
        List<String> allergens = new ArrayList<>();
        for(Item item:allItems){
            allergens.add(item.dreamItem().getPreference(Preferences.ALLERGENS).toString());
        }
        allergens.add("NA");
        return allergens.toArray();
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
