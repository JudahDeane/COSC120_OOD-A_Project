
public record Item(String Name,Type type, float price, DreamItem dreamItem) {

    @Override
    public String toString() {
        return Name + " is a " + type + ". Details include :\n" +
                "Price: $" + price + "\n" + dreamItem.getDescription();
    }
}
