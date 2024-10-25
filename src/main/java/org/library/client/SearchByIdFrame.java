package org.library.client;

import org.library.domain.Book;

import javax.swing.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchByIdFrame extends JFrame {
    private Client client;

    private JTextField searchField;
    private JTextArea resultArea;
    private JButton searchButton;

    public SearchByIdFrame() {
        // Создаем клиента для отправки HTTP-запросов
        client = ClientBuilder.newClient();
        initUI();
    }

    private void initUI() {
        // Устанавливаем заголовок окна
        setTitle("Поиск по ID - 2 лабораторная работа - Струков Д.М.");
        setSize(850, 200);                                          // Размер окна
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);                                // Кнопка закрытия
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
        panel.add(new JLabel("Поиск по ID:"), BorderLayout.NORTH);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.SOUTH);
        // текстовая область вывода панели
        JScrollPane scrollPane = new JScrollPane(resultArea);
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // поиск книги по ID
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBookById();
            }
        });
    }

    private void searchBookById() {
        String idText = searchField.getText();
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