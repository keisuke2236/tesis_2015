package Utils_SystemFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

public class TextWriter {

    FileWriter filewriter;
    FileReader filereader;
    BufferedReader br = null;
    File file;

    public TextWriter() throws IOException {
        this.file = new File("./google.dat");
    }

    public boolean saveHashMap(LinkedHashMap<String, LinkedHashMap> map) throws IOException {
         String current = new java.io.File(".").getCanonicalPath();
            System.out.println("Current dir:" + current);
           
        
        file.createNewFile();
        if (checkBeforeWritefile(file)) {

            filewriter = new FileWriter(file);
            for (String key : map.keySet()) {
                filewriter.write("--\n");
                filewriter.write(key + "\n");
                //System.out.println(key);
                for (Object key2 : map.get(key).keySet()) {
                    //System.out.println(key2);
                    filewriter.write((String)key2 + "\n");
                    //System.out.println(map.get(key).get(key2).toString());
                    filewriter.write(map.get(key).get((String)key2).toString() + "\n");
                }
            }
            
            filewriter.close();
        } else {
            //String current = new java.io.File(".").getCanonicalPath();
            System.out.println("Current dir:" + current);
            System.out.println("ファイルに書き込めません");

        }
        
        return false;
    }

    public LinkedHashMap<String, LinkedHashMap> getHashMap() throws IOException {
        LinkedHashMap<String, LinkedHashMap> map = new LinkedHashMap<String, LinkedHashMap>();
        filereader = new FileReader(file);
        br = new BufferedReader(filereader);
        String str;
        boolean firstFLG = true;
        while ((str = br.readLine()) != null) {
            if(firstFLG){firstFLG=false;str = br.readLine();}
            LinkedHashMap<String, Integer> map2 = new LinkedHashMap<String, Integer>();
            //System.out.println("主キー：" + str);
            String key2 = str;
            while(true){
                str = br.readLine();
                //System.out.println(str);
                if(str==null||"--".equals(str)){break;}
                //System.out.println("キー：" + str);
                int num = Integer.parseInt(br.readLine());
                //System.out.println("値：" + num);
                map2.put(str, num);
            }
            map.put(key2, map2);
        }
        filereader.close();
        br.close();
        return map;
    }

    private static boolean checkBeforeWritefile(File file) {
        if (file.exists()) {
            if (file.isFile() && file.canWrite()) {
                return true;
            }
        }
        return false;
    }

}
