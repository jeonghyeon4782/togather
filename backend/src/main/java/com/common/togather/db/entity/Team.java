package com.common.togather.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Team {

    // pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 모임명
    @Column(name = "title", nullable = false)
    private String title;

    // 대표 사진
    @Column(name = "group_img", nullable = false)
    private String groupImg;

    // 소개
    @Column(name = "description", nullable = false)
    private String description;

    // 모임 코드
    @Column(name = "code", nullable = false)
    private String code;

    // 모임 유저
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> teamMembers;

    // 일정
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plan> plans;
}
