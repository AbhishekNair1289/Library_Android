package com.example.libraryapp.Librarian;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.Librarian.ModelClass.FilePath;
import com.example.libraryapp.R;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class AddQPActivity extends AppCompatActivity {

    AutoCompleteTextView sub, dep, sem, year;
    String ssub, sdep, ssem, syear;
    ImageButton selectfile;
    TextView attachfiletxt;
    ImageView add;

    private int REQ_PDF = 21;

    private String encodedPDF;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    String path;
    String filename;
    List<String> semarraylist = new ArrayList<>();
    List<String> deptArraylist = new ArrayList<>();
    List<String> subjectArraylist = new ArrayList<>();
    String[] yeararray = {"2000" , "2001" , "2002" , "2003" , "2004" , "2005" , "2006" , "2007" , "2008" , "2009" , "2010" , "2011" , "2012" , "2013" , "2014" , "2015" , "2016" , "2017" , "2018" , "2019" , "2020" , "2021" , "2022" , "2023" , "2024" , "2025"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_q_p);

        sub=findViewById(R.id.aqp_subject);
        dep=findViewById(R.id.aqp_department);
        sem=findViewById(R.id.aqp_sem);
        year=findViewById(R.id.aqp_year);
        selectfile=findViewById(R.id.aqp_attachfile);
        attachfiletxt=findViewById(R.id.aqp_attachfiletxt);
        add=findViewById(R.id.aqp_addbtn);

        getsubjects();
        getDepartments();
        getSemesters();
        getyears();
        requestStoragePermission();

        selectfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    Toast.makeText(getApplicationContext(), "Enter valid data!", Toast.LENGTH_LONG).show();
                } else
                {
                    if (path != null) {
                        imageUpload(path);
                    } else {
                        Toast.makeText(getApplicationContext(), "Question Paper not selected!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_PDF && resultCode == RESULT_OK && data!=null){
            if (requestCode == REQ_PDF && resultCode == RESULT_OK) {
                path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                attachfiletxt.setText(path);
            }

        }
    }

    private void imageUpload(final String imagePath) {
        syear= year.getText().toString();
        ssem= sem.getText().toString();
        ssub= sub.getText().toString();
        sdep= dep.getText().toString();
        String filename= ssub+ " "+ssem+" "+syear;

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, Config.addquestionpaper,
                response -> {
                    Log.d("Response", response);
                    try {
                        JSONObject jObj = new JSONObject(response);
                        String message = jObj.getString("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        year.setText("");
                        sem.setText("");
                        sub.setText("");
                        dep.setText("");
                        attachfiletxt.setText("");
                        path = "";
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show());

        smr.addFile("image", imagePath);
        smr.addStringParam("department",sdep);
        smr.addStringParam("sem", ssem);
        smr.addStringParam("year", syear);
        smr.addStringParam("subject", ssub);
        smr.addStringParam("pdfname", filename);

        mRequestQueue.add(smr);

    }

    //FilePicker
    public void showFileChooser() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(REQ_PDF)
                .withFilter(Pattern.compile(".*\\.(pdf)$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(false) // Set directories filterable (false by default)
                .withHiddenFiles(true)
                // Show hidden files and folders
                .start();
    }

    private boolean validate() {
        if (sub.getText().toString().trim().equals("")) {
            sub.setError("Enter Book name");
            return false;
        } else if (sem.getText().toString().trim().equals("")) {
            sem.setError("Enter Author Name");
            return false;
        } else if (year.getText().toString().trim().equals("")) {
            year.setError("Enter Publisher name");
            return false;
        } else if (dep.getText().toString().trim().equals("") ) {
            dep.setError("Enter Department");
            return false;
        } else
            return true;


    }

    private void getSemesters() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.view_sem,
                response -> {
                    Log.v("HELLOSEMESTERS",response);
                    if(response!=null)
                    {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i = 0; i < jsonArray.length(); i++){
                                semarraylist.add(jsonArray.getJSONObject(i).getString("tbl_sem_sem"));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.layout_item_autocomplete, R.id.liac_tv_custom, semarraylist);
                            sem.setAdapter(arrayAdapter);
                            sem.setThreshold(1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_SHORT).show());
        queue.add(stringRequest);
    }

    private void getDepartments() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.view_department,
                response -> {
                    Log.v("HELLODEPARTMENT",response);
                    if(response!=null)
                    {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i = 0; i < jsonArray.length(); i++){
                                deptArraylist.add(jsonArray.getJSONObject(i).getString("tbl_department_dpt"));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(AddQPActivity.this, R.layout.layout_item_autocomplete, R.id.liac_tv_custom, deptArraylist);
                            dep.setAdapter(arrayAdapter);
                            dep.setThreshold(1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_SHORT).show());
        queue.add(stringRequest);
    }

    private void getsubjects() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.view_subject,
                response -> {
                    Log.v("HELLOSUBJECT",response);
                    if(response!=null)
                    {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i = 0; i < jsonArray.length(); i++){
                                subjectArraylist.add(jsonArray.getJSONObject(i).getString("tbl_subject_sub"));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.layout_item_autocomplete, R.id.liac_tv_custom, subjectArraylist);
                            sub.setAdapter(arrayAdapter);
                            sub.setThreshold(1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_SHORT).show());
        queue.add(stringRequest);
    }

    private void getyears() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.layout_item_autocomplete, R.id.liac_tv_custom, getResources().getStringArray(R.array.years));
        year.setAdapter(arrayAdapter);
        year.setThreshold(1);
    }


}