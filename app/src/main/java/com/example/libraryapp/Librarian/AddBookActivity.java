package com.example.libraryapp.Librarian;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.R;


//import net.gotev.uploadservice.MultipartUploadRequest;
//import net.gotev.uploadservice.UploadNotificationConfig;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddBookActivity extends AppCompatActivity {

    EditText name, author, publisher, desp, noodcopies, catid;
    ImageView bookimage;
    Button add;
    String sname, sauthor, spublisher, sdesp, snoodcopies, scatid;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    String path, path1,document_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

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

//            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
//            final byte[] imageBytes1 = baos1.toByteArray();
//            path = Base64.encodeToString(imageBytes1, Base64.DEFAULT);

//            path=filePath.toString();
            path=getPath(filePath);
            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();

            if (path != null) {
                imageUpload(path);
            } else {
                Toast.makeText(getApplicationContext(), "Image not selected!", Toast.LENGTH_LONG).show();
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
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                //Setting image to ImageView
                bookimage.setImageBitmap(bitmap);

                Toast.makeText(this, filePath.toString(), Toast.LENGTH_SHORT).show();
//                encodeBitmapImage(bitmap);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private String  getPath(Uri contentUri) {


        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            path1 = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path1 = cursor.getString(idx);
            cursor.close();
        }
        Log.v("VALUE1234565", contentUri.toString());
        return path1;
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
        sname= name.getText().toString();
        sauthor= author.getText().toString();
        spublisher= publisher.getText().toString();
        sdesp= desp.getText().toString();
        snoodcopies= noodcopies.getText().toString();
        scatid= catid.getText().toString();

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, Config.addbookimage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String message = jObj.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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

}


