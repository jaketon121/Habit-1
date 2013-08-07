package com.jasonfu19860310.tim;

import com.jasonfu19860310.tim.view.CreateProjectActivity;
import com.jasonfu19860310.tim.view.ExecuteProjectActivity;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {
	public static final int ADD = 1;
	private PorjectListAdapter listAdapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listAdapter = new PorjectListAdapter(this); 
        this.setListAdapter(listAdapter);
        this.getListView().setOnItemClickListener(new ItemClickListener(this));
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	public void onOptionAddProject(MenuItem i) {
		Intent intent = new Intent(this, CreateProjectActivity.class);
		this.startActivityForResult(intent, ADD);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ADD:
			listAdapter.reloadData();
			break;
		}
	}
}

class ItemClickListener implements OnItemClickListener {
	public ItemClickListener(Context context) {
		this.context = context;
	}
	private Context context;
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(context, ExecuteProjectActivity.class);
		context.startActivity(intent);
	}
	
}

