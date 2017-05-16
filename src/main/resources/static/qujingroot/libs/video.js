define(['app'],function(app){
	var View = {
		winType: 0,
		ips:[['192.168.0.51','192.168.0.52','192.168.0.53','192.168.0.54','192.168.0.55','192.168.0.56','192.168.0.57','192.168.0.58','192.168.0.59','192.168.0.60','192.168.0.61','192.168.0.62','192.168.0.63','192.168.0.64','192.168.0.65','192.168.0.66','192.168.0.67','192.168.0.68','192.168.0.69']
		,['192.168.0.51']]
	};//192.168.0.102
	
	return View.loginPlay = function(IP,i){
		//var IP = "192.168.1.101";
		var psd = 'kr65717197';
		/*if(IP == "192.168.0.117" || IP == "192.168.0.115" )
			psd = 'nongkeyuan1234';
		
		if(IP == "3706478a.nat123.net")
			port = '21358'*/
		var port = '80';
		WebVideoCtrl.I_Login(IP, 1, port, 'admin', psd, {
			async: true,
			
			success: function (xmlDoc) {
				View.play(IP,i);
				
			},
			error: function () {
				console.debug(IP + " 登录失败！");
			}
		});
	}, View.init = function(i, divId){
		if (-1 == WebVideoCtrl.I_CheckPluginInstall()) {
			alert("您还未安装过插件，请在常用链接里下载WebComponents.exe进行安装！");
			return;
		}
		View.winType = i;
		WebVideoCtrl.I_InitPlugin('100%', '100%', {
	        iWndowType: i,
			cbSelWnd: function (xmlDoc) {
				g_iWndIndex = $(xmlDoc).find("SelectWnd").eq(0).text();
				var szInfo = "当前选择的窗口编号：" + g_iWndIndex;
			}
		});
		WebVideoCtrl.I_InsertOBJECTPlugin(divId);
	}, View.play = function(ip,i){
			WebVideoCtrl.I_StartRealPlay(ip, {
				iWndIndex: i,
				iStreamType: 2,
				iChannelID: 1,
				bZeroChannel: false
			});
	}, View.stopAll = function(){
		for(var i = 0 ; i < View.winType*View.winType; i ++){
			var winInfo = WebVideoCtrl.I_GetWindowStatus(i);
			if(winInfo != null){
				WebVideoCtrl.I_Stop();
			}
		}
	}, View
});
