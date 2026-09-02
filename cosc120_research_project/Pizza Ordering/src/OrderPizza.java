import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

public class OrderPizza {
    private final static String appName = "Judah's Pizza Palace";



    public static void main(){
        AllItems allItems = loadData();

       System.out.println(allItems.getAllPreference(Preferences.CHEESE));
       DreamItem dreamItem = getUserPreference(allItems);
       List<Item> matches = allItems.getMatches(dreamItem);
       chooseItem(matches);


    }


    private static DreamItem getUserPreference(AllItems allItems){
        Type itemType = (Type) JOptionPane.showInputDialog(null,"Please enter what item you would like to buy",appName,JOptionPane.QUESTION_MESSAGE,null,Type.values(),null);
        if (itemType == null) {
            System.exit(0);
        };
        String cheese = (String) JOptionPane.showInputDialog(null,"Please enter what cheese would you like (NA if you don't mind)",appName,JOptionPane.QUESTION_MESSAGE,null,allItems.getAllPreference(Preferences.CHEESE).toArray(),null);
        if (cheese == null) {
            System.exit(0);
        };
        String meat = (String) JOptionPane.showInputDialog(null,"Please enter what meat would you like (NA if you don't mind)",appName,JOptionPane.QUESTION_MESSAGE,null,allItems.getAllPreference(Preferences.MEAT).toArray(),null);
        if (meat == null) {
            System.exit(0);
        };
        String ingredients = (String) JOptionPane.showInputDialog(null,"do you have any ingeridients you don't like (NA if you don't mind)",appName,JOptionPane.QUESTION_MESSAGE,null,allItems.getAllPreference(Preferences.INGREDIENTS).toArray(),null);
        if (ingredients == null) {
            System.exit(0);
        };
        String allergens = (String) JOptionPane.showInputDialog(null,"Do you have any allergens (NA if you don't have any)",appName,JOptionPane.QUESTION_MESSAGE,null,allItems.getAllPreference(Preferences.ALLERGENS).toArray(),null);
        if (allergens == null) {
            System.exit(0);
        };

        int maxPrice = -1;
        while (true) {
            try {
                String maxPriceString = (String) JOptionPane.showInputDialog(null,"What is the max price you would pay",appName,JOptionPane.QUESTION_MESSAGE);
                if (maxPriceString == null) {
                    System.exit(0);
                };
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


    private static AllItems loadData(){
        AllItems allItems = new AllItems();

        List<String> lines = new ArrayList<>();
        Path path = Path.of("pizza_menu_database_v10.csv");
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("error reading file");
            System.exit(0);
        }
        for (int i =1;i<lines.size();i++){
            Map<Preferences,Object> preferences = new HashMap<>();
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
           // System.out.println(line[3]);
            String[] cheeseArray = line[3].replace("[","").replace("]","").split(" ");
            String[] meatArray = line[4].replace("[","").replace("]","").split(" ");
            String[] ingredientsArray = line[5].replace("[","").replace("]","").split(" ");
            String[] sauceArray = line[6].replace("[","").replace("]","").split(" ");
            String[] allergensArray = line[7].replace("[","").replace("]","").split(" ");
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
            //System.out.println(item.dreamItem().getPreference(Preferences.CHEESE));
            //System.out.println(item.dreamItem().getPreference(Preferences.CHEESE));
            allItems.addItem(item);


        }
        return allItems;



    }

    public static void chooseItem(List<Item> matches){
        Map<String, Item> nameMap = new HashMap<>();
        for (Item item: matches){
            nameMap.put(item.Name(),item);
        }
        Map<Item,String> descriptions = new LinkedHashMap<>();
        for(Item item: matches){
            descriptions.put(item, item.toString());
        }
        List<String> names = new ArrayList<>();
        for(Item item: matches){
            names.add(item.Name());
        }

        Item choiceToLook = null;
        while (true) {
            String choiceToLookString = (String) JOptionPane.showInputDialog(null,"Please enter what item you would like to view in more detail ",appName,JOptionPane.QUESTION_MESSAGE,null,names.toArray(),null);
            if (choiceToLookString== null) System.exit(0);
            choiceToLook = nameMap.get(choiceToLookString);
            String buyChoice  = (String) JOptionPane.showInputDialog(null,descriptions.get(choiceToLook)+"\n would you like to buy this item",appName,JOptionPane.QUESTION_MESSAGE,null, new String[]{"Yes", "No"},null);
            if (buyChoice== null) System.exit(0);
            else if (buyChoice.equals("Yes")) {
                JOptionPane.showConfirmDialog(null,"Thank you for Purchasing ");
                Person person = getUserData();
                writeRecord(person,choiceToLook);
                break;
            } else if (buyChoice.equals("No")) {
                continue;
            }


        }

    }
    public static Person getUserData(){

        String email = (String) JOptionPane.showInputDialog(null,"Please enter your email");
        if (email== null) System.exit(0);
        String name = (String) JOptionPane.showInputDialog(null,"Please enter your name");
        if (name== null) System.exit(0);
        int phoneNum;
        while (true) {
            String phoneNumber = (String) JOptionPane.showInputDialog(null, "Please enter your phone number");
            if (phoneNumber == null) System.exit(0);
            try {
                 phoneNum = Integer.parseInt(phoneNumber);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showConfirmDialog(null,"Please enter a Integer");
                continue;
            }
        }
        String address = (String) JOptionPane.showInputDialog(null,"please enter your address for delivery ");


        return new Person(email,name,phoneNum,address);
    }
    public static void writeRecord(Person person, Item item){
        String pathString = person.name().strip()+"Delivery_request.txt";
        Path path = Path.of(pathString);
       try {
           Files.writeString(path, person.name()+" would like to order a "+item.Name()+" at "+ person.address()+"\n email: "+person.email()+"\n Phone Number: "+person.phoneNumber());
       } catch (IOException e){
           System.out.println("error trying to read file");
           System.exit(0);
       }

    }

    public static JPanel jPanel(Set<String> stringSet){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        for (String str: stringSet){
            JCheckBox option = new JCheckBox(str);
            panel.add(option);
        }
        return panel;
    };
}
