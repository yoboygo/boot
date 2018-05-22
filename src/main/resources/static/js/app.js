// 初始化
let isUpload = false // true 表示正在上传图片
var uploadUrl = "http://push.idengyun.com/appServer/user/uploadFile.do"

let bannerItemIndex = -1, //缓存查询banner图的顺序 用以存储goodsid
    file,
    pageList,
    content,
    editGoodsID
    pageTitle = '',
    pageDesc = '',
    pageName = '';

/*
  上传图片
*/
function upLoadImg() {
  
  let imgStr = ''
   // 替换上线

  return new Promise(function(resolve, reject) {
    $('#upImg').change(function() {
      isUpload = true
      let fileSize = file.files[0].size;
          fileSize = Math.round(fileSize/1024)/1000;
      if (fileSize>1) {
        alert('这张图片大与1M！');
        resolve(imgStr)
      }
      
      let formData = new FormData();  
      formData.append('file', file.files[0]);
         
        $.ajax({
          type: 'post',
          url: uploadUrl,
          contentType: false,    //这个一定要写
          processData: false, //这个也一定要写，不然会报错
          data:formData,
          dataType:'json',    //返回类型，有json，text，HTML。这里并没有jsonp格式，所以别妄想能用jsonp做跨域了。
          beforeSend: function () {
            $('#loading').show()
          }, success:function(data){
            imgStr = data.file_url
            resolve(imgStr)
            $('#upImg').val('')
            $('#loading').hide()
          }, error: function(XMLHttpRequest, textStatus, errorThrown, data){
            alert('头像上传失败，请稍后重试.')
            $('#loading').hide()
          }            
      });


    })
  })

}

/*
  添加banner方法
*/
function addBanner(){
  if (isUpload) {
    return 
  }
  $('#upImg').off().click()

  upLoadImg().then(function (result){
    // console.log(result);
    isUpload = false;
    if (result.length < 1) {
      return
    }
    let bannerItem = `
      <div class="banner"><img src="${result}"></div>
    `
    $('#content').append(bannerItem)
    let items = $('#pageList .page-manage-item').length
    let bannerTemp = 
        `<li class="page-manage-item" data-ids="">
            <span class="page-number"><em>${items + 1} </em></span>
            <span class="action-item edit-id" data-tooltip="添加商品ID"><i class="iconfont icon-goods"></i>商品ID</span>
            <span class="action-item change-img" data-tooltip="更改这张图片"><i class="iconfont icon-pic"></i>换图</span>
            <span class="action-item del-banner" data-tooltip="删除这张图片"><i class="iconfont icon-del"></i>删除</span>
            <div class="del-cover"><span class="confirm-btn">确认删除</span></div>
          </li>`
         
    pageList.append(bannerTemp)

  })

}
/*
 * 填写标题
*/
function showTitle() {
  $('.edit-title').addClass('active')
  $('.title-cover').fadeIn()
  $('#edit-title').focus()
}

function hideTitle() {
  $('.edit-title').removeClass('active')
  $('.template-head .prop').fadeOut()
}


/*
 * 填写描述
*/
function showAtb() {
  $('.edit-atb').addClass('active')
  $('.atb-cover').fadeIn()
  $('#edit-atb').focus()
}
function hideAtb() {
  $('.edit-atb').removeClass('active')
  $('.template-head .prop').fadeOut()
}

/*
 * 填写文件名
*/
function showFile() {
  $('.edit-file').addClass('active')
  $('.file-cover').fadeIn()
  $('#edit-file').focus()
}
function hideFile() {
  $('.edit-file').removeClass('active')
  $('.template-head .prop').fadeOut()
}
/*
 * 添加商品id
*/

function showGoodIDCover(that) {
  //先遍历是否有商品id并展示
  bannerItemIndex = that.parents('.page-manage-item').index()
  let goodsID = that.parents('.page-manage-item').data('ids')
  // console.log(goodsID)
  let idItems = ''
  $('.goods-bar').removeClass('hide-right')
  if(goodsID.length < 1) {
    idItems = `
      <li class="page-manage-item">
        <input type="text" class="input-id" placeholder="输入商品 ID">
        <span class="del-itemid action-item" data-tooltip="删除此项ID"><i class="iconfont icon-del"></i></span>
      </li>
    `
  }else {
    for (var val of goodsID) {
      idItems += `
        <li class="page-manage-item">
          <input type="text" class="input-id" placeholder="输入商品 ID" value="${val}">
          <span class="del-itemid action-item" data-tooltip="删除此项ID"><i class="iconfont icon-del"></i></span>
        </li>
      `
    }
  }
  editGoodsID.append(idItems)
  
}

function closeGoodIDCover(that) {

  //清空缓存的所有商品id
  $('.goods-bar').addClass('hide-right')
  bannerItemIndex = -1
  window.setTimeout(function(){
    editGoodsID.empty()
  },200)
  
}

function addGoodsID() {
  let editItem = `
      <li class="page-manage-item">
        <input type="text" class="input-id" placeholder="输入商品 ID">
        <span class="del-itemid action-item" data-tooltip="添加商品ID"><i class="iconfont icon-del"></i></span>
      </li>
    `
    editGoodsID.append(editItem)
}

function delGoodsID(that){
  that.parents('.page-manage-item').remove()
}

function saveGoodID() {
  let dataArr = [],
      flage = true,
      goodLink = '',
      goodWrap = '',
      goodsID = editGoodsID.find('.input-id'),
      bannerItems;

  $(goodsID).each(function(i, d) {
    if ($(d).val().length == 0) {
      window.alert('第'+parseInt(i+1)+'项的ID不能为空')
      flage = false
      return false
    }
    goodLink += `
      <div class="goodLink" onclick="gotoGoodsDetail(${$(d).val()},0)"></div>
    `
    dataArr.push(parseInt($(d).val()))
  })
  if(flage) {
    goodWrap = `<div class="goodWrap">${goodLink}</div>`
    bannerItems = content.find('.banner').eq(bannerItemIndex)
    bannerItems.find('.goodWrap').remove()
    bannerItems.append(goodWrap)
    $('.goods-bar').addClass('hide-right')
    pageList.find('.page-manage-item').eq(bannerItemIndex).data('ids', dataArr)
    closeGoodIDCover()
  }
   
}

/*
 * 更换图片
 */

function changeImg(that) {
  if (isUpload) {
    return 
  }
  let itemIdex = that.parent().index()
  let oldImg = content.find('.banner').eq(itemIdex).find('img')
  // let oldImg = content.find('.banner').eq(itemIdex).find('img').prop('src')
  
  $('#upImg').off().click()

  upLoadImg().then(function (result){
    // console.log(result);
    isUpload = false;
    if (result.length < 1) {
      return
    }
    
    oldImg.prop('src', result)

  })

}

/*
 * 删除banner
 */
function showConfirm(that) {
  that.siblings('.del-cover').addClass('show-confirm')
}

function delBanner(that) {
  let bannerIndex = that.parents('.page-manage-item').index()
  that.parents('.page-manage-item').remove()
  content.find('.banner').eq(bannerIndex).remove()
}

/*
 * 导入界面
*/

function generateDom() {
  let pageWrap = ''
    content.html($('#import-content').val())

    content.find('.banner').each(function(i, e) {
      let idArr = []
      let goodLink = $(e).find('.goodLink')

      if(goodLink.length < 1) { //没有click
        pageWrap = `
          <li class="page-manage-item" data-ids="">
            <span class="page-number"><em>${i} </em></span>
            <span class="action-item edit-id" data-tooltip="添加商品ID"><i class="iconfont icon-goods"></i>商品ID</span>
            <span class="action-item change-img" data-tooltip="更改这张图片"><i class="iconfont icon-pic"></i>换图</span>
            <span class="action-item del-banner" data-tooltip="删除这张图片"><i class="iconfont icon-del"></i>删除</span>
            <div class="del-cover"><span class="confirm-btn">确认删除</span></div>
          </li>
        `
      }else {

        for (var val of goodLink) {

          idArr.push( parseInt( $(val).attr('onclick').substring($(val).attr('onclick').indexOf('(')+1,$(val).attr('onclick').lastIndexOf(',')) ) )
        }
        pageWrap = `
          <li class="page-manage-item" data-ids="[${idArr}]">
            <span class="page-number"><em>${i} </em></span>
            <span class="action-item edit-id" data-tooltip="添加商品ID"><i class="iconfont icon-goods"></i>商品ID</span>
            <span class="action-item change-img" data-tooltip="更改这张图片"><i class="iconfont icon-pic"></i>换图</span>
            <span class="action-item del-banner" data-tooltip="删除这张图片"><i class="iconfont icon-del"></i>删除</span>
            <div class="del-cover"><span class="confirm-btn">确认删除</span></div>
          </li>
        `
      }
      pageList.append(pageWrap) 
    })

  $('.import-cover').hide()
}


$(function(){
  file = document.querySelector("#upImg");

  pageList = $('#pageList');
  content = $('#content');
  editGoodsID = $('#edit-goodsID')
  // 添加一个
  $('#add-banner').click(function(){
    addBanner()
  })

  // 填写标题
  $('.edit-title').click(function() {
    showTitle()
  })
  // 隐藏标题弹层
  $('.title-cover').mouseleave(function() {
    hideTitle()
  })
  $('#edit-title').blur(function() {
    hideTitle()
  })
  // 编辑标题
  $('#edit-title').on('input', function() {
    pageTitle = $(this).val()
    $('#title').html($(this).val())
  })

  // 填写描述
  $('.edit-atb').click(function() {
    showAtb()
  })
  // 隐藏标题弹层
  $('.atb-cover').mouseleave(function() {
    hideAtb()
  })
  $('#edit-atb').blur(function() {
    hideAtb()
  })
  // 编辑描述
  $('#edit-atb').blur(function() {
    pageDesc = $(this).val()
  })

  // edit-file
  $('.edit-file').click(function() {
    showFile()
  })
  // 隐藏标题弹层
  $('.file-cover').mouseleave(function() {
    hideFile()
  })
  $('#edit-file').blur(function() {
    hideFile()
  })
  // 编辑文件名
  $('#edit-file').change(function() {
    pageName = $(this).val()
  })

  /*
   * 侧边栏编辑
   *  修改id 修改图片 修改排序 hover效果
  */
  //hover
  $(document).on('mouseenter', '#pageList li', function() {
    let itemIdex = $(this).index(),
        banner = content.find('.banner').eq(itemIdex),
        posTop = content.scrollTop() + banner.position().top - 87;

    banner.addClass('current')
   // console.log(posTop)
    content.animate({"scrollTop":posTop})
  })

  $(document).on('mouseleave', '#pageList li', function() {
    let itemIdex = $(this).index()
    content.find('.banner').eq(itemIdex).removeClass('current')
    pageList.find('.del-cover').removeClass('show-confirm')
  })
  
  /*
   * 更换banner换图
  */

  $(document).on('click', '#pageList .change-img', function() {
    changeImg($(this))
  })

  /*
   * 删除banner
   */

  $(document).on('click', '#pageList .del-banner', function() {
    showConfirm($(this))
  })

  $(document).on('click', '#pageList .confirm-btn', function() {
    delBanner($(this))
  })

  /*
   * 输入商品id
   *  
  */
      
  $(document).on('click','#pageList .edit-id', function() {
    showGoodIDCover($(this))
  })
  $(document).on('click','.goods-bar .close-btn', function() {
    closeGoodIDCover($(this))
  })
  //增加一个商品id 
  $('.goods-bar #add-id').click(function() {
    addGoodsID()
  })
  // 保存goodsID
  $('#save-goodsID').click(function() {
    saveGoodID()
  })
  //删除id
  $(document).on('click','#edit-goodsID .del-itemid', function() {
    delGoodsID($(this))
  })

  /*
   * 下载页面
   */
  $('#download').click(function() {
    if(pageTitle.length < 1) {
      window.alert('页面的标题怎么不写呢！')
      return 
    }
    if(pageDesc.length < 1) {
      window.alert('APP里分享文字别忘了！')
      return 
    }
    if(pageName.length < 1) {
      window.alert('随便填一个载文件的名字啊！')
      return 
    }
    let contentHtml = content.html()
    let htmlPage = 
      `<!DOCTYPE html>
        <html lang="en">
          <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
            <meta name="apple-mobile-web-app-capable" content="yes">
            <meta name="apple-mobile-web-app-status-bar-style" content="black">
            <title>${pageTitle}</title>
            <link rel="stylesheet" href="https://static.idengyun.com/staticWebRoot/static/css/base1.css">
            <style>
              body{padding-bottom:0px;}
              .goodLink{float:left;height:100%;width:50%;-webkit-box-flex: 1;flex: 1;}
              .banner{position:relative;}
              .goodWrap{position:absolute;left:0;top:0;width:100%;height:100%;display:flex; }
              .goodLink{float:left;height:100%;width:50%;flex: 1;}
            </style>
          </head>
        <body>
        ${contentHtml}
        <script type="text/javascript" src="https://static.idengyun.com/staticWebRoot/static/js/jquery.min.js"></script>
        <script type="text/javascript">
          var isapp = 0;
          var $url = window.location.href;
          function isApp(s){ 
            isapp = s;
            native.viewDetailShare("${pageDesc}", "来自每天美耶客户端",$url);
          }
          function gotoGoodsDetail($goodsid , $actionType){
            if(isapp != 0){
              native.viewDetailGoodsId($goodsid, $actionType);
            }else{
              window.location.href = 'https://wap.idengyun.com/mtmy-wap/goods/queryGoodsDetail.do?goods_id=' + $goodsid + '&action_type=' + $actionType;
            }
          }
          function gotoUrl($url){
            if(isapp != 0){
              native.viewDetailLink($url);
            }else{
              window.location.href = $url ;
            }
          }
        </script>
        </body>
        </html>`
  
    funDownload(htmlPage, pageName+'.html');

  })

  /*
   * 导入界面
  */
  $('#import-html').click(function() {
    $('.import-cover').show()
  });

  $('#generate-dom').click(function() {
    generateDom()
  })

  $('#cancel-import').click(function() {
    $('.import-cover').hide()
  })

}) 
/*
 * 文件下载
*/
// funDownload(eleTextarea.value, 'test.html');
function funDownload(content, filename) {
  var eleLink = document.createElement('a');
      eleLink.download = filename;
      eleLink.style.display = 'none';
      // 字符内容转变成blob地址
    var blob = new Blob([content]);
    eleLink.href = URL.createObjectURL(blob);
    // 触发点击
    document.body.appendChild(eleLink);
    eleLink.click();
    // 然后移除
    document.body.removeChild(eleLink);
};



