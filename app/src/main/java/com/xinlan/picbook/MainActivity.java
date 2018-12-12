package com.xinlan.picbook;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.xinlan.picbook.engine.PicBookEngine;

public class MainActivity extends AppCompatActivity {
    private PicBookEngine mEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GLSurfaceView surfaceView = (GLSurfaceView) findViewById(R.id.main);
        mEngine = new PicBookEngine(surfaceView);

        mEngine.startRender();
    }

    @Override
    protected void onPause() {
        if (mEngine != null) {
            mEngine.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mEngine != null) {
            mEngine.onResume();
        }
        super.onResume();
    }
}//end class
