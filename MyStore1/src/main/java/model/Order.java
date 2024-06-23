package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@Table(name = "`ORDER`")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int orderId;

	private String deliveryAddress;

	private String fullName;

	@Temporal(TemporalType.DATE)
	private Date orderDate;

	private String orderedProductList;

	private double orderValue;

	private String phoneNumber;

	private int userId;

	public Order() {
	}

	public Order(int userId, String fullName, String phoneNumber, String deliveryAddress, double orderValue, Date orderDate, String orderedProductList) {
		this.deliveryAddress = deliveryAddress;
		this.fullName = fullName;
		this.orderDate = orderDate;
		this.orderedProductList = orderedProductList;
		this.orderValue = orderValue;
		this.phoneNumber = phoneNumber;
		this.userId = userId;
	}

	
	
	@Override
	public String toString() {
		return "Order [deliveryAddress=" + deliveryAddress + ", fullName=" + fullName + ", orderDate=" + orderDate
				+ ", orderedProductList=" + orderedProductList + ", orderId=" + orderId + ", orderValue=" + orderValue
				+ ", phoneNumber=" + phoneNumber + ", userId=" + userId + "]";
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getDeliveryAddress() {
		return this.deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderedProductList() {
		return this.orderedProductList;
	}

	public void setOrderedProductList(String orderedProductList) {
		this.orderedProductList = orderedProductList;
	}

	public double getOrderValue() {
		return this.orderValue;
	}

	public void setOrderValue(double orderValue) {
		this.orderValue = orderValue;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}