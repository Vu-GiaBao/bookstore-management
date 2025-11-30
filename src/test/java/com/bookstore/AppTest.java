package com.bookstore;

import com.bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigourous Test :-)
     */
    @Test
    public void testGetAllBooks() {
        BookService bookService = new BookService();
        assertNotNull(bookService.getAllBooks());
    }
}
