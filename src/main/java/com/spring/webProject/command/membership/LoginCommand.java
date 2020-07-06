package com.spring.webProject.command.membership;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.spring.webProject.command.ICommand;
import com.spring.webProject.dao.IUserDao;
import com.spring.webProject.dto.UserDto;

public class LoginCommand implements ICommand {

	@Override
	public void execute(SqlSession sqlSession, Model model) {
		// TODO Auto-generated method stub
		
		IUserDao dao = sqlSession.getMapper(IUserDao.class);
		
		Map<String, Object> map = model.asMap();
		String uId = (String) map.get("uId");
		String uPw = (String) map.get("uPw");
				
		String uName = dao.loginAction(uId,uPw);
		model.addAttribute("uName", uName);

		//�α��� ���Ĵ�? �������� �̸�����ֱ��ؾ��ϴµ� �׳� name�� ���ǿ� ������ ��
		//uId�� ������ ���ŵ� ��ٱ��ϵ� �Խ��Ǳ۾��⵵ ��� ������.
		//name�� uId�� �޾ƿ��� �ǰ�.. null�̸� �α��ν����ΰ�..		
	}

}
