package cn.nboot.nativex.demo.controller;

import cn.nboot.nativex.demo.entity.NdUser;
import cn.nboot.nativex.demo.repository.NdUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class NdUserController {

  @Autowired
  private NdUserRepository userRepository;

  @GetMapping("user")
  public ModelAndView index() {
    Page<NdUser> all = userRepository.findAll(Pageable.ofSize(10));
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("userList", all);
    modelAndView.setViewName("user");
    return modelAndView;
  }

  @GetMapping("user/list")
  @ResponseBody
  public List<NdUser> list() {
    Page<NdUser> all = userRepository.findAll(Pageable.ofSize(10));
    return all.getContent();
  }


}
