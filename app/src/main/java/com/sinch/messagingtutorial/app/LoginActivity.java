package com.sinch.messagingtutorial.app;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {

    //Editable text field
    EditText user;
    EditText pass;
    EditText nick;
    //Login/Signup Buttons
    Button loginButton;
    Button signupButton;
    //Naming the buttons
    String userField;
    String passField;
    String nickField;
    private Intent intent;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpagebutton);
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        signupButton = (Button) findViewById(R.id.signup);

        intent = new Intent(getApplicationContext(), ListUsersActivity.class);
        serviceIntent = new Intent(getApplicationContext(), MessageService.class);

        //wait for login button to be pressed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userField = user.getText().toString();
                passField = pass.getText().toString();
                ParseUser.logInInBackground(userField, passField,
                        new LogInCallback() {
                            //Error checks
                            public void done(ParseUser user, ParseException e) {
                                if (user != null)
                                {
                                    startService(serviceIntent);
                                    startActivity(intent);
                                    //  startActivity(new Intent( LoginPage.this,
                                    //Second.class));
                                    //Bubble widget to display text
                                    // Toast.makeText(getApplicationContext(),
                                    //  "Successfully Logged in",
                                    //Toast.LENGTH_LONG).show();
                                    //  finish();
                                }
                                else
                                {
                                    //Bubble widget to display text
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "No such user exist, please signup",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

        });
        //wait for signup button to be pressed
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent( LoginActivity.this,
                        SignUpPage.class));
            }

        });

    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, MessageService.class));
        super.onDestroy();
    }
}


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.my, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//        return super.onOptionsItemSelected(item);
//    }

/**
// * A placeholder fragment containing a simple view.
// */
//public static class PlaceholderFragment extends Fragment {
//
//    public PlaceholderFragment() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.timeline, container, false);
//        return rootView;
//    }
//}
//}
