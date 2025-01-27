package com.example.infits;

import static com.facebook.FacebookSdk.getApplicationContext;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class EditProfile extends AppCompatActivity {
    Context mcontext = EditProfile.this;

    ImageView male;
    ImageView female;
    static ImageView profile_pic;
    ImageView backBtn;

    RequestQueue queue;
    Button save, editProfile;
    String client_gender, cleint_name, client_age, client_email,client_phoneno,client_userID;

    String client_weight,client_height,client_about;

    static Bitmap bitmap = DataFromDatabase.profile;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    String url = String.format("%ssave.php",DataFromDatabase.ipConfig);

    ActivityResultLauncher<String> photo;

    File file;

    String fileName, path;

    String gen = "M";

    Bitmap photoBit;

    AlertDialog.Builder builder;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String IMG_ENCODED = "encoded";
    String img_text;
    static boolean default_pic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        SharedPrefManager spf = new SharedPrefManager(getApplicationContext());
        boolean b = spf.checkSession();



        male = findViewById(R.id.gender_male_icon);
        female = findViewById(R.id.gender_female_icon);
        EditText name = findViewById(R.id.name_edt);
        name.setText(DataFromDatabase.name);
        EditText age = findViewById(R.id.age_edt);
        age.setText(DataFromDatabase.age);
        EditText email = findViewById(R.id.email_edt);
        email.setText(DataFromDatabase.email);
        EditText phone = findViewById(R.id.phone_edt);
        EditText weight = findViewById(R.id.weight_edt);
        weight.setText(DataFromDatabase.weight);
        EditText height = findViewById(R.id.height_edt);
        height.setText(DataFromDatabase.height);
        EditText plan = findViewById(R.id.plan_edt);
        plan.setText("Basic");


        phone.setText(DataFromDatabase.mobile);
        profile_pic = findViewById(R.id.dp);
        if(b)
        {
            Bitmap profile_photo_temp = spf.getSessionDetails("key_session_profile_pic");
            profile_pic.setImageBitmap(profile_photo_temp);
        }
        else{
            profile_pic.setImageBitmap((Bitmap) DataFromDatabase.profile);
        }

        backBtn = findViewById(R.id.imgBack);
        editProfile = findViewById(R.id.button_editProfile);


        ImageView name_btn = findViewById(R.id.name_edt_button);
        ImageView age_btn = findViewById(R.id.age_edt_button);
        ImageView email_btn = findViewById(R.id.email_edt_button);
        ImageView phone_btn = findViewById(R.id.phone_edt_button);
        ImageView height_btn = findViewById(R.id.height_edt_button);
        ImageView weight_btn = findViewById(R.id.weight_edt_button);
        ImageView profile = findViewById(R.id.dp);


        if(DataFromDatabase.gender.equals("M")) {
            male.setImageResource(R.drawable.gender_male_selected);
            female.setImageResource(R.drawable.gender_female);
        } else {
            male.setImageResource(R.drawable.gender_male);
            female.setImageResource(R.drawable.gender_female_selected);
        }

        backBtn.setOnClickListener(v -> this.finish());

        name_btn.setOnClickListener(v -> {
            name.setCursorVisible(true);
            name.setFocusableInTouchMode(true);
            name.setInputType(InputType.TYPE_CLASS_TEXT);
        });

        age_btn.setOnClickListener(v -> {
            age.setCursorVisible(true);
            age.setFocusableInTouchMode(true);
            age.setInputType(InputType.TYPE_CLASS_NUMBER);
        });

        email_btn.setOnClickListener(v -> {
            email.setCursorVisible(true);
            email.setFocusableInTouchMode(true);
            email.setInputType(InputType.TYPE_CLASS_TEXT);
        });



        phone_btn.setOnClickListener(v -> {
            phone.setCursorVisible(true);
            phone.setFocusableInTouchMode(true);
            phone.setInputType(InputType.TYPE_CLASS_PHONE);
        });

        height_btn.setOnClickListener(v -> {
            height.setCursorVisible(true);
            height.setFocusableInTouchMode(true);
            height.setInputType(InputType.TYPE_CLASS_NUMBER);
        });

        weight_btn.setOnClickListener(v -> {
            weight.setCursorVisible(true);
            weight.setFocusableInTouchMode(true);
            weight.setInputType(InputType.TYPE_CLASS_NUMBER);
        });

        client_gender=DataFromDatabase.gender;
        male.setOnClickListener(v ->  {
            male.setImageResource(R.drawable.gender_male_selected);
            female.setImageResource(R.drawable.gender_female);
            client_gender="M";
        });
        female.setOnClickListener(v ->  {
            male.setImageResource(R.drawable.gender_male);
            female.setImageResource(R.drawable.gender_female_selected);
            client_gender="F";
        });
        builder = new AlertDialog.Builder(getApplicationContext());

        profile.setOnClickListener(new View.OnClickListener() { // Change Profile Picture
            @Override
            public void onClick(View v) {
                PhotoDialogBox dialog = new PhotoDialogBox();
                dialog.show(getSupportFragmentManager(), "option dialog");
            }
        });
    }
}