/**
 * code created by Judah Deane (Judahdeane2@gmail.com)
 * for TRIMESTER 2 2026 COSC120 Object Oriented Programming
 * @github https://github.com/JudahDeane/COSC120_OOD-A_Project.git
 *
 */

/**
 * this is the record of a real item contaning a dreamitem object for the other information to be searched by
 * @param Name the name of the product
 * @param price the price of the product
 * @param dreamItem a DreamItem contain containing a map of differing preferences to search by
 * @param energy the KiloJoule amount of the food
 * @param description a description of item
 */
public record Item(String Name, float price, DreamItem dreamItem, int energy ,String description) {

    @Override
    public String toString() {
        return Name + " is a " + dreamItem.getPreference(Preferences.TYPE).getFirst() + ". Details include :\n" +
                "Price: $" + price + "\n"+
                "Energy (Kilojoules): " + energy + "\n"+
                "Description: " + description + "\n" + dreamItem.getDescription();
    }
}
