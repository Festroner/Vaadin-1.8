package org.Imanol;

import java.util.ArrayList;

public class Metodos {
    public static Ips Encontrar(String Ip, ArrayList<Ips> lista)throws Exception{
        System.out.println(Long.valueOf(Dot2LongIP(Ip)));
        System.out.println(Long.valueOf(Dot2LongIP("255.255.255.255")));

        if (Ip=="") {
            throw new Exception("Ip vacia");
        }
        if (Long.valueOf(Dot2LongIP(Ip))> Long.valueOf(Dot2LongIP("255.255.255.255"))) {
            System.out.println(Long.valueOf(Dot2LongIP(Ip)));
            System.out.println(Long.valueOf(Dot2LongIP("255.255.255.255")));
            throw new Exception("Ip fuera de los margenes por arriba");
        }
        if (Long.valueOf(Dot2LongIP(Ip))< Long.valueOf(Dot2LongIP("0.0.0.0"))) {
            throw new Exception("Ip fuera de los margenes por abajo");
        }
        long ip = Dot2LongIP(Ip);
        Ips nueva = new Ips();
        for (Ips i : lista){

            if (i.getIp_from()<= ip && i.getIp_to()>=ip){
                System.out.println("Encontrada");
                return i;

            }
        }
        return nueva;
    }

    public static String longToIp(long ip) {
        StringBuilder result = new StringBuilder(15);
        for (int i = 0; i < 4; i++) {
            result.insert(0, Long.toString(ip & 0xff));
            if (i < 3) {
                result.insert(0, '.');
            }
            ip = ip >> 8;
        }
        return result.toString();
    }

    public static Long Dot2LongIP(String dottedIP) {
        String[] addrArray = dottedIP.split("\\.");
        long num = 0;
        for (int i=0;i<addrArray.length;i++) {
            int power = 3-i;
            num += ((Integer.parseInt(addrArray[i]) % 256) *
                    Math.pow(256,power));
        }
        return num;
    }
}
