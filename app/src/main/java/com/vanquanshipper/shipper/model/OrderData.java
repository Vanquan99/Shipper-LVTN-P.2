
package com.vanquanshipper.shipper.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrderData {

    @SerializedName("total_accept_order")
    private String mTotalAcceptOrder;
    @SerializedName("total_complete_order")
    private String mTotalCompleteOrder;
    @SerializedName("total_reject_order")
    private String mTotalRejectOrder;
    @SerializedName("total_sale")
    private String mTotalSale;

    @SerializedName("rider_status")
    private String riderStatus;



    public String getTotalAcceptOrder() {
        return mTotalAcceptOrder;
    }

    public void setTotalAcceptOrder(String totalAcceptOrder) {
        mTotalAcceptOrder = totalAcceptOrder;
    }

    public String getTotalCompleteOrder() {
        return mTotalCompleteOrder;
    }

    public void setTotalCompleteOrder(String totalCompleteOrder) {
        mTotalCompleteOrder = totalCompleteOrder;
    }

    public String getTotalRejectOrder() {
        return mTotalRejectOrder;
    }

    public void setTotalRejectOrder(String totalRejectOrder) {
        mTotalRejectOrder = totalRejectOrder;
    }

    public String getTotalSale() {
        return mTotalSale;
    }

    public void setTotalSale(String totalSale) {
        mTotalSale = totalSale;
    }

    public String getRiderStatus() {
        return riderStatus;
    }

    public void setRiderStatus(String riderStatus) {
        this.riderStatus = riderStatus;
    }
}
