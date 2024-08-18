package com.fourstit.pocari.repository;

import com.fourstit.pocari.dto.BookmarkDto;
import com.fourstit.pocari.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {

    @Query("SELECT new com.fourstit.pocari.dto.BookmarkDto(b.bookmarkId, b.newsId, n.title, n.summary, n.image, b.createdDate) " +
            "FROM Bookmark b JOIN News n ON b.newsId = n.newsId WHERE b.userId = :userId")
    List<BookmarkDto> findAllByUserId(Long userId);

    Optional<Bookmark> findByUserIdAndNewsId(Long userId, Long newsId);

    boolean existsByUserIdAndNewsId(Long userId, Long newsId);

    @Transactional
    void deleteByUserIdAndNewsId(Long userId, Long newsId);
}
