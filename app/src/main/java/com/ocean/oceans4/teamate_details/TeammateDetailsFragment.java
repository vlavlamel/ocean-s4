package com.ocean.oceans4.teamate_details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ocean.oceans4.MainActivity;
import com.ocean.oceans4.R;
import com.ocean.oceans4.base.BaseFragment;
import com.ocean.oceans4.data.Teammate;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeammateDetailsFragment extends BaseFragment<TeammateDetailsUIEvent, TeammateDetailsUIModel> {

	public static final String TEAMMATE_TAG = "key:teammate";

	@BindView(R.id.photo)
	CircleImageView mPhoto;
	@BindView(R.id.name)
	TextView mName;
	@BindView(R.id.group)
	TextView mGroup;
	@BindView(R.id.post)
	TextView mPost;
	@BindView(R.id.about)
	TextView mAbout;
	Unbinder unbinder;
	@BindView(R.id.toolbar)
	Toolbar mToolbar;

	public static TeammateDetailsFragment getInstance(Teammate teammate) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(TEAMMATE_TAG, teammate);
		TeammateDetailsFragment fragment = new TeammateDetailsFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		setPresenter(new TeammateDetailsPresenter());
		View view = inflater.inflate(R.layout.fragment_teamate_details, container, false);
		unbinder = ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager()
					.popBackStack();
			}
		});
		renderInfo((Teammate) getArguments().getSerializable(TEAMMATE_TAG));
	}

	private void renderInfo(Teammate teammate) {
		mName.setText(teammate.name);
		mPost.setText(teammate.post);
		mAbout.setText(teammate.about);
		mGroup.setText(teammate.group);
		Picasso.get()
			.load(teammate.photo)
			.into(mPhoto);
	}

	@Override
	public void setCurrentState(TeammateDetailsUIModel model) {

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
