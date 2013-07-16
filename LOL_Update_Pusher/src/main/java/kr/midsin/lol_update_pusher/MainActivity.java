package kr.midsin.lol_update_pusher;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kr.midsin.lol_update_pusher.Data.Sale;
import kr.midsin.lol_update_pusher.Data.SaleItem;

public class MainActivity extends Activity {
    private static final String MY_AD_UNIT_ID = "a151d98af078939";
    protected String mJsonData="";
    ListView mListView;
    SaleListAdapter saleListAdapter;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mListView = (ListView)findViewById(R.id.lv_list);
        new TestAsyncTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(adView == null) {
            switch (item.getItemId()){
                case R.id.support:
                    setAdMob();
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(adView != null){
            return false;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void jsonParser(){
        try {
            JSONArray saleArray = new JSONArray(mJsonData);
            List<Sale> sales = new ArrayList<Sale>();
            for(int i=0; i<saleArray.length(); i++) {
                JSONObject saleData = saleArray.getJSONObject(i);
                JSONArray saleItemArray = saleData.getJSONArray("sale_item");
                Sale sale = new Sale();
                sale.setSale_name(saleData.getString("sale_name"));
                sale.setPeriod(saleData.getString("period"));

                List<SaleItem> saleItems = new ArrayList<SaleItem>();
                for (int j=0; j<saleItemArray.length(); j++) {
                    JSONObject saleItemData = saleItemArray.getJSONObject(j);
                    SaleItem saleItem = new SaleItem();
                    saleItem.setName(saleItemData.getString("name"));
                    saleItem.setPrice(saleItemData.getString("price"));
                    saleItems.add(saleItem);
                }
                sale.setSaleItems(saleItems);
                sales.add(sale);
            }
            saleListAdapter = new SaleListAdapter(this, sales);
        } catch (JSONException e) {
            Log.d("lol_update_pusher", e.getMessage());
        }
    }

    public void getJsonData(){
        String mapURL=getString(R.string.url);
        String line;
        HttpURLConnection urlConnection = null;

        try
        {
            URL url = new URL(mapURL);
            urlConnection = (HttpURLConnection)url.openConnection();

            if(urlConnection!=null){
                urlConnection.setConnectTimeout(5000);
                urlConnection.setUseCaches(false);

                if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));

                    do {
                        line = br.readLine();
                        mJsonData += line;
                    } while (line != null);

                    br.close();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
    }

    private void setAdMob(){
        adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
        FrameLayout layout = (FrameLayout)findViewById(R.id.fl_ad_layout);
        layout.addView(adView);
        findViewById(R.id.rl_ad_layout).setVisibility(View.VISIBLE);
        adView.loadAd(new AdRequest());
    }
    private class TestAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            getJsonData();
            jsonParser();
            publishProgress();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mListView.setAdapter(saleListAdapter);
            super.onPostExecute(result);
        }

    }

    @Override
    protected void onDestroy() {
        if(adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
