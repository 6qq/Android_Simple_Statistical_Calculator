package com.statistical.calculator.statisticalcalculator;


import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoView extends GridLayout {
    TextView[]info;
    boolean isSample = true;
    public InfoView(Context c,Point screen){
        super(c);
        setRowCount(2);
        setColumnCount(3);
        info = new TextView[6];
        for(int i = 0;i < 6;i++){
            info[i] = new TextView(getContext());
            info[i].setGravity(Gravity.CENTER);
            info[i].setBackground(getResources().getDrawable(R.drawable.element_design));
            info[i].setSingleLine(true);
            addView(info[i]);
        }
    }

    public void init(){
        for(int i = 0;i < 6;i++){
            info[i].setWidth(getWidth()/3);
            info[i].setHeight(getHeight()/2);
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        init();
    }
    public InfoView(Context c, AttributeSet attr){
        super(c,attr);
    }

    public double[] calculate(ArrayList<Double[]>data){
        double sumX = 0, sumY = 0, eX, eY, varX = 0, varY = 0, covXY = 0, covXX = 0, corXY, a, b, n;
        int size = data.size();
        n = (isSample)? size - 1 : size;

        for(Double[]value : data){
            sumX += value[0];
            sumY += value[1];
        }

        eX = (sumX / data.size());
        eY = (sumY / data.size());

        if(Double.isNaN(eX) || Double.isInfinite(eX)){
            info[0].setText("E(X) = ");
        }else{
            info[0].setText("E(X) = " + String.format("%.2f", eX));
        }
        if(Double.isNaN(eY) || Double.isInfinite(eY)){
            info[3].setText("E(Y) = ");
        }else{
            info[3].setText("E(Y) = " + String.format("%.2f", eY));
        }

        for(Double[]value : data){
            varX += (value[0] - eX)*(value[0] - eX);
            varY += (value[1] - eY)*(value[1] - eY);
        }

        varX /= n;
        varY /= n;

        if(Double.isNaN(varX) || Double.isInfinite(varX)){
            info[1].setText("Var(X) = ");
        }else{
            info[1].setText("Var(X) = " + String.format("%.2f", varX));
        }
        if(Double.isNaN(varY) || Double.isInfinite(varY)){
            info[4].setText("Var(Y) = ");
        }else{
            info[4].setText("Var(Y) = " + String.format("%.2f", varY));
        }

        for(Double[]value : data){
            covXY += (value[0] - eX)*(value[1] - eY);
            covXX += (value[0] - eX)*(value[0] - eX);
        }

        covXY /= n;
        covXX /= n;
        corXY = covXY / Math.sqrt(varX*varY);

        if(Double.isNaN(covXY) || Double.isInfinite(covXY)){
            info[2].setText("Cov(X,Y) = ");
        }else{
            info[2].setText("Cov(X,Y) = " + String.format("%.2f", covXY));
        }
        if(Double.isNaN(corXY) || Double.isInfinite(corXY)){
            info[5].setText("Cor(X,Y) = ");
        }else{
            info[5].setText("Cor(X,Y) = " + String.format("%.4f", corXY));
        }

        b = covXY / covXX;
        a = eY - b*eX;
        return new double[]{a,b};
    }

    public void clear(){
        info[0].setText("E(X) = ");
        info[3].setText("E(Y) = ");
        info[1].setText("Var(X) = ");
        info[4].setText("Var(Y) = ");
        info[2].setText("Cov(X,Y) = ");
        info[5].setText("Cor(X,Y) = ");
    }
}