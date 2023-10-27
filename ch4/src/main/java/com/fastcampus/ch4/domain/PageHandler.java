package com.fastcampus.ch4.domain;

import org.springframework.web.util.UriComponentsBuilder;

public class PageHandler {

  private SearchCondition sc;
  
  private int naviSize = 10; // page navigation size
  private int totalPage; // 전체 페이지의 갯수
  private int totalCnt; // 게시물의 총 갯수
 
  private int beginPage; // 화면에 보여줄 첫 페이지
  private int endPage; // 화면에 보여줄 마지막 페이지
  
  private boolean showPrev = false; // 이전을 보여줄지의 여부. beginPage==1이 아니면 showPrev는 false
  private boolean showNext = false; // 이후를 보여줄지의 여부. endPage==totalPage이면, showNext는 false
  

  public PageHandler(int totalCnt, SearchCondition sc) {
	  this.totalCnt = totalCnt;
	  this.sc= sc;
	  
	  doPaging(totalCnt,sc);
  }
  
 
  public void doPaging(int totalCnt, SearchCondition sc) {
      this.totalCnt = totalCnt;

      
      totalPage = (int)Math.ceil(totalCnt /(double) sc.getPageSize());
      
      beginPage = (sc.getPageSize()-1) / naviSize * naviSize +1;
      endPage = Math.min(beginPage + naviSize - 1, totalPage);
      showPrev = beginPage !=1;
      showNext = endPage != totalPage;
  }
  
 
  

  void print() { 
      System.out.println("page="+sc.getPage());
      System.out.print(showPrev? "PREV " : "");

      for(int i=beginPage; i<=endPage; i++) {
          System.out.print(i+" ");
      }
      System.out.println(showNext? " NEXT" : "");
  }


@Override
public String toString() {
	return "PageHandler [sc=" + sc + ", naviSize=" + naviSize + ", totalPage=" + totalPage + ", totalCnt=" + totalCnt
			+ ", beginPage=" + beginPage + ", endPage=" + endPage + ", showPrev=" + showPrev + ", showNext=" + showNext
			+ "]";
}

public int getTotalCnt() {
	return totalCnt;
}

public void setTotalCnt(int totalCnt) {
	this.totalCnt = totalCnt;
}


public int getNaviSize() {
	return naviSize;
}

public void setNaviSize(int naviSize) {
	this.naviSize = naviSize;
}

public int getTotalPage() {
	return totalPage;
}

public void setTotalPage(int totalPage) {
	this.totalPage = totalPage;
}


public int getBeginPage() {
	return beginPage;
}

public void setBeginPage(int beginPage) {
	this.beginPage = beginPage;
}

public int getEndPage() {
	return endPage;
}

public void setEndPage(int endPage) {
	this.endPage = endPage;
}

public boolean isShowPrev() {
	return showPrev;
}

public void setShowPrev(boolean showPrev) {
	this.showPrev = showPrev;
}

public boolean isShowNext() {
	return showNext;
}

public void setShowNext(boolean showNext) {
	this.showNext = showNext;
}

public SearchCondition getSc() {
	return sc;
}

public void setSc(SearchCondition sc) {
	this.sc = sc;
}



}
