package com.ocean.oceans4.teammates;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ocean.oceans4.R;
import com.ocean.oceans4.data.Teammate;
import com.ocean.oceans4.teamate_details.ChangeInfo;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddTeammateDialog {

	@BindView(R.id.name_text)
	MaterialEditText mNameText;
	@BindView(R.id.group_text)
	MaterialEditText mGroupText;
	@BindView(R.id.post_text)
	MaterialEditText mPostText;
	@BindView(R.id.about_text)
	MaterialEditText mAboutText;
	@BindView(R.id.save)
	Button mSave;
	@BindView(R.id.progress)
	ProgressBar mProgress;

	private MaterialDialog mDialog;
	private Context ctx;
	private Unbinder mUnBinder;
	private SendData sendData;
	private boolean isShowing;

	public AddTeammateDialog(Context ctx, SendData sendData) {
		this.ctx = ctx;
		mDialog = new MaterialDialog.Builder(ctx)
			.customView(R.layout.dialog_add_teammate, false)
			.build();
		this.sendData = sendData;
		mUnBinder = ButterKnife.bind(this, mDialog);
	}

	public void showDialog() {
		isShowing = true;
		mDialog.show();
		if (mUnBinder == null) mUnBinder = ButterKnife.bind(this, mDialog);
	}

	public void setErrorFromServer(String message) {
		dismissProgress();
	}

	public void dismissProgress() {
		mSave.setVisibility(View.VISIBLE);
		mProgress.setVisibility(View.GONE);
	}

	public void showProgress() {
		mDialog.setCancelable(false);
		mSave.setVisibility(View.GONE);
		mProgress.setVisibility(View.VISIBLE);
	}

	public void dismissDialog() {
		mDialog.dismiss();
		mUnBinder.unbind();
		isShowing = false;
		mUnBinder = null;
	}

	@OnClick(R.id.save)
	public void onViewClicked() {
		if (validateEditText(mNameText) && validateEditText(mGroupText) && validateEditText(mPostText) && validateEditText(mAboutText)) {
			showProgress();
			String name = mNameText.getText().toString();
			sendData.sendData(new Teammate(name, mPostText.getText().toString(),mAboutText.getText().toString(),mGroupText.getText().toString(),name.hashCode()));
		}
	}

	private boolean validateEditText(MaterialEditText editText) {
		if (TextUtils.isEmpty(editText.getText().toString())) {
			editText.setError("Нужно заполнить поле");
			return false;
		} else {
			return true;
		}
	}

	public boolean isShowing() {
		return isShowing;
	}

	@OnClick(R.id.close)
	public void onCloseClicked() {
		dismissDialog();
	}

	public interface SendData {
		void sendData(Teammate teammate);
	}

}