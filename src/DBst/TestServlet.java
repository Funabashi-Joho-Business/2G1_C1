package DBst;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

class RecvData
{
	public String cmd;
	public Date date;
	public Double taizyu;
}

class SendData
{
	public int id;
	public Date date;
	public Double taizyu;
}

/**
 * Servlet implementation class TestSevlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBst.Oracle mOracle;
	private final String DB_ID = "x14g009";
	private final String DB_PASS = "a";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	public void init() throws ServletException {
		// TODO 自動生成されたメソッド・スタブ
		super.init();


		try{
			mOracle = new Oracle();
			mOracle.connect("ux4", DB_ID, DB_PASS);

			//テーブルが無ければ作成
			if(!mOracle.isTable("YAaaaI_DEBUuuu"))
			{
				mOracle.execute("create table YAaaaI_DEBUuuu(id number,date01 date,taizyu varchar2(200))");
				mOracle.execute("create sequence YAaaaI_DEBUuuu_seq");
			}
		} catch (Exception e) {
			System.err.println("認証に失敗しました");
		}
	}

	@Override
	public void destroy() {
		//DB切断
		mOracle.close();
		// TODO 自動生成されたメソッド・スタブ
		super.destroy();
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
        RecvData recvData = JSON.decode(request.getInputStream(),RecvData.class);
        if("write".equals(recvData.cmd))
        {
        	//書き込み処理
        	String sql = String.format("insert into YAaaaI_DEBUuuu values(YAaaaI_DEBUuuu_seq.nextval,'%s','%s')",
        			recvData.date,recvData.taizyu);
        	mOracle.execute(sql);
        }


        try {
			//データの送信処理
			ArrayList<SendData> list = new ArrayList<SendData>();
			ResultSet res = mOracle.query("select * from YAaaaI_DEBUuuu order by id");
			while(res.next())
			{
				SendData sendData = new SendData();
				sendData.id = res.getInt(1);
				sendData.date = res.getDate(2);
				sendData.taizyu = res.getDouble(3);
				list.add(sendData);
			}
			//JSON形式に変換
            String json = JSON.encode(list);
            //出力
            out.println(json);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
