<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>


<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<sec:authorize access="isAuthenticated()">
<sec:authentication property="principal.username" var="currentUserName"/> 
</sec:authorize>

<script>
var reviewsList = new Array();
var qnaList = new Array();

var currentUserName= "${currentUserName}"

$(document).ready(function(){
	getReviewList();
	showReviewList();
	
	QNAList();
	showQNAList()
});


function showQNAList(){ //qna 게시판 뿌려주는 메소드
	$('#qnaTable').html(
			'<tr>'+
			'<td style="width:50px;text-align: center; background-color:#dedede" ><b>번호</b></td>'+
			'<td style="width:80px;text-align: center;background-color:#dedede"><b>작성자</b></td>'+
			'<td style="text-align: center;background-color:#dedede"><b>제목</b></td>'+
			'<td style="width:100px; text-align: center;background-color:#dedede"><b>날짜</b></td>'+
			'<td style="width:100px; text-align: center;background-color:#dedede"><b>답변</b></td>'+
		'</tr>');
	
	
	for(var i=0 ; i<qnaList.length ;i++){ //list 페이지 내용
				
    	$('#qnaTable').append("<tr class='tr"+i+"'>"+
    		"<td style='text-align: center;'>"+(Number(i)+1)+"</td>"+
    		"<td style='text-align: center;'>"+qnaList[i].uName+"</td>"+
    		"<td><a href='javascript:void(0)' onclick='showContent("+i+")'>"+qnaList[i].qnaTitle+"</a></td>"+
    		"<td style='text-align: center;'>"+qnaList[i].qnaDate +"</td>"+
    		"<td style='text-align: center;'>"+qnaList[i].isAnswered +"</td></tr>"
    	);
   } 
}


function QNAList(){ //QNA 게시판 리스트
	$.ajax({
	    url: "/onlyKeyboardShop/userQnaList",
	    type: "POST",
	    cache: false,
	    dataType : "json",
	    async: false,
	    data: {"uId" : currentUserName},
	    success: function(data){
	    	
	    	//후기게시판, 페이징정보 가져오기
	    	for(var i =0;i<data.qnas.length;i++){
	    		
	    		var isSecret=false;
	    		var isAnswered="답변대기중";
	    		if (data.qnas[i].isSecret==1) isSecret=true;
	    		if (data.qnas[i].isAnswered==1) isAnswered="<b>답변완료</b>";
	    		
	    		var date = new Date(data.qnas[i].qnaDate);
	    		var dateString = date_to_str(date);
	    		
	    		var item = {qnaId:data.qnas[i].qnaId, uId:data.qnas[i].uId, 
	    		pId:data.qnas[i].pId, pName:data.qnas[i].pName, qnaTitle:data.qnas[i].qnaTitle, 
	    		qnaContent:data.qnas[i].qnaContent,	qnaDate:dateString,  
	    		isSecret:isSecret, isAnswered: isAnswered,
	    		qnaAnswer:data.qnas[i].qnaAnswer, uName:data.qnas[i].uName};
	    		
	    		qnaList.push(item);
	    	};
	    },	    
	  });
}

function date_to_str(format){
    var year = format.getFullYear();
    var month = format.getMonth() + 1;
    if(month<10) month = '0' + month;
    var date = format.getDate();
	if(date<10) date = '0' + date;
    var hour = format.getHours();
    if(hour<10) hour = '0' + hour;
    var min = format.getMinutes();
    if(min<10) min = '0' + min;
    var sec = format.getSeconds();
    if(sec<10) sec = '0' + sec;
	return year + "-" + month + "-" + date; // + " " + hour + ":" + min + ":" + sec
}

function showContent(id){ //toggle content view
	
	if($('.tr'+id).attr('content')==undefined){
		if(qnaList[id].isAnswered=="<b>답변완료</b>"){ //답변이 달렸을때
			$('.tr'+id).after("<tr><td colspan='5' style='height:100px;'>"+qnaList[id].qnaContent+
			"<br><br><b>답변</b><hr>"+qnaList[id].qnaAnswer+"<br>"+
			"<div align='right'>"+
			"<button class='btn btn-dark btn-sm' onclick='deleteQnA("+id+")'>삭제</button></div>"+
			"</td></tr>");
		} 
		else{ 
			$('.tr'+id).after(
				"<tr><td colspan='5' style='height:100px;'>"+
				qnaList[id].qnaContent+
				"<br><br><div align='right'>"+
				"<button class='btn btn-dark btn-sm' onclick='modifyQnA("+id+")'>수정</button> "+
				"<button class='btn btn-dark btn-sm' onclick='deleteQnA("+id+")'>삭제</button></div>"+
				"</td></tr>");
		}
		$('.tr'+id).attr('content',"true");
	}	
	else if($('.tr'+id).attr('content')=="true"){
		
		$('.tr'+id).next('tr').hide();
		$('.tr'+id).attr('content',"false");
	}
	else if($('.tr'+id).attr('content')=="false") {
		
		$('.tr'+id).next('tr').show();
		$('.tr'+id).attr('content',"true");
	} 
	
}

function modifyQnA(id){
	
	$('#QnaModifyView').append(
			"<input type='hidden' name='qnaId' value='"+qnaList[id].qnaId+"'/>"+
			"<input type='hidden' name='pId' value='"+qnaList[id].pId+"'/>"+
			"<input type='hidden' name='pName' value='"+qnaList[id].pName+"'/>"+
			"<input type='hidden' name='qnaTitle' value='"+qnaList[id].qnaTitle+"'/>"+
			"<input type='hidden' name='qnaContent' value='"+qnaList[id].qnaContent+"'/>"+
			"<input type='hidden' name='isSecret' value='"+qnaList[id].isSecret+"'/>"
			);
	$('#QnaModifyView').submit();
}

function deleteQnA(id){
	
	var check = confirm("정말삭제하시겠습니까?");
	if(check == false)
		return;
	$('#DeleteQna').append(
			"<input type='hidden' name='qnaId' value='"+qnaList[id].qnaId+"'/>"			
			);
	$('#DeleteQna').submit();
}


</script>

<script src="/onlyKeyboardShop/resources/js/myboard/myboardReview.js"></script>
<script src="/onlyKeyboardShop/resources/js/myboard/myboardQnA.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/menubar_top.jsp"%>
<br>
<h2 align="center">나의게시물</h2>
<br><br><br>


<div style='width:750px;margin:0 auto;'>
	<h4><b>작성한 후기</b></h4>
	<table style='width:100%' cellpadding="5" cellspacing="0" border="1" id="reviewTable">
	<tr><td>1</td><td>2</td></tr>
	<%-- <tr>
		<td style="width:50px;text-align: center;">No</td>
		<td style="width:100px;text-align: center;">image</td>
		<td>구매한 상품</td>
		<td style="width:120px; text-align: center;">구매날짜</td>
		<td style="width:80px;text-align: center;">배송조회</td>
	</tr>
	
	<c:forEach items="${beforeCheckList}" var="beforeList" varStatus="status">
	<tr>
		<td style='text-align: center;'>${status.index+1}</td>
		<td><img src="${beforeList.pImageRoute}" style="width:100%"/></td>
		<td>
			${beforeList.pName}(${beforeList.pColor})
		</td>
		<td style='text-align: center;'>${beforeList.purTime}</td>
		<td style='text-align: center;' id="beforeListState" >${beforeList.state}</td>
	</tr>
	</c:forEach> --%>
	</table>
</div>

<br><br>

<div style='width:750px;margin:0 auto;' >
	<h4><b>Q&A</b></h4>
	<table style='width:100%' cellpadding="5" cellspacing="0" border="1" id="qnaTable">
	</table>
</div>
<form id="QnaModifyView" method="post" action="userModifyQnaView"></form>
<form id="DeleteQna" method="post" action="/onlyKeyboardShop/userDeleteQna"></form>

<br><br>

<div style='width:750px;margin:0 auto;'>
	<h4><b>자유게시판 게시글</b></h4>
	<table style='width:100%' cellpadding="5" cellspacing="0" border="1" id="afterList">
	<tr><td>1</td><td>2</td></tr>
	</table>
</div>

<br><br>

<div style='width:750px;margin:0 auto;'>
	<h4><b>자유게시판 댓글</b></h4>
	<table style='width:100%' cellpadding="5" cellspacing="0" border="1" id="afterList">
	<tr><td>1</td><td>2</td></tr>

	</table>
</div>

<br><br><br><br><br><br><br>
<%@ include file="/WEB-INF/views/infobar_bottom.jsp"%>

</body>
</html>