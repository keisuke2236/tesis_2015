public class Mode_Topic_Ryori extends Abstract_Mode_parts {
	@Override
	public boolean ChatAnalyze(String chat) {
    stack.push(chat);   //料理の例
    return true;
  }
}
