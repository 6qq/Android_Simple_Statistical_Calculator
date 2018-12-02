package com.statistical.calculator.statisticalcalculator;


import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class GraphView extends View implements EditText.OnEditorActionListener,View.OnTouchListener, View.OnFocusChangeListener{
    Point origin = new Point(0,0);
    double sight = 1;
    int width = 10,height = 10;
    MainView root;
    Paint paint = new Paint();
    boolean isTableReady = false;
    public GraphView(Context c){
        super(c);
        setOnTouchListener(this);
    }
    public GraphView(Context c, AttributeSet attr){
        super(c,attr);
        setOnTouchListener(this);
    }

    public void initTableDraw(){
        isTableReady = true;
        invalidate();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        drawLines(canvas);
        if(isTableReady)drawDatas(canvas);
    }

    public void drawLines(Canvas canvas){
        paint.setStrokeWidth(3);
        double x1,y1,x2,y2;
        x1 = -100000;
        y1 = 0;
        x2 = 100000;
        y2 = 0;
        x1 *= width/10;
        x2 *= width/10;
        y1 *= width/10;
        y2 *= width/10;
        canvas.drawLine((int)(sight*(x1 - origin.x)) + width/2,height/2 - (int)(sight*(y1 - origin.y)), (int)(sight*(x2 - origin.x)) + width/2, height/2 - (int)(sight*(y2 - origin.y)),paint);
        x1 = 0;
        y1 = -100000;
        x2 = 0;
        y2 = 100000;
        x1 *= width/10;
        x2 *= width/10;
        y1 *= width/10;
        y2 *= width/10;
        canvas.drawLine((int)(sight*(x1 - origin.x)) + width/2,height/2 - (int)(sight*(y1 - origin.y)), (int)(sight*(x2 - origin.x)) + width/2, height/2 - (int)(sight*(y2 - origin.y)),paint);
        paint.setStrokeWidth(1);
        //horizontal
        for(int j = width/2 - (int)(height - sight*origin.y)%(width/10);j < height;j+= width/10){
            canvas.drawLine(0,j,width,j,paint);
        }
        //horizontal
        for(int j = width/2 - (int)(height - sight*origin.y)%(width/10);j > 0;j-= width/10){
            canvas.drawLine(0,j,width,j,paint);
        }
        //vertical
        for(int j = width/2 - (int)(sight*origin.x)%(width/10);j < width;j+= width/10){
            canvas.drawLine(j,0,j,height,paint);
        }
        //vertical
        for(int j = width/2 - (int)(sight*origin.x)%(width/10);j > 0;j-= width/10){
            canvas.drawLine(j,0,j,height,paint);
        }

    }

    public void drawDatas(Canvas canvas){
        TableView table = MainView.table;
        double x, y;

        if(table.getDataSize() > 0){
            ArrayList<Double[]> data = table.getData();

            for(Double[] value : data){
                x = value[0] * width / 10.0;
                y = value[1] * width / 10.0;
                canvas.drawCircle((int)((x - origin.x)*sight) + width/2 ,height/2 - (int)((y - origin.y)*sight),4,paint);
            }

            double[]line = MainView.info.calculate(data);

            paint.setStrokeWidth(2);
            double x1,y1,x2,y2;
            x1 = -100000;
            y1 = line[0] + x1*line[1];
            x2 = 100000;
            y2 = line[0] + x2*line[1];
            x1 *= width/10;
            x2 *= width/10;
            y1 *= width/10;
            y2 *= width/10;
            canvas.drawLine((int)(sight*(x1 - origin.x) + width/2),(int)(height/2 - sight*(y1 - origin.y)), (int)(sight*(x2 - origin.x) + width/2), (int)(height/2 - sight*(y2 - origin.y)),paint);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        invalidate();
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent e){
        if(e.getAction() == MotionEvent.ACTION_DOWN){
            Point clicked = new Point((int)e.getX(),(int)e.getY());
            origin.x += (clicked.x - getWidth()/2)/sight;
            origin.y += (getHeight()/2 - clicked.y)/sight;
            invalidate();
        }
        return false;
    }


    @Override
    public void onFocusChange(View view, boolean b) {
        EditText edit = (EditText)view;
        if(edit.getText().toString().equals("")){
            edit.setText("0");
        }
    }
}