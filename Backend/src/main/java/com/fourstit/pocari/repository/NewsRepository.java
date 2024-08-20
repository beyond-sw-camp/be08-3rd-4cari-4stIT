package com.fourstit.pocari.repository;

import com.fourstit.pocari.entity.News;
import com.fourstit.pocari.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT n FROM News n WHERE n.categoryId IN :categoryIds")
    List<News> findByCategoryIdIn(@Param("categoryIds") List<Integer> categoryIds);

    List<News> findTop3ByOrderByViewsDesc();
}
