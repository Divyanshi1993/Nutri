package Com.knoventive.nutri.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

import Com.knoventive.nutri.Util.CustomTextView_Air_Semibold;
import Com.knoventive.nutri.Util.DialopgPopup;
import Com.knoventive.nutri.Util.ScreenOrientationControl;
import Com.knoventive.nutri.R;


public class Product_info_pdf_screen extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, View.OnClickListener {
    private String pdfFileName = "page12.pdf";
    private PDFView pdfView;
    private Integer pageNumber = 0;
    private Toolbar toolbar;
    private ImageView btn_menu;
    private LinearLayout cal_inf, prd_inf, contact_fres, linearLayout;
    private boolean isMenuActivated = false;
    private CustomTextView_Air_Semibold tb_header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ScreenOrientationControl(this).setOrientation();
        setContentView(R.layout.activity_product__info_pdf);
        initViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getIntent().hasExtra("filename")&&getIntent().hasExtra("header_text")){
            pdfFileName = getIntent().getStringExtra("filename");}
            tb_header.setText(getIntent().getStringExtra("header_text"));
            displayFromAsset(pdfFileName);
    }

    /*
           Initialize views here
            */
    private void initViews() {
        pdfView = (PDFView) findViewById(R.id.pdfView);
        toolbar = findViewById(R.id.toolbar);
        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(this);
        linearLayout = findViewById(R.id.menu_group);
        cal_inf = findViewById(R.id.cal_inf);
        prd_inf = findViewById(R.id.prd_inf);
        contact_fres = findViewById(R.id.contact_fres);
        tb_header = findViewById(R.id.tb_header);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
         display pdf choose from assests
           */
    private void displayFromAsset(String assetFileName) {
        pdfView.fromAsset(pdfFileName)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_menu:
                animateMenuGroup();
                new DialopgPopup().showDialog(Product_info_pdf_screen.this, cal_inf, prd_inf, contact_fres);
                break;
        }
    }

    /*
   animate group menu button and display menu group
    */
    public void animateMenuGroup() {
        isMenuActivated = true;
        if (linearLayout.getVisibility() == View.GONE) {
            linearLayout.setVisibility(View.VISIBLE);
            pdfView.setEnabled(false);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            linearLayout.startAnimation(animation);
            animationListner(animation, View.VISIBLE);
            btn_menu.setBackground(getResources().getDrawable(R.drawable.close_btn));
        } else {
            btn_menu.setBackground(getResources().getDrawable(R.drawable.ic_menu));
            isMenuActivated = false;
            pdfView.setEnabled(true);
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
    public void onBackPressed() {
        if (isMenuActivated) {
            animateMenuGroup();
            return;
        } else {
            super.onBackPressed();
        }
    }
}
