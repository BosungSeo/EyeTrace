package com.example.traceeye.androidDraw;

import android.graphics.Point;

public class StageView1Data {
    private Point[][] mObjects = new Point[6][10];
    void generator() {
        for (int i = 0; i < 10; i++) {
            for(int j=0;j<6;j++) {
                mObjects[j][i] = new Point();
            }
        }
        mObjects[0][0].x = 15;
        mObjects[0][0].y = 10;
        mObjects[0][1].x = 45;
        mObjects[0][1].y = 11;
        mObjects[0][2].x = 20;
        mObjects[0][2].y = 35;
        mObjects[0][3].x = 30;
        mObjects[0][3].y = 80;
        mObjects[0][4].x = 50;
        mObjects[0][4].y = 50;
        mObjects[0][5].x = 47;
        mObjects[0][5].y = 80;
        mObjects[0][6].x = 80;
        mObjects[0][6].y = 80;
        mObjects[0][7].x = 80;
        mObjects[0][7].y = 10;
        mObjects[0][8].x = 65;
        mObjects[0][8].y = 35;
        mObjects[0][9].x = 65;
        mObjects[0][9].y = 20;

        mObjects[1][0].x = 50;
        mObjects[1][0].y = 10;
        mObjects[1][1].x = 30;
        mObjects[1][1].y = 40;
        mObjects[1][2].x = 40;
        mObjects[1][2].y = 60;
        mObjects[1][3].x = 60;
        mObjects[1][3].y = 40;
        mObjects[1][4].x = 80;
        mObjects[1][4].y = 60;
        mObjects[1][5].x = 65;
        mObjects[1][5].y = 80;
        mObjects[1][6].x = 40;
        mObjects[1][6].y = 80;
        mObjects[1][7].x = 25;
        mObjects[1][7].y = 65;
        mObjects[1][8].x = 15;
        mObjects[1][8].y = 50;
        mObjects[1][9].x = 15;
        mObjects[1][9].y = 10;

        mObjects[2][0].x = 50;
        mObjects[2][0].y = 50;
        mObjects[2][1].x = 30;
        mObjects[2][1].y = 10;
        mObjects[2][2].x = 65;
        mObjects[2][2].y = 10;
        mObjects[2][3].x = 55;
        mObjects[2][3].y = 40;
        mObjects[2][4].x = 80;
        mObjects[2][4].y = 50;
        mObjects[2][5].x = 80;
        mObjects[2][5].y = 90;
        mObjects[2][6].x = 60;
        mObjects[2][6].y = 70;
        mObjects[2][7].x = 40;
        mObjects[2][7].y = 80;
        mObjects[2][8].x = 30;
        mObjects[2][8].y = 50;
        mObjects[2][9].x = 20;
        mObjects[2][9].y = 10;

        mObjects[3][0].x = 10;
        mObjects[3][0].y = 10;
        mObjects[3][1].x = 40;
        mObjects[3][1].y = 30;
        mObjects[3][2].x = 10;
        mObjects[3][2].y = 30;
        mObjects[3][3].x = 10;
        mObjects[3][3].y = 60;
        mObjects[3][4].x = 40;
        mObjects[3][4].y = 60;
        mObjects[3][5].x = 10;
        mObjects[3][5].y = 90;
        mObjects[3][6].x = 40;
        mObjects[3][6].y = 90;
        mObjects[3][7].x = 70;
        mObjects[3][7].y = 60;
        mObjects[3][8].x = 70;
        mObjects[3][8].y = 30;
        mObjects[3][9].x = 90;
        mObjects[3][9].y = 10;

        mObjects[4][0].x = 30;
        mObjects[4][0].y = 10;
        mObjects[4][1].x = 15;
        mObjects[4][1].y = 30;
        mObjects[4][2].x = 20;
        mObjects[4][2].y = 60;
        mObjects[4][3].x = 40;
        mObjects[4][3].y = 80;
        mObjects[4][4].x = 70;
        mObjects[4][4].y = 70;
        mObjects[4][5].x = 30;
        mObjects[4][5].y = 60;
        mObjects[4][6].x = 45;
        mObjects[4][6].y = 25;
        mObjects[4][7].x = 65;
        mObjects[4][7].y = 45;
        mObjects[4][8].x = 65;
        mObjects[4][8].y = 10;
        mObjects[4][9].x = 80;
        mObjects[4][9].y = 40;

        mObjects[5][0].x = 90;
        mObjects[5][0].y = 20;
        mObjects[5][1].x = 50;
        mObjects[5][1].y = 10;
        mObjects[5][2].x = 20;
        mObjects[5][2].y = 45;
        mObjects[5][3].x = 45;
        mObjects[5][3].y = 50;
        mObjects[5][4].x = 50;
        mObjects[5][4].y = 70;
        mObjects[5][5].x = 25;
        mObjects[5][5].y = 65;
        mObjects[5][6].x = 25;
        mObjects[5][6].y = 85;
        mObjects[5][7].x = 40;
        mObjects[5][7].y = 80;
        mObjects[5][8].x = 75;
        mObjects[5][8].y = 75;
        mObjects[5][9].x = 75;
        mObjects[5][9].y = 45;
    }
    Point getPoint(int j, int i) {
        return mObjects[j][i];
    }
}
