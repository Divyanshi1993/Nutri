package Com.knoventive.nutri.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import Com.knoventive.nutri.Util.CustomTextView;
import Com.knoventive.nutri.Util.CustomTextView_Air_Ult_light;
import Com.knoventive.nutri.Util.CustomTextView_img;
import Com.knoventive.nutri.Util.DialopgPopup;
import Com.knoventive.nutri.Util.ScreenOrientationControl;
import Com.knoventive.nutri.R;
import Com.knoventive.nutri.prefrence.pass_pref;


public class HomeScreen extends AppCompatActivity implements View.OnClickListener {
    private pass_pref pref;
    private CustomTextView calculate;
    private CustomTextView_Air_Ult_light weight, calorie;
    private CustomTextView_img cal_img, weight_img;
    private String _calorie, _weight;
    private ImageView btn_menu;
    private LinearLayout linearLayout;
    private LinearLayout cal_inf, prd_inf, contact_fres;
    private boolean isMenuActivated = false;
    private Boolean isXLargeScreen ;
    private ScreenOrientationControl screenOrientationControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenOrientationControl =  new ScreenOrientationControl(this);
        screenOrientationControl.setOrientation();
        setContentView(R.layout.activity_main);
        checkLogin();
        initViews();
        setListners();
    }
    @Override
    protected void onResume() {
        super.onResume();
        calorie.getText().clear();
        weight.getText().clear();
        weigh_image_position(Gravity.CENTER, getResources().getColor(R.color.grey));
        calorie_image_position(Gravity.CENTER, getResources().getColor(R.color.grey));
        linearLayout.setVisibility(View.GONE);
        linearLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    /*
         Initialize views here
          */
    private void initViews() {
        calculate = findViewById(R.id.btn_calculate);
        calorie = findViewById(R.id.Calorie);
        weight = findViewById(R.id.Weight);
        btn_menu = findViewById(R.id.btn_menu);
        weight_img = findViewById(R.id.weight_img);
        cal_img = findViewById(R.id.cal_img);
        linearLayout = findViewById(R.id.menu_group);
        cal_inf = findViewById(R.id.cal_inf);
        prd_inf = findViewById(R.id.prd_inf);
        contact_fres = findViewById(R.id.contact_fres);
    }

    /*
    Set on click of views
           */
    private void setListners() {
        calculate.setOnClickListener(this);
        calorie.setOnClickListener(this);
        weight.setOnClickListener(this);
        btn_menu.setOnClickListener(this);
    }

    /*
    check for app login
     */
    private void checkLogin() {
        pref = new pass_pref(getApplicationContext());
        if (pref.getPass() == null) {
            Intent done_intent = new Intent(getApplicationContext(), LoginScreen.class);
            startActivity(done_intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_calculate:
                Calculation();
                break;
            case R.id.Calorie:
                chooseWeight_Calorie();
                break;
            case R.id.Weight:
                chooseWeight_Calorie();
                break;
            case R.id.btn_menu:
                animateMenuGroup();
                new DialopgPopup().showDialog(this, cal_inf, prd_inf, contact_fres);
                break;
        }
    }

    /*
        animate group menu button and display menu group
         */
    public void animateMenuGroup() {

        if (linearLayout.getVisibility() == View.GONE) {
            isMenuActivated = true;
            calorie.setEnabled(false);
            weight.setEnabled(false);
            linearLayout.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            linearLayout.startAnimation(animation);
            animationListner(animation, View.VISIBLE);
            btn_menu.setBackground(getDrawable(R.drawable.close_btn));
        } else {
            btn_menu.setBackground(getDrawable(R.drawable.ic_menu));
            isMenuActivated = false;
            calorie.setEnabled(true);
            weight.setEnabled(true);
            linearLayout.setBackgroundColor(Color.TRANSPARENT);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            linearLayout.startAnimation(animation);
            animationListner(animation, View.GONE);
        }
    }

    private void animationListner(Animation animation, final int visiblility) {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // set gropu menu visibilty on screen
                linearLayout.setVisibility(visiblility);
                if (visiblility == View.VISIBLE) {
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.trans));
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /*
    Display dialog to choose calorie and weight

     */
    private void chooseWeight_Calorie() {
        final Dialog dialog = new Dialog(HomeScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.number_picker);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        final com.shawnlin.numberpicker.NumberPicker numberPicker_weight = dialog.findViewById(R.id.numberPicker_weight);
        final com.shawnlin.numberpicker.NumberPicker numberPicker_calorie = dialog.findViewById(R.id.numberPicker_Calorie);
        numberPicker_calorie.setDisplayedValues(new String[]{"15", "20", "25", "30", "35"});
        Button button_done = (Button) dialog.findViewById(R.id.btn_done);
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Weight = numberPicker_weight.getValue();
                int calorie = numberPicker_calorie.getValue();
                onClickDone(Weight, calorie, dialog);
            }
        });
        dialog.show();
    }

    /*
    set calorie after choosing displayed on screen
     */
    private void onClickDone(int wegh, int cal, Dialog dialog) {
        int pick = 0;
        switch (cal) {
            case 1:
                pick = 15;
                break;
            case 2:
                pick = 20;
                break;
            case 3:
                pick = 25;
                break;
            case 4:
                pick = 30;
                break;
            case 5:
                pick = 35;
                break;
        }
        weigh_image_position(Gravity.END, getResources().getColor(R.color.orange));
        calorie_image_position(Gravity.END, getResources().getColor(R.color.green));
        weight.setText(String.valueOf(wegh));
        calorie.setText(String.valueOf(pick));
        dialog.dismiss();
    }

    /*
    Passing intent to Regimen screen for calculation
     */
    private void Calculation() {
        if(calorie.getText().toString()!=null && weight.getText().toString()!=null){
        _calorie = calorie.getText().toString();
        _weight = weight.getText().toString();}
        if (!_calorie.equals("") && !_weight.equals("")) {
            Intent regimn_scr = new Intent(getApplicationContext(), RegimenScreen.class);
            regimn_scr.putExtra(getString(R.string.cal), Integer.valueOf(_calorie));
            regimn_scr.putExtra(getString(R.string.Weight), Integer.valueOf(_weight));
            startActivity(regimn_scr);
        }
    }

    /*
    set Calorie image position and color
     */
    private void calorie_image_position(int gravity, int color) {
        cal_img.setGravity(gravity);
        cal_img.setTextColor(color);
    }

    /*
    set Weight image position and color
     */
    private void weigh_image_position(int gravity, int color) {
        weight_img.setGravity(gravity);
        weight_img.setTextColor(color);
    }

    @Override
    public void onBackPressed() {
        if (isMenuActivated) {
            animateMenuGroup();
            return;
        } else {
            super.onBackPressed();
        }
    }
}
