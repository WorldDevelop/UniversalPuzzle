package pro.world_develop.universalpuzzle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;


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
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                Drawable d = Drawable.createFromStream(is, null);
                final ImageView imageView = new ImageView(list.getContext());
                imageView.setImageBitmap(bitmap);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageView image = (ImageView) view;
                        SelectParamsPuzzleActivity.setImage(((BitmapDrawable) image.getDrawable()).getBitmap());
                        Intent intent = new Intent(SelectImageActivity.this, SelectParamsPuzzleActivity.class);
                        startActivity(intent);
                    }
                });
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
