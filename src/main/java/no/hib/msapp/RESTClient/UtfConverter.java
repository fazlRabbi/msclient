package no.hib.msapp.RESTClient;

public class UtfConverter {

    public static String convertFromUtf(String utf){
        if(utf == null || utf.isEmpty()) return utf;
        utf = utf.replaceAll("å","aa");
        utf = utf.replaceAll("æ","aee");
        utf = utf.replaceAll("ø","oee");
        return utf;
    }

    public static String convertToUtf(String nonutf){
        if(nonutf == null || nonutf.isEmpty()) return nonutf;
        nonutf = nonutf.replaceAll("aa","å");
        nonutf = nonutf.replaceAll("aee","æ");
        nonutf = nonutf.replaceAll("oee","ø");
        return nonutf;
    }


}
