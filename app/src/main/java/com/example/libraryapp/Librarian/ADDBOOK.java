package com.example.libraryapp.Librarian;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ADDBOOK extends AppCompatActivity {

    ProgressBar simpleProgressBar;
    EditText name, author, publisher, desp, noodcopies, catid;
    ImageView bookimage;
    Button add;
    String sname, sauthor, spublisher, sdesp, snoodcopies, scatid;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    String path, path1,document_id;
    Cursor cursor;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        simpleProgressBar = (ProgressBar) findViewById(R.id.aab_simpleProgressBar);

        name=findViewById(R.id.aab_bookname);
        author=findViewById(R.id.aab_bookauthor);
        publisher=findViewById(R.id.aab_bookpublisher);
        desp=findViewById(R.id.aab_bookdesp);
        noodcopies=findViewById(R.id.aab_noofbookcopies);
        catid=findViewById(R.id.aab_catid);
        bookimage=findViewById(R.id.aab_bookimg);
        add=findViewById(R.id.aab_addbtn);
        requestStoragePermission();




        bookimage.setOnClickListener(v -> Filechooser());

        add.setOnClickListener(view -> {
            if (!validate()) {
                Toast.makeText(getApplicationContext(), "Enter valid data!", Toast.LENGTH_LONG).show();
            } else
            {
                path=getPath(filePath);
                if (path != null) {
                    imageUpload(path);
                } else {
                    Toast.makeText(getApplicationContext(), "Image not selected!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void Filechooser() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bookimage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(),    contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
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

    private void imageUpload(final String imagePath) {
        simpleProgressBar.setVisibility(View.VISIBLE);

        sname= name.getText().toString();
        sauthor= author.getText().toString();
        spublisher= publisher.getText().toString();
        sdesp= desp.getText().toString();
        snoodcopies= noodcopies.getText().toString();
        scatid= catid.getText().toString();
//        path= getPath(filePath);


        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, Config.addbookimage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ResponseISSUEBOOK", response);
                        simpleProgressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String message = jObj.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            bookimage.setImageResource(R.drawable.addbookimagebg);
                             name.setText("");
                             author.setText("");
                             publisher.setText("");
                             desp.setText("");
                             noodcopies.setText("");
                             catid.setText("");
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        smr.addFile("image", imagePath);
        smr.addStringParam("name",sname);
        smr.addStringParam("author", sauthor);
        smr.addStringParam("publisher", spublisher);
        smr.addStringParam("description", sdesp);
        smr.addStringParam("number", snoodcopies);
        smr.addStringParam("catid", scatid);
//        smr.addStringParam("image",path);
        mRequestQueue.add(smr);

    }

//        EditText name, author, publisher, desp, noodcopies, catid;
    private boolean validate() {
        if (name.getText().toString().trim().equals("")) {
            name.setError("Enter Book name");
            return false;
        } else if (author.getText().toString().trim().equals("")) {
            author.setError("Enter Author Name");
            return false;
        } else if (publisher.getText().toString().trim().equals("")) {
            publisher.setError("Enter Publisher name");
            return false;
        } else if (desp.getText().toString().trim().equals("") ) {
            desp.setError("Enter Department");
            return false;
        } else if (noodcopies.getText().toString().trim().equals("") ) {
            noodcopies.setError("Enter Nuber of copies");
            return false;
        } else if (catid.getText().toString().trim().equals("") ) {
            catid.setError("Enter category ID");
            return false;
        } else
            return true;


    }

}