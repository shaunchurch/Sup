package com.pixsys.fistbump;

import org.json.JSONArray;

/**
 * Created by shaun on 26/02/2014.
 */
public class SpcUtils {

    public static String combine(JSONArray[] s, String glue)
    {
        int k=s.length;
        if (k==0)
            return null;
        StringBuilder out=new StringBuilder();
        out.append(s[0]);
        for (int x=1;x<k;++x)
            out.append(glue).append(s[x]);
        return out.toString();
    }
}
