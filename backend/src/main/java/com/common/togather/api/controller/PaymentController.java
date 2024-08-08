package com.common.togather.api.controller;

import com.common.togather.api.response.ErrorResponseDto;
import com.common.togather.api.response.PaymentFindByPlanIdResponse;
import com.common.togather.api.response.ResponseDto;
import com.common.togather.api.service.PaymentService;
import com.common.togather.common.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams/{teamId}/plans/{planId}")
@RequiredArgsConstructor
public class PaymentController {

    private final JwtUtil jwtUtil;
    private final PaymentService paymentService;

    //정산 내역 조회
    @Operation(summary = "정산 내역 조회")
    @GetMapping("/payments")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정산 내역 조회를 성공했습니다."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "2팀에 user1@example.com유저가 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 일정은 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),

    })
    public ResponseEntity<ResponseDto<PaymentFindByPlanIdResponse>> findPaymentByPlanId(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable(name = "teamId") int teamId,
            @PathVariable(name = "planId") int planId) {

        ResponseDto<PaymentFindByPlanIdResponse> responseDto = ResponseDto.<PaymentFindByPlanIdResponse>builder()
                .status(HttpStatus.OK.value())
                .message("정산 내역 조회를 성공했습니다.")
                .data(paymentService.findPaymentByPlanId(
                        jwtUtil.getAuthMemberEmail(token),
                        teamId,
                        planId))
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}