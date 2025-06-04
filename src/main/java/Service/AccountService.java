package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }


    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank())
            return null;
        if (account.getPassword() == null || account.getPassword().length() < 4)
            return null;
        
        Account existing = accountDAO.getAccountByUsername(account.getUsername());
        if (existing != null)
            return null;
        
        return accountDAO.registerAccount(account);
    }

    public Account login(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank())
            return null;
        if (account.getPassword() == null || account.getPassword().length() < 4)
            return null;
        
        Account existing = accountDAO.getAccountByUsername(account.getUsername());
        if (existing == null)
            return null;
        if (!existing.getPassword().equals(account.getPassword()))
            return null;
        
        return existing;
    }
}
