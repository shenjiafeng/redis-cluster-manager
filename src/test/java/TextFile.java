/**
 * Copyright (C), 2011-2017, 微贷网.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author jiafengshen 2017/12/20.
 */
public class TextFile {

    private static Logger logger = LoggerFactory.getLogger(TextFile.class);


    public static void main(String[] args) throws Exception{

        //BufferedReader是可以按行读取文件
        InputStream inputStream = TextFile.class.getResourceAsStream("redis63791.conf");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str = null;
        while((str = bufferedReader.readLine()) != null)
        {
            if (str.startsWith("#")) {
                continue;
            }
            System.out.println(str);
        }

        //close
        inputStream.close();
        bufferedReader.close();

    }


}
