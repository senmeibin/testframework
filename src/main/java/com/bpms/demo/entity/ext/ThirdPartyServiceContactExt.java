package com.bpms.demo.entity.ext;

import com.bpms.demo.entity.ThirdPartyServiceContact;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "demo_third_party_service_contact")
public class ThirdPartyServiceContactExt extends ThirdPartyServiceContact {
    private static final long serialVersionUID = -20170420172247274L;

    /**
     * 服务次数
     */
    @Transient
    private int serviceCount;

    /**
     * 第三方服务联系人实体列表
     */
    @Transient
    private List<ThirdPartyServiceContactsExt> thirdPartyServiceContactsList;

    public int getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

    public List<ThirdPartyServiceContactsExt> getThirdPartyServiceContactsList() {
        return thirdPartyServiceContactsList;
    }

    public void setThirdPartyServiceContactsList(List<ThirdPartyServiceContactsExt> thirdPartyServiceContactsList) {
        this.thirdPartyServiceContactsList = thirdPartyServiceContactsList;
    }
}