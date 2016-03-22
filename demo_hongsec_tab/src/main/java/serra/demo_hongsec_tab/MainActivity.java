package serra.demo_hongsec_tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void guide(View view){
         startActivity(new Intent(getApplicationContext(),GuideActivity.class));
    }

    public void tab(View view){
        startActivity(new Intent(getApplicationContext(), MainTabActivity.class));
    }

    public void scrolltab(View view){
        startActivity(new Intent(getApplicationContext(), ScrollTabActivity.class));
    }

    public void springtab(View view){
        startActivity(new Intent(getApplicationContext(), SpringActivity.class));
    }

    public void settingtab(View view){
        startActivity(new Intent(getApplicationContext(),SettingActivity.class));
    }

}
