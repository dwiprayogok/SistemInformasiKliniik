package com.example.sisteminformasikliniik.transfer;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.example.sisteminformasikliniik.transfer.interfaces.DaftarBerobatServices;
import com.example.sisteminformasikliniik.transfer.interfaces.IRegistrasiService;
import com.example.sisteminformasikliniik.transfer.interfaces.MasterDataService;
import com.example.sisteminformasikliniik.transfer.interfaces.ReportServices;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private Retrofit retrofit;
    private IRegistrasiService registrasiService;
    private MasterDataService masterDataService;
    private DaftarBerobatServices daftarBerobatServices;
    private ReportServices reportServices;
    private static ApiClient instance;
    private Context mContext;
    private OkHttpClient okHttpClient;

    private static final ConnectionPool connectionPool = new ConnectionPool(5, 3, TimeUnit.MINUTES);
    private static final int REQUEST_TIMEOUT_IN_SECOND = 60;
    private static final String EMPTY_ACCESS_TOKEN_ERROR_MESSAGE = "Failed to retrieve access token, please re-login.";
    private static final String NULL_ACCESS_TOKEN_INTERCEPTOR_ERROR_MESSAGE = "Access Token Interceptor is NULL";
    private static final String NULL_NO_INTERNET_CONNECTION_INTERCEPTOR_ERROR_MESSAGE = "No Internet Connection Interceptor is NULL";
    private static final String NULL_TIMEOUT_INTERCEPTOR_ERROR_MESSAGE = "Timeout Interceptor is NULL";
    private static final String NULL_SERVER_ERROR_INTERCEPTOR_ERROR_MESSAGE = "Server Error Interceptor is NULL";
    private static final String NULL_SERVER_ERROR_INTERCEPTOR_INCENTIVE_ERROR_MESSAGE = "Server Error Incentive Interceptor is NULL";


    private ConnectivityManager connectivityManager;
    private SharedPreferences sharedPreferences;
    private boolean isInitialized = false;
    private ApiClient(){

    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public void initialize(Context context) throws Exception {
        mContext = context;
        sharedPreferences = getDefaultSharedPreferences(context);
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        okHttpClient = createOkHttpClient();
        flushRetrofitAndServices();
        getiRegistrasiService();
        getMasterDataService();
        getDaftarBerobatServices();
        this.isInitialized = true;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    private Retrofit getRetrofit() {
//        if (okHttpClient == null) {
//            okHttpClient = createOkHttpClient();
//        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://kliniknurrizmedika.online/")
                    //.client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(GsonProvider.getInstance().getGson()))
                    //.addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


    public ReportServices getReportServices(){
        if (reportServices == null) {
            reportServices = getRetrofit().create(ReportServices.class);
        }
        return reportServices;
    }


    public IRegistrasiService getiRegistrasiService(){
        if (registrasiService == null) {
            registrasiService = getRetrofit().create(IRegistrasiService.class);
        }
        return registrasiService;
    }

    public MasterDataService getMasterDataService(){
        if (masterDataService == null) {
            masterDataService = getRetrofit().create(MasterDataService.class);
        }
        return masterDataService;
    }

    public DaftarBerobatServices getDaftarBerobatServices(){
        if (daftarBerobatServices == null) {
            daftarBerobatServices = getRetrofit().create(DaftarBerobatServices.class);
        }
        return daftarBerobatServices;
    }


    public void flushRetrofitAndServices() {
        retrofit = null;
        registrasiService = null;
        masterDataService = null;
        daftarBerobatServices = null;
        reportServices = null;
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectionPool(connectionPool)
                .connectTimeout(REQUEST_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT_IN_SECOND, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return builder.build();
    }



}
