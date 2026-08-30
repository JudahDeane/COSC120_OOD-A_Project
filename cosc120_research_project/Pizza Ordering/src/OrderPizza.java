import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class OrderPizza {
    private final String appName = "Judah's Pizza Palace";
    private AllItems allItems = new AllItems();





    private DreamItem getUserPreference(){
        Type itemType = (Type) JOptionPane.showInputDialog(null,"Please enter what item you would like to buy",appName,JOptionPane.QUESTION_MESSAGE,null,Type.values(),null);
        if (itemType == null) {
            System.exit(0);
        };
        String cheese = (String) JOptionPane.showInputDialog(null,"Please enter what cheese would you like (NA if you don't mind)",appName,JOptionPane.QUESTION_MESSAGE,null,allItems.getAllCheese(),null);
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


    private void loadData(){
        AllItems allItems = new AllItems();
        Map<Preferences,Object> preferences = new HashMap<>();
        List<String> lines = new ArrayList<>();
        Path path = Path.of("pizza_menu-database_v10.csv");
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e){
            System.out.println("error reading file");
            System.exit(0);
        }
        for (int i =1;i<lines.size();i++){
            String[] line = lines.get(i).split( ",");
            String name = line[0].replace(" ","_");
            Type type = null;

            type = Type.valueOf(line[1].toUpperCase());

            float price = 0;
            try {
                price = Float.parseFloat(line[2]);
            }catch(NumberFormatException e ){
                System.out.println("error reading price");
                System.exit(0);
            }

            String[] cheese = line[3].replace("[", "").replace("[","").split("|");
            String[] meat = line[4].replace("[", "").replace("[","").split("|");
            String[] ingredients = line[5].replace("[", "").replace("[","").split("|");
            String[] sauce = line[5].replace("[", "").replace("[","").split("|");
            String[] allergens = line[5].replace("[", "").replace("[","").split("|");

            preferences.put(Preferences.CHEESE,cheese);
            preferences.put(Preferences.MEAT,meat);
            preferences.put(Preferences.INGREDIENTS,ingredients);
            preferences.put(Preferences.SAUCE,sauce);
            preferences.put(Preferences.ALLERGENS,allergens);

            DreamItem dreamItem = new DreamItem(preferences);
            Item item = new Item(name,type,price,dreamItem);
            allItems.addItem(item);
        }



    }
}
