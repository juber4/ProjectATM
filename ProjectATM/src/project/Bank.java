package project;


import java.util.ArrayList;
import java.util.Random;

public class Bank {
	
	private String name;
	
	private ArrayList<User> user;
	
	private ArrayList<Account> accounts;
	/*
	 * create a new bank object with empty lists of users
	 */
	public Bank(String name) {
		this.name = name;
		this.user = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	/*
	 * Generate a new universally unique ID for a user
	 */
	
	

	public String getNewUserUUID() {
		//initialization
		String uuid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		//counting looping until we get a unique ID
		do {
			
			uuid = "";
			for(int c = 0; c<len; c++) {
				uuid+= ((Integer)rng.nextInt(10)).toString();
			}
			
			//check to make sure it's unique
			
			nonUnique = false;
			for(User u : this.user) {
				if (uuid.compareTo(u.getUUID())== 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while (nonUnique);
		
		return uuid;
	}
	
	/* Generate a new unique id for an account
	 * 
	 */
	public String getNewAccountUUID() {
		String uuid;
		Random rng = new Random();
		int len = 10;
		boolean  nonUnique;
		
		//Counting looping until we get a unique ID
		do {
			uuid ="";
			for(int c = 0; c< len; c++) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			//check to make sure it's unique
			nonUnique = false;
			for (Account a : this.accounts) {
				if(uuid.compareTo(a.getUUID())== 0) {
					nonUnique = true;
					break;
				}
			}
		} while (nonUnique);
		
		  return uuid;
		
		
	}
	
	
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}
	
	public User addUser(String firstName, String lastname, String pin) {
		
		//create  a new user object and add it to our list
		User newUser = new User(firstName, lastname, pin, this); // (this) is object of bank
		this.user.add(newUser);
		
		//create a saving account for the user
		Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		return newUser;
	
	}
	
	public User userLogin(String userID, String pin) {
		//search through list of users
		for(User u : this.user) {
			
			//check user id is correct
			if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin))
			{
				return u;
			}
		}
		//if we haven't found the user or have an incorrect pin
		return null;
	}
	public  String getName() {
		return this.name;
	}
	

}
