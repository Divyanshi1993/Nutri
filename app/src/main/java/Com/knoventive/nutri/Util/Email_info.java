package Com.knoventive.nutri.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;

import Com.knoventive.nutri.model.DataList;
public class Email_info {
    private String[] TO;

    public Email_info(Context context, ArrayList<DataList> nutritionList) {
        // send email to desired person
        TO = new String[]{""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        if (nutritionList != null) {
            TO = new String[]{""};
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Dose Regimen");
            StringBuilder sb = new StringBuilder();
            for (DataList s : nutritionList) {
                sb.append(s.getValues());
                sb.append("\n");
            }
            emailIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
        } else {
            emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        }
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        try {
            context.startActivity(emailIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
