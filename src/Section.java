import java.util.ArrayList;

class Section {
    private final String sectionName;
    private ArrayList<Item> items = new ArrayList<Item>();

    public Section(String sectionName, ArrayList<Item> arrayList) {
        this.sectionName = sectionName;
        this.items = arrayList;
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }
    public String getName(){
        return this.sectionName;
    };
}
