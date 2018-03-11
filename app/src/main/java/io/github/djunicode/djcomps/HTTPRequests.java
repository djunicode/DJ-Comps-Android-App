package io.github.djunicode.djcomps;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.djunicode.djcomps.adapters.FileAdapter;
import io.github.djunicode.djcomps.adapters.UserAdapter;
import io.github.djunicode.djcomps.database.AppDatabase;
import io.github.djunicode.djcomps.database.data.File;
import io.github.djunicode.djcomps.database.data.Group;
import io.github.djunicode.djcomps.database.data.User;

import static io.github.djunicode.djcomps.LoginActivity.SP_LOGIN_ID;

public class HTTPRequests {

    private static String baseUrl = "http://192.168.0.205:8888";

    private static RequestQueue reqQueue;
    private Context context;

    public HTTPRequests(Context context) {
        this.context = context;
        if(reqQueue == null)
            reqQueue = Volley.newRequestQueue(context);
    }


    RequestFuture<String> onLoginRequest(final String sap_id, final String password) {

        RequestFuture<String> futureRequest = RequestFuture.newFuture();
        String url = baseUrl + "/login";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, futureRequest, futureRequest){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("sap_id", sap_id);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        reqQueue.add(postRequest);
        return futureRequest;
    }

    public void getUser(final String sap_id, final UserAdapter adapter) {

        String url = "http://localhost";


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject  = new JSONObject(response);
                            JSONObject o = jsonObject.getJSONObject(response);
                            User item = new User(
                                    o.getLong("sap_id"),
                                    o.getString("bio"),
                                    o.getString("name"),
                                    o.getLong("group_id"),
                                    o.getString("profile_image_url")
                            );

                            adapter.addUser(item);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        HashMap<String, String> params = new HashMap<>();
        if(sap_id!=null)
            params.put("sap_id", sap_id);

        reqQueue.add(postRequest);

    }

    public void getUserByName(final String name, final UserAdapter adapter) {

        String url = "http://localhost";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                User item = new User(
                                        o.getLong("sap_id"),
                                        o.getString("bio"),
                                        o.getString("name"),
                                        o.getLong("group_id"),
                                        o.getString("profile_image_url")
                                );

                                adapter.addUser(item);
                            }} catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        HashMap<String, String> params = new HashMap<>();
        if(name!=null)
            params.put("name", name);

        reqQueue.add(postRequest);
    }

    public void getUsersByGroup(final List<Integer> groups, final UserAdapter adapter) {

        Log.e("lists", String.valueOf(groups));

        String url = baseUrl + "/users/group";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                User item = new User(
                                        o.getLong("sap_id"),
                                        o.getString("bio"),
                                        o.getString("name"),
                                        o.getLong("group"),
                                        null
                                );

                                adapter.addUser(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.networkResponse.toString());
                        Toast.makeText(context, "Error getting users from server", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("group", groups.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        reqQueue.add(postRequest);
    }


    public void getAllFiles(final FileAdapter adapter, String url, final String start_idx, final String end_idx, final String sort_by, final String sort_order) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                File item = new File(
                                        o.getLong("file_id"),
                                        o.getLong("sap_id"),
                                        o.getLong("size"),
                                        o.getInt("no_of_downloads"),
                                        o.getInt("no_of_stars"),
                                        o.getString("type"),
                                        o.getString("name"),
                                        new Date(o.getLong("time added")),
                                        o.getString("description"), false, false
                                );
                                adapter.addFile(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("start_idx", start_idx);
                params.put("end_idx", end_idx);
                if(sort_by != null)
                    params.put("sort_by", sort_by);
                if(sort_order != null)
                    params.put("sort_order", sort_order);

                return params;
            }
        };

        reqQueue.add(postRequest);
    }

    public void getAllFilesByUser(final FileAdapter adapter, String url, final String start_idx, final String end_idx, final String sap_id, final String sort_by, final String sort_order) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                File item = new File(
                                        o.getLong("file_id"),
                                        o.getLong("sap_id"),
                                        o.getLong("size"),
                                        o.getInt("no_of_downloads"),
                                        o.getInt("no_of_stars"),
                                        o.getString("type"),
                                        o.getString("name"),
                                        new Date(o.getLong("time added")),
                                        o.getString("description"), false, false
                                );
                                adapter.addFile(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("start_idx", start_idx);
                params.put("end_idx", end_idx);
                params.put("sap_id", sap_id);
                if(sort_by != null)
                    params.put("sort_by", sort_by);
                if(sort_order != null)
                    params.put("sort_order", sort_order);

                return params;
            }
        };

        reqQueue.add(postRequest);
    }

    public void getAllFilesByName(final FileAdapter adapter, String url, final String start_idx, final String end_idx, final String name, final String sort_by, final String sort_order) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                File item = new File(
                                        o.getLong("file_id"),
                                        o.getLong("sap_id"),
                                        o.getLong("size"),
                                        o.getInt("no_of_downloads"),
                                        o.getInt("no_of_stars"),
                                        o.getString("type"),
                                        o.getString("name"),
                                        new Date(o.getLong("time added")),
                                        o.getString("description"), false, false
                                );
                                adapter.addFile(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("start_idx", start_idx);
                params.put("end_idx", end_idx);
                params.put("name", name);
                if(sort_by != null)
                    params.put("sort_by", sort_by);
                if(sort_order != null)
                    params.put("sort_order", sort_order);

                return params;
            }
        };

        reqQueue.add(postRequest);
    }

    public void getAllFilesByType(final FileAdapter adapter, String url, final String start_idx, final String end_idx, final String type, final String sort_by, final String sort_order) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                File item = new File(
                                        o.getLong("file_id"),
                                        o.getLong("sap_id"),
                                        o.getLong("size"),
                                        o.getInt("no_of_downloads"),
                                        o.getInt("no_of_stars"),
                                        o.getString("type"),
                                        o.getString("name"),
                                        new Date(o.getLong("time added")),
                                        o.getString("description"), false, false
                                );
                                adapter.addFile(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("start_idx", start_idx);
                params.put("end_idx", end_idx);
                params.put("type", type);
                if(sort_by != null)
                    params.put("sort_by", sort_by);
                if(sort_order != null)
                    params.put("sort_order", sort_order);

                return params;
            }
        };

        reqQueue.add(postRequest);
    }

    public void getFileInfo(String url, final String file_id)
    {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject o = new JSONObject(response);
                            File item = new File(
                                    o.getLong("file_id"),
                                    o.getLong("sap_id"),
                                    o.getLong("size"),
                                    o.getInt("no_of_downloads"),
                                    o.getInt("no_of_stars"),
                                    o.getString("type"),
                                    o.getString("name"),
                                    new Date(o.getLong("time added")),
                                    o.getString("description"), false, false
                            );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("file_id", file_id);
                return params;
            }
        };

        reqQueue.add(postRequest);
    }


    public void loadFileData(final String file_id, final Context context)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://djunicode.pythonanywhere.com/file",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject o = new JSONObject(response);
                            File item = new File(
                                    o.getLong("file_id"),
                                    o.getLong("sap_id"),
                                    o.getLong("size"),
                                    o.getInt("no_of_downloads"),
                                    o.getInt("no_of_stars"),
                                    o.getString("type"),
                                    o.getString("name"),
                                    new Date(o.getLong("time added")),
                                    o.getString("description"), false, false
                            );
                            Intent intent = new Intent(context, FileDetailActivity.class);
                            intent.putExtra(FileDetailActivity.FILE_INFO_PARCEL, item);
                            context.startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("file_id", file_id);
                return params;
            }
        };

        reqQueue.add(postRequest);
    }

    public void uploadFileRequest(Context context, Uri uri, String filename, String description) {

        String url = baseUrl + "/file/upload/";

        SharedPreferences spref = context.getSharedPreferences(SP_LOGIN_ID, Context.MODE_PRIVATE);

        Map<String, String> params = new HashMap<>();
        params.put("name", filename);
        params.put("submitted_by", spref.getString(LoginActivity.SP_LOGIN_USER_SAP, null));
        params.put("description", description);


        new MultipartRequest(context, url, "file_data", uri, params).execute();
    }

    void syncGroups(){
        String url = baseUrl + "/Group";

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            List<Group> userGroups = new ArrayList<>();
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject groupObj = jsonArray.getJSONObject(i);
                                String grpCategory = groupObj.getString("category");
                                Group group = new Group(
                                        groupObj.getLong("group_id"),
                                        grpCategory.equalsIgnoreCase("S"),
                                        groupObj.getInt("year"),
                                        groupObj.getString("division").charAt(0),
                                        groupObj.getDouble("total_disk_available")
                                );
                                userGroups.add(group);
                            }
                            new syncGroupDatabase(context, userGroups).execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.networkResponse.toString());
                    }
                }
        );

        reqQueue.add(getRequest);
    }

    private static class syncGroupDatabase extends AsyncTask<Void, Void, Void> {

        WeakReference<Context> wrContext;
        List<Group> groups;

        syncGroupDatabase(Context context, List<Group> groups){
            this.wrContext = new WeakReference<>(context);
            this.groups = groups;
        }

        @Override
        protected Void doInBackground(Void... params) {
            AppDatabase.getInMemoryDatabase(wrContext.get()).groupModel().insertGroups(groups);
            return null;
        }
    }
}
