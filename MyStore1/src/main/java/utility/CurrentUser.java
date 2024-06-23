package utility;

public class CurrentUser {
	public static int userId;
	
	public static void setUserId(int userId) {
		CurrentUser.userId = userId;
	}
	
	public static int getUserid() {
		return CurrentUser.userId;
	}
}
