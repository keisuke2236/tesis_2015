//解析済みデータを取得する
public Object getData(String key) {
	if(objList.get(key)==null){
        System.out.println("Databaseより出力、getData("+key+")がしっぱいしました");
        return null;
    }
   return objList.get(key);
}