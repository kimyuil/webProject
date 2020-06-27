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

import com.spring.webProject.command.DeleteQnaCommand;
import com.spring.webProject.command.DeleteUserReviewCommand;
import com.spring.webProject.command.GetUserReviewOneItemCommand;
import com.spring.webProject.command.ICommand;
import com.spring.webProject.command.ModifyQnaCommand;
import com.spring.webProject.command.ModifyReivewCommand;
import com.spring.webProject.command.UserFreeboardListCommand;
import com.spring.webProject.command.UserQnAListCommand;
import com.spring.webProject.command.UserReviewListCommand;
import com.spring.webProject.dto.FreeBoardDto;
import com.spring.webProject.dto.QNABoardDto;
import com.spring.webProject.dto.ReviewBoardDto;


@Controller
public class ControllerMypageBoard {

	ICommand command;
	
	@Autowired
	private SqlSession sqlSession;
	
		
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
	
	//���� Q&A ����Ʈ �ҷ�����
	@ResponseBody
	@RequestMapping(value = "/userQnaList", method = RequestMethod.POST)
	public Map<String, Object> userQnaList(HttpServletRequest request, Model model) throws Exception{
		System.out.println("userQnaList");
		
		command = new UserQnAListCommand();
		
		String id = request.getParameter("uId");
		model.addAttribute("uId", id);
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();

		ArrayList<QNABoardDto> qnaList = (ArrayList<QNABoardDto>)map.get("qnas");
		Map<String,Object> result = new HashMap<String, Object>();// ��ȯ�� �����
		result.put("qnas", qnaList);
		return result;
	}
	//qna������ưŬ�� 
	@RequestMapping(value = "/member/userModifyQnaView", method = RequestMethod.POST)
	public String userModifyQnaView(HttpServletRequest request,Model model) throws Exception {
		System.out.println("userModifyQnaView");
		
		//command = new GetUserReviewOneItemCommand();
		model.addAttribute("qnaId", request.getParameter("qnaId"));
		model.addAttribute("pId", request.getParameter("pId"));
		model.addAttribute("pName", request.getParameter("pName"));
		model.addAttribute("qnaTitle", request.getParameter("qnaTitle"));
		model.addAttribute("qnaContent", request.getParameter("qnaContent"));
		model.addAttribute("isSecret", request.getParameter("isSecret"));
		model.addAttribute("isFromMypage", "true");
		//update from qna set title=~,content=~ isSecret=~ where qnaId = !
		
		return "product/QnaModifyView";
		
		//command.execute(sqlSession, model); //������ �ۼ��� �������� ��������
	}
	
	//Q&A DB ���̺�� update
	@RequestMapping(value = "/userModifyQna", method = RequestMethod.POST)
	public String updateQnA(HttpServletRequest request, Model model) throws Exception {
		System.out.println("Do user updateQnA");
				
		model.addAttribute("qnaId", request.getParameter("qnaId"));
		model.addAttribute("qnaTitle", request.getParameter("qnaTitle"));
		model.addAttribute("qnaContent", request.getParameter("qnaContent"));
		if(request.getParameter("isSecret")==null)
			model.addAttribute("isSecret", "0");
		else		
			model.addAttribute("isSecret", request.getParameter("isSecret"));
		
		command = new ModifyQnaCommand();
		command.execute(sqlSession, model);
		
		return "redirect:member/myboard";
		
	}
	
	//userDeleteQna
	@RequestMapping(value = "/userDeleteQna", method = RequestMethod.POST)
	public String userDeleteQna(HttpServletRequest request, Model model) throws Exception {
		System.out.println("Do userDeleteQna");
				
		model.addAttribute("qnaId", request.getParameter("qnaId"));
		
		command = new DeleteQnaCommand();
		command.execute(sqlSession, model);
		
		return "redirect:member/myboard";
		
	}
	
	//userFreeboard
	@ResponseBody
	@RequestMapping(value = "/userFreeboardList", method = RequestMethod.POST)
	public Map<String, Object> userFreeboardList(HttpServletRequest request, Model model) throws Exception{
		System.out.println("userFreeboardList");
		
		command = new UserFreeboardListCommand();
		
		String id = request.getParameter("uId");
		model.addAttribute("uId", id);
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();

		ArrayList<FreeBoardDto> boardsList = (ArrayList<FreeBoardDto>)map.get("boards");
		Map<String,Object> result = new HashMap<String, Object>();// ��ȯ�� �����
		result.put("boards", boardsList);
		return result;
	}
}
