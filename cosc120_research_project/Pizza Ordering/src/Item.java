
public record Item(String Name,Type type, float price, DreamItem dreamItem, int energy ,String description) {

    @Override
    public String toString() {
        return Name + " is a " + type + ". Details include :\n" +
                "Price: $" + price + "\n"+
                "Energy (Kilojoules): " + energy + "\n"+
                "Description: " + description + "\n" + dreamItem.getDescription();
    }
}
