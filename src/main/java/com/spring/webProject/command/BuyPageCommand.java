package com.spring.webProject.command;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

public class BuyPageCommand implements ICommand {

	@Override
	public void execute(SqlSession sqlSession, Model model) {
		// TODO Auto-generated method stub
		//user �����ʿ� -> uId �޾ƿ;���
		//product ���� �ʿ� -> pid �޾ƿ;���
		//�޾ƿ��� �׳� �ϴ� ������ model�� �־ �����ϸ� ��.
	}

}
