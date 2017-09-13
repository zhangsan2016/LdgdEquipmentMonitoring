package com.ldgd.equipment.monitoring.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.LargeValueFormatter;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.ldgd.equipment.monitoring.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SingleLampInformationActivity extends Activity {




    private BarChart wifiDataChart;
    private LineChart trafficMovesDataChart;

    private TextView tv_connect_count, tv_flow;
    private Typeface tf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_single_lamp_information);

        initView();

        initChart1();
        initChart2();


    }

    private void initChart2() {

        // if enabled, the chart will always start at zero on the y-axis
        trafficMovesDataChart.setStartAtZero(true);

        // 设置透明度
        trafficMovesDataChart.setAlpha(0.6f);
        // 双击缩放
        trafficMovesDataChart.setDoubleTapToZoomEnabled(false);

        // disable the drawing of values into the chart  画线显示值
        trafficMovesDataChart.setDrawYValues(false);
        trafficMovesDataChart.setValueTextColor(Color.WHITE);
        // 显示边界
        trafficMovesDataChart.setDrawBorder(false);
        // 显示 图例
        trafficMovesDataChart.setDrawLegend(false);
        //  trafficMovesDataChart.setStartAtZero(false);

        // no description text
        trafficMovesDataChart.setDescription("");
        //   trafficMovesDataChart.setUnit(" $");

        // 设置点击value的时候，是否高亮显示
        trafficMovesDataChart.setHighlightEnabled(true);

        // 设置是否可以触摸
        trafficMovesDataChart.setTouchEnabled(true);

        // enable scaling and dragging 缩放与拖动
        trafficMovesDataChart.setDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        trafficMovesDataChart.setPinchZoom(true);

        trafficMovesDataChart.setDrawGridBackground(false);
        trafficMovesDataChart.setDrawVerticalGrid(false);

        // 网格
        trafficMovesDataChart.setDrawHorizontalGrid(true);
        trafficMovesDataChart.setDrawVerticalGrid(true);

        trafficMovesDataChart.setValueTypeface(tf);


        XLabels xl = trafficMovesDataChart.getXLabels();
        xl.setPosition(XLabels.XLabelPosition.BOTTOM);
        xl.setCenterXLabelText(true);
        xl.setTextColor(Color.WHITE);
        xl.setTypeface(tf);


        YLabels y = trafficMovesDataChart.getYLabels();
        y.setTextColor(Color.WHITE);
        y.setTypeface(tf);
        y.setLabelCount(5);


        // add data
        setData1(30, 5000f);

        // 动画
        trafficMovesDataChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
        trafficMovesDataChart.invalidate();

    }

    private void initView() {
        // 设置字体 (Android LED数字/电子表字体digital font)
        //  Typeface mTfLight = Typeface.createFromAsset(getAssets(), "digital-7.ttf");
        Typeface mTfLight = Typeface.createFromAsset(getAssets(), "led.ttf");
        // 设置自定义字体
        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        wifiDataChart = (BarChart) this.findViewById(R.id.chart_wifi_data);
        trafficMovesDataChart = (LineChart) this.findViewById(R.id.chart_traffic_moves_data);

        tv_connect_count = (TextView) this.findViewById(R.id.tv_connect_count);
        tv_flow = (TextView) this.findViewById(R.id.tv_flow);
        tv_connect_count.setTypeface(mTfLight);
        tv_flow.setTypeface(mTfLight);

    }

    private void initChart1() {

        //  wifiDataChart.setOnChartValueSelectedListener(this);
        wifiDataChart.setDescription("");
        wifiDataChart.setAlpha(0.8f);
        //  wifiDataChart.setUnit("%");

        // disable the drawing of values
        wifiDataChart.setDrawYValues(false);

        // scaling can now only be done on x- and y-axis separately
        wifiDataChart.setPinchZoom(false);
        wifiDataChart.setValueFormatter(new LargeValueFormatter());

        wifiDataChart.setDrawBarShadow(false);

        wifiDataChart.setDrawGridBackground(false);  // 是否显示表格颜色
        wifiDataChart.setDrawHorizontalGrid(false);  // 设置表格水平有线

        // 网格
        wifiDataChart.setDrawHorizontalGrid(true);
        wifiDataChart.setDrawVerticalGrid(true);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // define an offset to change the original position of the marker
        // (optional)
        mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

        // set the marker to the chart
        wifiDataChart.setMarkerView(mv);

        wifiDataChart.setData(generateData(7, 70));

        //设置图例
        Legend legend = wifiDataChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);  // 图例形状
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        legend.setTypeface(tf);
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(10);
        //  legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);


        XLabels xl = wifiDataChart.getXLabels();
        xl.setCenterXLabelText(true);
        xl.setPosition(XLabels.XLabelPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setTextColor(Color.WHITE);

        YLabels yl = wifiDataChart.getYLabels();
        yl.setTypeface(tf);
        yl.setLabelCount(5);
        yl.setTextColor(Color.WHITE);
        //  yl.setFormatter(new LargeValueFormatter());


        wifiDataChart.setValueTypeface(tf);
        wifiDataChart.invalidate();

    }

    private BarData generateData(int x, int y) {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < x; i++) {
            xVals.add(i + 1 + "");
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();

        float mult = y;

        for (int i = 0; i < x; i++) {
            float val = (float) (Math.random() * mult) + 3;
            yVals1.add(new BarEntry(val, i));
        }

        for (int i = 0; i < x; i++) {
            float val = (float) (Math.random() * mult) + 3;
            yVals2.add(new BarEntry(val, i));
        }

        for (int i = 0; i < x; i++) {
            float val = (float) (Math.random() * mult) + 3;
            yVals3.add(new BarEntry(val, i));
        }

        // create 3 datasets with different types
        BarDataSet set1 = new BarDataSet(yVals1, "连接次数");
//        set1.setColors(ColorTemplate.createColors(getApplicationContext(), ColorTemplate.FRESH_COLORS));
        set1.setColor(Color.rgb(25, 145, 234));
        BarDataSet set2 = new BarDataSet(yVals2, "流量");
        set2.setColor(Color.rgb(131, 172, 108));
        BarDataSet set3 = new BarDataSet(yVals3, "Company C");
        set3.setColor(Color.rgb(242, 247, 158));
        set1.setHighLightAlpha(100);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        // dataSets.add(set3);

        BarData data = new BarData(xVals, dataSets);

        // add space between the dataset groups in percent of bar-width
        data.setGroupSpace(110f);

        return data;
    }


    private void setData1(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i + 1) + "");
        }

  /*      long currentTime = System.currentTimeMillis();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(TimeUtils.dateToString(currentTime, TimeUtils.dateFormat_day));
            currentTime -= (3 * 60 * 60 * 1000);
        }*/

        ArrayList<Entry> vals1 = new ArrayList<Entry>();

        // min+(int)(Math.random()*(max-min+1));
        for (int i = 0; i < count; i++) {
            //    float mult = (range + 1);
            //     float val = (float) (Math.random() * (mult)) ;// + (float)

            // float val = (float) (Math.random() * range);
            float val = (float) range + (int) (Math.random() * (3000 - range + 1));

            // ((mult *
            // 0.1) / 10);
            vals1.add(new Entry(val, i));
        }


        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(vals1, "DataSet 1");
        set1.setDrawCubic(true);
        set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(true);
        set1.setDrawCircles(false);
        set1.setLineWidth(2f);
        set1.setCircleSize(5f);
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        //  set1.setColor(Color.rgb(104, 241, 175));
        //set1.setColor(Color.rgb(208,240,240));
        //set1.setColor(Color.rgb(104, 241, 175));
        set1.setColor(Color.rgb(33, 181, 241));


        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1);

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        trafficMovesDataChart.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                //  DecimalFormat df = new DecimalFormat("#.0");  //生成一个df对象，确保放大的value也是小数点后一位
                DecimalFormat df = new DecimalFormat("###.##");
                return df.format(value);
            }
        });
        // set data
        trafficMovesDataChart.setData(data);
    }

}
