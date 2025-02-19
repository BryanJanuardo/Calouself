package Controllers;

import Models.UserModel;
import Utils.Response;

public class UserController {

	private static Boolean PasswordValidation(String words) {
		char[] uniqueSymbols = {'!', '@', '#', '$', '%', '^', '&', '*'};
		for (char c : uniqueSymbols) {
			for(int i = 0; i < words.length(); i++) {
				if(words.charAt(i) == c) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static Boolean PhoneNumberValidation(String words) {
		if(words.charAt(0) != '+' &&
				words.charAt(1) != '6' &&
				words.charAt(2) != '2') {
			return false;
		}
		
		return true;
	}
	
	public static Response<UserModel> Login(String Username, String Password) {
		Response<UserModel> res = new Response<UserModel>();
		
		if(Username.isEmpty()) {
			res.setMessages("Username cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Password.isEmpty()) {
			res.setMessages("Password cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return UserModel.Login(Username, Password);
	}
	
	public static Response<UserModel> Register(String Username, String Password, String Phone_Number, String Address, String Role) {
		Response<UserModel> res = new Response<UserModel>();
		
		if(Username.isEmpty()) {
			res.setMessages("Username cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Password.isEmpty()) {
			res.setMessages("Password cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Phone_Number.isEmpty()) {
			res.setMessages("Phone Number cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Address.isEmpty()) {
			res.setMessages("Address cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Role.isEmpty()) {
			res.setMessages("Role cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Username.length() < 3) {
			res.setMessages("Username must at least be 3 character long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Password.length() < 8) {
			res.setMessages("Password must at least be 8 character long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Phone_Number.length() != 12) {
			res.setMessages("Phone Number must be 10 numbers long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(UserModel.CheckAccountValidation(Username, Password, Phone_Number, Address).getIsSuccess()) {
			res.setMessages("Username must be unique!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(!PasswordValidation(Password)) {
			res.setMessages("Password must include special characters (!, @, #, $, %, ^, &, *)!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(!PhoneNumberValidation(Phone_Number)) {
			res.setMessages("Phone Number must at least contains a +62!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return UserModel.Register(Username, Password, Phone_Number, Address, Role);
	}
	
	public static Response<UserModel> CheckAccountValidation(String Username, String Password, String Phone_Number, String Address) {
		
		return null;
	}
	
	public UserController() {
		// TODO Auto-generated constructor stub
	}

}
