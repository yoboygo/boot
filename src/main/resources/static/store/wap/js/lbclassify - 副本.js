$(function() {
	$('#addload').fadeOut();

	/*
	 * 分类项目
	*/
	//分类菜单点击
	$('.menu-list li').click(function(){
		var $this=$(this), 
			$mallId = $this.attr('data-id');  //分类id
		
		$this.addClass('active').siblings().removeClass('active');
		
		//ajax加载示例 loading效果及添加节点擦做
		/*$.ajax({
			url:'http://www.tngou.net/tnfs/api/list',
			data:{
			},
			dataType: "json",
			type:"get",
			beforeSend:function(){
				$('#addload').show(); //显示loading
			},
			success: function(data) {
				$('#mall-box').empty();
				var str='';

				$(data.tngou).each(function(i,e){
					str +='<div class="mall-item" style="background-image:url(http://tnfs.tngou.net/image'+e.img+'_150x150)">'
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

	});

	// 分类项目拉动加载 
	$('.mall-box').scroll(function(){
		var scrollTop = $(this).scrollTop(),
			scrollHeight = $('#mall-box').height(),
			windowHeight = $(this).height();

		if(scrollTop + windowHeight == scrollHeight){
			//ajax 操作
		}

	});

});	