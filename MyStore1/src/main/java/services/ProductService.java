package services;

import java.util.List;

import javax.persistence.Persistence;

import application.MyException;
import dao.ProductDao;
import model.Product;

public class ProductService {
	private ProductDao productDao;

	public ProductService() {
		try {
			productDao = new ProductDao(Persistence.createEntityManagerFactory("MyStore1"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void addProduct(Product newProduct) {
		productDao.create(newProduct);
	}

	public void updateProduct(Product updatedProduct) {
		productDao.update(updatedProduct);
	}

	public List<Product> getAllProducts() {
		return productDao.findAll();
	}

	public Product findProduct(String name) throws MyException {
		List<Product> products = productDao.find(name);
		if (products.size() == 0) {
			throw new MyException("Product not found!");
		}
		Product p = products.get(0);
		return p;
	}
	
	public void checkIfListIsEmpty(List<Product> list) throws MyException {
		if (list.isEmpty()) {
			throw new MyException("The Cart is empty.");
		}
	}
}

