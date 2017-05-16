define(['app', '_' ],function(app,  _){
	var View = {},base,sub;
	return View.initialize = function(subId){
		$.ajax({
			url: '/api/root/sub/info/' + subId,
			success: function(e){
				if(e.status != "ok"){
					app.showUnlogin();
					return;
				}
				var tmp = _.template(document.getElementById('subinfo').innerHTML);
				e.result.dateformat = app.dateFormat;
				e.result.getStatus = app.getRunStatus;
				$('#js-view').html(tmp(e.result));
				View.bindEvt();
			}
		});
	}, View.bindEvt = function() {
		$('#close-sub').click(View.close);
		$('#open-sub').click(View.open);
	}, View.close = function() {
		$('#js-detail').html(document.getElementById('subclose').innerHTML).removeClass("none");
		$('#subclose-cancel').click(View.cancelClose);
		$('#subclose-save').click(View.submitClose);
	}, View.cancelClose = function() {
		$('#js-detail').html("").addClass("none");
	}, View.open = function(){
		subId = $('.choose')[0].dataset.id;
		if (confirm("是否确定要重新启动该分站？")) {
			$.ajax({
				url: '/api/root/opensub/' + subId,
				success: function(e){
					if (e.status == "unlogin") {
						app.showUnlogin();
						return;
					} else if (e.status == "error") {
						app.toast("启动分站成功！",2000);
						$('.choose').click();//ajax 静态刷新
					}
				}
			});
		}
	}, View.submitClose = function() {
		subId = $('.choose')[0].dataset.id;
		$.ajax({
			url: '/api/root/closesub/' + subId,
			type: 'post',
			contentType: 'application/json',
			data: app.parseForm('subclose-form'),
			success: function(e){
				if (e.status == "unlogin") {
					app.showUnlogin();
					return;
				} else if (e.status == "error") {
					app.toast(e.message);
				}
				app.toast("关闭分站成功！",2000);
				$('.choose').click();//ajax 静态刷新
			}
		})
	},  View
});