define(['app','list', '_' ],function(app, list, _){
	var View = list;
		/*
		 * custom configure
		 * @module 1
		 */
	var	v = {
			title: '行家列表',
			E:[[1,'头像'],[1,'姓名'],[1,'专业'],[1,'年级'],[1,'头衔'],[1,'微信'],[1,'作品链接'],[1,'评分'],[1,'完成预约数'],[1,'状态'],[1,'认证日期']],
			searchUrl : '/api/root/master/list',
			searchConfigureId:"searchConfigure",
			listTmpId: 'masterlist',
			listFuc : {
				getMasterStatus : function(status){
					if(status==0){
						return "<span class=off>已拉黑</span>"
					}else if(status == 1){
						return "<span class=on>正常</span>"
					}else if(status==2){
						return "<span class=normal>已下架</span>"
					}
				}
			}
		};
	
	return View.init = function(){
		$('input[name=subId]').val(urlInfo.subid);
		View = jQuery.extend(View,v);
		View.searchConfigure = View.getSearchConfigure() || app.parseForm('searchConfigure');
		View.loadSearchConfigure(View.searchConfigure);
		View.bindEvt();
	}, View.loadSearchConfigure = function(config){//init search form by search config
		config = JSON.parse(config);
		$('input[name=currentPage]').val(config.currentPage);
	}, View.criteriaChange = function() {//callback that when the form value has been changed
		$('input[name=currentPage]').val(0);
		View.search(true);
	},View.bindEvt = function(){
		
	},View.searchCallback = function(){
	}, View
});
