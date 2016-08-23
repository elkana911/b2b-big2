package com.big.web.b2b_big2.webapp.controller.finance;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;

@Controller
public class FinanceController extends BaseFormController {

	@RequestMapping(value = "/b2b/financeInfo", method = RequestMethod.GET)
	public @ResponseBody List<AppInfo> getFinanceInfo(){
    	return financeManager.getAppInfo();
    }

}
