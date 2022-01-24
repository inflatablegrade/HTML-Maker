package com.example.htmlmaker;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import com.example.htmlmaker.FileOpen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();

        try{
            String content = extras.getString("contentToLoad");
            System.out.println("Loaded content: " + content);
            EditText edit = (EditText) findViewById(R.id.editTextTextMultiLine2);
            edit.setText(content);
            WebView preview = findViewById(R.id.webView1);

            preview.loadData(content,"text/html", "utf8");
        }
        catch (Exception e){

        }

    }

    public void renderHTML(View v){
//        Button button = (Button) findViewById(R.id.button);
        EditText edit = (EditText) findViewById(R.id.editTextTextMultiLine2);
        String currentStr = edit.getText().toString();
        WebView preview = findViewById(R.id.webView1);

        preview.loadData(currentStr,"text/html", "utf8");

//        preview.evaluateJavascript(currentStr, new ValueCallback<String>() {
        //           @Override
//            public void onReceiveValue(String value) {
//            }
//        });
    }

    public void saveHTML(View v){
        EditText edit = (EditText) findViewById(R.id.editTextTextMultiLine2);
        String currentStr = edit.getText().toString();

//        FileNameDialogFragment namePrompt = new FileNameDialogFragment();
//        namePrompt.show(getSupportFragmentManager(), "FileNameDialogFragment");
        Intent intent = new Intent(this, FileNameDialogFragment.class);
        intent.putExtra("data",currentStr);
        startActivity(intent);



//         AlertDialog prompt = new AlertDialog.Builder();

//        AlertDialog prompt = new AlertDialog.Builder(context);
        //        AlertDialogActivity.this);
    }

    public void loadHTML(View v){
        // new intent to:
        // - pick a file and open it
        // - load the contents into the text box and web view
        Intent intent = new Intent(this, FileOpen.class);
        intent.putExtra("out", true);
        startActivity(intent);

//        Bundle extras = getIntent().getExtras();
//        String loadContent = extras.getString("contentToLoad");
//        EditText edit = (EditText) findViewById(R.id.editTextTextMultiLine2);
//        edit.setText(loadContent);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
//        if (resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            System.out.println("Returning to main screen...");
            Bundle extras = getIntent().getExtras();
            String content = extras.getString("contentToLoad");
            System.out.println("Loaded content: " + content);
            EditText edit = (EditText) findViewById(R.id.editTextTextMultiLine2);
            edit.setText(content);

            if (resultData != null) {


            }
//        }
    }
}