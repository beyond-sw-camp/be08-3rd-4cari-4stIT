package com.fourstit.pocari.controller;

import com.fourstit.pocari.dto.BookmarkDto;
import com.fourstit.pocari.dto.BookmarkRequestDto;
import com.fourstit.pocari.dto.NewsDto;
import com.fourstit.pocari.entity.Bookmark;
import com.fourstit.pocari.entity.News;
import com.fourstit.pocari.entity.User;
import com.fourstit.pocari.repository.BookmarkRepository;
import com.fourstit.pocari.repository.NewsRepository;
import com.fourstit.pocari.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173/")
public class MainController {

    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final BookmarkRepository bookmarkRepository;


    @GetMapping("/main")
    public List<News> newsList() {
        return newsRepository.findAll();
    }

    @GetMapping("/bookmark/{userNo}")
    public List<BookmarkDto> bookmarkList(@PathVariable("userNo") Long userId) {

        return  bookmarkRepository.findAllByUserId(userId);
    }

    @PostMapping("/createbookmark")
    public ResponseEntity<String> createBookmark(@RequestBody BookmarkRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자: " + requestDto.getUserId()));
        News news = newsRepository.findById(requestDto.getNewsId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 뉴스: " + requestDto.getNewsId()));

        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(user.getUserNo());
        bookmark.setNewsId(news.getNewsId());
        bookmark.setCreatedDate(LocalDateTime.now());
        bookmarkRepository.save(bookmark);

        return ResponseEntity.ok("");
    }

    @GetMapping("/detail/{newsNo}")
    public NewsDto getNewsById(@PathVariable("newsNo") Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 뉴스: " + newsId));
        return NewsDto.fromEntity(news);
    }

    //Todo: 뉴스 작성, 여유있으면 프론트 구현 @uzz99
//    @PostMapping("/createnews")
//    public void createNews(@RequestBody NewsDto newsDTO) {
//        News news = newsDTO.toEntity();
//        newsRepository.save(news);
//    }
}
