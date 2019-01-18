package Com.knoventive.nutri.Util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;


public class Custom_Edittext extends AppCompatEditText {
    public Custom_Edittext(Context context) {
        super(context);
        init(context);
    }

    public Custom_Edittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Custom_Edittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context con) {
        Typeface tf = Typeface.createFromAsset(con.getAssets(), "font/Aileron-SemiBold.otf");
        setTypeface(tf);
    }

}