package th.ac.kmutnb.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListSedan extends AppCompatActivity implements AdapterView.OnItemClickListener{

        private static final String TAG = "my_app";
        public static final String REQUEST_TAG = "myrequest";
        private RequestQueue mQueue;
        ProgressDialog pDialog;
        private List<DataModel> datas = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sedan);


        String url = "http://10.0.2.2:3000/detail";
        //String url = "http://itpart.com/android/json/test8records.php";

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading..");
        pDialog.show();

        JsonArrayRequest jsRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();

                        JSONObject jsObj;   // = null;
                        for (int i=0; i < response.length(); i++ ) {
                            try {
                                jsObj = response.getJSONObject(i);
                                String brand = jsObj.getString("brand");
                                String model = jsObj.getString("model");
                                Log.d(TAG, brand + " " + model );

                                DataModel dataitem = gson.fromJson(String.valueOf(jsObj), DataModel.class);
                                datas.add(dataitem);
                                Log.d(TAG,"gson "+ dataitem.getBrand());
                                Log.d(TAG,"gson "+ dataitem.getModel());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (datas.size() > 0){
                            displayListview();
                        }

                        pDialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,error.toString());
                        Toast.makeText(getBaseContext(),error.toString(),Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                });  // Request

        mQueue = Volley.newRequestQueue(this);
        jsRequest.setTag(REQUEST_TAG);
        mQueue.add(jsRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    public void displayListview(){
        MyAdapter adapter = new MyAdapter(this,datas);
        ListView lv = findViewById(R.id.listView);
        lv.setOnItemClickListener(this);
        lv.setAdapter(adapter);
    }

    // for json listview
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, String.valueOf(i));
        Intent itn = new Intent(this,DetailPageActivity.class);
        itn.putExtra("recID", i);
        startActivity(itn);
    }

}