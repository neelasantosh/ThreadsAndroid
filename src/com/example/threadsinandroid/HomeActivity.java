package com.example.threadsinandroid;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HomeActivity extends Activity {
	EditText editNum;
	Button buttonPrint;
	TextView textView1;
	MyHandler handler = new MyHandler();
	ProgressBar progress;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		editNum = (EditText) findViewById(R.id.editText1);
		buttonPrint = (Button) findViewById(R.id.button1);
		textView1 = (TextView) findViewById(R.id.textView1);
		progress = (ProgressBar)findViewById(R.id.progressBar1);
		
		
		buttonPrint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String str = editNum.getText().toString();
				int n = Integer.parseInt(str);
				progress.setMax(n);
				CountThread thread = new CountThread(n);
				thread.start();
			}
		});

	}// end of on create

	class CountThread extends Thread {
		int n;

		public CountThread(int n) {

			this.n = n;
		}

		public void run() {
			for (int i = 1; i <= n; i++) {
				// textView1.setText(i + "");
				Log.e("count", i + "");
				//send i to main thread
				Bundle bn = new Bundle();
				bn.putInt("count", i);
				Message msg = new Message();
				msg.setData(bn);
				handler.sendMessage(msg);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}// eof thread

	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle bn = msg.getData();
			int c = bn.getInt("count");
			textView1.setText("count:" +c);
			progress.setProgress(c);
			
		}
	}

}// eof activity

