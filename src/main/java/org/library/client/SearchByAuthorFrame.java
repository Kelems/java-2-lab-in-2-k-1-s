package org.library.client;

import org.library.domain.Book;

import javax.swing.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchByAuthorFrame extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private JButton searchButton;

    private Client client;

    public SearchByAuthorFrame() {
        // Создаем клиента для отправки HTTP-запросов
        client = ClientBuilder.newClient();
        initUI();
    }

    private void initUI() {
        // Устанавливаем заголовок окна
        setTitle("Поиск по автору - 2 лабораторная работа - Струков Д.М.");
        // Устанавливаем размер окна
        setSize(850, 300);
        // Устанавливаем действие при закрытии окна
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Устанавливаем положение окна по центру экрана
        setLocationRelativeTo(null);

        // Создаем панель для размещения компонентов
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Создаем текстовое поле для ввода автора книги
        searchField = new JTextField();
        // Создаем текстовую область для отображения результатов
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        // Создаем кнопку для поиска
        searchButton = new JButton("Поиск");

        // Добавляем слушатель событий для кнопки поиска
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Выполняем поиск книг по автору
                searchBooksByAuthor();
            }
        });

        // Добавляем компоненты на панель
        panel.add(new JLabel("Поиск по автору:"), BorderLayout.NORTH);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.SOUTH);

        // Создаем скролл-панель для текстовой области
        JScrollPane scrollPane = new JScrollPane(resultArea);
        // Добавляем панель и скролл-панель на главное окно
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void searchBooksByAuthor() {
        // Получаем текст из поля ввода
        String author = searchField.getText();
        // Отправляем GET-запрос к сервису для поиска книг по автору
        Response response = client.target("http://localhost:8080/JavaLab/rest/book/search/author")
                .queryParam("author", author)
                .request(MediaType.APPLICATION_JSON)
                .get();

        // Проверяем статус ответа
        if (response.getStatus() == 200) {
            // Получаем список книг из ответа
            List<Book> books = response.readEntity(new GenericType<List<Book>>() {});
            // Очищаем текстовую область
            resultArea.setText("");
            // Добавляем информацию о каждой книге в текстовую область
            for (Book book : books) {
                resultArea.append(book.toString() + "\n");
            }
        } else {
            // Выводим сообщение об ошибке
            resultArea.setText("Ошибка: " + response.getStatus());
        }
    }
}