package cn.nboot.nativedemo.repository;

import cn.nboot.nativedemo.entity.NdUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NdUserRepository extends PagingAndSortingRepository<NdUser, String> {
}
