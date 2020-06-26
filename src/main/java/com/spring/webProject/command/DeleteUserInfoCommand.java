package com.spring.webProject.command;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.spring.webProject.dao.IUserDao;

public class DeleteUserInfoCommand implements ICommand {

	@Override
	public void execute(SqlSession sqlSession, Model model) throws Exception {
		// TODO Auto-generated method stub
		IUserDao dao = sqlSession.getMapper(IUserDao.class);
		
		Map<String, Object> map = model.asMap();
		String uId = (String) map.get("uId");
		String inputuPw = (String) map.get("uPw");
		
		//pw�� ������ uId�� �״�� ����. �ƴϸ� null
		String result = dao.userCheck(uId, inputuPw);
		if(result==null) {
			model.addAttribute("result", "false");
			return;
		}
		
		int deleteResult = dao.deleteUser(uId);
		if(deleteResult==1)
			model.addAttribute("result", "true");
		else
			model.addAttribute("result", "error");
	}

}