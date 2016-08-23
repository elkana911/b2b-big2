<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="topup.title"/></title>
    <meta name="menu" content="TopUpMenu"/>
</head>

<div class="row">
<div class="col-sm-12">
    <h2><fmt:message key="topup.heading"/></h2>
<%--      <p><fmt:message key="topup.topup_success"/></p> --%>
</div>
</div>

<div class="row">
<div class="col-sm-12">
<%-- ${topup_va} --%>
<%-- <fmt:setLocale value="en_ID"/> --%>
<!-- TopUp [paymentMethod=1, userName=elkana, amount=2, VAN=1019-0298-7000-0001, bankCodeVA=022, bankCodeATM=014, adminFee=0 (FREE), total=2.000.000]  -->

<fmt:message key="topup.info.todo"/>
    <ul>
      <li><b>Virtual Account Number :   ${topup_va.VAN}</b></li>
<%--       <li>Transaction Code :<b>   ${topup_va.trans_code}</b></li> --%>
      <li>Bank Code : ${topup_va.bankCodeVA} ( ${bankName} )</li>
      <li>Minimum Top Up : ${topup_va.minimum}&nbsp;${topup_va.currency}</li>
      <li>Outstanding : ${topup_va.outstanding}&nbsp;${topup_va.currency}</li>
      <li>Balance : ${topup_va.balance}&nbsp;${topup_va.currency}</li>
<%--       <li>Top Up Amount   			: ${topup_atm.amount }</li> --%>
<%--       <li>Admin Fee   			: ${topup_atm.adminFee } </li> --%>
<%--       <li><b>Unique Code : ${topup_va.uniqueCode}</b></li> --%>
<%--       <li><b>Total Amount : ${pay}</b></li> --%>
<%--       <li><b>Time To Paid: ${topup_va.timeToPay}</b></li> --%>
    </ul>
      <br />
      For the payment steps, please refer to Payment Guide in Help menu.</div>

</div>
</div>

