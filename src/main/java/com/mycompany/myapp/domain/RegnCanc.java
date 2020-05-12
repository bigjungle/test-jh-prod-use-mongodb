package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Training institution registration cancellation 培训机构登记注销表\n@author JasonYang
 */
@Document(collection = "regn_canc")
public class RegnCanc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 256)
    @Field("name")
    private String name;

    /**
     * 描述
     */
    @Size(max = 256)
    @Field("desc_string")
    private String descString;

    /**
     * 培训机构信息
     */
    @Size(max = 4000)
    @Field("org_info")
    private String orgInfo;

    /**
     * 注销方式(主动,被动)
     */
    @Size(max = 256)
    @Field("cancellation_way")
    private String cancellationWay;

    /**
     * 注销原因
     */
    @Size(max = 256)
    @Field("cancellation_reason")
    private String cancellationReason;

    /**
     * 注销时间
     */
    @Field("cancellation_time")
    private LocalDate cancellationTime;

    /**
     * 注销凭证(存储路径)
     */
    @Size(max = 256)
    @Field("cancellation_proof")
    private String cancellationProof;

    /**
     * 备注
     */
    @Size(max = 256)
    @Field("remarks")
    private String remarks;

    /**
     * 负责人
     */
    @DBRef
    @Field("ownerBy")
    @JsonIgnoreProperties("regnCancs")
    private User ownerBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public RegnCanc name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescString() {
        return descString;
    }

    public RegnCanc descString(String descString) {
        this.descString = descString;
        return this;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public String getOrgInfo() {
        return orgInfo;
    }

    public RegnCanc orgInfo(String orgInfo) {
        this.orgInfo = orgInfo;
        return this;
    }

    public void setOrgInfo(String orgInfo) {
        this.orgInfo = orgInfo;
    }

    public String getCancellationWay() {
        return cancellationWay;
    }

    public RegnCanc cancellationWay(String cancellationWay) {
        this.cancellationWay = cancellationWay;
        return this;
    }

    public void setCancellationWay(String cancellationWay) {
        this.cancellationWay = cancellationWay;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public RegnCanc cancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
        return this;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public LocalDate getCancellationTime() {
        return cancellationTime;
    }

    public RegnCanc cancellationTime(LocalDate cancellationTime) {
        this.cancellationTime = cancellationTime;
        return this;
    }

    public void setCancellationTime(LocalDate cancellationTime) {
        this.cancellationTime = cancellationTime;
    }

    public String getCancellationProof() {
        return cancellationProof;
    }

    public RegnCanc cancellationProof(String cancellationProof) {
        this.cancellationProof = cancellationProof;
        return this;
    }

    public void setCancellationProof(String cancellationProof) {
        this.cancellationProof = cancellationProof;
    }

    public String getRemarks() {
        return remarks;
    }

    public RegnCanc remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public User getOwnerBy() {
        return ownerBy;
    }

    public RegnCanc ownerBy(User user) {
        this.ownerBy = user;
        return this;
    }

    public void setOwnerBy(User user) {
        this.ownerBy = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegnCanc)) {
            return false;
        }
        return id != null && id.equals(((RegnCanc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RegnCanc{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descString='" + getDescString() + "'" +
            ", orgInfo='" + getOrgInfo() + "'" +
            ", cancellationWay='" + getCancellationWay() + "'" +
            ", cancellationReason='" + getCancellationReason() + "'" +
            ", cancellationTime='" + getCancellationTime() + "'" +
            ", cancellationProof='" + getCancellationProof() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
