package com.example.puneetchugh.gooddealapplication.networkservices;

import android.content.ClipData;

import com.example.puneetchugh.gooddealapplication.model.ItemWalmart;
import com.example.puneetchugh.gooddealapplication.model.ProductInfoBestBuy;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by puneetchugh on 1/30/18.
 */

public interface NetworkService {

/*    @GET("v1/city")
    Observable<CityListResponse> getCityList();*/

    @GET("v1/products(search={search_item}&salePrice>{lower_limit}&salePrice<{upper_limit})?format=json&show=customerReviewAverage,customerReviewCount,sku,name,salePrice,image")
    Observable<ProductInfoBestBuy> getProductsBestBuy(@Path("search_item") String searchItem,
                                                      @Path("lower_limit") String lowerLimit ,
                                                      @Path("upper_limit") String upperLimit,
                                                      @Query("apiKey") String apiKey);
    //https://api.bestbuy.com/v1/products(search=oven&search=stainless&search=steel&salePrice>50&salePrice<400)?format=json&show=customerReviewAverage,customerReviewCount,sku,name,salePrice&apiKey=lAauF4W0qQRX97gJ05XvPhz4


    @GET("v1/search?numItems=5")
    Observable<ItemWalmart> getProductsWalmart(@Query("apiKey") String apiKey,
                                               @Query("query") String searchItem);


    //http://api.walmartlabs.com/v1/search?numItems=5&apiKey=vhvu3qsshkyv5cpxrrtr36ur&query=oven


}