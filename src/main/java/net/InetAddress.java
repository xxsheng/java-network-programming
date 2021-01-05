package net;

import java.net.UnknownHostException;

public class InetAddress {
    public static void main(String[] args) throws UnknownHostException {
//        java.net.InetAddress[] byName = java.net.InetAddress.getAllByName("wwww.baidu.com");
//        for (java.net.InetAddress inetAddress : byName) {
//            System.out.println(inetAddress);
//        }
//        java.net.InetAddress byName = java.net.InetAddress.getByName("123.125.114.144");
//        System.out.println(byName.getHostName());
        System.out.println(java.net.InetAddress.getLocalHost());
        byte a = (byte) 216;
        System.out.println(a);
    }
}
