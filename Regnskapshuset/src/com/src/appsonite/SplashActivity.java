package com.src.appsonite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class SplashActivity extends Activity {
	
	private int 	_splashTime = 3000; // time to display the splash screen in ms
	private ImageView mLogo;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        mLogo = (ImageView)findViewById(R.id.logo_img_id);
        startAnimation();
    }
    
    private void startAnimation() {
		AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.3f);
		alpha.setDuration(_splashTime);
		alpha.setFillAfter(true);
		alpha.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent loginIntent = new Intent(getBaseContext(), TabManageActivity.class);
				startActivity(loginIntent);
				finish();
				overridePendingTransition(R.anim.fade_hold, R.anim.fade);
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
		});
		mLogo.setAnimation(alpha);
    }
}