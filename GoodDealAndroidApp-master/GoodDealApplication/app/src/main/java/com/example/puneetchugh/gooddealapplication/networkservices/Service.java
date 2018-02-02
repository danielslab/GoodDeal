package com.example.puneetchugh.gooddealapplication.networkservices;

import android.util.Log;

import com.example.puneetchugh.gooddealapplication.model.ProductInfoBestBuy;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by puneetchugh on 1/30/18.
 */

public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getProductsList(final GetBestBuyProductsCallback callback, String productName, String lowerPrice, String upperPrice) {

        Log.d("GoodDeal", "Service Subscription called");
        return networkService.getProductsBestBuy(productName, lowerPrice, upperPrice, "lAauF4W0qQRX97gJ05XvPhz4")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ProductInfoBestBuy>>() {
                    @Override
                    public Observable<? extends ProductInfoBestBuy> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ProductInfoBestBuy>() {
                    @Override
                    public void onCompleted() {
                        //callback.onSuccess(productInfoBestBuy);
                        Log.d("GoodDeal", "onCompleted() of Service Subscription called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                        Log.d("GoodDeal", "onError() of Service Subscription called");
                    }

                    @Override
                    public void onNext(ProductInfoBestBuy productInfoBestBuy) {
                        callback.onSuccess(productInfoBestBuy);
                        Log.d("GoodDeal", "onNext() of Service Subscription called");
                    }
                });
    }

    public interface GetBestBuyProductsCallback{
        void onSuccess(ProductInfoBestBuy productInfoBestBuy);

        void onError(NetworkError networkError);
    }
}