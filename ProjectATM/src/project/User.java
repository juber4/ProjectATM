package project;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	// The first name of the user.
	private String firstname;
	//The last name of the user.
	private String lastname;
	//The ID number of the user.
	private String uuid;
	//The hash of the user pin number
	private byte pinHash[];
    // The list of accounts for the user.
	private ArrayList<Account> accounts;
	/*
	 * create a new user
	 * firstName
	 * lastName
	 * pin
	 * theBank
	 */
	
	
	
	public User(String firstName, String lastName, String pin, Bank theBank)
	{
		//set user'name
		this.firstname = firstName;
		this.lastname = lastName;
		
		//store the pin's MD5 hash, rather than the original value,for
		//security reasons
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
		
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		// get a new, unique universal ID for the user
		this.uuid = theBank.getNewUserUUID();
		
		//create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		// print log message
		System.out.printf("New user %s, %s, with ID %s created.\n", 
				lastName,firstName, this.uuid);
	}
	
	/*
	 * Add an account for the user
	 */
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}
	/*
	 * Return the uuid
	 */
	public String getUUID() {
		return this.uuid;
	}

     public boolean validatePin(String aPin) {
    	 
    	 try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()),
					this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught NoSuchAlgorithmexception");
		    e.printStackTrace();
		    System.exit(1);
		}
    	 return false;
     }
      
     public String getFirstName() {
    	return this.firstname;
     }
     public void printAccountsSummary() {
    	 System.out.printf("\n\n%s's accounts summary\n", this.firstname);
    	 for (int a = 0; a< this.accounts.size(); a++) {
    		 System.out.printf("  %d) %s\n", a+1, 
    		this.accounts.get(a).getSummaryLine());
    	 }
    	 System.out.println();
     }
     
     /*
      * get the number of accounts of the user
      */
     public int numAccounts() {
    	 return this.accounts.size();
     }
     public  void printAcctTransHistory(int acctIdx) {
    	 this.accounts.get(acctIdx).printTransHistory();
     }
     public double getAcctBalance(int acctIdx) {
    	 return this.accounts.get(acctIdx).getBalance();     
    	 }
     
     public String getAcctUUID(int acctIdx) {
    	 return this.accounts.get(acctIdx).getUUID();
     }
     
     public void addAcctTransaction(int acctIdx, double amount, String memo) {
    	 this.accounts.get(acctIdx).addTransaction(amount, memo);
     }

}

