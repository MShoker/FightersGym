package com.shoker.fightersgym;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shoker.fightersgym.DB.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.shoker.fightersgym.CaloriesFragment.CATEGORY;

public class Test extends AppCompatActivity {

    ListView lv;
    SearchView search;
    String LOG = "jasee";

    DatabaseHelper helper;

    ArrayList<Food> foods;
    FoodAdapter foodAdapter;
    AlertDialog.Builder dialog;

    String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        category = getIntent().getExtras().getString(CATEGORY);
        search = (SearchView) findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearch(newText);
                return false;
            }
        });

    }

    void mSearch(String search) {
        if (isNetworkConnected()) {
            foods = new ArrayList<Food>();
            lv = findViewById(R.id.lv_food);
            foodAdapter = new FoodAdapter(Test.this, foods);
            performSearch(search);
        } else {

        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(foods.get(position).getName());
            }
        });
    }


    void performSearch(String search) {
        String refineSearch = search.replaceAll(" ", "%20").toLowerCase();
        String URL = "https://api.edamam.com/api/food-database/v2/parser?ingr=" + refineSearch + "&app_id=0f908c89&app_key=a422792b414c2488b9078ec7e7c15ce6";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("hints");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonfood = jsonObject1.getJSONObject("food");
                        JSONObject jsonnutrients = jsonfood.getJSONObject("nutrients");

                        if (jsonfood.has("image")) {
                            String label = jsonfood.getString("label");
                            String image_url = jsonfood.getString("image");
                            String calories = jsonnutrients.getString("ENERC_KCAL");
                            Food food = new Food();
                            food.setName(label);
                            food.setImage_url(image_url);
                            food.setCalories(calories);
                            foods.add(food);
                        }
                    }
                    lv.setAdapter(foodAdapter);
                    foodAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d(LOG, error.getMessage());
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void showDialog(final String title) {
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setView(R.layout.dialog_food);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog dialog2 = Dialog.class.cast(dialog);
                EditText editText = dialog2.findViewById(R.id.et_food_dialog);
                save(title, Integer.parseInt(editText.getText().toString()));
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void save(String name, int energ) {
        try {
            helper = new DatabaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.insertFood(category, name, energ);
        helper.close();
        finish();
    }

}