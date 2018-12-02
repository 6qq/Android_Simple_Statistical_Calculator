package com.statistical.calculator.statisticalcalculator;

import android.util.AttributeSet;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MainView extends RelativeLayout{
    static GraphView graph;
    static TableView table;
    static InfoView info;
    public MainView(Context c){
        super(c);
    }

    public MainView(Context c, AttributeSet attr){
        super(c,attr);
    }

    public void setGraph(GraphView view){
        graph = view;
        graph.root = this;
        addView(graph);
    }

    public void setTable(ScrollView view){
        table = (TableView)view.getChildAt(0);
        addView(view);
    }

    public void setInfo(InfoView view){
        info = view;
        addView(info);
    }
}