package com.goldfish666.virus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/6 9:12
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaSpecificationExecutor<T>, JpaRepository<T, ID> {
}
