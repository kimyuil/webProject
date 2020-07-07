package com.spring.webProject.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.webProject.command.ICommand;
import com.spring.webProject.command.membership.DeleteUserInfoCommand;
import com.spring.webProject.command.membership.ModifyUserInfoCommand;


@Controller
public class ControllerMypageOthers {

	ICommand command;
	
	@Autowired
	private SqlSession sqlSession;
	

	//��ٱ���
	@RequestMapping(value = "/basket", method = RequestMethod.GET)
	public String basketShow(Model model) {
		System.out.println("basketShow");
		
		return "membership/basketPage";
	}
	

	//ȸ������ȭ��
	@RequestMapping(value = "/member/modifyInfo", method = RequestMethod.GET)
	public String modifyInfo(Model model) {
		System.out.println("modifyInfo");
		return "membership/mypage/modifyInfoView";
	}
	//ȸ������ ���� ȭ��
	@RequestMapping(value = "/member/modifyUserView", method = RequestMethod.GET)
	public String modifyUserView(Model model) {
		System.out.println("modifyUserView");
		return "membership/mypage/modifyUserView";
	}
	
	//ȸ������ ���� ȭ�� db�۾�!
	@RequestMapping(value = "/doModifyUserInfo", method = RequestMethod.POST)
	public String doModifyUserInfo(HttpServletRequest request, Model model) throws Exception {
		System.out.println("doModifyUserInfo");
		
		model.addAttribute("uPhone", request.getParameter("uPhone"));
		model.addAttribute("uEmail", request.getParameter("uEmail"));
		model.addAttribute("uAdress", request.getParameter("uAdress"));
		model.addAttribute("uId", request.getParameter("uId"));
		model.addAttribute("uPw", request.getParameter("uPw"));
		
		command = new ModifyUserInfoCommand();
		command.execute(sqlSession, model);
				
		return "membership/mypage/modifyInfoComplete";
		
	}
	
	//ȸ��Ż��ȭ��
	@RequestMapping(value = "/member/deleteInfo", method = RequestMethod.GET)
	public String deleteInfo(Model model) {
		System.out.println("deleteInfo");
		
		return "membership/mypage/deleteInfo";
	}
	//ȸ��Ż�� DB�۾�
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteUser(HttpServletRequest request, Model model) throws Exception {
		System.out.println("deleteUser");
		
		model.addAttribute("uId",request.getParameter("uId"));
		model.addAttribute("uPw",request.getParameter("uPw"));
		command = new DeleteUserInfoCommand();
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
		String result = (String) map.get("result");
		
		return result=="success" ? "membership/mypage/deleteInfoComplete" 
				: "membership/mypage/deleteInfo";  
		
		
		
	}
}
