$(function() {
	var $navTop,scrollTop,
		$sign = $('.tombsign');
	$('.banner.flexslider').flexslider({
		animation: "slide",
		slideDirection: "horizontal"
	});
	$('.loading').fadeOut();

	$('.tab-item').each(function(){
		$(this).data('firstLoad',true);
	});

	// 简介展开 收起
	$('.dropmenu').click(function(){
		var $elm = $(this).siblings('.banner');
		if ($elm.hasClass('expand')){
			$elm.removeClass('expand').css('height','330px').siblings('.dropmenu').html('查看全部简介');
		}else{
			$elm.addClass('expand').css('height','auto').siblings('.dropmenu').html('收起简介');
		}
	});
	// end
	$(window).scroll(function(){
		// 控制导航条
		$navTop = $('.tab-box').offset().top;
		scrollTop = $(this).scrollTop();
		if(scrollTop >= $navTop ){
			$('.nav').addClass('fixed');
		}else{
			$('.nav').removeClass('fixed');
		}
		// 分别load 文章 技师列表 
		var scrollTop = $(this).scrollTop(),
			scrollHeight = $(document).height(),
			windowHeight = $(this).height();
	　　if(scrollTop + windowHeight == scrollHeight){
			if (!$("#article").is(":hidden")){
				//加载文章 
				loadArticle();
			}else if(!$("#beautician").is(":hidden")){
				loadBeautician();
			}
	　　}
	});


	// tab栏 切换
	$('.nav span').click(function(){
		var $index = $(this).index();
			$(this).addClass('active').siblings().removeClass('active');
		$('.tab-box .tab-item').eq($index).fadeIn().siblings().hide();
		$('html,body').animate({scrollTop: $navTop+'px'},500);
		// 默认加载第一条
		if ($index ==1 && $("#article").data('firstLoad')){// 展示文章
			loadArticle()
		}else if($index == 2  && $("#beautician").data('firstLoad')){// 展示技师。
			loadBeautician();
		}
	});

	// 加载文章数据
	function loadArticle(){
		let $article = $('#article'),
			$page = $article.attr('data-page');

		if( !($article.data('nomore')) && ($page < 4) && !($article.data('isloading')) ) {
			
			// 设置加载状态，防止重复多次加载
			$article.data('isloading',true)
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
					
					str +='<div data-href="" class="art-item">'
						+'<div class="users"><img src="http://resource.idengyun.com/resource/images/2017/04/0a14e304-dbda-4e54-b5ce-fc7a1a6f52eb.jpg" alt=""><span>喵喵</span></div>'
						+'<div class="BGImg items1">'
							+'<div style="background-image:url(http://resource.idengyun.com/resource/images/2017/04/f8bc98d0-3aa6-477b-9dca-ccc519c3753d.jpg);"></div>'
						+'</div>'
						+'<p>不知道下一次超声刀是什么时候，皱纹都看不见了啊，真变得太厉害了，我要当天山童姥！！！</p>'
						+'<div class="art-menu">'
							+'<span class="browsed">12345</span><span class="comments">12442</span><span class="praised">75214</span><span class="shared">12347</span>'
						+'</div>'
					+'</div>';
					// 两张图用 items2
					str +='<div data-href="./articleinfo.html" class="art-item">'
						+'<div class="users"><img src="http://resource.idengyun.com/resource/images/2017/04/0a14e304-dbda-4e54-b5ce-fc7a1a6f52eb.jpg" alt=""><span>喵喵</span></div>'
						+'<p>容摘要最多25个字，可以是精秒的点评、</p>'
						+'<div class="BGImg items2">'
							+'<div style="background-image:url(http://resource.idengyun.com/resource/images/2017/02/ac70bad7-20d5-4dc1-b975-ea3d934fa7b9.png);"></div>'
							+'<div style="background-image:url(http://resource.idengyun.com/resource/images/2017/02/ca3e220e-8d4b-48de-909f-09659211f7d6.png);"></div>'
						+'</div>'
						+'<p>不知道下一次超声刀是什么时候，皱纹都看不见了啊，真变得太厉害了，我要当天山童姥！！！</p>'
						+'<div class="art-menu">'
							+'<span class="browsed">12345</span><span class="comments">12442</span><span class="praised">75214</span><span class="shared">12347</span>'
						+'</div>'
					+'</div>';
						// 三张图用 items3
					str +='<div data-href="./articleinfo.html" class="art-item">'
							+'<div class="users"><img src="http://resource.idengyun.com/resource/images/2017/04/0a14e304-dbda-4e54-b5ce-fc7a1a6f52eb.jpg" alt=""><span>喵喵</span></div>'
							+'<p>容摘要最多25个字，可以是精秒的点评、或简明的概括容摘要最多25个字，可以是精秒的点评、或简明的概括可以是精秒的点评</p>'
							+'<div class="BGImg items3">'
								+'<div style="background-image:url(http://resource.idengyun.com/resource/images/2017/01/65bb58e4-0dd2-4caa-a484-9a08ee4f6888.jpg);"></div>'
								+'<div style="background-image:url(http://resource.idengyun.com/resource/images/2017/01/d4f62da0-dd16-4eb1-a0e1-ca526a09b691.jpg);"></div>'
								+'<div style="background-image:url(http://resource.idengyun.com/resource/images/2017/01/b006a995-8058-4466-a60f-fa3b28bfa375.jpg);"></div>'
							+'</div>'
							+'<div class="art-menu">'
								+'<span class="browsed">12345</span><span class="comments">12442</span><span class="praised">75214</span><span class="shared">12347</span>'
							+'</div>'
						+'</div>';
					$sign.fadeOut();
					$article.append(str);//添加节点 
					$page++;//分页++
					$article.attr('data-page',$page)
					$article.data('isloading',false); //去掉加载限制
					// 判断到底啦条件
					if(($page >=4)) {
						$article.append('<div class="no-more"></div>');
					};

				},
				error:function(e){
					$load.fadeOut();
					msgAlert('网络请求超时');
				}
			});
		}
		
	}
	// 加载技师数据
	function loadBeautician(){
		let $beautician = $('#beautician'),
			$page = $beautician.attr('data-page');

		if( !($beautician.data('nomore')) && ($page < 4) && !($beautician.data('isloading')) ) {
			
			// 设置加载状态，防止重复多次加载
			$beautician.data('isloading',true)
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
					
					for (var i = 4; i >= 0; i--) {
						str+='<dl>'
		                    +'<dt><img src="../images/mall03_16.jpg" alt=""></dt>'
		                    +'<dd>'
		                        +'<a href="">'
		                        +'<h3>陈倩2<i>高级技师</i></h3>'
		                            +'<p class="features">'
		                                +'<span class="score"><span class="score-inner" style="width:100%"></span></span>'
		                                +'<span class="reservat">100</span>'
		                                +'<span class="comment">88</span>'
		                            +'</p>'
		                            +'<p class="ability">'
		                                +'<span>手法娴</span>'
		                                +'<span>水润秘籍</span>'
		                                +'<span>活好</span>'
		                                +'<span>无可挑剔</span>'
		                            +'</p>'
		                        +'</a>'
		                    +'</dd>'
		               +'</dl>';
					};
					
					$sign.fadeOut();
					$beautician.append(str);//添加节点 
					$page++;//分页++
					$beautician.attr('data-page',$page)
					$beautician.data('isloading',false); //去掉加载限制
					// 判断到底啦条件
					if(($page >=4)) {
						$beautician.append('<div class="no-more"></div>');
					};

				},
				error:function(e){
					$load.fadeOut();
					msgAlert('网络请求超时');
				}
			});
		}
	}

	// 加载案例数据 不要了
	

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