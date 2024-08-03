package com.common.togather.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Bookmark {

    // pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 일정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    // 장소 사진
    @Column(name = "place_img", nullable = true)
    private String place_img;

    // 장소명
    @Column(name = "place_name", nullable = false)
    private String place_name;

    // 장소 주소
    @Column(name = "place_addr", nullable = true)
    private String place_addr;

    // 일자
    @Column(name = "day", nullable = false)
    private Integer day;

    // 순서
    @Column(name = "item_order", nullable = false)
    private Integer itemOrder;

    // 영수증
    @OneToMany(mappedBy = "bookmark", cascade = CascadeType.ALL)
    private List<Receipt> receipts;
}


