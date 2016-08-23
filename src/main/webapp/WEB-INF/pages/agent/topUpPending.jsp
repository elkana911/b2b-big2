<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="topup.title"/></title>
    <meta name="menu" content="TopUpMenu"/>
</head>

<div class="row">
<div class="col-sm-12">
    <h2><fmt:message key="topup.headingPending"/></h2>
     <p><fmt:message key="topup.pendingMessage"/></p>
</div>
</div>

<%-- ${topup_pending} --%>
<div class="row">
<div class="col-sm-12">
<fmt:setLocale value="en_ID"/>
<!-- TopUpVO [id=2501ea26-eea5-4552-aca6-3d124b164886, account_no=1019029870000001, payment_type=1, lastUpdate=2015-03-09 16:12:05.0, bankCode=null, amountToPaid=2000000, status=P, timeToPay=2015-03-09 18:12:05.0] -->
	<c:choose> 
	  <c:when test="${topup_pending.payment_type == 1}" >
		    <ul>
		<%--       <li>Virtual Account Number :   ${topup_pending.account_no}</li> --%>
		      <li>Transaction Code :<b>   ${topup_pending.trans_code}</b></li>
		      <li>Bank Code : ${topup_pending.bankCode} ( ${bankName} )</li>
		<%--       <li>Top Up Amount   			: ${topup_pending.amount }</li> --%>
		<%--       <li>Admin Fee   			: ${topup_pending.adminFee } </li> --%>
		<%--       <li>Last Submit : ${topup_pending.lastUpdate}</li> --%>
		      <li><b>Unique Code : ${topup_pending.uniqueCode}</b></li>
		      <li><b>Total Amount : ${pay}</b></li>
		      <li><b>Time To Paid: ${topup_pending.timeToPay}</b></li>
		    </ul>
	  </c:when>
	  <c:otherwise>
		    <ul>
		      <li>Virtual Account Number :   <b>${topup_pending.account_no}</b></li>
		      <li>Transaction Code :<b>   ${topup_pending.trans_code}</b></li>
		      <li>Bank Code : ${topup_pending.bankCode} ( ${bankName} )</li>
<%-- 			  <li>Top Up Amount : ${topup_pending.amount }</li> --%>
		<%--       <li>Admin Fee   			: ${topup_pending.adminFee } </li> --%>
		<%--       <li>Last Submit : ${topup_pending.lastUpdate}</li> --%>
<%-- 		      <li><b>Unique Code : ${topup_pending.uniqueCode}</b></li> --%>
		      <li><b>Total Amount : ${pay}</b></li>
		      <li><b>Time To Paid: ${topup_pending.timeToPay}</b></li>
		    </ul>
	  </c:otherwise> 
	</c:choose> 

      <p>
      For the payment steps, please refer to Payment Guide in Help menu.</div>

</div>
</div>

