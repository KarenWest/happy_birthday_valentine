package com.example.happybirthdayvalentine;
//Karen West, Assignment #3 Android Apps Class, Feb. 9th, 2013
//This app demonstrates the use of concepts learned in Weeks 6 and 7
//and some concepts from earlier weeks as well.
//I used: ScrollView, LinearLayout, OnClickListener, TextView, ImageView
//Visibility, Simple Animation, Intents, EditText and a button for email
//(from the list the professor gave,
//where we must use at least 4 items on that list).
//I tested it on the emulator: Intel 10 480x800-mdpi and it worked fine.
//I tested it on the emulator: Nexus-7-18tvdpi, and it worked fine there too,
//except that when you went to the youtube video (clicking on bottom photo),
//the music played, but you could not see the monkey singing it, as you could
//on the other emulator, and I did not have time to find out why.
//NOTE: for the email part - the Intel 10 email client set up DID NOT work -
//I could NOT set up the email on the emulator.  However it did work on the
//Nexus 7.  Here is the set-up instructions I followed, since I do not yet own
//a mobile device and rely on the emulators for this class.
//http://www.androidaspect.com/2012/06/how-to-send-email-from-android-emulator.html
//These worked on the Nexus 7, so that is where that part will work, with email.
//On the Intel 10 emulated device, if you click send email, you will get the
//toast message to please configure your email device, and when I tried to do that
//there as I did on the Nexus 7, it said it was not allowed on this for some reason.
//However, sending email did work on the Nexus 7.
//The Nexus 7 did NOT work though as mentioned for VIEWING the monkey singing
//happy birthday you tube video - you could hear him but not see him.
//In order to SEE the monkey on you tube singing happy birthday, you have to use the
//Intel 10 device - did not have time to figure out why.


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private static final String TAG = "HappyBirthdayValentineActivity";
	private EditText mEmail;
	
	MediaPlayer beatlesHappyBirthdayMediaPlayer;
	//beatles happy birthday yout tube video not available for mobiles 
	//String url_happybirthdayvalentine = "http://www.youtube.com/watch?v=LPO5UBbR8SE";
	String url_happybirthdayvalentine = "http://www.youtube.com/watch?v=rur5wKoua1o&list=PLA9525CE7B38D5742&index=1";
	//String url_photoAfterAnimate = "file:///android_asset/peter_on_newyearseve_2013into2014.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mEmail = (EditText) findViewById(R.id.email);
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				
				//video not allowed on mobiles
				//change to a photo or happy birthday message
				i.setData(Uri.parse(url_happybirthdayvalentine));
				startActivity(i);        		
			}
		};
		/* another possible way - did other way -from onClick in xml
        final Button emailButton = (Button) findViewById(R.id.emailbutton);
        emailButton.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		sendValentineBirthdayEmail(v);
        	}
        });
*/
		findViewById(R.id.peterwithsaxophone).setOnClickListener(listener);
			
	}
	
	public void sendValentineBirthdayEmail(View v) {
		Log.e(TAG, "sendEmail");

		String email = mEmail.getText().toString();
		String message = null;

		if (email.length() > 0) {
			message = message + "\nAlternative Email:" + email;
		}

//From Professor Lawrence Angrave:
		// FYI There's lots of discussion about email intents on StackOverflow
		// eg SEND vs SENDTO and setting the mimetype to message/rfc822
		// Experimentally the following works on many devices
		// - Tested on Android 1.6 and 4.x phones, and tablets and 2.x,4.x
		// emulator.
		// You will need to configure the emulator's email client with a
		// real email address.

		// To test unsupported schemes change "mailto" to "horseback"
		Log.e(TAG, "creating email Intent");
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
		emailIntent.setData(Uri.fromParts("mailto",
				"incognitodiscforums@gmail.com", null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "happy birthday valentine!");

		emailIntent.putExtra(Intent.EXTRA_TEXT, message);

//From Professor Lawrence Angrave:
		// Better .... use resolveActivity
		// We can check to see if there is a configured email client
		// BEFORE trying to start an activity
		// Using this test we could have prevented the user from ever opening
		// the survey...
		Log.e(TAG,"email intent to resolve activity");
		if (emailIntent.resolveActivity(getPackageManager()) == null) {
			Log.e(TAG, "toast to please configure email client");
			Toast.makeText(getApplicationContext(),
					"Please configure your email client!", Toast.LENGTH_LONG).show();
		} else {
			// Secondly, use a chooser will gracefully handle 0,1,2+ matching
			// activities
			Log.e(TAG, "start activity create chooser");
			startActivity(Intent.createChooser(emailIntent,"Please choose your email app!"));
		}
	}

	@Override
	protected void onResume() {
		Log.e(TAG, "onResume!");
		beatlesHappyBirthdayMediaPlayer = MediaPlayer.create(this, R.raw.the_beatles_birthday_song);
		beatlesHappyBirthdayMediaPlayer.setLooping(true);
		beatlesHappyBirthdayMediaPlayer.start();
		super.onResume();
	}
	@Override
	protected void onPause() {
		Log.e(TAG, "onPause!");
		beatlesHappyBirthdayMediaPlayer.stop();
		beatlesHappyBirthdayMediaPlayer.release();
		super.onPause();
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void animatePeterAndSophie(View v) {
		Log.e(TAG, "animatePeterAndSophie");

		View view = findViewById(R.id.peterandsophie_newyears);
		//view.setVisibility(View.VISIBLE);
		// Declare the variable (the pointer!) The next line does NOT create a
		// new animation object - all we have is pointer so far.
		Animation anim;

		// Create a new animation object
		anim = AnimationUtils.makeOutAnimation(this, true);
		//view.setVisibility(View.INVISIBLE);
		// Tell the view it's time to start animating
		view.startAnimation(anim);
		
		//Intent i = new Intent(Intent.ACTION_VIEW);		
		//i.setData(Uri.parse(url_photoAfterAnimate));
		//startActivity(i);        		
	}
}
