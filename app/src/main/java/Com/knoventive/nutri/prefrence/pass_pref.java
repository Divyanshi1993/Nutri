package Com.knoventive.nutri.prefrence;

import android.content.Context;
import android.content.SharedPreferences;

public class pass_pref {
    private SharedPreferences pref;
    private Context mcontext;
    private String pass = "pass";
    private SharedPreferences.Editor editor;

    public pass_pref(Context context) {
        this.mcontext = context;
        pref = mcontext.getSharedPreferences("DatePref", 0);
        editor = pref.edit();
    }

    /*
    set password to prefrence
     */
    public void setpass(String value) {
        editor.putString(pass, value);
        editor.commit();
    }

    /*
    get password from prefrence
     */
    public String getPass() {
        return pref.getString(pass, null);
    }

    /*
    clear session from here
     */
    public void clearSession() {
        editor.clear();
        editor.commit();

    }

}
