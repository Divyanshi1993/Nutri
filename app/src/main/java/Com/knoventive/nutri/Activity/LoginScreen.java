package Com.knoventive.nutri.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import Com.knoventive.nutri.Util.ScreenOrientationControl;
import Com.knoventive.nutri.R;
import Com.knoventive.nutri.prefrence.pass_pref;



public class LoginScreen extends AppCompatActivity {
    AppCompatEditText activation_password;
    private pass_pref pref;
   private Button btn_login ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ScreenOrientationControl(this).setOrientation();
        setContentView(R.layout.activity_login_screen);
        activation_password = (AppCompatEditText) findViewById(R.id.activation_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activation_password.getText().toString().equals(getResources().getString(R.string.password))) {
                   loginApp();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_passsword), Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void loginApp() {
        Intent done_intent = new Intent(getApplicationContext(), HomeScreen.class);
      //  done_intent.putExtra("data",new DataList("","",""));
        pref = new pass_pref(getApplicationContext());
        pref.setpass(getResources().getString(R.string.password));
        startActivity(done_intent);
        finish();
    }
}
