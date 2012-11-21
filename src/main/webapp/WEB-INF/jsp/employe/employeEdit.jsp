<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/public.css">
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.7.min.js"></script>
	<script type="text/javascript">
         //保存
	     function toSubmit(){
	    	 var theForm = document.form1;
	    	 var name = $("input[name='name']").val();
	    	 var age = $("input[name='age']").val();
	    	 if(name == ''){
	    	 	alert("姓名不能为空");
	    	 	return;
	    	 }
	    	 if(age == ''){
	    	 	alert("年龄不能为空");
	    	 	return;
	    	 }
	    	 if(isNaN(age)){
	    	 	alert("年龄只能输入数字");
	    	 	return;
	    	 }
	    	 if(age.length >3){
	    	 	alert("年龄长度不能超过3位数字");
	    	 	return;
	    	 }
	    	 theForm.submit();  
	     }
	   </script>
  </head>
<body>
<center>

	<form id="form1" name="form1" action="<%=basePath%>employe/save.do" method="post">
	<input type="hidden" name="id" value="${bean.id}"> <br>
	姓名:<input type="text" name="name" value="${bean.name}" style="width: 150px"> <br>
	性别:<select name="sex" style="width: 150px">
			<option value="0" <c:if test="${bean.sex == 0}">selected</c:if>>保密</option>
			<option value="1" <c:if test="${bean.sex == 1}">selected</c:if>>男</option>
			<option value="2" <c:if test="${bean.sex == 2}">selected</c:if>>女</option>
		</select>
		<br>
	年龄:<input type="text" name="age" value="${bean.age}" style="width: 150px"> <br>
	备注:<input type="text" name="remark" value="${bean.remark}" style="width: 150px"><br>
		
		<img src="<%=basePath%>images/report/save.gif" onclick="toSubmit();" style="cursor: pointer;">&nbsp;
		<img src="<%=basePath%>images/report/fh.gif" onclick="window.history.go(-1);" style="cursor: pointer;">
	</form>
</center>
</body>
</html>