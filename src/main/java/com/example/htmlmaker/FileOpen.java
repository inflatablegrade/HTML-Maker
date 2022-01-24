package com.example.htmlmaker;

import static java.io.FileInputStream.*;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.lang.Byte;

public class FileOpen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openHTML();
    }

    public void openHTML(){

//        openIntent.putExtra(Intent.EXTRA_TITLE, name + ".html");

        Bundle extras = getIntent().getExtras();
        boolean isOut = extras.getBoolean("out");

        if(isOut){
            Intent openIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            openIntent.addCategory(Intent.CATEGORY_OPENABLE);
            openIntent.setType("text/html");
            startActivityForResult(openIntent, 2);
        }
        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.
//        saveIntent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;

            Bundle extras = getIntent().getExtras();
            String content = extras.getString("data");
//            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

            if (resultData != null) {
                uri = resultData.getData();
                // Perform operations on the document using its URI.
                System.out.println("Trying to load: " + uri);
                loadDocumentIntoApp(uri, content);
            }
        }
    }

    private void loadDocumentIntoApp(Uri uri, String str) {
        try {
            ParcelFileDescriptor pfd = this.getContentResolver().
                    openFileDescriptor(uri, "r");
            FileInputStream fileInputStream =
                    new FileInputStream(pfd.getFileDescriptor());

            int offset = 0;
            String allChars = "";
            System.out.println("About to read file...");

            for(int i=0; i<2; i++){
                byte[] readBytes = new byte[2048];
                int number = fileInputStream.read(readBytes, offset, 2048);
                String temp = "";
                int len = getEffectiveLength(readBytes);
//                System.out.println("Length: " + len);

                if(len > 0){
                    for(int j=0; j<len; j++){
                        char character = ((char) readBytes[j]);
                        temp = temp+ character + "";
                        if(len<2048){
                            i=1;
                        }
                        else{
                            i=0;
                        }
                    }

                }
                else{
                    break;
                }
                offset = offset + 2048;
                allChars = allChars + temp;
            }
            System.out.println("Content: " + allChars);

            // Let the document provider know you're done by closing the stream.
            fileInputStream.close();
            pfd.close();

            // Send back an intent to MainActivity to load allChars into the text box
//            EditText edit = (EditText) findViewById(R.id.editTextTextMultiLine2);
//            edit.setText(allChars);
            Intent returnIntent = new Intent(this, MainActivity.class);
            returnIntent.addCategory(Intent.CATEGORY_OPENABLE);
            returnIntent.putExtra("contentToLoad", allChars);
            startActivity(returnIntent);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] concatArrays(byte[] arr1, byte[] arr2){
        int len1 = arr1.length;
        int len2 = arr2.length;

        byte[] res = Arrays.copyOf(arr1, len1 + len2);
        res = Arrays.copyOfRange(res, len1, len2);

        return res;
    }

    public int getEffectiveLength(byte[] a){
        int res = 0;
        for(int i=0; i<a.length; i++){
            int code = a[i];
            if(code==0){
                res = i;
                break;
            }
        }
        return res;
    }
}
