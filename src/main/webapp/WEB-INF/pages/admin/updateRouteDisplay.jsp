<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="display.title"/></title>
    <meta name="menu" content="AdminMenu"/>
</head>

<div class="col-sm-10">
    <h2>Route Information</h2>
    <p>Below is a list of updated Route</p>

<!--     <table class="table-striped" cellpadding="5"> -->
<!--         <tr> -->
<!--             <th>Friendly Name:</th> -->
<%--             <td><c:out value="${friendlyName}"/></td> --%>
<!--         </tr> -->
<!--         <tr> -->
<!--             <th>Filename:</th> -->
<%--             <td><c:out value="${fileName}"/></td> --%>
<!--         </tr> -->
<!--         <tr> -->
<!--             <th>File content type:</th> -->
<%--             <td><c:out value="${contentType}"/></td> --%>
<!--         </tr> -->
<!--         <tr> -->
<!--             <th>File size:</th> -->
<%--             <td><c:out value="${size}"/></td> --%>
<!--         </tr> -->
<!--         <tr> -->
<!--             <th class="tallCell">File Location:</th> -->
<!--             <td>The file has been written to: <br /> -->
<%--                 <a href="<c:out value="${link}"/>"><c:out value="${location}" escapeXml="false"/></a> --%>
<!--             </td> -->
<!--         </tr> -->
<!--     </table> -->
    <div id="actions" class="form-group">
        <a class="btn btn-primary" href="updateroute" >
            <i class="icon-ok icon-white"></i>
            <fmt:message key="button.done"/>
        </a>
   
    </div>
</div>


