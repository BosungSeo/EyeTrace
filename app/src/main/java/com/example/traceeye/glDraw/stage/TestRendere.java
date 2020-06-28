package com.example.traceeye.glDraw.stage;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.traceeye.glDraw.object.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TestRendere implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    public static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //color buffer를 클리어할 때 사용할 색을 지정합니다.
        //red, green, blue, alpha 순으로 0~1사이의 값을 지정합니다.
        //여기에서는 검은색으로 지정하고 있습니다.
        mTriangle = new Triangle();
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    //GLSurfaceView가 다시 그려질때 마다 호출되는 메소드입니다.
    public void onDrawFrame(GL10 unused) {
        //glClearColor에서 설정한 값으로 color buffer를 클리어합니다.
        //glClear메소드를 사용하여 클리어할 수 있는 버퍼는 다음 3가지 입니다.
        //Color buffer (GL_COLOR_BUFFER_BIT)
        //depth buffer (GL_DEPTH_BUFFER_BIT)
        //stencil buffer (GL_STENCIL_BUFFER_BIT)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw();
    }

    //GLSurfaceView의 크기 변경 또는 디바이스 화면의 방향 전환 등으로 인해
    //GLSurfaceView의 geometry가 바뀔때 호출되는 메소드입니다.
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        //viewport를 설정합니다.
        //specifies the affine transformation of x and y from
        //normalized device coordinates to window coordinates
        //viewport rectangle의 왼쪽 아래를 (0,0)으로 지정하고
        //viewport의 width와 height를 지정합니다.
        GLES20.glViewport(0, 0, width, height);
    }
}