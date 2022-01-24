package com.example.htmlmaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileNameDialogFragment extends FragmentActivity { // extends DialogFragment extends FragmentActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_filename);
    }

    public void nameAndSaveHTML(View v) {
        EditText edit = (EditText) findViewById(R.id.fileNameInput);
        String name = edit.getText().toString();

        Bundle extras = getIntent().getExtras();
        String content = extras.getString("data");

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

        Intent saveIntent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        saveIntent.addCategory(Intent.CATEGORY_OPENABLE);
        saveIntent.setType("text/html");
        saveIntent.putExtra(Intent.EXTRA_TITLE, name + ".html");

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.
//        saveIntent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(saveIntent, 1);
//        System.out.println("Saved as: " + Environment.DIRECTORY_DOCUMENTS + "/" + name+".html");
    }


    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;

            Bundle extras = getIntent().getExtras();
            String content = extras.getString("data");
//            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

            if (resultData != null) {
                uri = resultData.getData();
                // Perform operations on the document using its URI.
                System.out.println("Trying to access: " + uri);
                alterDocument(uri, content);
            }
        }
    }

    private void alterDocument(Uri uri, String str) {
        try {
            ParcelFileDescriptor pfd = this.getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write((str).getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //       public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the Builder class for convenient dialog construction
//           AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            System.out.println("Preparing to save...");
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        TextInputLayout dialogInput = new TextInputLayout();
//           builder.setTitle("Enter file name here");

//          LayoutInflater inflater = this.getLayoutInflater();
//          builder.setView(inflater.inflate(R.layout.dialog_filename, null));
//          Context context = builder.getContext();

//        builder.setMessage(R.string.dialog_fire_missiles)
//          builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
              //              public void onClick(DialogInterface dialog, int id) { // id = button tapped
//                        this.findViewById(R.id.fileNameInput);
//                  FragmentManager fragmentManager = getSupportFragmentManager();
//                  Fragment frag = fragmentManager.findFragmentByTag("FileNameDialogFragment");
//                  EditText eText = (EditText) findViewById(R.id.fileNameInput);
//                  String s = eText.getText().toString();
              //                 System.out.println("Logging: " + s);
//              }
//         });
//            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
                // Cancelled
            //               dialog.cancel();
//                }
//          });
           // Create the AlertDialog object and return it
//           return builder.create();
//        }
//        Dialog();
}
