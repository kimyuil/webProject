package com.spring.webProject.dto;

public class PageDto {
	public static int pageCount; //���������� ���� �Խù���..
	public static int blockSize; //123 -> 456 -> �̷������� 3��.. ��.
    private int blockStartNum = 0; //���� ù����
    private int blockLastNum = 0; // ���� ����������.
    private int lastPageNum = 0; // ��ü ��Ʋ�� ������ ����������..(�Խù���)
    private int realLastBlockNum=0;
    
    private int currentPage = 1; // ����������
    private int currentPageFirstNum = 0; // ���������� ù �Խñ� �ε���
    private int currentPageLastNum = 4; // ���������� ������ �Խñ� �ε���
    
    public PageDto(int pageCount, int blockSize){ //�̰� final ���ص� �ɱ�???
    	this.pageCount=pageCount;
    	this.blockSize=blockSize;
    }

    
	public static int getPageCount() {
		return pageCount;
	}

	public static void setPageCount(int pageCount) {
		PageDto.pageCount = pageCount;
	}

	public static int getBlockSize() {
		return blockSize;
	}

	public static void setBlockSize(int blockSize) {
		PageDto.blockSize = blockSize;
	}

	public int getBlockStartNum() {
		return blockStartNum;
	}

	public void setBlockStartNum(int blockStartNum) {
		this.blockStartNum = blockStartNum;
	}

	public int getBlockLastNum() {
		return blockLastNum;
	}

	public void setBlockLastNum(int blockLastNum) {
		this.blockLastNum = blockLastNum;
	}

	public int getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(int lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	public int getRealLastBlockNum() {
		return realLastBlockNum;
	}

	public void setRealLastBlockNum(int realLastBlockNum) {
		this.realLastBlockNum = realLastBlockNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentPageFirstNum() {
		return currentPageFirstNum;
	}

	public void setCurrentPageFirstNum(int currentPageFirstNum) {
		this.currentPageFirstNum = currentPageFirstNum;
	}

	public int getCurrentPageLastNum() {
		return currentPageLastNum;
	}

	public void setCurrentPageLastNum(int currentPageLastNum) {
		this.currentPageLastNum = currentPageLastNum;
	}
    

}
