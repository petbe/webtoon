package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.controller.Action;
import net.member.controller.ActionForward;

public class footerNoticewriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String category=request.getParameter("category");
		String nicontent=request.getParameter("ni_content");
		String nititle=request.getParameter("ni_title");
		
		
		return null;
	}

	
}