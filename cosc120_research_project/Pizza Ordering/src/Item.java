/**
 * code created by Judah Deane (Judahdeane2@gmail.com)
 * for TRIMESTER 2 2026 COSC120 Object Oriented Programming
 * @github https://github.com/JudahDeane/COSC120_OOD-A_Project.git
 *
 */
public record Item(String Name, float price, DreamItem dreamItem, int energy ,String description) {

    @Override
    public String toString() {
        return Name + " is a " + dreamItem.getPreference(Preferences.TYPE) + ". Details include :\n" +
                "Price: $" + price + "\n"+
                "Energy (Kilojoules): " + energy + "\n"+
                "Description: " + description + "\n" + dreamItem.getDescription();
    }
}
