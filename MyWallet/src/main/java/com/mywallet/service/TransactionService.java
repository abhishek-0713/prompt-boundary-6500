package com.mywallet.service;

import java.time.LocalDate;
import java.util.List;

import com.mywallet.exceptions.CustomerException;
import com.mywallet.exceptions.TransactionException;
import com.mywallet.exceptions.WalletException;
import com.mywallet.model.Transaction;

public interface TransactionService {

	public Transaction addTransaction(Transaction transaction) throws TransactionException, WalletException;
	
	public List<Transaction> viewAllTransaction()throws TransactionException;
	
	public List<Transaction> viewTransactionsByDate(LocalDate fromDate, LocalDate toDate, String key)throws TransactionException, CustomerException;
	
	public List<Transaction> viewAllTransactionByType(String transactionType, String key) throws TransactionException, CustomerException;
	
}
