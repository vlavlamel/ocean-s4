package com.ocean.oceans4.teamate_details;

import android.content.Context;

import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ocean.oceans4.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChangeInfoDialog {

	@BindView(R.id.title)
	TextView mTitle;
	@BindView(R.id.close)
	ImageView mClose;
	@BindView(R.id.data_text)
	MaterialEditText mDataText;
	@BindView(R.id.save)
	Button mSave;
	@BindView(R.id.progress)
	ProgressBar mProgress;

	private MaterialDialog mDialog;
	private Context ctx;
	private Unbinder mUnBinder;
	private SendData sendData;
	private ChangeInfo type;

	public ChangeInfoDialog(String title, String data, Context ctx, SendData sendData, boolean multiLine, ChangeInfo type) {
		this.ctx = ctx;
		mDialog = new MaterialDialog.Builder(ctx)
			.customView(R.layout.dialog_change_data, false)
			.build();
		this.sendData = sendData;
		this.type = type;
		initViews(data, title, multiLine);
	}

	private void initViews(String s, String title, boolean multiLine) {
		mUnBinder = ButterKnife.bind(this, mDialog);
		if (multiLine){
			mDataText.setSingleLine(false);
			mDataText.setLines(4);
		}
		mDataText.setText(s);
		mDataText.setSelection(s.length());
		mTitle.setText(title);
	}

	public void showDialog() {
		mDialog.show();
		if (mUnBinder == null) mUnBinder = ButterKnife.bind(this, mDialog);
	}

	public void setErrorFromServer(String message) {
		mDataText.setError(message);
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
		mUnBinder = null;
	}

	@OnClick(R.id.save)
	public void onViewClicked() {
		if (validateEditText()) {
			showProgress();
			sendData.sendData(mDataText.getText().toString(),getType());
		}
	}

	private boolean validateEditText() {
		if (TextUtils.isEmpty(mDataText.getText().toString())) {
			mDataText.setError("Нужно заполнить поле");
			return false;
		} else {
			return true;
		}
	}

	public ChangeInfo getType() {
		return type;
	}

	@OnClick(R.id.close)
	public void onCloseClicked() {
		dismissDialog();
	}

	public interface SendData {
		void sendData(String data, ChangeInfo type);
	}

}