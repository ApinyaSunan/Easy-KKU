package kku.sunanyuenyong.apinya.easykku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Explicit ประกาศตัวแปร เพื่อให้มือถือเชคว่าเครื่องสามารถรองรับแอปนี้ได้มั้ย
    private Button signInButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget ผูกตัวแปรเข้ากับ Widget
        signInButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);

        //Sign Up Controller ทำให้ปุ่ม Sign Up มันกดได้
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

    } // Main Method

} // Main Class นี่คือ คลาสหลัก
