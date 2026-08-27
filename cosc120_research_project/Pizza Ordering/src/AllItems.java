import java.util.*;

public class AllItems {
    private final List<Item> allItems = new ArrayList<>();

    public void addList(Item item){
        allItems.add(item);
    }

    public List<Item> getList(){
        return new ArrayList<>(allItems);
    }

}
