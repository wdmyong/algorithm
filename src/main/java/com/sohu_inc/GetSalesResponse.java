
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
 *         &lt;element name="GetSalesResult" type="{http://focuscrm.sohu-inc.com/}ArrayOfSales" minOccurs="0"/&gt;
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
    "getSalesResult"
})
@XmlRootElement(name = "GetSalesResponse")
public class GetSalesResponse {

    @XmlElement(name = "GetSalesResult")
    protected ArrayOfSales getSalesResult;

    /**
     * ��ȡgetSalesResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSales }
     *     
     */
    public ArrayOfSales getGetSalesResult() {
        return getSalesResult;
    }

    /**
     * ����getSalesResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSales }
     *     
     */
    public void setGetSalesResult(ArrayOfSales value) {
        this.getSalesResult = value;
    }

}
