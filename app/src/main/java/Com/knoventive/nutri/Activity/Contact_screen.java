package Com.knoventive.nutri.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import Com.knoventive.nutri.Util.DialopgPopup;
import Com.knoventive.nutri.Util.Email_info;
import Com.knoventive.nutri.Util.ScreenOrientationControl;
import Com.knoventive.nutri.R;


public class Contact_screen extends AppCompatActivity implements View.OnClickListener {
    private CardView btn_call, btn_email, btn_website, btn_privacy;
    private ImageView btn_menu,home;
    private LinearLayout cal_inf, prd_inf, contact_fres,linearLayout;
    private boolean isMenuActivated =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ScreenOrientationControl(this).setOrientation();
        setContentView(R.layout.activity_contact_screen);
        initViews();
        clickListners();
    }
    /*
       Initialize views here
        */
    private void initViews() {
        btn_call = findViewById(R.id.call);
        btn_email = findViewById(R.id.email);
        btn_website = findViewById(R.id.website);
        btn_privacy = findViewById(R.id.privacy);
        btn_menu = findViewById(R.id.btn_menu);
        home = findViewById(R.id.home);
        cal_inf = findViewById(R.id.cal_inf);
        prd_inf = findViewById(R.id.prd_inf);
        contact_fres = findViewById(R.id.contact_fres);
        linearLayout = findViewById(R.id.menu_group);

    }
    /*
       Set on click of views
        */
    private void clickListners() {
        btn_call.setOnClickListener(this);
        btn_email.setOnClickListener(this);
        btn_website.setOnClickListener(this);
        btn_privacy.setOnClickListener(this);
        btn_menu.setOnClickListener(this);
        home.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call:
                if(!isMenuActivated){
                    // Dial Phone Number
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + ""));
                startActivity(intent);}
                break;
            case R.id.email:
                if(!isMenuActivated){
                    // Redirect to Mailing App
                new Email_info(this,null);
                }
                break;
            case R.id.privacy:
                if(!isMenuActivated){
                    // Redirect to child Webview
                    Intent browserIntent = new Intent(this,WebActivity.class);
                    browserIntent.putExtra("url","google.com");
                    startActivity(browserIntent);
                }
                break;
            case R.id.website:
                if(!isMenuActivated){
                    // Redirect to child Webview
                    Intent browserIntent = new Intent(this,WebActivity.class);
                    browserIntent.putExtra("url","google.com");
                    startActivity(browserIntent);
                }
                break;
            case R.id.btn_menu:
                animateMenuGroup();
                new DialopgPopup().showDialog(Contact_screen.this,cal_inf,prd_inf,contact_fres);
                break;
            case R.id.home:
                // Redirect to Home Screen
                Intent main = new Intent(this,HomeScreen.class);
                main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(main);
               finish();
                break;
            case R.id.card_view_container:

                break;
        }
    }
    /*
    animate group menu button and display menu group
     */
    public void animateMenuGroup() {
        if (linearLayout.getVisibility() == View.GONE) {
            isMenuActivated=true;
            home.setEnabled(false);
            linearLayout.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            linearLayout.startAnimation(animation);
            animationListner(animation,View.VISIBLE);
            btn_menu.setBackground(getResources().getDrawable(R.drawable.close_btn));
        } else {
            btn_menu.setBackground(getResources().getDrawable(R.drawable.ic_menu));
            isMenuActivated=false;
            home.setEnabled(true);
            linearLayout.setBackgroundColor(Color.TRANSPARENT);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            linearLayout.startAnimation(animation);
            animationListner(animation,View.GONE);
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
    public void onBackPressed() {
        if(isMenuActivated){
            animateMenuGroup();
            return ;
        }else {
            super.onBackPressed();
        }
    }

}
