package org.example.library.client;

import org.example.library.domain.Book;

import javax.swing.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;

public class LibraryClient extends JFrame {
    private JTextField titleField;
    private JTextArea resultArea;

    public LibraryClient() {
        setTitle("Библиотека");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Создаем панель для ввода
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Название книги:"));
        titleField = new JTextField();
        inputPanel.add(titleField);
        add(inputPanel, BorderLayout.NORTH);

        // Создаем кнопку для поиска
        JButton searchButton = new JButton("Поиск книги");
        searchButton.addActionListener(e -> searchBook());
        add(searchButton, BorderLayout.CENTER);

        // Создаем текстовую область для отображения результатов
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void searchBook() {
        String title = titleField.getText();
        Client client = ClientBuilder.newClient();
        List<Book> books = client.target("http://localhost:8080/rest/book/list")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Book>>() {});
        displayBooks(books);
    }

    private void displayBooks(List<Book> books) {
        resultArea.setText("");
        for (Book book : books) {
            resultArea.append("Название: " + book.getTitle() + "\n");
            resultArea.append("Автор: " + book.getAuthor() + "\n");
            resultArea.append("Местоположение: " + book.getLocation() + "\n");
            resultArea.append("Арендуется: " + book.getIssued() + "\n");
            resultArea.append("Только в читальном зале: " + book.getReadingRoom() + "\n\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryClient client = new LibraryClient();
            client.setVisible(true);
        });
    }
}