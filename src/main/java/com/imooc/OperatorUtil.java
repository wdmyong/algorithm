package com.imooc;

import com.recom.Rating;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;
import org.apache.axis2.wsdl.WSDL2Java;

import org.apache.soap.Constants;
import org.apache.soap.Fault;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;


import java.net.URL;
/**
 * Created by yongduan on 2017/1/10.
 */
public class OperatorUtil {

    public static Double add(Double doubleA, Double doubleB) {
        BigDecimal a = new BigDecimal(doubleA.toString());
        BigDecimal b = new BigDecimal(doubleB.toString());
        BigDecimal c = a.add(b);
        return c.doubleValue();
    }

    public static Double sub(Double doubleA, Double doubleB) {
        BigDecimal a = new BigDecimal(doubleA.toString());
        BigDecimal b = new BigDecimal(doubleB.toString());
        BigDecimal c = a.subtract(b);
        return c.doubleValue();
    }

    public static boolean equal(Double doubleA, Double doubleB) {
        BigDecimal a = new BigDecimal(doubleA.toString());
        BigDecimal b = new BigDecimal(doubleB.toString());
        return a.equals(b);
    }

    public static void main(String[] args) {
        try
        {
            System.out.println("fa");
            //建立远程呼叫
            Call call=new Call();
            //设置远程对象URI，必须与部属时字串一致
            call.setTargetObjectURI(
                    "urn:javawebservice.string.touppercase");
            //设置远程方法
            call.setMethodName("GetSales");
            //设置编码风格
            call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
            //设置远程方法所需参数，所有参数置于Vector中
            Vector params=new Vector();
            params.addElement(new Parameter(
                    "sSalesName",String.class,new String(""),null));
            params.addElement(new Parameter(
                    "sKey",String.class,new String("a6d5a08bfbd8678a1327b176a8dfa412"),null));
            call.setParams(params);
            //发送RPC请求
            Response resp=call.invoke(new URL(
                    "http://focuscrm.sohu-inc.com//FocusHouseSys/HouseSysService.asmx?WSDL"),"");
            //结果处理
            if(resp.generatedFault())
            {
                Fault fault=resp.getFault();
                System.out.println("call faild:");
                System.out.println("Fault Code="+
                        fault.getFaultCode());
                System.out.println("Fault String"+
                        fault.getFaultString());
                System.out.println("Something wrong!");
            }
            else
            {
                //得到服务器返回结果
                Parameter result=resp.getReturnValue();
                //代码转换
                System.out.println((String)result.getValue().toString());
            }
        }
        catch(Exception e)
        {
            System.out.println( e.toString());
        }



    }
}
