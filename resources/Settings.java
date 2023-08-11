package com.felicanetworks.mfm.main.policy.device;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.Objects;


public class Settings {

    /*

     Code in between removed

    */

    public boolean isCheckInbound(Context context) {
        // Counterintuitively, if this method returns false - the check goes through, and vice versa.
        int parseInt;
        Integer num = (Integer) Sg.getValue(Sg.Key.SETTING_SKU_VALUE);
        if (num != null) {
            try {
                // If 00000018 is 1, return false (Success). If 0, return true (Failure)
                return 1 != num.intValue();
            } catch (NumberFormatException unused) {
            }
        }
        String str = (String) Sg.getValue(Sg.Key.SETTING_SKU_URL_VALUE);
        String str2 = (String) Sg.getValue(Sg.Key.SETTING_SKU_KEY_VALUE);
        // If 00000014 and 00000015 are missing, return false (Success)
        if (str == null && str2 == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            try {
                Cursor query = context.getContentResolver().query(Uri.parse(str), null, null, null, null);
                query.moveToFirst();
                int columnIndex = query.getColumnIndex(str2);
                int type = query.getType(columnIndex);
                if (1 == type) {
                    parseInt = query.getInt(columnIndex);
                } else {
                    parseInt = 3 == type ? Integer.parseInt(query.getString(columnIndex)) : 0;
                }
                // If result is 1, return false (Success);
                if (parseInt == 1) {
                    if (query != null) {
                        query.close();
                    }
                    return false;
                }
                if (query != null) {
                    query.close();
                }
                // Otherwise, return true (Failure);
                return true;
            } catch (Exception unused2) {
                throw new IllegalStateException();
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }
}
