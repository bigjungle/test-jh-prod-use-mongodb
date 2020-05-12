package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Training institution registration revocation 培训机构资质吊销表\n@author JasonYang
 */
@Document(collection = "regn_revo")
public class RegnRevo implements Serializable {

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
     * 资质证书编号
     */
    @NotNull
    @Size(max = 4000)
    @Field("org_info")
    private String orgInfo;

    /**
     * 吊销时长
     */
    @NotNull
    @Field("revoke_time_span")
    private Integer revokeTimeSpan;

    /**
     * 吊销开始
     */
    @NotNull
    @Field("revoke_start")
    private LocalDate revokeStart;

    /**
     * 吊销结束
     */
    @NotNull
    @Field("revoke_over")
    private LocalDate revokeOver;

    /**
     * 处罚机关
     */
    @Size(max = 256)
    @Field("punish_org")
    private String punishOrg;

    /**
     * 处罚时间
     */
    @Field("punish_time")
    private Instant punishTime;

    /**
     * 事实依据
     */
    @Size(max = 256)
    @Field("facts")
    private String facts;

    /**
     * 期满自动处理(是否自动恢复)
     */
    @Field("auto_process")
    private Boolean autoProcess;

    /**
     * 凭证上传(存储路径)
     */
    @Size(max = 256)
    @Field("revoke_proof")
    private String revokeProof;

    /**
     * 备注
     */
    @Size(max = 256)
    @Field("remarks")
    private String remarks;

    /**
     * 处罚人
     */
    @DBRef
    @Field("punishPerson")
    @JsonIgnoreProperties("regnRevos")
    private User punishPerson;

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

    public RegnRevo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescString() {
        return descString;
    }

    public RegnRevo descString(String descString) {
        this.descString = descString;
        return this;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public String getOrgInfo() {
        return orgInfo;
    }

    public RegnRevo orgInfo(String orgInfo) {
        this.orgInfo = orgInfo;
        return this;
    }

    public void setOrgInfo(String orgInfo) {
        this.orgInfo = orgInfo;
    }

    public Integer getRevokeTimeSpan() {
        return revokeTimeSpan;
    }

    public RegnRevo revokeTimeSpan(Integer revokeTimeSpan) {
        this.revokeTimeSpan = revokeTimeSpan;
        return this;
    }

    public void setRevokeTimeSpan(Integer revokeTimeSpan) {
        this.revokeTimeSpan = revokeTimeSpan;
    }

    public LocalDate getRevokeStart() {
        return revokeStart;
    }

    public RegnRevo revokeStart(LocalDate revokeStart) {
        this.revokeStart = revokeStart;
        return this;
    }

    public void setRevokeStart(LocalDate revokeStart) {
        this.revokeStart = revokeStart;
    }

    public LocalDate getRevokeOver() {
        return revokeOver;
    }

    public RegnRevo revokeOver(LocalDate revokeOver) {
        this.revokeOver = revokeOver;
        return this;
    }

    public void setRevokeOver(LocalDate revokeOver) {
        this.revokeOver = revokeOver;
    }

    public String getPunishOrg() {
        return punishOrg;
    }

    public RegnRevo punishOrg(String punishOrg) {
        this.punishOrg = punishOrg;
        return this;
    }

    public void setPunishOrg(String punishOrg) {
        this.punishOrg = punishOrg;
    }

    public Instant getPunishTime() {
        return punishTime;
    }

    public RegnRevo punishTime(Instant punishTime) {
        this.punishTime = punishTime;
        return this;
    }

    public void setPunishTime(Instant punishTime) {
        this.punishTime = punishTime;
    }

    public String getFacts() {
        return facts;
    }

    public RegnRevo facts(String facts) {
        this.facts = facts;
        return this;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public Boolean isAutoProcess() {
        return autoProcess;
    }

    public RegnRevo autoProcess(Boolean autoProcess) {
        this.autoProcess = autoProcess;
        return this;
    }

    public void setAutoProcess(Boolean autoProcess) {
        this.autoProcess = autoProcess;
    }

    public String getRevokeProof() {
        return revokeProof;
    }

    public RegnRevo revokeProof(String revokeProof) {
        this.revokeProof = revokeProof;
        return this;
    }

    public void setRevokeProof(String revokeProof) {
        this.revokeProof = revokeProof;
    }

    public String getRemarks() {
        return remarks;
    }

    public RegnRevo remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public User getPunishPerson() {
        return punishPerson;
    }

    public RegnRevo punishPerson(User user) {
        this.punishPerson = user;
        return this;
    }

    public void setPunishPerson(User user) {
        this.punishPerson = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegnRevo)) {
            return false;
        }
        return id != null && id.equals(((RegnRevo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RegnRevo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descString='" + getDescString() + "'" +
            ", orgInfo='" + getOrgInfo() + "'" +
            ", revokeTimeSpan=" + getRevokeTimeSpan() +
            ", revokeStart='" + getRevokeStart() + "'" +
            ", revokeOver='" + getRevokeOver() + "'" +
            ", punishOrg='" + getPunishOrg() + "'" +
            ", punishTime='" + getPunishTime() + "'" +
            ", facts='" + getFacts() + "'" +
            ", autoProcess='" + isAutoProcess() + "'" +
            ", revokeProof='" + getRevokeProof() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
