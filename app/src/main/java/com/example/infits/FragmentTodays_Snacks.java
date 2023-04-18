package com.example.infits;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.MemoryFile;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTodays_BreakFast#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTodays_Snacks extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    ImageView calorieImgback;
    JSONObject mainJsonobj;
    LinearLayout linear_layout1, linear_layout2;
    Todays_Meal_info todays_meal_info;
    ArrayList<Todays_Meal_info> todays_meal_infos;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView DoneButtonView;
    String url = String.format("%ssaveMeal.php", DataFromDatabase.ipConfig);

    SharedPreferences sharedPreferences;
    RecyclerView recyclerView_Todays_breakfast;
    SimpleDateFormat todayDate;
    SimpleDateFormat todayTime;
    Date date;
    public FragmentTodays_Snacks() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTodays_Snacks.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTodays_Snacks newInstance(String param1, String param2) {
        FragmentTodays_Snacks fragment = new FragmentTodays_Snacks();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        todays_meal_infos = new ArrayList<>();
        todays_meal_infos.clear();

        View view = inflater.inflate(R.layout.fragment_todays__snacks, container, false);
        todayDate = new SimpleDateFormat("d MMM yyyy");

        todayTime = new SimpleDateFormat("h.m.s a");

        date=new Date();

        //recycleview
        recyclerView_Todays_breakfast = view.findViewById(R.id.recyclerView_Todays_breakfast);
        recyclerView_Todays_breakfast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DisplayDataInList();
//        todays_meal_infos.clear();

        Adapter_Todays_Meal Adapter_Todays_Meal = new Adapter_Todays_Meal(getContext(), todays_meal_infos);
        recyclerView_Todays_breakfast.setAdapter(Adapter_Todays_Meal);

        //backbutton
        calorieImgback = view.findViewById(R.id.calorieImgback);
        calorieImgback.setOnClickListener(v -> requireActivity().onBackPressed());


        //DoneButtonView
        linear_layout1 = view.findViewById(R.id.linear_layout1);
        linear_layout2 = view.findViewById(R.id.linear_layout2);

        DoneButtonView = view.findViewById(R.id.DoneButtonView);
        DoneButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    linear_layout1.setVisibility(View.GONE);
                    linear_layout2.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.d("Exception123", e.toString());
                }
            }
        });

        //delete shared preference

        DeleteSharedPreference();
        return view;
    }


    public void DisplayDataInList() {
        try {
            todays_meal_infos.clear();
            sharedPreferences = getActivity().getSharedPreferences("TodaysSnacks", Context.MODE_PRIVATE);
            JSONObject jsonObject = new JSONObject(sharedPreferences.getString("TodaysSnacks", ""));
            JSONArray jsonArray = jsonObject.getJSONArray("TodaysSnacks");
            for (int i = jsonArray.length()-1; i >=0; i--) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                todays_meal_infos.add(new Todays_Meal_info(getContext().getDrawable(R.drawable.pizza_img),
                        jsonObject1.getString("mealName"),
                        jsonObject1.getString("Meal_Type"),

                jsonObject1.getString("calorieValue"),
                        jsonObject1.getString("carbsValue"),
                        jsonObject1.getString("fatValue"),
                        jsonObject1.getString("proteinValue"),
                        jsonObject1.getString("Quantity"),
                        jsonObject1.getString("Size")));
            }
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
    }

    private void DeleteSharedPreference() {

        AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), NotificationReceiver.class);
        intent.putExtra("tracker", "TodaysMeal");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0L, 59 * 1000, pendingIntent);
    }
}