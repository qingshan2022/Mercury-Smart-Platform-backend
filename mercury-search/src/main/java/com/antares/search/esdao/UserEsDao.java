package com.mercury.search.esdao;

import com.mercury.search.model.dto.user.UserEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserEsDao extends ElasticsearchRepository<UserEsDTO, Long> {
    List<UserEsDTO> findByUsername(String username);
    List<UserEsDTO> findByUid(Long uid);
}
