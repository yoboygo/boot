$(function(){
	$('.loading').hide();
	var $sign = $('.tombsign');

	gallery();

	// 评论内容拉动加载 
	$(window).scroll(function(){
		var scrollTop = $(this).scrollTop(),
			scrollHeight = $(document).height(),
			windowHeight = $(this).height();
	　　if(scrollTop + windowHeight == scrollHeight){
			loadComment();
	　　}
	});

	function loadComment(){
		let $review = $('#review');
		if ( !($review.data('isloading')) && ($review.find('.no-more').length == 0) ){
			
			// 设置加载状态，防止重复多次加载
			$review.data('isloading',true);
			
			$.ajax({
				url:'../js/data.php',
				data:{
				},
				dataType: "json",
				type:"get",
				beforeSend:function(){
					$sign.show();
				},
				success: function() {
					var str='';
					
					for (var i = 3; i >= 0; i--) {
						str +='<div class="com-item">'
							+'<dl>'
								+'<dt><img src="../images/p2.jpg" alt=""></dt>'
								+'<dd class="uname">'
									+'<i>用户132</i>'
									+'<span class="levels"><i>Lv</i>5</span>'
									+'<span class="score"><span class="score-inner" style="width:70%"></span></span>'
								+'</dd>'
								+'<dd class="times">2016-05-28 </dd>'
							+'</dl>'
							+'<div class="comment">'
								+'<p>舒服……做完肚子小小了舒服……做完肚子小小了舒服……做完肚子小小了舒服……做完肚子小小了</p>'
								+'<div class="gallery">'
									+'<span class="slide"><img src="../images/p2.jpg" alt=""></span>'
									+'<span class="slide"><img src="../images/1.jpg" alt=""></span>'
								+'</div>'
							+'</div>'
						+'</div>';
					};
					
					$sign.fadeOut();
					$review.append(str);
					$review.data('isloading',false)
					// 查看图片
					gallery()
					// 判断到底啦条件
					//$review.append('<div class="no-more"></div>');
				},
				error:function(err){
					$('addload').fadeOut();
					alert('网络请求超时');
				}
			});
		};
		
	}
	// 查看图片
	function gallery(){
		$('.gallery').photoSwipe('.slide', {bgOpacity: 1, shareEl: false,loop:false}, {
	        close: function () {
	        	setTimeout(function(){
	        		$('.pswp').attr("class","pswp ");
	        	},500);
	        	
	        }
	    });
	}

})