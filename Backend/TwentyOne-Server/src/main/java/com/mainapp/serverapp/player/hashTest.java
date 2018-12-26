package com.mainapp.serverapp.player;

public class hashTest {

	
	public static void main(String args[]) {
		PasswordHashes pswh = new PasswordHashes();
		
		String password = "password";
		System.out.println(password);
		String hashPassword = pswh.passHash(password);
		System.out.println(hashPassword);
		System.out.println(pswh.passCheck("password", hashPassword));
		System.out.println(pswh.passCheck("proj309password", hashPassword));
	}
}
