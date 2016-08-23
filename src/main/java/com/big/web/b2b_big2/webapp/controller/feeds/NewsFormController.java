package com.big.web.b2b_big2.webapp.controller.feeds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.helpers.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.big.web.b2b_big2.feeds.model.FeedsVO;
import com.big.web.b2b_big2.feeds.service.IFeedManager;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;

@Controller
public class NewsFormController extends BaseFormController{

	@Autowired
	private IFeedManager feedMgr;

	public NewsFormController() {
		setCancelView("redirect:/home");
		setSuccessView("redirect:/admin/newslist");
	}
	
	@InitBinder
	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		super.initBinder(request, binder);
//		binder.setDisallowedFields("password", "confirmPassword");		

	}
	
	private boolean isFormSubmission(final HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}
	
    protected boolean isAdd(final HttpServletRequest request) {
        final String method = request.getParameter("method");
        return (method != null && method.equalsIgnoreCase("add"));
    }

    @ModelAttribute
    @RequestMapping(value = "/admin/newsform", method = RequestMethod.GET)
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response)
    throws Exception {

		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/logout");
		}

        final String id = request.getParameter("id");
 
        // maksudnya formsubmission itu kalo lagi post(menyerahkan form)
        if (!isFormSubmission(request)) {

            FeedsVO feeds = null;
            
            if (!StringUtils.isBlank(id)) {
                feeds = feedMgr.get(id);            	
            } else {
                feeds = new FeedsVO();
            }

            return new ModelAndView("/admin/newsForm", "newsBean", feeds);
        } else {
        	//jika GET, ambil aja dr database
            // populate user object from database, so all fields don't need to be hidden fields in form
            return new ModelAndView("/admin/newsForm", "newsBean", feedMgr.get(id));
        }
    }

    private boolean uploadThumbFile(FeedsVO feed, HttpServletRequest request, BindingResult errors) throws IOException{
    	
    	String loc = setupDao.getValue(ISetupDao.KEY_NEWS_THUMB);
    	// Create the directory if it doesn't exist
    	Utils.createDirectory(loc);
    	
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    	CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("thumbfile");
    	
    	if (file.getSize() < 1)  return false;

		String contentType = file.getFileItem().getContentType();
		
		if (!contentType.toLowerCase().startsWith("image")) {
			errors.rejectValue("feeds.thumbId", "errors.image", new Object[] { getText("feeds.thumbId", request.getLocale()) },
					"Invalid image.");
			
			return false;
		}
			
		String ext = org.springframework.util.StringUtils.getFilenameExtension(file.getFileItem().getName());
		String htmlFile = java.util.UUID.randomUUID().toString() + "." + ext;
		
		log.debug("attempt to write web file " + loc + htmlFile);
		
		feed.setThumbId(htmlFile);
		
		//retrieve the file data
		InputStream stream = file.getInputStream();
		
		//write the file to the file specified
		OutputStream bos = new FileOutputStream(loc + htmlFile);
		int bytesRead;
		byte[] buffer = new byte[8192];
		
		while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}
		
		bos.close();
		
		//close the stream
		stream.close();

		return true;
    }
    
    @RequestMapping(value = "/admin/newsform", method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("newsBean") final FeedsVO feeds, final BindingResult errors, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
    	
        if (request.getParameter("cancel") != null) {
            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                return getCancelView();
            } else {
                return getSuccessView();
            }
        }
        
//        karena ga pake backingbean, validasi manual saja
        
        if (validator != null) {
        	validator.validate(feeds, errors);
        	
            if (feeds.getTitle() == null || StringUtils.isBlank(feeds.getTitle().trim())) {
                Object[] args =
                        new Object[]{getText("feeds.title", request.getLocale())};
                errors.rejectValue("title", "errors.required", args, "News Title");
            }
            
            if (feeds.getRemarks() == null || Utils.isEmpty(feeds.getRemarks().trim())){
            	Object[] args =
            			new Object[]{getText("feeds.remarks", request.getLocale())};
            	errors.rejectValue("remarks", "errors.required", args, "News Remarks");
            }else{
            	if (feeds.getRemarks().length() > FeedsVO.MAX_LEN_REMARKS){
            		errors.rejectValue("remarks", "errors.maxlength", new Object[]{getText("feeds.remarks", request.getLocale()), FeedsVO.MAX_LEN_REMARKS}, "News Remarks");
            	}
            	
            }
        	
        	//dont validate when deleting
        	if (errors.hasErrors() && request.getParameter("delete") == null) {
        		return "/admin/newsForm";
        	}
        	
        }
        
        Locale locale = request.getLocale();
        if (request.getParameter("delete") != null) {
        	feedMgr.remove(feeds.getId());
        	saveMessage(request, getText("feeds.deleted", feeds.getTitle(), locale));
        }else {
        	String oldThumb = null;
        	if (!Utils.isEmpty(feeds.getThumbId())){
            	String loc = setupDao.getValue(ISetupDao.KEY_NEWS_THUMB);
            	
        		oldThumb = loc + feeds.getThumbId();
        	}
        	
        	boolean uploadStatus = uploadThumbFile(feeds, request, errors);
        	
        	//repair external url
        	if (!Utils.isEmpty(feeds.getExternalUrl())){
        		if (!feeds.getExternalUrl().trim().toLowerCase().startsWith("http")){
        			feeds.setExternalUrl("http://" + feeds.getExternalUrl());
        		}
        	}
        	
        	feeds.setLastUpdate(new Date());
        	String key = null;
        	if (isAdd(request)) {
        		key = "feeds.added";
        		feedMgr.add(feeds);
        	}else {
        		key = "feeds.updated.byAdmin";
        		feedMgr.update(feeds);
        	}
        	
        	//if succeed, delete old thumb
    		if (oldThumb != null && uploadStatus){
    			FileUtils.delete(new File(oldThumb));
    		}

            saveMessage(request, getText(key, feeds.getTitle(), locale));
        }
        
        return getSuccessView();
    }
    
}
