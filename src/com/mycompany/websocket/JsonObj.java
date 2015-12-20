package com.mycompany.websocket;
        
class JsonObj{    
    private String chat;
    private String motionName;
    
    
    
    public void setChat(final String age ){
        this.chat = age;
    }
    public void setName(final String name){
        this.motionName = name;
    }
    
    
    //ルールとしてgetがないとjsonに出力されないのでgetメソドも書きましょう
    public String getChat(){
        return this.chat;
    }
    public String getMotionName(){
        return this.motionName;
    }
    
    
}
