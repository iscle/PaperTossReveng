package com.bfs.papertoss;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import com.bfs.papertoss.platform.SaveData;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread;
import java.util.Date;
import java.util.HashMap;

/* loaded from: classes.dex */
public class ErrorReporter implements Thread.UncaughtExceptionHandler {
    private static ErrorReporter S_mInstance;
    String AndroidVersion;
    String Board;
    String Brand;
    private Context CurContext;
    String Device;
    String Display;
    String FilePath;
    String FingerPrint;
    String Host;
    String ID;
    String Manufacturer;
    String Model;
    String PackageName;
    String PhoneModel;
    private Thread.UncaughtExceptionHandler PreviousHandler;
    String Product;
    String Tags;
    long Time;
    String Type;
    String User;
    String VersionName;

    static ErrorReporter getInstance() {
        if (S_mInstance == null) {
            S_mInstance = new ErrorReporter();
        }
        return S_mInstance;
    }

    public void Init(Context context) {
        this.PreviousHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.CurContext = context;
    }

    public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    void RecoltInformations(Context context) throws PackageManager.NameNotFoundException {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            this.VersionName = pi.versionName;
            this.PackageName = pi.packageName;
            this.PhoneModel = Build.MODEL;
            this.AndroidVersion = Build.VERSION.RELEASE;
            this.Board = Build.BOARD;
            this.Brand = Build.BRAND;
            this.Device = Build.DEVICE;
            this.Display = Build.DISPLAY;
            this.FingerPrint = Build.FINGERPRINT;
            this.Host = Build.HOST;
            this.ID = Build.ID;
            this.Model = Build.MODEL;
            this.Product = Build.PRODUCT;
            this.Tags = Build.TAGS;
            this.Time = Build.TIME;
            this.Type = Build.TYPE;
            this.User = Build.USER;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String CreateInformationString() throws PackageManager.NameNotFoundException {
        RecoltInformations(this.CurContext);
        String ReturnVal = "Version : " + this.VersionName;
        return ((((((((((((((((((((((((((((((((((((((ReturnVal + "\n") + "Package : " + this.PackageName) + "\n") + "FilePath : " + this.FilePath) + "\n") + "Phone Model : " + this.PhoneModel) + "\n") + "Android Version : " + this.AndroidVersion) + "\n") + "Board : " + this.Board) + "\n") + "Brand : " + this.Brand) + "\n") + "Device : " + this.Device) + "\n") + "Display : " + this.Display) + "\n") + "Finger Print : " + this.FingerPrint) + "\n") + "Host : " + this.Host) + "\n") + "ID : " + this.ID) + "\n") + "Model : " + this.Model) + "\n") + "Product : " + this.Product) + "\n") + "Tags : " + this.Tags) + "\n") + "Time : " + this.Time) + "\n") + "Type : " + this.Type) + "\n") + "User : " + this.User) + "\n") + "Total Internal memory : " + getTotalInternalMemorySize()) + "\n") + "Available Internal memory : " + getAvailableInternalMemorySize()) + "\n";
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread t, Throwable e) throws IOException {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        String stacktrace = result.toString();
        String Report = "" + stacktrace;
        String Report2 = ((Report + "\n") + "Cause : \n") + "======= \n";
        for (Throwable cause = e.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
            Report2 = Report2 + result.toString();
        }
        printWriter.close();
        int causedByIndex = Report2.indexOf("Caused by");
        String causedBy = "";
        if (causedByIndex >= 0) {
            causedBy = Report2.substring(causedByIndex, Math.min(causedByIndex + 250, Report2.length()));
        }
        Date CurDate = new Date();
        String Report3 = (((((((((Report2 + "\n\nError Report collected on : " + CurDate.toString()) + "\n") + "\n") + "Information :") + "\n") + "==============") + "\n") + "\n") + CreateInformationString()) + "****  End of current Report ***";
        String freeMem = "" + ((getAvailableInternalMemorySize() / 1024.0d) / 1024.0d) + "MB";
        String totalMem = "" + ((getTotalInternalMemorySize() / 1024.0d) / 1024.0d) + "MB";
        HashMap<String, String> map = new HashMap<>();
        map.put("Trace", Report3.substring(0, Math.min(250, Report3.length())));
        map.put("Cause", causedBy);
        map.put("Model", this.PhoneModel);
        map.put("OS", this.AndroidVersion);
        map.put("FreeMem", freeMem);
        map.put("TotalMem", totalMem);
        String MyFingerprint = Build.BRAND + "//" + this.PhoneModel + "/" + this.AndroidVersion;
        map.put("MODEL/OS", this.PhoneModel + "/" + this.AndroidVersion);
        map.put("MY_FINGERPRINT", MyFingerprint);
        SaveData.write(Report3.substring(0, Math.min(250, Report3.length())), "trace", "CRASH");
        SaveData.write(causedBy, "cause", "CRASH");
        SaveData.write(this.PhoneModel, "model", "CRASH");
        SaveData.write(this.AndroidVersion, "os", "CRASH");
        SaveData.write(freeMem, "freeMem", "CRASH");
        SaveData.write(totalMem, "TotalMem", "CRASH");
        SaveData.write(MyFingerprint, "myFingerprint", "CRASH");
        if (Report3.contains("descendant")) {
            SaveData.write(Report3, "Report", "CRASH");
        }
        SaveData.save();
        this.PreviousHandler.uncaughtException(t, e);
    }
}
