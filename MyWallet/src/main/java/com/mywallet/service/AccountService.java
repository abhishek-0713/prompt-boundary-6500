package com.mywallet.service;

import java.util.List;

import javax.validation.Valid;

import com.mywallet.exceptions.BankAccountException;
import com.mywallet.exceptions.CustomerException;
import com.mywallet.exceptions.WalletException;
import com.mywallet.model.BankAccount;
import com.mywallet.model.Wallet;
import com.mywallet.model.dto.BankAccountDTO;


public interface AccountService {

	public Wallet addAccount(@Valid BankAccountDTO bankAccountDto, String key) throws WalletException, BankAccountException, CustomerException;
	
	public Wallet removeAccount(@Valid BankAccountDTO bankAccountDto, String key) throws BankAccountException, CustomerException;
	
	public BankAccount viewAccount(String key) throws BankAccountException, CustomerException;
	
	public List<BankAccount> viewAllAccount(String key) throws BankAccountException, CustomerException;

	
}
