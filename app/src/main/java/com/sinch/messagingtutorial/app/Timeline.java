package com.sinch.messagingtutorial.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by RC on 11/5/14.
 */
public class Timeline extends Activity
{
    //Create Buttons && Text/Image Views
    Button logout;
    TextView currentUserN;
    TextView displayHobby;
    TextView displayBirthday;
    TextView displayHometown;

    ImageView ProPic;
    Boolean picture = false;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        //setup xml with class
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);
        //Logout && ProPic Button
        logout = (Button) findViewById(R.id.logout);
        ProPic = (ImageView) findViewById(R.id.targetimage);
        currentUserN = (TextView) findViewById(R.id.currentUser);
        displayBirthday = (TextView) findViewById(R.id.displayBirthday);
        displayHobby = (TextView) findViewById(R.id.displayHobby);
        displayHometown = (TextView) findViewById(R.id.displayHometown);

        currentUserN.setText(ParseUser.getCurrentUser().get("Nickname").toString());
        displayBirthday.setText("Birthday: " + ParseUser.getCurrentUser().get("Birthday").toString());
        displayHobby.setText("Hobby: " + ParseUser.getCurrentUser().get("Hobby").toString());
        displayHometown.setText("Hometown: " + ParseUser.getCurrentUser().get("Hometown").toString());
        //Wait for Logout Button to be pressed
        logout.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0) {
                // Logout current user
                ParseUser.logOut();
                //finish();
                Intent intent = new Intent( Timeline.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        });
        //Set Profile Pic
        String ParseUsername = ParseUser.getCurrentUser().getUsername();
        //Start a Query from Profile Picture Class Parse Database
        ParseQuery query = new ParseQuery("ImageUpload");
        query.whereEqualTo("Uploader",ParseUsername);
        List<ParseObject> listOfProPics = null;
        try {
            listOfProPics = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("Size", "size" + listOfProPics.size());
        String objectId = " ";
        for(int i=0;i<listOfProPics.size();++i)
        {
            //Search based on Uploader
            Log.d("Uploader is","hm:" +listOfProPics.get(i).get("Uploader"));
            Log.d("ParseUser= ", ParseUsername.toString());
            if(listOfProPics.get(i).get("Uploader").equals(ParseUsername.toString()))
            {
                //if Found make it the current ID
                Log.d("We","MadeIt");
                objectId = listOfProPics.get(i).getObjectId();
                Log.d("INNEROBTEST","Test "+objectId);
            }
            if (listOfProPics.size() < 1)
            {
                picture = false;
            }
            else
            {
                picture = true;
            }
        }
        if (picture) {
            Log.d("Out", "Test " + objectId);
            ParseObject fileHolder = null;
            try {
                Log.d("We", "MadeIt2");
                //Get the object from Parse
                fileHolder = query.get(objectId);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Get specific COL from Parse
            ParseFile retrievePic = (ParseFile) fileHolder.get("ImageFile");
            byte[] fileByte = new byte[0];
            try {
                Log.d("We", "MadeIt3");
                fileByte = retrievePic.getData();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Convert to image and Display
            Bitmap images = BitmapFactory.decodeByteArray(fileByte, 0, fileByte.length);
            ProPic.setImageBitmap(images);
        }
        else
        {
            Log.d("TEST","IT HAS NO PICTURE");
            ParseObject fileHolder2 = null;
            byte[] fileByte = new byte[0];
            try {
                fileHolder2 = query.get("PvnNbVgL3f"); //MAKE THIS THE DEFAULT PICTURE
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ParseFile retrievePic = (ParseFile) fileHolder2.get("ImageFile");
            try {
                fileByte = retrievePic.getData();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Bitmap images = BitmapFactory.decodeByteArray(fileByte, 0, fileByte.length);
            ProPic.setImageBitmap(images);
        }
    }
}