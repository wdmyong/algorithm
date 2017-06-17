
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
 *         &lt;element name="DealConfirmSyncResult" type="{http://focuscrm.sohu-inc.com/}ResultInfo" minOccurs="0"/&gt;
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
    "dealConfirmSyncResult"
})
@XmlRootElement(name = "DealConfirmSyncResponse")
public class DealConfirmSyncResponse {

    @XmlElement(name = "DealConfirmSyncResult")
    protected ResultInfo dealConfirmSyncResult;

    /**
     * ��ȡdealConfirmSyncResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ResultInfo }
     *     
     */
    public ResultInfo getDealConfirmSyncResult() {
        return dealConfirmSyncResult;
    }

    /**
     * ����dealConfirmSyncResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ResultInfo }
     *     
     */
    public void setDealConfirmSyncResult(ResultInfo value) {
        this.dealConfirmSyncResult = value;
    }

}
