package org.library.client;

import org.library.domain.Book;

import javax.swing.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchByIdFrame extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private JButton searchButton;

    private Client client;

    public SearchByIdFrame() {
        // Создаем клиента для отправки HTTP-запросов
        client = ClientBuilder.newClient();
        initUI();
    }

    private void initUI() {
        // Устанавливаем заголовок окна
        setTitle("Поиск по ID - 2 лабораторная работа - Струков Д.М.");
        // Устанавливаем размер окна
        setSize(850, 300);
        // Устанавливаем действие при закрытии окна
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Устанавливаем положение окна по центру экрана
        setLocationRelativeTo(null);

        // Создаем панель для размещения компонентов
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Создаем текстовое поле для ввода ID книги
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
                // Выполняем поиск книги по ID
                searchBookById();
            }
        });

        // Добавляем компоненты на панель
        panel.add(new JLabel("Поиск по ID:"), BorderLayout.NORTH);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.SOUTH);

        // Создаем скролл-панель для текстовой области
        JScrollPane scrollPane = new JScrollPane(resultArea);
        // Добавляем панель и скролл-панель на главное окно
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void searchBookById() {
        // Получаем текст из поля ввода
        String idText = searchField.getText();
        // Проверяем, что поле ID не пустое
        if (idText.isEmpty()) {
            resultArea.setText("ID не может быть пустым");
            return;
        }

        // Преобразуем текст в число
        int id = Integer.parseInt(idText);
        // Отправляем GET-запрос к сервису для поиска книги по ID
        Response response = client.target("http://localhost:8080/JavaLab/rest/book/fetch/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get();

        // Проверяем статус ответа
        if (response.getStatus() == 200) {
            // Получаем книгу из ответа
            Book book = response.readEntity(Book.class);
            // Выводим информацию о книге в текстовую область
            resultArea.setText(book.toString());
        } else {
            // Выводим сообщение об ошибке
            resultArea.setText("Ошибка: " + response.getStatus());
        }
    }
}