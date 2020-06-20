package com.spring.webProject.command;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.spring.webProject.dao.IReviewDao;

public class DeleteUserReviewCommand implements ICommand {

	@Transactional
	@Override
	public void execute(SqlSession sqlSession, Model model) throws Exception {
		// TODO Auto-generated method stub
		
		
		ICommand reviewCommand = new DeleteOneReviewDataCommand();
		ICommand purchaseListCommand = new ReChangePurchaseStateCommand();
		
		try {
			reviewCommand.execute(sqlSession, model);//������̰� �Ǹŷ��ø���
			purchaseListCommand.execute(sqlSession, model);//���Ÿ�� db�� insert
		}
		catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		

	}

}
