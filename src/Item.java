class Item{

    private String itemName, itemUnit;
    private double itemPrice, itemQuantity, stock;

    Item(String itemName, double itemPrice, double quan,String unit){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = quan;
        this.itemUnit = unit;
        this.stock = 100;
    }
    public void SubtractStock(double stock){
        this.stock -= stock;
    }

    public void ReturnItem(double amount){
        if (this.stock+ amount>100) this.stock = 100;
        else this.stock += amount;
    };
    public String getItemName(){
        return this.itemName;
    }
    public double getItemPrice(){
        return this.itemPrice;
    }
    public double getItemQuantity(){
        return this.itemQuantity;
    }
    public String getItemUnit(){
        return this.itemUnit;
    }
    public double getItemStock(){
        return this.stock;
    }
}