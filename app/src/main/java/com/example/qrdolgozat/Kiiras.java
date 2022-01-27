package com.example.qrdolgozat;

import android.os.Environment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Kiiras {
    public static void kiir(String szoveg) throws IOException {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formazottDatum = dateFormat.format(date);
        String format = String.format("%s,%s",szoveg,formazottDatum);
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)){
            File file = new File(Environment.getExternalStorageDirectory(),"scannedCodes.csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true),1024);
            writer.append(format);
            writer.append(System.lineSeparator());
            writer.close();
        }

    }
}