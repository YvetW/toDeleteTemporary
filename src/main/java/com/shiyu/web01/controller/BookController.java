package com.shiyu.web01.controller;

import com.shiyu.web01.model.vo.Book;
import com.shiyu.web01.model.vo.BookFilter;
import com.shiyu.web01.model.vo.Result;
import com.shiyu.web01.util.BookStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(BookController.BASE_URL)
public class BookController {
    public static final String BASE_URL = "/api";

    private BookStore bookStore;

    @Autowired
    public BookController(BookStore bookStore) {
        this.bookStore = bookStore;
    }

    @GetMapping("/getbooks")
    public Result getBooks(@RequestParam(value = "id", required = false) Integer id,
                           @RequestParam(value = "bookname", required = false) String bookname,
                           @RequestParam(value = "author", required = false) String author,
                           @RequestParam(value = "publisher", required = false) String publisher) {

        BookFilter filter = new BookFilter();

        if (id == null && StringUtils.isEmpty(bookname) && StringUtils.isEmpty(author) && StringUtils.isEmpty(publisher)) {
            filter.setEmpty(true);
        } else {
            filter.setId(id);
            filter.setBookname(bookname);
            filter.setAuthor(author);
            filter.setPublisher(publisher);
            filter.setEmpty(false);
        }

        List<Book> books = bookStore.filterBook(filter);
        return new Result(200, "获取成功", books);
    }
}
