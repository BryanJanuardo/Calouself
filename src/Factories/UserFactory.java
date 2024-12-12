package Factories;

import Models.UserModel;

public class UserFactory {

	public UserFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static UserModel createUser(String User_id, String Username, String Password, String Phone_number, String Address, String Role) {
		return new UserModel(User_id, Username, Password, Phone_number, Address, Role);
	}

}
