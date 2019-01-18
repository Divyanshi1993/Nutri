package Com.knoventive.nutri.Util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomTextView_img extends android.support.v7.widget.AppCompatTextView {
    public CustomTextView_img(Context context) {
        super(context);
        init(context);
    }

    public CustomTextView_img(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextView_img(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context con) {
        Typeface tf = Typeface.createFromAsset(con.getAssets(), "font/smof.ttf");
        setTypeface(tf);
    }

}