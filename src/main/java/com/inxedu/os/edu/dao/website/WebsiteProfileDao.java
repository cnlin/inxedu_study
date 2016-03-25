package com.inxedu.os.edu.dao.website;


import java.util.List;

import com.inxedu.os.edu.entity.website.WebsiteProfile;

/**
 * 网站配置
 * @author www.inxedu.com
 */
public interface WebsiteProfileDao {
	/**
	 * 根据type查询网站配置
	 */
	public WebsiteProfile getWebsiteProfileByType(String type);

	/**
	 * 更新网站配置管理
	 */
	public void updateWebsiteProfile(WebsiteProfile websiteProfile);

	/**
	 * 查询网站配置
	 */
	public List<WebsiteProfile> getWebsiteProfileList();
}