package com.big.web.b2b_big2.webapp.controller.agent;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;

@Controller
public class AgentController extends BaseFormController{

    protected final transient Log log = LogFactory.getLog(getClass());

	/**
	 * 300 x 50
	 * @param imagePath
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value = "/agent/getLogo", method = RequestMethod.GET)
	protected void getImage(@RequestParam(required=true) final  String imagePath, HttpServletResponse response) throws IOException {	
		
		String cacheDir = Utils.includeTrailingPathDelimiter(setupDao.getValue(ISetupDao.KEY_LOGO));
		
		Utils.getImage(cacheDir + imagePath, response);
/*		
		File file = new File(cacheDir + imagePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		byte[] bytes = bos.toByteArray();
		response.setContentType("image/" + StringUtils.getFilenameExtension(imagePath));
		response.setContentLength(bytes.length);	     
		try {
			response.getOutputStream().write(bytes);
			response.getOutputStream().flush();
		} catch (IOException e) {
			// Handle the exceptions as per your application.
			e.printStackTrace();
		}
		*/
	}

    @RequestMapping(value = "/agent/agencyInfo", method = RequestMethod.GET)
	public @ResponseBody List<AppInfo> getAgencyInfo(){
    	return agentManager.getAppInfo();
    }

}
