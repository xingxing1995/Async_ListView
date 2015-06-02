package cn.edu.bzu.async_listview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView mListView;
	private static String URL="http://www.imooc.com/api/teacher?type=4&num=30";  //Ľ�����ṩ��api����
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView=(ListView) findViewById(R.id.lv_main);
		new NewAsyncTask().execute(URL);
	}
	
	/**
	 *ʵ��������첽����
	 */
	class NewAsyncTask extends AsyncTask<String, Void, List<NewsBean> >{ //������params������ֵ    progress ������   Result:����ֵ 
		
		@Override
		protected List<NewsBean> doInBackground(String... params) {
			return getJsonData(params[0]);  //�õ���url��ȡ��JSON����
		} 
		@Override
			protected void onPostExecute(List<NewsBean> newsBean) {
				// �����ɵ�newsBean���ø�ListView
				super.onPostExecute(newsBean);
				NewsAdapter adapter=new NewsAdapter(MainActivity.this, newsBean);//��������������
				mListView.setAdapter(adapter);
			}
	}
		/**
		 * ��url��Ӧ��json����ת��Ϊ��������װ��NewsBean����
		 * @param url
		 * @return newsList
		 */
	private List<NewsBean> getJsonData(String url) {
		List<NewsBean> newsBeanList=new ArrayList<NewsBean>();
		try {
			String jsonString=readStream(new URL(url).openStream()); //�˾书����url.openConnection().getInputStream()��ͬ���ɸ���URLֱ��������ȡ���ݣ�����ֵ���� InputStream;
			//Log.d("json",jsonString );  ��ӡ��ȡ��json��Ϣ
			//����json����
			JSONObject jsonObject; 
			NewsBean newsBean; //���ڷ�װjsonObject
			
			jsonObject=new JSONObject(jsonString);  //json������ӵ�jsonObject��
			JSONArray jsonArray=jsonObject.getJSONArray("data"); //ȡ��json�е�data���ݣ�dataΪһ����������
			for(int i=0;i<jsonArray.length();i++){
				//ȡ��data�е�����
				jsonObject=jsonArray.getJSONObject(i);
				newsBean=new NewsBean();
				newsBean.imgIconUrl=jsonObject.getString("picSmall");
				newsBean.newsTitle=jsonObject.getString("name");
				newsBean.newsContent=jsonObject.getString("description");
				newsBeanList.add(newsBean);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return newsBeanList;
	}
	
	/**
	 * ͨ��inputStream������ҳ�����ص�����
	 * @param is
	 * @return  result
	 */
	private String readStream(InputStream is){
		 InputStreamReader isr;  
		 String result="";
		 try {
			 String line=""; //ÿ�е�����
			isr=new InputStreamReader(is,"utf-8");  //�ֽ���ת��Ϊ�ַ���
			BufferedReader br=new BufferedReader(isr);  //���ַ�����buffer����ʽ��ȡ����
			while((line=br.readLine())!=null){
				result+=line;  //ƴ�ӵ�result��
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		 return result;
	}
		                                                         
	}

