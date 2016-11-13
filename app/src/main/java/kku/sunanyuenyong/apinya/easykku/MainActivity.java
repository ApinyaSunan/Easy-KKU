package kku.sunanyuenyong.apinya.easykku;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit ประกาศตัวแปร เพื่อให้มือถือเชคว่าเครื่องสามารถรองรับแอปนี้ได้มั้ย
    private Button signInButton, signUpButton;
    private EditText userEditText,passwordEditText;
    private String userString,passwordString;
    private  MyConstant myConstant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myConstant = new MyConstant();

        //Bind Widget ผูกตัวแปรเข้ากับ Widget
        signInButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);
        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText = (EditText) findViewById(R.id.editText6);

        //Sign In Controller
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Value from Edit Text
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if (userString.equals("") || passwordString.equals("")) {

                    //Have Space
                    MyAlert myAlert = new MyAlert(MainActivity.this, R.drawable.bird48,
                            getResources().getString(R.string.title_haveSpace),
                            getResources().getString(R.string.message_haveSpace));
                    myAlert.myDialog();

                } else {
                    //No Space
                    SynUser synUser = new SynUser(MainActivity.this);
                    synUser.execute(myConstant.getUrlGetJSON());
                }


            }//on click
        });

        //Sign Up Controller ทำให้ปุ่ม Sign Up มันกดได้
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

    } // Main Method



    private class SynUser extends AsyncTask<String, Void, String> {

        //Explicit
        private Context context;
        private  String[] nameStrings,phoneStrings,imageStrings;
        private String truePassword; //pasword mี่อ่านได้จากฐานข้อมูล
        private boolean aBoolean = true;

        public SynUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                Log.d("13novV2", "e doIn ==> " + e.toString());
                return null;
            }

        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("13novV2", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                nameStrings = new String[jsonArray.length()];
                phoneStrings = new String[jsonArray.length()]; //จองหน่วยความจำ = length
                imageStrings = new String[jsonArray.length()];

                //for loop ตามจำนวน record ที่มันมี
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i); //ตัวชี้
                    nameStrings[i] = jsonObject.getString("Name");
                    phoneStrings[i] = jsonObject.getString("Phone");
                    imageStrings[i] = jsonObject.getString("Image"); // ดึงค่า string ไปใส่ใน array

                    Log.d("13novV3","name(" + i + ") ==> "+ nameStrings[i]);

                    //Check User
                    if (userString.equals(jsonObject.getString("User"))) { //เช็คว่าค่าที่รับมามันมีในฐานข้อมูลมั้ย
                        aBoolean = false;
                        truePassword = jsonObject.getString("Password");
                    }

                }// for

                if (aBoolean) {
                    //User False
                    MyAlert myAlert = new MyAlert(context, R.drawable.kon48,
                            getResources().getString(R.string.title_userFalse),
                            getResources().getString(R.string.message_userFalse));
                    myAlert.myDialog();

                } else if (passwordString.equals(truePassword)) {
                    //password True
                    Toast.makeText(context,"Welcome",Toast.LENGTH_SHORT).show();

                    //Intent to Service
                    Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
                    intent.putExtra("Name", nameStrings);
                    intent.putExtra("Phone", phoneStrings);
                    intent.putExtra("image", imageStrings);
                    startActivity(intent);
                    finish();

                } else {
                    //password False
                    MyAlert myAlert = new MyAlert(context, R.drawable.rat48,
                            getResources().getString(R.string.title_passwordFalse),
                            getResources().getString(R.string.message_passwordFalse));
                    myAlert.myDialog();
                }

            }catch (Exception e){

            }

        }   // onPost

    }   // SynUser



} // Main Class นี่คือ คลาสหลัก
