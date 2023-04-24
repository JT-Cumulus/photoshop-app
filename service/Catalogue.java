package service;

import java.util.ArrayList;
import java.util.List;

// Catalogue for collecting all the items in a list
public class Catalogue {
    
    private List<Item> items;

    public Catalogue(){
        this.items = new ArrayList<>();
    }

    public Item getItem(int index) {
        return new Item(items.get(index));
    }

    public void setItem(int index, Item item) {
        this.items.set(index, new Item(item));
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public int getLength(){
        return this.items.size();
    }

    public String toString() {
        String temp = "";
        for (Item item : items) {
            temp += item.toString() + "\n";
        }
        return temp;
    }
    
}
