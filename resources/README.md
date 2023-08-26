# Code samples

This directory contains following files:

-   [DeviceInfoContentProvider.smali](./DeviceInfoContentProvider.smali):  
 Contains the Smali code snippet of the `com.google.android.pixelnfc` APK class that is responsible for Osaifu-Keitai lockout for non-global models.  
 Example code contains pre-added bypass for GP4BC MID. Change your file accordingly;  
- [DeviceInfoContentProvider.java](./DeviceInfoContentProvider.java):  
    Contains Java code retreived via `com.google.android.pixelnfc` APK decompilation with jadx, is a direct counterpart of the SMALI code previously referenced in this section;
- [common.cfg](./common.cfg):  
    Example of a FeliCa configuration file. This particular one taken from a global Pixel 7 Pro SKU. Provided as reference, don't put your hopes up by adding this file to a device that doesn't have it from factory (but tell me the results if you do :);
- [Sg.java](./Sg.java):  
    Class responsible for loading and storing setting keys from `common.cfg` file using `load`, `innerLoad` and `getValue` methods. Also contains definitions and values for keys found inside that same file. Static and irrelevant definitions have been removed to save space;
- [Settings.java](./Settings.java):  
    A snippet of code from `com.felicanetworks.mfm.main`, containing `isCheckInbound` method, responsible for error number 2);
- [Felica.java](./Felica.java):  
    A snippet of code from `com.felicanetworks.mfm.main`, containing `isFelicaSupported` method, responsible for error number 1).

For other information, there's always a possibility of exploring the code by yourself. This can be done using both jadx and apktool.


# References

- Useful links:
- Tools and applications:
  - [jadx](https://github.com/skylot/jadx);
  - [apktool](https://ibotpeaches.github.io/Apktool/).