package com.big.web.b2b_big2.webapp.controller.flight.b2b.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;

@Controller
public class MyController {
    public String doSomething() {
        return "example" ;
    }
    public List<BeanData> collectData() {
    	List<BeanData> list = new ArrayList<BeanData>();
    	
    	for (int i = 0; i <10; i++){
    		BeanData data = new BeanData();
    		data.setEpisode("Episode#" + i);
    		data.setName("Name#" + i);
    		data.setShow("Show#" + i);
    		data.setAirDate(new Date().toString());
    		list.add(data);
    	}
    	
    	return list;
    }
}
