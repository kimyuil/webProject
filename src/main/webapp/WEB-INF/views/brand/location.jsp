<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>

<script type="text/javascript" src="http://map.vworld.kr/js/vworldMapInit.js.do?version=2.0&apiKey=77443487-FD58-35A0-8633-FB8E85DAB6C7"></script>

	

</head>
<body>

<%@ include file="/WEB-INF/views/menubar_top.jsp"%>


	<div style="width: 50%; float:left;">
		 <div class="btn-group">
			<button type="button" class="btn btn-dark" onclick="javascript:move(14124500.590359,4503100.7639686,13);" >ȸ����ġ</button>
  			<button type="button" class="btn btn-dark" onclick="javascript:move(14146300.590359,4527600.7639686,13);" >������ �б�</button>
   
		</div>
		 <div id="vmap" style="width:100%;height:400px;left:0px;top:0px"></div>
		 
		 <script> 
		 
		 camera = vw.ol3.CameraPosition;
		 camera.center = [14124500.590359,4503100.7639686];
		 camera.zoom=16;
		 
		 vw.ol3.MapOptions = {
			      basemapType: vw.ol3.BasemapType.GRAPHIC
			    , controlDensity: vw.ol3.DensityType.EMPTY
			    , interactionDensity: vw.ol3.DensityType.BASIC
			    , controlsAutoArrange: true
			    , homePosition: vw.ol3.CameraPosition
			    , initPosition: camera
			   }; 
			      
	   	var vmap = new vw.ol3.Map("vmap",  vw.ol3.MapOptions);
	   	
	   	var markerLayer = new vw.ol3.layer.Marker(vmap);
	    vmap.addLayer(markerLayer);
	    addMarker();
				 
		function addMarker() {
		 vw.ol3.markerOption = {
		  x : 126.882, //14123900.590359,4503100.7639686,13
		  y : 37.458,
		  epsg : "EPSG:4326",
		  title : 'ȸ����ġ',
		  contents : '���� ����ó�� ǥ���߽��ϴ�.',
		  iconUrl : 'http://map.vworld.kr/images/ol3/marker_blue.png', 
		  text : {
		      offsetX: 0.5, //��ġ����
		      offsetY: 20,   //��ġ����
		      font: '12px Calibri,sans-serif',
		      fill: {color: '#000'},
		      stroke: {color: '#fff', width: 2},
		      text: 'ȸ����ġ'
		  },
		  attr: {"id":"maker01","name":"�Ӽ���1"}    
		 };
		 markerLayer.addMarker(vw.ol3.markerOption);
		 
		 vw.ol3.markerOption = {
				  x : 127.0776, //14123900.590359,4503100.7639686,13
				  y : 37.63090,
				  epsg : "EPSG:4326",
				  title : '�б���ġ',
				  contents : '���� ������ �б��Դϴ�.',
				  iconUrl : 'http://map.vworld.kr/images/ol3/marker_blue.png', 
				  text : {
				      offsetX: 0.5, //��ġ����
				      offsetY: 20,   //��ġ����
				      font: '12px Calibri,sans-serif',
				      fill: {color: '#000'},
				      stroke: {color: '#fff', width: 2},
				      text: '�б���ġ'
				  },
				  attr: {"id":"maker01","name":"�Ӽ���1"}    
				 };
				 markerLayer.addMarker(vw.ol3.markerOption);
		 
		 this.markerLayer.showAllMarker();
		} 


		//document.write(vw.ol3.CameraPosition.center);
		function move(x,y,z){
		 var _center = [ x, y ];
		  
		 var z = z;
		 var pan = ol.animation.pan({
		  duration : 3000, //3�ʰ����ϸ��̼����� ��ġ�̵�
		  source : (vmap.getView().getCenter())
		 });
		 vmap.beforeRender(pan);
		 vmap.getView().setCenter(_center);
		 setTimeout("fnMoveZoom()", 4000);//4���� 15��ŭ zoom
		 
		 
		}
			   
		function fnMoveZoom() {
		  zoom = vmap.getView().getZoom();
		  if (16 > zoom) {
		  vmap.getView().setZoom(15); //���ڰ� Ŭ���� zoom
		  }
			   
		};
			 
			 
 </script>
	</div>
	
	<div style="width: 48%; float:right; margin-top: 20px; ">
		<h2>���ô� ��</h2>
		<br>
		���� only keyboard shop�� ȸ����ġ��,<br> �������� <u>���� ��� �� �ֺ�</u>���� ǥ���� �ξ����ϴ�.<br>
		���� ������ ��Ŀ�� ǥ���� �ξ����ϴ�.<br><br>
		���� ȸ���� ��ġ�� ����������, <b>ö�꿪</b>���� ������ �̵��� �� �ֽ��ϴ�. <br>
		���� ���� �뼱���� ���� ȸ�縦 �����ϰ� �ֽ��ϴ�. �׷��� <b>������ ��</b>�մϴ�.<br><br>
		�߰������� ���� ������ �б� ��ġ�� ǥ���� �ξ����ϴ�.. <br>
		���� <b>������б�����б��� ����</b>�Ͽ����ϴ�.<br>
		<u>���� ����� ��ư</u>�� ���ؼ� �ش� ��ġ�� �̵��Ͻ� �� �ֽ��ϴ�.<br><br>
		<b>������ Ű���� only keyboard shop!</b> ���� �ֿ����ֽñ� ��Ź�帳�ϴ�. �����մϴ�.
		
	</div>

<div style="clear:both;"></div>
<br>
<%@ include file="/WEB-INF/views/infobar_bottom.jsp"%>

</body>
</html>