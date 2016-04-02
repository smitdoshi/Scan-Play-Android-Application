package smit.doshi.combineqr_youtube;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
 
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerView;
 
public class YoutubeActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {
	private static final String DEVELOPER_KEY = "AIzaSyDejcULIW4yK5Rz-BLSrimnu0Vo5v2YPC4";
	private static final int RECOVERY_DIALOG_REQUEST = 1;
	private static String yturl;
    // YouTube player view
    private YouTubePlayerView youTubeView;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle receiveBundle = this.getIntent().getExtras();
        yturl = receiveBundle.getString("id");
        setContentView(R.layout.youtube_activity);
 
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
 
        // Initializing video player with developer key
        youTubeView.initialize(DEVELOPER_KEY, this);
 
    }
 
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
            YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
 
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
            YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
 
            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(yturl);
 
            // Hiding player controls
            player.setPlayerStyle(PlayerStyle.CHROMELESS);
        }
    }
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY, this);
        }
    }
 
    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }
 
}