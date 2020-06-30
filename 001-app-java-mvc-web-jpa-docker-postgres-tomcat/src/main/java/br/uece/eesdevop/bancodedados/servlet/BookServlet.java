package br.uece.eesdevop.bancodedados.servlet;

import br.uece.eesdevop.bancodedados.model.Book;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/books")
@SuppressWarnings("serial")
public class BookServlet extends HttpServlet {

    private Connection connection = null;

    @Override
    public void init() {
        String url = "jdbc:postgresql://database:5432/eesdevops";
        String username = "postgres";
        String password = "postgres";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to database: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find PostgreSQL JDBC driver: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        List<Book> books = new ArrayList<>();
        
        try {
            if (connection != null && !connection.isClosed()) {
                String sql = "SELECT * FROM books;";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    books.add(new Book(id, title, author));
                }

                resultSet.close();
                statement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to fetch books: " + e.getMessage());
        }

        PrintWriter writer = resp.getWriter();
        for (Book book : books) {
            writer.println(book.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (connection != null && !connection.isClosed()) {
                String sql = "INSERT INTO books (id,title, author) VALUES(nextval('hibernate_sequence'), ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, "Fake Title");
                statement.setString(2, "Fake Author");
                statement.execute();
                statement.close();
                
                resp.getWriter().println("Book safe with success!");
            }
            
        } catch (SQLException e) {

            throw new RuntimeException("Unable to save a new book: " + e.getMessage());
        } catch (IOException e) {
        	 throw new RuntimeException("IOException: " + e.getMessage());
		}
    }

    @Override public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to close JDBC connection: " + e.getMessage());
        }
    }
}
