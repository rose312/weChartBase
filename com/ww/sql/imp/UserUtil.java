/**
 * 
 */
package com.ww.sql.imp;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangde
 * 
 */
public interface UserUtil {
	public StringBuffer getUsers(HttpServletRequest request);

	public StringBuffer getUserbyId(HttpServletRequest request, String id);

}
