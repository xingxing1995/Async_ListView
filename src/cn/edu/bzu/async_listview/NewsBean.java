package cn.edu.bzu.async_listview;

public class NewsBean {
	public String imgIconUrl;   //图片的URL
	public String newsTitle;     //课程的标题
	public String newsContent;   //课程的内容
	
	public NewsBean(String imgIconUrl, String newsTitle, String newsContent) {
		super();
		this.imgIconUrl = imgIconUrl;
		this.newsTitle = newsTitle;
		this.newsContent = newsContent;
	}

	public NewsBean() {
		super();
	}

	public String getImgIconUrl() {
		return imgIconUrl;
	}

	public void setImgIconUrl(String imgIconUrl) {
		this.imgIconUrl = imgIconUrl;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	
	
}
