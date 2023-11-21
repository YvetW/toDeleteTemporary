package com.shiyu.web01.util;

import com.shiyu.web01.model.vo.Book;
import com.shiyu.web01.model.vo.BookFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookStore {
    private final List<Book> store;
    public BookStore() {
        store = new ArrayList<>();
        System.out.println("Init book store");
        init();
    }

    public List<Book> filterBook(BookFilter filter) {
        if (filter == null || filter.isEmpty()) {
            return this.store;
        }
        List<Book> res = new ArrayList<>();
        if (filter.getId() != null) {

            this.store.forEach(b -> {
                if (b.getId().equals(filter.getId())) {
                    res.add(b);
                }
            });
        }

        if (filter.getBookname() != null) {

            this.store.forEach(b -> {
                if (b.getBookname().startsWith(filter.getBookname().trim())) {
                    res.add(b);
                }
            });
        }

        if (filter.getAuthor() != null) {

            this.store.forEach(b -> {
                if (b.getAuthor().startsWith(filter.getAuthor().trim())) {
                    res.add(b);
                }
            });
        }

        if (filter.getPublisher() != null) {

            this.store.forEach(b -> {
                if (b.getPublisher().startsWith(filter.getPublisher().trim())) {
                    res.add(b);
                }
            });
        }

        return res;
    }

    public void addBook(Book book) {
        if (book.getId() == null) {
            // set id
            if (store.isEmpty()) {
                book.setId(1);
            } else {
                Book last = store.get(store.size() - 1);
                book.setId(last.getId() + 1);
            }
        }

        this.store.add(book);
    }

    private void init() {
        Book book1 = new Book(1, "镜花缘1", "作者1", "publisher1");
        Book book2 = new Book(2, "镜花缘2", "作者2", "publisher2");
        Book book3 = new Book(3, "镜花缘3", "作者3", "publisher3");
        Book book4 = new Book(4, "镜花缘4", "作者4", "publisher4");
        store.add(book1);
        store.add(book2);
        store.add(book3);
        store.add(book4);
    }

    public void deleteBook(Integer id) {
        for (Book book: store) {
            if (book.getId().equals(id)) {
                store.remove(book);
                break;
            }
        }
    }
}
