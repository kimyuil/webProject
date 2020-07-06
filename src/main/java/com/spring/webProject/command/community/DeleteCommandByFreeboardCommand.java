package com.spring.webProject.command.community;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.spring.webProject.command.ICommand;
import com.spring.webProject.dao.IFreeCommentDao;

public class DeleteCommandByFreeboardCommand implements ICommand {

	@Override
	public void execute(SqlSession sqlSession, Model model) throws RuntimeException {
		// TODO Auto-generated method stub
		IFreeCommentDao dao = sqlSession.getMapper(IFreeCommentDao.class);
		
		Map<String, Object> map = model.asMap();
		String fbId = (String) map.get("fbId");
		
		int result = dao.deleteCommentByFreeboard(fbId);
		model.addAttribute("result", "ok");
		//����� �������� �ִ�. result�� 0�̶�� �ؼ� ������ �߸��Ȱ� �ƴ�.
	}

}
