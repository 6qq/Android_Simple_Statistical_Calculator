package com.statistical.calculator.statisticalcalculator;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static MainView root;
    static TableView tableview;
    static GraphView graphview;
    static InfoView infoview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        root = findViewById(R.id.rootPanel);
        tableview = createTableView();
        graphview = createGraphView();
        infoview = createInfoView();
        infoview.clear();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        ScrollView scrollview = new ScrollView(this);
        scrollview.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, (int)(size.y*0.8)));
        scrollview.setId(View.generateViewId());
        scrollview.setBackgroundColor(Color.rgb(220,220,220));

        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)graphview.getLayoutParams();
        param.addRule(RelativeLayout.RIGHT_OF,scrollview.getId());

        param = (RelativeLayout.LayoutParams)infoview.getLayoutParams();
        param.addRule(RelativeLayout.BELOW,scrollview.getId());

        scrollview.addView(tableview);
        root.setTable(scrollview);

        tableview.post(new Runnable() {
            @Override
            public void run() {
                tableview.init();
                root.table = tableview;
                root.setInfo(infoview);
                root.setGraph(graphview);
                graphview.initTableDraw();
            }
        });
        graphview.post(new Runnable() {
            @Override
            public void run() {
                graphview.width = graphview.getWidth();
                graphview.height = graphview.getHeight();
                graphview.invalidate();
            }
        });
    }

    public TableView createTableView(){
        TableView view = new TableView(this, getWindowManager().getDefaultDisplay());
        view.setId(View.generateViewId());
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ScrollView.LayoutParams param = new ScrollView.LayoutParams((int)(size.x*0.2), LinearLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(param);
        return view;
    }

    public GraphView createGraphView(){
        GraphView view = new GraphView(this);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams((int)(size.x*0.8),(int)(size.y*0.8));
        view.setLayoutParams(param);
        view.setBackgroundColor(Color.rgb(240,240,240));
        return view;
    }

    public InfoView createInfoView(){
        Display d2 = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        d2.getSize(p);
        InfoView view = new InfoView(this,p);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        view.setLayoutParams(param);
        view.setBackgroundColor(Color.rgb(140,140,140));
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, R.id.item1, 0, "Add");
        menu.add(Menu.NONE, R.id.item2, 0, "Delete");
        menu.add(Menu.NONE, R.id.item3, 0, "Reset");
        menu.add(Menu.NONE, R.id.item4, 0, "Zoom in");
        menu.add(Menu.NONE, R.id.item5, 0, "Zoom out");
        SubMenu type = menu.addSubMenu(Menu.NONE, R.id.item6, 0, "Type");
        type.addSubMenu(Menu.NONE, R.id.item61, 0, "Sample");
        type.addSubMenu(Menu.NONE, R.id.item62, 0, "Population");
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1 :
                tableview.addRow("0","0");
                break;

            case R.id.item2 :
                tableview.removeRow();
                graphview.invalidate();
                break;
            case R.id.item3 :
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                double size = tableview.getDataSize();
                                for(int i = 0;i < size;i++){
                                    tableview.removeRow();
                                }
                                graphview.origin = new Point(0,0);
                                graphview.sight = 1;
                                graphview.invalidate();
                                infoview.clear();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to reset?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                break;
            case R.id.item4 :
                graphview.sight *= 10;
                graphview.invalidate();
                break;
            case R.id.item5 :
                graphview.sight *= 0.1;
                graphview.invalidate();
                break;
            case R.id.item61 :
                infoview.isSample = true;
                graphview.invalidate();
                Toast.makeText(this, "Type : Sample",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.item62 :
                infoview.isSample = false;
                graphview.invalidate();
                Toast.makeText(this, "Type : Population",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}