package kku.sunanyuenyong.apinya.easykku;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, phoneEditText, userEditText, passwordEditText;
    private ImageView imageView;
    private Button button;
    private String nameString, phoneString, userString, passwordString,
            imagePathString, imageNameString;
    private Uri uri; // โยน Data กลับมา แล้วเราต้องมาคัดว่าอันไหนคือรูปภาพอีกที

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind widget
        nameEditText = (EditText) findViewById(R.id.editText);
        phoneEditText = (EditText) findViewById(R.id.editText2);
        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button3);


        //SignUp Controller ทำให้ปุ่ม สมัครสมาชิก สามารถคลิกได้
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Value from EditText
                nameString = nameEditText.getText().toString().trim(); //รับค่าจาก EditText เปลี่ยนเป็นสตริง และตัดช่องว่างซ้ายขวา
                phoneString = phoneEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space ดูว่ามีช่องว่างเปล่า คือ กรอกครบทุกช่องไหม
                if (nameString.equals("") || phoneString.equals("") ||
                        userString.equals("") || passwordString.equals("")) {
                    //Have Space
                    Log.d("12novV1", "Have Space");
                    MyAlert myAlert = new MyAlert(SignUpActivity.this,
                            R.drawable.doremon48,"มีช่องว่าง","กรุณากรอกให้ครบทุกช่อง");
                    //M ใหญ่ Class m เล็ก object
                    myAlert.myDialog();
                }

            }//onClick
        });

        // Image Controller ทำให้รูปภาพสามารถคลิกได้
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //เดี๋ยวมันจะไปทำที่โปรแกรมอื่น แล้วโปรแกรมนั้นจะโยนค่ากลับมา คือเราจะโยนไป Gallery
                intent.setType("image/*"); // เมื่อไหร่ก็ตามที่กดปุ่มภาพ ให้เปิดโปรแกรมที่เปิดภาพได้ แล้วให้ user เลือกโปรแกรม
                //ถ้าเป็น video ก็ video/*
                startActivityForResult(Intent.createChooser(intent,"โปรดเลือกแอปดูภาพ"),0);
                // บางทีมีรูปภาพมากกว่า 1 รูป เป็นตัว request code ใส่ 0 น่าจะใส่ได้แค่รูปเดียว

            } // onClick
        });

    }// Main Method

    // Override ดึง method สำเร็จรูปมาทำในเครื่องคุณ


    @Override
    protected void onActivityResult(int requestCode, //ได้ 0 มาจากข้างบน
                                    int resultCode, // ได้ OK ต้องเลือกภาพถึงจะสำเร็จ
                                    Intent data) { //ได้ข้อมูลภาพ
        super.onActivityResult(requestCode, resultCode, data); //พอส่ง user ไปเลือกภาพ แล้วมันจะกลับมาทำตรงนี้ต่อ

        if ((requestCode == 0) && (resultCode == RESULT_OK)) {

            Log.d("12novV1","Result OK");

            //Show Image
            uri = data.getData();
            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(uri));
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) { // ถ้า try error มันจะส่ง error มาให้ e จัดการกับ error
                e.printStackTrace();
            }

            //Find Path of Image
            imagePathString = myFindPath(uri); //method นี้จะกรองให้ได้รูปภาพขึ้นมา
            Log.d("12novV1","imagePath ==>"+imagePathString);

            //find Name of Image
            imageNameString = imagePathString.substring(imagePathString.lastIndexOf("/"));
            //หา index สุดท้าย ที่ถูกตัดด้วย /
            Log.d("12novV1","imageName ==>"+imageNameString);


        }// if

    } // onActivityResult

    private String myFindPath(Uri uri) {

        String result = null;
        String[] strings = {MediaStore.Images.Media.DATA}; // กรองรูปออกมาได้
        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);

        if (cursor != null) { // ถ้า boolean มี data
            //ถ้ามีหลายรูป ให้ค้นหาว่าเป็น 0 or 1 or 2 or 3 ect.
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); //รูปภาพที่เลือก
            result = cursor.getString(index); // ได้ path ของรูปภาพ
        } else {
            result = uri.getPath(); //ถ้ามีรูปแค่รูปเดียว ก็เอารูปนั้นเลย
        }

        return result;
    }
}// Main Class
