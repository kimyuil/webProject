package com.spring.webProject.command;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.spring.webProject.dao.IProductDao;
import com.spring.webProject.dao.IPurchaseListDao;
import com.spring.webProject.dto.ProductDto;
import com.spring.webProject.dto.PurchaseListDto;

public class AddBasketCommand implements ICommand {

	@Override
	public void execute(SqlSession sqlSession, Model model) {
		// TODO Auto-generated method stub
		/*
		 * IPurchaseListDao purdao = sqlSession.getMapper(IPurchaseListDao.class);
		 * IProductDao pdao = sqlSession.getMapper(IProductDao.class);
		 * 
		 * Map<String, Object> map = model.asMap(); String uId = (String)
		 * map.get("uId"); String pId = (String) map.get("pId");
		 * 
		 * //�ߺ��˻� String pIdCheck = purdao.getPidByUid(uId, pId);
		 * 
		 * if(pIdCheck == null) { //�ߺ��� ������ model.addAttribute("result",
		 * "alreadyAdded"); return; }
		 * 
		 * ProductDto product = pdao.getProduct(pId); //pId�� �ش��ϴ� ������������
		 * 
		 * //data���� ����, �÷���, ����ð��� ���� ���⼭ ���� �־��־���Ѵ�. PurchaseListDto purchase =
		 * purdao.addBasketItem(uId,pId,product.getpName(),�÷�??,����??,product.
		 * getpImageRoute(), �ð�??, "basket");
		 */
		/*
		 * 
		 * ���⼭ �޾ƿ� pId�� ���� pId�� ���ٸ�. 
		 * uId�� pId���� �־��ְ� 
		 * pId���� ���ؼ� product������ �� �����;���.
		 * �� �������� �ϴ� purchaselist db�� �־��־����. 
		 * */
		
		//�ߺ��˻�
//		String bookmark = dao.getBookmarkList(uId);
//		System.out.println("�ϸ�ũ����Ʈ�޾ƿ°� "+bookmark);
//		
//		if(bookmark != null) {
//			String bookmarkList[] = bookmark.split(";");
//		
//			for(int i=0;i<bookmarkList.length;i++) { 
//				if(pId.equals(bookmarkList[i])) {
//					model.addAttribute("result", "alreadyAdded");
//					return;
//				}
//			}
//		
//			pIdAdd+=bookmark;
//		}
		
		/*
		 * try { //dao.addBookmark(uId, pIdAdd); model.addAttribute("result",
		 * "success"); } catch(Exception e) { System.out.println("���ɸ�� �߰� ���ܹ߻�");
		 * model.addAttribute("result", "fail. ask to manager"); }
		 */
	}

}
