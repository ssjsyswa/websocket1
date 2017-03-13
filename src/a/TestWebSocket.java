package a;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.jsp.SkipPageException;
import javax.websocket.*;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
//API:http://tomcat.apache.org/tomcat-7.0-doc/api/org/apache/tomcat/websocket/WsSession.html#addMessageHandler(java.lang.Class,%20javax.websocket.MessageHandler.Partial)

/**
 * @author jsyswa 
 * @version  1.0
 * @since jdk1.7+tomcat 8.+browser accept(webSocket)
 * @date 2017/3/7
 * @parameter
 */
@ServerEndpoint(value="/websocket")
public class TestWebSocket {

	/**
	 * put this to cset
	 */
	private static CopyOnWriteArraySet<TestWebSocket> cset= new CopyOnWriteArraySet<>();
	
	/**
	 * session user's session
	 */
	private Session session;
	
	private static int num=0;
	
	/**
	 * 用户连接调用的方法
	 * @param Session session
	 * @return null
	 */
	@OnOpen
	public void open(Session session){
		this.session =session;
		cset.add(this);
		//receive user's session ID
		String id = session.getId();
		URI uri = session.getRequestURI();
//		Map<String, List<String>> parameterMap = session.getRequestParameterMap();
//		cset.add(this);
		System.out.println("someone is connect");
		addNum();
	} 
	@OnClose
	public void close(){
		cset.remove(this);
		System.out.println("guanbi");
	}
	@OnMessage
	public  void onMessage(String s,Session session) throws IOException{
		for (TestWebSocket testWebSocket : cset) {
			testWebSocket.sendMessage(s);
		}
	}
	
	public void sendMessage(String message) throws IOException{
		  Basic basicRemote = this.session.getBasicRemote();
		  basicRemote.sendText(message);
	}
	public synchronized void addNum(){
		num++;
	}
	public synchronized void subNum(){
		num--;
	}
}
