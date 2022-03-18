package cn.nboot.nativex.demo.repository;

import cn.nboot.nativex.demo.entity.NdUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NdUserRepository extends PagingAndSortingRepository<NdUser, String> {
}
