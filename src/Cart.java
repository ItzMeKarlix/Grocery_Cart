import java.util.HashMap;

class Cart {
    private HashMap<Item, Double> cart = new HashMap<Item, Double>();

    public void addToCart(Item item, double quantity) {
        if (cart.containsKey(item)) {
            cart.put(item, cart.get(item) + quantity);
        } else {
            cart.put(item, quantity);
        }
    }

    //
    public void removeFromCart(Item item) {
        if (cart.containsKey(item)) cart.remove(item);
    }
    // for clearing cart
    public void clearCart() {
        cart.clear();
    }

    public void subtractItemQuan(Item item, double quantity) {
        if (cart.containsKey(item))
            cart.put(item, cart.get(item) - quantity);
        if (cart.get(item) <= 0)
            cart.remove(item);
    }

    public double getTotalPrice(){
        double totalPrice = 0;

        for(Item item : cart.keySet()) {
            totalPrice += item.getItemPrice() * cart.get(item);
        }
        return totalPrice;
    }

    public double getTotalQuantity(){
        double totalQuantity = 0;
        for(Item item : cart.keySet()){
            totalQuantity += cart.get(item);
        }
        return totalQuantity;
    }


    public HashMap<Item, Double> getCart() {
        return cart;
    }

}