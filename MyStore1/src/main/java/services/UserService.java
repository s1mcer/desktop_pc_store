package services;

import java.util.List;
import javax.persistence.Persistence;
import dao.UserDao;
import model.User;
import application.MyException;
import utility.CurrentUser;

public class UserService {
	private UserDao userDao;

	public UserService() {
		try {
			userDao = new UserDao(Persistence.createEntityManagerFactory("MyStore1"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void addUser(User newUser) {
		userDao.create(newUser);
	}

	public void updateUser(User updatedUser) {
		userDao.update(updatedUser);
	}

	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	// for login
	public User findUser(String user, String pass) throws MyException {
		List<User> users = userDao.find(user);
		if (users.size() == 0) {
			throw new MyException("User not found!");
		}
		User u = users.get(0);
		CurrentUser.setUserId(u.getUserId());
		if (pass.compareTo(u.getPassword()) != 0) {
			throw new MyException("Password does not match");
		}
		return u;
	}
	
	// for sign up
	public User addUser(String username, String emailAddress, String password) {
		User newUser = new User(username, emailAddress, password);
		userDao.create(newUser);
		return newUser;
	}

	// MyException if one of the fields is null
	public void validateFields(String username, String email, String password, String repeatPassword) throws MyException {
		if (username.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
			throw new MyException("All fields are required.");
		}
	}

	// MyException if passwordField is different from repeatpasswordField
	public void checkPasswordMatch(String password, String repeatPassword) throws MyException {
		if (!password.equals(repeatPassword)) {
			throw new MyException("Those passwords didn't match. Try again.");
		}
	}

	// MyException if user already exists;
	public void checkUserExists(String username, String password) throws MyException {
		List<User> users = userDao.find(username);
		if (users.size() != 0) {
			throw new MyException("User already exists.");
		}
	}
}
