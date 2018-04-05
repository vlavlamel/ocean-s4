package com.ocean.oceans4.teamate_details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ocean.oceans4.R;
import com.ocean.oceans4.base.BaseFragment;
import com.ocean.oceans4.data.Teammate;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeammateDetailsFragment extends BaseFragment<TeammateDetailsUIEvent, TeammateDetailsUIModel> implements ChangeInfoDialog.SendData {

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

	ChangeInfoDialog dialog;

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
	public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
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
		mName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = new ChangeInfoDialog(getString(R.string.name), mName.getText()
					.toString(), getActivity(), TeammateDetailsFragment.this, false, ChangeInfo.NAME);
				dialog.showDialog();
			}
		});
		mPost.setText(teammate.post);
		mPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = new ChangeInfoDialog(getString(R.string.post), mPost.getText()
					.toString(), getActivity(), TeammateDetailsFragment.this, false, ChangeInfo.POST);
				dialog.showDialog();
			}
		});
		mAbout.setText(teammate.about);
		mAbout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = new ChangeInfoDialog(getString(R.string.about), mAbout.getText()
					.toString(), getActivity(), TeammateDetailsFragment.this, true, ChangeInfo.ABOUT);
				dialog.showDialog();
			}
		});
		mGroup.setText(teammate.group);
		mGroup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = new ChangeInfoDialog(getString(R.string.group), mGroup.getText()
					.toString(), getActivity(), TeammateDetailsFragment.this, false, ChangeInfo.GROUP);
				dialog.showDialog();
			}
		});
		if (teammate.photo != null) {
			Picasso.get()
				.load(teammate.photo)
				.into(mPhoto);
		} else {
			mPhoto.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.no_photo_account));
		}
	}

	@Override
	public void setCurrentState(TeammateDetailsUIModel model) {
		switch (model.getState()) {
			case ERROR:
				if (dialog != null) dialog.dismissDialog();
				Toast.makeText(getActivity(), onError(model.getError()), Toast.LENGTH_SHORT).show();
				break;
			case SUCCESS:
				if (dialog != null) {
					dialog.dismissDialog();
					switch (dialog.getType()) {
						case NAME:
							mName.setText(model.getData());
							break;
						case POST:
							mPost.setText(model.getData());
							break;
						case ABOUT:
							mAbout.setText(model.getData());
							break;
						case GROUP:
							mGroup.setText(model.getData());
							break;
					}
				}
				break;
			case IN_PROGRESS:
				if (dialog != null) dialog.showProgress();
				break;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	@Override
	public void sendData(String data, ChangeInfo type) {
		int id = ((Teammate) getArguments().getSerializable(TEAMMATE_TAG)).id;
		switch (type) {
			case NAME:
				sendEvent(new TeammateDetailsUIEvent.ChangeNameEvent(id, data));
				break;
			case POST:
				sendEvent(new TeammateDetailsUIEvent.ChangePostEvent(id, data));
				break;
			case ABOUT:
				sendEvent(new TeammateDetailsUIEvent.ChangeAboutEvent(id, data));
				break;
			case GROUP:
				sendEvent(new TeammateDetailsUIEvent.ChangeGroupEvent(id, data));
				break;
		}
	}
}
