package junit.tutorial;

public class UserForm {
	private final String userName;
	private final String password;
	
	public UserForm(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public boolean isValid() {
		return !userName.equals("") && !password.equals(""); 
	}
}