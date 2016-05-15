package pro.world_develop.universalpuzzle.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import pro.world_develop.universalpuzzle.R;
import pro.world_develop.universalpuzzle.SelectImageActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openGameActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SelectImageActivity.class);
        startActivity(intent);
    }
}
