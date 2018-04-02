package pr_2101.josselin.extruder.Utils;

import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
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
    private static boolean hasLabel = false;

    public static void configChart(Chart _chart, int _config, int[] _data,
                                   String[] _absValues) {

        switch (_config) {
            case CONFIG_COIL:
                SimpleAxisValueFormatter metersFormatter = new SimpleAxisValueFormatter(0);
                metersFormatter.setAppendedText("m".toCharArray());
                configColumnChart(((ColumnChartView) _chart), _data, _absValues,
                        metersFormatter);

                break;
            case CONFIG_GAS:
                SimpleAxisValueFormatter ppmFormatter = new SimpleAxisValueFormatter(0);
                ppmFormatter.setAppendedText("ppm".toCharArray());
                configLineChart(((LineChartView) _chart), _data, _absValues, ppmFormatter);
        }
    }

    private static void configColumnChart(final ColumnChartView _chart, final int[] _data,
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
        _chart.setZoomLevel(_data.length-1, 0, (((float) _data.length)*2.5f)/18f);

        Axis axisY = new Axis();
        axisY.setHasLines(true).setInside(true).setFormatter(_formatter);
        axisY.setInside(true);

        data.setAxisXBottom(new Axis(daysAxis));
        data.setAxisYLeft(axisY);

        /*_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasLabel = !hasLabel;
                configColumnChart(_chart, _numColumns, _data, _absValues, _formatter);
            }
        });*/
    }

    private static void configLineChart(final LineChartView _chart,
                                        final int[] _data, final String[] _absValues,
                                        final SimpleAxisValueFormatter _formatter) {
        _chart.setInteractive(true);
        _chart.setZoomType(ZoomType.HORIZONTAL);
        _chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);

        final List<AxisValue> daysAxis = new ArrayList<>();

        final List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < _data.length; i++) {
            values.add(new PointValue(i, _data[i]));
            daysAxis.add(new AxisValue(i).setLabel(_absValues[i]));
        }

        initChart(daysAxis, values, _chart,_formatter, null);

        //In most cased you can call data model methods in builder-pattern-like manner.

        /*_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasLabel = !hasLabel;
                Line line = new Line(values).setColor(Color.RED).setFilled(true).setHasLabels(hasLabel);
                initChart(daysAxis, values, _chart, _formatter, line);
            }
        });*/
    }

    private static void initChart(List<AxisValue> daysAxis, List<PointValue> values, LineChartView chart,
                                 SimpleAxisValueFormatter _formatter, Line line){

        if (line == null ) line = new Line(values).setColor(Color.RED).setFilled(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axisX = new Axis(daysAxis);
        data.setAxisXBottom(axisX);

        Axis axisY = new Axis().setFormatter(_formatter);
        axisY.setHasLines(true);
        data.setAxisYLeft(axisY);
        chart.setLineChartData(data);
        chart.setZoomLevel(chart.getCurrentViewport().centerX()*2-1,
                chart.getCurrentViewport().centerY(), (((float) values.size())*2.5f)/18f);
    }
}
