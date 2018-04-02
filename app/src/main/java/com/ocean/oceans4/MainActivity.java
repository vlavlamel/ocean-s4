package com.ocean.oceans4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ocean.oceans4.base.BaseFragment;
import com.ocean.oceans4.teammates.ListOfTeammatesFragment;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addFragment(ListOfTeammatesFragment.getInstance(), true);
	}

	public void addFragment(BaseFragment fragment, boolean inContainer) {
		getSupportFragmentManager().beginTransaction()
			.add(inContainer ? R.id.container : android.R.id.content, fragment)
			.addToBackStack(fragment.getClass()
				.getName())
			.commit();
	}

	@Override
	public void onBackPressed() {
		if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
			finish();
		} else {
			super.onBackPressed();
		}
	}


}
