//添加图片显示区域（高度参数）
function addImgBg(videoimgheight,url){
    if(videoimgheight == null || videoimgheight<0){
        videoimgheight = 200;
    }
    var icowidth = 50;
    var htmls = '<div id="videoimgbg" style="position:relative;background:#eee;width:100%;text-align:center;overflow:hidden;height:'+videoimgheight+'px;margin-top:5px;">';
    htmls += '<img id="videoimg" src= "'+url+'" style="widthwidth:100%;height:'+videoimgheight+'px;" />';
    htmls += '<div style="position:absolute;left:20px;bottom:20px;" class="play1"></div>';
    htmls += '</div>';
    $(".contentBox").prepend(htmls);
    
}
///添加点击事件
function btnAddClick(callback){
   
    var img =$("#videoimgbg");
    img.click(function(){
              
                           var w = $("#videoimgbg").width();
                           var h = w/16*9;
                           playHeight(h,callback);
                           });
    
}
///重新加载图片地址并增加点击事件
function reloadImgSrc(height,url,callback){


    var img = $("#videoimg");
    
    if(img.length==0){
    addImgBg(height,url);
    }else{
        img.height(height);
        img.attr("src",url);
    }
   
    btnAddClick(callback);
    
}

//获得显示区域距离顶部的距离
function getTop(){
    alert("ddddddddddd");

    window.imagelistner.getTopjs($("#videoimgbg").offset().top);//用接口stub, 通过调用内部类中的方法jsMethod给java传回
//    return $("#videoimgbg").offset().top;
}
//内部方法
function playHeight(videoimgheight,callback){
//    $("#videoimgbg").find("img").remove();
    $("#videoimgbg").animate({height:videoimgheight+"px"},500,function(){
//                             callback();
                             alert("ddddddddddd");
                             window.imagelistner.startVideo();
                             });
}
