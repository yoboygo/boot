$(function(){
	// 加载提示
	$('#addload').fadeOut();	
	var $elm = $('#lbarticle'),
		$sign = $('.tombsign'),
		$page = 1;

	loadComment();

	// 拉动加载
	$(window).scroll(function(){
		var scrollTop = $(this).scrollTop(),
			scrollHeight = $(document).height(),
			windowHeight = $(this).height();
	　　if(scrollTop + windowHeight == scrollHeight){
			loadComment();
	　　}

	});

	// 加载数据
	function loadComment(){

		if( !($elm.data('nomore')) && ($page < 4) && !($elm.data('isloading')) ) {
			
			// 设置加载状态，防止重复多次加载
			$elm.data('isloading',true)
			$.ajax({
				url:'../js/data.php',
				data:{

				},
				dataType: 'json',
				type:'get',
				beforeSend:function(){
					$sign.show();
				},
				success: function() {
					// 没有更多数据的判断条件
					var str = '';
					
					str +='<div class="art-item">'
						+'<div class="heading" data-href="" >'
							+'<div class="users"><img src="http://resource.idengyun.com/resource/images/2017/04/0a14e304-dbda-4e54-b5ce-fc7a1a6f52eb.jpg" alt=""><span>喵喵</span></div>'
							+'<div class="BGImg items1">'
								+'<div style="background-image:url(http://resource.idengyun.com/resource/images/2017/04/f8bc98d0-3aa6-477b-9dca-ccc519c3753d.jpg);"></div>'
							+'</div>'
							+'<p>不知道下一次超声刀是什么时候，皱纹都看不见了啊，真变得太厉害了，我要当天山童姥！！！</p>'
						+'</div>'
						+'<div class="commodity" good-id=""><span>泰妍雅集丰韵套装</span></div>'
						+'<div class="art-menu">'
							+'<span class="browsed">12345</span><span class="comments">12442</span><span class="praised">75214</span><span class="shared">12347</span>'
						+'</div>'
					+'</div>';
					// 两张图用 items2
					str +='<div data-href="./articleinfo.html" class="art-item">'
						+'<div class="heading" data-href="" >'
							+'<div class="users"><img src="http://resource.idengyun.com/resource/images/2017/04/0a14e304-dbda-4e54-b5ce-fc7a1a6f52eb.jpg" alt=""><span>喵喵</span></div>'
							+'<p>容摘要最多25个字，可以是精秒的点评、</p>'
							+'<div class="BGImg items2">'
								+'<div style="background-image:url(http://resource.idengyun.com/resource/images/2017/02/ac70bad7-20d5-4dc1-b975-ea3d934fa7b9.png);"></div>'
								+'<div style="background-image:url(http://resource.idengyun.com/resource/images/2017/02/ca3e220e-8d4b-48de-909f-09659211f7d6.png);"></div>'
							+'</div>'
							+'<p>不知道下一次超声刀是什么时候，皱纹都看不见了啊，真变得太厉害了，我要当天山童姥！！！</p>'
						+'</div>'
						+'<div class="commodity" good-id=""><span>泰妍雅集丰韵套装</span></div>'
						+'<div class="art-menu">'
							+'<span class="browsed">12345</span><span class="comments">12442</span><span class="praised">75214</span><span class="shared">12347</span>'
						+'</div>'
					+'</div>';
						// 三张图用 items3
					str +='<div data-href="./articleinfo.html" class="art-item">'
							+'<div class="heading" data-href="" >'
								+'<div class="users"><img src="http://resource.idengyun.com/resource/images/2017/04/0a14e304-dbda-4e54-b5ce-fc7a1a6f52eb.jpg" alt=""><span>喵喵</span></div>'
								+'<p>容摘要最多25个字，可以是精秒的点评、或简明的概括容摘要最多25个字，可以是精秒的点评、或简明的概括可以是精秒的点评</p>'
								+'<div class="BGImg items3">'
									+'<div style="background-image:url(http://resource.idengyun.com/resource/images/2017/01/65bb58e4-0dd2-4caa-a484-9a08ee4f6888.jpg);"></div>'
									+'<div style="background-image:url(http://resource.idengyun.com/resource/images/2017/01/d4f62da0-dd16-4eb1-a0e1-ca526a09b691.jpg);"></div>'
									+'<div style="background-image:url(http://resource.idengyun.com/resource/images/2017/01/b006a995-8058-4466-a60f-fa3b28bfa375.jpg);"></div>'
								+'</div>'
							+'</div>'
							+'<div class="commodity" good-id=""><span>泰妍雅集丰韵套装</span></div>'
							+'<div class="art-menu">'
								+'<span class="browsed">12345</span><span class="comments">12442</span><span class="praised">75214</span><span class="shared">12347</span>'
							+'</div>'
						+'</div>';
					$sign.fadeOut();
					$sign.before(str);//添加节点 
					$page++;//分页++
					$elm.data('isloading',false); //去掉加载限制
					// 判断到底啦条件
					console.log($page)
					if(($page >=4)) {
						$elm.append('<div class="no-more"></div>');
					};

				},
				error:function(e){
					$load.fadeOut();
					msgAlert('网络请求超时');
				}
			});
		}
		
	}

	// 提示方法
	var $load = $('#addload'),
		$tip = $load.find('.spiner');
	function msgAlert(text){
		$tip.addClass('warning').append('<p class="text">'+text+'</p>');
		$load.fadeIn();
		setTimeout(function(){
			$load.fadeOut(500,function(){
				$tip.empty().removeClass('warning');
			})
		},1000);
	};

});