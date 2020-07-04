package com.example.traceeye.glDraw.stage;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class FirstGLView extends GLSurfaceView {
    private final TestRendere mRenderer;

    public FirstGLView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        mRenderer = new TestRendere();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

}
