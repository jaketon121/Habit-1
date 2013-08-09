package com.jasonfu19860310.tim.view;

import com.jasonfu19860310.db.ProjectDBContract.ProjectEntry;
import com.jasonfu19860310.project.RecordManager;
import com.jasonfu19860310.tim.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ExecuteProjectActivity extends Activity {

	private static final String COLON = ":";
	private TextView currentTime;
	private long projectID;
	private RecordManager recordManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_execute_project);
		currentTime = (TextView) findViewById(R.id.execute_project_textView_time);
		projectID = getIntent().getLongExtra(ProjectEntry.COLUMN_NAME_ID, -1);
		recordManager = new RecordManager(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.execute_project, menu);
		return true;
	}
	
	public void onStartClicked(View view) {
	 }
	
	public void onClearClicked(View view) {
		currentTime.setText("00:00");
	 }
	
	public void onSaveClicked(View view) {
		String[] time = getHourAndMinute((TextView) currentTime);
		int hour = Integer.valueOf(time[0]);
		int minute = Integer.valueOf(time[1]);
		recordManager.addNewRecord(projectID, hour*60 + minute);
	}
	
	public void onInputManuallyClicked(View view) {
		AlertDialog.Builder builder = initialDialogBuilder();
		AlertDialog dialog = builder.create();
		dialog.show();
		addMinutesChangeListener(currentTime, dialog);
		addHoursChangeListener(currentTime, dialog);
		
	}

	private AlertDialog.Builder initialDialogBuilder() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.input_time).setView(getLayoutInflater().inflate(R.layout.input_time, null));
		final String oldTime = currentTime.getText().toString();
		addButtonClickListener(builder, currentTime, oldTime);
		return builder;
	}

	private void addButtonClickListener(AlertDialog.Builder builder,
			final TextView currentTime, final String oldTime) {
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	           }
	       });
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   currentTime.setText(oldTime);
	           }
	       });
	}

	private void addHoursChangeListener(final TextView currentTime,
			AlertDialog dialog) {
		final EditText hours = (EditText) dialog.findViewById(R.id.input_time_editText_hour);
		hours.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String[] times = getHourAndMinute(currentTime);
				currentTime.setText(hours.getText().toString().trim() + COLON + times[1]);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}});
	}

	private void addMinutesChangeListener(final TextView currentTime,
			AlertDialog dialog) {
		final EditText minutes = (EditText) dialog.findViewById(R.id.input_time_editText_minitue);
		minutes.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String[] times = getHourAndMinute(currentTime);
				currentTime.setText(times[0] + COLON + minutes.getText().toString().trim());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}});
	}
	
	private String[] getHourAndMinute(TextView currentTime) {
		String time = currentTime.getText().toString();
		String[] times = time.split(COLON);
		return times;
	}

}