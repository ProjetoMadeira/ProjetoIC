package com.ufsj.madeira.projetoic.helper;

import android.util.Base64;


import android.util.Base64;

/**
 * Created by JV on 24/09/2017.
 */
public class Base64Custom {
    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(),Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }

    public static String decodificarBase64(String texto){
        return new String(Base64.decode(texto,Base64.DEFAULT));
    }
}

