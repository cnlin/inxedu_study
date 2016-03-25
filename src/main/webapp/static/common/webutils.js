//全局变量default布局中重新赋值
var baselocation = "";
var imagesPath="";
var keuploadSimpleUrl="";//kindeditor中使用的路径需要2个参数来区分项目和模块
var uploadSimpleUrl="";//单独的上传按钮使用的路径
var uploadServerUrl="";//上传服务用服务器地址
/**
 * 验证前台学员是否已经登录 
 * @returns true登录 false未登录
 */
function isLogin(){
	var is = false;
	var user = getLoginUser();
	if(user!=null && user.userId>0){
		is=true;
	}
	return is;
}

/**
 * 获取登录学员
 * @returns User
 */
function getLoginUser(){
	var user = null;
	$.ajax({
		url:baselocation+'/uc/getloginUser',
		type:'post',
		async:false,
		dataType:'json',
		success:function(result){
			user = result.entity;
		}
	});
	return user;
}

/**
 * 学员退出登录
 */
function exit(){
	$.ajax({
		url:baselocation+'/uc/exit',
		type:'post',
		dataType:'json',
		async:true,
		success:function(result){
			window.location.reload();
		}
	});
}

/**
 * 内容编辑器
 * @param id 文本域ID
 * @param width 编辑器的宽
 * @param height 编辑器的高
 * @param keImageUploadUrl 上传图片服务的URL
 */
function initKindEditor_addblog(id, width, height,param,pressText) {
	EditorObject = KindEditor.create('textarea[id=' + id + ']', {
		resizeType : 1,
		filterMode : false,// true时过滤HTML代码，false时允许输入任何代码。
		allowPreviewEmoticons : false,
		allowUpload : true,// 允许上传
		urlType : 'domain',// absolute
		newlineTag : 'br',// 回车换行br|p
		width : width,
		height : height,
		minWidth : '10px',
		minHeight : '10px',
		uploadJson : keuploadSimpleUrl+'&param='+param+'&fileType=jpg,gif,png,jpeg&pressText='+pressText,// 图片上传路径
		afterBlur : function() {
			this.sync();
		},
		allowFileManager : false,
		items : [ 'source','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
				'bold', 'italic', 'underline','formatblock','lineheight', 'removeformat', '|',
				'justifyleft', 'justifycenter', 'justifyright',
				'insertorderedlist', 'insertunorderedlist', '|', 'emoticons',
				'image', 'link']
	});
}

/**
 * 后台专用图片上传
 * @param btnId 上传组件的ID
 * @param param 图片上传目录名
 * @param callback 上传成功后的回调函数，函数接收一个参数（上传图片的URL）
 * @param pressText 是否上水印 false或空 否 true是
 */
function initSimpleImageUpload(btnId,param,callback,pressText){
	KindEditor.ready(function(K) {
		var uploadbutton = K.uploadbutton({
			button : K('#'+btnId+'')[0],
			fieldName : "uploadfile",
			url : uploadSimpleUrl+'&param='+param+'&fileType=jpg,gif,png,jpeg&pressText='+pressText,
			afterUpload : function(data) {
				if (data.error == 0) {
					var url = K.formatUrl(data.url, 'absolute');//absolute,domain
					callback(url);
				} else {
					alert(data.message);
				}
			},
			afterError : function(str) {
				alert('自定义错误信息: ' + str);
			}
		});
		uploadbutton.fileBox.change(function(e) {
			uploadbutton.submit();
		});
	});
}

/**
 * 前台专用图片上传
 * @param btnId 上传组件的ID
 * @param param 图片上传目录名
 * @param callback 上传成功后的回调函数，函数接收一个参数（上传图片的URL）
 */
function webImageUpload(btnId,param,callback){
		var uploadbutton = KindEditor.uploadbutton({
			button : KindEditor('#'+btnId+'')[0],
			fieldName : "uploadfile",
			url : uploadSimpleUrl+'&param='+param+'&fileType=jpg,gif,png,jpeg',
			afterUpload : function(data) {
				if (data.error ==0) {
					var url = KindEditor.formatUrl(data.url, 'absolute');//absolute,domain
					callback(url);
				} else {
					alert(data.message);
				}
			},
			afterError : function(str) {
				alert('自定义错误信息: ' + str);
			}
		});
		uploadbutton.fileBox.change(function(e) {
			uploadbutton.submit();
		});
}

/**
 * 删除文件
 * @param filePath
 */
function deleteFile(filePath){
	$.ajax({
		url:baselocation+'/image/deletefile',
		type:'post',
		data:{'filePath':filePath},
		dataType:'json',
		success:function(){}
	});
}

/**
 * 获取Cookie值
 * @param cookieName cookie名
 * @returns 返回Cookie值
 */
function getCookie(cookieName) {
	var cookieString = document.cookie;
	var start = cookieString.indexOf(cookieName+'=');
	if(start!=-1){
		var cookieValue='';
		var cookieArr = cookieString.split(";");
		for(var i=0;i<cookieArr.length;i++){
			var arr = cookieArr[i].split("=");
			if($.trim(cookieName)==$.trim(arr[0])){
				cookieValue=arr[1];
			}
		}
		return cookieValue;
	}
	return null;
}

/**
 * 设置Cookie值 
 * @param name 
 * @param value
 */
function setCookie(name,value){
	var Days = 2;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires="+ exp.toGMTString() + ";path=/";
}

