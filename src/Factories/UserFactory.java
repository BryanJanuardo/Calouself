package Factories;

import Models.UserModel;

public class UserFactory {

	public UserFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static UserModel createUser() {
		return new UserModel();
	}
	
	public static UserModel createUser(String user_id, String username, String password, String phone_number, String address, String role) {
		return new UserModel(user_id, username, password, phone_number, address, role);
	}

}
