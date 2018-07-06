package org.openobservatory.ooniprobe.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.github.paolorotolo.appintro.AppIntro;

import org.openobservatory.ooniprobe.R;
import org.openobservatory.ooniprobe.fragment.consent.IConsentPage1Fragment;
import org.openobservatory.ooniprobe.fragment.consent.IConsentPage2Fragment;
import org.openobservatory.ooniprobe.fragment.consent.IConsentPage3Fragment;
import org.openobservatory.ooniprobe.fragment.consent.IConsentPage4Fragment;
import org.openobservatory.ooniprobe.fragment.consent.IConsentQuizFragment;

public class InformedConsentActivity extends AppIntro {
	public static final int REQUEST_CODE = 1000;
	public static final int RESULT_CODE_COMPLETED = 1;
	public int QUESTION_NUMBER = 1;
	private IConsentPage1Fragment fragment1;
	private IConsentPage2Fragment fragment2;
	private IConsentPage3Fragment fragment3;
	private IConsentPage4Fragment fragment4;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragment1 = new IConsentPage1Fragment();
		fragment2 = new IConsentPage2Fragment();
		fragment3 = new IConsentPage3Fragment();
		fragment4 = new IConsentPage4Fragment();
		addSlide(fragment1);
		addSlide(fragment2);
		addSlide(fragment3);
		addSlide(fragment4);
		setSeparatorColor(getResources().getColor(android.R.color.transparent));
		showSkipButton(false);
		setSkipText(getString(R.string.Onboarding_DefaultSettings_Button_Change));
		setDoneText(getString(R.string.Onboarding_DefaultSettings_Button_Go));
	}

	@Override
	public void onSkipPressed(Fragment currentFragment) {
		super.onSkipPressed(currentFragment);
		//TODO open settings
		next();
	}

	@Override
	public void onDonePressed(Fragment currentFragment) {
		super.onDonePressed(currentFragment);
		next();
	}

	@Override
	public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
		super.onSlideChanged(oldFragment, newFragment);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	public void loadQuizFragment() {
		FragmentManager fm = getSupportFragmentManager();
		IConsentQuizFragment dFragment = new IConsentQuizFragment();
		dFragment.mActivity = this;
		dFragment.show(fm, "quiz");
	}

	@Override
	public void onPageSelected(int position) {
		if (position == 2 && QUESTION_NUMBER < 3) {
			setNextPageSwipeLock(true);
		} else
			setNextPageSwipeLock(false);
		if (position == 3)
			showSkipButton(true);
		else
			showSkipButton(false);
	}

	public void hideNextButton() {
		fragment3.hideNextButton();
	}

	private void next() {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		editor.putBoolean("include_ip", false);
		editor.putBoolean("include_asn", true);
		editor.putBoolean("include_country", true);
		editor.putBoolean("upload_results", true);
		editor.apply();
		setResult(RESULT_CODE_COMPLETED);
		finish();
	}
}
