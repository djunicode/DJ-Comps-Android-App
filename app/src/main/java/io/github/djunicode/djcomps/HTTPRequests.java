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

import io.github.djunicode.djcomps.adapters.UserAdapter;
import io.github.djunicode.djcomps.database.data.File;
import io.github.djunicode.djcomps.database.data.User;

public class HTTPRequests {

    public Context context;
    List<File> files;
    File file;


    public static void onLoginRequest(final String sap_id, final String password, final UserAdapter adapter) {

        String url = "http://djunicode.pythonanywhere.com/login";

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

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sap_id", sap_id);
        params.put("password", password);

        ApplicationController.getInstance().addToRequestQueue(postRequest);

    }

    public static void getUser(final String sap_id, final UserAdapter adapter) {

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

        HashMap<String, String> params = new HashMap<String, String>();
        if(sap_id!=null)
            params.put("sap_id", sap_id);

        ApplicationController.getInstance().addToRequestQueue(postRequest);

    }

    public static void getUserByName(final String name, final UserAdapter adapter) {

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

        HashMap<String, String> params = new HashMap<String, String>();
        if(name!=null)
            params.put("name", name);

        ApplicationController.getInstance().addToRequestQueue(postRequest);
    }

    public static void getUsersByGroup(final String groupname, final UserAdapter adapter) {

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

        HashMap<String, String> params = new HashMap<String, String>();
        if(groupname!=null)
            params.put("group", groupname);

        ApplicationController.getInstance().addToRequestQueue(postRequest);
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
                                        o.getString("description"), false, false
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
                                        o.getString("description"), false, false
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
                                        o.getString("description"), false, false
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
                                        o.getString("description"), false, false
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

}
