package com.bibliotech.repository.jdbc;

import com.bibliotech.domain.Book;
import com.bibliotech.repository.BookRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcBookRepository implements BookRepository {
    private static final Logger logger = Logger.getLogger(JdbcBookRepository.class.getName());
    private final Connection connection;

    public JdbcBookRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Book save(Book book) {
        String sql = "insert into book (title, author, isbn, category_id, year) values (?, ?, ?, ?, ?) returning id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            if (book.getCategoryId() != null) {
                ps.setLong(4, book.getCategoryId());
            } else {
                ps.setNull(4, Types.BIGINT);
            }
            if (book.getYear() != null) {
                ps.setInt(5, book.getYear());
            } else {
                ps.setNull(5, Types.SMALLINT);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    book.setId(rs.getLong("id"));
                }
            }
            logger.info("libro guardado exitosamente");
            return book;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "error al guardar el libro", e);
            throw new RuntimeException("no se pudo guardar el libro", e);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        String sql = "select id, title, author, isbn, category_id, year from book where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn"),
                            rs.getObject("category_id") != null ? rs.getLong("category_id") : null,
                            rs.getObject("year") != null ? rs.getInt("year") : null);
                    return Optional.of(book);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "error al buscar el libro por id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        String sql = "select id, title, author, isbn, category_id, year from book";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getObject("category_id") != null ? rs.getLong("category_id") : null,
                        rs.getObject("year") != null ? rs.getInt("year") : null);
                list.add(book);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "error al listar los libros", e);
        }
        return list;
    }

    @Override
    public void deleteById(long id) {
        String sql = "delete from book where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            logger.info("libro eliminado con id: " + id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "error al eliminar el libro", e);
            throw new RuntimeException("no se pudo eliminar el libro", e);
        }
    }
}