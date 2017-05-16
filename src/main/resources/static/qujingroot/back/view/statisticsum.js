define(['_','app'],function(_,app){
	var View = {
		items:['user'],//需要 在图表里画的数据
		timeType:"week"
	};
	return View.initialize = function(){
		$.when(View.init()).then(View.showChart());
	}, 
	View.changeStartEnd = function(type){//点击 day/week/month 三个按钮时的回调函数
		View.timeType = type;
		View.showChart();
	}, 
	View.init = function(){//页面静态部分的初始化、事件绑定等（ajax之前执行）
		View.showCurrent();
		var n = new Date();
        n.setDate(n.getDate()-1);
        var szCurTime = app.dateFormat(n, "yyyy-MM-dd hh:mm:ss");
        $("#endTime").val(szCurTime);
		$("#mSearch").click(function(){
			View.showChart();
		});
		$(".tab-day-con").click(function(e){
			$(".tab-day-con").removeClass('ons');
			e.target.classList.add('ons');
			View.timeType = e.target.id;
			View.showChart();
		});
		$('input[name="data-item"]').change(function(){
			var s;
			s = app.getCheckedByName('data-item');
			var a = $("input[name='data-item']");
			for(var i = 0 ; i < a.length ; i ++){
				if(s.includes(a[i].value)){
					if(!View.items.includes(a[i].value)){
						View.items.push(a[i].value);
					}
				}else{
					if(View.items.includes(a[i].value)){
						var t = View.items.indexOf(a[i].value);
						for(var j = t; j < (View.items.length-1); j ++){
							View.items[j] = View.items[j+1];
						}
						View.items.pop();
					}
				}
			}
			View.showChart();
		});
	} , View.ifChecked = function(id){
		if(View.items.includes(id) || View.items.includes(id+"")){
			return "checked"
		}
	}, View.showChart = function(){
		var endDate = new Date($("#endTime").val());
		var startDate = new Date($("#endTime").val());
		var subId = urlInfo.subid;
		if (View.timeType == "week") {
			startDate.setDate(startDate.getDate() - 6);
		} else if (View.timeType == "month") {
			startDate.setDate(startDate.getDate() - 29);
		} else if (View.timeType == "season") {
			startDate.setDate(startDate.getDate() - 89);
		}
		startDate = app.dateFormat(startDate, "yyyy MM dd").split(' ').join('');
		endDate = app.dateFormat(endDate, "yyyy MM dd").split(' ').join('');
		var data = {
			startDate: 1*startDate,
			endDate: 1*endDate,
			dataId: View.items.join(','),
			subId: 1*subId
		};
		data = JSON.stringify(data);
		$.ajax({
			type:"post",
			contentType:"application/json",
			url:"/api/root/statistic/sum",
			data: data, 
			success:function(e){
				if(e.status != "ok"){
					app.showUnlogin();
					return;
				}
				var data, xAxisData, legendData = [];
				var result = e.result;
				data = View.parseData(result);
				for(var i = 0 ; i < result.length; i ++){
					legendData.push(View.typeText(result[i].name));
				}
				xAxisData = View.parseDate(result);
				if(legendData.length == 0){//刚刚进入页面没有选择数据，通过此配置可以显示无数据动画
					legendData = [''];
					data = [{
						name:"",
						type:"line",
						data:[]
					}];
				}
				var myChart = echarts.init(document.getElementById('echartContainer')); 
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
				        	 	 dataView : {show: true, readOnly: false},
				            restore : {show: true,title:"刷新"},
				            saveAsImage : {show: true}
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
			}
		});
	}, 
	View.bindEvt = function(){
		$('input[name="data-item"]').change(function(){
			var s;
			s = app.getCheckedByName('data-item');
			var a = $("input[name='data-item']");
			for(var i = 0 ; i < a.length ; i ++){
				if(s.includes(a[i].value)){
					if(!View.items.includes(a[i].value)){
						View.items.push(a[i].value);
					}
				}else{
					if(View.items.includes(a[i].value)){
						var t = View.items.indexOf(a[i].value);
						for(var j = t; j < (View.items.length-1); j ++){
							View.items[j] = View.items[j+1];
						}
						View.items.pop();
					}
				}
			}
			View.showChart();
		});
	}, View.parseData = function(result){
		var data = [];
		for (var i = 0; i < result.length; i ++) {
			var item = {};
			item.name = View.typeText(result[i].name);
			item.symbol = "none";
			item.type = "line";
			item.data = result[i].value.toString().split(',');
			data[i] = item;
		}
		return data;
	},  View.parseDate = function(result){
		for (var i = 0; i < result.length; i ++) {
			return result[i].date;
		}
	}, View.typeText = function(type){
		if(type == "user"){
			return "用户人数"
		} else if(type == "master"){
			return "行家人数"
		} else if(type == "apply"){
			return "行家认证次数"
		} else if(type == "order"){
			return "预约个数"
		} else if(type == "skill"){
			return "发布技能个数"
		} else if(type == "finishOrder"){
			return "已完成预约个数"
		} 
	}, View.showCurrent = function(){
		$.ajax({
			url:'/api/statistic/rootcount/'+urlInfo.subid,
			success:function(e){
				if(e.status == "ok"){
					var es = $('.pannel-value');
					for(var i = 0; i < es.length; i ++) {
						var view = es[i].id.split('-')[1];
						$(es[i]).html(e.result[view]);
					}
				}
			}
		})
	}, View
});
