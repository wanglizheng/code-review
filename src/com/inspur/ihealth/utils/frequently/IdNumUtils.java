package com.inspur.ihealth.utils.frequently;


public class IdNumUtils {

    public static String getBirthByCard(String idCard) {
        if(null==idCard||idCard.length()!=18){
            return "";
        }
        String result;
        result = idCard.substring(6, 14);

        return result;
    }

    public static String getSexByCard(String idCard) {
        if(null==idCard||idCard.length()!=18){
            return "";
        }
        String result;
        String id =idCard;
        String sex = id.substring(16, 17);
        if(Integer.parseInt(sex)%2==0){
            result = "女";
        }else{
            result ="男";

        }
        return result;
    }
    public static String getChenghuByCard(String idCard){
        String result = "";
        String sex = getSexByCard(idCard);
        String birth = getBirthByCard(idCard);
        if(sex.equals("男")){
            result = "先生";
        }else if(sex.equals("女")){
            result = "女士";
        }
        return result;
    }
}
