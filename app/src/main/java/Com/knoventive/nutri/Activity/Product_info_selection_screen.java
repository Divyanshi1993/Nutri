package Com.knoventive.nutri.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import Com.knoventive.nutri.Adapter.AdapterProductList;
import Com.knoventive.nutri.Util.CustomTextView_Air_Semibold;
import Com.knoventive.nutri.Util.DialopgPopup;
import Com.knoventive.nutri.Util.ScreenOrientationControl;
import Com.knoventive.nutri.Listners.ClickListener;
import Com.knoventive.nutri.Listners.RecyclerTouchListener;
import Com.knoventive.nutri.R;
import Com.knoventive.nutri.model.ProductListItem;

public class Product_info_selection_screen extends AppCompatActivity
        implements View.OnClickListener {
    private RecyclerView Reclerview_prd;
    private ArrayList productList ;
    private ImageView btn_menu,home;
    private LinearLayout linearLayout;
    private AdapterProductList adapterProductList;
    private LinearLayout cal_inf, prd_inf, contact_fres,main_layout;
    private boolean isMenuActivated=false;
    public static Context prd_context;
    private CustomTextView_Air_Semibold header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ScreenOrientationControl(this).setOrientation();
        setContentView(R.layout.activity_product_info_selection_screen);
        initViews();
        //productList();
        setListner();
        adapterProductList = new AdapterProductList(this,productList);
        Reclerview_prd.setAdapter(adapterProductList);
        adapterProductList.isClickable(true);
        prd_context=this;


    }

//adding element to productlist

   /* private void productList() {
        productList = new ArrayList();
        productList.add(new ProductListItem(getResources().getString(R.string.SmofKabevin),getResources().getString(R.string.product_inf_sel_label1)));
        productList.add(new ProductListItem(getResources().getString(R.string.SmofKabevin),getResources().getString(R.string.product_inf_sel_label2)));
        productList.add(new ProductListItem(getResources().getString(R.string.SmofKabevin_el_free),getResources().getString(R.string.product_inf_sel_label1)));
        productList.add(new ProductListItem(getResources().getString(R.string.SmofKabevin_el_free),getResources().getString(R.string.product_inf_sel_label2)));

    }*/

         //  Initialize views here

    private void initViews() {
        Reclerview_prd = findViewById(R.id.product_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Reclerview_prd.setLayoutManager(mLayoutManager);
        Reclerview_prd.setItemAnimator(new DefaultItemAnimator());
        btn_menu = findViewById(R.id.btn_menu);
        home = findViewById(R.id.home);
        linearLayout = findViewById(R.id.menu_group);
        cal_inf = findViewById(R.id.cal_inf);
        prd_inf = findViewById(R.id.prd_inf);
        contact_fres = findViewById(R.id.contact_fres);
        main_layout = findViewById(R.id.main_layout);
        header_text = findViewById(R.id.header_text);
    }

     //  set clicks of views

    private void setListner() {
        btn_menu.setOnClickListener(this);
        home.setOnClickListener(this);
        Reclerview_prd.addOnItemTouchListener(new RecyclerTouchListener(this,
                Reclerview_prd, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                //forward_pdf_Screen(position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    //    pass intent to pdf view screen

    private void forward_pdf_Screen(int position) {
        Intent pdf = new Intent(Product_info_selection_screen.this, Product_info_pdf_screen.class);
        if(position==0){pdf.putExtra("filename", "SmofKabiven_PI.pdf"); pdf.putExtra("header_text","Product Information");}
        if(position==1){pdf.putExtra("filename", "SmofKabiven_CMI.pdf"); pdf.putExtra("header_text","Consumer Medicines Information");}
        if(position==2){pdf.putExtra("filename", "SmofKabivenEF_PI.pdf"); pdf.putExtra("header_text","Product Information");}
        if(position==3){pdf.putExtra("filename", "SmofKabiveEF_CMI.pdf"); pdf.putExtra("header_text","Consumer Medicines Information");}
        startActivity(pdf);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_menu:
                animateMenuGroup();
                new DialopgPopup().showDialog(Product_info_selection_screen.this,cal_inf,prd_inf,contact_fres);
                break;
            case R.id.home:
                Intent main = new Intent(this,HomeScreen.class);
                main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(main);
                finish();
                break;

        }

    }

 //   animate group menu button and display menu group

    public void animateMenuGroup() {
        isMenuActivated=true;
        if (linearLayout.getVisibility() == View.GONE) {
            home.setEnabled(false);
            Reclerview_prd.setLayoutFrozen(true);
            adapterProductList.isClickable(false);
            linearLayout.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            linearLayout.startAnimation(animation);
            animationListner(animation,View.VISIBLE);
            btn_menu.setBackground(getResources().getDrawable(R.drawable.close_btn));
        } else {
            btn_menu.setBackground(getResources().getDrawable(R.drawable.ic_menu));
            isMenuActivated=false;
            home.setEnabled(true);
            Reclerview_prd.setLayoutFrozen(false);
            adapterProductList.isClickable(true);
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
                if(visiblility==View.VISIBLE){
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.trans));}
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
