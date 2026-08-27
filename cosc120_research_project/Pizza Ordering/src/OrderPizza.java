import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class OrderPizza {

    private void loadData(){
        DreamItem dreamItem = null;
        List<String> lines = new ArrayList<>();
        Path path = Path.of("pizza_menu-database_v5.csv");
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e){
            System.out.println("error reading file");
            System.exit(0);
        }
        for (int i =1;i<lines.size();i++){
            String[] line = lines.get(i).split(",");
        }

    }
}
