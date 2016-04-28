package pro.world_develop.universalpuzzle;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by User on 28.04.2016.
 */
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