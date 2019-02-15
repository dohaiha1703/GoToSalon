package com.framgia.gotosalon.data.source;

import android.content.SharedPreferences;

import com.framgia.gotosalon.data.model.Account;

public class SavingAccountPreference {
    private static final String KEY_PREFERENCE_EMAIL = "KEY_PREFERENCE_EMAIL";
    private static final String KEY_PREFERENCE_PASSWORD = "KEY_PREFERENCE_PASSWORD";
    private static final String KEY_PREFERENCE_CHECK = "KEY_PREFERENCE_CHECK";
    private static final String DEFAULT_EMAIL = "EMAIL";
    private static final String DEFAULT_PASSWORD = "PASSWORD";
    private static SavingAccountPreference sInstance;
    private SharedPreferences mPreferences;

    public SavingAccountPreference(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    public static SavingAccountPreference getInstance(SharedPreferences preferences) {
        if (sInstance == null) {
            sInstance = new SavingAccountPreference(preferences);
        }
        return sInstance;
    }

    public void saveAccount(Account account) {
        SharedPreferences.Editor editor = mPreferences.edit();
        if (account.isRemember()) {
            editor.putString(KEY_PREFERENCE_EMAIL, account.getEmail());
            editor.putString(KEY_PREFERENCE_PASSWORD, account.getPassword());
            editor.putBoolean(KEY_PREFERENCE_CHECK, account.isRemember());
        } else {
            editor.clear();
        }
        editor.commit();
    }

    public void restoreAccount(Account account) {
        boolean check = mPreferences.getBoolean(KEY_PREFERENCE_CHECK, false);
        if (check) {
            account.setRemember(check);
            account.setEmail(mPreferences.getString(KEY_PREFERENCE_EMAIL, DEFAULT_EMAIL));
            account.setPassword(mPreferences.getString(KEY_PREFERENCE_PASSWORD, DEFAULT_PASSWORD));
        }
    }
}
