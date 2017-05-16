define([], function() {
    var n = {
    		baseUrl:''
        //baseUrl: "http://154881sl06.iask.in:47358"
        //baseUrl:"localhost:8080"
    };
       
    return  n.toast = function(content,time,callback){
    		var back,win;
    		if(!document.getElementById('toastwin')){
    			back = document.createElement('div');
	    		back.setAttribute('id',"toastback");
	    		back.setAttribute("style","background: #000;z-index: 10000;opacity: 0.7; position:fixed;top:0px;left:0px;right:0px;bottom:0px;");
	    		document.body.appendChild(back);
	    		win =  document.createElement('div');
	    		win.setAttribute('id',"toastwin");
	    		win.setAttribute("style",'background:#fff;z-index: 10000;padding:32px 24px 45px 24px;text-align: center;border-radius:5px;border: 1px solid #dadada;position:fixed;left:50%;top:50%;transform: translate(-50%,-50%);');
	    		win.innerHTML = content;
	    		document.body.appendChild(win);
	    		
    		}else{
    			win =  document.getElementById('toastwin');
    			back = document.getElementById('toastback');
    			win.innerHTML = content;
    		}
    		if(time){
    			setTimeout(function(){
    				$(back).remove();
    				$(win).remove();
    				callback();
    			},time)
    		}
    		
    },n.parseForm = function(formId){
    		var str = $('#'+formId).serialize();//"closeReason=123&password=123"
    		var arr = str.split("&");
		var d = {};
		for(var i = 0; i < arr.length; i ++){
			var name,value;
			name = arr[i].split('=')[0];
			value = arr[i].split('=')[1];
			d[name] = value;
		}
		return JSON.stringify(d);
    }, n.clearToast = function(){
    		$('#toastback,#toastwin').remove();
    },  n.show = function(m){
    		console.debug(m)
    }, n.showUnlogin = function(){
    		$("#content").html("<div class='toast-content'>登录超时，请返回<a href=../login.html>首页</a>重新登录</div>");
    }, n.showDeny = function(i){
    		if(i == "base"){
    			$("#content").html("<div class='toast-content'>您当前的账号权限不足，需要基地管理员权限</div>");
    			return;
    		}
    		$("#content").html("<div class='toast-content'>您当前的账号权限不足，需要云系统root权限</div>");
    }, n.setObjItem = function(key,obj){
    		localStorage.setItem(key,JSON.stringify((obj||{})));
    }, n.getObjItem = function(key){
    		return JSON.parse((localStorage.getItem(key)||"{}"))
    }, n.logout = function(){
		localStorage.clear();    		
    }, n.parseUrl = function(){
		var url = window.location.href;
		url = url.split('?')[1];
		if (url == undefined) 
			return null;
		url = url.replace('#','');
		var arr = url.split("&");
		if(arr.length == 0)
			return null;
		var d = {};
		for(var i = 0; i < arr.length; i ++){
			var name,value;
			name = arr[i].split('=')[0];
			value = arr[i].split('=')[1];
			d[name] = value;
		}
		return d;
	}, n.getUrlInfo = function() {
		var urlInfo	= n.parseUrl();
		if (urlInfo == null) {
			urlInfo = n.getObjItem("urlinfo");
			if (!urlInfo.hasOwnProperty("view")) {
				urlInfo["view"] = "overview";
				n.setObjItem("urlinfo", urlInfo);
			}
		}
		return urlInfo;
	},n.setUrlInfo =function(urlinfo){
		n.setObjItem("urlinfo", urlinfo)
	}, n.showNoData = function(){
		var myChart = echarts.init(document.getElementById('content')); 
        var option = {
		    tooltip : {
		        trigger: 'item'
		    },
	      
		    calculable : true,
		    legend: {
		        data: []
		    },
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : false,
		            data : []
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
		    series : {}
		};
	                    
		myChart.setTheme('macarons');
        // 为echarts对象加载数据 
        myChart.setOption(option); 
	}, n.dateFormat = function(e, t) {
        void 0 === t && (t = e, e = new Date);
        var n = {
            M: e.getMonth() + 1,
            d: e.getDate(),
            h: e.getHours(),
            m: e.getMinutes(),
            s: e.getSeconds(),
            q: Math.floor((e.getMonth() + 3) / 3),
            S: e.getMilliseconds()
        };
        return t = t.replace(/([yMdhmsqS])+/g, function(t, o) {
            var r = n[o];
            return void 0 !== r ? (t.length > 1 && (r = "0" + r, r = r.substr(r.length - 2)), r) : "y" === o ? (e.getFullYear() + "").substr(4 - t.length) : t
        })
    },n.getTimeDiff = function(t1,t2){
    		var date1 = new Date(t1.replace('  ',' '));
    		var date2 = new Date(t2.replace('  ',' ')); 
    		var t3 = date2.getTime() - date1.getTime();
    		
    		var h = Math.floor(t3/(3600*1000));
    		t3 = t3%(3600*1000);
    		var m = Math.floor(t3/(60*1000));
    		t3 = t3%(60*1000);
    		var s = Math.floor(t3/(1000));
    		return h+"小时 "+m+"分钟 "+s+"秒" ;
    		
    }, n.getCheckedByName = function(name){
    		var a = $("input[name='"+name+"']:checked");
    		var r = [];
    		for(var i =0 ; i < a.length; i ++){
    			r.push(a[i].value);
    		}
    		return r;
    },n.getRunStatus = function(s){
    		if(s == 1 || s == "1"){
    			return "<span class=on>正在运行</span>"
    		}else{
    			return "<span class=off>停止</span>"
    		}
    }, n.getResult = function(result) {
    		if (result == 0) {
    			return "<span style='color: rgba(43, 103, 226, 0.8);'>待处理</span>"
    		} else if (result == 1) {
    			return "<span class=on>已通过</span>"
    		} else if (result == 2) {
    			return "<span class=off>已回绝</span>"
    		}
    }, n

});

