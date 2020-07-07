package com.spring.webProject.command.product;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.spring.webProject.command.ICommand;
import com.spring.webProject.dao.IReviewDao;
import com.spring.webProject.dto.PageDto;

public class ReviewPageCommand implements ICommand {
	
	int page; 
	
	public ReviewPageCommand(int p) {
		// TODO Auto-generated constructor stub
		page = p;
	}	

	@Override
	public void execute(SqlSession sqlSession, Model model) throws Exception {
		// TODO Auto-generated method stub
		IReviewDao dao = sqlSession.getMapper(IReviewDao.class);
		
		Map<String, Object> map = model.asMap();
		String pId = (String) map.get("pId");
	
		int totalNum = dao.listLength(pId);
		
		
		PageDto pageInfo=new PageDto(5,3); //�ı�Խ��� 5���� 3������� ǥ��
		int pageStartNum = (page-1)*PageDto.pageCount;
		int pageLastNum = page*PageDto.pageCount -1;
		
		int blockStartNum = ((page-1)/PageDto.blockSize)*PageDto.blockSize+1;
		int blockLastNum = blockStartNum+PageDto.blockSize-1;
		
		int realLastBlockNum = totalNum/PageDto.pageCount +1;//������block����
		if(realLastBlockNum<=blockLastNum) {
			pageInfo.setBlockLastNum(realLastBlockNum);
		}
		else {
			pageInfo.setBlockLastNum(blockLastNum); 
		}
		
		if(pageLastNum<totalNum) { //�Ϲ����� ��Ȳ.
			pageInfo.setCurrentPageLastNum(pageLastNum);
		}
		else {//�� �� ������
			pageInfo.setCurrentPageLastNum(totalNum);
		}
		
		
		pageInfo.setCurrentPage(page);
		pageInfo.setCurrentPageFirstNum(pageStartNum);
		pageInfo.setBlockStartNum(blockStartNum);
		pageInfo.setLastPageNum(totalNum);
		pageInfo.setRealLastBlockNum(realLastBlockNum);
		
		model.addAttribute("pageInfo", pageInfo);
	}

}
