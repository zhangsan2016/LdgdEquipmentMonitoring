package com.ldgd.equipment.monitoring.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.ldgd.equipment.monitoring.R;
import com.ldgd.equipment.monitoring.util.TimeUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SingleLampStateActivity extends Activity {

    private LineChart mChart;
    private LineChart chart2;
    private TextView tvTime;
    private Typeface  tf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_single_lamp_state);



        initView();

        initChart1();
        initChart2();

        // 设置字体 (Android LED数字/电子表字体digital font)
      //  Typeface mTfLight = Typeface.createFromAsset(getAssets(), "digital-7.ttf");
        Typeface mTfLight = Typeface.createFromAsset(getAssets(), "led.ttf");
        tvTime.setTypeface(mTfLight);


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

        XLabels xl =chart2.getXLabels();
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
         chart2 =  (LineChart)this.findViewById(R.id.chart_dissipation_statistics);


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

            float val = (float)  (Math.random() * (range - 0.25)+0.25) ;

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
        set1.setColor(Color.rgb(33,181,241));


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
            m.add(i+1 +"");
        }

        return m;
    }
}
