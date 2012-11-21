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
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.7.min.js"></script>
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
			    	theForm.action="<%=basePath%>employe/deletes.do";
	    			theForm.submit();
	    			//deleteWb(theForm);
			    } 
	    	} 
	    }
	   </script>
  </head>
 <body>
<center>
<form name="form1" method="post" action="<%=basePath%>employe/list.do">
  
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tit" align="left"><strong>&nbsp;系统管理&gt;&gt;员工管理</strong></td>
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
				姓名：
			</td>
			<td width="35%">
				<input class="inputsearch" type="text" id="name" name="name"
					value="<c:out value="${model.name}" />" size="15" maxlength="15" />
			</td>
			<td width="15%" style="text-align: right;">
				性别：
			</td>
			<td width="35%">
				<select class="inputsearch" name="sex">
					<option value="" >----</option>
					<option value="0" <c:if test="${model.sex == 0}">selected</c:if>>保密</option>
					<option value="1" <c:if test="${model.sex == 1}">selected</c:if>>男</option>
					<option value="2" <c:if test="${model.sex == 2}">selected</c:if>>女</option>
					
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<img src="<%=basePath%>images/report/query.gif" onclick="toSubmit('query')" style="cursor: pointer;">
			</td>
		</tr>
		<tr><td colspan="4">&nbsp;</td></tr>
	</table>
</td>
</tr>
</table>
<table id="scroll_box_content" class="tablelistcontent" width="98%" border="1" cellspacing="1">
    <tr>
    	<%-- 查询列表标题模版 请修改标题--%>
        <th width="5%" height="25"><input type="checkbox" onclick="doSelectAll('ids', this)"></th>
		<th width="5%" height="25">序号</th>
		<th width="12%">姓名</th>
		<th width="12%">性别</th>
		<th width="12%">年龄</th>
		<th width="8%">备注</th>
    </tr>
     <c:forEach items="${resultList}"  var="item" varStatus="st">
	    <tr align="center" <c:if test="${st.count%2==0}">bgcolor="#EEEEEE"</c:if> class="Off" onMouseOver="this.className='Up'" onMouseOut="this.className='Off'">
	        <td><input type="checkbox" name="ids" value="${item.id}"></td>
			<td><c:out value="${st.count}"/></td>   
			<td><c:out value="${item.name}"/></td>
			<td><c:if test="${item.sex == 0}">保密</c:if>
				<c:if test="${item.sex == 1}">男</c:if>
				<c:if test="${item.sex == 2}">女</c:if>
			</td>
			<td><c:out value="${item.age}"/></td>
			<td><c:out value="${item.remark}"/></td> 
		</tr>
	   </c:forEach>
</table>
<table width="98%"  border="0" cellspacing="1" cellpadding="0" height="30">
    <tr>
        <td> <%-- 按钮--%>
		     <img src="<%=basePath%>images/report/add.gif" onclick="toSubmit('add')" style="cursor: pointer;">&nbsp;
		     <img src="<%=basePath%>images/report/edit.gif" onclick="toSubmit('edit')" style="cursor: pointer;">&nbsp;
		     <img src="<%=basePath%>images/report/delete.gif" onclick="toSubmit('del')" style="cursor: pointer;">&nbsp;
		</td> 
		<%-- 分页面公共页面 --%>
		<td align="right"><%@ include file="../common/navigate.jsp"%></td>
	</tr>
</table>  
</form>
</center>
</body>
</html>