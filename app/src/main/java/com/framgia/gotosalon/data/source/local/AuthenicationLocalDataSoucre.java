package com.framgia.gotosalon.data.source.local;

import com.framgia.gotosalon.data.model.Account;
import com.framgia.gotosalon.data.source.AuthenicationDataSource;
import com.framgia.gotosalon.data.source.SavingAccountPreference;

public class AuthenicationLocalDataSoucre implements AuthenicationDataSource.Local {
    private SavingAccountPreference mPreference;
    private static AuthenicationLocalDataSoucre sInstance;

    public AuthenicationLocalDataSoucre(SavingAccountPreference preference) {
        mPreference = preference;
    }

    public static AuthenicationLocalDataSoucre getInstance(SavingAccountPreference preference) {
        if (sInstance == null) {
            sInstance = new AuthenicationLocalDataSoucre(preference);
        }
        return sInstance;
    }

    @Override
    public void saveAccount(Account account) {
        mPreference.saveAccount(account);
    }

    @Override
    public void restoreAccount(Account account) {
        mPreference.restoreAccount(account);
    }
}
