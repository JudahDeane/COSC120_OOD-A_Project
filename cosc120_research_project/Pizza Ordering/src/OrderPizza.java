import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class OrderPizza {

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
            allItems.addList(item);







        }

    }
}
