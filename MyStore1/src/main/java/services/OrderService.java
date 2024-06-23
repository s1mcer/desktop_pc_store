package services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Persistence;

import application.MyException;
import dao.OrderDao;
import model.Order;

public class OrderService {
	private OrderDao orderDao;

	public OrderService() {
		try {
			orderDao = new OrderDao(Persistence.createEntityManagerFactory("MyStore1"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void addOrder(Order newOrder) {
		orderDao.create(newOrder);
	}

	public void updateOrder(Order updatedOrder) {
		orderDao.update(updatedOrder);
	}

	public List<Order> getAllOrders() {
		return orderDao.findAll();
	}

	public Order addOrder(int userId, String fullName, String phoneNumber, String deliveryAddress, double orderValue,
			Date orderDate, String list) {
		Order newOrder = new Order(userId, fullName, phoneNumber, deliveryAddress, orderValue, orderDate, list);
		orderDao.create(newOrder);
		return newOrder;
	}

	public void validateFields(String name, String phone, String address) throws MyException {
		if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
			throw new MyException("All fields are required.");
		}
	}
	
	public List<Order> getAllOrdersFromTheSameUser(int userId) {
		List<Order> allOrders = orderDao.findAll();
		return allOrders.stream()
			.filter(order -> order.getUserId() == userId)
			.collect(Collectors.toList());
	}
}
