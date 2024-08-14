package com.common.togather.common.websocket.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OneBookmarkMessage {

    private int bookmarkId; // 북마크 아이디
    private LocalDate date; // 북마크 날짜
    private String placeId; // 장소 아이디
    private String placeImg; // 장소 이미지
    private String placeName; // 장소 이름
    private String placeAddr; // 장소 주소
    private int itemOrder; // 순서
    private int receiptCnt; // 맵핑된 영수증의 수

}