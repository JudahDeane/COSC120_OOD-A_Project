import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class OrderPizza {
    private final static String appName = "Judah's Pizza Palace";



    public static void main(){
        //AllItems allItems = loadData();
        loadData().dreamItem().getPreference(Preferences.CHEESE);
        // DreamItem dreamItem = getUserPreference(allItems);

    }


    private static DreamItem getUserPreference(AllItems allItems){
        Type itemType = (Type) JOptionPane.showInputDialog(null,"Please enter what item you would like to buy",appName,JOptionPane.QUESTION_MESSAGE,null,Type.values(),null);
        if (itemType == null) {
            System.exit(0);
        };
        String cheese = (String) JOptionPane.showInputDialog(null,"Please enter what cheese would you like (NA if you don't mind)",appName,JOptionPane.QUESTION_MESSAGE,null,allItems.getAllCheese().toArray(),null);
        if (cheese == null) {
            System.exit(0);
        };
        String meat = (String) JOptionPane.showInputDialog(null,"Please enter what meat would you like (NA if you don't mind)",appName,JOptionPane.QUESTION_MESSAGE,null,allItems.getAllMeat(),null);
        if (meat == null) {
            System.exit(0);
        };
        String ingredients = (String) JOptionPane.showInputDialog(null,"Do you have preferred ingredients (NA if you don't mind)",appName,JOptionPane.QUESTION_MESSAGE,null,allItems.getAllIngredients(),null);
        if (ingredients == null) {
            System.exit(0);
        };
        String allergens = (String) JOptionPane.showInputDialog(null,"Do you have any allergens (NA if you don't have any)",appName,JOptionPane.QUESTION_MESSAGE,null,allItems.getAllAllergens(),null);
        if (allergens == null) {
            System.exit(0);
        };
        String maxPriceString = (String) JOptionPane.showInputDialog(null,"What is the max price you would pay",appName,JOptionPane.QUESTION_MESSAGE);
        if (maxPriceString == null) {
            System.exit(0);
        };
        int maxPrice = -1;
        while (true) {
            try {
                maxPrice = Integer.parseInt(maxPriceString);
            } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "please enter a valid integer");
            continue;
            }
            break;
        }
        Map<Preferences,Object> map = new HashMap<>();
        map.put(Preferences.TYPE,itemType);
        map.put(Preferences.MEAT,meat);
        map.put(Preferences.CHEESE,cheese);
        map.put(Preferences.INGREDIENTS,ingredients);
        map.put(Preferences.ALLERGENS,allergens);


       return new DreamItem(map,maxPrice);
    }


    private static Item loadData(){
        AllItems allItems = new AllItems();
        Map<Preferences,Object> preferences = new HashMap<>();
        List<String> lines = new ArrayList<>();
        Path path = Path.of("pizza_menu_database_v10.csv");
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e){
            System.out.println("error reading file");
            System.exit(0);
        }
        Item item2 = null;
        for (int i =1;i<lines.size();i++){
            String[] line = lines.get(i).split( ",");
            String name = line[0].replace(" ","_");
            Type type = null;

            type = Type.valueOf(line[1].toUpperCase());

            float price = 0;
            try {
                price = Float.parseFloat(line[2].replace("$",""));
            }catch(NumberFormatException e ){
                System.out.println("error reading price");
                System.exit(0);
            }
            System.out.println(line[3]);
            String cheeseArray = line[3];
            String meatArray = line[4];
            String ingredientsArray = line[5];
            String sauceArray = line[6];
            String allergensArray = line[7];
            Set<String> cheese = new TreeSet<>(List.of(cheeseArray));
            Set<String> meat = new TreeSet<>(List.of(meatArray));
            Set<String> ingredients = new TreeSet<>(List.of(ingredientsArray));
            Set<String> sauce = new TreeSet<>(List.of(sauceArray));
            Set<String> allergens = new TreeSet<>(List.of(allergensArray));


            preferences.put(Preferences.CHEESE,cheese);
            preferences.put(Preferences.MEAT,meat);
            preferences.put(Preferences.INGREDIENTS,ingredients);
            preferences.put(Preferences.SAUCE,sauce);
            preferences.put(Preferences.ALLERGENS,allergens);

            DreamItem dreamItem = new DreamItem(preferences);
            //System.out.println(dreamItem.getPreference(Preferences.CHEESE));
            Item item = new Item(name,type,price,dreamItem);
            System.out.println(item.dreamItem().getPreference(Preferences.CHEESE));
            item2 = item;
            //allItems.addItem(item);
            break;
        }

        return item2;



    }
}
