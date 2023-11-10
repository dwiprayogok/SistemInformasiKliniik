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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportPembayaranActivity extends AppCompatActivity implements IReportDataPasien {
    private Button btnGeneratePDF;
    private Bitmap bitmap, scaleBmp;
    int pageWidth =1200;
    private ReportController reportController;
    private LoadingDialog loadingDialog;
    private List<PembayaranEntity> pembayaranEntityList = new ArrayList<>();
    private List<DataLoginEntitiy> dataLoginEntitiys;
    private static final Locale DEFAULT_LOCALE_INDONESIA = new Locale("id", "ID");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_pasien_berobat);
        loadingDialog = new LoadingDialog(ReportPembayaranActivity.this);
        reportController = new ReportController(this,loadingDialog);
        btnGeneratePDF =findViewById(R.id.btnGeneratePDF);
        loadingDialog.show();
        pembayaranEntityList = reportController.getALLDataPembayaran();
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
            mTextPaint.setTextSize(25);
            mTextPaint.setTextAlign(Paint.Align.CENTER);

            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
            PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);
            Canvas canvas = myPage.getCanvas();

            canvas.drawBitmap(scaleBmp,0,0,myPaint);

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            titlePaint.setTextSize(40);

            canvas.drawRect(20,250,pageWidth-20,250,linePaint);
            canvas.drawRect(20,400,pageWidth-20,400,linePaint);
            canvas.drawText("Laporan Data Pembayaran",pageWidth/2,350,titlePaint);

            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(2);
            canvas.drawRect(20,500,pageWidth-20,430,myPaint);

            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setStyle(Paint.Style.FILL);
            myPaint.setTextSize(25);

            String [] judulLayanan ;
            String [] judulBiayaObat ;
            String [] judulTotalBiaya ;
            int lay = 460;
            int obat = 460;
            int total = 460;
            judulLayanan = "Biaya Layanan".split(" ");
            judulBiayaObat = "Biaya Obat".split(" ");
            judulTotalBiaya = "Total Biaya".split(" ");

            canvas.drawText("Kode\nfaktur",40,480,myPaint);
            canvas.drawText("Nama",230,480,myPaint);
            canvas.drawText("Tanggal",350,480,myPaint);
            canvas.drawText("Pelayanan",500,480,myPaint);
            for (String line: judulLayanan) {
                canvas.drawText(line, 720,lay, mTextPaint);
                lay += mTextPaint.descent() - mTextPaint.ascent();
            }
            for (String line: judulBiayaObat) {
                canvas.drawText(line, 900,obat, mTextPaint);
                obat += mTextPaint.descent() - mTextPaint.ascent();
            }

            for (String line: judulTotalBiaya) {
                canvas.drawText(line, 1070,total, mTextPaint);
                total += mTextPaint.descent() - mTextPaint.ascent();
            }

            for (int i=0; i < pembayaranEntityList.size(); i++ ){
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                myPaint.setTextSize(20);


                String [] namaLayanan ;

                if (pembayaranEntityList.get(i).getNamaLayanan().contains("/")) {
                    namaLayanan = pembayaranEntityList.get(i).getNamaLayanan().split("/");
                } else {
                    namaLayanan = pembayaranEntityList.get(i).getNamaLayanan().split(" ");
                }

                if (i==0) {

                    int ya0 = 480+50;
                    int ya00 = 480+50;

                    canvas.drawText(pembayaranEntityList.get(i).getKodeFaktur(),40,550,myPaint);

                    for (String line: pembayaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 250,ya0, mTextPaint);
                        ya0 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pembayaranEntityList.get(i).getTanggalPeriksa(),350,550,myPaint);

                    for (String line: namaLayanan) {
                        canvas.drawText(line, 550,ya00, mTextPaint);
                        ya00 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaLayanan(),680,550,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaObat(),850,550,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaBerobat(),1020,550,myPaint);

                } else if (i==1) {

                    int ya1 = 480+150;
                    int ya11 = 480+150;

                    canvas.drawText(pembayaranEntityList.get(i).getKodeFaktur(),40,480+150,myPaint);
                    for (String line: pembayaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 250,ya1, mTextPaint);
                        ya1 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pembayaranEntityList.get(i).getTanggalPeriksa(),350,480+150,myPaint);

                    for (String line: namaLayanan) {
                        canvas.drawText(line, 550,ya11, mTextPaint);
                        ya11 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaLayanan(),680,480+150,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaObat(),850,480+150,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaBerobat(),1020,480+150,myPaint);

                } else if (i==2) {

                    int ya2 = 480+250;
                    int ya22 = 480+250;

                    canvas.drawText(pembayaranEntityList.get(i).getKodeFaktur(),40,480+250,myPaint);
                    for (String line: pembayaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 250,ya2, mTextPaint);
                        ya2 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pembayaranEntityList.get(i).getTanggalPeriksa(),350,480+250,myPaint);

                    for (String line: namaLayanan) {
                        canvas.drawText(line, 550,ya22, mTextPaint);
                        ya22 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaLayanan(),680,480+250,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaObat(),850,480+250,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaBerobat(),1020,480+250,myPaint);
                }
                else if (i==3) {
                    int ya3 = 480+350;
                    int ya33 = 480+350;

                    canvas.drawText(pembayaranEntityList.get(i).getKodeFaktur(),40,480+350,myPaint);
                    for (String line: pembayaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 250,ya3, mTextPaint);
                        ya3 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pembayaranEntityList.get(i).getTanggalPeriksa(),350,480+350,myPaint);

                    for (String line: namaLayanan) {
                        canvas.drawText(line, 550,ya33, mTextPaint);
                        ya33 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaLayanan(),680,480+350,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaObat(),850,480+350,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaBerobat(),1020,480+350,myPaint);
                }
                else if (i==4) {

                    int ya4 = 480+450;
                    int ya44 = 480+450;

                    canvas.drawText(pembayaranEntityList.get(i).getKodeFaktur(),40,480+450,myPaint);
                    for (String line: pembayaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 250,ya4, mTextPaint);
                        ya4 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pembayaranEntityList.get(i).getTanggalPeriksa(),350,480+450,myPaint);

                    for (String line: namaLayanan) {
                        canvas.drawText(line, 550,ya44, mTextPaint);
                        ya44 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaLayanan(),680,480+450,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaObat(),850,480+450,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaBerobat(),1020,480+450,myPaint);
                }
                else if (i==5) {
                    int ya5 = 480+550;
                    int ya55 = 480+550;

                    canvas.drawText(pembayaranEntityList.get(i).getKodeFaktur(),40,480+550,myPaint);
                    for (String line: pembayaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 250,ya5, mTextPaint);
                        ya5 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pembayaranEntityList.get(i).getTanggalPeriksa(),350,480+550,myPaint);

                    for (String line: namaLayanan) {
                        canvas.drawText(line, 550,ya55, mTextPaint);
                        ya55 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaLayanan(),680,480+550,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaObat(),850,480+550,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaBerobat(),1020,480+550,myPaint);
                }
                else if (i==6) {
                    int ya6 = 480+650;
                    int ya66 = 480+650;

                    canvas.drawText(pembayaranEntityList.get(i).getKodeFaktur(),40,480+650,myPaint);
                    for (String line: pembayaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 250,ya6, mTextPaint);
                        ya6 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pembayaranEntityList.get(i).getTanggalPeriksa(),350,480+650,myPaint);

                    for (String line: namaLayanan) {
                        canvas.drawText(line, 550,ya66, mTextPaint);
                        ya66 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaLayanan(),680,480+650,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaObat(),850,480+650,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaBerobat(),1020,480+650,myPaint);
                }
                else if (i==7) {
                    int ya7 = 480+750;
                    int ya77 = 480+750;

                    canvas.drawText(pembayaranEntityList.get(i).getKodeFaktur(),40,480+750,myPaint);
                    for (String line: pembayaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 250,ya7, mTextPaint);
                        ya7 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pembayaranEntityList.get(i).getTanggalPeriksa(),350,480+750,myPaint);

                    for (String line: namaLayanan) {
                        canvas.drawText(line, 550,ya77, mTextPaint);
                        ya77 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaLayanan(),680,480+750,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaObat(),850,480+750,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaBerobat(),1020,480+750,myPaint);
                }
                else if (i==8) {
                    int ya8 = 480+850;
                    int ya88 = 480+850;

                    canvas.drawText(pembayaranEntityList.get(i).getKodeFaktur(),40,480+850,myPaint);
                    for (String line: pembayaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 250,ya8, mTextPaint);
                        ya8 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pembayaranEntityList.get(i).getTanggalPeriksa(),350,480+850,myPaint);

                    for (String line: namaLayanan) {
                        canvas.drawText(line, 550,ya88, mTextPaint);
                        ya88 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaLayanan(),680,480+850,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaObat(),850,480+850,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaBerobat(),1020,480+850,myPaint);
                }
                else if (i==9) {
                    int ya9 = 480+950;
                    int ya99 = 480+950;

                    canvas.drawText(pembayaranEntityList.get(i).getKodeFaktur(),40,480+950,myPaint);
                    for (String line: pembayaranEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 250,ya9, mTextPaint);
                        ya9 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(pembayaranEntityList.get(i).getTanggalPeriksa(),350,480+950,myPaint);

                    for (String line: namaLayanan) {
                        canvas.drawText(line, 550,ya99, mTextPaint);
                        ya99 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaLayanan(),680,480+950,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaObat(),850,480+950,myPaint);
                    canvas.drawText(pembayaranEntityList.get(i).getTotalBiayaBerobat(),1020,480+950,myPaint);
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

            File file = new File(Environment.getExternalStorageDirectory(),"ReportPembayaran.pdf");
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
    public void onShowDataPasien(List<DataPendaftaranEntity> dataPendaftaranEntityList, List<DataLoginEntitiy> dataLoginEntitiys) {

    }

    @Override
    public void onShowDetailDataPasiem(List<UserEntity> userEntityList, List<DataLoginEntitiy> dataLoginEntitiys) {

    }

    @Override
    public void onShowDataRekamMedisPasien(List<RekamMedisEntity> rekamMedisEntityList, List<DataLoginEntitiy> dataLoginEntitiys) {

    }

    @Override
    public void onShowDataPembayaran(List<PembayaranEntity> pembayaranEntities, List<DataLoginEntitiy> dataLoginEntitiys1) {
        loadingDialog.dismiss();
        pembayaranEntityList = pembayaranEntities;
        dataLoginEntitiys  = dataLoginEntitiys1;
    }
}

