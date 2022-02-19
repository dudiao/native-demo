package cn.nboot.nativedemo.controller;

import cn.nboot.nativedemo.entity.NdUser;
import cn.nboot.nativedemo.repository.NdUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NdUserController {

    @Autowired
    private NdUserRepository userRepository;

    @GetMapping("user/list")
    public List<NdUser> list() {
        Page<NdUser> all = userRepository.findAll(Pageable.ofSize(10));
        return all.getContent();
    }
}
