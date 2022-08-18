package com.capstoneproject.studyhelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView show;
    private Button sign_in, Log_in, show_user_btn;
    private EditText Name_in, Pass_in;
    String User_Name, User_Password;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = findViewById(R.id.textView);
        sign_in = findViewById(R.id.Sign_in_btn);
        Log_in = findViewById(R.id.Login_btn);
        show_user_btn = findViewById(R.id.Show_user_btn);
        Name_in = findViewById(R.id.Login_Name);
        Pass_in = findViewById(R.id.Login_Password);
        show.setText("Welcome to\n Study helper App");
        Name_in.setText("Enter Your Name");
        Pass_in.setText("");

        Log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Name = Name_in.getText().toString();
                User_Password = Pass_in.getText().toString();
                if (dbHelper.ExistingUser(User_Name, User_Password) == true) {

                    makeToast("Logged in successfully!");
                    OpenIndexPage();
                }
                else
                {
                    AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Sorry");
                    builder.setMessage("Wrong Credentials!");
                    builder.show();
                }
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Name = Name_in.getText().toString();
                User_Password = Pass_in.getText().toString();
                if(dbHelper.NewUser(User_Name,User_Password) == true)
                {
                    makeToast("Successfully Created!");
                    OpenIndexPage();
                }
                else
                {
                    AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Sorry");
                    builder.setMessage("Your Credentials may Exsists already!");
                    builder.show();
                }
            }
        });
        show_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer strbuff = new StringBuffer();
                strbuff = dbHelper.UserDetails();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("All user Details");
                builder.setMessage(strbuff.toString());
                builder.show();
            }
        });

    }

    private void OpenIndexPage()
    {
        Intent intent = new Intent(this,index_page.class);
        startActivity(intent);
    }
    private void makeToast(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}