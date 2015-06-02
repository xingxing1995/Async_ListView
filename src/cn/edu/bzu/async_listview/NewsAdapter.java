package cn.edu.bzu.async_listview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {
	private List<NewsBean> mList;
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;
	
	public NewsAdapter(Context context,List<NewsBean> data){
		mList=data;
		mInflater=LayoutInflater.from(context);
		mImageLoader=new ImageLoader();
	}
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int positon, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.listview_item, null); //布局转化为视图
			viewHolder.ivIcon=(ImageView) convertView.findViewById(R.id.iv_icon);
			viewHolder.tvTitle=(TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.tv_Content=(TextView) convertView.findViewById(R.id.tv_content);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.ivIcon.setImageResource(R.drawable.ic_launcher);
		
		String url=mList.get(positon).imgIconUrl;
		viewHolder.ivIcon.setTag(url);
		//new ImageLoader().showImageByThread(viewHolder.ivIcon,url);  //图片id，图片的链接
		mImageLoader.showImageByAsyncTask(viewHolder.ivIcon,url);  //使用继承AsyncTask的方式实现图片的异步加载
		viewHolder.tvTitle.setText(mList.get(positon).newsTitle);
		viewHolder.tv_Content.setText(mList.get(positon).newsContent);
		
		return convertView;
	}
	class ViewHolder{
		public TextView tvTitle,tv_Content;
		public ImageView ivIcon;
	}
}
