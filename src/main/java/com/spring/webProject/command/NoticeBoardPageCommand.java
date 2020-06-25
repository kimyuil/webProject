package com.spring.webProject.command;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.spring.webProject.dao.IFreeBoardDao;
import com.spring.webProject.dao.INoticeBoardDao;
import com.spring.webProject.dto.PageDto;

public class NoticeBoardPageCommand implements ICommand {

	int page;
	
	public NoticeBoardPageCommand(int p) {
		// TODO Auto-generated constructor stub
		page = p;
	}
	
	@Override
	public void execute(SqlSession sqlSession, Model model) throws Exception {
		// TODO Auto-generated method stub
		INoticeBoardDao dao = sqlSession.getMapper(INoticeBoardDao.class);
		
		int totalNum = dao.noticelistLength();
		
		PageDto pageInfo=new PageDto(5,3); //�����Խ��� 8���� 3�������� ǥ��
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
		pageInfo.setRealLastBlockNum(realLastBlockNum);//�̰� �� �����ִ�.
		
		model.addAttribute("pageInfo", pageInfo);
	}

}