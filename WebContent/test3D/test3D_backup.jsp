<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.itheima.utils.HTML_td"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.regex.Pattern" %>
<%@page import="java.util.regex.Matcher" %>
<%@page import="java.util.List"%> 
<%@page import="java.util.Arrays"%> 
<%@page import="com.sun.media.jfxmedia.events.NewFrameEvent"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="cn.itcast.goods.user.domain.User"%>
<%@page import="com.itheima.utils.CompratorByLastModified"%>
<%@page import="com.itheima.utils.CompratorByName"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="java.io.File"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.net.URLDecoder"%>
 <!DOCTYPE html>
<!-- saved from url=(0045)http://fiddle.jshell.net/z4sp5o90/show/light/ -->
<html> 
  
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>3D</title>
    <meta name="robots" content="noindex, nofollow">
    <meta name="googlebot" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
	<script type="text/javascript" src="../test3D/js/xtk_edge.js"></script>
    <link rel="stylesheet" type="text/css" href="../test3D/css/result-light.css">
    <script type="text/javascript" src="../test3D/js/xtk_xdat.gui.js"></script>
    
	<style id="compiled-css" type="text/css">html, body { background-color:#000; margin: 0; padding: 0; height: 100%; overflow: hidden !important; }</style>
    <!-- TODO: Missing CoffeeScript 2 -->
 
    <style type="text/css" media="screen">
	.progress-bar-horizontal { 
		position: relative; 
		border: 1px solid #949dad; 
		background: white; 
		padding: 1px; 
		overflow: hidden; 
		margin: 2px; 
		width: 100px; 
		height: 5px; }
	.progress-bar-thumb { 
		position: relative; 
		background: #F62217; 
		overflow: hidden; 
		width: 0%; 
		height: 100%; }
	.progress-bar-thumb-done { 
		background: #57E964; 
	}
	</style></head>
  
  <body>
  <%!File file = null;%>
    
   <%--  <%!String path = "E:\\dicom-examples\\md65";%> --%>
    <%!%>
    
 
    <%  
    //获取该目录下的所有文件和目录的文件名     

    String path=(String)session.getAttribute("path");
    System.out.println("path="+path);
    
    String path1;
    path1=path.substring(path.lastIndexOf('/')+1);
    System.out.println("path1="+path1);
    int length0= path1.length()+1;
    System.out.println(length0);
    int length1=path.length()-length0;
    System.out.println(length1);
    String path3=path.substring(0, length1);
    System.out.println("path3="+path3);
    String path4=path3.substring(path3.lastIndexOf('/')+1);
    System.out.println("path4="+path4);
    
    String pathname=(String)session.getAttribute("pathname");
    System.out.println("pathname="+pathname);
    String pathname1;
    pathname1=pathname.substring(pathname.lastIndexOf('/')+1);
    System.out.println("pathname1="+pathname1);

    
    
    
  /*   String path1=(String)session.getAttribute("path1");
    path3 =path1.substring(path1.lastIndexOf('\\')+1)+"\\\\";
    System.out.println("path3="+path1); */
    
    
    file = new File(path);
   
    String[] files = file.list();
    System.out.println("files   "+files.toString());
    

    String parentPath = file.getParent(); // null
    File parentDir = file.getParentFile(); // null
  

    for (int i = 0;i<files.length;i++){
        System.out.println(files[i]);
        
       /*  parentPath = file.getParent(); // D:\wzy */
      /*   parentDir = file.getParentFile(); // D:\wzy  */
        parentPath = file.getCanonicalPath();
        
        parentPath=parentPath.substring(parentPath.lastIndexOf('\\')+1)+"\\\\";
        
        System.out.println("parentPath="+parentPath);
        files[i]="\"http://localhost:8080\\\\test\\\\"+pathname1+"\\\\"+path4+"\\\\"+parentPath+files[i]+"\""; 
  /*       files[i]="http://localhost:8080\\\\try\\\\"+parentPath+files[i]; */
        System.out.println("f123  "+files[i]);
    }
    %>

     <%String str="[";
   for (int i=0; i<files.length; i++){ 
	   if(i!= files.length-1){
                     str= str+files[i]+",";
	   }
	   else {
		   str= str+files[i]+"]";
	   }
    }
    System.out.println(str);%>
     
     <%
     String nii="["+"\"D:\\train_Gist030v.nii"+"\""+"]";
     System.out.println("nii::"+nii);
     %>
     
    <!-- the container for the renderers -->
    <div id="3d" style="background-color: #000; width: 75%; height: 100%; margin-bottom: 2px; float: right; color:#FFFFFF;">3D</div>
    <div id="sliceX" style="border-top: 2px solid red; border-right: 1px solid #666666; background-color: #000; width: 24%; height: 33%; float: left; text-align:center; font-size:13px; color:#FFFFFF;">矢状面-X轴</div>
    <div id="sliceY" style="border-top: 2px solid green; border-right: 1px solid #666666; background-color: #000; width: 24%; height: 33%; float: left; text-align:center; font-size:13px; color:#FFFFFF;">冠状面-Y轴</div>
    <div id="sliceZ" style="border-top: 2px solid blue; border-right: 1px solid #666666; background-color: #000; width: 24%; height: 33%; float: left; text-align:center; font-size:13px; color:#FFFFFF;">横断面-Z轴</div>
    <script>// tell the embed parent frame the height of the content
      if (window.parent && window.parent.parent) {
        window.parent.parent.postMessage(["resultsFrame", {
          height: document.body.getBoundingClientRect().height,
          slug: "z4sp5o90"
        }], "*")
      }
    

      // always overwrite window.name, in case users try to set it manually
      window.name = "result"</script>
   <script type="text/javascript">//<![CDATA[
      window.onload = function() {

        //
        // try to create the 3D renderer
        //
        _webGLFriendly = true;
        try {
          // try to create and initialize a 3D renderer
          threeD = new X.renderer3D();
          threeD.container = '3d';
          threeD.init();
        } catch(Exception) {

          // no webgl on this machine
          _webGLFriendly = false;

        }

        //
        // create the 2D renderers
        // .. for the X orientation
        sliceX = new X.renderer2D();
        sliceX.container = 'sliceX';
        sliceX.orientation = 'X';
        sliceX.init();
        // .. for Y
        var sliceY = new X.renderer2D();
        sliceY.container = 'sliceY';
        sliceY.orientation = 'Y';
        sliceY.init();
        // .. and for Z
        var sliceZ = new X.renderer2D();
        sliceZ.container = 'sliceZ';
        sliceZ.orientation = 'Z';
    
    /* var volume = new X.volume();
    
    volume.file = _dicom.sort().map(function(v) {
    	  return 'http://x.babymri.org/?' + v + '&.DCM';
    });
 */
      // create a new test_renderer
     // we create the X.volume container and attach all DICOM files
     var volume = new X.volume();
 		volume.load('vol.nrrd');
     // map the data url to each of the slices

     volume.file=<%=str%>; 
     <%-- volume.file=<%=nii%>;  --%>
    

     
/*    /*  volume.file = ["http://x.babymri.org/?53320924&.DCM","http://x.babymri.org/?53321068&.DCM","http://x.babymri.org/?53325599&.DCM","http://x.babymri.org/?53325743&.DCM"];
  */ 
 
    <%-- <%for (int i=0; i<files.length; i++){ %>
   volume.file[<%=i%>] = <%=files[i]%>;
   <%}%>  --%>

		
        sliceX.add(volume);

        // start the loading/rendering
        sliceX.render();

        //
        // THE GUI
        //
        // the onShowtime method gets executed after all files were fully loaded and
        // just before the first rendering attempt
        sliceX.onShowtime = function() {

          //
          // add the volume to the other 3 renderers
          //
          sliceY.add(volume);
          sliceY.render();
          sliceZ.add(volume);
          sliceZ.render();

          if (_webGLFriendly) {
            threeD.add(volume);
            threeD.camera.position = [-150, -50, 700];
            threeD.camera.up = [0, 0, -1];
            threeD.render();
          }

          // now the real GUI
          var gui = new dat.GUI();

          // the following configures the gui for interacting with the X.volume 以下配置用于与X.volume交互的gui
          var volumegui = gui.addFolder('Volume');
          // now we can configure controllers which..
          // .. switch between slicing and volume rendering
          //现在我们可以配置控制器了
           // ..在切片和体积渲染之间切换
      
          var vrController = volumegui.add(volume, 'volumeRendering');
          // .. configure the volume rendering opacity // ..配置体积渲染的不透明度
          var opacityController = volumegui.add(volume, 'opacity', 0, 1);
          // .. and the threshold in the min..max range ..和阈值在min..max范围内
          var lowerThresholdController = volumegui.add(volume, 'lowerThreshold', volume.min, volume.max);
          var upperThresholdController = volumegui.add(volume, 'upperThreshold', volume.min, volume.max);
          var lowerWindowController = volumegui.add(volume, 'windowLow', volume.min, volume.max);
          var upperWindowController = volumegui.add(volume, 'windowHigh', volume.min, volume.max);
          // the indexX,Y,Z are the currently displayed slice indices in the range // indexX，Y，Z是当前显示的范围内的切片索引
          // 0..dimensions-1
          var sliceXController = volumegui.add(volume, 'indexX', 0, volume.dimensions[0] - 1);
          var sliceYController = volumegui.add(volume, 'indexY', 0, volume.dimensions[1] - 1);
          var sliceZController = volumegui.add(volume, 'indexZ', 0, volume.dimensions[2] - 1);
          
          volumegui.open();

        };
       
      };
      InitializeAllParameters();// Not executed!!!!!!!!
      
      //]]>
      </script>
</html>
