"use strict";

/**
 * all the url should be format of
 * ../main.html?v=viewId[&sub=subSystem]
 * main.js only deal the parameter viewId
 * the param sub may be used in view js, such as viewIndex.js, viewData.js.... 
 */

var h = window.location.href,regV = /(\?|&)v=([^&]*)(&|$)/,arr = h.match(regV),viewId;
viewId = (arr ? arr[2].trim() : "viewIndex");

var userInfo = JSON.parse((localStorage.getItem("userinfo")||null));
userInfo = userInfo || {};
var userName = userInfo.userName;
userName = (userName ? "," + userName : "");
$("#mainUsername").text(userName);
var watchRight = userInfo.watchRight;

var urlInfo = {};

require.config({
    baseUrl: "./",
    paths: {
    	
        _: "libs/lodash",
        
        list: "libs/list",
        
        line: "libs/line",
        
        app: "js/app" ,
        
        video: "libs/video"
//		 echarts: 'http://echarts.baidu.com/build/dist'
    }
//  ,
//  packages: [
//      {
//          name: 'echarts',
//          location: 'libs/echarts',
//          main: 'echarts'
//  }, {
//          name: 'zrender',
//          location: 'libs/zrender', // zrender与echarts在同一级目录
//          main: 'zrender'
//      }
//  ]
});

(function(){
	
	urlInfo = parseUrl();
	$("#subId").val(urlInfo.sub);
	var subSelectHtml = '<option value="0">基地概览</option>';
	$.ajax({
		url: '/dataAction!getSubList.action',
		data: {
			baseId: urlInfo.base
		},
		success:function(e){
			var list = e.data.list;
			for(var i = 0 ;i < list.length; i ++){
				subSelectHtml += '<option value="'+list[i].id+'">'+list[i].name+'</option>';
			}
			subSelectHtml = subSelectHtml.replace('value="'+urlInfo.sub+'"','value="'+urlInfo.sub+'" selected');
			$('#subId').html(subSelectHtml);
			$("#subId").change(function(){
				var h = window.location.href;
				h = h.split('=')[0]+"="+urlInfo.v + "&sub=" + $("#subId").val()+"&base="+urlInfo.base;
				window.location.href = h;
			});
			
			var baseName = e.data.extra.name;
			var footer = e.data.extra.footer;
			var logo = e.data.extra.logo;
			$('#baseTitle').html(baseName);
			$('.main-footer').html(footer);
			if(logo){
				$('#logo-img').attr('src','./img/logo/base'+urlInfo.base+'.jpg');
			}else{
				$('#logo-img').attr('src','./img/baseIndexLogo.png');
			}
		}
	});
	//'<option value="1">沼气中试</option><option value="2">沼气工程</option><option value="3">温室育秧</option><option value="4">温室工程</option><option value="5">生态灌溉</option><option value="6">猪舍养殖</option>';
	
	$.ajax({
		url: '/adminAction!getCloudWelcome.action',
		success:function(e){
			var link = e.data.link;
			if(link.trim() == "")
				return;
			var links = link.split(',')
			for(var i = 0; i < links.length; i ++){
				var str = links[i];
				if(str.trim() == "")
					continue;
				var u = str.split('#');
				var item = $('<button>');
				item.attr('type','button');
				item.attr('data-href',u[1]);
				item.attr('class','js-link am-btn am-btn-secondary am-btn-sm am-btn-default');
				item.text(u[0]);
				$('#menuAnchor').append(item);
			}
			
			$('.js-link').click(function(e){
				var h = e.target.dataset.href;
				window.open(h);
			});
		}
	});
	
	window.onresize = function(){
		adjustContentDiv();	
	}
	adjustContentDiv();
	$("#"+viewId).addClass('am-active');
	$('.js-show').click(function(e){
		e = e.target;
		console.debug(e.tagName);
		if(e.classList.contains('am-active')){
			return;
		}
		
		//http://127.0.0.1:8020/pc/main.html?v=viewIndex&sub=0
		var h = window.location.href;
		h = h.split('=')[0]+"="+e.id + "&sub=" + urlInfo.sub+"&base="+urlInfo.base;
		
		window.location.href = h;
	});
	
	$('#viewLink').mouseover(function(){
		$('.drop-down').removeClass("none");
	});
	
	$('#viewLink').mouseout(function(){
		$('.drop-down').addClass("none");
	});
	$('.drop-down').mouseover(function(){
		$('.drop-down').removeClass("none");
	});
	$('.drop-down').mouseout(function(){
		$('.drop-down').addClass("none");
	});
	
	
	lConn4Alarm();
})();


userInfo.userId ? $.ajax({
	type: "get",
	url: getTemplate(viewId),
	async: true,
	success: function(html){
		$('#content').html(html);
		require([ getView(viewId)], function(View) {
			View.initialize();
		})
	}
}) : require(['app'],function(app){
	app.showUnlogin()
})

function getTemplate(viewId) {
	return './template/'+viewId+'.html'
}

function getView(viewId) {
	return './view/'+viewId+'.js'
}

function adjustContentDiv(){
	var h = document.documentElement.clientHeight;
	h -= 120;
	h = h + "px";
	$("#content").css({"height":h,"overflow":"auto"});
	
}

function parseUrl(){
	var url = window.location.href;
	url = url.split('?')[1];
	url = url.replace('#','');
	var arr = url.split("&");
	var d = {};
	for(var i = 0; i < arr.length; i ++){
		var name,value;
		name = arr[i].split('=')[0];
		value = arr[i].split('=')[1];
		d[name] = value;
	}
	return d;
}

function lConn4Alarm(){//长连接动态更新alarm信息
	$.ajax({
		url:'/alarmAction!getAlarmNum.action',
		data: {
			baseId: urlInfo.base	
		},
		success:function(e){
			if(e.status == "ok"){
				var n = e.alarmNum;
				if(n == 0){
					$('.alarm-point').html("").addClass('none');
				}else{
					$('.alarm-point').html(n).removeClass('none');
				}
			}
			setTimeout(function(){
				lConn4Alarm();
			},5000)
		}
	})
}
