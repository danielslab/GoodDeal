package com.example.puneetchugh.gooddealapplication.fragments;

/**
 * Created by TonyHuang on 10/29/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.puneetchugh.gooddealapplication.R;
import com.example.puneetchugh.gooddealapplication.activities.searchActivity;
import com.example.puneetchugh.gooddealapplication.adapters.HistoryAdapter;
import com.example.puneetchugh.gooddealapplication.data.ProductsDataSource;
import com.example.puneetchugh.gooddealapplication.models.History;

import java.sql.SQLException;
import java.util.ArrayList;

public class HistoryTab extends Fragment {

    private ArrayList<History> histories;
    private ProductsDataSource productsDataSource;
    public HistoryTab CustomListView = null;
    private ListView historyListView;
    private HistoryAdapter historyAdapter;
    private Button historyDelete;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.history_list, container, false);
        historyDelete = (Button) v.findViewById(R.id.delete_all_history);
        historyListView = (ListView) v.findViewById(R.id.list_for_history);

        productsDataSource = new ProductsDataSource(getActivity());
        CustomListView = this;
        try {
            productsDataSource.open();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        histories = productsDataSource.getAllHistory();
        //productsDataSource.close();

        Resources res = getResources();

        for(History history : histories){
            Log.d("History" , history.getName());
        }
        if(histories == null || histories.isEmpty()){}
        else{
            historyAdapter = new HistoryAdapter(getActivity(), histories, res);
            historyListView.setAdapter(historyAdapter);
        }

        historyDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    productsDataSource.open();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
                productsDataSource.deleteAllHistory();
                histories.clear();
                histories = productsDataSource.getAllHistory();

                historyAdapter.notifyDataSetChanged();
                //historyListView.invalidateViews();
                //historyListView.refreshDrawableState();
                //historyListView.invalidateViews();
                //FragmentTransaction tr = getFragmentManager().beginTransaction();
                //HistoryTab historyTab = new HistoryTab();
                //tr.replace(R.id.historyTab,historyTab);
                //tr.detach(historyTab);
                //tr.attach(historyTab);
                //tr.commit();
                //historyAdapter.notifyDataSetChanged();
                //historyListView.invalidateViews();


                // TODO : Add code here to refresh the HistoryTab

            }


        });

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, final long id) {
                try {
                    productsDataSource.open();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
                //final long new_id = id+1;
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select the action:");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Resources res = getResources();

                        //histories.clear();
                        productsDataSource.deleteHistory(id);
                        histories.clear();
                        histories = productsDataSource.getAllHistory();
                        historyAdapter = new HistoryAdapter(getActivity(), histories, res);
                        //historyListView.setAdapter(historyAdapter);
                        historyAdapter.notifyDataSetChanged();
                        historyListView.setAdapter(historyAdapter);
                        //historyListView.refreshDrawableState();
                        //historyListView.invalidateViews();

                        //FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //
                        // ft.detach(this).attach(this).commit();

                        // TODO : Add code here to refresh HistoryTab page

                    }
                });

                builder.setNegativeButton("Search", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            productsDataSource.open();
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        Intent intent = new Intent(getActivity(), searchActivity.class);
                        History history = productsDataSource.getHistory(id);
                        intent.putExtra("entered_name", history.getName());
                        intent.putExtra("entered_price", history.getPrice());
                        startActivity(intent);
                    }
                });


                builder.show();
            }
        });
        productsDataSource.close();
        return v;

    }


/*    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }*/
}