document.addEventListener("DOMContentLoaded", Main, false);
function Main() {

	// セレクターで各要素のインスタンスを取得
	var output = document.querySelector("output#kekka");
	// 身長
	var data1 = document.querySelector("input#data01");
	// 体重
	var data2 = document.querySelector("input#data02");
	var button = document.querySelector("input#bt");

	// クリックイベントの処理
	button.addEventListener("click", onClick);
	function onClick() {
		// 計算式
		output.innerHTML = parsefloat(data2.value)
				/ (parsefloat(data1.value) / 100.0)
				/ (parsefloat(data1.value) / 100.0);
	}
}