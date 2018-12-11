package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.board.controller.Action;
import net.board.controller.ActionForward;
import net.board.db.FanDAO;

public class FanModify implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("FanModify execute()");
		request.setCharacterEncoding("utf-8");
		
		int fa_num = Integer.parseInt(request.getParameter("fa_num")); 
		String pageNum =request.getParameter("pageNum");
		
		System.out.println("fa_num은?"+fa_num);
		
		HttpSession session = request.getSession();
		String mem_num = (String)session.getAttribute("mem_num");
		
		System.out.println("mem_num은?"+mem_num);
		
		FanDAO fdao = new FanDAO();
		
		ActionForward forward = new ActionForward();
		forward.setPath("./board/fan_modifyPage.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
