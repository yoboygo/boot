;(function ($,window,document,undefined) {

    //页面加载完成后绑定事件
    $(function () {

        var $title = $('#title');
        var $content = $('#editor');

        //创建富文本编辑器
        var E = window.wangEditor
        var $editor = new E('#editor')
        // 或者 var editor = new E( document.getElementById('editor') )
        $editor.customConfig.uploadImgShowBase64 = true;

        //选中内容框，清空初始化内容
        $editor.customConfig.onfocus = function(){
            var init = $content.data('init');
            if(init == undefined || !init){
                $content.data('init',true);
                $editor.txt.clear();
            }
        }

        $editor.create()

        //发布文章
        $('#publish').click(function () {
            var title = $title.val();
            var content = $editor.txt.html();
            var text = $editor.txt.text();
            if(title == undefined || title == ''){
                //标题不为空
                alertDranger('标题不为空');
                return false;
            }
            if(content == undefined || content == ''){
                //内容不能为空
                alertDranger('内容不能为空');
                return false;
            }

            //保存文章

        });

        //绑定关闭事件
        $('.alert-danger').click(function () {
            $('.alert-danger').fadeOut();
        });
    });

    //提示警告消息
    function alertDranger(msg){
        $('.alert-danger').html(msg);
        $('.alert-danger').fadeIn();
    }
})(jQuery,window,document);