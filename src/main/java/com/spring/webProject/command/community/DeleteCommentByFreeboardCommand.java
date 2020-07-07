package com.spring.webProject.command.community;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.spring.webProject.command.ICommand;
import com.spring.webProject.dao.IFreeCommentDao;

public class DeleteCommentByFreeboardCommand implements ICommand {

	@Override
	public void execute(SqlSession sqlSession, Model model) throws RuntimeException {
		// TODO Auto-generated method stub
		IFreeCommentDao dao = sqlSession.getMapper(IFreeCommentDao.class);
		
		Map<String, Object> map = model.asMap();
		dao.deleteCommentByFreeboard((String) map.get("fbId"));
		return;
		//�Խ����� �����ϸ鼭 ������ ��� ������ ������ or 0���� �� ����
	}

}
