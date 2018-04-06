package com.ocean.oceans4.teammates;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ocean.oceans4.MainActivity;
import com.ocean.oceans4.R;
import com.ocean.oceans4.base.BaseFragment;
import com.ocean.oceans4.base.StateView;
import com.ocean.oceans4.data.Teammate;
import com.ocean.oceans4.teamate_details.TeammateDetailsFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListOfTeammatesFragment extends BaseFragment<ListOfTeammatesEvent, ListOfTeammatesUIModel> implements TeammateClickListener, AddTeammateDialog.SendData {

	@BindView(R.id.recycler_view)
	RecyclerView mRecyclerView;
	@BindView(R.id.state_view)
	StateView mStateView;
	@BindView(R.id.add_teammate)
	TextView mAddTeammate;
	Unbinder unbinder;
	@BindView(R.id.swipe_refresh)
	SwipeRefreshLayout mSwipeRefresh;

	ListOfTeammatesAdapter adapter;
	AddTeammateDialog dialog;

	public static ListOfTeammatesFragment getInstance() {
		return new ListOfTeammatesFragment();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		setPresenter(new ListOfTeammatesPresenter());
		View view = inflater.inflate(R.layout.fragment_list_teammates, container, false);
		unbinder = ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		if (adapter == null) adapter = new ListOfTeammatesAdapter(this, getActivity());
		mRecyclerView.setAdapter(adapter);
		initAddLink();
		mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				sendEvent(new ListOfTeammatesEvent.GetTeammates());
			}
		});
		sendEvent(new ListOfTeammatesEvent.GetTeammates());
	}

	private void initAddLink() {
		SpannableString ss = new SpannableString(getString(R.string.add_teammate));
		ClickableSpan clickableSpan = new ClickableSpan() {
			@Override
			public void onClick(View textView) {
				dialog = new AddTeammateDialog(getActivity(), ListOfTeammatesFragment.this);
				dialog.showDialog();
			}

			@Override
			public void updateDrawState(TextPaint ds) {
				super.updateDrawState(ds);
				ds.setUnderlineText(true);
			}
		};
		ss.setSpan(clickableSpan, 0, getString(R.string.add_teammate).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mAddTeammate.setText(ss);
		mAddTeammate.setMovementMethod(LinkMovementMethod.getInstance());
		mAddTeammate.setHighlightColor(Color.TRANSPARENT);
	}

	@Override
	public void setCurrentState(ListOfTeammatesUIModel model) {
		switch (model.getState()) {
			case ERROR:
				if (dialog != null && dialog.isShowing()) dialog.dismissDialog();
				mSwipeRefresh.setRefreshing(false);
				mStateView.showDefaultErrorWithText(onError(model.getError()));
				mStateView.getErrorLayout()
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							sendEvent(new ListOfTeammatesEvent.GetTeammates());
						}
					});
				break;
			case SUCCESS:
				mStateView.showData();
				mSwipeRefresh.setRefreshing(false);
				if (model.getTeammates() != null) {
					adapter.setData(new ArrayList<Teammate>(model.getTeammates()
						.values()));
				} else if (model.getTeammate() != null) {
					dialog.dismissDialog();
					adapter.addData(model.getTeammate());
				}
				break;
			case IN_PROGRESS:
				if (dialog != null && dialog.isShowing()) {
					dialog.showProgress();
				} else if (!mSwipeRefresh.isRefreshing()) {
					mStateView.showProgress();
				}
				break;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	@Override
	public void onClick(Teammate teammate) {
		((MainActivity) getActivity()).addFragment(TeammateDetailsFragment.getInstance(teammate), false);
	}

	@Override
	public void sendData(Teammate teammate) {
		Map<String, Teammate> request = new LinkedHashMap<>();
		request.put(String.valueOf(teammate.id), teammate);
		sendEvent(new ListOfTeammatesEvent.AddTeammate(request));
	}
}
