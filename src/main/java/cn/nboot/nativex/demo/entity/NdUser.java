package cn.nboot.nativex.demo.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class NdUser {

    @Id
    private Long id;

    private String name;

    private String email;
    private String wechatOpenid;
    private String description;
    private LocalDateTime createTime;


}
