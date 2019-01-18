package Com.knoventive.nutri.Util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import Com.knoventive.nutri.Activity.Calculator_instructions;
import Com.knoventive.nutri.Activity.Contact_screen;
import Com.knoventive.nutri.Activity.HomeScreen;
import Com.knoventive.nutri.Activity.Product_info_pdf_screen;
import Com.knoventive.nutri.Activity.Product_info_selection_screen;
import Com.knoventive.nutri.R;

import static Com.knoventive.nutri.Activity.Product_info_selection_screen.prd_context;
import static Com.knoventive.nutri.Activity.RegimenScreen.reg_Context;


public class DialopgPopup implements View.OnClickListener {
    private LinearLayout cal_inf, prd_inf, contact_fres;
    private Context context;

    /*
    show dialog
     */
    public void showDialog(Context context, LinearLayout cal_inf, LinearLayout prd_inf, LinearLayout contact_fres) {
        this.context = context;
        this.cal_inf = cal_inf;
        this.contact_fres = contact_fres;
        this.prd_inf = prd_inf;
        initDialogViews();
    }
    /*
    Initialize views here
     */
    private void initDialogViews() {
        cal_inf.setOnClickListener(this);
        prd_inf.setOnClickListener(this);
        contact_fres.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cal_inf:
                // check wether context is instanceof  Calculator_instructions activity
                if (context instanceof Calculator_instructions) {
                    // if yes than remain onn same activity
                    ((Calculator_instructions) context).animateMenuGroup();
                } else {
                    // if not than pass to Calculator_instructions activity
                    Intent prd_inf = new Intent(context, Calculator_instructions.class);
                    if ((context instanceof HomeScreen)) {
                        ((HomeScreen) context).animateMenuGroup();
                    } else {
                        ((AppCompatActivity) context).finish();
                        if (context instanceof Product_info_pdf_screen) {
                            if (reg_Context != null && prd_context != null) {
                                ((AppCompatActivity) prd_context).finish();
                                ((AppCompatActivity) reg_Context).finish();
                            }
                        }
                    }
                    context.startActivity(prd_inf);
                }
                break;
            case R.id.prd_inf:
                // check wether context is instanceof  Product_info_selection_screen activity
                if (context instanceof Product_info_selection_screen) {
                    // if yes than remain onn same activity
                    ((Product_info_selection_screen) context).animateMenuGroup();
                } else {
                    // if not than pass to Product_info_selection_screen activity
                    Intent prd_inf = new Intent(context, Product_info_selection_screen.class);
                    if (context instanceof HomeScreen) {
                        ((HomeScreen) context).animateMenuGroup();
                    } else {
                        ((AppCompatActivity) context).finish();
                        if (context instanceof Product_info_pdf_screen) {
                            if (reg_Context != null && prd_context != null) {
                                ((AppCompatActivity) prd_context).finish();
                                ((AppCompatActivity) reg_Context).finish();
                            }
                        }
                    }
                    context.startActivity(prd_inf);
                }
                break;
            case R.id.contact_fres:
                // check wether context is instanceof  Contact_screen activity
                if (context instanceof Contact_screen) {
                    // if yes than remain onn same activity
                    ((Contact_screen) context).animateMenuGroup();
                } else {
                    // if not than pass to Contact_screen activity
                    Intent contact_fres = new Intent(context, Contact_screen.class);
                    if (context instanceof HomeScreen) {
                        ((HomeScreen) context).animateMenuGroup();
                    } else {
                        ((AppCompatActivity) context).finish();
                        if (context instanceof Product_info_pdf_screen) {
                            if (reg_Context != null && prd_context != null) {
                                ((AppCompatActivity) prd_context).finish();
                                ((AppCompatActivity) reg_Context).finish();
                            }
                        }
                    }
                    context.startActivity(contact_fres);
                }
                break;
        }

    }}
