package com.fourstit.pocari.repository;

import com.fourstit.pocari.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
