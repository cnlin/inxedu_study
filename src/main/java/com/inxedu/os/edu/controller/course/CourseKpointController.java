package com.inxedu.os.edu.controller.course;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.edu.constants.enums.WebSiteProfileType;
import com.inxedu.os.edu.entity.course.Course;
import com.inxedu.os.edu.entity.kpoint.CourseKpoint;
import com.inxedu.os.edu.service.course.CourseKpointService;
import com.inxedu.os.edu.service.course.CourseService;
import com.inxedu.os.edu.service.website.WebsiteProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * CourseKpoint 课程章节管理
 * @author www.inxedu.com
 */
@Controller
public class CourseKpointController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CourseKpointController.class);


	private static final String getKopintHtml = getViewPath("/web/course/videocode");// 课程播放
	private static final String callBack56Uploading = getViewPath("/course/callBack56_uploading");//56视频上传回调

	@Autowired
	private CourseKpointService courseKpointService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private WebsiteProfileService websiteProfileService;
	
	/**
	 * 获得视频播放的html
	 * @return
	 */
	@RequestMapping("/front/ajax/getKopintHtml")
	public String getKopintHtml(Model model, HttpServletRequest request) {
		try {
			if(ObjectUtils.isNull(request.getParameter("kpointId"))){
				return getKopintHtml;
			}
			int kpointId = Integer.parseInt(request.getParameter("kpointId"));
			// 查询节点
			CourseKpoint courseKpoint = courseKpointService.queryCourseKpointById(kpointId);
			// 当传入数据不正确时直接返回
			if (ObjectUtils.isNull(courseKpoint)) {
				return getKopintHtml;
			}
						
			//获取课程
			Course course = courseService.queryCourseById(courseKpoint.getCourseId());
			if (course==null) {
				logger.info("++isok:" + false);
				return getKopintHtml;
			}

			// 视频url
			String videourl = courseKpoint.getVideoUrl();
			// 播放类型
			String videotype = courseKpoint.getVideoType();
			
			
			//查询cc的appId key
			if(courseKpoint.getVideoType().equalsIgnoreCase(WebSiteProfileType.cc.toString())){//如果cc
				Map<String,Object> map=websiteProfileService.getWebsiteProfileByType(WebSiteProfileType.cc.toString());
				model.addAttribute("ccwebsitemap", map);
			}
			//查询乐视的appId key
			if(courseKpoint.getVideoType().equalsIgnoreCase(WebSiteProfileType.letv.toString())){//如果乐视
    			Map<String,Object>	lmap=websiteProfileService.getWebsiteProfileByType(WebSiteProfileType.letv.toString());
    			model.addAttribute("websiteLetvmap", lmap);
			}
			
            //判断是否为手机浏览器
            boolean isMoblie = JudgeIsMoblie(request);
            model.addAttribute("isMoblie", isMoblie);
			// 放入数据
			model.addAttribute("videourl", videourl);
			model.addAttribute("videotype", videotype);
			return getKopintHtml;
		} catch (Exception e) {
			logger.error("CourseKpointController.getKopintHtml", e);
			return setExceptionRequest(request, e);
		}
	}


    //判断是否为手机浏览器
    public boolean JudgeIsMoblie(HttpServletRequest request) {
        boolean isMoblie = false;
        String[] mobileAgents = { "iphone", "android","ipad", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
                "Googlebot-Mobile" };
        if (request.getHeader("User-Agent") != null) {
            System.out.println("User-Agent:"+request.getHeader("User-Agent"));
            for (String mobileAgent : mobileAgents) {
                if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
                    isMoblie = true;
                    break;
                }
            }
        }
        return isMoblie;
    }

	/**
     * 获得56上传成功之后的回调
     */
    @RequestMapping("/api/56/callBack")
    public String callBack56Uploading(){
        return callBack56Uploading;
    }
}