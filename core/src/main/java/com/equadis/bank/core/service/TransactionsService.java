package com.equadis.bank.core.service;

import com.equadis.bank.core.configurations.Constants;
import com.equadis.bank.core.dto.accounting.AccountBalanceResponse;
import com.equadis.bank.core.dto.accounting.ApplyInterestRequest;
import com.equadis.bank.core.dto.transaction.AddTransactionRequest;
import com.equadis.bank.core.dto.transaction.TransactionSearchFilter;
import com.equadis.bank.core.dto.transaction.TransactionSearchResponse;
import com.equadis.bank.core.exceptions.BadRequestException;
import com.equadis.bank.core.exceptions.ConflictException;
import com.equadis.bank.core.exceptions.errors.TransactionErrorCode;
import com.equadis.bank.core.model.accounting.Account;
import com.equadis.bank.core.model.accounting.AccountType;
import com.equadis.bank.core.model.accounting.SavingsAccount;
import com.equadis.bank.core.model.transaction.Transaction;
import com.equadis.bank.core.model.transaction.TransactionCategory;
import com.equadis.bank.core.model.transaction.TransactionType;
import com.equadis.bank.core.repository.transaction.TransactionsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;

    private final AccountingService accountingService;

    public TransactionsService(TransactionsRepository transactionsRepository, AccountingService accountingService) {
        this.transactionsRepository = transactionsRepository;
        this.accountingService = accountingService;
    }

    public Page<TransactionSearchResponse> getTransactions(TransactionSearchFilter transactionSearchFilter, Pageable pageable) {
        return transactionsRepository.findBySearchCriteria(transactionSearchFilter, pageable);
    }

    public AccountBalanceResponse addTransaction(AddTransactionRequest addTransactionRequest) {
        Account account = accountingService.findByIdentificationNumber(addTransactionRequest.accountIdentificationNumber());

        if (TransactionCategory.WITHDRAWAL.equals(addTransactionRequest.category()) && AccountType.SAVINGS.equals(account.getType())) {
            SavingsAccount savingsAccount = (SavingsAccount) account;
            if (!savingsAccount.isWithdrawalEnable()) {
                throw new ConflictException(TransactionErrorCode.WITHDRAWAL_DISABLE_CONFLICT, account.getIdentificationNumber());
            }
        }

        double currentBalance = getUpdatedBalance(account.getBalance(), addTransactionRequest.amount(), addTransactionRequest.type());
        account.setBalance(currentBalance);
        Transaction newTransaction = Transaction.builder()
                .account(account)
                .username(addTransactionRequest.username())
                .timestamp(OffsetDateTime.now())
                .category(addTransactionRequest.category())
                .type(addTransactionRequest.type())
                .amount(addTransactionRequest.amount())
                .currentBalance(currentBalance)
                .description(addTransactionRequest.description())
                .build();
        transactionsRepository.save(newTransaction);
        return AccountBalanceResponse.builder()
                .identificationNumber(account.getIdentificationNumber())
                .type(account.getType())
                .balance(account.getBalance())
                .build();
    }

    public void applyInterest(ApplyInterestRequest applyInterestRequest) {
        List<Account> accounts = accountingService.findAccounts(applyInterestRequest.identificationNumbers());
        List<Transaction> transactions = accounts.stream()
                .filter(Account::isSavingsAccount)
                .map(SavingsAccount.class::cast)
                .map(savingsAccount -> {
                    double oldBalance = savingsAccount.getBalance();
                    double amount = oldBalance * savingsAccount.getInterest();
                    double currentBalance = getUpdatedBalance(oldBalance, amount, TransactionType.CREDIT);
                    savingsAccount.setBalance(currentBalance);
                    return Transaction.builder()
                            .account(savingsAccount)
                            .timestamp(OffsetDateTime.now())
                            .category(TransactionCategory.INTEREST)
                            .type(TransactionType.CREDIT)
                            .amount(amount)
                            .currentBalance(currentBalance)
                            .description(String.format(Constants.INTEREST_APPLIED, savingsAccount.getInterest()))
                            .build();
                }).toList();
        transactionsRepository.saveAll(transactions);
    }

    private double getUpdatedBalance(double balance, double amount, TransactionType type) {
        if (amount > 0 && TransactionType.CREDIT.equals(type)) {
            return balance + amount;
        } else if (amount < 0 && TransactionType.DEBIT.equals(type)) {
            return balance - amount;
        } else {
            throw new BadRequestException(TransactionErrorCode.TYPE_AMOUNT_CONFLICT, type, amount);
        }
    }
}
