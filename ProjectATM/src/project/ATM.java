 package project;

 
 import java.util.Scanner;
public class ATM {
	public static void main(String[] args) {
		
		//initialization scanner
		Scanner sc = new Scanner(System.in);
		
		//initialization bank
		Bank theBank = new Bank("Bank of India");
		
		//add a user, which also creates a saving account
		
		User aUser = theBank.addUser("Juber", "Ali", "1234");
		
		//add a checking account for our user
		
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		while (true) {
			// stay in the login prompt until successful login
			curUser = ATM.mainMenuPrompt(theBank, sc);
			
			//stay in main menu until user quits
			ATM.printUserMenu(curUser, sc);
		}
	}
	
	public static User mainMenuPrompt(Bank theBank, Scanner sc) {
		
		//initialization
		String userID;
		String pin;
		User authUser;
		
		//prompt the user for user ID/pin combo
		
		do {
			System.out.printf("\n\nwelcome to %s\n\n", theBank.getName());
			System.out.print("Enter user ID:");
			userID = sc.nextLine();
			System.out.printf("Enter pin: ");
			pin = sc.nextLine();
			
			//try to get the user object corresponding to the the ID and pin combo
			authUser = theBank.userLogin(userID, pin);
			if(authUser == null) {
				System.out.println("Incorrect user ID/pin combination." +
			"please try again.");
			}
		} while(authUser == null); //continue looping until successful login
		
		return authUser;
	}
	public static void printUserMenu(User theUser, Scanner sc) {
		// print a summary of the user's account
		theUser.printAccountsSummary();
		
		int choice;
		
		do {
			System.out.printf("Welcome%s, what would you like to do?\n",
			theUser.getFirstName());
			System.out.println(" 1) Show account transaction history");
			System.out.println(" 2) Withdraw ");
			System.out.println(" 3) Deposit");
			System.out.println(" 4) Transfer");
			System.out.println(" 5) Quit");
			System.out.println();
			System.out.println("Enter choice");
			choice = sc.nextInt();
			
			if (choice < 1 || choice > 5) {
				System.out.println("Invalid choice. please choose 1-5");
			}
		} while(choice < 1 || choice >5);
		
		//process the choice
		
		switch (choice) {
		
		case 1:
			ATM.showTransHistory(theUser, sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser, sc);
			break;
		case 3:
			ATM.depositFunds(theUser, sc);
			break;
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
		}
		//redisplay this menu unless the user wants to quit
		
		if(choice !=5) {
			ATM.printUserMenu(theUser, sc);
		}
	}
	public static void showTransHistory(User theUser, Scanner sc) {
		int theAcct;
		
		//get account whose transaction history to look at
		
		do {
			System.out.printf("enter the number(1-%d) of the account" +
		     "whose transactions you want to see: ", 
		      theUser.numAccounts());
			
			theAcct = sc.nextInt()-1;
			if (theAcct <0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. please try agian.");
			}
			
		} while (theAcct <0 || theAcct >= theUser.numAccounts());
		
		// print the transaction history
		theUser.printAcctTransHistory(theAcct);
	}
    
	public static void transferFunds(User theUser, Scanner sc) {
		
		//initi
		 int fromAcct;
		 int toAcct;
		 double amount;
		 double acctBal;
		 
		 // get the account to transfer from
		 do {
			 System.out.printf("Enter the number (1-%d) of the account\n  " + 
		     "to transfer from", theUser.numAccounts());
			 fromAcct = sc.nextInt()-1;
			 if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				 System.out.println("Invalid account. please try again.");
			 }
		 } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		 acctBal = theUser.getAcctBalance(fromAcct);
		 
		 //get the account to transfer to
		 do {
			 System.out.printf("Enter the number (1-%d) of the account\n" + 
		      "to transfer to:", theUser.numAccounts());
			 toAcct = sc.nextInt()-1;
			 if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
				 System.out.println("Invalid account. please try again.");
			 }
		 } while (toAcct < 0 || toAcct >= theUser.numAccounts());
		// get the amount to transfer
		 
		 do {
			 System.out.printf("Enter the amount to transfer (max $%.02f):$ ",
					 acctBal);
			 amount = sc.nextDouble();
			 if(amount < 0) {
				 System.out.println("Amount must be greater than zero.");
			 }else if (amount > acctBal) {
				 System.out.printf("Amount must be grerater than\n" +
			    "balance of $&.02f.\n", acctBal);
			 }
		 }while (amount < 0 || amount > acctBal);
		 
		 //do transfer
		 theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
				 "Transfer to account %s", theUser.getAcctUUID(toAcct)));
		 theUser.addAcctTransaction(toAcct, amount, String.format(
				 "Transfer to account %s", theUser.getAcctUUID(fromAcct)));
	}
	public static void withdrawFunds(User theUser, Scanner sc) {
		
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		//get the account to transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
		"to transfer from ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. please try again.");
			}
		} while (fromAcct < 0|| fromAcct >= theUser.numAccounts());
		  acctBal = theUser.getAcctBalance(fromAcct);
		  
		  do {
			  System.out.printf("Enter the amount to transfer (max $%.02f): $",
					  acctBal);
			  amount = sc.nextDouble();
			  
			  if(amount < 0) {
				  System.out.println("Amount must be greater than zero");
			  }else if (amount > acctBal) {
				  System.out.printf("Amount must be greater than\n" +
			       "balance of $%.02f.\n", acctBal);
			  }
		  }while (amount < 0 || amount > acctBal);
		  
		  sc.nextLine();
		  
		  //get a memo
		  System.out.println("enter a memo: ");
		  memo = sc.nextLine();
		  
		  //do thewithdraw 
		  theUser.addAcctTransaction(fromAcct, -1*amount, memo);
	}
     
	
	//fund deposit
    public static void depositFunds(User theUser, Scanner sc) {
    	int toAcct;
    	double amount;
    	double acctBal;
    	String memo;
    	
    	
    	do {
    		System.out.printf("Enter the number (1-%d) of the accounts\n" +
    	"to transfer from: ", theUser.numAccounts());
    		toAcct = sc.nextInt()-1;
    		if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
    			System.out.println("Invalid account. please try again.");
    		}
    		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
    	      acctBal = theUser.getAcctBalance(toAcct);
    	      
    	      do {
    	    	  System.out.printf("Enter the amount to transfer (max $%.02f): $",
    	    			  acctBal);
    	    	  amount = sc.nextDouble();
    	    	  if(amount < 0) {
    	    		  System.out.println("Amount must be greater than zero.");
    	    	  }else if (amount > acctBal) {
    	    		  System.out.printf("Amount must be freater than\n" +
    	    	        "balance of $%.02f.\n", acctBal);
    	    	  }
    	      }while (amount < 0|| amount > acctBal);
    	      
    	      sc.nextLine();
    	      // get a memo
    	      System.out.println("Enter a memo:");
    	      memo = sc.nextLine();
    	      
    	      //do the withdraw
    	      theUser.addAcctTransaction(toAcct, amount, memo);
    }

}