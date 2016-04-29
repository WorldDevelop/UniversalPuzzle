package pro.world_develop.universalpuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView fragmentsOnHeight;
    TextView fragmentsOnWidth;
    SeekBar seekBarOnHeight;
    SeekBar seekBarOnWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentsOnWidth = (TextView) findViewById(R.id.fragmentsOnWidth);
        fragmentsOnHeight = (TextView) findViewById(R.id.fragmentsOnHeight);
        seekBarOnWidth = (SeekBar) findViewById(R.id.seekBarOnWidth);
        seekBarOnHeight = (SeekBar) findViewById(R.id.seekBarOnHeight);

        seekBarOnHeight.setOnSeekBarChangeListener(
                new CountFragmentsOnSeekBarChangeListener(fragmentsOnHeight)
        );
        seekBarOnWidth.setOnSeekBarChangeListener(
                new CountFragmentsOnSeekBarChangeListener(fragmentsOnWidth)
        );
    }

    public void openGameActivity(View view) {
        GameActivity.setCountFragmentOnHeight(Integer.parseInt(fragmentsOnHeight.getText().toString()));
        GameActivity.setCountFragmentOnWidth(Integer.parseInt(fragmentsOnWidth.getText().toString()));
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }
}
