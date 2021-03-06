package com.example.nuvi_demo.domain.member;//<<<<<<< HEAD
//package com.example.nuvi_demo.domain.member;

import com.example.nuvi_demo.domain.post.Post;
import com.example.nuvi_demo.domain.recommend.Recommend;
import com.example.nuvi_demo.domain.region.Region;
import com.example.nuvi_demo.domain.reply.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "member")
@DynamicInsert
@DynamicUpdate
public class Member {
    @Id
    private String user_id;
    @Version
    @Transient
    @Column(insertable = false, updatable = false)
    private Long version;
    // N : 1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable=false, updatable=false)
    private Region region;
    // 1 : N 관계
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", insertable=false, updatable=false)
    private List<Post> postList;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Recommend> recommendList;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Reply> replyList;
    private String nick_name;
    private String password;
    private String email;
    private Integer age;
    private LocalDateTime create_dt;
    private LocalDateTime update_dt;
    private int is_del;
    private int region_id;

    @Builder
    public Member(String user_id, String nick_name, String password, String email, int age, int region_id) {
        this.user_id = user_id;
        this.nick_name = nick_name;
        this.password = password;
        this.email = email;
        this.age = age;
        this.region_id = region_id;
    }

    @Override
    public String toString() {
        return "nick_name : " + getNick_name() + "\n" + "email : " + getEmail() + "\n" + "age : " + getAge();
    }

    public void update(String user_id, String nick_name, String email) {
        this.user_id = user_id;
        this.nick_name = nick_name;
        this.email = email;
    }
}
//=======
//package com.example.nuvi_demo.domain.member;
//
//import com.example.nuvi_demo.domain.post.Post;
//import com.example.nuvi_demo.domain.recommend.Recommend;
//import com.example.nuvi_demo.domain.region.Region;
//import com.example.nuvi_demo.domain.reply.Reply;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Getter
//@Entity
//@NoArgsConstructor
//@Table(name = "member")
//@DynamicInsert
//@DynamicUpdate
//public class Member {
//    @Id
//    private String user_id;
//    @Version
//    @Transient
//    @Column(insertable = false, updatable = false)
//    private Long version;
//    // N : 1 관계
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "region_id", insertable=false, updatable=false)
//    private Region region;
//    // 1 : N 관계
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id", insertable=false, updatable=false)
//    private List<Post> postList;
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "idx")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<Recommend> recommendList;
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "idx")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<Reply> replyList;
//    private String nick_name;
//    private String password;
//    private String email;
//    private int age;
//    private LocalDateTime create_dt;
//    private LocalDateTime update_dt;
//    private int is_del;
//    private int region_id;
//
//    @Builder
//    public Member(String user_id, String nick_name, String password, String email, int age, int region_id) {
//        this.user_id = user_id;
//        this.nick_name = nick_name;
//        this.password = password;
//        this.email = email;
//        this.age = age;
//        this.region_id = region_id;
//    }
//
//    @Override
//    public String toString() {
//        return "nick_name : " + getNick_name() + "\n" + "email : " + getEmail() + "\n" + "age : " + getAge();
//    }
//
//    public void update(String user_id, String nick_name, String email) {
//        this.user_id = user_id;
//        this.nick_name = nick_name;
//        this.email = email;
//    }
//}
//>>>>>>> 650992229e08e4f7b83e8049b8f9c3badcc70b48
