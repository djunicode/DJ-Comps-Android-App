package io.github.djunicode.djcomps;

import android.content.Context;
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

import io.github.djunicode.djcomps.Database.Data.File;

public class HTTPRequests {

    public Context context;
    List<File> files;
    File file;
    String userinfo;
    List<String> usersinfo;


    public void onLoginRequest(final String sap_id, final String password) {

        String url = "http://localhost";



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


    public void getAllFiles(final String start_idx, final String end_idx, final String sort_by, final String sort_order, Context context) {

        String url = "http://localhost";
        files = new ArrayList<>();
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
                                        o.getString("description")
                                );
                                files.add(item);
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
                params.put("sort_by", sort_by);
                params.put("sort_order", sort_order);

                return params;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(postRequest);

    }

    public void getAllFilesByUser(final String start_idx, final String end_idx, final String sap_id, final String sort_by, final String sort_order, Context context) {

        String url = "http://localhost";
        files = new ArrayList<>();
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
                                        o.getString("description")
                                );
                                files.add(item);
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
                params.put("sort_by", sort_by);
                params.put("sort_order", sort_order);

                return params;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(postRequest);

    }

    public void getAllFilesByName(final String start_idx, final String end_idx, final String name, final String sort_by, final String sort_order, Context context) {

        String url = "http://localhost";
        files = new ArrayList<>();
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
                                        o.getString("description")
                                );
                                files.add(item);
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
                params.put("sort_by", sort_by);
                params.put("sort_order", sort_order);

                return params;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(postRequest);

    }

    public void getAllFilesByType(final String start_idx, final String end_idx, final String type, final String sort_by, final String sort_order, Context context) {

        String url = "http://localhost";
        files = new ArrayList<>();
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
                                        o.getString("description")
                                );
                                files.add(item);
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
                params.put("sort_by", sort_by);
                params.put("sort_order", sort_order);

                return params;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(postRequest);

    }

    public File getFileInfo(final String file_id, Context context)
    {
        String url = "http://localhost";
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
                                    o.getString("description")
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

}
