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
        setSize(850, 200);                                          // Размер окна
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);                            // Кнопка закрытия
        setLocationRelativeTo(null);                                            // Положение окна (в центре экрана)

        // Создаем панель для размещения компонентов
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Создаем компоненты
        searchField = new JTextField();
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        searchButton = new JButton("Поиск");    // кнопка для поиска

        // Добавляем компоненты на панель
        // часть поиска
        panel.add(new JLabel("Поиск по автору:"), BorderLayout.NORTH);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.SOUTH);
        // текстовая область вывода панели
        JScrollPane scrollPane = new JScrollPane(resultArea);
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Добавляем слушатель событий для кнопки поиска
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Выполняем поиск книг по автору
                searchBooksByAuthor();
            }
        });
    }

    private void searchBooksByAuthor() {
        String author = searchField.getText();
        // Отправляем GET-запрос к сервису для поиска книг по автору
        Response response = client.target("http://localhost:8080/JavaLab/rest/book/search/author")
                .queryParam("author", author)
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == 200) {  // если все успешно будет код 200
            // Получаем список книг из ответа
            List<Book> books = response.readEntity(new GenericType<List<Book>>() {});
            // Добавляем информацию о каждой книге в текстовую область
            resultArea.setText("");
            for (Book book : books) {
                resultArea.append(book.toString() + "\n");
            }
        } else {                            // Что-то пошло не так
            resultArea.setText("Ошибка: " + response.getStatus());
        }
    }
}