package com.example.menumodule.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.menumodule.R;
import com.example.menumodule.adapters.menuListAdapter;
import com.example.menumodule.models.ApiPlaceHolder;
import com.example.menumodule.models.MenuListModel;
import com.example.menumodule.utils.BaseFragment;
import com.example.menumodule.utils.NetworkUtils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuFragment extends BaseFragment implements menuListAdapter.sendItemData {

    private Button btnListAdd;
    private ShimmerFrameLayout shimmerMenuList;
    List<MenuListModel> menuListModelList = new ArrayList<MenuListModel>();
    private RecyclerView rvMenuList;
    private menuListAdapter menuListAdapter;
    private BottomSheetDialog dialogMenuAdd, dialogAccessability, dialogCuisineType;
    private SwipeRefreshLayout swipe_refresh;
    private MenuDetailsFragment menuDetailsFragment;
    public Boolean delivery = false;
    public Boolean pickup = false;
    public Boolean dining = false;
    public Boolean veg = false;
    public Boolean nonveg = false;
    public Boolean egg = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        findViewById(view);

        shimmerMenuList.startShimmer();
        shimmerMenuList.setVisibility(View.VISIBLE);
        getMenuList();

        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!NetworkUtils.isNetworkConnected(getContext())) {
                    showSnackBarRed("No internet");
                    swipe_refresh.setRefreshing(false);
                } else {
                    shimmerMenuList.startShimmer();
                    getMenuList();

                }
            }
        });

        btnListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddMenuDialog();
            }
        });

        return view;

    }

    private void openAddMenuDialog() {
        dialogMenuAdd = new BottomSheetDialog(requireContext());
        dialogMenuAdd.setContentView(R.layout.add_menu_dialog);

        Button btnAddMenu = dialogMenuAdd.findViewById(R.id.btnAddMenu);
        TextView txtCuisineTypeAddMenu = dialogMenuAdd.findViewById(R.id.txtCuisineTypeAddMenu);
        TextView txtAccessibilityAddMenu = dialogMenuAdd.findViewById(R.id.txtAccessibilityAddMenu);
        TextView txtCategoryAddMenu = dialogMenuAdd.findViewById(R.id.txtCategoryAddMenu);
        EditText etCategoryNameAddMenu = dialogMenuAdd.findViewById(R.id.etCategoryNameAddMenu);
        EditText etCategoryDescAddMenu = dialogMenuAdd.findViewById(R.id.etCategoryDescAddMenu);
        SwitchCompat btnAllDayToggleAddMenu = dialogMenuAdd.findViewById(R.id.btnAllDayToggleAddMenu);
        SwitchCompat btnDisableAddMenuToggle = dialogMenuAdd.findViewById(R.id.btnDisableAddMenuToggle);
        SwitchCompat btnSuperAppVisibleAddMenuToggle = dialogMenuAdd.findViewById(R.id.btnSuperAppVisibleAddMenuToggle);
        TextView txtStartTimeAddMenu = dialogMenuAdd.findViewById(R.id.txtStartTimeAddMenu);
        TextView txtEndTimeAddMenu = dialogMenuAdd.findViewById(R.id.txtEndTimeAddMenu);

        txtStartTimeAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker(txtStartTimeAddMenu);
            }
        });

        txtEndTimeAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker(txtEndTimeAddMenu);
            }
        });

        txtAccessibilityAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAccessability = new BottomSheetDialog((requireContext()));
                dialogAccessability.setContentView(R.layout.add_menu_choose_accessibility_dialog);

                Button btnSelectAccessibilityMenuAdd;
                RadioButton checkDelivery, checkPickup, checkDining;
                checkDelivery = dialogAccessability.findViewById(R.id.checkDelivery);
                checkPickup = dialogAccessability.findViewById(R.id.checkPickup);
                checkDining = dialogAccessability.findViewById(R.id.checkDining);
                btnSelectAccessibilityMenuAdd = dialogAccessability.findViewById(R.id.btnSelectAccessibilityMenuAdd);

                unselectRadiobtn(checkDelivery);
                unselectRadiobtn(checkPickup);
                unselectRadiobtn(checkDining);


                btnSelectAccessibilityMenuAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String accessibility = "";



                        if (checkDelivery.isChecked()) {
                            accessibility = "Delivery ";
                            delivery = true;
                        }
                        if (checkPickup.isChecked()) {
                            accessibility = accessibility + "Pickup ";
                            pickup = true;
                        }
                        if (checkDining.isChecked()) {
                            accessibility = accessibility + "Dining";
                            dining = true;
                        }


                        dialogAccessability.dismiss();

                        txtAccessibilityAddMenu.setText(accessibility);
                    }
                });


                dialogAccessability.show();
            }
        });

        txtCuisineTypeAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCuisineType = new BottomSheetDialog(requireContext());
                dialogCuisineType.setContentView(R.layout.add_menu_choose_cuisine_dialog);

                Button btnSelectCuisineTypeMenuAdd;
                RadioButton checkVeg, checkNonVeg, checkEgg;
                checkVeg = dialogCuisineType.findViewById(R.id.checkVeg);
                checkNonVeg = dialogCuisineType.findViewById(R.id.checkNonVeg);
                checkEgg = dialogCuisineType.findViewById(R.id.checkEgg);
                btnSelectCuisineTypeMenuAdd = dialogCuisineType.findViewById(R.id.btnSelectCuisineTypeMenuAdd);

                unselectRadiobtn(checkVeg);
                unselectRadiobtn(checkNonVeg);
                unselectRadiobtn(checkEgg);


                btnSelectCuisineTypeMenuAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String CuisineType = "";

                        if (checkVeg.isChecked()) {
                            CuisineType = "Veg ";
                            veg = true;
                        }
                        if (checkNonVeg.isChecked()) {
                            CuisineType = CuisineType + "Non-Veg ";
                            nonveg = true;
                        }
                        if (checkEgg.isChecked()) {
                            CuisineType = CuisineType + "Egg";
                            egg = true;
                        }


                        dialogCuisineType.dismiss();

                        txtCuisineTypeAddMenu.setText(CuisineType);
                    }
                });

                dialogCuisineType.show();
            }
        });

        Boolean finalVeg = veg;
        btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCategoryNameAddMenu.getText().toString().isEmpty()) {
                    etCategoryNameAddMenu.setError("Name cannot be empty");
                }else{
                   dialogMenuAdd.dismiss();
                    if (NetworkUtils.isNetworkConnected(getActivity())) {
                        addMenuList(etCategoryNameAddMenu.getText().toString(),
                                etCategoryDescAddMenu.getText().toString(),
                                txtStartTimeAddMenu.getText().toString(),
                                txtEndTimeAddMenu.getText().toString(),
                                btnAllDayToggleAddMenu.isChecked(),
                                delivery, pickup, dining, veg, nonveg, egg, btnSuperAppVisibleAddMenuToggle.isChecked(), btnDisableAddMenuToggle.isChecked());

                    }else{
                        showSnackBarRed("No internet");
                    }
                }

            }
        });

        dialogMenuAdd.show();
    }

    private void unselectRadiobtn(RadioButton rb){
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rb.isSelected()) {
                    rb.setChecked(true);
                    rb.setSelected(true);
                } else {
                    rb.setChecked(false);
                    rb.setSelected(false);
                }
            }
        });
    }
    private void openTimePicker(TextView txt) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                txt.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void findViewById(View view) {
        btnListAdd = view.findViewById(R.id.btnListAdd);
        shimmerMenuList = view.findViewById(R.id.shimmerFrameLayout);
        rvMenuList = view.findViewById(R.id.rvMenuList);
        swipe_refresh = view.findViewById(R.id.swipe_refresh);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvMenuList = view.findViewById(R.id.rvMenuList);
        rvMenuList.setLayoutManager(layoutManager);
        rvMenuList.addItemDecoration(new DividerItemDecoration(requireContext(), 0));
    }

    private void getMenuList() {

        showLoading();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pidu.in/mybiz/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiPlaceHolder retrofitAPI = retrofit.create(ApiPlaceHolder.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("apikey", "164310030161efb88d2d888");
        jsonObject.addProperty("userid", "1");
        jsonObject.addProperty("storeid", "158");

        Call<ResponseBody> call = retrofitAPI.getMenuList(jsonObject);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {

                        try {
                            JSONObject json = new JSONObject(response.body().string());

                            if (json.getString("status").equals("success")) {


                                JSONObject json1 = new JSONObject(json.get("data").toString());

                                Iterator x = json1.keys();

                                menuListModelList.clear();

                                while (x.hasNext()) {
                                    String key = (String) x.next();

                                    JSONObject json2 = new JSONObject(json1.get(key).toString());


                                    menuListModelList.add(new MenuListModel(
                                            json2.get("id") + "",
                                            json2.get("name") + "",
                                            json2.get("description") + "",
                                            json2.get("veg") + "",
                                            json2.get("nonveg") + "",
                                            json2.get("egg") + "",
                                            json2.get("dinein") + "",
                                            json2.get("pickup") + "",
                                            json2.get("delivery") + "",
                                            json2.get("allday") + "",
                                            json2.get("starttime") + "",
                                            json2.get("endtime") + "",
                                            json2.get("superappvisible") + "",
                                            json2.get("disable") + ""

                                    ));
                                    swipe_refresh.setRefreshing(false);
                                }
                                onListLoadSuccess(menuListModelList);

                            } else {
                                showSnackBarRed("No Salary Found");

                            }

                            onListLoadSuccess(menuListModelList);


                        } catch (IOException | JSONException e) {
                            hideLoading();
                        }
                    }
                } else {
                    hideLoading();
                    shimmerMenuList.stopShimmer();
                    shimmerMenuList.setVisibility(View.INVISIBLE);

                    showSnackBarRed("Something Error!!");

                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                hideLoading();
                showSnackBarRed("Something Went Wrong");

            }
        });

    }

    private void addMenuList(String name,
                             String description,
                             String starttime,
                             String endtime,
                             Boolean allday,
                             Boolean delivery,
                             Boolean pickup,
                             Boolean dinein,
                             Boolean veg,
                             Boolean nonveg,
                             Boolean egg,
                             Boolean visibleuserapp,
                             Boolean disable) {

        showLoading(requireContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pidu.in/mybiz/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiPlaceHolder retrofitAPI = retrofit.create(ApiPlaceHolder.class);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("apikey", "164310030161efb88d2d888");
        jsonObject.addProperty("userid", "1");
        jsonObject.addProperty("storeid", "158");
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("description", description);
        jsonObject.addProperty("starttime", starttime);
        jsonObject.addProperty("endtime", endtime);
        jsonObject.addProperty("allday", allday);
        jsonObject.addProperty("delivery", delivery);
        jsonObject.addProperty("pickup", pickup);
        jsonObject.addProperty("dinein", dinein);
        jsonObject.addProperty("veg", veg);
        jsonObject.addProperty("nonveg", nonveg);
        jsonObject.addProperty("egg", egg);
        jsonObject.addProperty("visibleuserapp", visibleuserapp);
        jsonObject.addProperty("disable", disable);

        Call<ResponseBody> call = retrofitAPI.addMenuList(jsonObject);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200) {
                    if (response.body() != null) {

                        try {
                            JSONObject json = new JSONObject(response.body().string());

                            if (json.getString("status").equals("success")) {
                                hideLoading();
                                dialogMenuAdd.dismiss();
                                getMenuList();
                                showSnackBarGreen("Menu Added Successfully");

                            } else {
                                dialogMenuAdd.dismiss();
                                hideLoading();
                                showSnackBarRed("Error Occurred!..");
                            }

                        } catch (IOException | JSONException e) {
                            dialogMenuAdd.dismiss();
                            hideLoading();
                            showSnackBarRed(e.toString());
                        }
                    } else {
                        dialogMenuAdd.dismiss();
                        showSnackBarRed("Something went wrong");
                        hideLoading();
                    }
                } else {
                    dialogMenuAdd.dismiss();
                    showSnackBarRed("Something Error");
                    hideLoading();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialogMenuAdd.dismiss();
                showSnackBarRed(t.toString());
                hideLoading();
            }
        });


    }

    public void onListLoadSuccess(List<MenuListModel> menuListModelList) {
        menuListAdapter = new menuListAdapter(this.getContext(), menuListModelList, this);

        shimmerMenuList.stopShimmer();
        shimmerMenuList.setVisibility(View.INVISIBLE);
        hideLoading();

//        if (menuListAdapter.getItemCount() != 0) {
//            imgEmptyDisplay.setVisibility(View.INVISIBLE);
//            swipe_refresh.setVisibility(View.VISIBLE);
//
//
//        } else {
//            swipe_refresh.setVisibility(View.INVISIBLE);
//            imgEmptyDisplay.setVisibility(View.VISIBLE);
//
//
//        }

        rvMenuList.setAdapter(menuListAdapter);
    }


    @Override
    public void updateItem(MenuListModel itemViewModel, int pos) {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        MenuDetailsFragment menuDetailsFragment = new MenuDetailsFragment(itemViewModel);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_menu, menuDetailsFragment).addToBackStack(null).commit();
    }
}
