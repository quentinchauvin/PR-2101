package pr_2101.josselin.extruder.Utils;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by josselin on 02/04/18.
 */

public class GraphUtils {
    public final static int CONFIG_COIL = 1;
    public final static int CONFIG_GAS = 2;
    private static boolean hasLabel;

    public static boolean hasLabel(){
        return hasLabel;
    }

    public static void configChart(Chart _chart, int _config, float[] _data,
                                   String[] _absValues, boolean _hasLabel) {

        hasLabel = _hasLabel;

        switch (_config) {
            case CONFIG_COIL:
                /*SimpleAxisValueFormatter metersFormatter = new SimpleAxisValueFormatter(0);
                metersFormatter.setAppendedText("m".toCharArray());
                configColumnChart(((ColumnChartView) _chart), _data, _absValues,
                        metersFormatter);*/

                break;
            case CONFIG_GAS:
                configLineChart(((LineChartView) _chart), _data, _absValues);
        }
    }

    /*private static void configColumnChart(final ColumnChartView _chart, final float[] _data,
                                          final String[] _absValues,
                                          final SimpleAxisValueFormatter _formatter) {
        _chart.setInteractive(true);
        _chart.setZoomType(ZoomType.HORIZONTAL);
        _chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);

        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        List<AxisValue> daysAxis = new ArrayList<>();

        for (int i = 0; i < _data.length; ++i) {

            values = new ArrayList<>();
            values.add(new SubcolumnValue(_data[i], ChartUtils.pickColor()));

            daysAxis.add(new AxisValue(i).setLabel(_absValues[i]));

            Column column = new Column(values);
            column.setHasLabels(GraphUtils.hasLabel);
            columns.add(column);
        }

        ColumnChartData data = new ColumnChartData(columns);

        _chart.setColumnChartData(data);
        _chart.setZoomLevel(_data.length - 1, 0, (((float) _data.length) * 2.5f) / 18f);

        Axis axisY = new Axis();
        axisY.setHasLines(true).setInside(true).setFormatter(_formatter);
        axisY.setInside(true);

        data.setAxisXBottom(new Axis(daysAxis));
        data.setAxisYLeft(axisY);


        _chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasLabel = !hasLabel;
                configColumnChart(_chart, _numColumns, _data, _absValues, _formatter);
            }
        });
    }*/

    private static void configLineChart(final LineChartView _chart,
                                        final float[] _data, final String[] _absValues) {
        _chart.setInteractive(true);
        _chart.setZoomType(ZoomType.HORIZONTAL);
        _chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);

        final List<AxisValue> daysAxis = new ArrayList<>();

        final List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < _data.length; i++) {
            values.add(new PointValue(i, _data[i]));
            daysAxis.add(new AxisValue(i).setLabel(_absValues[i]));
        }

        initChart(daysAxis, values, _chart, null, null);
        _chart.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                hasLabel = !hasLabel;
                Line line = new Line(values).setColor(Color.parseColor("#F6402C")).setFilled(true).setHasLabels(hasLabel);

                float[] viewPort = {_chart.getCurrentViewport().left, _chart.getCurrentViewport().top,
                        _chart.getCurrentViewport().right, _chart.getCurrentViewport().bottom};

                initChart(daysAxis, values, _chart, line, viewPort);
            }

            @Override
            public void onValueDeselected() {

            }
        });
    }

    private static void initChart(List<AxisValue> daysAxis, List<PointValue> values, LineChartView chart
            , Line line, float[] f) {

        if (line == null)
            line = new Line(values).setColor(Color.parseColor("#F6402C")).setFilled(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axisX = new Axis(daysAxis);
        data.setAxisXBottom(axisX);

        Axis axisY = new Axis();
        axisY.setHasLines(true);
        data.setAxisYLeft(axisY);
        chart.setLineChartData(data);

        axisX.setTextColor(Color.parseColor("#cccccc"));

        if (f == null)
            chart.setZoomLevel(chart.getCurrentViewport().centerX() * 2 - 1,
                    chart.getCurrentViewport().centerY(), (((float) values.size()) * 2.5f) / 18f);
        else chart.setCurrentViewport(new Viewport(f[0], f[1], f[2], f[3]));
    }

}
