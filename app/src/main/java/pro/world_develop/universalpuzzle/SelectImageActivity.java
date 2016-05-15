package pro.world_develop.universalpuzzle;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SelectImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        LinearLayout imageList = (LinearLayout) findViewById(R.id.imageList);

        addImageSet(imageList);
    }

    private void addImageSet(LinearLayout list) {
        final AssetManager mgr = getAssets();
        String[] filenameList = getFilenameList(mgr, "puzzle_images");
        if (filenameList == null) return;

        for (String filename : filenameList) {
            try {
                InputStream is = mgr.open("puzzle_images/" + filename);
                Drawable d = Drawable.createFromStream(is, null);
                final ImageView imageView = new ImageView(list.getContext());
                imageView.setImageDrawable(d);
                imageView.setPadding(0, 20, 0, 20);
                list.addView(imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String[] getFilenameList(AssetManager mgr, String path) {
        String[] list = null;
        try {
            list = mgr.list(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
