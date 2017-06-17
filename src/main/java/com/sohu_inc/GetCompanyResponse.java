
package com.sohu_inc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetCompanyResult" type="{http://focuscrm.sohu-inc.com/}ArrayOfCompany" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getCompanyResult"
})
@XmlRootElement(name = "GetCompanyResponse")
public class GetCompanyResponse {

    @XmlElement(name = "GetCompanyResult")
    protected ArrayOfCompany getCompanyResult;

    /**
     * ��ȡgetCompanyResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCompany }
     *     
     */
    public ArrayOfCompany getGetCompanyResult() {
        return getCompanyResult;
    }

    /**
     * ����getCompanyResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCompany }
     *     
     */
    public void setGetCompanyResult(ArrayOfCompany value) {
        this.getCompanyResult = value;
    }

}
