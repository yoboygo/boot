$(function(){
	
	$('.banner.flexslider').flexslider({
		animation: "slide",
		slideDirection: "horizontal"
	});
	$('.loading').fadeOut();

	// 滚动显示导航
	var scrollTop = 0,$navTop1 = 0,$navTop2 = 0,$navTop3 = 0;

	$(".flexslider img").one('load', function() {
  	// do stuff 
  		$navTop1 = $('#local1').offset().top;
	}).each(function() {
	  if(this.complete) $(this).load();
	});

	function showNav(){
		$navTop2 = $('#local2').offset().top;
		$navTop3 = $('#local3').offset().top;

		$(window).scroll(function(){
			scrollTop = $(this).scrollTop() + 50;
			if(scrollTop < $navTop1 ){
				$('.salon-nav').removeClass('animation-show');
			}
			if(scrollTop >= $navTop1 ){
				$('.salon-nav').addClass('animation-show');
				$('.salon-nav div').eq(0).addClass('active').siblings().removeClass('active');
			}
			if(scrollTop >= $navTop2 ){
				$('.salon-nav div').eq(1).addClass('active').siblings().removeClass('active');
			}
			if(scrollTop >= $navTop3 ){
				$('.salon-nav div').eq(2).addClass('active').siblings().removeClass('active');
			}

		});
	}
	// 点击滚动
	$('.salon-nav div').click(function(){
		var $this = $(this);
		var $navTop = $('#'+$this.attr('data-id') ).offset().top;
		console.log($navTop)
		$('html,body').animate({scrollTop:$navTop+'px'},150);
	});

	setTimeout(function(){
		showNav()
	},1000);
	// 点击收藏
	$('.store-footer .collection').click(function(){
		console.log(1);
		if(!$(this).hasClass('active')){
			//ajax
			$(this).addClass('active');
		}
	});

});