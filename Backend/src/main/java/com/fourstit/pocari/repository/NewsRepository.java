package com.fourstit.pocari.repository;

import com.fourstit.pocari.dto.NewsCategoryDto;
import com.fourstit.pocari.entity.News;
import com.fourstit.pocari.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("SELECT new com.fourstit.pocari.dto.NewsCategoryDto(n.newsId, n.title, n.content, n.categoryId, n.createdDate, n.author, n.newspaper, n.summary, n.image, n.views, c.name) " +
            "FROM News n JOIN Category c ON n.categoryId = c.categoryId " +
            "WHERE n.categoryId IN :categoryIds")
    List<NewsCategoryDto> findByCategoryIdInWithCategoryName(@Param("categoryIds") List<Long> categoryIds);


    List<News> findTop3ByOrderByViewsDesc();

    @Query("SELECT new com.fourstit.pocari.dto.NewsCategoryDto(" +
            "n.newsId, n.title, n.content, n.categoryId, n.createdDate, " +
            "n.author, n.newspaper, n.summary, n.image, n.views, c.name) " +
            "FROM News n JOIN Category c ON n.categoryId = c.categoryId")
    List<NewsCategoryDto> findAllWithCategories();

    @Query("SELECT new com.fourstit.pocari.dto.NewsCategoryDto(n.newsId, n.title, n.content, n.categoryId, n.createdDate, n.author, n.newspaper, n.summary, n.image, n.views, c.name) " +
            "FROM News n JOIN Category c ON n.categoryId = c.categoryId " +
            "WHERE n.title LIKE %:query% OR n.content LIKE %:query% OR c.name LIKE %:query%")
    List<NewsCategoryDto> searchByQuery(@Param("query") String query);
}
