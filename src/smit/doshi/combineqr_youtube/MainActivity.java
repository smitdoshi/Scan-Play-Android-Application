package smit.doshi.combineqr_youtube;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Button scanBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		scanBtn = (Button)findViewById(R.id.scan_button);
		scanBtn.setOnClickListener(this);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId()==R.id.scan_button){
			//scan
			
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
			
			}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve scan result
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			//we have a result
			Log.i("Error", "Before Scan Contents");
			String scanContent = scanningResult.getContents();
			Log.i("Error", "After Scan Contents:"+scanContent);
			String urlid=scanContent.substring(scanContent.indexOf("=")+1);
			Log.i("Error", "After URL ID:"+urlid);
			Toast toast = Toast.makeText(getApplicationContext(),urlid,Toast.LENGTH_LONG);
			toast.show();
			Log.i("Error", "After Toast:"+toast);
/*
			Bundle sendBundle = new Bundle();
			sendBundle.putString("id", urlid);
			Intent i = new Intent(MainActivity.this,YoutubeActivity.class);
			i.putExtras(sendBundle);
			startActivity(i);
*/
			try {
		        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + urlid));
		        i.putExtra("force_fullscreen", true);
		        startActivityForResult(i, 1);
		    } catch (ActivityNotFoundException ex) {
		        Intent i = new Intent(Intent.ACTION_VIEW,
		                Uri.parse("http://www.youtube.com/watch?v=" + urlid));
		        startActivity(i);
		    }
			}
		else{
		    Toast toast = Toast.makeText(getApplicationContext(), 
		        "No scan data received!", Toast.LENGTH_SHORT);
		    toast.show();
		}
		
		}
}
