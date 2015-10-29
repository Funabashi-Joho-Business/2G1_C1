//ページ読み込みイベントに登録
document.addEventListener("DOMContentLoaded", Main, false);

function Main()
{
	//タイトルの設定
	document.title = "サンプル10";
	
	//セレクターで各要素のインスタンスを取得
	var output = document.querySelector("div#output");
	var data1 = document.querySelector("input#i");
	var data2 = document.querySelector("input#p");
	var data3 = document.querySelector("input#s");
	var data4 = document.querySelector("input#t");
	var data4 = document.querySelector("input#b");
	var button = document.querySelector("input#bt");
	button.addEventListener("click", onClick, false);

	//データ受信処理
	function onRecv(datas)
	{
		//内容のクリア
		output.innerHTML = "";
		for(var index in datas)
		{
			var data = datas[index];
			output.innerHTML = AFL.sprintf("[%d]%s<br>%s<hr>",data.id,data.name,data.msg) + output.innerHTML;
		}
	}
	//ボタンクリック時の送信処理
	function onClick()
	{
		//データ送信
		var sendData = {};
		sendData.cmd = "write";
		sendData.s = data3.value;
		sendData.t = data4.value;
		AFL.sendJson("Ajax10",sendData,onRecv);
	}

	
	//データ受信要求
	var sendData = {"cmd":"read"};
	AFL.sendJson("Ajax10",sendData,onRecv);
}
