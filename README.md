# Enabling Osaifu-Keitai feature on non-Japanese Google Pixel smartphones

<p float="left">
 <img src="./assets/OK.INIT.webp" alt="![Video demonstrating OK initialization]" width=200>
 <img src="./assets/OK.SETUP.PASMO.webp" alt="![Video demonstrating PASMO provisioning]" width=200>
<img src="./assets/OK.WALLET.webp" alt="![Osaifu-Keitai Cards in Wallet]" width=200>
<img src="./assets/OK.UNROOT.webp" alt="![Osaifu-Keitai After Unrooting]" width=200>
</p>
<sub>GP4BC displayed in GIFs is a global SKU, this particular Pixel 7 Pro was purchased in Germany.</sub>  

<br />  


# Introduction

This doc describes the way that the Osaifu-Keitai feature is disabled on non-Japanese Google Pixel SKUs and gives solutions on how to overcome this **artificial** limitation in order to enable it.  
<sub>(TLDR: Need ROOT to modify MID/FeliCa configuration file or a system app.)</sub>

[Osaifu-Keitai (おサイフケータイ, Osaifu-Kētai), is the de facto standard mobile payment system in Japan. Osaifu-Keitai services include electronic money, identity card, loyalty card, fare collection of public transits (including railways, buses, and airplanes), or credit card.](https://en.wikipedia.org/wiki/Osaifu-Keitai)


# Eligibility and the root of the issue

The only real limiting factor for Osaifu-Keitai support is a requirement for one of the following to be present on a device:
1) Dedicated Mobile FeliCa chip or NFC SWP support for FeliCa SIM `FelicaRf` (original, old implementation);
2) Global Platform based Secure Element with FeliCa applet `FelicaGp` (new implementation).

Both Google Pixel 7 and 6 series devices have the required FeliCa applet provisoned into the Secure Element (SE) from the factory regardless of target region. As for other models, they have to be verified separately.

To verify that your device is supported, download the [Osaifu-Keitai](https://play.google.com/store/apps/details?id=com.felicanetworks.mfm.main) app `com.felicanetworks.mfm.main` and try opening it. If you're on a non-Japanese/unsupported model, you'll be met with one of the following errors:
1. `This phone doesn't support Osaifu-Keitai function. Close this application`:  
   This error means that the Osaifu-Keitai configuration file has not been found in a system. In this case there is **very little chance to enable support**, as the device most probably lacks required hardware capabilities. The only extra thing to try in this case is installing JP ROM that may have the config, but it wouldn't help if an applet is missing too.
2. `This app contains configuration files for services in Japan and has no menu items`:  
  This is the error that should give you hope, as it means that **your device has required applet and configuration files**, but configuration informs the app that it should not allow you in.  
   <img src="./assets/OK.INBOUND.UNSUPPORTED.jpg" alt="![Error message which signals that your device does indeed support Osaifu-Keitai but it is disabled]" width=250px>

After decompiling the APK and inspecting the code, we see that this app does following operations that lead to this error:

1. Upon start, `isFelicaSupported` method looks for a file at system paths:
    - `/product/etc/felica/common.cfg`;
    - `/vendor/etc/felica/common.cfg`;
    - `/system/etc/felica/common.cfg`.   

    If no file is found. App returns error 1):  

2. If a file is found, `innerLoad` method reads all entries/keys inside of it and saves them into a hash map . We are interested in following entries:   
   * `00000018`
   * `00000015`
   * `00000014`

3. On UI initialization, application calls `isCheckInbound` method, which then does the following:  
a) If key `00000018` is available, it checks if its value is `1` for success;  
b) If keys `00000015` and `00000014` are missing too, returns success ([thanks to @palBazzi for pointing this out](https://github.com/kormax/osaifu-keitai-google-pixel/issues/1));  
c) Otherwise, it retreives [ContentProvider](https://developer.android.com/guide/topics/providers/content-providers) URL from key `00000014` and column index `00000015`. Application queries the provider, if it returns `1` result is considered a success.  
If any check returns a failure, app returns error 2).

*  **This is the moment Pixel users are being screwed**.  
The provider in case of Google Pixel has URL:  
`content://com.google.android.pixelnfc.provider.DeviceInfoContentProvider/isJapanSku`, which corresponds to `com.google.android.pixelnfc` application.  
Inside the source code we see that this app retreives SKU from system build props and checks if it is in a whitelist using the `isDeviceJapanSku` method, returning `1` if it is and `0` otherwise to the application that queries the provider.  
In conclusion, **this app actually makes your device worse**. It's not responsible for proper NFC operation in any way, unlike what the name may lead people to think. Its only purpose is tell Osaifu-Keitai app if it should or shouldn't let you in.

# Possible solutions

## Overcome the limitations without root.

<sub>TLDR: no proven methods without ROOT.</suv>

**No known working ROOT-less methods**.  
Following list is provided for reference and to give a possible starting point for people who might want to dig further:

1. Manipulate `com.google.android.pixelnfc`:  
  (Failure) System apps are well-protected agaist manipulation.
    1. Uninstall this app:  
    (Failure) It is a system application and thus cannot be uninstalled.  
    2. Install your own app with same provider URL:  
    (Failure) You cannot install a new application that declares a provider with conflicting URL untill you uninstall an old one.  
    3. Decompile and patch the apk, adding your SKU to whitelist:  
    (Failure) Not possible as Android requires app overwrites/updates to have the same signature.
2. Manipulate `com.felicanetworks.mfm.main`:  
(Theory) This might be possible to do without root, but I did not attempt to finish it to the end as it proved to be too complex lacking any experience with Android development.  
Thing is, `com.felicanetworks.mfm.main` communicates with, `com.felicanetworks.mfc` (Mobile FeliCa client),  `com.felicanetworks.mfs` (Mobile FeliCa settings), `com.google.android.gms.pay.sidecar` (Google Play services for payments) which all do mutual signature verification, thus requiring you to patch ALL of those apps for them to work, replacing signatures and fixing API access due to signature changes.

As rootless solutions have led us to a dead end, we're gonna join the **dark side**.


## Solutions that require unlocking the bootloader and/or rooting.

<sub>TLDR: ROOT-based solutions work.</suv>

> **Warning**  
> Following solutins should ONLY be attempted if you know what you are doing as it is possible to PERMAMENTELY render your device unusable.  
> I assume no responsibility for your actions in case you go forward and encounter an issue.

> **Warning**  
> This section **consciously** does not provide a detailed step-by-step guide to discourage people lacking enough understanding from trying and breaking their devices.  
If someone creates a full-fledged tutorial, links and references to them will be added here.

Let's look at root-based solutions, some of which were tested and work:

1. (Success) It is possible to permamently modify the model ID (aka MID) of Google Pixel devices using custom recovery or Root+ Magisk so that they pass the check.  
For more details, read [this forum topic at XDA](https://forum.xda-developers.com/t/converting-japanese-pixel-6-to-global-version.4365275/).  
  This solution has following upsides:
    - It does not require keeping ROOT, so no SafetyNet cat and mouse and you can get OTA;  
    - Easy to reproduce by following tips presented in source thread.

    But there are also downsides:
    - You get shutter sound and that some cellular bands used in your region might be disabled for JP.
    - Modifying MID is not a safe operation, with an even increased risk of bricking a device if something goes wrong in comparison to ROOT;  
    - Currently available MID patcher script contains big binary data blobs (which are probably just blocks of model-specific configuration data) and pieces of undocumented code inside of `update-binary` file.  
    I lack required expertise to fully asses safety and correctness of that script, so the only assurance in this case is the high forum reputation of its creator.  
    There are no implications it's malicious, but if you're uncomfortable with this fact, it's adviced to look at the other two solutions. (This is the reason why I did the other ones, personally).

2. (Theory) Creating a magisk module that modifies FeliCa configuration file ([reported by one person as not working, attempt only if you are willing to experiment](https://github.com/kormax/osaifu-keitai-google-pixel/issues/2)):  
    1. (Theory) With the `00000018` key set to `1`;
    2. (Theory) With keys `00000015` and `00000014` pointing to the provider created by your own app. Harder than 1).  

    This solution has following upsides:
      - Safer to try around;
      - .1) can be done without programming experience.

    And following downsides:
      - Requires keeping ROOT for retaining access to the Osaifu-Keitai app. Need to play the SafetyNet survival horror game in order to keep access to Google Wallet.
3. (Success) By meddling with the `com.google.android.pixelnfc` application:
    1. (Success) Via an Xposed module that patches `com.google.android.pixelnfc`, using a following piece of code ([thanks to @yjwong for finding this out](https://github.com/kormax/osaifu-keitai-google-pixel/issues/3)):
        ```java
          private void hookPixelNFC(final LoadPackageParam lpparam) {
            findAndHookMethod(
                "com.google.android.pixelnfc.provider.DeviceInfoContentProvider",
                lpparam.classLoader,
                "isDeviceJapanSku",
                String.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        return true;
                    }
                }
            );
          }
        ```
    2. (Success) By [removing](https://github.com/sunilpaulmathew/De-Bloater) the original `com.google.android.pixelnfc` apk and uploading a patched one that returns successful check on every request. As MSM does not check its signature (cause as of now there is no way for it to do so) everything works from the get go.  
This is the way i've done it (proof in the GIFs at the beginning of this page).
This soultion has the same upsides and downsides as 2), although more complex to replicate.

My personal advice is to go with solution 3.1) or 3.2), even though they are a bit more complex. Solutions 2.* look simpler as they do not require you to make any modifications to software and/or create your own one, but there were reports of them not working, which has to be verified by extra **experienced** users before being deemed 100% successful or failed.

If you go with 2) or 3), you should know the following tips:
1. If you want to initialize Osaifu-Keitai with Google Wallet, you have to install [Universal SafetyNet Fix](https://github.com/Displax/safetynet-fix) in order to pass SafetyNet. For me it did the job from the get go;
2. To verify successful SafetyNet attestation, you can use the [YASNAC](https://play.google.com/store/apps/details?id=rikka.safetynetchecker). It should return PASS;
3. To delete a system app, you can use [De-Bloater](https://github.com/sunilpaulmathew/De-Bloater). Reboot the system after removing the patch;
4. To unpack and pack apk into modifyable [SMALI](https://github.com/google/smali) form, use [apktool](https://ibotpeaches.github.io/Apktool/);
5. To verify that your SMALI modifications did not break the code, you can decompile the app into java source code using [jadx](https://github.com/skylot/jadx) and check that the modifyed code has no errors; 
Be aware that this decompilation is lossy and cannot be used for patching;
6. To sideload required apps to your device in case they aren't available on Play Store, use APKMirror or APKPure. Beware most APK upload sites allow user contributions, so be careful when selecting which app to download as protections are not failproof.  
Best alternative to those sites is Aurora Store, which allows to spoof any device/client/region in order to download required app directly from Google's servers. Beware that Google isn't really happy about it as it breaks their TOS, so you might encounter problems using it. Moreover, don't not use your personal account with it; 
7. Some Osaifu-Keitai partner apps are geoblocked, I had to use multiple VPNs before it let me provision some services;
8. Some apps detect root by tring to invoke Magisk. In this case you have to hide Magisk and add the problematic app into the denylist;
9. If you plan on unrooting, DO NOT lock the bootloader before verifying that an unrooted install is bootable. You can use recovery if direct factory image flashing does not work;
10. When following tutorials, watch/read the tutorial FIRST before going forward with its steps. Rewatch/Reread multiple times, and follow the tutorial closely in order not to skip an important step.


# Extras

## Enabling Japanese Google Wallet

In order to enable Japanese Google Wallet UI, you have to install `com.felicanetworks.mfc` and turn on Japanese VPN. After a couple of minutes the wallet app should go into "Updating" state, in a couple of minutes after that it will start up with a new Japanese UI.   

Be aware that for Osaifu-Keitai functionality to work with Google Wallet, you have to install all following applications:  
- `com.felicanetworks.mfm.main` 
- `com.felicanetworks.mfc` 
- `com.felicanetworks.mfs`  
- `com.google.android.gms.pay.sidecar`


## Keeping provisioned service upon unroot

If you unroot/reinstall the system after provisioning the services, they'll continue to be available because the Osaifu-Keitai applet (Unlike CarKey applet and etc) is not cleared upon system reset. So the device will continue to act as a super-card with all provisioned services.  

If you reinstall all applications related to Osaifu-Keitai after returning to stock, while the Osaifu-Keitai app itself won't work, other applications will continue to operate. This includes:
- Google Wallet;
- Pasmo, Suica;
- Rakuten, Nanaco, Waon.  

I have not tested if the full functionality is available (topup/new service creation), but those apps at least open and allow to look at provisioned service info.

## Observations

This section contains comments and thoughts that appeared when researching this topic:

- This document was created in order to shed light on feature lock out and try to give some pointers to the people that want to try and overcome this limitation.

- There is no way to be fully sure why Google decided to lock global users out of using this feature.  
In my opinion, the following explanations, even with some overlap, could be valid:
  1. Google does not want to commit enabling Osaifu-Keitai for global markets where this feature is not required, allowing them to switch up chip/hardware suppliers in the future models without actually "taking back" any functionality from users.
  2. Google may not want to promote "closed" Osaifu-Keitai solution, trying to push FeliCa networks into implementing/allowing support via [Android Ready SE Alliance](https://developers.google.com/android/security/android-ready-se), which could be beneficial as it would give Google more contol in terms of software and UX, similarly to the way it was done by Apple;  
  3. Google did not pay licensing fees for non-Japanese models, even though all chips come preconfigured, so they are obliged by contractual obligations to attempt to lock unlicensed devices out;  
  4. Lack of proper communication between development teams who are/were unaware of this capability in global models.  
- Android implementation is worse in comparison to the one Apple has:
  - Google Wallet app wraps external apps instead of implementing all functionality on its own.
  - System does not display active SE/FeliCa-related interactions in any meaningful way (animation, sound, vibration), some apps add a button for manual state refresh as the system does not guarantee state synchronization, and the only way to know that something had happened with your service is with a notification that comes after the transaction.  
  It is either a manifestation of barebones software implementation on the OS side, or a limitation imposed by a CRS/FeliCa-specific applet.
  - Provisioned "systems" share? same logical space, therefore having multiple services with overlapping service and system codes is not possible, user has to reconfigure 'move to/from mainland' on the applet each time he wants to enable one of the conflicting systems.

- Japanese UI of google wallet is better than global one. Displaying payment cards vertically instead of in a horizontal carousel is not only more space-efficient, but also better in terms of UX as opening a credit card "tile" to pay with a particular card requires more intention from the end user, thus preventing payment with a wrong card due to accidental horizontal swipe.

- During the tests i've found out that CarKey applet does not work on rooted system as it requires hardware-backed SafetyNet attestation before configuration, which cannot be achieved with root. It signals the end of time for SafetyNet bypass, as in the near future more devs will start mandating the hardware-based check in their apps, thus making root-based bypass irreversibly unusable.


# Notes

- If you have any information to add, found an issue/typo, feel free to raise an Issue or create a PR;  
- At this moment I'm unable to create a comprehensive step-by-step tutorial on how to reproduce my findings. I think that it's better not to publish one at all rather than doing an incomplete/untested/messed up one, thus luring regular users into a situation where their device may become inoperable due to a mistake made by me.  
If a full-fledged text-based tutorial or video comes around, I'll surely add a link to it. Meanwhile, assume all info provided in this repository is intended for experienced users only;  
- In case you've been able to re-create the provided steps, feel free to report your success and findings throught the proccess;  
- [Resources](./resources/README.md) directory contains code snippets of the apps related to Osaifu-Keitai functionality lock-out.


# References

- Useful links:
  - [Converting Japanese Google Pixel to Global version](https://forum.xda-developers.com/t/converting-japanese-pixel-6-to-global-version.4365275/) - information from this thread can be used to do everything in reverse;
  - [Android Ready SE Alliance](https://developers.google.com/android/security/android-ready-se). 
- Secure Element, FeliCa info:
  - [Mobile FeliCa Platform](https://www.felicanetworks.co.jp/en/mfelica_pf.html);
  - [FeliCa FAST certification](https://www.felicanetworks.co.jp/en/security_cert/);
  - [Trust CB - FAST](https://www.trustcb.com/global-ticketing/fast/);
  - [Global Platform Card Specification](https://globalplatform.org/wp-content/uploads/2018/05/GPC_CardSpecification_v2.3.1_PublicRelease_CC.pdf); [(Archive)](https://web.archive.org/web/20230725201402/https://globalplatform.org/wp-content/uploads/2018/05/GPC_CardSpecification_v2.3.1_PublicRelease_CC.pdf).
- Tools or websites to download APK files with:
  - [Aurora Store](https://auroraoss.com) - best solution for downloading fresh and secure APKs that are not allowed for your region and/or device model.  
  Violates Google's TOS, strongly adviced not to use personal Google account with it, instead try anonymous mode or a throwaway account;
  - [APKMirror](https://www.apkmirror.com);
  - [APKPure](https://apkpure.com).
- Tools and applications:
  - [Universal SafetyNet Fix](https://github.com/Displax/safetynet-fix);
  - [YASNAC](https://play.google.com/store/apps/details?id=rikka.safetynetchecker);
  - [De-Bloater](https://github.com/sunilpaulmathew/De-Bloater);
  - [jadx](https://github.com/skylot/jadx);
  - [apktool](https://ibotpeaches.github.io/Apktool/).
- Analysed applications:
  - [Osaifu-Keitai application](https://play.google.com/store/apps/details?id=com.felicanetworks.mfm.main);
  - [Osaifu-Keitai settings](https://play.google.com/store/apps/details?id=com.felicanetworks.mfs);
  - [Google Play services for payments](https://play.google.com/store/apps/details?id=com.google.android.gms.pay.sidecar).
