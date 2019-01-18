package Com.knoventive.nutri.Util;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;


public class ScreenOrientationControl {
    public Boolean isXtraLarge = false;
    private AppCompatActivity activity;

    public ScreenOrientationControl(AppCompatActivity appCompatActivity) {
        activity = appCompatActivity;
        if ((activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            isXtraLarge = true;
        }
    }

    public void setOrientation() {
        if (isXtraLarge)
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}