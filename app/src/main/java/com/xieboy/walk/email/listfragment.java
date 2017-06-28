package com.xieboy.walk.email;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.xieboy.walk.email.RecyclerViewAdapter.LONG_CLICK;

/**
 * Created by Walk on 2017/4/29.
 */

public class listfragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Notes notes;
    private RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notes = new Notes(getContext());
        mRecyclerView =
                (RecyclerView) inflater.inflate(R.layout.list_fragment, container, false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        adapter=new RecyclerViewAdapter(getActivity()).setListChangedListener(new Action() {
            @Override
            public void onCall(Object obj) {
                if((Integer)obj==LONG_CLICK){
                    adapter.setList(notes.getData());
                }
            }
        });
        mRecyclerView.setAdapter(adapter);
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//
////        接收参数
//        Bundle bundle1 = getArguments();
//        ArrayList list = bundle1.getParcelableArrayList("list");
//        //从List中将参数转回 List<Map<String, Object>>
//        List<Map<String, Object>> lists= (List<Map<String, Object>>)list.get(0);

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.setList(notes.getData());
    }
}
