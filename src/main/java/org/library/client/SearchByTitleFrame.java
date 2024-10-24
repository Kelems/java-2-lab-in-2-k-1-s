package org.library.client;

import org.library.domain.Book;

import javax.swing.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchByTitleFrame extends JFrame {
    private Client client;
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea resultArea;

    // Клиент для отправки HTTP-запросов
    public SearchByTitleFrame() {
        client = ClientBuilder.newClient();
        initUI();
    }

    private void initUI() {
        setTitle("Поиск по названию - 2 лабораторная работа - Струков Д.М.");    // Заголовок окна
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
        panel.add(new JLabel("Поиск по названию:"), BorderLayout.NORTH);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.SOUTH);
        // текстовая область вывода панели
        JScrollPane scrollPane = new JScrollPane(resultArea);
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Поиск книг по названию
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBooksByTitle();
            }
        });

    }

    private void searchBooksByTitle() {
        String title = searchField.getText();
        // Отправляем GET-запрос к сервису для поиска книг по названию
        Response response = client.target("http://localhost:8080/JavaLab/rest/book/search/title")
                .queryParam("title", title)
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