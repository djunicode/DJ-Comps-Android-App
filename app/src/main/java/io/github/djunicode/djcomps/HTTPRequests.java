package io.github.djunicode.djcomps;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.djunicode.djcomps.adapters.FileAdapter;
import io.github.djunicode.djcomps.database.data.File;

public class HTTPRequests {

    public Context context;
    static File file;
    String userinfo;
    List<String> usersinfo;


    public static void onLoginRequest(final String sap_id, final String password) {

        String url = "http://127.0.0.1:8000";



        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject  = new JSONObject(response);
                            JSONObject o = jsonObject.getJSONObject(response);
                            o.get("token");
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

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sap_id", sap_id);
        params.put("password", password);

        ApplicationController.getInstance().addToRequestQueue(postRequest);

    }

    public String getUser(final String sap_id) {

        String url = "http://localhost";


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject  = new JSONObject(response);
                            JSONObject o = jsonObject.getJSONObject(response);
                            o.get(userinfo);
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

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sap_id", sap_id);

        ApplicationController.getInstance().addToRequestQueue(postRequest);

        return userinfo;
    }

    public List getUserByName(final String name) {

        String url = "http://localhost";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                String item = new String();
                                o.get(item);
                                usersinfo.add(item);
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

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);

        ApplicationController.getInstance().addToRequestQueue(postRequest);
        return usersinfo;
    }

    public List getUsersByGroup(final String groupname) {

        String url = "http://localhost";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                String item = new String();
                                o.get(item);
                                usersinfo.add(item);
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

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("group", groupname);

        ApplicationController.getInstance().addToRequestQueue(postRequest);
        return usersinfo;
    }


    public static void getAllFiles(final FileAdapter adapter, String url, final String start_idx, final String end_idx, final String sort_by, final String sort_order) {

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
                Map<String, String> params = new HashMap<String, String>();
                params.put("start_idx", start_idx);
                params.put("end_idx", end_idx);
                if(sort_by != null)
                    params.put("sort_by", sort_by);
                if(sort_order != null)
                    params.put("sort_order", sort_order);

                return params;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(postRequest);

    }

    public static void getAllFilesByUser(final FileAdapter adapter, String url, final String start_idx, final String end_idx, final String sap_id, final String sort_by, final String sort_order) {

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
                Map<String, String> params = new HashMap<String, String>();
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

        ApplicationController.getInstance().addToRequestQueue(postRequest);

    }

    public static void getAllFilesByName(final FileAdapter adapter, String url, final String start_idx, final String end_idx, final String name, final String sort_by, final String sort_order) {

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
                Map<String, String> params = new HashMap<String, String>();
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

        ApplicationController.getInstance().addToRequestQueue(postRequest);

    }

    public static void getAllFilesByType(final FileAdapter adapter, String url, final String start_idx, final String end_idx, final String type, final String sort_by, final String sort_order) {

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
                Map<String, String> params = new HashMap<String, String>();
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

        ApplicationController.getInstance().addToRequestQueue(postRequest);

    }

    public static File getFileInfo(String url, final String file_id)
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
                            file = item;

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
                Map<String, String> params = new HashMap<String, String>();
                params.put("file_id", file_id);
                return params;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(postRequest);

        return file;
    }


    public static void loadFileData(final String file_id, final Context context)
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("file_id", file_id);
                return params;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(postRequest);
    }
}
