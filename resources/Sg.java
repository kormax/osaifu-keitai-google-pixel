package com.felicanetworks.mfm.main.policy.sg;

import android.content.Context;
import android.content.res.Resources;
import androidx.room.RoomDatabase;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.policy.device.Settings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sg {
    private static final Map<String, Integer> ISSUER_MAP;
    private static final String LOG_TAG = "SG";
    private static final String[] MATCH_PROTOCOL;
    private static final String MATCH_URI = "[#-;?-Za-z!=_~]+";
    private static final String SEPARATOR = ",";
    private static final String SUPPORTED_LINE_FORMAT = "^[0-9A-F]{8},.*";
    private static final String UNSUPPORTED_LINE_FORMAT = ".*,.*,";
    private static Sg _this;
    private Context _context;
    private Map<Key, Object> _sgMap = new HashMap();
    private Map<Key, Object> _testSgMap = new HashMap();


    public enum Key {
        
        /*

        Code in between removed

        */

        NET_SIM_UC_SESSION_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { 
            @Override  
            String keyId() {
                return "02030304";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 8, false);
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        NET_SIM_UC_READ_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { 
            @Override  
            String keyId() {
                return "02030312";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 8, false);
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },

        /*

        Code in between removed

        */
    
        NET_SIM_GID_SESSION_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { 
            @Override  
            String keyId() {
                return "02030306";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 8, false);
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        NET_SIM_GID_READ_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { 
            @Override  
            String keyId() {
                return "02030313";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 8, false);
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },

        /*

        Code in between removed

        */

        NET_SIM_SID_SESSION_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { 
            @Override  
            String keyId() {
                return "02030308";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 8, false);
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        NET_SIM_SID_READ_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { 
            @Override  
            String keyId() {
                return "02030314";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 8, false);
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        
        /*

        Code in between removed

        */

        NET_AIM_VERSION_SESSION_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { 3
            @Override  
            String keyId() {
                return "02030319";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 8, false);
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        NET_AIM_VERSION_READ_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { 4
            @Override  
            String keyId() {
                return "0203031A";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 8, false);
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        
        /*

        Code in between removed

        */

        DB_AREA_IDS_TABLE_SELECT_LIMIT(FileType.EXTERNAL_MFM_CFG) { 4
            @Override  
            String keyId() {
                return "02030702";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 8, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        DB_APP_IDS_TABLE_SELECT_LIMIT(FileType.EXTERNAL_MFM_CFG) { 5
            @Override  
            String keyId() {
                return "02030703";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 8, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        DB_MULTI_IDS_TABLE_SELECT_LIMIT(FileType.INTERNAL_SG_DB_XML) { 6
            @Override  
            Object getRes(Resources resources, int i) {
                return resources.getString(R.string.sg_02030780);
            }
        },
        DB_SERVICE_TABLE_SELECT_LIMIT(FileType.EXTERNAL_MFM_CFG) { 7
            @Override  
            String keyId() {
                return "02030704";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 8, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        
        /*

        Code in between removed

        */

        FELICA_CHIP_TIMEOUT_VALUE(FileType.EXTERNAL_MFM_CFG) { 0
            @Override  
            String keyId() {
                return "02030201";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 8, false);
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        FELICA_CHIP_RETRY_COUNT(FileType.EXTERNAL_MFM_CFG) { 1
            @Override  
            String keyId() {
                return "02030202";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 2, false);
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        FELICA_MFC_ISSUER_CODE(FileType.EXTERNAL_COMMON_CFG) { 2
            @Override  
            String keyId() {
                return "00000001";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkHexNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 6, true);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_TIMEZONE(FileType.EXTERNAL_MFM_CFG) { 3
            @Override  
            String keyId() {
                return "0203030C";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(str);
                    DataCheckerUtil.checkByteLength(str, 64, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_API_CODE_ALPHA(FileType.EXTERNAL_MFM_CFG) { 4
            @Override  
            String keyId() {
                return "02030309";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkHexNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 32, true);
                    return CommonUtil.hexStringToBin(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }

            @Override  
            Object translate(String str) {
                return CommonUtil.hexStringToBin(str);
            }
        },
        SETTING_API_CODE_VERSION(FileType.EXTERNAL_MFM_CFG) { 5
            @Override  
            String keyId() {
                return "02030311";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkHexNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 4, true);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },

        /*

        Code in between removed

        */

        SETTING_ONLINE_FORMAT(FileType.EXTERNAL_COMMON_CFG) { 7
            @Override  
            String keyId() {
                return "00000005";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 1, true);
                    DataCheckerUtil.checkFixValue(str, new String[]{String.valueOf(0), String.valueOf(1)});
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_DEVICE_TYPE(FileType.EXTERNAL_MFM_CFG) { 8
            @Override  
            String keyId() {
                return "02030906";
            }

            @Override  
            boolean required() {
                return false;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 1, true);
                    DataCheckerUtil.checkFixValue(str, new String[]{String.valueOf(1), String.valueOf(2)});
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_FELICA_VERSION(FileType.EXTERNAL_COMMON_CFG) { 9
            @Override  
            String keyId() {
                return "00000013";
            }

            @Override  
            boolean required() {
                return false;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 1, true);
                    DataCheckerUtil.checkFixValue(str, new String[]{String.valueOf(0), String.valueOf(1)});
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        
        /*

        Code in between removed

        */
    
        SETTING_SKU_URL_VALUE(FileType.EXTERNAL_COMMON_CFG) { 7
            @Override  
            String keyId() {
                return "00000014";
            }

            @Override  
            boolean required() {
                return false;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(str);
                    DataCheckerUtil.checkByteLength(str, RoomDatabase.MAX_BIND_PARAMETER_CNT, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_SKU_KEY_VALUE(FileType.EXTERNAL_COMMON_CFG) { 8
            @Override  
            String keyId() {
                return "00000015";
            }

            @Override  
            boolean required() {
                return false;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(str);
                    DataCheckerUtil.checkByteLength(str, 64, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_SKU_VALUE(FileType.EXTERNAL_COMMON_CFG) { 9
            @Override  
            String keyId() {
                return "00000018";
            }

            @Override  
            boolean required() {
                return false;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 1, true);
                    DataCheckerUtil.checkFixValue(str, new String[]{String.valueOf(0), String.valueOf(1)});
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_SERVICE_INTENT_FILTER_ACTION_NAME(FileType.EXTERNAL_MFM_CFG) { 0
            @Override  
            String keyId() {
                return "02030607";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(str);
                    DataCheckerUtil.checkByteLength(str, RoomDatabase.MAX_BIND_PARAMETER_CNT, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_PKG_MFS(FileType.EXTERNAL_MFM_CFG) { 1
            @Override  
            String keyId() {
                return "02030601";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(str);
                    DataCheckerUtil.checkByteLength(str, 64, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_CLS_MFS(FileType.EXTERNAL_MFM_CFG) { 2
            @Override  
            String keyId() {
                return "02030602";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(str);
                    DataCheckerUtil.checkByteLength(str, 96, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_PKG_PLAY_STORE(FileType.EXTERNAL_COMMON_CFG) { 3
            @Override  
            String keyId() {
                return "00000004";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(str);
                    DataCheckerUtil.checkByteLength(str, 64, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_PKG_FELICA_LOCK(FileType.EXTERNAL_COMMON_CFG) { 4
            @Override  
            String keyId() {
                return "02030001";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(str);
                    DataCheckerUtil.checkByteLength(str, 64, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_CLS_FELICA_LOCK(FileType.EXTERNAL_COMMON_CFG) { 5
            @Override  
            String keyId() {
                return "02030002";
            }

            @Override  
            boolean required() {
                return true;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(str);
                    DataCheckerUtil.checkByteLength(str, 96, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_SCREEN_LOCK_SETTING(FileType.EXTERNAL_COMMON_CFG) { 6
            @Override  
            String keyId() {
                return "02030003";
            }

            @Override  
            boolean required() {
                return false;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(str);
                    DataCheckerUtil.checkByteLength(str, RoomDatabase.MAX_BIND_PARAMETER_CNT, false);
                    return str;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_TAP_INTERACTION_INCOMPATIBLE_FLG(FileType.EXTERNAL_COMMON_CFG) { 7
            @Override  
            String keyId() {
                return "00000017";
            }

            @Override  
            boolean required() {
                return false;
            }

            @Override  
            Object inspect(String str) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(str);
                    DataCheckerUtil.checkByteLength(str, 1, true);
                    DataCheckerUtil.checkFixValue(str, new String[]{String.valueOf(1)});
                    return Integer.valueOf(str);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        };
        
        /*

        Code in between removed

        */
        
        private FileType fileType;

        public enum FileType {
            EXTERNAL_MFM_CFG,
            EXTERNAL_COMMON_CFG,
            INTERNAL_SG_DB_XML,
            INTERNAL_SG_FELICA_XML,
            INTERNAL_SG_NET_XML,
            INTERNAL_SG_SETTING_XML
        }

        Object translate(String str) {
            return str;
        }

        /* synthetic */ Key(FileType fileType, AnonymousClass1 anonymousClass1) {
            this(fileType);
        }

        public FileType getFileType() {
            return this.fileType;
        }

        Key(FileType fileType) {
            this.fileType = fileType;
        }

        String keyId() {
            throw new UnsupportedOperationException("bug");
        }

        boolean required() {
            throw new UnsupportedOperationException("bug");
        }

        Object inspect(String str) throws InspectException {
            throw new UnsupportedOperationException("bug");
        }

        Object getRes(Resources resources, int i) {
            throw new UnsupportedOperationException("bug");
        }
    }

    public enum DependencyChecker {
        FELICA_CHIP_VERSION_CHECKER { dencyChecker.1
            @Override 
            boolean check() {
                return Settings.getFelicaChipVersion() != null;
            }
        };

        abstract boolean check();

        /* synthetic */ DependencyChecker(AnonymousClass1 anonymousClass1) {
            this();
        }

        void inspect() throws InspectException {
            if (!check()) {
                throw new InspectException(InspectException.Type.INVALID_DEPENDENCY, "dependency error");
            }
        }
    }

    public static class InspectException extends Exception {
        private Type type;

        public enum Type {
            INVALID_LENGTH,
            INVALID_ATTRIBUTE,
            INVALID_RANGE,
            INVALID_DEPENDENCY
        }

        InspectException(DataCheckerException dataCheckerException) {
            super(dataCheckerException);
            int errorId = dataCheckerException.getErrorId();
            if (errorId == 0) {
                this.type = Type.INVALID_LENGTH;
            } else if (errorId != 1) {
            } else {
                this.type = Type.INVALID_ATTRIBUTE;
            }
        }

        InspectException(Type type, String str) {
            super(str);
            this.type = type;
        }

        public Type getType() {
            return this.type;
        }
    }

    private Sg(Context context) {
        this._context = context;
    }

    static {
        HashMap hashMap = new HashMap();
        ISSUER_MAP = hashMap;
        hashMap.put("100001", 0);
        ISSUER_MAP.put("100002", 1);
        ISSUER_MAP.put("100003", 2);
        ISSUER_MAP.put("100008", 3);
        MATCH_PROTOCOL = new String[]{"http:", "https:"};
    }

    public static boolean load(Context context) {
        try {
            Sg sg = new Sg(context);
            _this = sg;
            boolean innerLoad = sg.innerLoad();
            if (innerLoad) {
                return innerLoad;
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            _this = null;
            throw th;
        }
        _this = null;
        return false;
    }

    private boolean innerLoad() {
        String str;
        Key[] values;
        DependencyChecker[] values2;
        Resources resources = this._context.getResources();
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        String[] sgPath = getSgPath(resources);
        int length = sgPath.length;
        String str2 = null;
        String str3 = null;
        int i = 0;
        while (true) {
            if (i >= length) {
                str = str2;
                break;
            }
            str = sgPath[i];
            String str4 = str + getCmnFileName(resources);
            try {
                if (new File(str4).exists()) {
                    try {
                        str2 = str + getMfmFileName(resources);
                        str3 = str4;
                        break;
                    } catch (Exception unused) {
                        str3 = str4;
                    }
                } else {
                    continue;
                }
            } catch (Exception unused2) {
            }
            i++;
        }
        if (str3 != null) {
            hashMap.putAll(readCfgFile(str3, arrayList));
        }
        int intValue = ISSUER_MAP.get(hashMap.get(Key.FELICA_MFC_ISSUER_CODE.keyId())).intValue();
        if (str2 != null) {
            hashMap.putAll(readCfgFile(str2, arrayList));
        }
        this._testSgMap.putAll(readTestSgFile(resources, str));
        for (Key key : Key.values()) {
            switch (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$sg$Sg$Key$FileType[key.getFileType().ordinal()]) {
                case 1:
                case 2:
                    if (hashMap.containsKey(key.keyId())) {
                        try {
                            this._sgMap.put(key, key.inspect((String) hashMap.get(key.keyId())));
                            break;
                        } catch (InspectException unused3) {
                            arrayList.add("Illegal:" + key);
                            break;
                        }
                    } else if (key.required()) {
                        arrayList.add("Required:" + key);
                        break;
                    } else {
                        break;
                    }
                case 3:
                case 4:
                case 5:
                case 6:
                    this._sgMap.put(key, key.getRes(resources, intValue));
                    break;
            }
        }
        for (DependencyChecker dependencyChecker : DependencyChecker.values()) {
            try {
                dependencyChecker.inspect();
            } catch (InspectException unused4) {
                arrayList.add("Banned Dependency : " + dependencyChecker);
            }
        }
        return arrayList.isEmpty();
    }

    /*

     Code in between removed

    */

    public static Object getValue(Key key) {
        Sg sg = _this;
        if (sg != null) {
            return sg.innerGetValue(key);
        }
        throw new IllegalStateException("Call load() before this method.");
    }

    private Object innerGetValue(Key key) {
        Object obj = this._testSgMap.get(key);
        return obj == null ? this._sgMap.get(key) : obj;
    }

    private String getCmnFileName(Resources resources) {
        return resources.getString(R.string.sg_02030901);
    }

    private String getMfmFileName(Resources resources) {
        return resources.getString(R.string.sg_02030902);
    }

    private String[] getSgPath(Resources resources) {
        return resources.getStringArray(R.array.sg_02030903);
    }

    private Map<String, String> readCfgFile(String str, List<String> list) {
        BufferedReader bufferedReader;
        HashMap hashMap = new HashMap();
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(str)), StringUtil.UTF_8));
            } catch (IOException unused) {
            }
        } catch (IOException unused2) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                if (readLine.matches(SUPPORTED_LINE_FORMAT) && !readLine.matches(UNSUPPORTED_LINE_FORMAT)) {
                    String[] split = readLine.split(SEPARATOR);
                    if (split.length > 1) {
                        if (hashMap.containsKey(split[0])) {
                            list.add("Overlap:" + split[0]);
                        } else {
                            hashMap.put(split[0], split[1]);
                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException unused3) {
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
                bufferedReader2.close();
            }
            return hashMap;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException unused4) {
                }
            }
            throw th;
        }
        return hashMap;
    }

    private Map<Key, Object> readTestSgFile(Resources resources, String str) {
        Key[] values;
        HashMap hashMap = new HashMap();
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File(str + resources.getString(R.string.sg_02030919))), StandardCharsets.UTF_8));
                try {
                    try {
                        StringBuilder sb = new StringBuilder();
                        for (String readLine = bufferedReader2.readLine(); readLine != null; readLine = bufferedReader2.readLine()) {
                            sb.append(readLine);
                        }
                        JSONObject jSONObject = new JSONObject(sb.toString());
                        for (Key key : Key.values()) {
                            try {
                                Object obj = jSONObject.get(key.name());
                                if (obj instanceof JSONArray) {
                                    JSONArray jSONArray = (JSONArray) obj;
                                    if (jSONArray.get(0) instanceof String) {
                                        ArrayList arrayList = new ArrayList();
                                        for (int i = 0; i < jSONArray.length(); i++) {
                                            arrayList.add(jSONArray.getString(i));
                                        }
                                        hashMap.put(key, arrayList.toArray(new String[0]));
                                    } else if (jSONArray.get(0) instanceof Integer) {
                                        ArrayList arrayList2 = new ArrayList();
                                        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                                            arrayList2.add(Integer.valueOf(jSONArray.getInt(i2)));
                                        }
                                        hashMap.put(key, arrayList2.toArray(new Integer[0]));
                                    }
                                } else if (obj instanceof String) {
                                    hashMap.put(key, key.translate((String) obj));
                                } else if (obj instanceof Integer) {
                                    hashMap.put(key, obj);
                                }
                            } catch (Exception unused) {
                            }
                        }
                        bufferedReader2.close();
                    } catch (IOException | JSONException unused2) {
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        return hashMap;
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused4) {
            }
        } catch (IOException | JSONException unused5) {
        } catch (Throwable th2) {
            th = th2;
        }
        return hashMap;
    }

    private static void checkUrlCharFormat(String str) throws InspectException {
        if (str == null) {
            throw new InspectException(InspectException.Type.INVALID_LENGTH, "target is null");
        }
        String[] strArr = MATCH_PROTOCOL;
        int length = strArr.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (str.indexOf(strArr[i]) == 0) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (!z) {
            InspectException.Type type = InspectException.Type.INVALID_ATTRIBUTE;
            throw new InspectException(type, str + " is invalid protocol");
        } else if (str.matches("[#-;?-Za-z!=_~]+")) {
        } else {
            InspectException.Type type2 = InspectException.Type.INVALID_ATTRIBUTE;
            throw new InspectException(type2, str + " is unmatch URI");
        }
    }
}
