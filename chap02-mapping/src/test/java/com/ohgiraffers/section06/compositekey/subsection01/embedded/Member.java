package com.ohgiraffers.section06.compositekey.subsection01.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "member_section06_subsection01")
@Table(name = "tbl_member_section06_subsection01")
public class Member {


    @EmbeddedId
    private MemberPk memberPk;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;




    public Member() {
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberPk=" + memberPk +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public MemberPk getMemberPk() {
        return memberPk;
    }

    public void setMemberPk(MemberPk memberPk) {
        this.memberPk = memberPk;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Member(MemberPk memberPk, String phone, String address) {
        this.memberPk = memberPk;
        this.phone = phone;
        this.address = address;
    }
}
