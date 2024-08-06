package com.common.togather.api.service;

import com.common.togather.api.error.*;
import com.common.togather.api.request.TeamJoinSaveRequest;
import com.common.togather.api.request.TeamSaveRequest;
import com.common.togather.api.request.TeamUpdateRequest;
import com.common.togather.api.response.*;
import com.common.togather.db.entity.*;
import com.common.togather.db.repository.MemberRepository;
import com.common.togather.db.repository.TeamJoinRepository;
import com.common.togather.db.repository.TeamMemberRepository;
import com.common.togather.db.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamJoinRepository teamJoinRepository;

    // 모임 생성
    public TeamSaveResponse saveTeam(String email, TeamSaveRequest requestDto) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("해당 유저가 존재하지 않습니다."));

        String code;
        do {
            code = generateCode();
        } while (teamRepository.existsByCode(code));

        Team team = Team.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .teamImg(requestDto.getTeamImg())
                .code(code)
                .build();
        teamRepository.save(team);

        teamMemberRepository.save(TeamMember.builder()
                .role(1)
                .team(team)
                .member(member)
                .build());

        return TeamSaveResponse.builder()
                .teamId(team.getId())
                .build();
    }

    // 모임 수정
    public void updateTeam(int teamId, TeamUpdateRequest requestDto) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("해당 모임이 존재하지 않습니다."));

        team.updateTeam(requestDto.getTitle(), requestDto.getDescription(), requestDto.getTeamImg());
        teamRepository.save(team);
    }

    // 랜덤 코드 생성
    public static String generateCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }

    // 내가 속한 모임 조회
    public List<TeamFindAllByMemberIdResponse> findAllTeamByMemberId(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("해당 유저가 존재하지 않습니다."));

        List<TeamMember> teamMembers = teamMemberRepository.findByMember(member);
        return teamMembers.stream()
                .map(teamMember -> {
                    Team team = teamMember.getTeam();
                    return TeamFindAllByMemberIdResponse.builder()
                            .teamId(team.getId())
                            .title(team.getTitle())
                            .teamImg(team.getTeamImg())
                            .description(team.getDescription())
                            .build();
                })
                .collect(Collectors.toList());
    }

    // 모임 상세 조회
    public TeamFindByTeamIdResponse findTeamByTeamId(String email, Integer teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("해당 모임이 존재하지 않습니다."));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("해당 유저가 존재하지 않습니다."));

        // 모임장이라면 true 반환
        boolean isAdmin = team.getTeamMembers().stream()
                .anyMatch(teamMember -> teamMember.getMember().getId() == member.getId() && teamMember.getRole() == 1);

        List<PlanFindAllByTeamIdResponse> plans = team.getPlans().stream()
                .map(plan -> new PlanFindAllByTeamIdResponse(plan.getId(), plan.getTitle()))
                .collect(Collectors.toList());

        return TeamFindByTeamIdResponse.builder()
                .title(team.getTitle())
                .teamImg(team.getTeamImg())
                .description(team.getDescription())
                .code(team.getCode())
                .isAdmin(isAdmin)
                .plans(plans)
                .build();
    }

    // 모임 참여 요청
    public void joinTeamByCode(String email, TeamJoinSaveRequest requestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("해당 유저가 존재하지 않습니다."));
        Team team = teamRepository.findByCode(requestDto.getCode())
                .orElseThrow(() -> new TeamNotFoundException("코드에 해당하는 모임이 없습니다."));

        if (teamMemberRepository.existsByMemberAndTeam(member, team)) {
            throw new AlreadyJoinedTeamException("가입된 모임입니다.");
        }

        TeamJoin teamJoin = teamJoinRepository.findByMemberAndTeam(member, team);

        if (teamJoin != null) {
            if (teamJoin.getStatus() == 0) {
                throw new AlreadyJoinedTeamException("가입 요청이 완료된 모임입니다.");
            }
            if (teamJoin.getStatus() == 1) {
                throw new TeamJoinBlockedException("이 모임에는 더 이상 참여할 수 없습니다.");
            }
        }

        teamJoinRepository.save(TeamJoin.builder()
                .team(team)
                .member(member)
                .status(0)
                .build());
    }

    // 모임 참여 요청 조회
    public List<TeamJoinFindAllByTeamIdResponse> findTeamJoinByTeamId(String email, Integer teamId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("해당 유저가 존재하지 않습니다."));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("해당하는 모임이 없습니다."));
        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team);

        if (teamMember.getRole() != 1) { // 방장이 아닌 경우
            throw new NotTeamLeaderException("모임 방장이 아니므로 요청을 조회할 수 없습니다.");
        }

        List<TeamJoin> teamJoins = teamJoinRepository.findByTeam(team);
        return teamJoins.stream()
                .map(teamJoin -> TeamJoinFindAllByTeamIdResponse.builder()
                        .teamId(teamJoin.getTeam().getId())
                        .memberId(teamJoin.getMember().getId())
                        .nickname(teamJoin.getMember().getNickname())
                        .status(teamJoin.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    // 모임 참여 인원 조회
    public List<TeamMemberFindAllByTeamIdResponse> findAllTeamMemberByTeamId(Integer teamId) {
        List<TeamMember> teamMembers = teamMemberRepository.findByTeamId(teamId);

        return teamMembers.stream()
                .map(teamMember -> TeamMemberFindAllByTeamIdResponse.builder()
                        .memberId(teamMember.getMember().getId())
                        .nickname(teamMember.getMember().getNickname())
                        .role(teamMember.getRole())
                        .profileImg(teamMember.getMember().getProfileImg())
                        .build())
                .collect(Collectors.toList());
    }

    // 모임 참여 요청 수락 또는 거절
    public void acceptOrRejectTeamJoin(String email, Integer teamId, Integer guestId, boolean flag) {
        Member host = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email + " 유저가 존재하지 않습니다."));
        Member guest = memberRepository.findById(guestId)
                .orElseThrow(() -> new MemberNotFoundException(guestId + " 유저가 존재하지 않습니다."));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("해당하는 모임이 없습니다."));
        TeamMember teamMemberHost = teamMemberRepository.findByMemberAndTeam(host, team);
        TeamJoin teamJoin = teamJoinRepository.findByMemberAndTeam(guest, team);

        if (teamMemberHost.getRole() != 1) {
            throw new NotTeamLeaderException("모임 방장이 아닙니다.");
        }

        teamJoinRepository.delete(teamJoin);
        teamJoinRepository.flush();

        if (flag) {
            teamMemberRepository.save(TeamMember.builder()
                    .member(guest)
                    .team(team)
                    .role(0)
                    .build());
        }
    }

    // 모임 나가기 구현
    public void deleteTeamMember(String email, Integer teamId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("해당 유저가 존재하지 않습니다."));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("해당 모임이 존재하지 않습니다."));
        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team);

        teamMemberRepository.deleteByMemberAndTeam(member, team);
        teamMemberRepository.flush();
    }

    // 모임 삭제
    public void deleteTeamByTeamId(String email, Integer teamId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("해당 유저가 존재하지 않습니다."));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("해당 모임이 존재하지 않습니다."));
        TeamMember teamMember = teamMemberRepository.findByMemberAndTeam(member, team);

        if (teamMember.getRole() != 1) {
            throw new NotTeamLeaderException("모임 삭제는 방장만 가능합니다.");
        }

        boolean hasActivePlans = team.getPlans().stream()
                .anyMatch(plan -> plan.getStatus() == 0);

        if (hasActivePlans) {
            throw new PlansExistException("모임에 활성화된 일정이 있어 삭제할 수 없습니다.");
        }

        teamRepository.delete(team);
        teamRepository.flush();
    }

    // 모임에서 추방
    public void deleteMemberFromTeam(String email, Integer teamId, Integer guestId) {
        Member host = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email + " 유저가 존재하지 않습니다."));
        Member guest = memberRepository.findById(guestId)
                .orElseThrow(() -> new MemberNotFoundException(guestId + " 유저가 존재하지 않습니다."));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("해당하는 모임이 없습니다."));
        TeamMember teamMemberHost = teamMemberRepository.findByMemberAndTeam(host, team);
        TeamMember teamMemberGuest = teamMemberRepository.findByMemberAndTeam(guest, team);

        if (teamMemberHost.getRole() != 1) {
            throw new NotTeamLeaderException("모임 방장만 추방이 가능합니다.");
        }
        if (teamMemberGuest == null) {
            throw new MemberNotInTeamException(guestId + "가 모임에 없습니다.");
        }

        teamMemberRepository.delete(teamMemberGuest);
        teamMemberRepository.flush();

        teamJoinRepository.save(TeamJoin.builder()
                .team(team)
                .member(guest)
                .status(1)
                .build());
    }
}
