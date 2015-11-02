package Jprogram;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;


class AjaxData2
{
	public int A;
	public int B;
}
class AjaxData
{
	public String Msg;
}
/**
 * Servlet implementation class Ajax09
 */
@WebServlet("/test01")
public class test01 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test01() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request,response);
	}
	private void action(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//出力ストリームの作成
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();	
        
        //データの受け取り処理
        AjaxData2 data = JSON.decode(request.getInputStream(),AjaxData2.class);
        if(data == null)
            out.println("データが不正です");         
        else
        {
        	AjaxData msg = new AjaxData();
        	msg.Msg = (data.A + data.B) + "が送られてきました";
            //JSON形式に変換
            String json = JSON.encode(msg);
            //出力
            out.println(json);         	
        }

        
	}
}
