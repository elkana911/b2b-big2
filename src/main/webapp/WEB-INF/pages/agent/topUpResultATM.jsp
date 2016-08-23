<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="topup.title"/></title>
    <meta name="menu" content="TopUpMenu"/>
</head>

<div class="row">
<div class="col-sm-12">
    <h2><fmt:message key="topup.heading"/></h2>
     <p><fmt:message key="topup.topup_success"/></p>
</div>
</div>

<div class="row">
<div class="col-sm-12">
<%-- <fmt:setLocale value="en_ID"/> --%>
<!-- TopUp [paymentMethod=1, userName=elkana, amount=2, VAN=1019-0298-7000-0001, bankCodeVA=022, bankCodeATM=014, adminFee=0 (FREE), total=2.000.000]  -->
<fmt:message key="topup.info.todo"/>
    <ul>
<%--       <li>Virtual Account Number :   ${topup_atm.account_no}</li> --%>
      <li>Transaction Code :<b>   ${topup_atm.trans_code}</b></li>
      <li>Bank Code : ${topup_atm.bankCode} ( ${bankName} )</li>
<%--       <li>Top Up Amount   			: ${topup_atm.amount }</li> --%>
<%--       <li>Admin Fee   			: ${topup_atm.adminFee } </li> --%>
      <li><b>Unique Code : ${topup_atm.uniqueCode}</b></li>
      <li><b>Total Amount : ${pay}</b></li>
      <li><b>Time To Paid: ${topup_atm.timeToPay}</b></li>
    </ul>
      <br />
      For the payment steps, please refer to Payment Guide in Help menu.</div>

</div>
</div>

