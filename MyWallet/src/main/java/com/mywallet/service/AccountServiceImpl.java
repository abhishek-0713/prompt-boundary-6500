package com.mywallet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mywallet.exceptions.BankAccountException;
import com.mywallet.exceptions.CustomerException;
import com.mywallet.exceptions.WalletException;
import com.mywallet.model.BankAccount;
import com.mywallet.model.CurrentUserSession;
import com.mywallet.model.Wallet;
import com.mywallet.model.dto.BankAccountDTO;
import com.mywallet.repository.BankAccountRepo;
import com.mywallet.repository.CurrentSessionRepo;
import com.mywallet.repository.WalletRepo;

@Service
public class AccountServiceImpl implements AccountService {

//	@Autowired
//	private CustomerRepo customerRepo;
	@Autowired
	private WalletRepo walletRepo;
	@Autowired
	private BankAccountRepo bankAccountRepo;
	@Autowired 
	private CurrentSessionRepo currentSessionRepo;

	
	/*---------------------------------   Add Customer Bank Account  -------------------------------------*/
	@Override
	public Wallet addAccount(BankAccountDTO bankAccountDTO, String key) throws WalletException, BankAccountException, CustomerException {

		CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        
		if(currUserSession == null) {
    		throw new CustomerException("No Customer LoggedIn");
		}
		
		Optional<BankAccount> optional = bankAccountRepo.findById(bankAccountDTO.getAccountNo());
	
		
		if(optional.isEmpty()){	
			  
			  Wallet wallet =  walletRepo.customerWalletDetailsByCId(currUserSession.getUserId());
			  
			  BankAccount createBankAccount = new BankAccount(bankAccountDTO.getAccountNo(), bankAccountDTO.getIFSCCode(), bankAccountDTO.getBankName(), bankAccountDTO.getBalance());
			  createBankAccount.setWallet(wallet); 
			   
			  bankAccountRepo.save(createBankAccount);

			  return wallet;
		  }
		  throw new BankAccountException("Bank Account already exist With Given AccountNumber... Try Different");
	
	}

	
	/*---------------------------------   Remove Customer Bank Account  -------------------------------------*/
	@Override
	public Wallet removeAccount(BankAccountDTO bankAccountDTO, String key) throws BankAccountException, CustomerException {

	
		CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
		
		if(currUserSession == null) {
			throw new CustomerException("No Customer LoggedIn");
		}
		
		Optional<BankAccount> optional = bankAccountRepo.findById(bankAccountDTO.getAccountNo());
		
		if(optional.isPresent()) {
			
			bankAccountRepo.delete(optional.get());
			
			Wallet wallet = optional.get().getWallet();
			
			return wallet;
		}
		throw new BankAccountException("No Bank Account exist");
		
	}

	
	/*---------------------------------   View Customer Bank Account  -------------------------------------*/
	@Override
	public BankAccount viewAccount(String key) throws BankAccountException, CustomerException {
		
		CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
		
		if(currUserSession == null) {
			throw new CustomerException("No Customer LoggedIn");
		}
		
		BankAccount baccAccount = bankAccountRepo.findByWalletId(walletRepo.customerWalletDetailsByCId(currUserSession.getUserId()).getWalletId()).get(0);
		
		if(baccAccount != null) {
			return baccAccount;
		}
		throw new BankAccountException("No Bank Account exist");

	}

	
	/*---------------------------------   View All Customers Account  -------------------------------------*/
	@Override
	public List<BankAccount> viewAllAccount( String key) throws BankAccountException, CustomerException {
		
		CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
		
		if(currUserSession == null) {
			throw new CustomerException("No Customer LoggedIn");
		}
		
		List<BankAccount> baccAccounts = bankAccountRepo.findAllByWalletId(walletRepo.customerWalletDetailsByCId(currUserSession.getUserId()).getWalletId());
		
		if(baccAccounts != null) {
			return baccAccounts;
		}
		throw new BankAccountException("No Bank Account exist");
		
	}


}
