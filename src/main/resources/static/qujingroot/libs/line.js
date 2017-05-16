/*!
 * list.js v1.01
 * depends on jquer.js and jquery.pagination.js plugin
 * @author yjdini
 */
define(['app','_'],function(app,_){
	var View = {};
	
	return View.initXAxis = function(startDate,type){
		var ret = [];
		
		if(type == "hour"){
			for(var i = 0 ; i < 24*7 ; i++){
				ret.push(app.dateFormat(startDate, "MM-dd hh")+":00");
				startDate.setHours(startDate.getHours() + 1);
			}
		}else if(type == "min"){
			for(var i = 0 ; i < 60*2 ; i++){
				ret.push(app.dateFormat(startDate, "MM-dd hh:mm"));
				startDate.setMinutes(startDate.getMinutes() + 1);
			}
		}else if(type == "sec"){
			for(var i = 0 ; i < 60*2 ; i++){
				ret.push(app.dateFormat(startDate, "MM-dd hh:mm"));
				startDate.setSeconds(startDate.getSeconds() + 10);
			}
		}
		return ret;
	}, View.showLine = function(data,divId,startTime,type){
		var xAxisData, legendData = [];
		var list = data;
		for(var i = 0 ; i < list.length; i ++){
			legendData.push(list[i].name);
		}
		xAxisData = View.initXAxis(startTime,type);
		
		
		var myChart = echarts.init(document.getElementById(divId)); 
        var option = {
		    tooltip: {
		        trigger: 'axis',           // 触发类型，默认数据触发，见下图，可选为：'item' ¦ 'axis'
		        showDelay: 20,             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
		        hideDelay: 100,            // 隐藏延迟，单位ms
		        transitionDuration : 0.4,  // 动画变换时间，单位s
		        backgroundColor: 'rgba(0,0,0,0.7)',     // 提示背景颜色，默认为透明度为0.7的黑色
		        borderColor: '#333',       // 提示边框颜色
		        borderRadius: 4,           // 提示边框圆角，单位px，默认为4
		        borderWidth: 0,            // 提示边框线宽，单位px，默认为0（无边框）
		        padding: 5,                // 提示内边距，单位px，默认各方向内边距为5，
		                                   // 接受数组分别设定上右下左边距，同css
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'line',         // 默认为直线，可选为：'line' | 'shadow'
		            lineStyle : {          // 直线指示器样式设置
		                color: '#48b',
		                width: 2,
		                type: 'solid'
		            },
		            shadowStyle : {                       // 阴影指示器样式设置
		                width: 'auto',                   // 阴影大小
		                color: 'rgba(150,150,150,0.3)'  // 阴影颜色
		            }
		        },
		        textStyle: {
		            color: '#fff'
		        }
	    		},
	      
		    toolbox: {
		        show : true,
		        feature : {
//		        	 	 dataView : {show: true, readOnly: false},
//		            restore : {show: true},
//		            saveAsImage : {show: true}
		        }
		    },
		    calculable : false,
		    legend: {
		        data: legendData
		    },
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : false,
		            data : xAxisData
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            axisLabel : {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series : data
		};
                    
		myChart.setTheme('macarons');
        // 为echarts对象加载数据 
        myChart.setOption(option); 
        
	} ,View
});