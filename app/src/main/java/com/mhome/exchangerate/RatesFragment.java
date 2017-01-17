package com.mhome.exchangerate;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RatesFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    MyRecyclerViewAdapter adapter;
    List<List<String>> itemList;
    String APP_KEY = "03e971239c591a856761f079a276cd5d";
    RequestQueue queue;
    TextView tv_ginger;

    public RatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        queue = ((MainActivity) getActivity()).queue;
        view = inflater.inflate(R.layout.fragment_, container, false);
        tv_ginger = (TextView) view.findViewById(R.id.tv_ginger);
        updateData();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        updateData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1);
            }
        });
        return view;


    }

    private void updateData(){
        String url = "http://op.juhe.cn/onebox/exchange/query";
        url += "?key=" + APP_KEY;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        RatesResponse ratesResponse = gson.fromJson(response.toString(),RatesResponse.class);
                        List<List<String>> mList = ratesResponse.result.list;
                        mList.add(0,new ArrayList<String>() {{
                            add("人民币");
                            add("100");
                            add("100");
                            add("100");
                            add("100");
                            add("100");
                        }});
                        if(itemList == null){
                            itemList = mList;
                            adapter = new MyRecyclerViewAdapter(itemList);
                            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                            tv_ginger.setVisibility(View.INVISIBLE);
                        }else{
                            itemList.clear();
                            itemList.addAll(mList);
                            adapter.notifyDataSetChanged();
                            Log.i("ADA","updated");
                        }
                        //tv_ginger.setText("Response: " + ratesResponse.result.list.get(0).get(0));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv_ginger.setText("ERROR");
                    }
                });
        queue.add(jsObjRequest);
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

        private List<List<String>> responses;

        MyRecyclerViewAdapter(List<List<String>> responses){
            this.responses = responses;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View itemView = inflater.inflate(R.layout.list_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public int getItemCount() {
            Log.i("ginger","responses.size() == "+ responses.size());
            return responses.size();

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final List<String> rates = responses.get(position);

            TextView title = holder.title;
            TextView price = holder.rates;
//            ImageView image = holder.iv;

            title.setText("100 "+rates.get(0));
            price.setText("= "+rates.get(5)+" 人民币");
            //Glide.with(getContext()).load(images[rates.image]).into(image);
            /*holder.cv.setOnClickListener(new CardView.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(),ScrollingActivity.class);
                    intent.putExtra(TAG,images[rates.image]);
                    intent.putExtra("subtitle",rates.subtitle);
                    view.getContext().startActivity(intent);
                }
            });*/


        }

        class ViewHolder extends RecyclerView.ViewHolder{
            CardView cv;
            TextView title, rates;
            ImageView iv;
            public ViewHolder(View view){
                super(view);
                cv = (CardView) view.findViewById(R.id.cv);
                title = (TextView) view.findViewById(R.id.tv_title);
                rates = (TextView) view.findViewById(R.id.tv_rates);
            }
        }
    }


}
