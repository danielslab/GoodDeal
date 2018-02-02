package com.example.puneetchugh.gooddealapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.puneetchugh.gooddealapplication.BaseApp;
import com.example.puneetchugh.gooddealapplication.R;
import com.example.puneetchugh.gooddealapplication.View.SearchView;
import com.example.puneetchugh.gooddealapplication.adapters.ViewPagerAdapter;
import com.example.puneetchugh.gooddealapplication.data.ProductsDataSource;
import com.example.puneetchugh.gooddealapplication.model.ProductBestBuy;
import com.example.puneetchugh.gooddealapplication.model.ProductInfoBestBuy;
import com.example.puneetchugh.gooddealapplication.networkservices.Service;
import com.example.puneetchugh.gooddealapplication.presenters.SearchPresenter;
import com.example.puneetchugh.gooddealapplication.ui.SlidingTabLayout;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SearchActivity extends BaseApp implements SearchView {

    @BindView(R.id.tool_bar) Toolbar toolbar;
    //@BindView(R.id.pager) ViewPager pager;
    //@BindView(R.id.tabs) SlidingTabLayout tabs;
    ViewPager pager;
    SlidingTabLayout tabs;
    private CharSequence Titles[] = {"Search", "Deals", "History", "Wish List"};
    private int Numboftabs = 4;
    private String myString = "search";
    private int responseCount = 0;
    private String enteredName;
    private String enteredPrice;
    private ProductsDataSource productsDataSource;
    private ViewPagerAdapter pageadapter;
    @Inject
    public Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productsDataSource = new ProductsDataSource(this);
        try {
            productsDataSource.open();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        //CustomListView = this;
        Intent searchIntent = getIntent();
        enteredName = searchIntent.getStringExtra("entered_name");
        enteredPrice = searchIntent.getStringExtra("entered_price");
        productsDataSource.createHistory(enteredName, enteredPrice);
        if (isNetworkAvailable()) {
            String enteredNameTemp = enteredName.replace(" ", "&search="); // takes space as multiple search string

        } else {
            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_SHORT).show();
        }

        getDeps().inject(this);
        SearchPresenter searchPresenter = new SearchPresenter(service, this, enteredName, enteredPrice);
        searchPresenter.getProductsList();
        renderView();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void renderView(){

        Log.d("GoodDeal", "renderView() called");
        try {
            if (responseCount == 0) {
                productsDataSource.deleteAllProducts();
                responseCount++;
            }
            setSupportActionBar(toolbar);

            // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
            pageadapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs, "search", enteredPrice);

            // Assigning ViewPager View and setting the adapter
            pager = (ViewPager) findViewById(R.id.pager);
            if(pager == null){
                Log.d("GoodDeal", "Pager is null");
            }
            pager.setAdapter(pageadapter);
            pager.setCurrentItem(1);

            // Assiging the Sliding Tab Layout View
            tabs = (SlidingTabLayout) findViewById(R.id.tabs);
            tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

            // Setting Custom Color for the Scroll bar indicator of the Tab View
            tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    return getResources().getColor(R.color.tabsScrollColor);
                }
            });
            // Setting the ViewPager For the SlidingTabsLayout
            tabs.setViewPager(pager);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            //productInfo.setText(e.getMessage());// set productInfo toast or message
        }

    }

    @Override
    public void showWait() {

    }

    @Override
    public void removeWait() {

    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getBestBuyProductInfo(ProductInfoBestBuy productInfoBestBuy) {

        Log.d("GoodDeal", "SearchActivity getBestBuyProductInfo called");
        List<ProductBestBuy> productBestBuyList = productInfoBestBuy.getProducts();
        for(ProductBestBuy productBestBuy: productBestBuyList){

            String productId = String.valueOf(productBestBuy.getSku());
            String productName = productBestBuy.getName();
            String productImageURL = productBestBuy.getImage();
            String productRating = String.valueOf(productBestBuy.getCustomerReviewAverage());
            String productPrice = String.valueOf(productBestBuy.getSalePrice());
            Log.d("GoodDeal", "ProductName : "+productName);
            productsDataSource.createProduct(productId, productName, productRating, productPrice, (productImageURL == null ? "null": productImageURL));
            pager.setAdapter(pageadapter);
            pager.setCurrentItem(1);
        }

    }
}
