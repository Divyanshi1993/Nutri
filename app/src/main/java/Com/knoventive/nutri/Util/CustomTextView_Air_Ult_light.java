package Com.knoventive.nutri.Util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomTextView_Air_Ult_light extends android.support.v7.widget.AppCompatAutoCompleteTextView {
    public CustomTextView_Air_Ult_light(Context context) {
        super(context);
        init(context);
    }

    public CustomTextView_Air_Ult_light(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextView_Air_Ult_light(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context con) {
        Typeface tf = Typeface.createFromAsset(con.getAssets(), "font/Aileron-UltraLight.otf");
        setTypeface(tf);
    }

}