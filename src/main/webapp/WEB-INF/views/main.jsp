<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<style>

	.image_main{
        position: relative;
        background-image: url(/onlyKeyboardShop/resources/main/main01.jpg);                                                               
        height: 100vh;
        background-size: cover;
    }

    .img-cover_main{
       position: absolute;
       top: 10%;
       left: 10%;
       height: 300;
       width: 600;
       background-color: rgba(0, 0, 0, 0.7);                                                                 
       z-index:1;
    }

    .image_main .content_main{
         position: absolute;
         width : 100%;
         top:50%;
         left:50%;
         transform: translate(-50%, -50%);                                                                   
         font-size:2rem;
         color: white;
         z-index: 2;
         text-align: center;
    }

</style>

</head>
<body>

<div class="image_main">

<div class="img-cover_main">
<div class="content_main">Only Keyboard Shop�Դϴ�<br> ����� �����ΰ� ��ǰ���� �Ҹ���<br> ���� �� �ֽ��ϴ�.</div>
</div>

</div>
<br>
<!-- <img src="/onlyKeyboardShop/resources/main/main01.jpg" width="100%"/> -->

</body>
</html>