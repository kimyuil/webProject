package com.spring.webProject.command;

import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

public class WriteReivewCommand implements ICommand {

	@Transactional
	@Override
	public void execute(SqlSession sqlSession, Model model) throws Exception {
		// TODO Auto-generated method stub
		ICommand reviewCommand = new InsertReviewDataCommand();
		ICommand purchaseListCommand = new ChangePurchaseStateCommand();
		
		try {
			reviewCommand.execute(sqlSession, model);//재고줄이고 판매량늘리기
			purchaseListCommand.execute(sqlSession, model);//구매목록 db에 insert
		}
		catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}

}
