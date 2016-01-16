package Utils_SystemFile;

public class InputBasicData {
    /*
    モード選択
    1：普通の会話内容の送信
    2：クリックまたはタッチされた体の部位
    3：モノの名前とどの部位で扱うか
    
    [1,"こんにちは"]
    [2,"体"]
    [3,"椅子","尻"]
    */
    String message;//こんにちはとか体とか椅子とか
    String bui;//尻とか足とか頭とか手とか
    int mode;
    
    public int getMode(){
        return mode;
    }
    public String getMessage(){
        return message;
    }
    public String getBui(){
        return bui;
    }
    public void setMode(int mode){
        this.mode = mode;
    }
    public void setBui(String bui){
        this.bui = bui;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
