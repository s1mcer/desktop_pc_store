package application;

import model.Product;
import model.Order;

public interface MyListener {
	public void onClickListener(Product product);
	public void onClickListener(Order order);
}
