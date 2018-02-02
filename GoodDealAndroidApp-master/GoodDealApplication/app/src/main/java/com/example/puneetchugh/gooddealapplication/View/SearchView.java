package com.example.puneetchugh.gooddealapplication.View;

import com.example.puneetchugh.gooddealapplication.model.ProductInfoBestBuy;

/**
 * Created by puneetchugh on 1/30/18.
 */

public interface SearchView {

    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getBestBuyProductInfo(ProductInfoBestBuy productInfoBestBuy);

}

