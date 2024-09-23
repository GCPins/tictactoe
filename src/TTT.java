import org.apache.commons.text.WordUtils;
import java.util.*;

public class TTT {

    static int WRAP_LEN = 80;

    public static void main(String[] args) {

        Game g = new Game();

        Scanner s = new Scanner(System.in);
        while (true) { // game loop
            g.run(args);
            System.out.print("Would you like to play again (yes/y/no/n)? ");
            if (s.nextLine().toLowerCase().contains("n")) break;
            g.reset();
        }
    }

    //Clears Screen in java
    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
