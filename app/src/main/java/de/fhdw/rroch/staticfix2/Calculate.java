package de.fhdw.rroch.staticfix2;

import java.util.ArrayList;

public class Calculate {

    public String outPutData(ArrayList<Integer> input){
        String result = "";
        if(!input.isEmpty()){
            result = input.toString();
            //result = result.substring(result.length() - 2);
        }else{
            result = "Bitte gehen Sie zurück und tragen Sie Werte ein!";
        }
        return result;
    }

    public String organizedData(ArrayList<Integer> input){
        String result;
        if(!input.isEmpty()){
            input.sort(Integer::compareTo);
            result = outPutData(input);
        }else {
            result = "Bitte gehen Sie zurück und tragen Sie Werte ein!";
        }
        return result;
    }
}
