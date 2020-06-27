package com.spring.webProject.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.spring.webProject.command.DeleteQnaCommand;
import com.spring.webProject.command.ICommand;
import com.spring.webProject.command.ModifyQnaCommand;
import com.spring.webProject.command.PurchaseItemsCommand;
import com.spring.webProject.command.QNAListCommand;
import com.spring.webProject.command.QNAPageCommand;
import com.spring.webProject.command.ReviewListCommand;
import com.spring.webProject.command.ReviewPageCommand;
import com.spring.webProject.command.UserCheckCommand;
import com.spring.webProject.command.WriteQnaCommand;
import com.spring.webProject.dto.PageDto;
import com.spring.webProject.dto.QNABoardDto;
import com.spring.webProject.dto.ReviewBoardDto;


/**
 * Handles requests for the application home page.
 */
@Controller
public class ControllerProduct {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerProduct.class);
	
	ICommand command;
	
	@Autowired
	private SqlSession sqlSession;
	

	//�����������̵� (�α����ʿ�) post(��ǰ����������)
	@RequestMapping(value = "member/buyPage", method = RequestMethod.POST)
	public String buyPost(HttpServletRequest request, Model model) {
		System.out.println("buy POST");
		return "product/buyPage";
	}
	//�����������̵� (�α����ʿ�) get (��ٱ��Ͽ��� �ö�)
	@RequestMapping(value = "member/buyPage", method = RequestMethod.GET)
	public String buyGet(HttpServletRequest request, Model model) {
		System.out.println("buy GET");
		return "product/buyPage";
	}
	
	
	//���������� (�α����ʿ�) post(��ǰ����������)
	@Transactional
	@RequestMapping(value = "member/buyAction", method = RequestMethod.POST)
	public String buyAction(HttpServletRequest request, Model model) throws RuntimeException {
		System.out.println("buyAction test page");
		
		command = new PurchaseItemsCommand();
 
		model.addAttribute("uId", request.getParameter("uId"));
		model.addAttribute("uName", request.getParameter("uName"));
		model.addAttribute("uAdress", request.getParameter("uAdress"));
		model.addAttribute("uPhone", request.getParameter("uPhone"));
		model.addAttribute("deliverMessage", request.getParameter("deliverMessage"));
		model.addAttribute("pId", request.getParameter("pId"));
		model.addAttribute("pName", request.getParameter("pName"));
		model.addAttribute("pColor", request.getParameter("pColor"));
		model.addAttribute("pImage", request.getParameter("pImage"));
		model.addAttribute("pNumof", request.getParameter("pNumof"));
		
		try {
		command.execute(sqlSession, model);
		}
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());			
		}
		
		
		Map<String, Object> map = model.asMap();
		String error = (String) map.get("error");
		
		if(error!=null) // ������ ������
			return "home";
		else
			return "product/buySuccess";
	}
	
	  //ajax �ı� �Խ��� ��������
	@RequestMapping(value = "/reviewList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> reviewList(HttpServletRequest request, Model model)throws Exception {
		
		System.out.println("reviewList");
		
		command = new ReviewListCommand();
		
		String pId = request.getParameter("pId");
		model.addAttribute("pId", pId);
		
		command.execute(sqlSession, model); //�Խ��� ����Ʈ�� �޾ƿ�
		
		
		String page = request.getParameter("reviewPage"); //����������
		command = new ReviewPageCommand(Integer.parseInt(page));
		command.execute(sqlSession, model);
				
		
		Map<String, Object> map = model.asMap();
				
		ArrayList<ReviewBoardDto> reviews = (ArrayList<ReviewBoardDto>)map.get("reviews");//�Խ��Ǹ���Ʈ��
		PageDto pageInfo = (PageDto)map.get("pageInfo");//����¡����
		
		Map<String,Object> result = new HashMap<String, Object>();// ��ȯ�� �����
		result.put("reviews", reviews);
		result.put("pageInfo", pageInfo);
		
		return result;
		
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
	
	
	//ajax �ı� �Խ��� ��������
	@RequestMapping(value = "/qnaList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> qnaList(HttpServletRequest request, Model model)throws Exception {
		
		System.out.println("qnaList");
		
		command = new QNAListCommand();
		
		String pId = request.getParameter("pId");
		model.addAttribute("pId", pId);
		
		command.execute(sqlSession, model); //�Խ��� ����Ʈ�� �޾ƿ�
		
		
		String page = request.getParameter("qnaPage"); //����������
		command = new QNAPageCommand(Integer.parseInt(page));
		command.execute(sqlSession, model);
				
		
		Map<String, Object> map = model.asMap();
				
		ArrayList<QNABoardDto> qnas = (ArrayList<QNABoardDto>)map.get("qnas");//�Խ��Ǹ���Ʈ��
		PageDto pageInfo = (PageDto)map.get("pageInfo");//����¡����
				
		Map<String,Object> result = new HashMap<String, Object>();// ��ȯ�� �����
		result.put("qnas", qnas);
		result.put("QpageInfo", pageInfo);
		
		return result;
		
	}
	
	
	//Q&A �ۼ� ȭ���̵�
	@RequestMapping(value = "/member/QnaWriteView", method = RequestMethod.POST)
	public String QnaWriteView(HttpServletRequest request, Model model) {
		System.out.println("member/QnaWriteView");
		
		model.addAttribute("pName", request.getParameter("pName"));
		model.addAttribute("pId", request.getParameter("pId"));
		model.addAttribute("uName", request.getParameter("uName"));
		model.addAttribute("uId", request.getParameter("uId"));
		
		return "product/QnaWriteView";
	}
	//Q&A DB ���̺�� insert
	@RequestMapping(value = "/QnaWrite", method = RequestMethod.POST)
	public String WriteQnA(QNABoardDto qna, Model model) throws Exception {
		System.out.println("Do WriteQnA");
		
		model.addAttribute("qna", qna);
		command = new WriteQnaCommand();
		command.execute(sqlSession, model);
		return "redirect:productPage?pId="+qna.getpId();
	}
	
	//Q&A �ۼ� ȭ���̵�
	@RequestMapping(value = "/member/QnaModifyView", method = RequestMethod.POST)
	public String QnaModifyView(HttpServletRequest request, Model model) {
		System.out.println("member/QnaModifyView");
		
		model.addAttribute("qnaId", request.getParameter("qnaId"));
		model.addAttribute("pId", request.getParameter("pId"));
		model.addAttribute("pName", request.getParameter("pName"));
		model.addAttribute("qnaTitle", request.getParameter("qnaTitle"));
		model.addAttribute("qnaContent", request.getParameter("qnaContent"));
		model.addAttribute("isSecret", request.getParameter("isSecret"));
		//update from qna set title=~,content=~ isSecret=~ where qnaId = !
		
		return "product/QnaModifyView";
	}
	//Q&A DB ���̺�� update
	@RequestMapping(value = "/ModifyQnA", method = RequestMethod.POST)
	public String updateQnA(HttpServletRequest request, Model model) throws Exception {
		System.out.println("Do updateQnA");
		String pId = request.getParameter("pId");
		
		model.addAttribute("qnaId", request.getParameter("qnaId"));
		model.addAttribute("qnaTitle", request.getParameter("qnaTitle"));
		model.addAttribute("qnaContent", request.getParameter("qnaContent"));
		if(request.getParameter("isSecret")==null)
			model.addAttribute("isSecret", "0");
		else		
			model.addAttribute("isSecret", request.getParameter("isSecret"));
		
		command = new ModifyQnaCommand();
		command.execute(sqlSession, model);
		
		return "redirect:productPage?pId="+pId;
		
	}
	// /deleteQna
	@RequestMapping(value = "/deleteQna", method = RequestMethod.POST)
	public String deleteQna(HttpServletRequest request, Model model) throws Exception {
		System.out.println("Do deleteQna");
		
		String pId = request.getParameter("pId");
		String qnaId = request.getParameter("qnaId");
		model.addAttribute("qnaId",qnaId);
		
		command = new DeleteQnaCommand();
		command.execute(sqlSession, model);
		
		return "redirect:productPage?pId="+pId;
		
	}
}
