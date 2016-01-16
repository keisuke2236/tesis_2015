package Output;

import Database.DataBase;
import java.io.IOException;
import java.util.Stack;

/*
 何とかの会話に特化した会話知能
 例：ミリタリー系の会話に特化
 例：日常会話に特化
 例：挨拶に特化
 例：質問に特化
 例：プログラミングに特化
 */
abstract public class Abstract_Mode_parts {

    DataBase database;
    Stack chatLog;
    

    //この解析知能が何に向いているかを羅列するためのString変数
    String about;

    public Abstract_Mode_parts(DataBase database) throws IOException {
        this.database = database;
        about = "挨拶";
    }

    //現在のデータベースの内容を確認し、どれくらい応えたいかを返すメソッド
    //abstract public int getActionLevel() throws IOException;

    //実際に来た内容に対して返答や動作などを返す
    abstract public String Action() throws IOException;

    //どれくらい自分から動作や話しかけることをしたいかを返すメソッド
    abstract public int getTimeActionLevel() throws IOException;

    //自分から話しかける場合に返答を返すメソッド
    abstract public String TimeAction() throws IOException;

    //すべての変数を最新の状態にする
    public void dataRefresh() throws IOException {
        if (database.check() == 1) {
            chatLog = (Stack) database.getData("Mode_ChatLog_SaveLog_stack");
        } else {
            System.out.println("データベースエラー,データベースの中身を確認してください");
            System.out.println("取得データがあるかどうかの確認");
            database.showData();
        }
    }
}
