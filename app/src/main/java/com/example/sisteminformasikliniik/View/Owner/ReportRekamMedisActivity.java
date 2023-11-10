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
import java.util.List;
import java.util.Locale;

public class ReportRekamMedisActivity extends AppCompatActivity implements IReportDataPasien {
    private Button btnGeneratePDF;
    private Bitmap bitmap, scaleBmp;
    int pageWidth =1200;
    private ReportController reportController;
    private LoadingDialog loadingDialog;
    private List<RekamMedisEntity> rekamMedisEntityList;
    private List<DataLoginEntitiy> dataLoginEntitiys;
    private static final Locale DEFAULT_LOCALE_INDONESIA = new Locale("id", "ID");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_pasien_berobat);
        loadingDialog = new LoadingDialog(ReportRekamMedisActivity.this);
        reportController = new ReportController(this,loadingDialog);
        btnGeneratePDF =findViewById(R.id.btnGeneratePDF);

        loadingDialog.show();
        rekamMedisEntityList = reportController.getALLDataRekamMedisPasien("Done");
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

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            titlePaint.setTextSize(40);

            canvas.drawRect(20,250,pageWidth-20,250,linePaint);
            canvas.drawRect(20,400,pageWidth-20,400,linePaint);
            canvas.drawText("Laporan Data Rekam Medis",pageWidth/2,350,titlePaint);

            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(2);
            canvas.drawRect(20,500,pageWidth-20,450,myPaint);

            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setStyle(Paint.Style.FILL);
            myPaint.setTextSize(25);
            canvas.drawText("Kode Rekam",40,480,myPaint);
            canvas.drawText("Nama",200,480,myPaint);
            canvas.drawText("Umur",330,480,myPaint);
            canvas.drawText("Tanggal",430,480,myPaint);
            canvas.drawText("Anamnesa",630,480,myPaint);
            canvas.drawText("Therapi",830,480,myPaint);
            canvas.drawText("Keterangan",1000,480,myPaint);

            for (int i=0; i < rekamMedisEntityList.size(); i++ ){
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                myPaint.setTextSize(20);

                String [] anamnesa  ;
                String [] therapi  ;
                String [] keterangan;

                if (rekamMedisEntityList.get(i).getAnamnesa().contains(",")){
                    anamnesa = rekamMedisEntityList.get(i).getAnamnesa(). split(",");
                } else if (rekamMedisEntityList.get(i).getAnamnesa().contains("\n")){
                    anamnesa = rekamMedisEntityList.get(i).getAnamnesa().split("\n");
                } else {
                    anamnesa = rekamMedisEntityList.get(i).getAnamnesa().split(" ");
                }

                if (rekamMedisEntityList.get(i).getTherapi().contains(",")){
                    therapi = rekamMedisEntityList.get(i).getTherapi().split(",");
                } else if (rekamMedisEntityList.get(i).getTherapi().contains("\n")){
                    therapi = rekamMedisEntityList.get(i).getTherapi().split("\n");
                } else {
                    therapi = rekamMedisEntityList.get(i).getTherapi().split(" ");
                }

                 if (rekamMedisEntityList.get(i).getKeterangan().contains(",")){
                    keterangan = rekamMedisEntityList.get(i).getKeterangan().split(",");
                } else {
                    keterangan = rekamMedisEntityList.get(i).getKeterangan().split(" ");
                }


                if (i==0) {

                    int ya0 = 480+50;
                    int ya00 = 480+50;
                    int ya000 = 480+50;
                    int ya0000 = 480+50;

                    canvas.drawText(rekamMedisEntityList.get(i).getKodeRekam(),40,550,myPaint);

                    for (String line: rekamMedisEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 230,ya0, mTextPaint);
                        ya0 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(rekamMedisEntityList.get(i).getUmur(),330,550,myPaint);
                    canvas.drawText(rekamMedisEntityList.get(i).getTanggalPeriksa(),430,550,myPaint);

                    for (String line: anamnesa) {

                        canvas.drawText(line, 680,ya00, mTextPaint);
                        ya00 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: therapi) {
                        canvas.drawText(line, 860,ya000, mTextPaint);
                        ya000 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: keterangan) {
                        canvas.drawText(line, 1050,ya0000, mTextPaint);
                        ya0000 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                }
                else if (i==1) {

                    int ya1 = 480+150;
                    int ya11 = 480+150;
                    int ya111 = 480+150;
                    int ya1111 = 480+150;

                    canvas.drawText(rekamMedisEntityList.get(i).getKodeRekam(),40,480+150,myPaint);

                    for (String line: rekamMedisEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 230,ya1, mTextPaint);
                        ya1 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(rekamMedisEntityList.get(i).getUmur(),330,480+150,myPaint);
                    canvas.drawText(rekamMedisEntityList.get(i).getTanggalPeriksa(),430,480+150,myPaint);

                    for (String line: anamnesa) {
                        if (line.contains("dan")) {
                            String [] detail = line.replace("dan"," ").split(":");
                            for (String lineDetail: detail) {
                                canvas.drawText(lineDetail, 680,ya11, mTextPaint);
                                ya11 += mTextPaint.descent() - mTextPaint.ascent();
                            }
                        } else {
                            canvas.drawText(line, 680,ya11, mTextPaint);
                            ya11 += mTextPaint.descent() - mTextPaint.ascent();
                        }

                    }

                    for (String line: therapi) {
                        canvas.drawText(line, 860,ya111, mTextPaint);
                        ya111 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: keterangan) {
                        canvas.drawText(line, 1050,ya1111, mTextPaint);
                        ya1111 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }
                else if (i==2) {
                    int ya2 = 480+250;
                    int ya22 = 480+250;
                    int ya222 = 480+250;
                    int ya2222 = 480+250;

                    canvas.drawText(rekamMedisEntityList.get(i).getKodeRekam(),40,480+250,myPaint);

                    for (String line: rekamMedisEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 230,ya2, mTextPaint);
                        ya2 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(rekamMedisEntityList.get(i).getUmur(),330,480+250,myPaint);
                    canvas.drawText(rekamMedisEntityList.get(i).getTanggalPeriksa(),430,480+250,myPaint);

                    for (String line: anamnesa) {
                        canvas.drawText(line, 680,ya22, mTextPaint);
                        ya22 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: therapi) {
                        canvas.drawText(line, 860,ya222, mTextPaint);
                        ya222 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: keterangan) {
                        canvas.drawText(line, 1050,ya2222, mTextPaint);
                        ya2222 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }
                else if (i==3) {

                    int ya3 = 480+350;
                    int ya33 = 480+350;
                    int ya333 = 480+350;
                    int ya3333 = 480+350;

                    canvas.drawText(rekamMedisEntityList.get(i).getKodeRekam(),40,480+350,myPaint);

                    for (String line: rekamMedisEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 230,ya3, mTextPaint);
                        ya3 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(rekamMedisEntityList.get(i).getUmur(),330,480+350,myPaint);
                    canvas.drawText(rekamMedisEntityList.get(i).getTanggalPeriksa(),430,480+350,myPaint);

                    for (String line: anamnesa) {
                        canvas.drawText(line, 680,ya33, mTextPaint);
                        ya33 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: therapi) {
                        canvas.drawText(line, 860 ,ya333, mTextPaint);
                        ya333 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: keterangan) {
                        canvas.drawText(line, 1050,ya3333, mTextPaint);
                        ya3333 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }

                else if (i==4) {

                    int ya4 = 480+450;
                    int ya44 = 480+450;
                    int ya444 = 480+450;
                    int ya4444 = 480+450;

                    canvas.drawText(rekamMedisEntityList.get(i).getKodeRekam(),40,480+450,myPaint);

                    for (String line: rekamMedisEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 230,ya4, mTextPaint);
                        ya4 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(rekamMedisEntityList.get(i).getUmur(),330,480+450,myPaint);
                    canvas.drawText(rekamMedisEntityList.get(i).getTanggalPeriksa(),430,480+450,myPaint);

                    for (String line: anamnesa) {
                        canvas.drawText(line, 680,ya44, mTextPaint);
                        ya44 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: therapi) {
                        canvas.drawText(line, 860 ,ya444, mTextPaint);
                        ya444 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: keterangan) {
                        canvas.drawText(line, 1050,ya4444, mTextPaint);
                        ya4444 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }
                else if (i==5) {

                    int ya5 = 480+550;
                    int ya55 = 480+550;
                    int ya555 = 480+550;
                    int ya5555 = 480+550;

                    canvas.drawText(rekamMedisEntityList.get(i).getKodeRekam(),40,480+550,myPaint);

                    for (String line: rekamMedisEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 230,ya5, mTextPaint);
                        ya5 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(rekamMedisEntityList.get(i).getUmur(),330,480+550,myPaint);
                    canvas.drawText(rekamMedisEntityList.get(i).getTanggalPeriksa(),430,480+550,myPaint);

                    for (String line: anamnesa) {
                        canvas.drawText(line, 680,ya55, mTextPaint);
                        ya55 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: therapi) {
                        canvas.drawText(line, 860 ,ya555, mTextPaint);
                        ya555 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: keterangan) {
                        canvas.drawText(line, 1050,ya5555, mTextPaint);
                        ya5555 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }
                else if (i==6) {

                    int ya6 = 480+650;
                    int ya66 = 480+650;
                    int ya666 = 480+650;
                    int ya6666 = 480+650;

                    canvas.drawText(rekamMedisEntityList.get(i).getKodeRekam(),40,480+650,myPaint);

                    for (String line: rekamMedisEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 230,ya6, mTextPaint);
                        ya6 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(rekamMedisEntityList.get(i).getUmur(),330,480+650,myPaint);
                    canvas.drawText(rekamMedisEntityList.get(i).getTanggalPeriksa(),430,480+650,myPaint);

                    for (String line: anamnesa) {
                        canvas.drawText(line, 680,ya66, mTextPaint);
                        ya66 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: therapi) {
                        canvas.drawText(line, 860 ,ya666, mTextPaint);
                        ya666 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: keterangan) {
                        canvas.drawText(line, 1050,ya6666, mTextPaint);
                        ya6666 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }
                else if (i==7) {

                    int ya7 = 480+750;
                    int ya77 = 480+750;
                    int ya777 = 480+750;
                    int ya7777 = 480+750;

                    canvas.drawText(rekamMedisEntityList.get(i).getKodeRekam(),40,480+750,myPaint);

                    for (String line: rekamMedisEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 230,ya7, mTextPaint);
                        ya7 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(rekamMedisEntityList.get(i).getUmur(),330,480+750,myPaint);
                    canvas.drawText(rekamMedisEntityList.get(i).getTanggalPeriksa(),430,480+750,myPaint);

                    for (String line: anamnesa) {
                        canvas.drawText(line, 680,ya77, mTextPaint);
                        ya77 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: therapi) {
                        canvas.drawText(line, 860 ,ya777, mTextPaint);
                        ya777 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: keterangan) {
                        canvas.drawText(line, 1050,ya7777, mTextPaint);
                        ya7777 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }
                else if (i==8) {

                    int ya8 = 480+850;
                    int ya88 = 480+850;
                    int ya888 = 480+850;
                    int ya8888 = 480+850;

                    canvas.drawText(rekamMedisEntityList.get(i).getKodeRekam(),40,480+850,myPaint);

                    for (String line: rekamMedisEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 230,ya8, mTextPaint);
                        ya8 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(rekamMedisEntityList.get(i).getUmur(),330,480+850,myPaint);
                    canvas.drawText(rekamMedisEntityList.get(i).getTanggalPeriksa(),430,480+850,myPaint);

                    for (String line: anamnesa) {
                        canvas.drawText(line, 680,ya88, mTextPaint);
                        ya88 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: therapi) {
                        canvas.drawText(line, 860 ,ya888, mTextPaint);
                        ya888 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: keterangan) {
                        canvas.drawText(line, 1050,ya8888, mTextPaint);
                        ya8888 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                }
                else if (i==9) {

                    int ya9 = 480+950;
                    int ya99 = 480+950;
                    int ya999 = 480+950;
                    int ya9999 = 480+950;

                    canvas.drawText(rekamMedisEntityList.get(i).getKodeRekam(),40,480+950,myPaint);

                    for (String line: rekamMedisEntityList.get(i).getNamaPasien().split(" ")) {
                        canvas.drawText(line, 230,ya9, mTextPaint);
                        ya9 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(rekamMedisEntityList.get(i).getUmur(),330,480+950,myPaint);
                    canvas.drawText(rekamMedisEntityList.get(i).getTanggalPeriksa(),430,480+950,myPaint);

                    for (String line: anamnesa) {
                        canvas.drawText(line, 680,ya99, mTextPaint);
                        ya99 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: therapi) {
                        canvas.drawText(line, 860 ,ya999, mTextPaint);
                        ya999 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    for (String line: keterangan) {
                        canvas.drawText(line, 1050,ya9999, mTextPaint);
                        ya9999 += mTextPaint.descent() - mTextPaint.ascent();
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

            File file = new File(Environment.getExternalStorageDirectory(),"ReportRekamMedisPasien.pdf");
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
    public void onShowDataRekamMedisPasien(List<RekamMedisEntity> rekamMedisEntities, List<DataLoginEntitiy> dataLoginEntitiys1) {
        loadingDialog.dismiss();
        rekamMedisEntityList = rekamMedisEntities;
        dataLoginEntitiys = dataLoginEntitiys1;

    }

    @Override
    public void onShowDataPembayaran(List<PembayaranEntity> pembayaranEntities, List<DataLoginEntitiy> dataLoginEntitiys) {

    }
}
