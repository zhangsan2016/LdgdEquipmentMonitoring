package com.ldgd.equipment.monitoring.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.LargeValueFormatter;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.ldgd.equipment.monitoring.R;
import com.ldgd.equipment.monitoring.util.TimeUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SingleLampStateActivity extends Activity implements OnChartValueSelectedListener {

    private LineChart mChart;
    private LineChart chart2;
    private BarChart chart3;
    private TextView tvTime;
    private Typeface tf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_single_lamp_state);


        initView();

        initChart1();
        initChart2();
        initChart3();

        // 设置字体 (Android LED数字/电子表字体digital font)
        //  Typeface mTfLight = Typeface.createFromAsset(getAssets(), "digital-7.ttf");
        Typeface mTfLight = Typeface.createFromAsset(getAssets(), "led.ttf");
        tvTime.setTypeface(mTfLight);


    }

    private void initChart3() {
        chart3.setOnChartValueSelectedListener(this);
        chart3.setDescription("");
        chart3.setAlpha(0.8f);
        chart3.setUnit("%");

        // disable the drawing of values
        chart3.setDrawYValues(false);

        // scaling can now only be done on x- and y-axis separately
        chart3.setPinchZoom(false);
        chart3.setValueFormatter(new LargeValueFormatter());

        chart3.setDrawBarShadow(false);

        chart3.setDrawGridBackground(false);  // 是否显示表格颜色
        chart3.setDrawHorizontalGrid(false);  // 设置表格水平有线

        // 网格
        chart3.setDrawHorizontalGrid(true);
        chart3.setDrawVerticalGrid(true);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // define an offset to change the original position of the marker
        // (optional)
        mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

        // set the marker to the chart
        chart3.setMarkerView(mv);

        chart3.setData(generateData(4, 100));

        //设置图例
        Legend legend = chart3.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        legend.setTypeface(tf);
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(14);
        //  legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);


        XLabels xl = chart3.getXLabels();
        xl.setCenterXLabelText(true);
        xl.setPosition(XLabels.XLabelPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setTextColor(Color.WHITE);

        YLabels yl = chart3.getYLabels();
        yl.setTypeface(tf);
        yl.setLabelCount(3);
        yl.setTextColor(Color.WHITE);
        //  yl.setFormatter(new LargeValueFormatter());


        chart3.setValueTypeface(tf);
        chart3.invalidate();
    }

    private String[] streetLamp = {"LED智慧路灯", "LED路灯", "高压钠路灯", "荧光灯"};

    private BarData generateData(int x, int y) {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < x; i++) {
            xVals.add((streetLamp[i]));
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
        BarDataSet set1 = new BarDataSet(yVals1, "功耗");
//        set1.setColors(ColorTemplate.createColors(getApplicationContext(), ColorTemplate.FRESH_COLORS));
        set1.setColor(Color.rgb(25, 145, 234));
        BarDataSet set2 = new BarDataSet(yVals2, "节能");
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

    private void initChart2() {
        // apply styling
        //chart2.setValueTypeface(mTf);
        chart2.setDrawYValues(false);
        chart2.setDescription("");
        // 显示图例
        chart2.setDrawLegend(true);

        chart2.setDrawVerticalGrid(false);
        chart2.setDrawGridBackground(false);

        // 网格
        chart2.setDrawHorizontalGrid(true);
        chart2.setDrawVerticalGrid(true);

        // y轴单位
        chart2.setUnit("%");

        XLabels xl = chart2.getXLabels();
        xl.setCenterXLabelText(true);
        xl.setPosition(XLabels.XLabelPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setTextColor(Color.WHITE);


        YLabels yl = chart2.getYLabels();
        yl.setTypeface(tf);
        yl.setLabelCount(4);
        yl.setTextColor(Color.WHITE);

        // set data
        chart2.setData(generateDataLine(1));

        // 设置图例字体
        Legend legend = chart2.getLegend();//设置比例图
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(14);
        //  legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);

        // do not forget to refresh the chart
        //chart2.invalidate();
        chart2.animateX(1000);

    }

    private LineData generateDataLine(int cnt) {
        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e1.add(new Entry((int) (Math.random() * 65) + 40, i));
        }

        LineDataSet d1 = new LineDataSet(e1, "功耗");
        // d1.getCircleColor(Color.rgb(248,248,255));
        d1.setLineWidth(3f);
        d1.setCircleSize(5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));

        ArrayList<Entry> e2 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e2.add(new Entry(e1.get(i).getVal() - 30, i));
        }

        LineDataSet d2 = new LineDataSet(e2, "New DataSet " + cnt + ", (2)");
        d2.setLineWidth(3f);
        d2.setCircleSize(5f);

        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);

        //    LineData cd = new LineData(getMonths(), sets);
        LineData cd = new LineData(getMonths(), sets);
        return cd;
    }

    private void initView() {
        mChart = (LineChart) findViewById(R.id.chart1);
        // 单灯状态表
        tvTime = (TextView) findViewById(R.id.tv_single_lamp_time);
        // 功耗统计表
        chart2 = (LineChart) this.findViewById(R.id.chart_dissipation_statistics);
        // 节能减排分析
        chart3 = (BarChart) findViewById(R.id.chart_analyze);

        TextView tv_environmental1 = (TextView) this.findViewById(R.id.tv_environmental1);
       //两次加大字体，设置字体为红色（big会加大字号，font可以定义颜色）
        tv_environmental1.setText(Html.fromHtml("LED路灯比高压钠路灯节能 <font color='#0B99F7'><big><big> 75%</big></big></font>"));
        TextView tv_environmental2 = (TextView) this.findViewById(R.id.tv_environmental2);
        //两次加大字体，设置字体为红色（big会加大字号，font可以定义颜色）
        tv_environmental2.setText(Html.fromHtml("相当于1kwLED智慧路灯每年节能减排： <font color='#0B99F7'><big><big>3275</big></big></font>千克\"二氧化碳\""));


    }

    private void initChart1() {
        // 设置自定义字体
        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // if enabled, the chart will always start at zero on the y-axis
        mChart.setStartAtZero(true);


        // 设置透明度
        mChart.setAlpha(0.6f);
        // 双击缩放
        mChart.setDoubleTapToZoomEnabled(false);

        // disable the drawing of values into the chart
        mChart.setDrawYValues(true);
        mChart.setValueTextColor(Color.WHITE);
        // 显示边界
        mChart.setDrawBorder(false);
        // 显示 图例
        mChart.setDrawLegend(false);
        //  mChart.setStartAtZero(false);

        // no description text
        mChart.setDescription("");
        //   mChart.setUnit(" $");

        // 设置点击value的时候，是否高亮显示
        mChart.setHighlightEnabled(true);

        // 设置是否可以触摸
        mChart.setTouchEnabled(true);

        // enable scaling and dragging 缩放与拖动
        mChart.setDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        mChart.setDrawGridBackground(false);
        mChart.setDrawVerticalGrid(false);

        // 网格
        mChart.setDrawHorizontalGrid(true);
        mChart.setDrawVerticalGrid(true);

        mChart.setValueTypeface(tf);


        XLabels xl = mChart.getXLabels();
        xl.setPosition(XLabels.XLabelPosition.BOTTOM);
        xl.setCenterXLabelText(true);
        xl.setTextColor(Color.WHITE);
        xl.setTypeface(tf);


        YLabels y = mChart.getYLabels();
        y.setTextColor(Color.WHITE);
        y.setTypeface(tf);
        y.setLabelCount(5);


        // add data
        setData1(13, 0.55f);

        // 动画
        mChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
        mChart.invalidate();
    }


    private void setData1(int count, float range) {

   /*     ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((1990 +i) + "");
        }*/

        long currentTime = System.currentTimeMillis();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(TimeUtils.dateToString(currentTime, TimeUtils.dateFormat_day));
            currentTime -= (3 * 60 * 60 * 1000);
        }

        ArrayList<Entry> vals1 = new ArrayList<Entry>();

        // min+(int)(Math.random()*(max-min+1));
        for (int i = 0; i < count; i++) {
            //    float mult = (range + 1);
//            float val = (float) (Math.random() * (mult)) ;// + (float)

            float val = (float) (Math.random() * (range - 0.25) + 0.25);

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

        mChart.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                //  DecimalFormat df = new DecimalFormat("#.0");  //生成一个df对象，确保放大的value也是小数点后一位
                DecimalFormat df = new DecimalFormat("###.##");
                return df.format(value);
            }
        });
        // set data
        mChart.setData(data);
    }


    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        for (int i = 0; i <= 30; i++) {
            m.add(i + 1 + "");
        }

        return m;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex) {
        Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("Activity", "Nothing selected.");
    }
}
