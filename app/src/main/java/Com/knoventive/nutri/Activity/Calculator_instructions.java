package Com.knoventive.nutri.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import Com.knoventive.nutri.Adapter.CustomPagerAdapter;
import Com.knoventive.nutri.R;
import Com.knoventive.nutri.Util.DialopgPopup;
import Com.knoventive.nutri.Util.ScreenOrientationControl;

public class Calculator_instructions extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private TextView header_text;
    private ImageView btn_menu, btn_arrow;
    private ViewPager viewPager;
    private int count = 1;
    private LinearLayout cal_inf, prd_inf, contact_fres, linearLayout;
    private boolean isMenuActivated = false;
    private CustomPagerAdapter customPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ScreenOrientationControl(this).setOrientation();
        setContentView(R.layout.activity_calcualte_information);
        View overlay = findViewById(R.id.mylayout);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        initViews();
        setListners();

    }

    /*
    Initialize views here
     */
    private void initViews() {
        header_text = findViewById(R.id.header_label);
        header_text.setText(getResources().getString(R.string.Cal_instructions));
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        customPagerAdapter = new CustomPagerAdapter(this, count);
        viewPager.setAdapter(customPagerAdapter);
        btn_menu = findViewById(R.id.btn_menu);
        btn_arrow = findViewById(R.id.btn_arrow);
        cal_inf = findViewById(R.id.cal_inf);
        prd_inf = findViewById(R.id.prd_inf);
        contact_fres = findViewById(R.id.contact_fres);
        linearLayout = findViewById(R.id.menu_group);

    }

    /*
    set on Click of views
     */
    private void setListners() {
        btn_menu.setOnClickListener(this);
        btn_arrow.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isMenuActivated) {
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu:
                animateMenuGroup();
                new DialopgPopup().showDialog(Calculator_instructions.this, cal_inf, prd_inf, contact_fres);
                break;
            case R.id.btn_arrow:
                if (!isMenuActivated) {
                    count++;
                    if (btn_arrow.getTag() != null) {
                        if ((int) btn_arrow.getTag() == R.drawable.fwd_home) {
                            Intent intent = new Intent(this, HomeScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                    if (count == 4) {
                        btn_arrow.setBackground(getResources().getDrawable(R.drawable.fwd_home));
                        btn_arrow.setTag(R.drawable.fwd_home);
                    }
                    viewPager.setCurrentItem(count);
                }
        }
    }

    /*
    animate group menu button and display menu group
     */
    public void animateMenuGroup() {
        if (linearLayout.getVisibility() == View.GONE) {
            isMenuActivated = true;
            customPagerAdapter = new CustomPagerAdapter(this, 0);
            linearLayout.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            linearLayout.startAnimation(animation);
            animationListner(animation, View.VISIBLE);
            btn_menu.setBackground(getResources().getDrawable(R.drawable.close_btn));
        } else {
            btn_menu.setBackground(getResources().getDrawable(R.drawable.ic_menu));
            customPagerAdapter = new CustomPagerAdapter(this, 1);
            isMenuActivated = false;
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
// arrow button changes according to positions
        if (position == 4) {
            btn_arrow.setBackground(getResources().getDrawable(R.drawable.fwd_home));
            btn_arrow.setTag(R.drawable.fwd_home);
        } else {
            btn_arrow.setBackground(getResources().getDrawable(R.drawable.blue_arrow));
            btn_arrow.setTag(R.drawable.blue_arrow);
        }
    }

    @Override
    public void onPageSelected(int position) {
        count = position;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
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