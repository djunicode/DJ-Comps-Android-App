package io.github.djunicode.djcomps;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import io.github.djunicode.djcomps.adapters.FileAdapter;
import io.github.djunicode.djcomps.adapters.UserAdapter;
import io.github.djunicode.djcomps.database.data.File;
import io.github.djunicode.djcomps.database.data.User;

public class HTTPRequests {

    private static RequestQueue reqQueue;

    public HTTPRequests(Context context) {
        if(reqQueue == null)
            reqQueue = Volley.newRequestQueue(context);
    }


    RequestFuture<JSONObject> onLoginRequest(final String sap_id, final String password) {

        RequestFuture<JSONObject> futureRequest = RequestFuture.newFuture();
        String url = "http://192.168.0.205:8888/login";

        JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(), futureRequest, futureRequest){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sap_id", sap_id);
                params.put("password", password);
                return params;
            }
        };

        reqQueue.add(jsonPostRequest);
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

    public void getUsersByGroup(final String groupname, final UserAdapter adapter) {

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
        if(groupname!=null)
            params.put("group", groupname);

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

    public void uploadImage(final Bitmap bitmap, Context context)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://djunicode.pythonanywhere.com/file/upload",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
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
                params.put("file", getStringImage(bitmap));
                return params;
            }
        };

        reqQueue.add(postRequest);
    }

    public String getStringImage(Bitmap bm)
    {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,ba);
        byte[] imagebyte = ba.toByteArray();
        String encode = Base64.encodeToString(imagebyte, Base64.DEFAULT);
        return encode;
    }
}
