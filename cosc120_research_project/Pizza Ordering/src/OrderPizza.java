import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderPizza {
    private final static String appName = "Judah's Pizza Palace";


    /**
     * a method that:
     * loads all data from a database in allItems
     * gets the users preference
     * gets all the matches in allItems
     * the call chooseItem on the matches
     */
    static void main(){
        AllItems allItems = loadData();
        while (true) {
            DreamItem dreamItem = getUserPreference(allItems);
            List<Item> matches = allItems.getMatches(dreamItem);
            if (matches.isEmpty()) {
                JOptionPane.showConfirmDialog(null, "Sorry we didn't have any products that fit your criteria");
                System.exit(0);
            }
            boolean chooseItem = chooseItem(matches);
            if (chooseItem) {
                continue;
            }
            break;
        }

    }

    /**
     * gets users preference by asking questions with JOptionPane then storing that data in a DreamItem
     * @param allItems all the items to get all the values of preferences
     * @return returns a DreamItem with the preferences
     */
    private static DreamItem getUserPreference(AllItems allItems){
        Type itemType = (Type) JOptionPane.showInputDialog(null,"Please enter what item you would like to buy",appName,JOptionPane.QUESTION_MESSAGE,null,Type.values(),null);
        if (itemType == null) {
            System.exit(0);
        }
        Set<Preferences> itemValues = allItems.getAllPreferences(itemType);
        List<Object> cheese = new ArrayList<>();
        if (itemValues.contains(Preferences.CHEESE)) {
            cheese = checkBox(allItems.getAllPreferenceValues(Preferences.CHEESE, itemType), "cheese");
        }
        List<Object> meat = new ArrayList<>();
        if (itemValues.contains(Preferences.MEAT)) {
            meat = checkBox(allItems.getAllPreferenceValues(Preferences.MEAT, itemType), "meat");
        }
        List<Object> ingredients = new ArrayList<>();
        if (itemValues.contains(Preferences.INGREDIENTS)) {
            ingredients = checkBox(allItems.getAllPreferenceValues(Preferences.INGREDIENTS, itemType), "ingredients");
        }
        List<Object> sauce = new ArrayList<>();
        if (itemValues.contains(Preferences.SAUCE)) {
            sauce = checkBox(allItems.getAllPreferenceValues(Preferences.SAUCE, itemType), "sauce");
        }


        int maxPrice;
        while (true) {
            try {
                String maxPriceString = JOptionPane.showInputDialog(null,"What is the max price you would pay",appName,JOptionPane.QUESTION_MESSAGE);
                if (maxPriceString == null) {
                    System.exit(0);
                }
                maxPrice = Integer.parseInt(maxPriceString);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "please enter a valid integer");
                continue;
            }
            break;
        }
        boolean loop = true;
        boolean vegan = false;
        do{
            String veganString = (String) JOptionPane.showInputDialog(null,"Are you vegan",appName,JOptionPane.QUESTION_MESSAGE,null, new String[]{"Yes", "No"},"No");
            if (veganString == null) {
                System.exit(0);
            }


                switch (veganString) {
                    case "yes" -> {
                        vegan = true;
                        loop = false;
                    }
                    case "no" ->loop = false;
                    default -> System.out.println("Please enter yes or no");

                }
            } while (loop);

        loop = true;
        boolean dairy = false;
        do {
            String dairyString = (String) JOptionPane.showInputDialog(null, "Do you have a dairy allergen", appName, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, "No");
            if (dairyString == null) {
                System.exit(0);
            }

            switch (dairyString) {
                case "yes" -> {
                    dairy = true;
                    loop = false;
                }
                case "no" -> {
                    loop = false;
                }
                default -> System.out.println("Please enter yes or no");

            }
        }while (loop);
        loop = true;
        boolean soy = false;
        do{
            String soyString = (String) JOptionPane.showInputDialog(null, "Do you have a soy allergen", appName, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, "No");
            if (soyString == null) {
                System.exit(0);
            }


            switch (soyString) {
                case "yes" -> {
                    soy = true;
                    loop = false;
                }
                case "no" -> {
                    loop = false;
                }
                default -> System.out.println("Please enter yes or no") ;

            }
        }while (loop);
        loop = true;
        boolean gluten = false;
        do {
            String glutenString = (String) JOptionPane.showInputDialog(null, "Do you have a gluten allergen", appName, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, "No");
            if (glutenString == null) {
                System.exit(0);
            }


            switch (glutenString) {
                case "yes" -> {
                    gluten = true;
                    loop = false;
                }
                case "no" -> {
                    loop = false;
                }
                default -> System.out.println("Please enter yes or no");

            }
        }while (loop);

        Map<Preferences,List<Object>> map = new HashMap<>();
        List<Object> typeList = new ArrayList<>();
        typeList.add(itemType);
        map.put(Preferences.TYPE,typeList);



        map.put(Preferences.MEAT,meat);

        map.put(Preferences.CHEESE,cheese);

        map.put(Preferences.INGREDIENTS,ingredients);

        map.put(Preferences.SAUCE,sauce);

        List<Object> veganList = new ArrayList<>();
        veganList.add(vegan);
        map.put(Preferences.VEGAN,veganList);
        List<Object> dairyList = new ArrayList<>();
        dairyList.add(dairy);
        map.put(Preferences.DAIRY,dairyList);
        List<Object> soyList = new ArrayList<>();
        soyList.add(soy);
        map.put(Preferences.SOY,soyList);
        List<Object> glutenList = new ArrayList<>();
        glutenList.add(gluten);
        map.put(Preferences.GLUTEN,glutenList);





        return new DreamItem(map,maxPrice);
    }

    /**
     * loads and parses all the data safely from a database then stores it in a AllItems object
     * @return an AllItems object with all the data from the database
     */
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


            List<String> items = new ArrayList<>();
            // this regex pattern and logic was created with the help of google gemini
            Matcher matcher = Pattern.compile("\\[(.*?)]|([^,]+)").matcher(lines.get(i));

            while (matcher.find()) {
                if (matcher.group(1) != null) {
                    // Bracketed group content (without brackets)
                    items.add(matcher.group(1));
                } else if (matcher.group(2) != null) {
                    // Non-bracketed, standard comma-separated item
                    items.add(matcher.group(2).trim());
                }
            }
            // this code here only works if the lines end with a [] like description which is not ideal but it works for my data set


            String name = items.get(0).replace(" ","_");
            Type type;

            type = Type.valueOf(items.get(1).toUpperCase());

            float price = 0;
            try {
                price = Float.parseFloat(items.get(2).replace("$",""));
            }catch(NumberFormatException e ){
                System.out.println("error reading price");
                System.exit(0);
            }
            // System.out.println(line[3]);
            String[] cheeseArray = items.get(3).split(",");
            String[] meatArray = items.get(4).split(",");
            String[] ingredientsArray = items.get(5).split(",");
            String[] sauceArray = items.get(6).split(",");
            boolean dairy_allergy = false;
            switch (items.get(7)){
                case "yes" -> dairy_allergy = true;
                case "no" -> {}
                default ->  System.out.println("error trying to read line:"+i +" dairy allergy" );

            }
            boolean gluten_allergy = false;
            switch (items.get(8)){
                case "yes" -> gluten_allergy = true;
                case "no" -> {}
                default ->  System.out.println("error trying to read line:"+i +" dairy allergy" );

            }
            boolean soy_allergy = false;
            switch (items.get(9)){
                case "yes" -> soy_allergy = true;
                case "no" -> {}
                default ->  System.out.println("error trying to read line:"+i +" dairy allergy" );

            }
            boolean vegan = false;
            switch (items.get(10)){
                case "yes" -> vegan = true;
                case "no" -> {}
                default ->  System.out.println("error trying to read line:"+i +" dairy allergy" );
            }
            int energy = -1;
            try {
                energy = Integer.parseInt(items.get(11));
            } catch (NumberFormatException e){
                System.out.println("error trying to read energy at line "+i);
            }

            String description = items.get(12);

            Set<String> cheese = new TreeSet<>(List.of(cheeseArray));
            Set<String> meat = new TreeSet<>(List.of(meatArray));
            Set<String> ingredients = new TreeSet<>(List.of(ingredientsArray));
            Set<String> sauce = new TreeSet<>(List.of(sauceArray));



            preferences.put(Preferences.CHEESE,cheese);
            preferences.put(Preferences.TYPE,type);
            preferences.put(Preferences.MEAT,meat);
            preferences.put(Preferences.INGREDIENTS,ingredients);
            preferences.put(Preferences.SAUCE,sauce);
            preferences.put(Preferences.DAIRY,dairy_allergy);
            preferences.put(Preferences.SOY,soy_allergy);
            preferences.put(Preferences.GLUTEN,gluten_allergy);
            preferences.put(Preferences.VEGAN,vegan);

            DreamItem dreamItem = new DreamItem(preferences);

            Item item = new Item(name,price,dreamItem,energy,description);

            allItems.addItem(item);


        }
        return allItems;



    }

    /**
     * looks at all the items given then askes which they which item they would like to select.
     * writes that selected item to a record file
     * @param matches a list of matches to choose from
     */
    public static Boolean chooseItem(List<Item> matches){
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

        Item choiceToLook;
        List<Item> items = new ArrayList<>();
        while (true) {

            String choiceToLookString = (String) JOptionPane.showInputDialog(null,"Please enter what item you would like to view in more detail ",appName,JOptionPane.QUESTION_MESSAGE,null,names.toArray(),null);
            if (choiceToLookString== null) System.exit(0);
            choiceToLook = nameMap.get(choiceToLookString);
            String buyChoice  = (String) JOptionPane.showInputDialog(null,descriptions.get(choiceToLook)+"\n would you like to buy this item",appName,JOptionPane.QUESTION_MESSAGE,null, new String[]{"Yes", "No"},null);
            if (buyChoice== null) System.exit(0);
            else if (buyChoice.equals("Yes")) {
                JOptionPane.showMessageDialog(null,"Thank you for Purchasing ");
                items.add(choiceToLook);
                String buyAnother = (String) JOptionPane.showInputDialog(null,"would you like to buy another item",appName,JOptionPane.QUESTION_MESSAGE,null,new String[]{"Yes","No"},null);
                if(buyAnother.isEmpty()) System.exit(0);
                if(buyAnother.equals("Yes")) return true;
                break;
            }



        }
        Person person = getUserData();
        writeRecord(person,items);
        return false;

    }

    /**
     * gets the user contact and delivery information
     * @return a person record with the information
     */
    public static Person getUserData(){

        String email = JOptionPane.showInputDialog(null,"Please enter your email");
        if (email== null) System.exit(0);
        String name =  JOptionPane.showInputDialog(null,"Please enter your name");
        if (name== null) System.exit(0);
        int phoneNum;
        while (true) {
            String phoneNumber =  JOptionPane.showInputDialog(null, "Please enter your phone number");
            if (phoneNumber == null) System.exit(0);
            try {
                phoneNum = Integer.parseInt(phoneNumber);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showConfirmDialog(null,"Please enter a Integer");
            }
        }
        String address =  JOptionPane.showInputDialog(null,"please enter your address for delivery ");


        return new Person(email,name,phoneNum,address);
    }

    /**
     * writes a record of and item and the person ording that item to a file
     * @param person the person ordering the item
     * @param items the item being ordered
     */
    public static void writeRecord(Person person, List<Item> items){
        String pathString = person.name().strip()+"Delivery_request.txt";
        Path path = Path.of(pathString);
        StringBuilder write = new StringBuilder(person.name() + " with details" + "\n Address:" + person.address() + "\n email: " + person.email() + "\n Phone Number: " + person.phoneNumber()+"\n Would like to order the following items:");
        for (Item item:items){
            write.append(item.description()).append("\n");
        }
        try {
            Files.writeString(path, write);

        } catch (IOException e){
            System.out.println("error trying to read file");
            System.exit(0);
        }

    }

    /**
     * this method was coded with help from Google Gemini
     *
     * creates a JOption pane with check boxes with the set of strings provided
     * @param set the set of strings to use as checkboxes
     * @param item the name of the type of thing to be selecting
     * @return a list of the selected checkboxes
     */
    public static List<Object> checkBox(Set<String> set, String item) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(new JLabel("Select your preferred " + item + ":"));

        List<JCheckBox> checkBoxes = new ArrayList<>();

        for (String str : set) {
            JCheckBox checkbox = new JCheckBox(str);

            if (str.equals("NA")){
                checkbox.setSelected(true);
            }
            checkBoxes.add(checkbox);
            panel.add(checkbox);
        }

        int option = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Preferences",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null
        );
        if (option == JOptionPane.CLOSED_OPTION || option == JOptionPane.CANCEL_OPTION) {
            System.exit(0);
        }
        List<Object> selected = new ArrayList<>();
        if (option == JOptionPane.OK_OPTION) {
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    selected.add(checkBox.getText());
                }
            }
        }
        return selected;
    }


}
