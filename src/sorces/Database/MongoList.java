/*
外部データベースへの接続
 */
package Database;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoList {
    MongoClient mongoClient;
    DB db;
    DBCollection coll;
    ArrayList list;
    public HashMap<String,HashMap<String,HashMap<String,Integer>>> dbMap;   
    
    public HashMap<String,HashMap<String,HashMap<String,Integer>>> getdbMap(){
        return dbMap;
    }
    
    public MongoList() {
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        // MongoDBサーバに接続
        mongoClient = new MongoClient("192.168.1.107", 27017);
//        mongoClient = new MongoClient("192.168.0.6", 27017);
        // 利用するDBを取得
        db = mongoClient.getDB( "miku_motion" );
        // 指定のコレクション取得
        coll = db.getCollection("unity");

        BasicDBObject query = new BasicDBObject();
//        query.put("tag", "走る");
        DBCursor cursor = coll.find(query);
        list = new ArrayList();
        try {
           while(cursor.hasNext()) {
               BasicDBObject obj = (BasicDBObject) cursor.next();
               list.add(obj);
           }
        } finally {
           cursor.close();
        }        

        // サーバから切断
        mongoClient.close();        

    }
    
    public void SplitArray() {
        HashMap<String,HashMap<String,Integer>> tagMap;
        HashMap<String,Integer> map;
        dbMap = new HashMap<>();

        for(int i = 0; i < list.size(); i++ ) {
            String listStr = list.get(i).toString();
//            System.out.println(listStr);

            //ファイル名
            String pt1 = "file_name\" : \"";
            String pt2 = "\" , \"tag";
            int index1 = listStr.indexOf(pt1);
            index1 += pt1.length();
            String file_name = listStr.substring(index1);
            int index2 = file_name.indexOf(pt2);
            file_name = file_name.substring(0,index2);
//            System.out.println(file_name);

            //タグ
            pt1 = "tag\" : [ ";
            pt2 = "] , \"tag_phrase";
            index1 = listStr.indexOf(pt1);
            index1 += pt1.length();
            String tag = listStr.substring(index1);
            index2 = tag.indexOf(pt2);
            tag = tag.substring(0, index2);
//            System.out.println(tag);

            //セリフ
            pt1 = "tag_phrase\" : [ ";
            pt2 = "] , \"tag_google";
            index1 = listStr.indexOf(pt1);
            index1 += pt1.length();
            String phrase = listStr.substring(index1);
            index2 = phrase.indexOf(pt2);
            phrase = phrase.substring(0, index2);
//            System.out.println(phrase);

            //解析
            pt1 = "tag_google\" : [ \"";
            pt2 = "\"]}";
            index1 = listStr.indexOf(pt1);
            index1 += pt1.length();
            String tag_google = listStr.substring(index1);
            index2 = tag_google.indexOf(pt2);
            tag_google = tag_google.substring(0, index2);
//            System.out.println(tag_google);


            String[] part1 = tag_google.split("\" , \"", -1);
            String[] part2 = new String[part1.length];
            String tag_name;
            String[] tag_analyze;
            int index;
            tagMap = new HashMap<>();
            for(int ana_i = 0; ana_i < part1.length; ana_i++ ) {
//                System.out.println(part1[ana_i]);
                pt1 = "{";
                pt2 = "}";
                index1 = part1[ana_i].indexOf(pt1);
                index1 += pt1.length();
                part2[ana_i] = part1[ana_i].substring(index1);
                index2 = part2[ana_i].indexOf(pt2);
                part2[ana_i] = part2[ana_i].substring(0, index2);
//                System.out.println(analyze2);

                tag_name = part1[ana_i].substring(0, part1[ana_i].indexOf("{"));
                tag_analyze = part2[ana_i].split(", ");
                //HashMap<ファイル名, HashMap<タグ, HashMap<googlesan, 値>>>
                map = new HashMap<>();
                for(int map_i = 0; map_i < tag_analyze.length; map_i++ ) {
                    index = tag_analyze[map_i].indexOf("=");
                    map.put(tag_analyze[map_i].substring(0,index), Integer.parseInt(tag_analyze[map_i].substring(index+1)));
//                    System.out.println(goo[i]);
                }
                tagMap.put(tag_name, map);
            }
            dbMap.put(file_name,tagMap);

        }

//        System.out.println(dbMap);
//        tagMap = dbMap.get("DAMAGED00");
//        System.out.println(tagMap);
//        map = tagMap.get("痛い");
//        System.out.println(map);
//        System.out.println(map.get("大阪"));
        
    }
    
}
