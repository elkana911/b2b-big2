package com.big.web.b2b_big2.webapp.controller.guide;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GuideController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/guide/tac")
	public String loadTAC(HttpServletRequest request, HttpServletResponse response) {
		
		return "/guide/tac";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/guide/topup")
	public String loadTopUp(HttpServletRequest request, HttpServletResponse response) {
		
		return "/guide/topup";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/guide/payment")
	public String loadPayment(HttpServletRequest request, HttpServletResponse response) {
		
		return "/guide/payment";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/guide/reservation")
	public String loadReservation(HttpServletRequest request, HttpServletResponse response) {
		
		return "/guide/reservation";
	}

}
