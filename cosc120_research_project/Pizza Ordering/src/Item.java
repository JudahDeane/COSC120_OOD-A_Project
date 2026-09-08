
public record Item(String Name, float price, DreamItem dreamItem, int energy ,String description) {

    @Override
    public String toString() {
        return Name + " is a " + dreamItem.getPreference(Preferences.TYPE) + ". Details include :\n" +
                "Price: $" + price + "\n"+
                "Energy (Kilojoules): " + energy + "\n"+
                "Description: " + description + "\n" + dreamItem.getDescription();
    }
}
