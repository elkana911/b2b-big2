package com.big.web.b2b_big2.webapp.controller.feeds;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.big.web.b2b_big2.feeds.model.FeedsVO;
import com.big.web.b2b_big2.feeds.service.IFeedManager;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Utils;


@Controller
public class NewsController {
	
    @Autowired
    ISetupDao setupDao;

    @Autowired
	private IFeedManager feedMgr;
	
    @RequestMapping(value = "/feeds/getHeadlines", method = RequestMethod.GET, headers="Accept=*/*")
    public @ResponseBody List<FeedsVO> getHeadlines(){
    	
    	List<FeedsVO> headlines = feedMgr.getHeadlines();
    	
    	return headlines;
    }

    @RequestMapping(value = "/admin/newslist", method = RequestMethod.GET)
    public ModelAndView showList(@RequestParam(required = false, value = "q")String query) throws Exception{
    	Model model = new ExtendedModelMap();
    	
    	try {
    		List<FeedsVO> list = feedMgr.search(query);
    		
    		model.addAttribute("newsList", list);
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new ModelAndView("/admin/newsList", model.asMap());
    }

    /*
     * H2U:
  	 * <img class="img-thumbnail" src="<c:url value='/feeds/getThumb?imagePath=${thumbId}' />" style="height:100px;"/>
  	 * 
  	 * di javascript :  var thumb = "${pageContext.request.contextPath}/feeds/getThumb?imagePath=" + item.thumbId; 

     */
	@RequestMapping(value = "/feeds/getThumb", method = RequestMethod.GET)
	protected void getImage(@RequestParam(required=true) final  String imagePath, HttpServletResponse response) throws IOException {	
		
		String cacheDir = Utils.includeTrailingPathDelimiter(setupDao.getValue(ISetupDao.KEY_NEWS_THUMB));
		//hopefully if imagePath is null, return dummy image
		Utils.getImage(cacheDir + imagePath, response);
	}
}
