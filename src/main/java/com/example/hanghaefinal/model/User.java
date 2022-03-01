package com.example.hanghaefinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class User extends Timestamped{

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore // pw json에서 숨김처리
    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String nickName;

    @Column(nullable = true)
    private String userProfileImage;

    @Column(nullable = true)
    private String introduction;

    public User(String username, String password ) {
        this.username = username;
        this.password = password;
    }

}