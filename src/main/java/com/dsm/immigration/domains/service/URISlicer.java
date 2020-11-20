package com.dsm.immigration.domains.service;

public class URISlicer {
    public static String slice(String uri) {
        char[] chars = uri.toCharArray();
        int count = 0;
        for(int i = 1 ; i < chars.length ; i++) {
            if(chars[i] == '/') {
                count++;
                if(count >= 2) {
                    return uri.substring(i + 1);
                }
            }
        }
        return "/";
    }
}
