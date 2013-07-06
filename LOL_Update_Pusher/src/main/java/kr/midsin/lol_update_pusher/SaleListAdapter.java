package kr.midsin.lol_update_pusher;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.zip.Inflater;

import kr.midsin.lol_update_pusher.Data.Sale;
import kr.midsin.lol_update_pusher.View.SaleView;

/**
 * Created by jin on 13. 7. 6.
 */
public class SaleListAdapter extends BaseAdapter {
    Context mContext;
    List<Sale> mSales;
    public SaleListAdapter(Context context, List<Sale> sales) {
        mContext = context;
        mSales = sales;
    }

    @Override
    public int getCount() {
        return mSales.size();
    }

    @Override
    public Object getItem(int position) {
        return mSales.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            SaleView saleView = new SaleView(mContext);
            Sale sale = mSales.get(position);
            saleView.setContents(sale.getSale_name(), sale.getPeriod(), sale.getSaleItems());
            convertView = saleView;
        }
        return convertView;
    }
}
