package com.ohgiraffers.section06.compositekey.subsection01.embedded;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


// 임베드 될 수 있는 복합 키 타입을 지정할 떄 사용하는 어노테이션
@Embeddable
public class MemberPk implements Serializable {
    @Column(name = "member_no")
    private int memberNo;

    @Column(name = "member_id")
    private String memberId;

    public MemberPk() {
    }

    public MemberPk(int memberNo, String memberId) {
        this.memberNo = memberNo;
        this.memberId = memberId;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberPk memberPk = (MemberPk) o;
        return memberNo == memberPk.memberNo && Objects.equals(memberId, memberPk.memberId);
    }


    @Override
    public int hashCode() {
        return Objects.hash(memberNo, memberId);
    }

    @Override
    public String toString() {
        return "MemberPk{" +
                "memberNo=" + memberNo +
                ", memberId='" + memberId + '\'' +
                '}';
    }
}
