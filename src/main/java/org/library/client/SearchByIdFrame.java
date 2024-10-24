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
        getContentPane().add(panel, BorderLayout.NORTH);

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
        int id = Integer.parseInt(idText);    // Преобразуем текст в число
        // Отправляем GET-запрос к сервису для поиска книги по ID
        Response response = client.target("http://localhost:8080/JavaLab/rest/book/fetch/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() == 200) {  // если все успешно будет код 200
            // Получаем список книг из ответа
            java.util.List<Book> books = response.readEntity(new GenericType<List<Book>>() {});
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