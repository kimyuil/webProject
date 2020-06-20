package com.spring.webProject.controller;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.webProject.command.ChangePurchaseStateCommand;
import com.spring.webProject.command.DeleteUserReviewCommand;
import com.spring.webProject.command.FindIdCommand;
import com.spring.webProject.command.FindPwCommand;
import com.spring.webProject.command.GetUserReviewOneItemCommand;
import com.spring.webProject.command.GetUserReviewOneItemCommand;
import com.spring.webProject.command.ICommand;
import com.spring.webProject.command.IdCheckCommand;
import com.spring.webProject.command.JoinCommand;
import com.spring.webProject.command.LoginCommand;
import com.spring.webProject.command.ModifyReivewCommand;
import com.spring.webProject.command.OrderListCommand;
import com.spring.webProject.command.PageCommand;
import com.spring.webProject.command.PreventReWriteCheckCommand;
import com.spring.webProject.command.ProductCommand;
import com.spring.webProject.command.ProductPageCommand;
import com.spring.webProject.command.RenewPwCommand;
import com.spring.webProject.command.ReviewListCommand;
import com.spring.webProject.command.TestCommand;
import com.spring.webProject.command.UserCheckCommand;
import com.spring.webProject.command.UserCheckDeliveryCommand;
import com.spring.webProject.command.UserReviewListCommand;
import com.spring.webProject.command.WriteReivewCommand;
import com.spring.webProject.command.InsertReviewDataCommand;
import com.spring.webProject.dto.PageDto;
import com.spring.webProject.dto.PurchaseListDto;
import com.spring.webProject.dto.ReviewBoardDto;
import com.spring.webProject.dto.UserDto;


@Controller
public class ControllerMembership {

	ICommand command;
	
	@Autowired
	private SqlSession sqlSession;
	
	///test
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String test(HttpServletRequest request, Model model)throws Exception {
		System.out.println("test ����");
		
		System.out.println(request.getParameter("param1"));
		//mainDao=new TestDao();
		return "home";
	}
	
	//�������̵�
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		System.out.println("login");
		return "membership/login";
	}
	@RequestMapping(value = "/mypage", method = RequestMethod.GET)
	public String mypage(Locale locale, Model model) {
		System.out.println("mypage");
		return "membership/mypage/mypage";
	}
	
	//idã��
	@RequestMapping(value = "/findIdView", method = RequestMethod.GET)
	public String findIdView(Locale locale, Model model) {
		System.out.println("findIdView");
		
		return "membership/findId";
	}
	@RequestMapping(value = "/findId", method = RequestMethod.POST)
	public String findId(HttpServletRequest request, Model model)throws Exception {
		System.out.println("findId");
		command = new FindIdCommand();
		model.addAttribute("email",request.getParameter("email") );
		model.addAttribute("name",request.getParameter("name") );
		
		command.execute(sqlSession, model);
		
		return "membership/findResultId";
	}
	
	//pwã��
	@RequestMapping(value = "/findPwView", method = RequestMethod.GET)
	public String findPwView(Locale locale, Model model) {
		System.out.println("findPwView");
		
		return "membership/findPw";
	}
	@RequestMapping(value = "/findPw", method = RequestMethod.POST)
	public String findPw(HttpServletRequest request, Model model)throws Exception {
		System.out.println("findPw");
		
		command = new FindPwCommand();
		model.addAttribute("email",request.getParameter("email") );
		model.addAttribute("id",request.getParameter("id") );
		
		command.execute(sqlSession, model);
		
		return "membership/findResultPw";
	}
	@RequestMapping(value = "/renewPw", method = RequestMethod.POST)
	public String renewPw(HttpServletRequest request, Model model)throws Exception {
		System.out.println("findPw");
		
		command = new RenewPwCommand();
		model.addAttribute("pw",request.getParameter("uPw") );
		model.addAttribute("id",request.getParameter("id") );//����
				
		command.execute(sqlSession, model);
		
		return "membership/login";//����
	}
	
	//�α׾ƿ�
	@RequestMapping(value = "/member/home", method = RequestMethod.GET)
	public String logout(Locale locale, Model model) {
		System.out.println("logout");
		return "home";
	}
	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public String logout2(Locale locale, Model model) {
		System.out.println("logout2");
		return "home";
	}
	
	
	//ȸ������
	@RequestMapping(value = "/joinView", method = RequestMethod.GET)
	public String joinView(Locale locale, Model model) {
		System.out.println("joinView");
		
		return "membership/join";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserDto user, Model model) throws Exception {
		System.out.println("join");
		command = new JoinCommand();
		model.addAttribute("user",user);
		command.execute(sqlSession, model);

		return "redirect:login";
	}
	
	@ResponseBody  //ajax
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public String join(HttpServletRequest request, Model model)throws Exception {
		System.out.println("inCheck");
		
		command = new IdCheckCommand();
		
		String id = request.getParameter("uId");
		model.addAttribute("id", id);
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
		Integer result = (Integer) map.get("result");
		
		if(result == 1)
			return "success";
		else
			return "fail";

	}
		
	//���� �߸� ��������
	@RequestMapping(value = "/errorPage", method = RequestMethod.GET)
	public String errorPageView(Locale locale, Model model) {
		System.out.println("errorPageView");
		
		return "membership/errorPage";
	}
	

	//��ٱ���
	@RequestMapping(value = "/basket", method = RequestMethod.GET)
	public String basketShow(Model model) {
		System.out.println("basketShow");
		
		return "membership/basketPage";
	}
	

	@ResponseBody  //ajax �����Ҷ� ����Ȯ���ϴ� �۾�
	@RequestMapping(value = "/userCheck", method = RequestMethod.POST)
	public String userCheck(HttpServletRequest request, Model model)throws Exception {
		System.out.println("userCheck");
		
		command = new UserCheckCommand();
		
		String id = request.getParameter("uId");
		model.addAttribute("id", id);
		String pw = request.getParameter("uPw");
		model.addAttribute("pw", pw);
		
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
		Integer result = (Integer) map.get("result");
		
		if(result == 1) {
			System.out.println("success");
			return "success";
		}
		else {
			System.out.println("fail");
			return "fail";
		}

	}
	
	//�ֹ������ȸ ����
	@RequestMapping(value = "/member/lookupOrder", method = RequestMethod.GET)
	public String lookupOrder(HttpServletRequest request, Model model) throws Exception {
		System.out.println("lookupOrder");
			
		return "membership/mypage/lookupOrder";
	}
	  //ajax �ı� �Խ��� ��������
	@RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> reviewList(HttpServletRequest request, Model model)throws Exception {
		
		System.out.println("getOrderList");
		
		command = new OrderListCommand();
		String uId = request.getParameter("uId");
		System.out.println(uId);
		model.addAttribute("uId", uId);
		
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
				
		ArrayList<PurchaseListDto> beforeList = (ArrayList<PurchaseListDto>)map.get("beforeCheckList");
		ArrayList<PurchaseListDto> afterList = (ArrayList<PurchaseListDto>)map.get("afterCheckList");
		
		Map<String,Object> result = new HashMap<String, Object>();// ��ȯ�� �����
		result.put("beforeList", beforeList);
		result.put("afterList", afterList);
		
		return result;
		
	}//	
	  //ajax ���� �߼ۿϷ� üũ��ư
	@RequestMapping(value = "/checkDelivery", method = RequestMethod.POST)
	public @ResponseBody String checkDelivery(HttpServletRequest request, Model model)throws Exception {
		
		System.out.println("User check Delivery");
		
		command = new UserCheckDeliveryCommand();
		String purId = request.getParameter("purId");
		System.out.println(purId);
		model.addAttribute("purId", purId);
		
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
						
		int result = (Integer)map.get("result");
		System.out.println("result : "+result);
		if(result==1)
			return "sucess";
		else
			return null;
	}
	
	//�����ۼ���ưŬ��
	@RequestMapping(value = "/writeReviewView", method = RequestMethod.GET)
	public String writeReviewView(HttpServletRequest request,Model model) throws Exception {
		System.out.println("writeReviewView");
		
		command = new PreventReWriteCheckCommand();
		String purId = request.getParameter("purId");
		model.addAttribute("purId",request.getParameter("purId"));
		command.execute(sqlSession, model); //���� �ߺ��ۼ��� Ȯ��
		
		Map<String, Object> map = model.asMap();
		String resultState = (String)map.get("resultState");
		
		if (resultState.equals(PurchaseListDto.writeReview)) {
			model.addAttribute("submit","true");//â�ݱ��
			return "membership/mypage/writeReviewView";
		}
			
		model.addAttribute("pId",request.getParameter("pId"));
		model.addAttribute("uId",request.getParameter("uId"));
		model.addAttribute("pName",request.getParameter("pName"));
		model.addAttribute("pColor",request.getParameter("pColor"));
		model.addAttribute("uName",request.getParameter("uName"));
		return "membership/mypage/writeReviewView";
	}
	

	//���� �ۼ��� db�� ���� ����
	@Transactional
	@RequestMapping(value = "/writeReview", method = RequestMethod.POST)
	public String writeReview(HttpServletRequest request,Model model) throws RuntimeException {
		System.out.println("do writeReview");
		
		model.addAttribute("purId", request.getParameter("purId"));
		model.addAttribute("pId", request.getParameter("pId"));
		model.addAttribute("uId", request.getParameter("uId"));
		model.addAttribute("pName", request.getParameter("pName"));
		model.addAttribute("pColor", request.getParameter("pColor"));
		model.addAttribute("uName", request.getParameter("uName"));
		model.addAttribute("reGrade", request.getParameter("reGrade"));
		model.addAttribute("reContent", request.getParameter("reContent"));
		
		command = new WriteReivewCommand();
		try {
			
			command.execute(sqlSession, model);
		}
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());			
		}
		
		
		Map<String, Object> map = model.asMap();
		
		String result = (String)map.get("result");
		System.out.println("result : "+result);
		if(result=="success") {//success
			model.addAttribute("submit",request.getParameter("submit"));//â�ݱ��
			return "membership/mypage/writeReviewView";
		}
		else {
			model.addAttribute("submit","fail");
			return "membership/mypage/writeReviewView";
		}
		
	}
		
	
	//���ǰԽù� /userReviewList
	@RequestMapping(value = "/member/myboard", method = RequestMethod.GET)
	public String myboard(Model model) {
		System.out.println("myboard");	
		return "membership/mypage/myboard";
	}
	//���� ���丮��Ʈ �ҷ�����
	@ResponseBody
	@RequestMapping(value = "/userReviewList", method = RequestMethod.POST)
	public Map<String, Object> userReviewList(HttpServletRequest request, Model model) throws Exception{
		System.out.println("userReviewList");
		
		command = new UserReviewListCommand();
		
		String id = request.getParameter("uId");
		model.addAttribute("uId", id);
		command.execute(sqlSession, model);
		
		
		
		  Map<String, Object> map = model.asMap();

		  ArrayList<ReviewBoardDto> reviewList = (ArrayList<ReviewBoardDto>)map.get("reviews");
		  Map<String,Object> result = new HashMap<String, Object>();// ��ȯ�� �����
		  result.put("reviews", reviewList);
		  return result;
	}
	
	//���������ưŬ�� (�������â �˾�)
	@RequestMapping(value = "/modifyReviewView", method = RequestMethod.GET)
	public String modifyReviewView(HttpServletRequest request,Model model) throws Exception {
		System.out.println("modifyReviewView");
		
		command = new GetUserReviewOneItemCommand();
		String reId = request.getParameter("reId");
		model.addAttribute("reId",reId);
		command.execute(sqlSession, model); //������ �ۼ��� �������� ��������
		
		return "membership/mypage/modifyReviewView";
	}
	//���� �������� db�� update
	@RequestMapping(value = "/modifyUserReview", method = RequestMethod.POST)
	public String modifyUserReview(HttpServletRequest request,Model model) throws Exception {
		
		System.out.println("modifyUserReview");
		
		model.addAttribute("reId", request.getParameter("reId"));
		model.addAttribute("reContent", request.getParameter("reContent"));
		model.addAttribute("reGrade", request.getParameter("reGrade"));
		
		command = new ModifyReivewCommand();
		command.execute(sqlSession, model);


		Map<String, Object> map = model.asMap();
		String result = (String)map.get("result");
		
		if(result=="success") {//success
			model.addAttribute("submit","success");//â�ݱ��
			return "membership/mypage/modifyReviewView";
		}
		else {
			model.addAttribute("submit","fail");
			return "membership/mypage/modifyReviewView";
		}
	}	
	
	//���� �����ϱ� & ���ſ� reviewWrite ���� �ٲٱ�
	@Transactional
	@RequestMapping(value = "/deleteUserReview", method = RequestMethod.POST)
	public @ResponseBody String deleteUserReview(HttpServletRequest request, Model model) throws RuntimeException{
		System.out.println("deleteUserReview");
		
		command = new DeleteUserReviewCommand();
		
		String reId = request.getParameter("reId");
		String purId = request.getParameter("purId");
		model.addAttribute("reId", reId);
		model.addAttribute("purId", purId);
		
		try {
		command.execute(sqlSession, model);
		}
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());			
		}
		
		Map<String, Object> map = model.asMap();
		String result = (String)map.get("result");
		System.out.println(result);
		if(result=="success") 
			return "success";
		else
			return null;		
		
	}
	
	
	
	
	//ȸ����������
	@RequestMapping(value = "/member/modifyInfo", method = RequestMethod.GET)
	public String modifyInfo(Model model) {
		System.out.println("modifyInfo");
		
		return "membership/mypage/modifyInfo";
	}
	//ȸ��Ż��
	@RequestMapping(value = "/member/deleteInfo", method = RequestMethod.GET)
	public String deleteInfo(Model model) {
		System.out.println("deleteInfo");
		
		return "membership/mypage/deleteInfo";
	}
}
