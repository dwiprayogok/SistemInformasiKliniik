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

public class ReportDataPasienActivity extends AppCompatActivity implements IReportDataPasien {
    private Button btnGeneratePDF;
    private Bitmap bitmap, scaleBmp;
    int pageWidth =1200;
    private List<UserEntity> userEntityList;
    private List<DataLoginEntitiy> dataLoginEntitiys;
    private ReportController reportController;
    private LoadingDialog loadingDialog;
    private static final Locale DEFAULT_LOCALE_INDONESIA = new Locale("id", "ID");
    private float posisiY =550 ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_pasien_berobat);
        loadingDialog = new LoadingDialog(ReportDataPasienActivity.this);
        reportController = new ReportController(this,loadingDialog);
        btnGeneratePDF =findViewById(R.id.btnGeneratePDF);
        loadingDialog.show();
        userEntityList = reportController.getAllDataPasien();
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
            int y = 550;
            int y2 = 550;
            int j = 0;
            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200,2010,1).create();

            PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);
            Canvas canvas = myPage.getCanvas();

            canvas.drawBitmap(scaleBmp,0,0,myPaint);

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            titlePaint.setTextSize(40);


            canvas.drawRect(20,250,pageWidth-20,250,linePaint);
            canvas.drawRect(20,400,pageWidth-20,400,linePaint);
            canvas.drawText("Laporan Data Pasien",pageWidth/2,350,titlePaint);

            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(2);
            canvas.drawRect(20,500,pageWidth-20,450,myPaint);

            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setStyle(Paint.Style.FILL);
            myPaint.setTextSize(25);
            canvas.drawText("NIK",40,480,myPaint);
            canvas.drawText("Nama",280,480,myPaint);
            canvas.drawText("Jenis Kelamin",450,480,myPaint);
            canvas.drawText("Alamat",780,480,myPaint);
            canvas.drawText("No.Hp",980,480,myPaint);


            for (int i=0; i < userEntityList.size(); i++ ){
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                myPaint.setTextSize(20);
                if (i==0) {

                    canvas.drawText(userEntityList.get(i).getNik(),40,posisiY,myPaint);

                    for (String line: userEntityList.get(i).getNama().split(" ")) {
                        canvas.drawText(line, 300,y, mTextPaint);
                        y += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getGender(),480,posisiY,myPaint);

                    for (String line: userEntityList.get(i).getAlamat().split(" ")) {
                        canvas.drawText(line, 800,y2, mTextPaint);
                        y2 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getNoHp(),950,posisiY,myPaint);

                } else if (i==1) {

                    int ya = 550+100;

                    int ya2 = 550+100;

                    canvas.drawText(userEntityList.get(i).getNik(),40,posisiY+100,myPaint);

                    for (String line: userEntityList.get(i).getNama().split(" ")) {
                        canvas.drawText(line, 300,ya, mTextPaint);
                        ya += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getGender(),480,posisiY+100,myPaint);


                    for (String line: userEntityList.get(i).getAlamat().split(" ")) {
                        canvas.drawText(line, 800,ya2, mTextPaint);
                        ya2 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getNoHp(),950,posisiY+100,myPaint);
                }
                else if (i==2) {

                    int ya22 = 550+200;

                    int ya222 = 550+200;

                    canvas.drawText(userEntityList.get(i).getNik(),40,posisiY+200,myPaint);


                    for (String line: userEntityList.get(i).getNama().split(" ")) {
                        canvas.drawText(line, 300,ya22, mTextPaint);
                        ya22 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getGender(),480,posisiY+200,myPaint);


                    for (String line: userEntityList.get(i).getAlamat().split(" ")) {
                        canvas.drawText(line, 800,ya222, mTextPaint);
                        ya222 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getNoHp(),950,posisiY+200,myPaint);
                }
                else if (i==3) {

                    int ya33 = 550+300;

                    int ya333 = 550+300;

                    canvas.drawText(userEntityList.get(i).getNik(),40,posisiY+300,myPaint);

                    for (String line: userEntityList.get(i).getNama().split(" ")) {
                        canvas.drawText(line, 300,ya33, mTextPaint);
                        ya33 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getGender(),480,posisiY+300,myPaint);

                    for (String line: userEntityList.get(i).getAlamat().split(" ")) {
                        canvas.drawText(line, 800,ya333, mTextPaint);
                        ya333 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getNoHp(),950,posisiY+300,myPaint);

                }
                else if (i==4) {
                    int ya44 = 550+400;

                    int ya444 = 550+400;

                    canvas.drawText(userEntityList.get(i).getNik(),40,posisiY+400,myPaint);

                    for (String line: userEntityList.get(i).getNama().split(" ")) {
                        canvas.drawText(line, 300,ya44, mTextPaint);
                        ya44 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getGender(),480,posisiY+400,myPaint);

                    for (String line: userEntityList.get(i).getAlamat().split(" ")) {
                        canvas.drawText(line, 800,ya444, mTextPaint);
                        ya444 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getNoHp(),950,posisiY+400,myPaint);
                }
                else if (i==5) {

                    int ya55= 550+500;

                    int ya555 = 550+500;

                    canvas.drawText(userEntityList.get(i).getNik(),40,posisiY+500,myPaint);

                    for (String line: userEntityList.get(i).getNama().split(" ")) {
                        canvas.drawText(line, 300,ya55, mTextPaint);
                        ya55 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getGender(),480,posisiY+500,myPaint);

                    for (String line: userEntityList.get(i).getAlamat().split(" ")) {
                        canvas.drawText(line, 800,ya555, mTextPaint);
                        ya555 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getNoHp(),950,posisiY+500,myPaint);
                }
                else if (i==6) {

                    int ya66= 550+600;

                    int ya666 = 550+600;

                    canvas.drawText(userEntityList.get(i).getNik(),40,posisiY+600,myPaint);

                    for (String line: userEntityList.get(i).getNama().split(" ")) {
                        canvas.drawText(line, 300,ya66, mTextPaint);
                        ya66 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getGender(),480,posisiY+600,myPaint);

                    for (String line: userEntityList.get(i).getAlamat().split(" ")) {
                        canvas.drawText(line, 800,ya666, mTextPaint);
                        ya666 += mTextPaint.descent() - mTextPaint.ascent();
                    }

                    canvas.drawText(userEntityList.get(i).getNoHp(),950,posisiY+600,myPaint);
                }
                else if (i==7) {

                    int ya77= 550+700;

                    int ya777 = 550+700;

                    canvas.drawText(userEntityList.get(i).getNik(),40,posisiY+700,myPaint);
                    for (String line: userEntityList.get(i).getNama().split(" ")) {
                        canvas.drawText(line, 300,ya77, mTextPaint);
                        ya77 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(userEntityList.get(i).getGender(),480,posisiY+700,myPaint);
                    for (String line: userEntityList.get(i).getAlamat().split(" ")) {
                        canvas.drawText(line, 800,ya777, mTextPaint);
                        ya777 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(userEntityList.get(i).getNoHp(),950,posisiY+700,myPaint);
                }
                else if (i==8) {
                    int ya88= 550+800;

                    int ya888 = 550+800;
                    canvas.drawText(userEntityList.get(i).getNik(),40,posisiY+800,myPaint);
                    for (String line: userEntityList.get(i).getNama().split(" ")) {
                        canvas.drawText(line, 300,ya88, mTextPaint);
                        ya88 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(userEntityList.get(i).getGender(),480,posisiY+800,myPaint);
                    for (String line: userEntityList.get(i).getAlamat().split(" ")) {
                        canvas.drawText(line, 800,ya888, mTextPaint);
                        ya888 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(userEntityList.get(i).getNoHp(),950,posisiY+800,myPaint);
                }
                else if (i==9) {
                    int ya99= 550+900;

                    int ya999 = 550+900;

                    canvas.drawText(userEntityList.get(i).getNik(),40,posisiY+900,myPaint);
                    for (String line: userEntityList.get(i).getNama().split(" ")) {
                        canvas.drawText(line, 300,ya99, mTextPaint);
                        ya99 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(userEntityList.get(i).getGender(),480,posisiY+900,myPaint);
                    for (String line: userEntityList.get(i).getAlamat().split(" ")) {
                        canvas.drawText(line, 800,ya999, mTextPaint);
                        ya999 += mTextPaint.descent() - mTextPaint.ascent();
                    }
                    canvas.drawText(userEntityList.get(i).getNoHp(),950,posisiY+900,myPaint);
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

            File file = new File(Environment.getExternalStorageDirectory(),"ReportPasien.pdf");
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
    public void onShowDetailDataPasiem(List<UserEntity> entityList, List<DataLoginEntitiy> dataLoginEntitiys1) {
        loadingDialog.dismiss();
        userEntityList = entityList;
        dataLoginEntitiys  = dataLoginEntitiys1;
    }

    @Override
    public void onShowDataPasien(List<DataPendaftaranEntity> dataPendaftaranEntityList, List<DataLoginEntitiy> dataLoginEntitiys) {

    }

    @Override
    public void onShowDataRekamMedisPasien(List<RekamMedisEntity> rekamMedisEntityList, List<DataLoginEntitiy> dataLoginEntitiys) {

    }

    @Override
    public void onShowDataPembayaran(List<PembayaranEntity> pembayaranEntities, List<DataLoginEntitiy> dataLoginEntitiys) {

    }
}
