define(['app','list', '_' ],function(app, list, _){
	var View = list;
		/*
		 * custom configure
		 * @module 1
		 */
	var	v = {
			title: '分站开通申请列表',
			E:[[1,'申请编号'],[1,'学校名称'],[1,'邮箱'],[1,'公众号名称'],[1,'公众号人数'],[1,'微信号'],[1,'申请日期'],[1,'申请状态'],[1,'操作']],
			searchUrl : '/api/root/opensub/list',
			searchConfigureId	 :  "searchConfigure",
			listTmpId: 'opensublist'
		};
	
	return View.init = function(){
		View = jQuery.extend(View,v);
		View.searchConfigure = View.getSearchConfigure() || app.parseForm('searchConfigure');
		View.loadSearchConfigure(View.searchConfigure);
		View.bindEvt();
	}, View.loadSearchConfigure = function(config){//init search form by search config
		config = JSON.parse(config);
		$('input[name=currentPage]').val(config.currentPage || 0);
		$('select[name=status]').val(config.status || 1);
	}, View.criteriaChange = function() {//callback that when the form value has been changed
		$('input[name=currentPage]').val(0);
		View.search(true);
	},View.bindEvt = function(){
		$('select[name=status]').change(View.criteriaChange);
	},View.searchCallback = function(){
		$('.js-reject').click(View.reject);
		$('.js-approve').click(View.approve);
	}, View.reject = function(e){
		var id = e.target.dataset.id;
		if(confirm("确定要拒绝该开通申请吗？操作不可撤销！")){
			$.ajax({
				url: '/api/root/rejectopen/' + id,
				success: function(e){
					if (e.status == 'ok') {
						app.toast("拒绝成功！", 2000);
						$('.choose').click();
					} else {
						app.showUnlogin()
					}
				}
			});
		}
	},View.approve = function(e){
		var id = e.target.dataset.id;
		var schoolName = $('#schoolName'+id).html();
		if(confirm("通过该申请将会为【"+schoolName+
		"】开通一个分站，并且添加一个账号密码均为申请邮箱的管理员账号。确定继续吗？")){
			$.ajax({
				url: '/api/root/approveopen/' + id,
				success: function(e) {
					if (e.status == 'ok') {
						app.toast("开通成功！", 2000, function(){
							window.location.reload();
						});
					} else {
						app.showUnlogin()
					}
				}
			});
		}
	}, View
});
