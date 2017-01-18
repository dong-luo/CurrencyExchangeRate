package com.mhome.exchangerate;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConverterFragment extends Fragment {
    Spinner spinner_from, spinner_to;
    View view;
    RequestQueue queue;
    boolean connected = false;
    String[] codeList;
    String[] nameList;
    EditText amount;
    TextView result;
    int currency_from = 0;
    int currency_to = 0;
    double exchange = 1;


    public ConverterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_converter, container, false);
        queue = ((MainActivity) getActivity()).queue;
        setSpinner();
        amount = (EditText) view.findViewById(R.id.editText);
        amount.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
        result = (TextView) view.findViewById(R.id.tv_result);
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showResult();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    public void setSpinner() {
        spinner_from = (Spinner) view.findViewById(R.id.spinner2);
        spinner_to = (Spinner) view.findViewById(R.id.spinner3);
        String url = "http://op.juhe.cn/onebox/exchange/list?key=03e971239c591a856761f079a276cd5d";

        RetrofitManager.apiService
                .getList(Constants.APP_KEY)
                .enqueue(new Callback<CurrencyResponse>() {
            @Override
            public void onResponse(Call<CurrencyResponse> call, retrofit2.Response<CurrencyResponse> response) {
                int statusCode = response.code();
                CurrencyResponse currencyResponse = response.body();
                List<CurrencyResponse.Result.Currency> currencyList = currencyResponse.result.list;
                nameList = new String[currencyList.size()];
                codeList = new String[currencyList.size()];
                for (int i = 0; i < currencyList.size(); i++) {
                    nameList[i] = currencyList.get(i).name;
                    codeList[i] = currencyList.get(i).code;
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_from.setAdapter(arrayAdapter);
                spinner_to.setAdapter(arrayAdapter);
                //tv_ginger.setText("Response: " + ratesResponse.result.list.get(0).get(0));
                connected = true;
            }

            @Override
            public void onFailure(Call<CurrencyResponse> call, Throwable t) {

            }
        });


        /*JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        CurrencyResponse currencyResponse = gson.fromJson(response.toString(), CurrencyResponse.class);
                        List<CurrencyResponse.Result.Currency> currencyList = currencyResponse.result.list;
                        nameList = new String[currencyList.size()];
                        codeList = new String[currencyList.size()];
                        for (int i = 0; i < currencyList.size(); i++) {
                            nameList[i] = currencyList.get(i).name;
                            codeList[i] = currencyList.get(i).code;
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,nameList);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_from.setAdapter(arrayAdapter);
                        spinner_to.setAdapter(arrayAdapter);
                        //tv_ginger.setText("Response: " + ratesResponse.result.list.get(0).get(0));
                        connected = true;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsObjRequest);*/
        AdapterView.OnItemSelectedListener spinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currencySelectionChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner_from.setOnItemSelectedListener(spinnerOnItemSelectedListener);
        spinner_to.setOnItemSelectedListener(spinnerOnItemSelectedListener);
        View.OnTouchListener spinnerTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return false;
            }
        };
        spinner_from.setOnTouchListener(spinnerTouchListener);
        spinner_to.setOnTouchListener(spinnerTouchListener);
    }

    void currencySelectionChanged(){
        currency_from = spinner_from.getSelectedItemPosition();
        currency_to = spinner_to.getSelectedItemPosition();
        if(currency_from == currency_to){
            exchange = 1;
            showResult();
            return;
        }
        /*String url = "http://op.juhe.cn/onebox/exchange/currency?key=03e971239c591a856761f079a276cd5d&from="
                +codeList[currency_from]
                +"&to="
                +codeList[currency_to];
        Log.i("MEIWA",url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        ConverterResponse converterResponse = gson.fromJson(response.toString(),ConverterResponse.class);
                        Log.i("MEIWA",converterResponse.reason);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsObjRequest);*/

        RetrofitManager.apiService
                .getCurrency(codeList[currency_from],codeList[currency_to],Constants.APP_KEY)
                .enqueue(new Callback<ConverterResponse>() {
                    @Override
                    public void onResponse(Call<ConverterResponse> call, retrofit2.Response<ConverterResponse> response) {
                        ConverterResponse converterResponse = response.body();
                        if(response.code() == 200 && converterResponse.error_code == 0){
                            exchange = converterResponse.result.get(0).exchange;
                            showResult();
                        }
                    }

                    @Override
                    public void onFailure(Call<ConverterResponse> call, Throwable t) {

                    }
                });
    }

    void showResult(){
        if(connected){
            double amount_from = amount.getText().toString().isEmpty() ? 0 : Double.parseDouble(amount.getText().toString());
            double amount_to = (double) Math.round(amount_from * exchange * 10000) / 10000;
            String string_amount_from = Math.round(amount_from) == amount_from ? Integer.toString((int) amount_from) : Double.toString(amount_from);
            String string_amount_to = Math.round(amount_to) == amount_to ? Integer.toString((int) amount_to) : Double.toString(amount_to);
            result.setText(string_amount_from + nameList[currency_from] + " = " + string_amount_to + nameList[currency_to]);
        }
    }

}
