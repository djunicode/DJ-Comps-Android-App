package io.github.djunicode.djcomps;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class MultipartRequest extends AsyncTask<Void, Void, Void> {

    private String url;
    private WeakReference<Context> wrContext;
    private Uri fileUri;
    private Map<String, String> params;
    private String filefield;
    private ProgressDialog progressDialog;

    MultipartRequest(Context context, String url, String filefield, Uri fileUri, Map<String, String> params){
        this.wrContext = new WeakReference<>(context);
        this.url = url;
        this.fileUri = fileUri;
        this.params = params;
        this.filefield = filefield;
    }

    @Override
    protected void onPreExecute(){
        progressDialog = new ProgressDialog(wrContext.get());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading");
        progressDialog.setCancelable(false);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    @Override
    protected final Void doInBackground(Void... aVoid) {
        sendRequest(url, params, fileUri);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPreExecute();
        progressDialog.dismiss();
    }



    private String sendRequest(String urlTo, Map<String, String> parmas, Uri fileUri) {
        HttpURLConnection connection;
        DataOutputStream outputStream;
        InputStream inputStream;

        String twoHyphens = "--";
        String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
        String lineEnd = "\r\n";

        String result = "";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 128 * 1024;

        String fileName = Utils.getFileName(wrContext.get(), fileUri);
        String fileMimeType = Utils.getMimeType(wrContext.get(), fileUri);

        ContentResolver contentResolver = wrContext.get().getContentResolver();

        try {
            InputStream fileInputStream = contentResolver.openInputStream(fileUri);

            URL url = new URL(urlTo);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + fileName + "\"" + lineEnd);
            outputStream.writeBytes("Content-Type: " + fileMimeType + lineEnd);
            outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);

            outputStream.writeBytes(lineEnd);

            if(fileInputStream == null)
                throw new Exception("Invalid file");
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            long totalBytes = bytesAvailable;
            long totalBytesUploaded = 0;

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                totalBytesUploaded += bufferSize;
                progressDialog.setProgress((int) (100.0*totalBytesUploaded/totalBytes));
            }

            progressDialog.setProgress(100);

            outputStream.writeBytes(lineEnd);

            // Upload POST Data
            for (String key : parmas.keySet()) {
                String value = parmas.get(key);

                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                outputStream.writeBytes("Content-Type: text/plain" + lineEnd);
                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(value);
                outputStream.writeBytes(lineEnd);
            }

            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


            if (201 != connection.getResponseCode() || 200 != connection.getResponseCode()) {
                throw new Exception("Failed to upload code:" + connection.getResponseCode() + " " + connection.getResponseMessage());
            }

            inputStream = connection.getInputStream();

            result = this.convertStreamToString(inputStream);

            fileInputStream.close();
            inputStream.close();
            outputStream.flush();
            outputStream.close();

            return result;
        } catch (Exception e) {
            Log.e("Multipart Upload Error", e.getMessage());
            e.printStackTrace();
        }


        return result;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
