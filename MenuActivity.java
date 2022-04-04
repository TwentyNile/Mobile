package th.ac.kmutnb.project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public void openshowroom(View v){
        Intent itn = new Intent(this,ShowRoomActivity.class);
        startActivity(itn);
    }
    public void openPromotion (View v){
        Intent itn = new Intent(this,PromotionActivity.class);
        startActivity(itn);
    }

}