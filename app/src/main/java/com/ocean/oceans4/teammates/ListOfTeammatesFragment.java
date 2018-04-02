package com.ocean.oceans4.teammates;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ocean.oceans4.MainActivity;
import com.ocean.oceans4.R;
import com.ocean.oceans4.base.BaseFragment;
import com.ocean.oceans4.base.StateView;
import com.ocean.oceans4.data.Teammate;
import com.ocean.oceans4.teamate_details.TeammateDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListOfTeammatesFragment extends BaseFragment<ListOfTeammatesEvent, ListOfTeammatesUIModel> implements TeammateClickListener {

	@BindView(R.id.recycler_view)
	RecyclerView mRecyclerView;
	@BindView(R.id.state_view)
	StateView mStateView;
	Unbinder unbinder;

	ListOfTeammatesAdapter adapter;

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
		if (adapter == null) adapter = new ListOfTeammatesAdapter(this);
		mRecyclerView.setAdapter(adapter);
		sendEvent(new ListOfTeammatesEvent());
	}

	@Override
	public void setCurrentState(ListOfTeammatesUIModel model) {
		switch (model.getState()) {
			case ERROR:
				mStateView.showError();
				break;
			case SUCCESS:
				mStateView.showData();
				adapter.setData(model.getTeammates());
				break;
			case IN_PROGRESS:
				mStateView.showProgress();
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
		((MainActivity) getActivity()).addFragment(TeammateDetailsFragment.getInstance(teammate),false);
	}
}
