/*!
 * list.js v1.01
 * depends on jquer.js and jquery.pagination.js plugin
 * @author yjdini
 */
define(['app','_'],function(app,_){
	var View = {
		/**
		 * custom configure
		 * @module 1
		 * 每个不同的list页面都要根据页面内容，在view js里进行自定义配置
		 * title，字符串，该列表的名称
		 * E，字符数组，该数据表的字段名数组
		 * searchUrl，每次查询数据时访问的链接
		 * searchConfigure，一些默认的查询选项
		 */
		title: '',
		E:[],
		searchUrl : '',
		searchConfigure : "",
		searchConfigureId:"",
		/**
		 * system configure
		 * 默认的一些配置，同一个系统中的所有list页面共用一份相同的配置，若修改则直接改list.js
		 * id 为该div对应的id名称
		 * opt 为调用pagination插件时的默认配置
		 * storageIndetifier 保存在浏览器localstorage中的数据的key值前缀
		 */
		criteriaFormId : 'search',
		paginateDivId: 'js-pagination',
		titleDivId: 'js-title',
		dataTableDivId: 'js-table',
		listTmpId: 'js-list2table',
		addNewBtnId: 'js-addnew',
		deleteItemClass: 'js-delete',
		showDetailClass: 'js-detail' ,
		opt : {
	    		items_per_page: 10,
	    		prev_text: "<",
	    		next_text: ">",
	    		num_edge_entries: 1,
	    		num_display_entries:9,
	    },
	    storageIdentifier : 'QUJING-'
	};
	
	/**
	 * system implement of methods
	 * 
	 */
	return View.search = function(init){
		View.table = document.getElementById(View.dataTableDivId);
		tabletpl = _.template(document.getElementById(View.listTmpId).innerHTML);
		$.ajax({
			url: View.searchUrl,
			type: 'post',
			contentType: 'application/json',
			data: app.parseForm(View.searchConfigureId),
			async: !0,
			type: 'post',
			success: function(e){
				if(e.status == "unlogin"){
					app.showUnlogin();
					return;
				}
				View.saveSearchConfigure();
				var config = JSON.stringify(View.getSearchConfigure());
				e.result.E=View.E,
				e.result.currentPage =config.currentPage ,
				e.result.list.getStatus = app.getRunStatus,
				e.result.list.getResult = app.getResult,
				e.result.list.dateFormat = app.dateFormat,
				e.result.list.getTimeDiff = app.getTimeDiff,
				e.result.list.f = View.listFuc;
				View.table.innerHTML = tabletpl(e.result);
				if(init)
					View.paginate(config.currentPage, e.result.recordsNum, View.pageCallback);
				View.searchCallback();
			}
		});
	}, 
	/*
	 * dateFormat(new Date(t[1].time)," yy-MM-dd hh:mm")
	 */
	View.dateFormat = function(e, t) {
        e = new Date(e);
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
    },
	View.paginate = function(currentPage,recordsNum,callBack) {
		View.opt.current_page = currentPage;
		View.opt.callback = callBack;
		$("#"+View.paginateDivId).pagination(recordsNum,View.opt);
    },
    View.getViewId = function(){
		return app.getUrlInfo().view;
	}, 
    View.pageCallback = function (page_index, jq) {
    		if(page_index != $('input[name=currentPage]').val()) {
    			$('input[name=currentPage]').val(page_index);
    			View.search();
    		}
	}, 
	View.getSearchConfigure = function(){
		return View.getHistory(View.getViewId())
	}, 
	View.saveSearchConfigure = function(){
		//viewId 和 searchConfigure 在localstorage里映射在一起
		View.setHistory(View.getViewId(), app.parseForm(View.searchConfigureId));
	}, 
	View.getHistory = function(viewId){
		return localStorage.getItem(View.storageIdentifier + viewId)
	},
	View.setHistory = function(viewId, item){
		localStorage.setItem(View.storageIdentifier+viewId,item)
	},
	View.init = function(){
		//这个要在 具体view js中重写，但是方法唯一，不能自定义
	},
	View.initialize = function () {
		if(app.getObjItem("userinfo") == {}){
			app.showUnlogin();
			return;
		}
		View.init();
		
		View.search(true);
		var title = document.getElementById(View.titleDivId);
		var addnew = document.getElementById(View.addNewBtnId);
		title && (title.innerHTML = View.title);
		addnew && addnew.addEventListener('click', View.addNew);
	}, 
	
	/**
	 * custom implement of methods
	 * @module 2
	 * showDetail 显示详情（.js-detail）被点击时的触发事件
	 * addNew 添加按钮（#js-addnew） 被点击时触发的事件
	 * loadFinishCallback 每次查询结束，数据装载完成后回调函数
	 */
	View.showDetail = function(viewId){
		
	},
	View.deleteItem = function(viewId){
		
	},
	View.addNew = function(){
		
	},
	View.searchCallback = function () {
		
	},View
});