package ru.moonser.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MenuController<T> {
    private T executor;
    private List<MenuItem<T>> menuItems = new ArrayList<>();

    public MenuController(T executor) {
        this.executor = executor;
    }

    public void print() {
        System.out.println("Menu:");
        for (int i = 0; i < this.menuItems.size(); ++i) {
            MenuItem<T> menuItem = this.menuItems.get(i);
            System.out.printf("%d) %s\n", i+1, menuItem.getText());
        }
        System.out.print("Choose: ");
    }

    public void execute(int i) throws Exception {
        this.menuItems.get(i).execute(this.executor);
    }

    public void addAction(String text, Function<T, Void> callback) {
        this.menuItems.add(new MenuItem<T>(text, callback));
    }

    public int size() {
        return this.menuItems.size();
    }

    public int getUserInput(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        try {            
            int index = Integer.parseInt(s);
            if (index < 1 || index > this.size())
                throw new Exception();
                                
            return index-1;        
        }catch(Exception e) {
            System.out.println("Error!" + e + "Try one more...");
            return -1;
        }
    }
    
}
