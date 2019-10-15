function buildPieChart(containerName, title, seriesName, pieData) {
    Highcharts.chart(containerName, {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: title
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            name: seriesName,
            colorByPoint: true,
            data: pieData
        }]

    });
}

function buildBarChart(containerName, title, yAxisTitle, seriesName, barCategories, barData) {
    Highcharts.chart(containerName, {
      chart: {
          type: 'column'
      },
      title: {
          text: title
      },
      xAxis: {
          categories: barCategories,
          crosshair: true
      },
      yAxis: {
          min: 0,
          title: {
              text: yAxisTitle
          }
      },
      tooltip: {
          headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
          pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
              '<td style="padding:0"><b>{point.y} songs</b></td></tr>',
          footerFormat: '</table>',
          shared: true,
          useHTML: true
      },
      plotOptions: {
          column: {
              pointPadding: 0.2,
              borderWidth: 0
          }
      },
      series: [{
        name: seriesName,
        data: barData
      }]
    });
}
