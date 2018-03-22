package Http;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.app1.MyApplication;
import com.example.app1.vo.blog;
import com.example.app1.vo.follow;
import com.example.app1.vo.plate;
import com.example.app1.vo.response;
import com.example.app1.vo.user;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 世哲 on 2017/8/2.
 */

public class MyHttpConnection {
    private final static String BASE_URL = "http://47.93.246.251:8080/app/";
    private final static String REGISTER = "register.do";
    private final static String PLATES = "plates.do";
    private final static String LOGIN = "Login.do";
    private final static String GETALLBLOG = "getAllBlog.do";
    private final static String INSERTBLOGS="insertBlog.do";
    public static Gson G = new GsonBuilder().create();
    private static String sessionid;

    public static boolean isOnline() {
        ConnectivityManager cManager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取当前活动的默认网络信息
        NetworkInfo networkInfo = cManager.getActiveNetworkInfo();
        // 判断是否存在默认网络，及默认网络是否开启 && 判断连接是否存在，是否可能建立连接进行数据传输
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        Toast.makeText(MyApplication.getContext(), "请打开网络", Toast.LENGTH_LONG).show();
        return false;
    }
    public static String getStringContent(String url) {
        HttpURLConnection conn = null;
        try {
            URL myurl = new URL(BASE_URL + url);
            conn = (HttpURLConnection) myurl.openConnection();
            if(sessionid != null) {
                conn.setRequestProperty("cookie", sessionid);
            }
            conn.setReadTimeout(1000);
            conn.setConnectTimeout(900);
            // 默认即为Get
            conn.setRequestMethod("GET");
            // 本次连接是否允许返回值
            conn.setDoInput(true);
            //conn.connect();
            // 判断远程HTTP服务器返回的响应CODE
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return getString(conn);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
public static boolean insertBlogs(Map<String,String> M) {
    HttpURLConnection conn = null;
    try {
        URL url = new URL(BASE_URL + INSERTBLOGS);
        conn = (HttpURLConnection) url.openConnection();
        if(sessionid != null) {
            conn.setRequestProperty("cookie", sessionid);
            System.out.println(sessionid);
        }
        conn.setReadTimeout(2000);
        conn.setConnectTimeout(2000);
        // 默认即为Get
        conn.setRequestMethod("POST");
        // 本次连接是否允许返回值
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        byte[] bytes=getAttributes(M);
        System.out.println(new String(bytes));
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
        OutputStream os = conn.getOutputStream();
        os.write(bytes);
        System.out.println(conn.getURL().getPath()+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+url.getPort());
        if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
            return true;
        }
    } catch (ProtocolException e) {
        e.printStackTrace();
    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return false;
}
    public static Bitmap getImage(String url) {
        HttpURLConnection conn = null;
        try {
            URL uri = new URL(BASE_URL + url);
            conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 6;
                Bitmap bitmap = BitmapFactory.decodeStream(is, null, opts);
                is.close();
                return bitmap;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLoginResult(String username, String password) {
        HttpURLConnection conn = null;
        try {
            URL myURL = new URL(BASE_URL + LOGIN + "?username=" + username + "&password=" + password + "");
            conn = (HttpURLConnection) myURL.openConnection();

            conn.setReadTimeout(2000);
            conn.setConnectTimeout(2000);
            // 默认即为Get
            conn.setRequestMethod("GET");
            // 本次连接是否允许返回值
            conn.setDoInput(true);
            conn.setDoOutput(true);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                /*StringBuilder builder = new StringBuilder();
                InputStream is = conn.getInputStream();
                Reader reader = new InputStreamReader(is, Charset.defaultCharset());
                char[] buffer = new char[256];
                while (reader.read(buffer) != -1) {
                    builder.append(buffer);
                }
                is.close();
                return builder.toString();*/
                String cookieval = conn.getHeaderField("set-cookie");
                sessionid=cookieval.substring(0, cookieval.indexOf(";"));
                return getString(conn);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getRegisterResult(String username, String password, String name) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + REGISTER);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(2000);
            conn.setConnectTimeout(2000);
            // 默认即为Get
            conn.setRequestMethod("POST");
            // 本次连接是否允许返回值
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            byte[] data = ("username=" + username + "&password=" + password + "&name=" + name).getBytes();
            // conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            OutputStream os = conn.getOutputStream();
            os.write(data);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder builder = new StringBuilder();
                InputStream is = conn.getInputStream();
                Reader reader = new InputStreamReader(is, Charset.defaultCharset());
                char[] buffer = new char[256];
                while (reader.read(buffer) != -1) {
                    builder.append(buffer);
                }
                is.close();
                return builder.toString();
            } else return "connectfailed" + conn.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<blog> enterBlogs(String plateid) {
        Map<String, String> M = new HashMap<String, String>();
        M.put("plateid", plateid);
        byte[] b = getAttributes(M);
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + GETALLBLOG);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(2000);
            conn.setConnectTimeout(2000);
            // 默认即为Get
            conn.setRequestMethod("POST");
            // 本次连接是否允许返回值
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(b.length));
            OutputStream os = conn.getOutputStream();
            os.write(b);
            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                return G.fromJson(new JsonReader(new InputStreamReader(conn.getInputStream())),new TypeToken<List<blog>>(){}.getType());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean insertFollow(int plateid){
        String a=getStringContent("insertFollow.do?plateid="+String.valueOf(plateid));
        if(a.equals("0"))
            return false;
        else return true;
    }
    public static List<plate> getMyPlates(){
       String a=getStringContent("getFollowPlates.do");
        List<plate> L=G.fromJson(a, new TypeToken<List<plate>>() {}.getType());
        return L;
        }
        public static List<plate> getPlatesActivity() {
        HttpURLConnection conn = null;
        try {
            URL myURL = new URL(BASE_URL + PLATES);
            conn = (HttpURLConnection) myURL.openConnection();
            conn.setReadTimeout(2000);
            conn.setConnectTimeout(2000);
            // 默认即为Get
            conn.setRequestMethod("GET");
            // 本次连接是否允许返回值
            conn.setDoInput(true);
            conn.setDoOutput(true);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                List<plate> L = G.fromJson(new JsonReader(new InputStreamReader(conn.getInputStream())), new TypeToken<List<plate>>() {
                }.getType());
                return L;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
public static user getUserById(String username){
    String user=getStringContent("getUser.do?username="+username);
   return G.fromJson(user,new TypeToken<user>(){}.getType());
}
public static follow getFollowById(String username,int plateid){
    String plate=new Integer(plateid).toString();
    String json=getStringContent("getFollow.do?username="+username+"&plateid="+plate);
    if(json.equals("none")){
        return null;
    }
    else return G.fromJson(json,new TypeToken<follow>(){}.getType());
}
public static String getPlateid(int responseid){
  String need=new Integer(responseid).toString();
    return getStringContent("getPlateByResponse.do?responseid="+need);
}
    public static boolean prepareGetConn(HttpURLConnection conn, String x) {
        try {
            URL myURL = new URL(BASE_URL + x);
            conn = (HttpURLConnection) myURL.openConnection();
            System.out.println(conn == null);
            conn.setReadTimeout(2000);
            conn.setConnectTimeout(2000);
            // 默认即为Get
            conn.setRequestMethod("GET");
            // 本次连接是否允许返回值
            conn.setDoInput(true);
            conn.setDoOutput(true);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean preparePostConn(HttpURLConnection conn, String x, byte[] bytes) {
        try {
            URL url = new URL(BASE_URL + x);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(2000);
            conn.setConnectTimeout(2000);
            // 默认即为Get
            conn.setRequestMethod("POST");
            // 本次连接是否允许返回值
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            OutputStream os = conn.getOutputStream();
            os.write(bytes);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static byte[] getAttributes(Map<String, String> attributes) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString().getBytes();
    }

    public static String getString(HttpURLConnection conn) {
        StringBuffer builder = new StringBuffer();
        try {
            InputStream is = conn.getInputStream();
            Reader reader = new InputStreamReader(is, Charset.defaultCharset());
            BufferedReader br = new BufferedReader(reader);
            String str = br.readLine();
            builder.append(str);
            while ((str = br.readLine()) != null) {
                builder.append("\r\n");
                builder.append(str);
            }
            br.close();
            is.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
