package cn.edu.bzu.async_listview;

public class NewsBean {
	public String imgIconUrl;   //ͼƬ��URL
	public String newsTitle;     //�γ̵ı���
	public String newsContent;   //�γ̵�����
	
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
