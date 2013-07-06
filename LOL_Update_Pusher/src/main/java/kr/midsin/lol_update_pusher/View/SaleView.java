package kr.midsin.lol_update_pusher.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import kr.midsin.lol_update_pusher.Data.SaleItem;
import kr.midsin.lol_update_pusher.R;

/**
 * Created by jin on 13. 7. 6.
 */
public class SaleView extends LinearLayout{
    private TextView mSaleName;
    private TextView mPeriod;
    private LinearLayout mSales;
    public SaleView(Context context) {
        super(context);
        setup();
    }

    public SaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup(){
        ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.sale_view, this);
        mSaleName = (TextView)findViewById(R.id.tv_sale_name);
        mPeriod = (TextView)findViewById(R.id.tv_period);
        mSales = (LinearLayout)findViewById(R.id.ll_sales);
    }

    public void setContents(String saleName, String period, List<SaleItem> sales){
        mSaleName.setText(saleName);
        mPeriod.setText(period);
        for(int i=0; i<sales.size(); i++){
            SaleItemView saleItemView = new SaleItemView(getContext());
            saleItemView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            SaleItem saleItem = new SaleItem();
            saleItem = sales.get(i);
            saleItemView.setContents(saleItem.getName(), saleItem.getPrice());
            mSales.addView(saleItemView);
        }
    }
}
