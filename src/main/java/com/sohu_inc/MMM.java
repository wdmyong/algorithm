package com.sohu_inc;

import javax.xml.namespace.QName;

import org.springframework.web.context.support.XmlWebApplicationContext;

import java.io.File;
import java.net.URL;

/**
 * Created by yongduan on 2017/1/17.
 */
public class MMM {

    private static final QName SERVICE_NAME = new QName("http://focuscrm.sohu-inc.com/", "HouseSysService");
    public static void main(String args[]) throws Exception {
        String url = "http://10.2.183.96//FocusHouseSys/HouseSysService.asmx?WSDL";
        URL wsdlURL = null;
        File wsdlFile = new File(url);
        try {
            if (wsdlFile.exists()) {
                wsdlURL = wsdlFile.toURI().toURL();
            } else {
                wsdlURL = new URL(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HouseSysService houseSysService = new HouseSysService(wsdlURL, SERVICE_NAME);
        ArrayOfSales arrayOfSales = houseSysService.getHouseSysServiceSoap().getSales("", "83f799ba8073e4c69ca123f981c655ba");
        System.out.println("fads");
        
        
    }
}
