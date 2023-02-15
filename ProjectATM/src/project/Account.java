package project;

import java.util.ArrayList;

public class Account {
	
	//The name of the account.
	private String name;
	//The current balance of the account.
	private double balance;
	//The account ID number.
	
	private String uuid;
	//The user object that owns this account.
	
	private User holder;
	//The list of transactions for this account.
	
	private ArrayList<Transaction> transaction;
	/*
	 * Create a new Account
	 * name
	 * holder
	 * theBank
	 */
	
	public Account(String name, User holder, Bank theBank) {
		//set the account name and holder
		this.name = name;
		this.holder = holder;
		
		//get new account uuid
		this.uuid = theBank.getNewAccountUUID();
		
		//init transaction
		this.transaction = new ArrayList<Transaction>();
		
		 	}
	
	/*
	 * get the account ID
	 * return uuid
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/*
	 * get summary line for the account
	 */
	public String getSummaryLine() {
		// get the account's balance
		double balance = this.getBalance();
		
		// format the summary line, depending on the whether the balance is
	    if(balance>= 0) {
	    	return String.format("%s : $%.02f : %s", this.uuid, balance,
	    			this.name);
	    } else {
	    	return String.format("%s : $(%.02f) : %s", this.uuid, balance,
	    			this.name);
	    	
	    }
	
	}
	public double getBalance() {
		double balance = 0;
		for (Transaction t : this.transaction) {
			balance += t.getAmount();
		}
		return balance;
	}
	/*
	 * print the transaction history of the account
	 */
	public void printTransHistory() {
		System.out.printf("\nTransaction history for account %\n",
				this.uuid);
		for(int t = this.transaction.size()-1; t>=0; t--) {
			System.out.printf(this.transaction.get(t).getSummaryLine());
		}
		System.out.println();
	}
	
	public void addTransaction(double amount, String memo) {
		
		//new transaction object
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transaction.add(newTrans);
	}
	

}
