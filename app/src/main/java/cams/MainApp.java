/*
 * This is the entry point of the app.
 */
package cams;
import java.util.Scanner;
public class MainApp {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(new MainApp().getGreeting());
        System.out.println("input string:");
        System.out.println(sc.next());
    }
}
