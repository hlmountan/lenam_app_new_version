package com.paditech.mvpbase.screen.detail;


import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.DashPathEffect;

import android.graphics.PorterDuff;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseActivity;
import com.paditech.mvpbase.common.base.BaseDialog;
import com.paditech.mvpbase.common.dialog.MessageDialog;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.AppPriceHistory;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;
import com.paditech.mvpbase.common.utils.BlurBuilder;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.common.view.FadeToolbarScrollView;
import com.paditech.mvpbase.screen.home.HomeListAppAdapter;
import com.paditech.mvpbase.screen.home.HomeViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;


/**
 * Created by hung on 1/5/2018.
 */

public class DetailActivity extends MVPActivity<DetailContact.PresenterViewOps> implements DetailContact.ViewOps {

    List<Entry> entries = new ArrayList<Entry>();
    private ArrayList<ArrayList<String>> screenShot = null;
    ProgressDialog mProgressDialog;
    File file;
    ArrayList<ArrayList<String>> priceHistory = null;
    ArrayList<ArrayList<String>> priceHistory_trans_date = null;
    HomeListAppAdapter mHomeListAppAdapter = new HomeListAppAdapter(this);
    DetailViewPagerAdapter mDetailViewPagerAdapter;
    private List<AppModel.SourceBean> mList = new ArrayList<>();
    String title;
    @BindView(R.id.recycler_view_relate_app)
    RecyclerView recycler_view_relate_app;
    @BindView(R.id.tv_title)
    TextView textView_title;
    @BindView(R.id.tv_offerby)
    TextView tv_offerby;
    @BindView(R.id.tv_install_number)
    TextView tv_install_number;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_description)
    TextView tv_description;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn_install_app)
    Button btn_install_app;
    @BindView(R.id.lc_price_history)
    LineChart chart;
    @BindView(R.id.view_page_screenshot)
    ViewPager view_page_screenshot;
    @BindView(R.id.gp_info)
    TextView tv_gp_info;
    private boolean checkPriceHistory = true;
    @BindView(R.id.progressBar_chart)
    ProgressBar progressBar_chart;
    @BindView(R.id.img_avar)
    ImageView imgAvatar;
    @BindView(R.id.card_image)
    View avatarLayout;

    @BindView(R.id.tv_title_scroll)
    TextView tv_title_scroll;
    @BindView(R.id.tv_price_history)
    TextView tv_price_history;
    @BindView(R.id.scrollView)
    FadeToolbarScrollView scrollView;
    @BindView(R.id.info_layout)
    View infoLayout;
    @BindView(R.id.toolbar)
    View titleLayout;
    @BindView(R.id.img_cover)
    ImageView imgCover;
    boolean showdes = false;
    public int des_lines;
    private String urlInstall;
    private boolean animing;
    Integer isHistory;
    @Override
    protected int getContentView() {
        return R.layout.act_detail_new;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // use to do transition throw each screen
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        avatarLayout.setTransitionName("image_avatar");

        // install app apk
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return DetailPresenter.class;
    }

    @Override
    protected void onRestart() {
        progressBar_chart.setVisibility(View.GONE);
        super.onRestart();
    }

    @Override
    protected void initView() {
        // be called  when click on viewpager

        recycler_view_relate_app.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_relate_app.setAdapter(mHomeListAppAdapter);
        //btn  way back to the previous screen
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();

            }
        });
        // when click on text description this text will show all it have
        tv_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int currSize = des_lines - 3;
                Animation animation;
                if (showdes) {
                    animation = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            super.applyTransformation(interpolatedTime, t);
                            tv_description.setLines((int) (currSize * (1 - interpolatedTime)) + 3);
                            showdes = false;
                            tv_description.requestLayout();
                        }
                    };
                } else {
                    animation = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            super.applyTransformation(interpolatedTime, t);
                            tv_description.setLines((int) (currSize * (interpolatedTime)) + 3);
                            showdes = true;
                            tv_description.requestLayout();
                        }
                    };
                }

                animation.setDuration(300);
                animation.setInterpolator(new DecelerateInterpolator());
                tv_description.clearAnimation();
                tv_description.startAnimation(animation);
            }
        });

        // download apk file
        btn_install_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_install_app.getText().equals(getResources().getString(R.string.download_apk))) {
                    downloadApkFile(getUrlInstall());
                } else {
                    Log.e("", "You need buy App first");
                }


            }
        });

        //setup fade when scrolling ScrollView
        scrollView.setScrollViewCallbacks(new FadeToolbarScrollView.ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, int oldScollY, boolean firstScroll, boolean dragging) {
                int baseColor = getResources().getColor(R.color.colorPrimary);
                float alpha = Math.min(1, (float) scrollY / infoLayout.getMeasuredHeight());
                int color = CommonUtil.getColorWithAlpha(alpha, baseColor);
                int color_2 = CommonUtil.getColorWithAlpha(1 - alpha, baseColor);
                titleLayout.setBackgroundColor(color);
                //infoLayout.setTranslationY(scrollY / 2);
                //btn_back.setColorFilter(color_2, PorterDuff.Mode.SRC_ATOP);
                tv_title_scroll.setAlpha(alpha);
            }

            @Override
            public void onDownMotionEvent() {

            }

            @Override
            public void onUpOrCancelMotionEvent(FadeToolbarScrollView.ScrollState scrollState) {

            }
        });
    }

    private void downloadApkFile(String url) {

        mProgressDialog = new ProgressDialog(DetailActivity.this);
        mProgressDialog.setMessage("Download thread are running . . .");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        //check permission to access external storage
        if (ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // guide to allow app permission to access the external storage or ask the opinion if this is the first time
            if (!ActivityCompat.shouldShowRequestPermissionRationale(DetailActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(DetailActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            } else {
                // show dialog yeu cau vao setting cua app de cho phep quyen
                MessageDialog messageDialog = MessageDialog.newInstance(false, getString(R.string.mess_permission_storage),
                        getString(R.string.ok), getString(R.string.cancel));
                messageDialog.setmOnPositiveClickListener(new BaseDialog.OnPositiveClickListener() {
                    @Override
                    public void onPositiveClick() {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 0);
                    }
                });
                messageDialog.show(getSupportFragmentManager(), messageDialog.getClass().getSimpleName());
            }
            return;
        }

        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(DetailActivity.this);
        downloadTask.execute(url);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetAppEvent(AppModel.SourceBean app) {

        if (!getIntent().getBooleanExtra("is_cover", false))
            ImageUtil.loadImage(DetailActivity.this, app.getCover(), imgAvatar, R.drawable.events_placeholder, R.drawable.image_placeholder_500x500);
        // setup BlurView
        BlurBuilder.blur(this, app.getCover(), new BlurBuilder.GetBlurBitmapListener() {
            @Override
            public void setBitmap(final Bitmap bitmap) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imgCover.setImageBitmap(bitmap);
                    }
                });
            }
        });

        if (app.getAll_price() != null){
            isHistory=0;
            getPresenter().cURLFromApi(app.getAppid(),0);
        }else {
            isHistory=1;
            getPresenter().cURLFromApi(app.getAppid(),1);

        }

//        if (app.getTitle() != null) tv_title_scroll.setText(app.getTitle());
//        getAppHistory("https://appsxyz.com/api/apk/price_history/?appid=" + app.getAppid());
//        setupAppInfo("https://appsxyz.com/api/apk/detailApp/?appid=" + app.getAppid());
//        if (app.getInstalls() != 0) {
//            tv_install_number.setText("" + NumberFormat.getInstance().format(app.getInstalls()));
//        }
    }




    private void setUpViewpager(ArrayList<ArrayList<String>> screenShot) {
        for (ArrayList<String> a : screenShot) {
            AppModel.SourceBean appModel = new AppModel.SourceBean();
            appModel.setCover(a.get(2));
            mList.add(appModel);
        }
        tv_price_history.setText("Android");
        view_page_screenshot.setVisibility(View.VISIBLE);
        progressBar_chart.setVisibility(View.GONE);

        mDetailViewPagerAdapter = new DetailViewPagerAdapter();
        mDetailViewPagerAdapter.setList(mList);
//        mDetailViewPagerAdapter.setActivityName("DETAIL");
        view_page_screenshot.setAdapter(mDetailViewPagerAdapter);
    }

    // ham count text line  su dung de showmore description
    private void linesCount(String s) {
        int count = 0;
        int position = 0;
        while ((position = s.indexOf("<br>", position)) != -1) {
            count++;
            position++; // Skip this occurrence!
        }
        if (count != 0)
            des_lines = count;
        else
            des_lines = 3;
    }

    private void getRelateData(String url) {
        APIClient.getInstance().execGet(url, null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mHomeListAppAdapter.setmList(result.getResult());
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void getAppHistory(String url) {
        progressBar_chart.setVisibility(View.VISIBLE);
        APIClient.getInstance().execGet(url, null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {
            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                try {
                    final AppPriceHistory appPriceHistory = new Gson().fromJson(response, AppPriceHistory.class);
                    if (appPriceHistory != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                priceHistory = appPriceHistory.getPriceHistory();
                                lineChartData();
                            }
                        });

                    } else {
                        checkPriceHistory = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // failed get data
                    checkPriceHistory = false;
                    System.out.println(e);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!checkPriceHistory) {
                            chart.setVisibility(View.GONE);
                            view_page_screenshot.setVisibility(View.VISIBLE);

                            tv_price_history.setText("Android");
                        } else {
                            view_page_screenshot.setVisibility(View.GONE);
                            chart.setVisibility(View.VISIBLE);

                            tv_price_history.setText("Price History");
                        }
                        progressBar_chart.setVisibility(View.GONE);
                        tv_price_history.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
    }

    private void prepareData() {
        Integer count = 0;
        for (int i = priceHistory.size() - 1; i >= 0; i--) {
            count++;
            Float pri = Float.parseFloat(priceHistory.get(i).get(0));
            entries.add(new Entry(count, pri));
        }
    }

    private void lineChartData() {

        prepareData();
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setTouchEnabled(true);
        chart.setScaleEnabled(true);
        chart.setHighlightPerDragEnabled(true);
        chart.setPinchZoom(true);

//        chart.setViewPortOffsets(0, 0, 0, 0);
        chart.setVisibleXRangeMaximum(20);
        chart.setBackgroundColor(Color.rgb(104, 241, 175));
        chart.setDrawGridBackground(false);
//        chart.setMaxHighlightDistance(300);
        YAxis y = chart.getAxisLeft();
        y.setLabelCount(6, false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setAxisLineColor(Color.WHITE);

        chart.getAxisRight().setEnabled(false);

        DateAxisValueFormatter formatter = new DateAxisValueFormatter(priceHistory);
        XAxis x = chart.getXAxis();
        x.setLabelCount(4, false);
        x.setTextColor(Color.WHITE);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setAxisLineColor(Color.WHITE);
        x.setValueFormatter(formatter);
        // set maker view

        MyMakerView myMakerView = new MyMakerView(this, R.layout.custom_maker_view, priceHistory);

        myMakerView.setChartView(chart);
        chart.setMarker(myMakerView);


        LineDataSet dataSet;


        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            dataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(entries);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            dataSet = new LineDataSet(entries, "Google Play");

            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet.setCubicIntensity(0.2f);
            dataSet.setFillColor(Color.WHITE);
            dataSet.setLineWidth(1.8f);
            dataSet.setCircleRadius(4f);
//        dataSet.setCircleColor(Color.WHITE);
            dataSet.setHighLightColor(Color.rgb(244, 117, 117));
            dataSet.setColor(Color.WHITE);
            dataSet.setFillColor(Color.WHITE);
            dataSet.setFillAlpha(100);
            dataSet.setDrawFilled(true);


            dataSet.setFormLineWidth(1f);
            dataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            dataSet.setFormSize(15.f);


            dataSet.setDrawHorizontalHighlightIndicator(false);
            dataSet.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });


            LineData lineData = new LineData(dataSet);
            lineData.setValueTextSize(9f);
            lineData.setDrawValues(false);
            chart.setData(lineData);

        }


        chart.getLegend().setEnabled(false);
        chart.animateXY(2000, 3000);
        chart.invalidate();

    }

    @Override
    public void setAppInfo(final AppModel.SourceBean app) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isHistory != 0)
                        btn_install_app.setText("$" + app.getPrice());
                    else {
                        btn_install_app.setText("" + getResources().getString(R.string.download_apk));
                        setUrlInstall(app);
                    }

                    if (app.getTitle() != null) tv_title_scroll.setText(app.getTitle());
                    textView_title.setText(app.getTitle());

                    tv_offerby.setText(app.getOfferby());
                    tv_version.setText("Version: " + app.getVersion());
                    String des = app.getDescription();
                    System.out.println(app.getDescription());
                    linesCount(des);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tv_description.setText(Html.fromHtml(des, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        tv_description.setText(Html.fromHtml(des));
                    }
                    //set up screenshot data
                    screenShot = app.getScreenShot();
                    if (app.getScreenShot() != null){
                        setUpViewpager(app.getScreenShot());
                        System.out.println(app.getScreenShot());
                    }
//                    if(isHistory ==1){
//                        view_page_screenshot.setVisibility(View.GONE);
//                    }


                    //set title for download api
                    title = app.getAppid().replace(" ", "_").replace(".", "_");
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });

    }

    @Override
    public void setAppPriceHistory(ArrayList<ArrayList<String>> priceHistory) {
        tv_price_history.setText("Price History");
        this.priceHistory = priceHistory;
        lineChartData();
        chart.setVisibility(View.VISIBLE);
        view_page_screenshot.setVisibility(View.GONE);
        progressBar_chart.setVisibility(View.GONE);
    }

    @Override
    public void setRelateApp() {

    }

    @Override
    public void setPriceHistory() {

    }

    @Override
    public void setDevApp() {

    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setInstanceFollowRedirects(true);
                HttpURLConnection.setFollowRedirects(true);
                connection.connect();
                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length cái getcontentlenght
//                long fileLength = 0;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                    fileLength = connection.getContentLengthLong();
//                } else fileLength = connection.getContentLength();


                // download the file
                File folder = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
                if (!folder.exists() && !folder.mkdirs()) {
                    return "";
                }

                file = new File(folder, title + ".apk");
                input = connection.getInputStream();
                output = new FileOutputStream(file);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
////                    if (fileLength > 0) // only if total length is known
//                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.setMax(100);
//            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // đây là hàm khi chạy xong luôn download, nen m install ngay cho nay
            // phia tren install the la no chua chay xong luong download
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null) {
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                installApk();
            }
            // install apk when app download finish

        }
    }

    private void setUrlInstall(AppModel.SourceBean app) {

//        if ((app != null) && (app.getPrice() == 0)) {
//            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            String salt = "axq appnaz.com";
//            String hasd = salt + timestamp.getTime();
//
//            // create md5 code with the merging of salt and current timestamp
//            final String MD5 = "MD5";
//            MessageDigest md = null;
//            try {
//                md = MessageDigest.getInstance(MD5);
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
//            md.update(hasd.getBytes(), 0, hasd.length());
//            String newHasd = new BigInteger(1, md.digest()).toString(16);
//            while (newHasd.length() < 32)
//                newHasd = "0" + newHasd;
//            String titleUrl = app.getTitle() == null ? "" : app.getTitle().replace(" ", "-");
//            String fieldId = app.getAppid() + "_appnaz.com_" + app.getVersion();
//            String field = "app=" + titleUrl + "&appid=" + fieldId + "&ds=" + newHasd + "&t=" + timestamp.getTime();
//            this.urlInstall = "http://downloadapk.appnaz.com/file/apk_file?" + field;
//        } else {
//            this.urlInstall = "Tin Nguoi VCL!!!";
//        }
    }

    private String getUrlInstall() {
        System.out.println(this.urlInstall);
        return this.urlInstall;
    }

    private void installApk() {
        try {
            Uri uri = Uri.fromFile(file);
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            install.setDataAndType(uri,
                    "application/vnd.android.package-archive");
            startActivity(install);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
