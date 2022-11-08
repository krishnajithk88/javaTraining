package com.wallet.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletException;
import com.wallet.app.service.WalletService;
import com.wallet.app.service.WalletServiceImpl;

public class WalletServiceTest {
	
	WalletService walletService = new WalletServiceImpl();
	
	@BeforeAll
	public static void setupTestData() {
		System.out.println("Create all test data");
	}
	
	@Test
	public void registerWalletTest() {
		
		
		try {
			Wallet wallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			assertNotNull(wallet);
			assertEquals(100, wallet.getId());
			
		} catch (WalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void showWalletBalanceTest() {
		
		try {
			Wallet wallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			
			assertEquals(1000.0, walletService.showWalletBalance(100));
		} catch (WalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void showWalletBalanceExceptionTest() {
		try {
			Wallet wallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			assertThrows(WalletException.class,()-> walletService.showWalletBalance(200));
		} catch (WalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Test
	public void LoginTest() {
		
		try {
			Wallet wallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			Boolean isWalletPresent = walletService.login(100, "123");
			assertEquals(wallet.getId(),100);
			assertEquals(wallet.getPassword(),"123");
		}catch(WalletException e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void loginExceptionTest() {
		try {
			Wallet wallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			Boolean isWalletPresent = walletService.login(100, "123");
			assertThrows(WalletException.class,()-> walletService.login(200,"123"));
			assertThrows(WalletException.class,()-> walletService.login(100,"1234"));
			assertThrows(WalletException.class,()-> walletService.login(200,"1232"));
		} catch (WalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}
	@Test
	public void addFundsToWalletTest() {
		try {
			Wallet wallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			Double balance = wallet.getBalance();
			Double newBalance = walletService.addFundsToWallet(100, 500.0);
			assertEquals(500.00+balance,newBalance);
		} catch (WalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void addFundsToWalletExceptionTest() {
		try {
			Wallet wallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			assertThrows(WalletException.class,()-> walletService.addFundsToWallet(200,500.00));
		} catch (WalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Test
	public void withdrawFundsTest() {
		try {
			Wallet wallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			Double balance = wallet.getBalance();
			Double newBalance = walletService.withdrawFunds(100, 500.0);
			assertEquals(500.0,balance-newBalance);
		} catch (WalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void withdrawFundsExceptionTest() {
		try {
			Wallet wallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			assertThrows(WalletException.class,()-> walletService.withdrawFunds(200,500.00));
			assertThrows(WalletException.class,()-> walletService.withdrawFunds(100,5000.00));
		} catch (WalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Test
	public void unregisterWalletTest() {
		try {
			Wallet wallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			Wallet unregWallet = walletService.unRegisterWallet(100, "123");
			assertEquals(100,unregWallet.getId());
			assertEquals("123",unregWallet.getPassword());
		} catch (WalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void unregisterExceptionTest() {
		try {
			Wallet wallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			assertThrows(WalletException.class,()-> walletService.unRegisterWallet(100,"1234"));
			//assertThrows(WalletException.class,()-> walletService.unRegisterWallet(200,"123"));
		} catch (WalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Test
	public void fundTransferTest() {
		try {
			Wallet fromWallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			Double fromWalletBalance = fromWallet.getBalance();
			assertEquals(100,fromWallet.getId());
			//System.out.println(fromWalletBalance);
			Wallet toWallet =walletService.registerWallet(new Wallet(101, "test name2", 2000.0, "456"));
			Double toWalletBalance = toWallet.getBalance();
			assertEquals(101,toWallet.getId());
			//System.out.println(toWalletBalance);
			Boolean isFundTransferHappened = walletService.fundTransfer(100,101,250.0);
			Double newFromWalletBalance = walletService.showWalletBalance(fromWallet.getId());
			Double newToWalletBalance = walletService.showWalletBalance(toWallet.getId());
		    assertEquals(true,isFundTransferHappened);
		    assertEquals(250.0,fromWalletBalance-newFromWalletBalance);
		    assertEquals(250.0,newToWalletBalance-toWalletBalance);
		} catch (WalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void fundTransferExceptionTest() {
		try {
			Wallet fromWallet =walletService.registerWallet(new Wallet(100, "test name1", 1000.0, "123"));
			Wallet toWallet =walletService.registerWallet(new Wallet(101, "test name2", 2000.0, "456"));
			//Boolean isFundTransferHappened = walletService.fundTransfer(100,101,250.0);
			assertThrows(WalletException.class,()-> walletService.fundTransfer(102, 101, 250.0));
			assertThrows(WalletException.class,()-> walletService.fundTransfer(100, 102, 250.0));
			assertThrows(WalletException.class,()-> walletService.fundTransfer(100, 101, 1250.0));
		} catch (WalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	@AfterAll
	public static void destroyTestData() {
		System.out.println("Clear all test data");
	}

}