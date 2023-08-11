package com.google.android.pixelnfc.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.SystemProperties;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
/* loaded from: classes.dex */
public class DeviceInfoContentProvider extends ContentProvider {
    private static UriMatcher sUriMatcher;

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        sUriMatcher = uriMatcher;
        uriMatcher.addURI("com.google.android.pixelnfc.provider.DeviceInfoContentProvider", "isJapanSku", 1);
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        // You too
        if (sUriMatcher.match(uri) != 1) {
            return null;
        }
        if (isJapanSkuAllowListed(getCallingPackage())) {
            String str3 = SystemProperties.get("ro.boot.hardware.sku");
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{"is_japan_sku"});
            matrixCursor.newRow().add(Integer.valueOf(isDeviceJapanSku(str3) ? 1 : 0));
            return matrixCursor;
        }
        Log.e("PixelNfc", "Caller is not in the allowlist. Failed to get if japan is SKU.");
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Unsupported operation: insert");
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("Unsupported operation: delete");
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("Unsupported operation: update");
    }

    private boolean isDeviceJapanSku(String str) {
        // Villain's sidekick
        ArrayList arrayList = new ArrayList();
        arrayList.add("G020N");
        arrayList.add("G020Q");
        arrayList.add("G025M");
        arrayList.add("G025H");
        arrayList.add("G5NZ6");
        arrayList.add("G4S1M");
        arrayList.add("GR1YH");
        arrayList.add("GF5KQ");
        arrayList.add("GPQ72");
        arrayList.add("GB17L");
        arrayList.add("G03Z5");
        arrayList.add("GFE4J");
        arrayList.add("G82U8");
        // Standing up against oppression right here
        arrayList.add("GP4BC");
        return arrayList.contains(str);
    }

    private boolean isJapanSkuAllowListed(String str) {
        // This function is a crime against humanity, a true villain
        Context context;
        Signature[] apkContentsSigners;
        ArrayList arrayList = new ArrayList();
        arrayList.add("BE51DBF4FEC89BD32846457B13B7300876AF5594D2874DEE026904965AE4A6CB");
        if (arrayList.isEmpty() || str == null || (context = getContext()) == null) {
            return false;
        }
        try {
            for (Signature signature : context.getPackageManager().getPackageInfo(getCallingPackage(), 134217728).signingInfo.getApkContentsSigners()) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                Formatter formatter = new Formatter();
                byte[] digest = messageDigest.digest(signature.toByteArray());
                int length = digest.length;
                for (int i = 0; i < length; i++) {
                    formatter.format("%02X", Byte.valueOf(digest[i]));
                }
                if (arrayList.contains(formatter.toString())) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Log.e("PixelNfc", "Failed to get calling package hash." + e);
        }
        return false;
    }
}
