package cn.edu.bzu.async_listview;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Message;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.LruCache;
import android.widget.ImageView;

/**
 * ���ڴ���ͼƬ�ļ���
 * @author monster
 *
 */
public class ImageLoader {
	private ImageView mImageView;
	private String mUrl;
	private LruCache<String, Bitmap> mCaches ;  //�û�ͼƬ�Ļ���
	
	public ImageLoader(){
		int maxMemory=(int) Runtime.getRuntime().maxMemory();  //��ȡ�������ڴ�
		int cacheSize=maxMemory/4;  //����Ĵ�С
		mCaches=new LruCache<String,Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				//��ÿ�δ��뻺���ʱ�����
				return value.getByteCount(); //����ϵͳ�������ͼƬ�Ĵ�С
			}
		};
	}
	/**
	 * ��bitmap���뵽������
	 * @param url
	 * @param bitmap
	 */
	public void addBitmapToCache(String url,Bitmap bitmap){
		if(getBitmapFromCache(url)==null){
			mCaches.put(url, bitmap);
		}
	}
	
	/**
	 * ��ͼƬ�ӻ�����ȡ����
	 * @param url
	 * @return bitmap
	 */
	public Bitmap getBitmapFromCache(String url){
		return mCaches.get(url);
	}
	/**
	 * UI���߳�
	 */
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg); 
			//ͨ������tag���Ա��⻺��ͼƬ����ȷͼƬ��Ӱ��
			if(mImageView.getTag().equals(mUrl)){
				mImageView.setImageBitmap((Bitmap) msg.obj);
			}
		};
	};
	/**
	 * ͨ�����̵߳ķ�ʽ����ͼƬ
	 * @param imageView
	 * @param url
	 */
	public void showImageByThread(ImageView imageView,final String url){
		mImageView=imageView; //��ImageView�������Ա������
		mUrl=url;
		new Thread(){
			@Override
			public void run() {
				super.run();
				Bitmap bitmap=getBitmapFromURL(url);
				Message message=Message.obtain();
				message.obj=bitmap;
				mHandler.sendMessage(message); //�����ݷ��͵�Handle�߳���
			}
		}.start();
	}
	/**
	 * ͨ��url�õ�bitmap
	 * @param urlString
	 * @return bitmap
	 */
	public Bitmap getBitmapFromURL(String urlString){
		Bitmap bitmap;
		InputStream is = null;
		try {
			URL url=new URL(urlString);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection(); //������   //ע���ǣ�HttpURLConnection������HttpsURLConnection
			is=new BufferedInputStream(connection.getInputStream());
			bitmap=BitmapFactory.decodeStream(is); //�������ת��Ϊbitmap
			connection.disconnect(); //��Դ�ͷ�
			return bitmap;
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * ͨ��AsyncTask�ķ�ʽ�첽����ͼƬ
	 * @param imageView
	 * @param url
	 */
	public void showImageByAsyncTask(ImageView imageView,String url){
		Bitmap bitmap=getBitmapFromCache(url);  //�ӻ�����ȡ��ͼƬ
		if(bitmap==null){
			new NewsAsyncTask(imageView,url).execute(url);	
		}else{
			imageView.setImageBitmap(bitmap);
		}
		
	}
	private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap>{
		private ImageView mImageView;
		private String mUrl;
		public NewsAsyncTask(ImageView imageView,String url){
			mImageView=imageView;
			mUrl=url;
		}
		/**
		 * �������л�ȡͼƬ�����ͼƬ�Ѿ����أ�����뵽����
		 */
		@Override
		protected Bitmap doInBackground(String... params) {
			String url=params[0];
			Bitmap bitmap=getBitmapFromURL(url);
			if(bitmap!=null){
				addBitmapToCache(url, bitmap);
			}
			return bitmap ;
		}
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			if(mImageView.getTag().equals(mUrl)){
				 mImageView.setImageBitmap(bitmap);	
			}
		}
	}
}
