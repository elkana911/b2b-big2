<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<script type="text/javascript">
	
		function startProgress()
		{
			//alert("in startProgress");
			if(document.getElementById('file1').value=="")
			return;
			//document.getElementById('btn_upload').style.display = 'none';
			//document.getElementById('file1').style.display = 'none';
			document.getElementById('div_file').style.display = 'none';
			document.getElementById('progressBar').style.display = 'block';
			//document.getElementById('progressBarText').innerHTML = 'upload in progress: 0%';
			document.getElementById('progressBarText').innerHTML = "0" + '%';
			//document.getElementById('btn_upload').disabled = true;
			var_timeout = window.setTimeout("refreshProgress()", 1500);
			return true;
		}
	
	
		function refreshProgress()
		{
			UploadMonitor.getUploadInfo(updateProgress);
		}
		
		
		//Following javascript function is a callback functions that will be invoked by DWR.
		function updateProgress(uploadInfo)
		{ //alert("in updateProgress.... uploadInfo = "+uploadInfo);
			//alert("uploadInfo.inProgress = "+uploadInfo.inProgress);
			if(document.getElementById('upload_id').value == ''){
				document.getElementById('upload_id').value = uploadInfo.id;
				//alert(document.getElementById('upload_id').value);
			}

			if (uploadInfo.inProgress)
			{
				//document.getElementById('uploadbutton').disabled = true;
				var fileIndex = uploadInfo.fileIndex;
				var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);
				//document.getElementById('progressBarText').innerHTML = 'upload in progress: ' + progressPercent + '%';
				document.getElementById('progressBarText').innerHTML = progressPercent + '%';
				document.getElementById('progressBarBoxContent').style.width = parseInt(progressPercent * 3.5) + 'px';
				var_timeout = window.setTimeout("refreshProgress()", 1000);
			}else{
				//document.getElementById('uploadbutton').disabled = false;
				//alert("uploadInfo.id = "+uploadInfo.id);
				document.getElementById('progressBarBoxContent').style.width = "350px";
				//document.getElementById('progressBarText').innerHTML = 'upload in progress: ' + "100" + '%';
				document.getElementById('progressBarText').innerHTML = "100" + '%';
				var fileName = document.getElementById('file1').value;
				//document.getElementById('status').innerHTML = document.getElementById('file1').value;
				/*var div_str = "
				link - "+fileName+"";
				div_str = div_str + " Remove
				";
				*/
				document.getElementById('status').innerHTML = document.getElementById('status').innerHTML
					+ getUploadedDiv(uploadInfo.id, fileName);
				if(document.getElementById('file1'))
				document.getElementById('div_file').removeChild(document.getElementById('file1'));
				/*if(document.getElementById('btn_upload'))
				document.getElementById('form1').removeChild(document.getElementById('btn_upload'));
				if(document.getElementById('progressBar'))
				document.getElementById('form1').removeChild(document.getElementById('progressBar'));*/
				document.getElementById('progressBar').style.display = "none";
				document.getElementById('progressBarBoxContent').style.width = "0px";
				document.getElementById('div_attach_first').style.display = "";
				//document.getElementById('div_attach').style.display = "";
				//alert("done");
			}
			return true;
		}
</script>
</body>
</html>