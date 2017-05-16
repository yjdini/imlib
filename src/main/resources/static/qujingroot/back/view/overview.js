define(['app', '_' ],function(app,  _){
	var View = {},base,sub;
	return View.initialize = function(subId){
		$.ajax({
			url: '/api/root/sys/info',
			success: function(e){
				if(e.status != "ok"){
					app.showUnlogin();
					return;
				}
				var tmp = _.template(document.getElementById('sysinfo').innerHTML);
				e.result.dateformat = app.dateFormat;
				$('#js-view').html(tmp(e.result));
			}
		});
	}, View
});