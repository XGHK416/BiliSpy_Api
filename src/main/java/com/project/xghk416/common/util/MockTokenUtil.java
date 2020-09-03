package com.project.xghk416.common.util;

import java.util.Random;

public class MockTokenUtil {

    public static String mockToken(){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<20;i++){
            int number=random.nextInt(3);
            long result=0;
            switch(number){
                case 0:
                    result=Math.round(Math.random()*25+65);
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:
                    result=Math.round(Math.random()*25+97);
                    sb.append(String.valueOf((char)result));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(mockToken());
    }
}
