<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/public.css">
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.6.2.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	 	var theForm;
         window.onload = function(){
            theForm = document.form1;
         } 
	    function toSubmit(obj){
	    	if (obj == "query"){
	    		theForm.submit(); 
	    	} else if (obj == "add"){  
				theForm.action="<%=basePath%>employe/toadd.do";
    			theForm.submit();
	    	} else if (obj == "edit"){
	    		var num= $("input[type=checkbox][name='ids']:checked").size(); 
			    if(-1==num){
				   return;
			    }else if(num==0){
				   alert("请选择要修改的项！");
				   return;
			    }else if(num>1){
				   alert("请选择一项！");
				   return;
				}else{
		    		theForm.action="<%=basePath%>employe/toedit.do";   
		    		theForm.submit();
	    		}
	    	} else if (obj == "del"){  
		    	var num= $("input[type=checkbox][name='ids']:checked").size(); 
			    if(-1==num){ 
				   return;
			    }else if(num==0){
				   alert("请选择要删除的项！");
				   return;
			    }else if(confirm("选中的记录将被删除，是否继续？")){   
			    	theForm.action="<%=basePath%>${lowerName}/deletes.do";
	    			theForm.submit();
	    			//deleteWb(theForm);
			    } 
	    	} 
	    }
	   </script>
  </head>
  
 <body>
<center>
<form name="form1" method="post" action="<%=basePath%>/${lowerName}//list.do">
   <script language="javascript">                  
		<s:property value="clientScript" />
   </script>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tit" align="left"><strong>&nbsp;系统管理&gt;&gt;微博管理</strong></td>
	</tr>
</table>

<div style="height:10px;"></div>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
<td class="td_bd">
	<table class="maintable" border="0" width="98%" cellspacing="0">
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
		<%-- 查询条件区域 模板区域 --%>
		<tr>
			<td width="15%" style="text-align: right;">
				关键字：
			</td>
			<td width="35%">
				<input class="inputsearch" type="text" id="model.keyWord" name="model.keyWord"
					value="<s:property value="model.keyWord" />" size="15" maxlength="15" />
			</td>
			<td width="15%" style="text-align: right;">
				微博分类：
			</td>
			<td width="35%">
				<select class="inputsearch" name="model.type">
					<option value="" >----</option>
					<s:iterator value="wbTypeList">
						<option value="${id}" <s:if test="id == model.type">selected</s:if>>${name}</option>
					</s:iterator>
				</select>
			</td>
		</tr>
		<tr>
			<td width="15%" style="text-align: right;">
				审核状态：
			</td>
			<td width="35%">
				<select class="inputsearch" name="model.verifyState">
					<option value="" >----</option>
					<option value="0" <s:if test="0 == model.verifyState">selected</s:if> >已审核</option>
					<option value="1" <s:if test="1 == model.verifyState">selected</s:if> >待审核</option>
					<option value="2" <s:if test="2 == model.verifyState">selected</s:if> >不通过</option>
				</select>
			</td>
			<td width="15%" style="text-align: right;">
				微博类型：
			</td>
			<td width="35%">
				<select class="inputsearch" name="model.isUpload">
					<option value="" >----</option>
					<option value="0" <s:if test="0 == model.isUpload">selected</s:if> >文本</option>
					<option value="1" <s:if test="1 == model.isUpload">selected</s:if> >图片</option>
					<option value="2" <s:if test="2 == model.isUpload">selected</s:if> >视频</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="15%" style="text-align: right;">
				发布时间（开始）：
			</td>
			<td width="35%">
				<input class="inputsearch" name="model.startTime" id="model.startTime"
					type="text" size="15" value="<s:date name="model.startTime" format="yyyy-MM-dd"/>"
					class="Wdate" onClick="WdatePicker()" readonly >
			</td>
			<td width="15%" style="text-align: right;">
				发布时间（至）：
			</td>
			<td width="35%">
				<input class="inputsearch" name="model.entTime" id="model.entTime"
					type="text" size="15" value="<s:date name="model.entTime" format="yyyy-MM-dd"/>"
					class="Wdate" onClick="WdatePicker()" readonly >
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<img src="${e:basePath()}images/report/query.gif" onclick="toSubmit('query')" style="cursor: pointer;">
			</td>
		</tr>
		<tr><td colspan="4">&nbsp;</td></tr>
	</table>
</td>
</tr>
</table>
<div id="scroll_box_bar" class="scroll_box_bar">
<table id="scroll_box_content" class="tablelistcontent" width="100%" border="1" cellspacing="1">
    <tr>
    	<%-- 查询列表标题模版 请修改标题--%>
        <th width="5%" height="25"><input type="checkbox" onclick="doSelectAll('ids', this)"></th>
		<th width="5%" height="25">序号</th>
		<th width="12%">分类</th>
		<th width="12%">标题</th>
		<th width="12%">内容</th>
		<!-- <th width="12%">内容</th> -->
		<th width="8%">类型</th>
		<th width="8%">审核状态</th>
		<th width="10%">发表人</th>
		<th width="15%">发布时间</th>
		<th width="15%">缩略图</th>
    </tr>
    	<%-- 模板，列表信息  --%>
	    <s:iterator id="item" value="proSendWbList" status="st" >   
	    <tr align="center" <s:if test="#st.even">bgcolor="#EEEEEE"</s:if> class="Off" onMouseOver="this.className='Up'" onMouseOut="this.className='Off'">
	        <td><input type="checkbox" name="ids" value="<s:property value='id'/>"></td>
			<td><s:property value="#st.count"/></td>   
			<td><s:property value="typeName"/></td>
			<td><s:property value="title"/></td>
			<td><textarea style="width: 100px; height: 25px"><s:property value="content"/></textarea> </td>      
			<!-- <td><s:property value="content"/></td>-->  
			<td><s:if test="isUpload ==  null || isUpload == 0">文本</s:if>
				<s:if test="isUpload == 1">图片</s:if>
				<s:if test="isUpload == 2">视频</s:if>
			</td>
			<td>
				<s:if test="verifyState == 0">已审核</s:if>
				<s:if test="verifyState ==  null || verifyState == 1">待审核</s:if>
				<s:if test="verifyState == 2">不通过</s:if>
			</td>
			<td><s:property value="nickName"/></td>
			<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			<td><a href="${e:pic(item.picUrl) }" target="_blank"><img src="${e:minPic(item.picUrl,"50_50") }" style="width:25px ;height: 25px;"/></a></td>  
		</tr>
	   </s:iterator>
	  
</table>
</div>
<table width="98%"  border="0" cellspacing="1" cellpadding="0" height="30">
    <tr>
        <td> <%-- 按钮--%>
		     <img src="${e:basePath()}images/report/add.gif" onclick="toSubmit('add')" style="cursor: pointer;">&nbsp;
		     <img src="${e:basePath()}images/report/edit.gif" onclick="toSubmit('edit')" style="cursor: pointer;">&nbsp;
		     <img src="${e:basePath()}images/report/delete.gif" onclick="toSubmit('del')" style="cursor: pointer;">&nbsp;
		     <img src="${e:basePath()}images/report/tgsh.gif" onclick="toSubmit('edit')" style="cursor: pointer;">&nbsp;
		     <img src="${e:basePath()}images/report/btgsh.gif" onclick="toSubmit('del')" style="cursor: pointer;">
		</td> 
		<%-- 分页面公共页面 --%>
		<td align="right"><%@ include file="/common/navigate.jsp"%></td>
	</tr>
</table>  
</form>
</center>
</body>
</html>
