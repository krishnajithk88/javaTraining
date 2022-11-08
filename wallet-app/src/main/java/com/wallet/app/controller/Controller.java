package com.wallet.app.controller;

import java.util.Scanner;

import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletException;
import com.wallet.app.service.WalletService;
import com.wallet.app.service.WalletServiceImpl;

public class Controller {

	public static void main(String[] args) {
		
		WalletService walletService = new WalletServiceImpl();
		
		System.out.println("Welcome to Wallet App");
		Scanner scanner = new Scanner(System.in);
		Boolean quit = false;
		do {
		System.out.println("Please select the operation you want to perform,enter the number represenitng operation");
		System.out.println(" 1.Registration\n 2.Login\n 3.Balance Enquiry\n 4.Deposit\n 5.Transfer Amount\n 6.Cash Withdrawal\n 7.Unregistration\n");
		int purpose = scanner.nextInt();
		switch(purpose){
		case 1:
			// registering
			try {
				System.out.println("Enter ID:");
				int id = scanner.nextInt();
				System.out.println("Enter Name:");
				String name = scanner.next();
				System.out.println("Enter Opening Amount:");
				double amount = scanner.nextDouble();
				System.out.println("Enter Password:");
				String password = scanner.next();
				Wallet user = walletService.registerWallet(new Wallet(id,name,amount,password));
				System.out.println("Registered successfully, wallet details:"+user);
				
			}
			catch(WalletException e){
				System.out.println(e.getMessage());
			}
			break;
		
		case 2:
			// login
			try {
				System.out.println("Enter ID:");
				int id = scanner.nextInt();
				System.out.println("Enter Password:");
				String password = scanner.next();
				Boolean userLogin = walletService.login(id, password);
				if(userLogin) {
					System.out.println("logged in successfully");
				}
			}
			catch(WalletException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 3:
			// balance enquiry
			try {
				System.out.println("Enter ID:");
				int id = scanner.nextInt();
				Double balance = walletService.showWalletBalance(id);
				System.out.println("Balance:"+balance);
			}catch(WalletException e){
				System.out.println(e.getMessage());
			}
			break;
		case 4:
			// deposit
			try {
				System.out.println("Enter ID:");
				int id = scanner.nextInt();
				System.out.println("Enter Amount:");
				double amount = scanner.nextDouble();
				Double deposit = walletService.addFundsToWallet(id, amount);
				System.out.println("Current Balance:"+deposit);
				
			}catch(WalletException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 5:
			// transfer fund
			try {
				System.out.println("Enter your ID:");
				int id = scanner.nextInt();
				System.out.println("Enter receiver's ID:");
				int receiverId = scanner.nextInt();
				System.out.println("Enter the amount you want to transfer:");
				double amount = scanner.nextDouble();
				Boolean transfer = walletService.fundTransfer(id, receiverId, amount);
				//System.out.println(transfer);
				if(transfer) {
					System.out.println("Transaction was successfull");
				}
			}catch(WalletException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 6:
			// withdraw
			try {
				System.out.println("Enter ID:");
				int id = scanner.nextInt();
				System.out.println("Enter the amount:");
				double amount = scanner.nextDouble();
				Double withdraw =  walletService.withdrawFunds(id, amount);
				System.out.println("Current Balance:"+withdraw);
				
			}catch(WalletException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 7:
			// unregister
			try {
				System.out.println("Enter ID:");
				int id = scanner.nextInt();
				System.out.println("Enter Password:");
				String password = scanner.next();
				Wallet unreg = walletService.unRegisterWallet(id, password);
				System.out.println("unregistration was successfull, Wallet details:"+unreg);
				
			}catch(WalletException e) {
				System.out.println(e.getMessage());
			}
			break;
		default:
			System.out.println("You have provided an invalid input");
		}
		System.out.println("Do you want to quit the application:");
		System.out.println(" 1.Yes\n 2.No\n");
		int numberToQuit = scanner.nextInt();
		if (numberToQuit == 1) {
			quit = true;
		}
		}while(!quit);
	}
}

