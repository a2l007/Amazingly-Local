package com.iu.amazelocal.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PasswordAuth {

		Map<String, String> DB = new HashMap<String, String>();
		public static final String SALT = "my-salt-text";

		public void tes() {
			PasswordAuth demo = new PasswordAuth();

			// login should succeed.
			
		}

		public String signup(String password) {
			String saltedPassword = SALT + password;
			String hashedPassword = generateHash(saltedPassword);
			System.out.println("Signup"+password+"-> "+hashedPassword);
			return hashedPassword;
		}

		public Boolean login(String userName, String password) {
			Boolean isAuthenticated = false;
			UserCrud user=new UserCrud();
			// remember to use the same SALT value use used while storing password
			// for the first time.
			String saltedPassword = SALT + password;
			String hashedPassword = generateHash(saltedPassword);
			System.out.println("username is "+userName);
			String storedPasswordHash = user.fetchPasswordfromEmail(userName);
			System.out.println("Stored password is"+storedPasswordHash);

			if(hashedPassword.equals(storedPasswordHash)){
				isAuthenticated = true;
			}else{
				isAuthenticated = false;
			}
			return isAuthenticated;
		}

		public static String generateHash(String input) {
			StringBuilder hash = new StringBuilder();

			try {
				MessageDigest sha = MessageDigest.getInstance("SHA-1");
				byte[] hashedBytes = sha.digest(input.getBytes());
				char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
						'a', 'b', 'c', 'd', 'e', 'f' };
				for (int idx = 0; idx < hashedBytes.length; ++idx) {
					byte b = hashedBytes[idx];
					hash.append(digits[(b & 0xf0) >> 4]);
					hash.append(digits[b & 0x0f]);
				}
			} catch (NoSuchAlgorithmException e) {
				// handle error here.
			}

			return hash.toString();
		}
		public String fetchSecurityQuestion(String email){
			UserCrud uc=new UserCrud();
			String secQuestion=uc.fetchQuestionFromEmail(email);
			return secQuestion;
		}
		public Boolean validateAnswer(String email, String sAnswer){
			UserCrud uc=new UserCrud();
			String secAnswer=uc.fetchAnswerFromEmail(email);
			System.out.println("Sec answer is"+secAnswer);
			if(secAnswer.equals(sAnswer)){
				return true;
			}
			else{
				return false;
			}
		}
	}