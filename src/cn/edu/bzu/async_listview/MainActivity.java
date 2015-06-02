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
	private static String URL="http://www.imooc.com/api/teacher?type=4&num=30";  //慕课网提供的api链接
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView=(ListView) findViewById(R.id.lv_main);
		new NewAsyncTask().execute(URL);
	}
	
	/**
	 *实现网络的异步访问
	 */
	class NewAsyncTask extends AsyncTask<String, Void, List<NewsBean> >{ //参数：params：传入值    progress ：进程   Result:返回值 
		
		@Override
		protected List<NewsBean> doInBackground(String... params) {
			return getJsonData(params[0]);  //得到从url读取的JSON数据
		} 
		@Override
			protected void onPostExecute(List<NewsBean> newsBean) {
				// 将生成的newsBean设置给ListView
				super.onPostExecute(newsBean);
				NewsAdapter adapter=new NewsAdapter(MainActivity.this, newsBean);//创建适配器对象
				mListView.setAdapter(adapter);
			}
	}
		/**
		 * 将url对应的json数据转换为我们所封装的NewsBean对象
		 * @param url
		 * @return newsList
		 */
	private List<NewsBean> getJsonData(String url) {
		List<NewsBean> newsBeanList=new ArrayList<NewsBean>();
		try {
			String jsonString=readStream(new URL(url).openStream()); //此句功能与url.openConnection().getInputStream()相同，可根据URL直接联网获取数据，返回值类型 InputStream;
			//Log.d("json",jsonString );  打印读取的json信息
			//解析json数据
			JSONObject jsonObject; 
			NewsBean newsBean; //用于封装jsonObject
			
			jsonObject=new JSONObject(jsonString);  //json数据添加到jsonObject中
			JSONArray jsonArray=jsonObject.getJSONArray("data"); //取出json中的data数据，data为一个数组类型
			for(int i=0;i<jsonArray.length();i++){
				//取出data中的数据
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
	 * 通过inputStream解析网页所返回的数据
	 * @param is
	 * @return  result
	 */
	private String readStream(InputStream is){
		 InputStreamReader isr;  
		 String result="";
		 try {
			 String line=""; //每行的数据
			isr=new InputStreamReader(is,"utf-8");  //字节流转换为字符流
			BufferedReader br=new BufferedReader(isr);  //将字符流以buffer的形式读取出来
			while((line=br.readLine())!=null){
				result+=line;  //拼接到result中
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		 return result;
	}
		                                                         
	}

