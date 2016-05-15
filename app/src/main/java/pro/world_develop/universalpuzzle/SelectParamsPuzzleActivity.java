package pro.world_develop.universalpuzzle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import pro.world_develop.universalpuzzle.activities.GameActivity;

public class SelectParamsPuzzleActivity extends Activity {
    private static Bitmap image;

    TextView fragmentsOnHeight;
    TextView fragmentsOnWidth;
    SeekBar seekBarOnHeight;
    SeekBar seekBarOnWidth;

    public class CountFragmentsOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        private TextView textView;

        public CountFragmentsOnSeekBarChangeListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            textView.setText(String.valueOf(seekBar.getProgress() + 2));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_params_puzzle);
        if (image != null) ((ImageView) findViewById(R.id.selectImage)).setImageBitmap(image);
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

    public static void setImage(Bitmap image) {
        SelectParamsPuzzleActivity.image = image;
    }

    public void openGameActivity(View view) {
        GameActivity.setCountFragmentOnHeight(Integer.parseInt(fragmentsOnHeight.getText().toString()));
        GameActivity.setCountFragmentOnWidth(Integer.parseInt(fragmentsOnWidth.getText().toString()));
        GameActivity.setImage(image);
        Intent intent = new Intent(SelectParamsPuzzleActivity.this, GameActivity.class);
        startActivity(intent);
    }
}
