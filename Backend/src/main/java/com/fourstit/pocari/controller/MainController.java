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

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDto userDto) {
        // 사용자의 ID가 존재하는지 확인
        if (userRepository.existsById(userDto.getId())) {
            // ID로 사용자를 조회 (필요한 경우 쿼리 추가 가능)
            List<User> users = userRepository.findAll(); // 모든 사용자 가져오기
            for (User user : users) {
                if (user.getId().equals(userDto.getId()) && user.getPwd().equals(userDto.getPwd())) {
                    // 로그인 성공 시
                    //return ResponseEntity.ok("Login successful");
                    return ResponseEntity.ok(user);
                }
            }
            // 비밀번호가 일치하지 않을 경우
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            // 사용자가 존재하지 않을 경우
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

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
        log.info("Searching news for {}", userNo);
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
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @GetMapping("/myinfo/{userNo}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable("userNo") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자: " + userId));

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPwd(user.getPwd());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setBirth(user.getBirth());
        userDto.setGender(user.getGender());
        userDto.setPhone(user.getPhone());
        userDto.setInterest(Arrays.asList(user.getInterest().split(",")));

        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/myinfo/update/{userNo}")
    public ResponseEntity<String> updateUserInfo(@PathVariable("userNo") Long userId, @RequestBody UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자: " + userId));

        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setBirth(userDto.getBirth());
        user.setGender(userDto.getGender());
        user.setPhone(userDto.getPhone());
        user.setInterest(String.join(",", userDto.getInterest()));

        userRepository.save(user);
        return ResponseEntity.ok("User information updated successfully");
    }

    @GetMapping("/main/top")
    public List<News> getTop3News() {
        return newsRepository.findTop3ByOrderByViewsDesc();
    }

    //Todo: 뉴스 작성, 여유있으면 프론트 구현 @uzz99
//    @PostMapping("/createnews")
//    public void createNews(@RequestBody NewsDto newsDTO) {
//        News news = newsDTO.toEntity();
//        newsRepository.save(news);
//    }
}
