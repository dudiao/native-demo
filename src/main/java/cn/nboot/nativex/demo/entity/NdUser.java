package cn.nboot.nativex.demo.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
public class NdUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(length = 128)
  private String email;

  @Column(length = 128)
  private String wechatOpenid;

//  @Column(columnDefinition = "text comment '说明'")
  private String description;

  @CreatedDate
  private LocalDateTime createTime;

}
