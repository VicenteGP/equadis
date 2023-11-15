package com.equadis.bank.core.service;

import com.equadis.bank.core.configurations.Constants;
import com.equadis.bank.core.dto.accounting.AccountBalanceResponse;
import com.equadis.bank.core.dto.accounting.AccountCreatedResponse;
import com.equadis.bank.core.dto.accounting.CreateSavingsAccountRequest;
import com.equadis.bank.core.exceptions.NotFoundException;
import com.equadis.bank.core.exceptions.errors.AccountingErrorCode;
import com.equadis.bank.core.exceptions.ConflictException;
import com.equadis.bank.core.messaging.RabbitMqMessageProducer;
import com.equadis.bank.core.model.accounting.Account;
import com.equadis.bank.core.model.accounting.CheckingAccount;
import com.equadis.bank.core.model.accounting.SavingsAccount;
import com.equadis.bank.core.model.customer.Customer;
import com.equadis.bank.core.repository.accounting.AccountRepository;
import com.equadis.bank.core.utils.AuditManager;
import com.equadis.bank.core.utils.mappers.MessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AccountingService {

    private final AccountRepository accountRepository;

    private final RabbitMqMessageProducer rabbitMqMessageProducer;

    public AccountingService(AccountRepository accountRepository, RabbitMqMessageProducer rabbitMqMessageProducer) {
        this.accountRepository = accountRepository;
        this.rabbitMqMessageProducer = rabbitMqMessageProducer;
    }

    /**
     * Get account balance given the identificationNumber
     *
     * @param identificationNumber account identification
     * @return AccountBalanceResponse
     */
    public AccountBalanceResponse getAccountBalance(String identificationNumber) {
        Account account = findByIdentificationNumber(identificationNumber);

        return AccountBalanceResponse.builder()
                .identificationNumber(account.getIdentificationNumber())
                .type(account.getType())
                .balance(account.getBalance())
                .build();
    }

    /**
     *
     * @param identificationNumber
     * @param customer
     */
    public void addCustomer(String identificationNumber, Customer customer) {
        Account account = findByIdentificationNumber(identificationNumber);

        if (containsCustomer(account, customer.getUsername())) {
            throw new ConflictException(AccountingErrorCode.CUSTOMER_ALREADY_ADDED, customer.getUsername());

        } else {
           account.getCustomers().add(customer);
           account.setAudit(AuditManager.updated(account.getAudit()));
           accountRepository.save(account);
        }
    }

    /**
     *
     * @param customer
     * @param initialBalance
     * @return
     */
    public AccountCreatedResponse createCheckingAccount(Customer customer, long initialBalance) {
        CheckingAccount accountToCreate = CheckingAccount.builder()
                .audit(AuditManager.created())
                .identificationNumber(UUID.randomUUID())
                .balance(initialBalance)
                .customers(Set.of(customer))
                .build();

        CheckingAccount accountCreated = accountRepository.save(accountToCreate);



        return AccountCreatedResponse.builder()
                .identificationNumber(accountCreated.getIdentificationNumber())
                .build();
    }

    /**
     *
     * @param customer
     * @param createSavingsAccountRequest
     * @return
     */
    public AccountCreatedResponse createSavingsAccount(Customer customer, CreateSavingsAccountRequest createSavingsAccountRequest) {
        SavingsAccount accountToCreate = SavingsAccount.builder()
                .audit(AuditManager.created())
                .identificationNumber(UUID.randomUUID())
                .balance(createSavingsAccountRequest.initialBalance())
                .customers(Set.of(customer))
                .interest(createSavingsAccountRequest.interest())
                .withdrawalEnable(createSavingsAccountRequest.withdrawalEnable())
                .build();

        SavingsAccount accountCreated = accountRepository.save(accountToCreate);

        rabbitMqMessageProducer.sendMessage(Constants.SCHEDULE_INTEREST_QUEUE, MessageMapper.toAccountInterestMessage(accountCreated));

        return AccountCreatedResponse.builder()
                .identificationNumber(accountCreated.getIdentificationNumber())
                .build();
    }

    /**
     *
     * @param identificationNumber
     * @param enable
     */
    public void enableTransactions(String identificationNumber, boolean enable) {
        SavingsAccount savingsAccount = (SavingsAccount) accountRepository.findByIdentificationNumber(UUID.fromString(identificationNumber));
        savingsAccount.setAudit(AuditManager.updated(savingsAccount.getAudit()));
        savingsAccount.setWithdrawalEnable(enable);
        accountRepository.save(savingsAccount);
    }

    /**
     *
     * @param identificationNumbers
     * @return
     */
    public List<Account> findAccounts(Set<String> identificationNumbers) {
        return accountRepository.findBySetIdentificationNumbers(identificationNumbers);
    }

    /**
     * Find Account give identification number.
     * Throws NotFoundException if the account it's not found.
     *
     * @param identificationNumber account identification
     * @return Account
     */
    public Account findByIdentificationNumber(String identificationNumber) {
        return Optional.ofNullable(accountRepository.findByIdentificationNumber(UUID.fromString(identificationNumber)))
                .orElseThrow(() -> new NotFoundException(AccountingErrorCode.ACCOUNT_NOT_FOUND, identificationNumber));
    }

    /**
     * Check is account contains customer given username
     *
     * @param account account to check
     * @param username customer username
     * @return if customer is in account
     */
    private boolean containsCustomer(Account account, String username){
        return account.getCustomers().stream().anyMatch(customer -> customer.getUsername().equals(username));
    }

}
