package utility;

import java.util.ArrayList;
import java.util.List;
import model.Product;

public class Cart {
    private static List<Product> productsInCart = new ArrayList<>();

    public static void addProduct(Product product) {
        productsInCart.add(product);
    }

    public static void removeProduct(Product product) {
        productsInCart.remove(product);
    }
    
    public static List<Product> getProductsInCart() {
        return new ArrayList<>(productsInCart);
    }

    public static void clearCart() {
        productsInCart.clear();
    }
}
