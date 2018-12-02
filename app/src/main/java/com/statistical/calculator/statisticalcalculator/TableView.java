package com.statistical.calculator.statisticalcalculator;

import android.graphics.Point;
import android.graphics.Typeface;
import android.text.InputType;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TableView extends TableLayout{
    Point screen = new Point();
    private int size = 0;
    public TableView(Context c, Display display){
        super(c);
        display.getSize(screen);
    }

    void init(){
        addHeader("X","Y");
    }

    void addHeader(String x,String y){
        TableRow row = new TableRow(this.getContext());
        TableLayout.LayoutParams param = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView data = new TextView(this.getContext());
        data.setText(x);
        TableRow.LayoutParams dataParam = new TableRow.LayoutParams(getWidth()/2,screen.y/20);
        data.setLayoutParams(dataParam);
        data.setBackground(getResources().getDrawable(R.drawable.element_design));
        data.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        data.setTextSize(20);
        data.setTypeface(null, Typeface.BOLD);
        data.setPadding(0,0,0,0);
        row.addView(data);

        data = new TextView(this.getContext());
        data.setText(y);
        data.setLayoutParams(dataParam);
        data.setBackground(getResources().getDrawable(R.drawable.element_design));
        data.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        data.setTextSize(20);
        data.setTypeface(null, Typeface.BOLD);
        data.setPadding(0,0,0,0);
        row.addView(data);
        row.setLayoutParams(param);
        this.addView(row);
    }

    void addRow(String x,String y){
        TableRow row = new TableRow(this.getContext());
        TableLayout.LayoutParams param = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        EditText data = new EditText(this.getContext());
        data.setText(x);

        TableRow.LayoutParams dataParam = new TableRow.LayoutParams(getWidth()/2,screen.y/20);
        data.setLayoutParams(dataParam);
        data.setBackground(getResources().getDrawable(R.drawable.element_design));
        data.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        data.setOnEditorActionListener(MainView.graph);
        data.setOnFocusChangeListener(MainView.graph);
        data.setTextSize(10);
        data.setSingleLine();
        data.setPadding(0,0,0,0);
        data.setRawInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
        row.addView(data);

        data = new EditText(this.getContext());
        data.setText(y);
        data.setLayoutParams(dataParam);
        data.setBackground(getResources().getDrawable(R.drawable.element_design));
        data.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        data.setOnEditorActionListener(MainView.graph);
        data.setOnFocusChangeListener(MainView.graph);
        data.setTextSize(10);
        data.setSingleLine();
        data.setPadding(0,0,0,0);
        data.setRawInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
        row.addView(data);

        row.setLayoutParams(param);
        this.addView(row);
        size++;
    }

    public void removeRow(){
        if(size != 0){
            removeViewAt(size--);
        }
    }

    public void setValue(String value, int rowIndex, int columnIndex){
        TableRow row = (TableRow)getChildAt(rowIndex + 1);
        ((EditText)row.getChildAt(columnIndex)).setText(value);
    }

    public Double getValue(int rowIndex, int columnIndex){
        TableRow row = (TableRow)getChildAt(rowIndex + 1);
        String value = ((EditText)row.getChildAt(columnIndex)).getText().toString();
        value = value.replace(",",".");
        if(value.matches("^[-+]?\\d+(\\.\\d+)?$")){
            return Double.parseDouble(value);
        }else{
            setValue("0",rowIndex,columnIndex);
            return 0.0;
        }
    }

    public int getDataSize(){
        return size;
    }

    public ArrayList<Double[]> getData() {
        ArrayList<Double[]>data = new ArrayList<Double[]>(size);
        for(int i = 0;i < size;i++){
            data.add(new Double[]{getValue(i,0),getValue(i,1)});
        }
        return data;
    }
}