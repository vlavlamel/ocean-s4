package com.ocean.oceans4.teamate_details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ocean.oceans4.R;
import com.ocean.oceans4.base.BaseFragment;
import com.ocean.oceans4.data.Teammate;
import com.squareup.picasso.Picasso;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

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
	@BindView(R.id.rating)
	MaterialRatingBar mRating;

	ChangeInfoDialog dialog;

	private ObservableEmitter<Float> emitter;

	private Observable<Float> ratingObservable = Observable.create(new ObservableOnSubscribe<Float>() {
		@Override
		public void subscribe(ObservableEmitter<Float> emitter) throws Exception {
			TeammateDetailsFragment.this.emitter = emitter;
			TeammateDetailsFragment.this.emitter.setCancellable(new Cancellable() {
				@Override
				public void cancel() throws Exception {
					TeammateDetailsFragment.this.emitter = null;
				}
			});
		}
	})
		.debounce(400, TimeUnit.MILLISECONDS)
		.distinctUntilChanged()
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread());

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
		mToolbar.inflateMenu(R.menu.menu_temmate);
		mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				permitRedacting();
				return true;
			}
		});
		renderInfo((Teammate) getArguments().getSerializable(TEAMMATE_TAG));
		initRating();
		garbage.add(ratingObservable.subscribe(new Consumer<Float>() {
			@Override
			public void accept(Float aFloat) throws Exception {
				int id = ((Teammate) getArguments().getSerializable(TEAMMATE_TAG)).id;
				sendEvent(new TeammateDetailsUIEvent.RatingEvent(id, aFloat));
			}
		}));
	}

	private void initRating() {
		mRating.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
			@Override
			public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
				emitter.onNext(rating);
			}
		});
	}

	private void renderInfo(Teammate teammate) {
		mPost.setText(teammate.post);
		mAbout.setText(teammate.about);
		mGroup.setText(teammate.group);
		mName.setText(teammate.name);
		if (teammate.rating != null) mRating.setRating(teammate.rating);

		if (teammate.photo != null) {
			Picasso.get()
				.load(teammate.photo)
				.into(mPhoto);
		} else {
			mPhoto.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.no_photo_account));
		}
	}

	private void permitRedacting() {
		mName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mode_edit, 0);
		mName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = new ChangeInfoDialog(getString(R.string.name), mName.getText()
					.toString(), getActivity(), TeammateDetailsFragment.this, false, ChangeInfo.NAME);
				dialog.showDialog();
			}
		});
		mPost.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mode_edit, 0);
		mPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = new ChangeInfoDialog(getString(R.string.post), mPost.getText()
					.toString(), getActivity(), TeammateDetailsFragment.this, false, ChangeInfo.POST);
				dialog.showDialog();
			}
		});
		mAbout.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mode_edit, 0);
		mAbout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = new ChangeInfoDialog(getString(R.string.about), mAbout.getText()
					.toString(), getActivity(), TeammateDetailsFragment.this, true, ChangeInfo.ABOUT);
				dialog.showDialog();
			}
		});
		mGroup.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mode_edit, 0);
		mGroup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = new ChangeInfoDialog(getString(R.string.group), mGroup.getText()
					.toString(), getActivity(), TeammateDetailsFragment.this, false, ChangeInfo.GROUP);
				dialog.showDialog();
			}
		});

		mToolbar.getMenu()
			.findItem(R.id.action_edit)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// finish editing
				mName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				mName.setOnClickListener(null);
				mAbout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				mAbout.setOnClickListener(null);
				mGroup.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				mGroup.setOnClickListener(null);
				mPost.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				mPost.setOnClickListener(null);
				mToolbar.getMenu()
					.findItem(R.id.action_edit)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
				mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						permitRedacting();
						return true;
					}
				});
				return true;
			}
		});
	}

	@Override
	public void setCurrentState(TeammateDetailsUIModel model) {
		switch (model.getState()) {
			case ERROR:
				if (dialog != null) dialog.dismissDialog();
				Toast.makeText(getActivity(), onError(model.getError()), Toast.LENGTH_SHORT)
					.show();
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
