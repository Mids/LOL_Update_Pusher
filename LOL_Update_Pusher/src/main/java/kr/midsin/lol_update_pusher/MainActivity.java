package kr.midsin.lol_update_pusher;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
    protected String mJsonData="";
    ListView mListView;
    SaleListAdapter saleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mListView = (ListView)findViewById(R.id.lv_list);
        new TestAsyncTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return false;
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
}
