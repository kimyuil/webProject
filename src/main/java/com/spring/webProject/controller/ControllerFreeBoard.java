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
import com.spring.webProject.command.community.CheckCommentPwCommand;
import com.spring.webProject.command.community.CommentListCommand;
import com.spring.webProject.command.community.DeleteCommentCommand;
import com.spring.webProject.command.community.DeleteFreeboardCommand;
import com.spring.webProject.command.community.FreeBoardContentViewCommand;
import com.spring.webProject.command.community.FreeBoardListCommand;
import com.spring.webProject.command.community.FreeBoardPageCommand;
import com.spring.webProject.command.community.FreeboardLikeMinusCommand;
import com.spring.webProject.command.community.FreeboardLikePlusCommand;
import com.spring.webProject.command.community.InsertFreeboardCommand;
import com.spring.webProject.command.community.ModifyCommentCommand;
import com.spring.webProject.command.community.ModifyFreeboardCommand;
import com.spring.webProject.command.community.WrtieCommentCommand;
import com.spring.webProject.command.community.WrtieReCommentCommand;
import com.spring.webProject.dto.FreeBoardDto;
import com.spring.webProject.dto.FreeCommentDto;


/**
 * Handles requests for the application home page.
 */
@Controller
public class ControllerFreeBoard {
		
	ICommand command;
	
	@Autowired
	private SqlSession sqlSession;
	
	//�����Խ��� ����Ʈ
	@RequestMapping(value = "/freeboardList", method = RequestMethod.GET)
	public String freeboard(HttpServletRequest request , Model model) throws Exception {
		System.out.println("freeboardList");
		
		String page = request.getParameter("page");
		if (page==null)
			page="1";
		
		command = new FreeBoardListCommand();
		command.execute(sqlSession, model);
		
		command = new FreeBoardPageCommand(Integer.parseInt(page));
		command.execute(sqlSession, model);
		
		return "community/freeboard";
	}
	//freeboardContentView �Խñ�ȭ�� 
	@RequestMapping(value = "/freeboardContentView", method = RequestMethod.GET)
	public String freeboardContentView(HttpServletRequest request , Model model) throws Exception {
		System.out.println("freeboardContentView");
		
		model.addAttribute("fbId", request.getParameter("fbId"));
		model.addAttribute("page", request.getParameter("page"));
				
		command = new FreeBoardContentViewCommand();
		command.execute(sqlSession, model);
		
		return "community/freeboardContentView";
	}
	//freeboardContentView �Խñ۾��� ȭ��
	@RequestMapping(value = "/member/freeboardWriteView", method = RequestMethod.GET)
	public String freeboardWriteView(HttpServletRequest request , Model model) throws Exception {
		System.out.println("freeboardWriteView");
		
		String page = request.getParameter("page");
		model.addAttribute("page", page);	
		
		return "community/freeboardWriteView";
	}
	//freeboardWrite  db�Է�
	@RequestMapping(value = "/freeboardWrite", method = RequestMethod.POST)
	public String freeboardWrite(FreeBoardDto board , Model model) throws Exception {
		System.out.println("freeboardWrite");
		
		command = new InsertFreeboardCommand();
		model.addAttribute("board", board);
		command.execute(sqlSession, model);
		
		//model.result -> success or null üũ����
		return "redirect:freeboardList";
	}
	//freeboard ��õ��ư
	@ResponseBody
	@RequestMapping(value = "/freeboardLikePlus", method = RequestMethod.POST)
	public String freeboardLikePlus(HttpServletRequest request, Model model) throws Exception {
		System.out.println("freeboardLikePlus");
		
		model.addAttribute("fbId",request.getParameter("fbId"));
		
		command = new FreeboardLikePlusCommand();
		
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
		return (String)map.get("result"); //success or null
	}
	//freeboard ��õ ��ҹ�ư
	@ResponseBody
	@RequestMapping(value = "/freeboardLikeMinus", method = RequestMethod.POST)
	public String freeboardLikeMinus(HttpServletRequest request, Model model) throws Exception {
		System.out.println("freeboardLikeMinus");
		
		model.addAttribute("fbId",request.getParameter("fbId"));
		
		command = new FreeboardLikeMinusCommand();
		
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
		return (String)map.get("result"); //success or null
	}
	//freeboardModifyView ���� view
	@RequestMapping(value = "/member/freeboardModifyView", method = RequestMethod.POST)
	public String freeboardModifyView(HttpServletRequest request , Model model) throws Exception {
		System.out.println("freeboardModifyView");
		
		model.addAttribute("page", request.getParameter("page"));
		model.addAttribute("fbId", request.getParameter("fbId"));
		model.addAttribute("fbTitle", request.getParameter("fbTitle"));
		model.addAttribute("fbContent", request.getParameter("fbContent"));
		return "community/freeboardModifyView";
	}
	// �����Խñ� ����
	@RequestMapping(value = "/freeboardModify", method = RequestMethod.POST)
	public String freeboardModify(HttpServletRequest request , Model model) throws Exception {
		System.out.println("freeboardModify");
		
		model.addAttribute("page", request.getParameter("page"));
		model.addAttribute("fbId", request.getParameter("fbId"));
		model.addAttribute("fbTitle", request.getParameter("fbTitle"));
		model.addAttribute("fbContent", request.getParameter("fbContent"));
		
		command = new ModifyFreeboardCommand();
		command.execute(sqlSession, model);
		
		//model.result -> success or null üũ����
		return "redirect:freeboardContentView";
	}
	//deleteFreeboard
	@Transactional
	@RequestMapping(value = "/deleteFreeboard", method = RequestMethod.POST)
	public String deleteFreeboard(HttpServletRequest request , Model model) throws RuntimeException {
		System.out.println("deleteFreeboard");
		
		model.addAttribute("page", request.getParameter("page"));
		model.addAttribute("fbId", request.getParameter("fbId"));
		
		command = new DeleteFreeboardCommand();
		try {
			command.execute(sqlSession, model);
		}
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());			
		}
		
		//model.result �˻� success or null üũ����
		return "redirect:freeboardList";
	}
	
	
	// comment~~~~~~~~~~~~~~~
	
	//ajax�� ��۸���Ʈ �Ѹ���
	@RequestMapping(value = "/commentList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> commentList(HttpServletRequest request, Model model)throws Exception {
		
		System.out.println("commentList");
		
		model.addAttribute("fbId", request.getParameter("fbId"));

		command = new CommentListCommand();
		command.execute(sqlSession, model);//�Խ��� ����Ʈ�� �޾ƿ�
		
		Map<String, Object> map = model.asMap();
		
		ArrayList<FreeCommentDto> comments = (ArrayList<FreeCommentDto>)map.get("comments");
		ArrayList<FreeCommentDto> recomments = (ArrayList<FreeCommentDto>)map.get("recomments");
		
		Map<String,Object> result = new HashMap<String, Object>();// ��ȯ�� �����
		result.put("comments", comments);
		result.put("recomments", recomments);
		
		return result;
	}
		
	//ajax comment wirte
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/writeComment", method = RequestMethod.POST)
	public String writeComment(FreeCommentDto comment, Model model) throws RuntimeException {
		System.out.println("writeComment");
		
		model.addAttribute("comment", comment);
		command = new WrtieCommentCommand();
		try {
			command.execute(sqlSession, model);
		}
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());			
		}  
		
		Map<String, Object> map = model.asMap();
		return (String)map.get("result"); //success or null
		
	}
	//ajax comment wirte ����
	@ResponseBody
	@RequestMapping(value = "/writeReComment", method = RequestMethod.POST)
	public String writeReComment(FreeCommentDto comment, Model model) throws Exception {
		System.out.println("write ReComment");
				
		model.addAttribute("comment", comment);
		command = new WrtieReCommentCommand();
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
		return (String)map.get("result"); //success or null
	}
	
	@ResponseBody  //��� ��й�ȣ Ȯ��
	@RequestMapping(value = "/checkCommentPw", method = RequestMethod.POST)
	public String checkCommentPw(HttpServletRequest request, Model model) throws Exception {
		System.out.println("checkCommentPw");
				
		model.addAttribute("cId",request.getParameter("cId"));
		model.addAttribute("cPw",request.getParameter("cPw"));
		
		command = new CheckCommentPwCommand();
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
		return (String)map.get("result"); //ok or null
	}
	@ResponseBody  //��� �����ϱ�
	@RequestMapping(value = "/modifyComment", method = RequestMethod.POST)
	public String modifyComment(HttpServletRequest request, Model model) throws Exception {
		System.out.println("modifyComment");
				
		model.addAttribute("cId",request.getParameter("cId"));
		model.addAttribute("cComment",request.getParameter("cComment"));
		
		command = new ModifyCommentCommand();
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
		int result = (Integer)map.get("result"); //1 or 0
		return result==1 ? "success" : null;
	}
	
	@Transactional
	@ResponseBody  //��� �����ϱ�
	@RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
	public String deleteComment(HttpServletRequest request, Model model) throws RuntimeException {
		System.out.println("deleteComment");
		
		model.addAttribute("cId",request.getParameter("cId"));
		model.addAttribute("fbId",request.getParameter("fbId"));
		
		command = new DeleteCommentCommand(); //���, ���� ���� ��� ����
		
		try {
			command.execute(sqlSession, model);
		}
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());			
		}  
		
		Map<String, Object> map = model.asMap();
		return (String)map.get("result"); // success or null
		
	}
}
