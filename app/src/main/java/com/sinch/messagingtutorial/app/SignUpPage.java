package com.sinch.messagingtutorial.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by RC on 11/3/14.
 */
public class SignUpPage extends Activity
{
    EditText user;
    EditText pass;
    EditText nick;
    Button signupButton;
    Button backButton;
    String userField;
    String passField;
    String nickField;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        nick = (EditText) findViewById(R.id.nickname);
        signupButton = (Button) findViewById(R.id.signup);
        backButton = (Button) findViewById(R.id.back);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userField = user.getText().toString();
                passField = pass.getText().toString();
                nickField = nick.getText().toString();
                ParseUser.logInInBackground(userField, passField,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {

                                if (userField.length() == 0 || passField.length() == 0
                                        ) {
                                    Toast.makeText(getApplicationContext(),
                                            "Please fill", Toast.LENGTH_LONG).show();
                                } else {
                                    //add new User to Parse
                                    ParseUser newUser = new ParseUser();
                                    newUser.setUsername(userField);
                                    newUser.setPassword(passField);
                                    newUser.put("Nickname", userField);
                                    newUser.signUpInBackground(new SignUpCallback() {
                                        @Override
                                        //Error check on signup
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                //Bubble widget to display text
                                                Toast.makeText(getApplicationContext(),
                                                        "Success Signup!", Toast.LENGTH_LONG).show();
                                            } else {
                                                //Bubble widget to display text
                                                Toast.makeText(getApplicationContext(),
                                                        "Failed Signup!"
                                                        , Toast.LENGTH_LONG).show();
                                            }
                                        }

                                    });
                                }
                            }
                        });
            }

        });//end of signup button
        //wait for back button to be pressed
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(SignUpPage.this,LoginActivity.class));
            }

            });
    }

}
