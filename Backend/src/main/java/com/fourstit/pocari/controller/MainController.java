package com.fourstit.pocari.controller;

import com.fourstit.pocari.dto.BookmarkDto;
import com.fourstit.pocari.dto.BookmarkRequestDto;
import com.fourstit.pocari.dto.NewsDto;
import com.fourstit.pocari.dto.UserDto;
import com.fourstit.pocari.entity.Bookmark;
import com.fourstit.pocari.entity.Category;
import com.fourstit.pocari.entity.News;
import com.fourstit.pocari.entity.User;
import com.fourstit.pocari.repository.BookmarkRepository;
import com.fourstit.pocari.repository.CategoryRepository;
import com.fourstit.pocari.repository.NewsRepository;
import com.fourstit.pocari.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173/")
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final BookmarkRepository bookmarkRepository;
    private final CategoryRepository categoryRepository;


    @GetMapping("/main")
    public List<News> newsList() {
        return newsRepository.findAll();
    }

    @GetMapping("/main/search")
    public List<News> searchNews(@RequestParam String query) {
        return newsRepository.findAll().stream()
                .filter(news -> news.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        news.getContent().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    @GetMapping("/main/{userNo}")
    public List<News> searchNews(@PathVariable("userNo") Long userNo) {
        User user = userRepository.findById(userNo).orElseThrow();
        List<Integer> categoryIds = Arrays.stream(user.getInterest().split(","))
                .map(Integer::parseInt)
                .toList();

        return newsRepository.findByCategoryIdIn(categoryIds);
    }

    @GetMapping("/bookmark/{userNo}")
    public List<BookmarkDto> bookmarkList(@PathVariable("userNo") Long userId) {

        return bookmarkRepository.findAllByUserId(userId);
    }

    @PostMapping("/createbookmark")
    public ResponseEntity<String> createBookmark(@RequestBody BookmarkRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자: " + requestDto.getUserId()));
        News news = newsRepository.findById(requestDto.getNewsId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 뉴스: " + requestDto.getNewsId()));

        Optional<Bookmark> optionalBookmark = bookmarkRepository.findByUserIdAndNewsId(user.getUserNo(), news.getNewsId());

        System.out.println("optionalBookmark = " + optionalBookmark);
        if (optionalBookmark.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 추가된 뉴스입니다.");
        } else {
            Bookmark bookmark = new Bookmark();
            bookmark.setUserId(user.getUserNo());
            bookmark.setNewsId(news.getNewsId());
            bookmark.setCreatedDate(LocalDateTime.now());
            bookmarkRepository.save(bookmark);

            return ResponseEntity.ok("");
        }
    }

    @Transactional
    @DeleteMapping("/deletebookmark")
    public ResponseEntity<String> deleteBookmark(@RequestParam Long userId, @RequestParam Long newsId) {
        userRepository.findById(userId);
        newsRepository.findById(newsId);

        if (bookmarkRepository.existsByUserIdAndNewsId(userId, newsId)) {
            bookmarkRepository.deleteByUserIdAndNewsId(userId, newsId);
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 북마크에 해당 뉴스가 존재하지 않음");
        }

    }

    @GetMapping("/detail/{newsNo}")
    public NewsDto getNewsById(@PathVariable("newsNo") Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 뉴스: " + newsId));

        news.setViews(news.getViews() + 1);
        newsRepository.save(news);

        return NewsDto.fromEntity(news);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> list = categoryRepository.findAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/user/check-userid")
    public ResponseEntity<Boolean> getChkUserId(@RequestParam String userId) {
        boolean isDuplicate = userRepository.existsById(userId);
        return ResponseEntity.ok(isDuplicate);
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setPwd(userDto.getPwd());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setBirth(userDto.getBirth());
        user.setGender(userDto.getGender());
        user.setPhone(userDto.getPhone());

        StringBuilder interest = new StringBuilder();

        for (String str : userDto.getInterest()) {
            interest.append(str).append(",");
        }

        interest.deleteCharAt(interest.length() - 1);

        user.setInterest(interest.toString());

        userRepository.save(user);

        // 성공 응답
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    //Todo: 뉴스 작성, 여유있으면 프론트 구현 @uzz99
//    @PostMapping("/createnews")
//    public void createNews(@RequestBody NewsDto newsDTO) {
//        News news = newsDTO.toEntity();
//        newsRepository.save(news);
//    }
}
