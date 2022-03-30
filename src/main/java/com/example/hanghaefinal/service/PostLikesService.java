package com.example.hanghaefinal.service;

import com.example.hanghaefinal.dto.requestDto.PostLikesRequestDto;
import com.example.hanghaefinal.dto.responseDto.PostLikeClickersResponseDto;
import com.example.hanghaefinal.dto.responseDto.PostLikesResponseDto;
import com.example.hanghaefinal.model.Alarm;
import com.example.hanghaefinal.model.Post;
import com.example.hanghaefinal.model.PostLikes;
import com.example.hanghaefinal.model.User;
import com.example.hanghaefinal.repository.AlarmRepository;
import com.example.hanghaefinal.repository.PostLikesRepository;
import com.example.hanghaefinal.repository.PostRepository;
import com.example.hanghaefinal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostLikesService {

    private final PostLikesRepository postLikesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AlarmService alarmService;
    private final AlarmRepository alarmRepository;

    //좋아요 등록
    @Transactional
    public PostLikesResponseDto addLike(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저정보가 없습니다.")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );

        PostLikes findLike = postLikesRepository.findByUserAndPost(user, post).orElse(null);

        //좋아요가 되어있는지 아닌지 체크해서 등록/해제
        if (findLike == null) {
            PostLikesRequestDto postLikesRequestDto = new PostLikesRequestDto(user, post);
            PostLikes postLikes = new PostLikes(postLikesRequestDto);
            postLikesRepository.save(postLikes);

            // 내가 참여한 게시글에 좋아요를 받았을 때
            log.info("---------------------- 444444aaaa ----------------------");
            alarmService.generatePostLikesAlarm(post);

            List<Alarm> alarmList = alarmRepository.findAllByUserId(user.getId()); //asc로 가져옴

            if(alarmList.size() > 20){
                Alarm oldAlarm = alarmList.stream().findFirst().orElseThrow(
                        () -> new IllegalArgumentException("알람이 존재하지 않습니다.")
                );
                alarmList.remove(oldAlarm);
            }

        } else {
            postLikesRepository.deleteById(findLike.getId());
        }

        List<PostLikes> postLikes = postLikesRepository.findAllByPostId(postId);
        List<PostLikeClickersResponseDto> postLikeClickersResponseDtos = new ArrayList<>();
        for (PostLikes postLikesTemp : postLikes) {
            postLikeClickersResponseDtos.add(new PostLikeClickersResponseDto(postLikesTemp));
        }

        return  new PostLikesResponseDto(postId, postLikeClickersResponseDtos,postLikesRepository.countByPost(post));
    }
}
