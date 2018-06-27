/*
 * 获取地理定位信息
*/
function getLocation(){
	if (navigator.geolocation){
		navigator.geolocation.getCurrentPosition(showPosition,showError);
	}else{
		return false;
	}
}
function showPosition(position){
	var latlon = position.coords.latitude+','+position.coords.longitude;
	//定义 省 市 区
	var province='',city='',district='';
	//baidu
	var url = "http://api.map.baidu.com/geocoder/v2/?ak=5w3jxhCepBL2GOMlCFZ8j6uV&callback=renderReverse&location="+latlon+"&output=json&pois=0";
	$.ajax({ 
		type: "GET", 
		dataType: "jsonp", 
		url: url,
		beforeSend: function(){
			
		},
		success: function (json) {

			if(json.status==0){
				$("#allmap").html(json.result.addressComponent.city);
				province = json.result.addressComponent.province;
				city = json.result.addressComponent.city;
				if(json.result.addressComponent.district){
					district = json.result.addressComponent.district;
				}
				/*
				 * 根据定位信息 ajax 发送后台 进行处理 province city district
				*/ 
				//alert(province+city+district) //为展示信息 请必须删除
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
			console.log(errorThrown)
		}
	});
	
}
function showError(error){
	switch(error.code) {
		case error.PERMISSION_DENIED:
			console.log("定位失败,用户拒绝请求地理定位");
			break;
		case error.POSITION_UNAVAILABLE:
			console.log("定位失败,位置信息是不可用");
			break;
		case error.TIMEOUT:
			console.log("定位失败,请求获取用户位置超时");
			break;
		case error.UNKNOWN_ERROR:
			console.log("定位失败,定位系统失效");
			break;
    }
}
$(function() {
	$('.loading').fadeOut();
	$('.banner.flexslider').flexslider({
		animation: "slide",
		slideDirection: "horizontal"
	});
	$('.hot-news').flexslider({
		animation: "slide",
		direction:"vertical",
		slideshowSpeed:"3000"
	});
	//调用地理信息
	getLocation();

	// 热门项目滚动

	$('.offers-item').each(function(){
		var len = $(this).find('a').length;
		$(this).wrap('<div class="offers-wrap"></div>')
		$(this).find('.offers-inner').css({'width':(len*120)+'px'})
		// $(this).attr()
	});

});	
