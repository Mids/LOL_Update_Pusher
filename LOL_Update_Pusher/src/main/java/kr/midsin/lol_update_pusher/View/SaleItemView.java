package kr.midsin.lol_update_pusher.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.midsin.lol_update_pusher.R;

/**
 * Created by jin on 13. 7. 6.
 */
public class SaleItemView extends LinearLayout {
    TextView mName;
    TextView mPrice;
    public SaleItemView(Context context) {
        super(context);
        setUp();
    }

    public SaleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    private void setUp(){
        ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.sale_item_view, this);
        mName = (TextView)findViewById(R.id.tv_content_name);
        mPrice = (TextView)findViewById(R.id.tv_content_price);
    }

    public void setContents(String name, String price){
        mName.setText(name);
        mPrice.setText(price);
    }
}
