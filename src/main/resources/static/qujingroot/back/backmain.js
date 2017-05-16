"use strict";
require.config({
    baseUrl: "../",
    paths: {
        _: "libs/lodash",
        app: "js/app",
        pagination: "libs/pagination",
        list: "libs/list",
        detail: "libs/detail",
    }
});

var urlInfo;
function bindEvt(){
	$('#btn-logout').click(function(){
		require(['app'], function(app){
			app.logout();
			window.location.href = "../login.html";
		});
	})
	
	$('.js-top-menu').click(function(e){
		var el = e.target;
		while (el.tagName != "LI")
			el = el.parentElement;
		var view = el.id.split('-')[1];
		var subid = urlInfo.subid;
		loadView(view, subid);
	});
	
	$('.sub-entry').click(function(e){
		var el = e.target;
		if(el.tagName != "LI")
			el = el.parentElement;
		var subid = el.dataset.id;
		loadView("subinfo", subid);
	});
	
	$('.js-menu').click(function(e){
		var ea = e.target;
		while(ea.tagName != "LI")
			ea = ea.parentElement;
		if (ea.id == "sublist") {
			$(ea).toggleClass("active")
		} else {
			if ( !$(ea).hasClass("choose") ) {
				loadView(ea.id);
			}
		}
	});
	
}
function showView(view, subId) {
	var explict = '?t='+new Date().getTime();	
	$.ajax({
		type: "get",
		url: "./template/"+view+".html",
		async: true,
		success: function(d){
			$('#content').html(d);
			require(['app', './view/'+view+'.js'+explict], function(app, View) {
				View.initialize(subId);
				app.setUrlInfo({"view":view,"subid":subId});
				urlInfo = app.getUrlInfo();
			})
		}
	});
}
function loadView(view, subId) {
	$('.li-entry').removeClass("choose");
	$('.sub-entry').removeClass("choose");
	$('.js-top-nav').addClass("none");
	$('.js-top-menu').removeClass("am-active");
	$('#js-'+view).addClass("am-active");
	
	if (view == "overview") {
		$('#overview').addClass("choose");
		$('#sys-nav').removeClass("none");
	} else if (view == "opensub") {
		$('#opensub').addClass("choose");
		$('#open-nav').removeClass("none");
	} else if (view == "subinfo" || view == "master" || view == "user" || view == "statisticsum" || view == "statisticincre") {
		$('#sublist').addClass("active");
		$('#sub'+subId).addClass("choose");
		$('#sub-nav').removeClass("none");
	}
	showView(view, subId)
}

function initSidebar(){
	require(['_', 'app'], function(_, app){
		$.ajax({
			url: '/api/root/sub/list',
			type: 'get',
			success: function(e){
				if(e.status != 'ok'){
					app.showUnlogin();
					return;
				}
				var tplSidebar = _.template(document.getElementById('js-sidebar').innerHTML);
				$("#sidebar").html(tplSidebar(e));

				bindEvt();

				var sub = urlInfo.sub;
				$("#sub"+urlInfo.sub).addClass("active");
				$("#baseicon"+urlInfo.base).removeClass('am-icon-caret-right').addClass('am-icon-caret-down');
				$("#base"+urlInfo.base+" .sub"+urlInfo.sub).addClass("choose");
				initContent();
			}
		});
	})
}

function initContent() {
	loadView(urlInfo.view, urlInfo.subid);
}
$(function(){
	require(['app'], function(app){
		if(!localStorage.getItem('userinfo') || 
				!JSON.parse(localStorage.getItem('userinfo')).name){
			app.showUnlogin();
			return;
		}
		urlInfo = app.getUrlInfo();
		$("#adminName").html(JSON.parse(localStorage.getItem('userinfo')).name);
		initSidebar();
	})
});

