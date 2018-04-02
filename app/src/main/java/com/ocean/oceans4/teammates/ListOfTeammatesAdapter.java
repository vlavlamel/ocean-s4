package com.ocean.oceans4.teammates;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ocean.oceans4.R;
import com.ocean.oceans4.data.Teammate;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ListOfTeammatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

	private List<Teammate> listOfTeammates;
	private RecyclerView recyclerView;
	private TeammateClickListener clickListener;

	public ListOfTeammatesAdapter(TeammateClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public void setData(List<Teammate> listOfTeammates) {
		this.listOfTeammates = listOfTeammates;
		notifyDataSetChanged();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View inflatedView = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.item_teammate, parent, false);
		return new ViewHolder(inflatedView);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		((ViewHolder) holder).onBind(position);
		holder.itemView.setOnClickListener(this);
	}

	@Override
	public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		this.recyclerView = recyclerView;
	}

	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		super.onDetachedFromRecyclerView(recyclerView);
		this.recyclerView = null;
	}

	@Override
	public int getItemCount() {
		if (listOfTeammates == null) return 0;
		else return listOfTeammates.size();
	}

	@Override
	public void onClick(View v) {
		if (recyclerView != null && clickListener != null) {
			RecyclerView.ViewHolder holder = recyclerView.findContainingViewHolder(v);
			if (holder != null) {
				clickListener.onClick(listOfTeammates.get(holder.getAdapterPosition()));
			}
		}
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.photo)
		CircleImageView mPhoto;
		@BindView(R.id.name)
		TextView mName;
		@BindView(R.id.post)
		TextView mPost;

		public ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, itemView);
		}

		public void onBind(int position) {
			Teammate currentTeammate = listOfTeammates.get(position);
			mName.setText(currentTeammate.name);
			mPost.setText(currentTeammate.post);
			Picasso.get()
				.load(currentTeammate.photo)
				.into(mPhoto);
		}
	}
}
