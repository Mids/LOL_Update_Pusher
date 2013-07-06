package kr.midsin.lol_update_pusher.Data;

import java.util.List;

/**
 * Created by jin on 13. 7. 6.
 */
public class Sale {
    String sale_name;
    String period;
    List<SaleItem> saleItems;

    public String getSale_name() {
        return sale_name;
    }

    public void setSale_name(String sale_name) {
        this.sale_name = sale_name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }
}
