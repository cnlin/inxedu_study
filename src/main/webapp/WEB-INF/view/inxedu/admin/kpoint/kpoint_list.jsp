<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>课程节点</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/common/ztree/css/zTreeStyle.css" />
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/kpoint/kpoint.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/teacher/select_teacher_list.js"></script>

<script type="text/javascript" src="${ctx}/static/common/uploadify/ccswfobject.js"></script>
<style type="text/css">
#swfDiv embed {
	position: absolute;
	z-index: 1;
}
#swfDiv{*position:absolute; z-index:2;}
</style>
<script type="text/javascript">
		var ztree='${kpointList}';
		$(function(){
			showKpointZtree(ztree);
		});
	</script>
</head>
<body>
	<div class="mt20">
		<table width="100%" cellspacing="0" cellpadding="0" border="0" class="commonTab01">
			<thead>
				<tr>
					<th colspan="2" align="left">
						<span>视频节点管理</span>
						<font color="red">*视频节点只支持二级</font>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td width="20%">
						<div id="kpointList" class="ztree"></div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="left">
						<a title="创建视频节点" onclick="addaKpoint(${courseId});" class="button tooltip" href="javascript:void(0)">
							<span></span>
							创建视频节点
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<!-- 修改视频节点信息窗口，开始 -->
	<div id="updateWin" aria-labelledby="ui-dialog-title-dialog" role="dialog" tabindex="-1"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
		style="display: none; position: absolute; overflow: hidden; z-index: 1010; outline: 0px none; height: auto; width: 511px; top: 173px; left: 367px;">
		<div style="-moz-user-select: none;" unselectable="on" class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
			<span style="-moz-user-select: none;" unselectable="on" id="ui-dialog-title-dialog" class="ui-dialog-title">修改视频节点</span>
			<a style="-moz-user-select: none;" unselectable="on" role="button" class="ui-dialog-titlebar-close ui-corner-all"
				href="javascript:void(0)">
				<span style="-moz-user-select: none;" unselectable="on" class="ui-icon ui-icon-closethick">close</span>
			</a>
		</div>
		<div style="height: 300px; min-height: 42px; width: auto;" class="ui-dialog-content ui-widget-content">
			<form id="updateForm">
				<input type="hidden" name="courseKpoint.kpointId" />
				<table style="line-height: 35px;">
					<tr>
						<td>视频节点名:</td>
						<td style="text-align: left;">
							<input name="courseKpoint.name" type="text" />
						</td>
					</tr>
					<tr>
						<td>节点类型:</td>
						<td style="text-align: left;">
							<select id="courseKpointKpointType" name="courseKpoint.kpointType" onchange="kpointTypeChange(this)">
								<option value="0">目录</option>
								<option value="1">视频</option>
							</select>
						</td>
					</tr>
					<tr style="display:none">
						<td>视频类型:</td>
						<td style="text-align: left;">
							<select id="courseKpointVideoType" name="courseKpoint.videoType" onchange="videoTypeChange(this)">
								<option value="">--请选择--</option>
								<option value="IFRAME">IFRAME(56,SWF)</option>
								<option value="CC">CC视频</option>
								<option value="LETV">乐视云</option>
							</select>
						</td>
					</tr>
					<tr class="uploadCCVideo" style="display:none">
						<td>上传CC视频:</td>
						<td style="text-align: left;">
							<div id="swfDiv" style="*position:absolute; z-index:2;float:left;z-index: 1000;cursor: pointer; margin-top:5px;"></div><input type="button" value="上传" id="btn_width" style="width: 80px; height: 25px;"/>
							<input type="hidden" id="upload_title" minlength="0">
							<input type="hidden"  id="upload_tag" minlength="0">
							<input type="hidden"  id="upload_desp" minlength="0">
							<script type="text/javascript">
								// 加载上传flash ------------- start
									var swfobj=new SWFObject('http://union.bokecc.com/flash/api/uploader.swf', 'uploadswf', '80', '25', '8');
									swfobj.addVariable( "progress_interval" , 1);	//	上传进度通知间隔时长（单位：s）
									swfobj.addVariable( "notify_url" , "");	//	上传视频后回调接口
									swfobj.addParam('allowFullscreen','true');
									swfobj.addParam('allowScriptAccess','always');
									swfobj.addParam('wmode','transparent');
									swfobj.write('swfDiv');
								// 加载上传flash ------------- end
							</script>
						</td>
					</tr>
					<tr class="uploadCCVideo" style="display:none">
						<td>上传进度：</td>
						<td style="text-align: left;">
							<span id="up">无</span>
						</td>
					</tr>
					<tr style="display:none">
						<td>视频节点URL:</td>
						<td style="text-align: left;">
							<input type="text" name="courseKpoint.videoUrl" value="" />
					</tr>
					<tr>
						<td>排序:</td>
						<td>
							<input type="text" value="0" name="courseKpoint.sort" />
						</td>
					</tr>
					<tr>
						<td>播放次数:</td>
						<td>
							<input type="text" value="0" name="courseKpoint.playCount" />
						</td>
					</tr>
					<tr>
						<td>播放时间:</td>
						<td>
							<input type="text" value="00:00" name="courseKpoint.playTime" />
						</td>
					</tr>
					<tr>
						<td>是否免费:</td>
						<td style="text-align: left;">
							<input type="radio" name="courseKpoint.free" value="1" />
							是
							<input type="radio" name="courseKpoint.free" value="2" />
							否
						</td>
					</tr>
					<tr>
						<td>视频讲师:</td>
						<td style="text-align: left;">
							<input type="hidden" name="courseKpoint.teacherId" value="0" />
							<p id="teacher" style="margin: 0 0 0em;"></p>
							<a href="javascript:void(0)" onclick="selectTeacher('radio')">选择老师</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div style="-moz-user-select: none;" unselectable="on" class="ui-resizable-handle ui-resizable-n"></div>
		<div style="-moz-user-select: none;" unselectable="on" class="ui-resizable-handle ui-resizable-e"></div>
		<div style="-moz-user-select: none;" unselectable="on" class="ui-resizable-handle ui-resizable-s"></div>
		<div style="-moz-user-select: none;" unselectable="on" class="ui-resizable-handle ui-resizable-w"></div>
		<div unselectable="on" style="z-index: 1001; -moz-user-select: none;"
			class="ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se ui-icon-grip-diagonal-se"></div>
		<div unselectable="on" style="z-index: 1002; -moz-user-select: none;" class="ui-resizable-handle ui-resizable-sw"></div>
		<div unselectable="on" style="z-index: 1003; -moz-user-select: none;" class="ui-resizable-handle ui-resizable-ne"></div>
		<div unselectable="on" style="z-index: 1004; -moz-user-select: none;" class="ui-resizable-handle ui-resizable-nw"></div>
		<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
			<button class="ui-state-default ui-corner-all" onclick="updateKpoint()" type="button">确定</button>
			<button class="ui-state-default ui-corner-all closeBut" type="button">取消</button>
		</div>
	</div>
	<!-- 修改视频节点信息窗口，结束 -->
</body>
</html>