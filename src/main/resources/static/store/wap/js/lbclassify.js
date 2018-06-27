$(function() {
	$('#addload').fadeOut();
	
	/*
	 * 分类项目 
	 * 默认加载第一个分类
	*/

	//ajaxLoad(1)

	//分类菜单点击
	$('.menu-list li').click(function(){
		var $this=$(this), 
			$mallId = $this.attr('data-id');  //分类id
		
		// 先判断是否展开的分类 防止重复加载
		if( !($this.hasClass('active')) ){
			ajaxLoad($mallId);
			$this.addClass('active').siblings().removeClass('active');
		}
	});

	// 加载
	function ajaxLoad(typeId){
		/*$.ajax({
			url:'http://www.tngou.net/tnfs/api/list',
			data:{
			},
			dataType: "jsonp",
			type:"get",
			beforeSend:function(){
				$('#addload').show(); //显示loading
			},
			success: function(data) {
				$('#mall-box').empty();
				var str='';

				$(data.tngou).each(function(i,e){
					str +='<div class="mall-item" style="background-image:url(../images/mall03_16.jpg)">'
							+'<p>'+e.title+'</p>'
						+'</div>'
				});
				$('#mall-box').append(str);
				$('#addload').hide(); //隐藏loading
			},
			error:function(err){
				$('#addload').hide();
			}
		});*/
	}

});	