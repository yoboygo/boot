var provinces = {
    "A": [
        "厦门市","莆田市"
    ],
    "B": [
        "北京市"
    ],
    "C": [
        "湛江市","茂名市"
    ],
    "D": [
        "重庆市"
    ],
    "E": [
       "厦门市","莆田市"
    ],
    "F": [
        "福州市","三明市","泉州市","漳州市","南平市","龙岩市","宁德市"
    ],
    "G": [
        "广州市","深圳市","珠海市","汕头市","韶关市","佛山市","江门市","肇庆市","惠州市","梅州市","汕尾市","河源市","阳江市","清远市","东莞市","中山市","潮州市","揭阳市","云浮市"
    ],
    "H": [
        "郑州市","开封市","洛阳市","平顶山市","安阳市","鹤壁市","新乡市","焦作市","濮阳市","许昌市","漯河市","三门峡市","南阳市","商丘市","信阳市","周口市","驻马店市"
    ],
    "J": [
        "福州市","三明市","泉州市","漳州市","南平市","龙岩市","宁德市"
    ],
    "L": [
        "长春市","吉林市","四平市","辽源市","通化市","白山市","松原市","白城市","延边朝鲜族自治州"
    ],
    "N": [
        "长春市","吉林市","四平市","辽源市","通化市","白山市","松原市","白城市","延边朝鲜族自治州"
    ],
    "Q": [
        "青海","西宁市","海东地区","海北藏族自治州","黄南藏族自治州","海南藏族自治州","果洛藏族自治州","玉树藏族自治州","海西蒙古族藏族自治州"
    ],
    "S": [
         "广州市","深圳市","珠海市","汕头市","韶关市","佛山市","江门市","肇庆市","惠州市","梅州市","汕尾市","河源市","阳江市","清远市","东莞市","中山市","潮州市","揭阳市","云浮市"
    ],
    "T": [
        "天津市"
    ],
    "X": [
        "新疆","乌鲁木齐市","克拉玛依市","吐鲁番地区","哈密地区","昌吉回族自治州","博尔塔拉蒙古自治州","巴音郭楞蒙古自治州","阿克苏地区","克孜勒苏柯尔克孜自治州","喀什地区"
    ],
    "Y": [
        "云南","福州市","三明市","泉州市"
    ],
    "Z": [
        "浙江","杭州市","宁波市","温州市","嘉兴市","湖州市","绍兴市","金华市","衢州市","舟山市","台州市","丽水市"
   ]
};

/*
 * 新版获取地理定位信息
*/

//定义 省 市 区
var province='',city='',district='';

function getLocation(){
    $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js',function(){  
        // alert(remote_ip_info.country);//国家  
        // alert(remote_ip_info.province);//省份  
       // alert(remote_ip_info.city);//城市  
       district = remote_ip_info.city;
    }); 
}

/*CityPicker */
    var CityPicker = function (el, options) {
        this.el = $(el);
        this.options = options;
        this.provinces = provinces;
        this.pro = null;
        this.city = null;
        this.elType = this.el.is('div');

        this.init();
    };

    var p = CityPicker.prototype;
    var oPos = 0;
    var selectCity = '';
    

    p.init = function () {
        this.initEvent();
        this.preventPopKeyboard();

    };

    p.preventPopKeyboard = function () {
        if (this.elType) {
            this.el.prop("readonly", true);
        }
    };

    p.initEvent = function () {
        this.el.on("click", function (e) {
            var pickerBox = $(".picker-box");

            if (pickerBox[0]) {
                pickerBox.show();
                $('.picker-box .location span').html(district+'<i></i>')
            } else {
                this.create();
            }
            
            // 加入排序弹层判断
            if( $('.sorting').hasClass('drop-trigger') ){
                // 收起排序选项
                $('.sorting').removeClass('drop-trigger');
                $('.droplist-sort-cover').hide();
            }

        }.bind(this));
    };

    p.create = function () {
        this.createCityPickerBox();
        this.createProList();
        this.proClick();
        this.createNavBar();
        this.navEvent();
    };

    p.createCityPickerBox = function () {
        var proBox = '<div class="picker-box"><dl class="location"><dt><span>'+district+'<i></i></span></dt></dl></div>';
        $("body").append(proBox);
    };

    p.createProList = function () {
        var provinces = this.provinces;
        var proBox;
        var dl = '<dl class="location"><dt>所有城市</dt></dl>';
        for (var letterKey in provinces) {
            var val = provinces[letterKey];
            // console.log(val)
            if (provinces.hasOwnProperty(letterKey)) {
                var dt = "<dt id='" + letterKey + "'>" + letterKey + "</dt>";
                var dd = "";
                for (var proKey in val) {
                    if (val.hasOwnProperty(proKey)) {

                        dd += "<dd data-letter=" + val[proKey] + ">" + val[proKey] + "</dd>";
                    }
                }
                dl += "<dl>" + dt + dd + "</dl>";
            }
        }

        proBox = "<section class='pro-picker'>" + dl + "</section>";

        $(".picker-box").append(proBox);
        $('.picker-box .location').on('click',function(){
            $('.picker-box').hide();
        })
    };

    p.proClick = function () {
        var that = this;
        $(".pro-picker").on("click", function (e) {
            var target = e.target;
            if ($(target).is("dd")) {
                that.pro = $(target).html();
               
                district = that.pro; //获取筛选的城市名称
                //加载商品方法，传入city 和 sort 默认0

                loadItem(district,sort);
                
                $(".picker-box").hide();
            }
        });
    };

    p.createNavBar = function () {
        var str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        var arr = str.split("");
        var a = "";
        arr.forEach(function (item, i) {
            a += '<span href="#' + item + '">' + item + '</span>';
        });

        var div = '<div class="navbar">' + a + '</div>';

        $(".picker-box").append(div);
    };

    p.navEvent = function () {
        var that = this;
        var navBar = $(".navbar");
        var width = navBar.find("span").width();
        var height = navBar.find("span").height();
        navBar.on("touchstart", function (e) {
            $(this).addClass("active");
            that.createLetterPrompt($(e.target).html());
        });
        
        navBar.on("touchmove", function (e) {
            e.preventDefault();
            var touch = e.originalEvent.touches[0];
            var pos = {"x": touch.pageX, "y": touch.pageY};
            var x = pos.x, y = pos.y;
            
            $(this).find("span").each(function (i, item) {
                var offset = $(item).offset();
                var left = offset.left, top = offset.top;
                if (x > left && x < (left + width) && y > top && y < (top + height)) {
                    if($(''+$(item).attr('href')+'').length > 0){
                        oPos = $(''+$(item).attr('href')+'').offset().top + $('.pro-picker').scrollTop()-46;
                    }
                    $('.pro-picker').stop().animate({scrollTop:oPos+'px'},150);
                    that.changeLetter($(item).html());
                }
            });
        });

        navBar.on("touchend", function () {
            $(this).removeClass("active");
            $(".prompt").hide();
        })
    };

    p.createLetterPrompt = function (letter) {
        var prompt = $(".prompt");
        if (prompt[0]) {
            prompt.show();
        } else {
            var span = "<span class='prompt'>" + letter + "</span>";
            $(".picker-box").append(span);
        }
    };


    p.changeLetter = function (letter) {
        var prompt = $(".prompt");
        prompt.html(letter);
    };

    $.fn.CityPicker = function (options) {
        return new CityPicker(this, options);
    }

/*CityPicker end*/

// 加载商品方法 传入city名称 和 sort排序选项 

var sort = 0; //例sort默认值: 0 

function loadItem(city,sort){
        
    $('.select-city').html(city+'<i></i>');
    var sortId = sort,
        cityId = city;

    //ajax
    $.ajax({
        url:'http://www.tngou.net/tnfs/api/list',
    
        data:{

        },
        dataType: 'jsonp',
        type:'get',
        beforeSend:function(){
            $load.show();
        },
        success: function() {
            // 没有更多数据的判断条件
            var str = '';
            $('#beautician').empty();

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
            $load.hide();
            $('#beautician').append(str);
        },
        error:function(e){
           $load.hide();
            msgAlert('网络请求超时');
        }
    });
}
// 提示方法
var $load = $('#addload'),
    $tip = $load.find('.spiner');
function msgAlert(text){
    $('#addload').find('.spiner').addClass('warning').append('<p class="text">'+text+'</p>');

    $('#addload').fadeIn();
    setTimeout(function(){
        $load.fadeOut(500,function(){
            $tip.empty().removeClass('warning');
        })
    },1000);
};

$(function(){
    getLocation();//调用地理位置
    $('#addload').hide();
    $(".select-city").CityPicker();
    
    // 
    sortOption();
});

/*
 * 智能排序
*/
// district

function sortOption(){
    
    $('.droplist-sort-tab').click(function(){

        if( $('.sorting').hasClass('drop-trigger') ){
            // 收起排序选项
            $('.sorting').removeClass('drop-trigger');
            $('.droplist-sort-cover').hide();
        }else{
            // 展开排序选项
            $('.sorting').addClass('drop-trigger');
            $('.droplist-sort-cover').show();
        }
    
        $('.droplist-sort-option li').click(function(){
            
            sort = $(this).prop('id');

            $(this).addClass('selected').siblings().removeClass('selected');
            $('.droplist-sort-tab span').html($(this).html());
            // 收起排序选项
            $('.sorting').removeClass('drop-trigger');
            $('.droplist-sort-cover').hide();

            // district 
            loadItem(district,sort);

        });

    });

}


