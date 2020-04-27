package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    public void addCommand(Command cmd){this.commands.put(cmd.getKey(),cmd);}

    private void printMenu(){
        for (Command cmd:commands.values()){
            String line = String.format("%4s : %s",cmd.getKey(),cmd.getDescription());
            System.out.println(line);
        }
    }
    public void show() throws InterruptedException{
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if (com == null) {
                System.out.println("Invalid Option");
                continue;
            }
            com.execute();
        }
    }
}