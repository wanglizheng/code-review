package com.inspur.ihealth.utils.frequently;

/**
*
*@author: wanglz
*@description:  脱敏工具类
*@date: 2018/5/2 15:13
*
*/

public class DataConcealUtil {

    public static int SIZE = 14;//脱敏的字符个数
    public static String SYMBOL = "*"; //脱敏用字符

    public static String toConceal(String str){

        //空置或者空串直接返回
        if(null== str || "".equals(str)){

            return str;
        }

        int L = str.length();
        int a = L/2;
        int b = a - 1;
        //求余运算
        int c = L % 2;

        StringBuffer sb = new StringBuffer(L);

        if(L <= 2){

            //如果是一个字符直接返回
            if(c==1){return str;}

            //如果是两个字符，第一个字符脱敏
/*            sb.append(SYMBOL);
            sb.append(str.charAt(L -1));*/
            //留姓脱名
            sb.append(str.charAt(0));
            sb.append(SYMBOL);
        }else{
            //如果是三个字符，中间的字符脱敏
            if(b<=0){
               // sb.append(str.substring(0,2));
/*                sb.append(SYMBOL);
                sb.append(str.substring(L-2,L));*/

                sb.append(str.substring(0,1));
                sb.append(SYMBOL);
                sb.append(SYMBOL);

            }else if(b >= SIZE / 2 && SIZE+1 != L){
                int e = (L - SIZE) / 2;
                sb.append(str.substring(0,e));
                for(int i=0;i<SIZE;i++){sb.append(SYMBOL);}
                if((c == 0 && SIZE%2 == 0)||(c!=0 && SIZE%2 !=0)){
                    sb.append(str.substring(L-e,L));
                }else{sb.append(str.substring(L-(e+1),L));}
            }else {
                int d = L - 2;
                sb.append(str.substring(0,1));
                for(int i=0;i<d;i++){sb.append(SYMBOL);}
                sb.append(str.substring(L-1,L));
            }
        }



        return sb.toString();
    }

    public static void main(String[] args){
        String[] strs = {null,"","1","12","123"
                ,"1234","12345","123456","1234567","12345678","123456789"};

        for(String str:strs){
            System.out.println(toConceal(str));
        }
    }

}
