package com.fourstit.pocari.controller;

import com.fourstit.pocari.dto.BookmarkDto;
import com.fourstit.pocari.entity.News;
import com.fourstit.pocari.repository.BookmarkRepository;
import com.fourstit.pocari.repository.NewsRepository;
import com.fourstit.pocari.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

}
