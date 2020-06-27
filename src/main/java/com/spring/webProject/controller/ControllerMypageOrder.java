package com.spring.webProject.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.webProject.command.ICommand;
import com.spring.webProject.command.OrderListCommand;
import com.spring.webProject.command.PreventReWriteCheckCommand;
import com.spring.webProject.command.UserCheckDeliveryCommand;
import com.spring.webProject.command.WriteReivewCommand;
import com.spring.webProject.dto.PurchaseListDto;


@Controller
public class ControllerMypageOrder {

	ICommand command;
	
	@Autowired
	private SqlSession sqlSession;
	
			
	//�ֹ������ȸ ����
	@RequestMapping(value = "/member/lookupOrder", method = RequestMethod.GET)
	public String lookupOrder(HttpServletRequest request, Model model) throws Exception {
		System.out.println("lookupOrder");
			
		return "membership/mypage/lookupOrder";
	}
	  //ajax ���Ÿ�� ��������
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
	  //ajax ��۵Ȱ� �޾Ҵٰ� üũ��ư
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
		
}
