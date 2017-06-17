package com.sohu_inc;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.9
 * 2017-01-17T20:56:00.313+08:00
 * Generated source version: 3.1.9
 * 
 */
@WebServiceClient(name = "HouseSysService", 
                  wsdlLocation = "http://focuscrm.sohu-inc.com//FocusHouseSys/HouseSysService.asmx?WSDL",
                  targetNamespace = "http://focuscrm.sohu-inc.com/") 
public class HouseSysService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://focuscrm.sohu-inc.com/", "HouseSysService");
    public final static QName HouseSysServiceSoap = new QName("http://focuscrm.sohu-inc.com/", "HouseSysServiceSoap");
    static {
        URL url = null;
        try {
            url = new URL("http://focuscrm.sohu-inc.com//FocusHouseSys/HouseSysService.asmx?WSDL");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(HouseSysService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://focuscrm.sohu-inc.com//FocusHouseSys/HouseSysService.asmx?WSDL");
        }
        WSDL_LOCATION = url;
    }

    public HouseSysService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public HouseSysService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HouseSysService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public HouseSysService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public HouseSysService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public HouseSysService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns HouseSysServiceSoap
     */
    @WebEndpoint(name = "HouseSysServiceSoap")
    public HouseSysServiceSoap getHouseSysServiceSoap() {
        return super.getPort(HouseSysServiceSoap, HouseSysServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns HouseSysServiceSoap
     */
    @WebEndpoint(name = "HouseSysServiceSoap")
    public HouseSysServiceSoap getHouseSysServiceSoap(WebServiceFeature... features) {
        return super.getPort(HouseSysServiceSoap, HouseSysServiceSoap.class, features);
    }

}
