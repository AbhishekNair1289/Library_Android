//package com.example.libraryapp;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.core.content.FileProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.Manifest;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.MultiAutoCompleteTextView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.NetworkResponse;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.request.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.google.gson.Gson;
//
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class AddProducts extends AppCompatActivity {
//    RecyclerView feature;
//    ImageView Addfeature;
//
//    Button save;
//    EditText name, mrp, selling, details;
//    MultiAutoCompleteTextView size;
//    Spinner subcat, brand, cat_spinner, gender;
//    ImageView profile, driving, vehicle, book;
//    Uri profile_uri, license_uri, vehicle_uri, book_uri;
//    ProgressDialog progress;
//    int sex;
//    String newstr = null;
//    Dialog dialog2;
//    Toolbar toolbar;
//    BottomSheetDialog dialog;
//    ImageView gallery, camera, close;
//    String filePath = "", subcat_id = "", brand_id = "";
//    ImageView profile_nodata, driving_nodata, vehicle_nodata, book_nodata;
//    Bitmap profile_bitmap, licence_bitmap, vehicle_bitmap, book_bitmap;
//    ArrayList<SpinnerData> subcat_model = new ArrayList<>();
//    ArrayList<SpinnerData> brand_model = new ArrayList<>();
//    List<String> sub_name = new ArrayList<>();
//    List<String> brand_name = new ArrayList<>();
//    ArrayList<SpinnerData> cat_model = new ArrayList<>();
//    List<String> catname = new ArrayList<>();
//    String cat_id = "";
//    String genders[] = {"MENS", "WOMENS", "KIDS"};
//    String[] sizes = {"S", "M", "L", "XL", "XXL", "3/4/S", "5/6/M", "7/8/L", "9/10/XL", "11/12/2XL", "XXL(US 12)", "3XL(US 14)", "4XL(US 16)", "5XL(US 18)"};
//    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
//    public static final int PROFILE_CAMERA = 1;
//    public static final int PROFILE_GALLERY = 2;
//    public static final int LICENCE_CAMERA = 3;
//    public static final int LICENCE_GALLERY = 4;
//    public static final int VEHICLE_CAMERA = 5;
//    public static final int VEHICLE_GALLEY = 6;
//    public static final int BOOK_CAMERA = 7;
//    public static final int BOOK_GALLERY = 8;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_products);
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////        getSupportActionBar().setTitle("Add Product");
//        dialog2=new Dialog(AddProducts.this);
//        toolbar = findViewById(R.id.toolbaraddProduct);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//        progress = new ProgressDialog(AddProducts.this);
//        progress.setMessage("Please wait...");
//        progress.setCancelable(false);
//        progress.setCanceledOnTouchOutside(false);
//        save = findViewById(R.id.save);
//        name = findViewById(R.id.name);
//        mrp = findViewById(R.id.mrp);
//        gender = findViewById(R.id.gender);
//        selling = findViewById(R.id.selling);
//        size = findViewById(R.id.size);
//        details = findViewById(R.id.details);
//        subcat = findViewById(R.id.subcat);
//        brand = findViewById(R.id.brand);
//        profile = findViewById(R.id.profile);
//        driving = findViewById(R.id.driving);
//        vehicle = findViewById(R.id.vehicle);
//        cat_spinner = findViewById(R.id.cat_spinner);
//        //  book = findViewById(R.id.book);
//        profile_nodata = findViewById(R.id.profile_nodata);
//        driving_nodata = findViewById(R.id.driving_nodata);
//        feature=findViewById(R.id.feature);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        feature.setLayoutManager(linearLayoutManager);
//        featuersAdapter=new FeatuersAdapter(getApplicationContext(),featuerslist);
//        feature.setAdapter(featuersAdapter);
//        Addfeature=findViewById(R.id.addd);
//
//
//        Addfeature.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showalert();
//            }
//        });
//
//
//
//        vehicle_nodata = findViewById(R.id.vehicle_nodata);
//        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genders);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        gender.setAdapter(aa);
//
//        //  book_nodata = findViewById(R.id.book_nodata);
//
//        ArrayAdapter<String> sizeees = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sizes);
//        size.setAdapter(sizeees);
//        size.setThreshold(1);
//        size.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
//        View modalbottomsheet = getLayoutInflater().inflate(R.layout.bottom_sheet_sample, null);
//        dialog = new BottomSheetDialog(this);
//        dialog.setContentView(modalbottomsheet);
//        dialog.setTitle("Choose item");
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
//        camera = dialog.findViewById(R.id.camera);
//        close = dialog.findViewById(R.id.close);
//        gallery = dialog.findViewById(R.id.gallery);
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkPermission(PROFILE_CAMERA, PROFILE_GALLERY);
//            }
//        });
//        driving.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkPermission(LICENCE_CAMERA, LICENCE_GALLERY);
//            }
//        });
//        vehicle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkPermission(VEHICLE_CAMERA, VEHICLE_GALLEY);
//            }
//        });
//
//        get_category();
//        get_brands();
//
//        cat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                TextView textView = view.findViewById(android.R.id.text1);
//                if (position == 0) {
//                    textView.setTextColor(Color.GRAY);
//                    cat_id = "";
//                } else {
//                    textView.setTextColor(Color.BLACK);
//                    cat_id = cat_model.get(position - 1).getId();
//                    get_subcategory();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//        subcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                TextView textView = view.findViewById(android.R.id.text1);
//                if (position == 0) {
//                    textView.setTextColor(Color.GRAY);
//                    subcat_id = "";
//                } else {
//                    textView.setTextColor(Color.BLACK);
//                    subcat_id = subcat_model.get(position - 1).getId();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                TextView textView = view.findViewById(android.R.id.text1);
//                if (position == 0) {
//                    textView.setTextColor(Color.GRAY);
//                    brand_id = "";
//                } else {
//                    if (position == 1) {
//                        brand_id = "0";
//                    } else {
//                        textView.setTextColor(Color.BLACK);
//                        brand_id = brand_model.get(position - 2).getId();
//                    }
//
//                }
//                Log.e("brand_id", brand_id);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(validation()) {
//
//                    newstr = size.getText().toString().trim();
//                    Gson gson = new Gson();
//                    String model = gson.toJson(featuerslist);
//                    Log.e("select", model);
////
////                        if (newstr.length() > 0)
////                        {
//                    if (newstr.substring(newstr.length() - 1).equals(","))
//                    {
//                        size.setText(newstr.substring(0, newstr.length() - 1));
//                        save_data(model);
//                    }
//                    //   }
//                }
//            }
//        });
//
//
//    }
//
//
//
//
//    private void showalert() {
//
//        dialog2.setContentView(R.layout.alertaddfeature);
//        dialog2.setCancelable(true);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog2.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        // lp.height=WindowManager.LayoutParams.MATCH_PARENT;
//        Button save = (Button) dialog2.findViewById(R.id.add);
//        final EditText name = (EditText) dialog2.findViewById(R.id.feat);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(name.getText().toString().length()>0){
//                    featuerslist.add(new Featuers(name.getText().toString()));
//                    featuersAdapter.notifyDataSetChanged();
//                    dialog2.dismiss();
//
//
//                }
//            }
//        });
//        dialog2.getWindow().setAttributes(lp);
//        dialog2.show();
//
//
//    }
//
//    private void save_data(final String model) {
//        progress.show();
//        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.PRODUCT_ADD,
//                new Response.Listener<NetworkResponse>() {
//                    @Override
//                    public void onResponse(NetworkResponse response) {
//                        Log.e("resp", new String(response.data));
//                        progress.dismiss();
//
//                        try {
//                            JSONObject ob = new JSONObject(new String(response.data));
//                            if (ob.getBoolean("status")) {
//                                Toast.makeText(AddProducts.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
//                                finish();
//                            } else {
//                                Toast.makeText(AddProducts.this, "Something Went wrong !!", Toast.LENGTH_SHORT).show();
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progress.dismiss();
//                Log.e("error", new String(error.networkResponse.data));
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("name", name.getText().toString());
//                params.put("originalprice", mrp.getText().toString());
//                params.put("sellingprice", selling.getText().toString());
//                params.put("size", size.getText().toString());
//                params.put("subcategoryid", subcat_id);
//                params.put("features",model);
//                params.put("brand_id", brand_id);
//                if (gender.getSelectedItem().toString().equals("Male")) {
//                    sex = 1;
//                } else if (gender.getSelectedItem().toString().equals("Female")) {
//                    sex = 2;
//                } else {
//                    sex = 3;
//                }
//                params.put("gender", String.valueOf(sex));
//                params.put("details", details.getText().toString());
//                params.put("store_id", SharedPrefManager.getInstance(getApplicationContext()).getUser().getStore_id());
//
//                if (profile_bitmap == null) {
//                    params.put("productpic1", "");
//                }
//                if (licence_bitmap == null) {
//                    params.put("productpic2", "");
//                }
//                if (vehicle_bitmap == null) {
//                    params.put("productpic3", "");
//                }
//
//                Log.e("params", String.valueOf(params));
//                return params;
//            }
//
//            @Override
//            protected Map<String, DataPart> getByteData() throws AuthFailureError {
//                Map<String, DataPart> params = new HashMap<>();
//                long imagename = System.currentTimeMillis();
//                if (profile_bitmap != null) {
//                    params.put("productpic1", new DataPart(imagename + ".jpeg", getFileDataFromDrawable(profile_bitmap)));
//                }
//                if (licence_bitmap != null) {
//                    params.put("productpic2", new DataPart(imagename + ".jpeg", getFileDataFromDrawable(licence_bitmap)));
//                }
//                if (vehicle_bitmap != null) {
//                    params.put("productpic3", new DataPart(imagename + ".jpeg", getFileDataFromDrawable(vehicle_bitmap)));
//                }
////                if (book_bitmap != null) {
////                    params.put("productpic4", new DataPart(imagename + ".jpeg", getFileDataFromDrawable(book_bitmap)));
////                }
//                Log.e("data", String.valueOf(params));
//                return params;
//            }
//        };
//        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(90 * 1000,
//                1,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(this).add(volleyMultipartRequest);
//
//    }
//
//    private boolean validation() {
//        if (profile_bitmap == null && licence_bitmap == null && vehicle_bitmap == null) {
//            Toast.makeText(AddProducts.this, "Please choose Product image", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if(featuerslist.size()==0){
//            Toast.makeText(this, "Add atleast 1 feature", Toast.LENGTH_SHORT).show();
//            feature.requestFocus();
//            return  false;
//        }
//        if (name.getText().toString().isEmpty()) {
//            name.setError("Please fill this field !");
//            name.requestFocus();
//            return false;
//        }
//        if (mrp.getText().toString().isEmpty()) {
//            mrp.setError("Please fill this field !");
//            mrp.requestFocus();
//            return false;
//        }
//        if (selling.getText().toString().isEmpty()) {
//            selling.setError("Please fill this field !");
//            selling.requestFocus();
//            return false;
//        }
//        if (size.getText().toString().isEmpty()) {
//            size.setError("Please fill this field !");
//            size.requestFocus();
//            return false;
//        }
//        if (cat_id.isEmpty()) {
//            TextView error = (TextView) cat_spinner.getSelectedView();
//            error.setError("");
//            error.setTextColor(Color.RED);
//            error.setText("Choose Category !");
//            return false;
//        }
//
//        if (subcat_model.size() > 0) {
//            if (subcat_id.isEmpty()) {
//                TextView error = (TextView) subcat.getSelectedView();
//                error.setError("");
//                error.setTextColor(Color.RED);
//                error.setText("Choose Subcategory !");
//                return false;
//            }
//        } else {
//            Toast.makeText(this, "Choose other Category for subcategory !", Toast.LENGTH_LONG).show();
//            return false;
//        }
//
//        if (brand_id.isEmpty()) {
//            TextView error = (TextView) brand.getSelectedView();
//            error.setError("");
//            error.setTextColor(Color.RED);
//            error.setText("Choose Brand !");
//            return false;
//        }
//        if (details.getText().toString().isEmpty()) {
//            details.setError("Please fill this field !");
//            details.requestFocus();
//            return false;
//        }
//
//        return true;
//    }
//
//    private void get_subcategory() {
//        progress.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GET_SUBCATEGORY,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progress.dismiss();
//                        try {
//                            JSONObject ob = new JSONObject(response);
//                            Log.e("subcategory", response);
//
//                            if (ob.getBoolean("status")) {
//                                JSONArray array = ob.getJSONArray("subcategoryList");
//                                subcat_model = new ArrayList<>();
//                                sub_name = new ArrayList<>();
//                                sub_name.add("Choose Subcategory");
//                                for (int i = 0; i < array.length(); i++) {
//                                    JSONObject obb = array.getJSONObject(i);
//                                    subcat_model.add(new SpinnerData(obb.getString("subcategory_id"), obb.getString("subcategory_name")));
//                                    sub_name.add(obb.getString("subcategory_name"));
//                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, sub_name) {
//                                        @Override
//                                        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                                            View view = super.getDropDownView(position, convertView, parent);
//                                            TextView tv = (TextView) view;
//                                            if (position == 0) {
//                                                tv.setTextColor(Color.GRAY);
//                                            } else {
//                                                tv.setTextColor(Color.BLACK);
//                                            }
//                                            return view;
//                                        }
//                                    };
//                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    subcat.setAdapter(adapter);
//                                }
//
//
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progress.dismiss();
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("store_id", SharedPrefManager.getInstance(getApplicationContext()).getUser().getStore_id());
//                params.put("store_name", SharedPrefManager.getInstance(getApplicationContext()).getUser().getStore_name());
//                params.put("category_id", cat_id);
//                Log.e("params_sub", String.valueOf(params));
//                return params;
//            }
//        };
//        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//    }
//
//    private void get_brands() {
//        progress.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GET_BRANDS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progress.dismiss();
//                        try {
//                            JSONObject ob = new JSONObject(response);
//                            Log.e("brands", response);
//
//                            if (ob.getBoolean("status")) {
//                                JSONArray array = ob.getJSONArray("brandslist");
//                                brand_model = new ArrayList<>();
//                                brand_name = new ArrayList<>();
//                                brand_name.add("Choose Brand");
//                                brand_name.add("No Brand");
//                                for (int i = 0; i < array.length(); i++) {
//                                    JSONObject obb = array.getJSONObject(i);
//                                    brand_model.add(new SpinnerData(obb.getString("brand_id"), obb.getString("brand_name")));
//                                    brand_name.add(obb.getString("brand_name"));
//                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, brand_name) {
//                                        @Override
//                                        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                                            View view = super.getDropDownView(position, convertView, parent);
//                                            TextView tv = (TextView) view;
//                                            if (position == 0) {
//                                                tv.setTextColor(Color.GRAY);
//                                            } else {
//                                                tv.setTextColor(Color.BLACK);
//                                            }
//                                            return view;
//                                        }
//                                    };
//                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    brand.setAdapter(adapter);
//                                }
//
//
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progress.dismiss();
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("store_id", SharedPrefManager.getInstance(getApplicationContext()).getUser().getStore_id());
//                Log.e("brands input", params.toString());
//                return params;
//            }
//        };
//        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PROFILE_GALLERY) {
//
//            if (resultCode == RESULT_OK) {
//                Uri imageUri = data.getData();
//                profile_nodata.setVisibility(View.GONE);
//                profile_uri = imageUri;
//                try {
//                    profile_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), profile_uri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                //filePath = getPath(profile_uri);
//                if (filePath != null) {
//
//                } else {
//                    Toast.makeText(this, "No Image Selected !!", Toast.LENGTH_SHORT).show();
//                }
//                profile.setImageBitmap(profile_bitmap);
//            }
//
//        }
//        if (requestCode == PROFILE_CAMERA) {
//            profile_nodata.setVisibility(View.GONE);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            profile_bitmap = BitmapFactory.decodeFile(filePath, options);
//            profile.setImageBitmap(profile_bitmap);
//        }
//        if (requestCode == LICENCE_GALLERY) {
//            if (resultCode == RESULT_OK) {
//                Uri imageUri = data.getData();
//                driving_nodata.setVisibility(View.GONE);
//                license_uri = imageUri;
//                //filePath = getPath(license_uri);
//                try {
//                    licence_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), license_uri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (filePath != null) {
//
//                } else {
//                    Toast.makeText(this, "No Image Selected !!", Toast.LENGTH_SHORT).show();
//                }
//                driving.setImageBitmap(licence_bitmap);
//            }
//        }
//        if (requestCode == LICENCE_CAMERA) {
//            driving_nodata.setVisibility(View.GONE);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            licence_bitmap = BitmapFactory.decodeFile(filePath, options);
//            driving.setImageBitmap(licence_bitmap);
//        }
//        if (requestCode == VEHICLE_GALLEY) {
//            if (resultCode == RESULT_OK) {
//                Uri imageUri = data.getData();
//                vehicle_nodata.setVisibility(View.GONE);
//                vehicle_uri = imageUri;
//                //filePath = getPath(vehicle_uri);
//                try {
//                    vehicle_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), vehicle_uri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (filePath != null) {
//
//                } else {
//                    Toast.makeText(this, "No Image Selected !!", Toast.LENGTH_SHORT).show();
//                }
//                vehicle.setImageBitmap(vehicle_bitmap);
//            }
//
//        }
//        if (requestCode == VEHICLE_CAMERA) {
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            vehicle_bitmap = BitmapFactory.decodeFile(filePath, options);
//            vehicle.setImageBitmap(vehicle_bitmap);
//            vehicle_nodata.setVisibility(View.GONE);
//        }
//        if (requestCode == BOOK_GALLERY) {
//            if (resultCode == RESULT_OK) {
//                Uri imageUri = data.getData();
//                book_nodata.setVisibility(View.GONE);
//                book_uri = imageUri;
//                //filePath = getPath(book_uri);
//                try {
//                    book_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), book_uri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (filePath != null) {
//
//                } else {
//                    Toast.makeText(this, "No Image Selected !!", Toast.LENGTH_SHORT).show();
//                }
//                book.setImageBitmap(book_bitmap);
//            }
//
//        }
//        if (requestCode == BOOK_CAMERA) {
//
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            book_bitmap = BitmapFactory.decodeFile(filePath, options);
//            book.setImageBitmap(book_bitmap);
//            book_nodata.setVisibility(View.GONE);
//
//        }
//    }
//
//    public String getPath(Uri uri) {
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        String document_id = cursor.getString(0);
//        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
//        cursor.close();
//
//        cursor = getContentResolver().query(
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
//        cursor.moveToFirst();
//        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        cursor.close();
//
//        return path;
//    }
//
//    private void checkPermission(final int profileCamera, final int profileGallery) {
//
//        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
//                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
//            if ((ActivityCompat.shouldShowRequestPermissionRationale(AddProducts.this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(AddProducts.this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE))) {
//
//            } else {
//                ActivityCompat.requestPermissions(AddProducts.this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
//                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//            }
//        } else {
//            dialog.show();
//            camera.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    /*Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePicture, profileCamera);*/
//                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                        File photoFile = null;
//                        try {
//                            photoFile = createImageFile();
//                        } catch (IOException ex) {
//
//                        }
//                        if (photoFile != null) {
//                            Uri photoURI = FileProvider.getUriForFile(AddProducts.this,
//                                    "com.ynot.fdrobestore.fileprovider",
//                                    photoFile);
//                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                            startActivityForResult(takePictureIntent, profileCamera);
//
//
//                        }
//                    }
//                    dialog.dismiss();
//                }
//            });
//            gallery.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent logoIntent = new Intent();
//                    logoIntent.setType("image/*");
//                    logoIntent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(logoIntent, "Select Picture"), profileGallery);
//                    dialog.dismiss();
//                }
//            });
//
//
//        }
//    }
//
//    private File createImageFile() throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,
//                ".jpg",
//                storageDir
//        );
//        filePath = image.getAbsolutePath();
//        return image;
//    }
//
//    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
//        return byteArrayOutputStream.toByteArray();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void get_category() {
//        progress.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_CATEGORY,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progress.dismiss();
//                        try {
//                            JSONObject ob = new JSONObject(response);
//                            Log.e("category", response);
//
//                            if (ob.getBoolean("status")) {
//                                JSONArray array = ob.getJSONArray("categoryList");
//                                cat_model = new ArrayList<>();
//                                catname = new ArrayList<>();
//                                catname.add("Choose Category");
//                                for (int i = 0; i < array.length(); i++) {
//                                    JSONObject obb = array.getJSONObject(i);
//                                    cat_model.add(new SpinnerData(obb.getString("category_id"), obb.getString("category_name")));
//                                    catname.add(obb.getString("category_name"));
//                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, catname) {
//                                        @Override
//                                        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                                            View view = super.getDropDownView(position, convertView, parent);
//                                            TextView tv = (TextView) view;
//                                            if (position == 0) {
//                                                tv.setTextColor(Color.GRAY);
//                                            } else {
//                                                tv.setTextColor(Color.BLACK);
//                                            }
//                                            return view;
//                                        }
//                                    };
//                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    cat_spinner.setAdapter(adapter);
//                                }
//
//
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progress.dismiss();
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("store_id", SharedPrefManager.getInstance(getApplicationContext()).getUser().getStore_id());
//                params.put("store_name", SharedPrefManager.getInstance(getApplicationContext()).getUser().getStore_name());
//                return params;
//            }
//        };
//        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//    }
//
//}