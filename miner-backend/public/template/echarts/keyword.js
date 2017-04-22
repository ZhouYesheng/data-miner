function keyword(){
    // 基于准备好的dom，初始化echarts实例
    option = {
        title: {
            text: '评论关注点分析',
            link: 'https://localhost:9000/s?wd=' + encodeURIComponent('ECharts'),
            x: 'center',
            textStyle: {
                fontSize: 23
            }

        },
        backgroundColor: '#F7F7F7',
        tooltip: {
            show: true
        },
        toolbox: {
            feature: {
                saveAsImage: {
                    iconStyle: {
                        normal: {
                            color: '#FFFFFF'
                        }
                    }
                }
            }
        },
        series: [{
            name: '评论关注点分析',
            type: 'wordCloud',
            //size: ['9%', '99%'],
            sizeRange: [6, 66],
            //textRotation: [0, 45, 90, -45],
            rotationRange: [-45, 90],
            //shape: 'circle',
            textPadding: 0,
            autoSize: {
                enable: true,
                minSize: 6
            },
            textStyle: {
                normal: {
                    color: function() {
                        return 'rgb(' + [
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160)
                            ].join(',') + ')';
                    }
                },
                emphasis: {
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            data: [{
                name: "Jayfee",
                value: 666
            }, {
                name: "Nancy",
                value: 520
            }]
        }]
    };
    var myChart = echarts.init(document.getElementById('keyword'));
//        $.get("http://localhost:9000/keyword/ed53e949acecdbc9b3ecd5564ff136bc",function(data,status){
//            option.series[0].data = data;
//            myChart.setOption(option);
//        });
    $.getJSON("echarts/data.json",function(data){
        option.series[0].data = data;
        myChart.setOption(option);
    });
}

function allload(){
    keyword();
}