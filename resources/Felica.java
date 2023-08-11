package com.felicanetworks.mfm.mfcutil.mfc;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.FelicaResultInfoBlockCountInformationArray;
import com.felicanetworks.mfc.FelicaResultInfoBoolean;
import com.felicanetworks.mfc.FelicaResultInfoByteArray;
import com.felicanetworks.mfc.FelicaResultInfoDataArray;
import com.felicanetworks.mfc.FelicaResultInfoInt;
import com.felicanetworks.mfc.FelicaResultInfoIntArray;
import com.felicanetworks.mfc.FelicaResultInfoKeyInformationArray;
import com.felicanetworks.mfc.FelicaResultInfoNodeInformation;
import com.felicanetworks.mfc.IFelica;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.IFelicaPushAppNotificationListener;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.PushAppNotificationListener;
import com.felicanetworks.mfc.PushNotifyAppSegment;
import com.felicanetworks.mfc.PushSegment;
import com.felicanetworks.mfc.PushSegmentParcelableWrapper;
import com.felicanetworks.mfc.ServiceUtil;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiClient;
import com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr;
import java.io.File;
/* loaded from: classes.dex */
public class Felica extends Service {
    private static final String COMMON_FILE_NAME = "common.cfg";
    private static final String[] COMMON_FILE_PATHS = {"/product/etc/felica/", "/vendor/etc/felica/", "/system/etc/felica/"};

    /*

    Code in between removed

    */

    public static boolean isFelicaSupported() {
        LogMgr.log(3, "000");
        String[] strArr = COMMON_FILE_PATHS;
        int length = strArr.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String str = strArr[i] + COMMON_FILE_NAME;
            try {
            } catch (Exception e) {
                LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            }
            if (new File(str).exists()) {
                LogMgr.log(7, "001 exist file path = " + str);
                z = true;
                break;
            }
            continue;
            i++;
        }
        LogMgr.log(3, "999 result = " + z);
        return z;
    }
}
