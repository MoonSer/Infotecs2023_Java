package ru.moonser.Menu;
import java.util.function.Function;


public class MenuItem<T> {
    private String menuText;
    private Function<T, Void> callback;


    public MenuItem(String menuText, Function<T, Void> callback) {
        this.menuText = menuText;
        this.callback = callback;
    }

    public void execute(T t) throws Exception {
        this.callback.apply(t);
    }

    
    public String getText() {
        return menuText;
    }
    public void setText(String menuText) {
        this.menuText = menuText;
    }    
    
    public Function<T, Void> getCallback() {
        return callback;
    }
    public void setCallback(Function<T, Void> callback) {
        this.callback = callback;
    }
}
