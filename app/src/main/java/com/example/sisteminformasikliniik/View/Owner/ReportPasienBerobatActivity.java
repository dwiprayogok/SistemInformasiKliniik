package com.example.sisteminformasikliniik.View.Owner;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisteminformasikliniik.Controller.ReportController;
import com.example.sisteminformasikliniik.DB.DataLogin.DataLoginEntitiy;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.Pembayaran.PembayaranEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.interfaces.IReportDataPasien;

import org.joda.time.LocalDate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ReportPasienBerobatActivity extends AppCompatActivity implements IReportDataPasien {
    private Button btnGeneratePDF;
    private RecyclerView recyclerView;
    private Bitmap bitmap, scaleBmp;
    int pageWidth =1200;
    private ReportController reportController;
    private LoadingDialog loadingDialog;
    private List<DataPendaftaranEntity> pendaftaranEntityList;
    private List<DataLoginEntitiy> dataLoginEntitiys;
    private static final Locale DEFAULT_LOCALE_INDONESIA = new Locale("id", "ID");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_pasien_berobat);
        loadingDialog = new LoadingDialog(ReportPasienBerobatActivity.this);
        reportController = new ReportController(this,loadingDialog);
        btnGeneratePDF =findViewById(R.id.btnGeneratePDF);
        loadingDialog.show();
        pendaftaranEntityList = reportController.getAllDataPasienApproved("Approved");
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.headerpdf);
        scaleBmp = Bitmap.createScaledBitmap(bitmap,1096,257,false);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        generateReportPDF();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void generateReportPDF(){
        btnGeneratePDF.setOnClickListener(v -> {
            PdfDocument pdfDocument = new PdfDocument();
            Paint myPaint = new Paint();
            Paint linePaint = new Paint();
            Paint titlePaint = new Paint();
            Paint footerTitle = new Paint();
            Paint footerName = new Paint();
            TextPaint mTextPaint=new TextPaint();
            mTextPaint.setTextSize(20);
            mTextPaint.setTextAlign(Paint.Align.CENTER);

            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
            PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);
            Canvas canvas = myPage.getCanvas();

             canvas.drawBitmap(scaleBmp,0,0,myPaint);
             myPaint.setStyle(Paint.Style.STROKE);
             myPaint.setStrokeWidth(2);

             linePaint.setStyle(Paint.Style.STROKE);
             linePaint.setStrokeWidth(2);

             titlePaint.setTextAlign(Paint.Align.CENTER);
             titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
             titlePaint.setTextSize(40);


             canvas.drawRect(20,250,pageWidth-20,250,linePaint);
             canvas.drawRect(20,400,pageWidth-20,400,linePaint);
             canvas.drawText("Laporan Data Pasien Berobat",pageWidth/2,350,titlePaint);
             canvas.drawRect(20,500,pageWidth-20,450,myPaint);

             myPaint.setTextAlign(Paint.Align.LEFT);
             myPaint.setStyle(Paint.Style.FILL);
             myPaint.setTextSize(25);
             canvas.drawText("NIK",40,480,myPaint);
             canvas.drawText("Nama",280,480,myPaint);
             canvas.drawText("Dokter/Bidan",430,480,myPaint);
             canvas.drawText("Pelayanan",650,480,myPaint);
             canvas.drawText("Tanggal",850,480,myPaint);
             canvas.drawText("Keluhan",1050,480,myPaint);

            for (int i=0; i < pendaftaranEntityList.size(); i++ ){
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                myPaint.setTextSize(20);

                String [] namaDokter  ;
                String [] namaLayanan ;
                String [] keluhan ;

                if (pendaftaranEntityList.get(i).getNamaDokter().contains(",")) {
                    namaDokter = pendaftaranEntityList.get(i).getNamaDokter().split(",");
                } else {
                    namaDokter = pendaftaranEntityList.get(i).getNamaDokter().split(" ");
                }

                if (pendaftaranEntityList.get(i).getJenisPelayanan().contains("/")) {
                    namaLayanan = pendaftaranEntityList.get(i).getJenisPelayanan().split("/");
                } else {
                    namaLayanan = pendaftaranEntityList.get(i).getJenisPelayanan().split(" ");
                }

                if (pendaftaranEntityList.get(i).getKeluhan().contains(":")) {
                    keluhan = pendaftaranEntityList.get(i).getKeluhan().split(":");
                } else {
                    keluhan = pendaftaranEntityList.get(i).getKeluhan().split(" ");
                }


                if (i==0) {
                    int ya = 480+50;
                    int ya0 = 480+50;
                    int ya00 = 480+50;
                    int ya000 = 480+50;

                    canvas.drawText(pendaftaranEntityList.get(i).getNik(),40,550,myPaint);

                    for (String line: pendaftaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 300,ya, mTextPaint);
                        ya += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: namaDokter) {
                        canvas.drawText(line, 500,ya0, mTextPaint);
                        ya0 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line:namaLayanan) {
                        canvas.drawText(line, 700,ya00, mTextPaint);
                        ya00 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pendaftaranEntityList.get(i).getTanggalBerobat(),850,550,myPaint);

                    for (String line: keluhan) {
                        canvas.drawText(line, 1080,ya000, mTextPaint);
                        ya000 += mTextPaint.descent() - mTextPaint.ascent();
                    }


                }
                else if (i==1){

                    int ya1 = 480+150;
                    int ya11 = 480+150;
                    int ya111 = 480+150;
                    int ya1111 = 480+150;

                    canvas.drawText(pendaftaranEntityList.get(i).getNik(),40,480+180,myPaint);

                    for (String line: pendaftaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 300,ya1, mTextPaint);
                        ya1 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaDokter) {
                        canvas.drawText(line, 500,ya11, mTextPaint);
                        ya11 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaLayanan) {
                        canvas.drawText(line, 700,ya111, mTextPaint);
                        ya111 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pendaftaranEntityList.get(i).getTanggalBerobat(),850,480+150,myPaint);

                    for (String line: keluhan) {
                        canvas.drawText(line, 1080,ya1111, mTextPaint);
                        ya1111 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }
                else if (i==2){
                    int ya2 = 480+250;
                    int ya22 = 480+250;
                    int ya222 = 480+250;
                    int ya2222 = 480+250;

                    canvas.drawText(pendaftaranEntityList.get(i).getNik(),40,480+270,myPaint);

                    for (String line: pendaftaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 300,ya2, mTextPaint);
                        ya2 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaDokter) {
                        canvas.drawText(line, 500,ya22, mTextPaint);
                        ya22 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaLayanan) {
                        canvas.drawText(line, 700,ya222, mTextPaint);
                        ya222 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pendaftaranEntityList.get(i).getTanggalBerobat(),850,480+250,myPaint);

                    for (String line: keluhan) {
                        canvas.drawText(line, 1080,ya2222, mTextPaint);
                        ya2222 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }

                else if (i==3){
                    int ya3 = 480+350;
                    int ya33 = 480+350;
                    int ya333 = 480+350;
                    int ya3333 = 480+350;

                    canvas.drawText(pendaftaranEntityList.get(i).getNik(),40,480+370,myPaint);

                    for (String line: pendaftaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 300,ya3, mTextPaint);
                        ya3 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaDokter) {
                        canvas.drawText(line, 500,ya33, mTextPaint);
                        ya33 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaLayanan) {
                        canvas.drawText(line, 700,ya333, mTextPaint);
                        ya333 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pendaftaranEntityList.get(i).getTanggalBerobat(),850,480+350,myPaint);

                    for (String line: keluhan) {
                        canvas.drawText(line, 1080,ya3333, mTextPaint);
                        ya3333 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }

                else if (i==4){
                    int ya4 = 480+450;
                    int ya44 = 480+450;
                    int ya444 = 480+450;
                    int ya4444 = 480+450;

                    canvas.drawText(pendaftaranEntityList.get(i).getNik(),40,480+450,myPaint);

                    for (String line: pendaftaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 300,ya4, mTextPaint);
                        ya4 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaDokter) {
                        canvas.drawText(line, 500,ya44, mTextPaint);
                        ya44 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaLayanan) {
                        canvas.drawText(line, 700,ya444, mTextPaint);
                        ya444 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pendaftaranEntityList.get(i).getTanggalBerobat(),850,480+450,myPaint);

                    for (String line: keluhan) {
                        canvas.drawText(line, 1080,ya4444, mTextPaint);
                        ya4444 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }
                else if (i==5){
                    int ya5 = 480+550;
                    int ya55 = 480+550;
                    int ya555 = 480+550;
                    int ya5555 = 480+550;

                    canvas.drawText(pendaftaranEntityList.get(i).getNik(),40,480+550,myPaint);

                    for (String line: pendaftaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 300,ya5, mTextPaint);
                        ya5 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaDokter) {
                        canvas.drawText(line, 500,ya55, mTextPaint);
                        ya55 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaLayanan) {
                        canvas.drawText(line, 700,ya555, mTextPaint);
                        ya555 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pendaftaranEntityList.get(i).getTanggalBerobat(),850,480+550,myPaint);

                    for (String line: keluhan) {
                        canvas.drawText(line, 1080,ya5555, mTextPaint);
                        ya5555 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }
                else if (i==6){
                    int ya6 = 480+650;
                    int ya66 = 480+650;
                    int ya666 = 480+650;
                    int ya6666 = 480+650;

                    canvas.drawText(pendaftaranEntityList.get(i).getNik(),40,480+650,myPaint);

                    for (String line: pendaftaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 300,ya6, mTextPaint);
                        ya6 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaDokter) {
                        canvas.drawText(line, 500,ya66, mTextPaint);
                        ya66 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaLayanan) {
                        canvas.drawText(line, 700,ya666, mTextPaint);
                        ya666 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pendaftaranEntityList.get(i).getTanggalBerobat(),850,480+650,myPaint);

                    for (String line: keluhan) {
                        canvas.drawText(line, 1080,ya6666, mTextPaint);
                        ya6666 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }
                else if (i==7){
                    int ya7 = 480+750;
                    int ya77 = 480+750;
                    int ya777 = 480+750;
                    int ya7777 = 480+750;

                    canvas.drawText(pendaftaranEntityList.get(i).getNik(),40,480+750,myPaint);

                    for (String line: pendaftaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 300,ya7, mTextPaint);
                        ya7 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaDokter) {
                        canvas.drawText(line, 500,ya77, mTextPaint);
                        ya77 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    for (String line: namaLayanan) {
                        canvas.drawText(line, 700,ya777, mTextPaint);
                        ya777 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pendaftaranEntityList.get(i).getTanggalBerobat(),850,480+750,myPaint);

                    for (String line: keluhan) {
                        canvas.drawText(line, 1080,ya7777, mTextPaint);
                        ya7777 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }

            }



            footerTitle.setTextAlign(Paint.Align.CENTER);
            footerTitle.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
            footerTitle.setTextSize(25);

            footerName.setTextAlign(Paint.Align.CENTER);
            footerName.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
            footerName.setTextSize(25);


            String tanggalHariIni = LocalDate.now().toString(getString(R.string.format_all_day_date_month_year_full), DEFAULT_LOCALE_INDONESIA);
            canvas.drawText(tanggalHariIni,1000,1700,footerTitle);
            canvas.drawText(dataLoginEntitiys.get(0).getNama(),1000,1900,footerName);

            pdfDocument.finishPage(myPage);

            File file = new File(Environment.getExternalStorageDirectory(),"ReportPasienBerobat.pdf");
            try {
                pdfDocument.writeTo(new FileOutputStream(file));
                Toast.makeText(this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
                Uri uriPdfPath = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                Log.d("pdfPath", "" + uriPdfPath);

                // Start Intent to View PDF from the Installed Applications.
                Intent pdfOpenIntent = new Intent(Intent.ACTION_VIEW);
                pdfOpenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pdfOpenIntent.setClipData(ClipData.newRawUri("", uriPdfPath));
                pdfOpenIntent.setDataAndType(uriPdfPath, "application/pdf");
                pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |  Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                try {
                    startActivity(pdfOpenIntent);
                } catch (ActivityNotFoundException activityNotFoundException) {
                    Toast.makeText(this,"There is no app to load corresponding PDF",Toast.LENGTH_LONG).show();

                }
            }catch (IOException e){
                e.printStackTrace();
            }

            pdfDocument.close();
        });
    }

    @Override
    public void onShowDataPasien(List<DataPendaftaranEntity> dataPendaftaranEntityList, List<DataLoginEntitiy> dataLoginEntitiyList) {
        loadingDialog.dismiss();
        pendaftaranEntityList = dataPendaftaranEntityList;
        dataLoginEntitiys  = dataLoginEntitiyList;
    }

    @Override
    public void onShowDetailDataPasiem(List<UserEntity> userEntityList, List<DataLoginEntitiy> dataLoginEntitiys1) {

    }

    @Override
    public void onShowDataRekamMedisPasien(List<RekamMedisEntity> rekamMedisEntityList, List<DataLoginEntitiy> dataLoginEntitiys) {

    }

    @Override
    public void onShowDataPembayaran(List<PembayaranEntity> pembayaranEntities, List<DataLoginEntitiy> dataLoginEntitiys) {

    }
}
