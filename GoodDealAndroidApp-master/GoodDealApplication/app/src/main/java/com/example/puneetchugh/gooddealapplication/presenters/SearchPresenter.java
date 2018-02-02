package com.example.puneetchugh.gooddealapplication.presenters;

import android.content.Intent;
import android.util.Log;
import android.util.StringBuilderPrinter;

import com.example.puneetchugh.gooddealapplication.View.SearchView;
import com.example.puneetchugh.gooddealapplication.model.ProductInfoBestBuy;
import com.example.puneetchugh.gooddealapplication.networkservices.NetworkError;
import com.example.puneetchugh.gooddealapplication.networkservices.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by puneetchugh on 1/30/18.
 */

public class SearchPresenter {
    private final Service service;
    private final SearchView view;
    private CompositeSubscription subscriptions;
    private String productName;
    private String productPriceLower;
    private String productPriceUpper;

    public SearchPresenter(Service service, SearchView view, String productName, String productPrice) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
        this.productName = productName;
        this.productPriceLower = String.valueOf(Integer.parseInt(productPrice) - Integer.parseInt(productPrice)*0.03);
        this.productPriceUpper = String.valueOf(Integer.parseInt(productPrice) + Integer.parseInt(productPrice)*0.03);
    }

    public void getProductsList() {
        view.showWait();

        Log.d("GoodDeal", "SearchPresenter getProductsList() called");
        Subscription subscription = service.getProductsList(new Service.GetBestBuyProductsCallback() {
            @Override
            public void onSuccess(ProductInfoBestBuy productInfoBestBuy) {
                view.removeWait();
                view.getBestBuyProductInfo(productInfoBestBuy);
                Log.d("GoodDeal", "SearchPresenter onSuccess() called");
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
                Log.d("GoodDeal", "SearchPresenter onError called");
            }

        }, productName, productPriceLower, productPriceUpper);

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
