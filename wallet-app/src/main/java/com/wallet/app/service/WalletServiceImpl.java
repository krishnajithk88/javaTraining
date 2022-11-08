package com.wallet.app.service;

import com.wallet.app.dao.WalletDao;
import com.wallet.app.dao.WalletDaoImpl;
import com.wallet.app.dto.Wallet;

import com.wallet.app.exception.WalletException;

public class WalletServiceImpl implements WalletService {

	private WalletDao walletRepository = new WalletDaoImpl();
	
	
	public Wallet registerWallet(Wallet newWallet) throws WalletException {
		if (this.walletRepository.getWalletById(newWallet.getId()) != null) {
			throw new WalletException("The wallet with the given wallet id is already registered ");
		}
		return this.walletRepository.addWallet(newWallet);
		
	}

	public Boolean login(Integer walletId, String password) throws WalletException {
		
		Wallet checkWallet = this.walletRepository.getWalletById(walletId);
		if (checkWallet == null) {
			throw new WalletException("Wallet id provided for login does not exist, id:"+walletId);
		}
		if (!checkWallet.getPassword().equals(password)) {
			throw new WalletException("Password provided for login is incorrect:");
		}
		return true;
	}

	public Double addFundsToWallet(Integer walletId, Double amount) throws WalletException {
		if(this.walletRepository.getWalletById(walletId) == null) {
			throw new WalletException("Wallet id provided for adding fund does not exist:"+walletId);
		}
		Wallet updateWallet = this.walletRepository.getWalletById(walletId);
		updateWallet.setBalance(amount+updateWallet.getBalance());
		this.walletRepository.updateWallet(updateWallet);
		// TODO Auto-generated method stub
		return updateWallet.getBalance();
	}

	public Double showWalletBalance(Integer walletId) throws WalletException {
		// TODO Auto-generated method stub
		Wallet checkWallet = this.walletRepository.getWalletById(walletId);
		if (checkWallet != null) {
			return this.walletRepository.getWalletById(walletId).getBalance();
		}
		else {
			throw new WalletException("Wallet id provided for checking balance does not exist");
		}
	}

	public Boolean fundTransfer(Integer fromId, Integer toId, Double amount) throws WalletException {
		Wallet fromWallet = this.walletRepository.getWalletById(fromId);
		Wallet toWallet = this.walletRepository.getWalletById(toId);
		if (fromWallet == null) {
			throw new WalletException("The id of sender given for fund transfer deos not exist ");
		}
		if (toWallet == null) {
			throw new WalletException("The id of reciever given for fund transfer does not exist ");
		}
		if (fromWallet.getBalance()< amount) {
			throw new WalletException("Currently you do not have the balance for this fund transfer, Balance:"+fromWallet.getBalance());
		}
		Double fromWalletOldBalance = fromWallet.getBalance();
		Double toWalletOldBalance = toWallet.getBalance();
		fromWallet.setBalance(fromWallet.getBalance() - amount);
		toWallet.setBalance(toWallet.getBalance() + amount);
		this.walletRepository.updateWallet(fromWallet);
		this.walletRepository.updateWallet(toWallet);
		// TODO Auto-generated method stub
		return true;
	}

	public Wallet unRegisterWallet(Integer walletId, String password) throws WalletException {
		Wallet unregWallet = this.walletRepository.getWalletById(walletId);
		if (unregWallet == null) {
			throw new WalletException("Wallet id provided to unregister does not exist:");
		}
		if (!unregWallet.getPassword().equals(password)) {
			//System.out.println(password);
			//System.out.println(unregWallet.getPassword());
			throw new WalletException("Password provided for unregistration is invalid , please provide the correct password ");
		}
		return this.walletRepository.deleteWalletById(walletId);
		// TODO Auto-generated method stub
	}

	@Override
	public Double withdrawFunds(Integer WalletID, Double amount) throws WalletException {
		
		Wallet withdrawWallet = this.walletRepository.getWalletById(WalletID);
		if (amount == 0.0) {
			throw new WalletException("The minimum value of withdrawl is 1 Rs");
		}
		if (withdrawWallet== null) {
			throw new WalletException("Provided wallet id is not valid ");
		}
		if (withdrawWallet.getBalance()< amount) {
			throw new WalletException("Entered amount is greater than your  current balance, which is : "+ withdrawWallet.getBalance());
		}
		withdrawWallet.setBalance(withdrawWallet.getBalance()-amount);
		this.walletRepository.updateWallet(withdrawWallet);
		return withdrawWallet.getBalance();
	}

}