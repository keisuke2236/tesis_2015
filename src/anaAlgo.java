public class Mode_Topic_Ryori extends Abstract_Mode_parts {
	@Override
	public boolean ChatAnalyze(String chat) {
		//この例は一番簡単なクラス名と入力内容をつなげて保存を行うだけの物
    stringMap.put("話題","料理の話題：" + chat) //アルゴリズムの記述
    return true;
  }
}
