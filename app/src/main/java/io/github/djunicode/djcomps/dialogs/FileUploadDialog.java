package io.github.djunicode.djcomps.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import io.github.djunicode.djcomps.HTTPRequests;
import io.github.djunicode.djcomps.R;
import io.github.djunicode.djcomps.Utils;

public class FileUploadDialog extends Dialog {

    private EditText filenameET, descriptionET;
    private Intent fileIntent;
    private Context context;

    public FileUploadDialog(@NonNull Context context, Intent fileData) {
        super(context);
        this.context = context;
        this.fileIntent = fileData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_file_upload);

        filenameET = findViewById(R.id.file_upload_filename);
        descriptionET = findViewById(R.id.file_upload_description);

        filenameET.setText(Utils.getFileName(getContext(), fileIntent.getData()));

        Button uploadButton = findViewById(R.id.file_upload_button);
        Button cancelButton = findViewById(R.id.file_upload_cancel);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fieldsVerified = true;

                String filename = filenameET.getText().toString();
                String description = descriptionET.getText().toString();

                if (TextUtils.isEmpty(filename)) {
                    filenameET.setError("Filename cannot be empty");
                    filenameET.requestFocus();
                    fieldsVerified = false;
                }
                if (TextUtils.isEmpty(description)) {
                    descriptionET.setError("Description cannot be empty");
                    descriptionET.requestFocus();
                    fieldsVerified = false;
                }

                if(fieldsVerified){
                    new HTTPRequests(context).uploadFileRequest(context, fileIntent.getData(),
                            filenameET.getText().toString(), descriptionET.getText().toString());
                    dismiss();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    //TODO: get type of file and set it to drop down spinner

}
