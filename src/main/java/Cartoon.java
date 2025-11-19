import java.util.List;

public class Cartoon {

    public static void example(){
        double fighter = Math.floor(Math.random() * 10);
        double fighterTwo = Math.floor(Math.random() * 15);
        System.out.print(fighter + " vs " + fighterTwo);
    }
    public static void main(String[] args) {
        Cartoon.example();
    }
}
