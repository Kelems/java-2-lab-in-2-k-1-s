package org.library.client;

import org.library.domain.Book;

import javax.swing.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookFrame extends JFrame {
    private JTextField idField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField locationField;
    private JCheckBox issuedCheckBox;
    private JCheckBox readingRoomCheckBox;
    private JButton addButton;
    private JTextArea resultArea;

    private Client client;

    public AddBookFrame() {
        // Создаем клиента для отправки HTTP-запросов
        client = ClientBuilder.newClient();
        initUI();
    }

    private void initUI() {
        // Устанавливаем заголовок окна
        setTitle("Добавить книгу в 'базу' - 2 лабораторная работа - Струков Д.М.");
        // Устанавливаем размер окна
        setSize(400, 300);
        // Устанавливаем действие при закрытии окна
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Устанавливаем положение окна по центру экрана
        setLocationRelativeTo(null);

        // Создаем панель для размещения компонентов
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        // Создаем текстовые поля и флажки для ввода данных о книге
        idField = new JTextField();
        titleField = new JTextField();
        authorField = new JTextField();
        locationField = new JTextField();
        issuedCheckBox = new JCheckBox("Арендуется");
        readingRoomCheckBox = new JCheckBox("Только в читальном зале");
        addButton = new JButton("Добавить");
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        // Добавляем слушатель событий для кнопки добавления
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Выполняем добавление книги
                addBook();
            }
        });

        // Добавляем компоненты на панель
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Название:"));
        panel.add(titleField);
        panel.add(new JLabel("Автор:"));
        panel.add(authorField);
        panel.add(new JLabel("Местоположение:"));
        panel.add(locationField);
        panel.add(issuedCheckBox);
        panel.add(readingRoomCheckBox);
        panel.add(addButton);

        // Создаем скролл-панель для текстовой области
        JScrollPane scrollPane = new JScrollPane(resultArea);
        // Добавляем панель и скролл-панель на главное окно
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void addBook() {
        // Получаем текст из поля ввода ID
        String idText = idField.getText();
        // Проверяем, что поле ID не пустое
        if (idText.isEmpty()) {
            resultArea.setText("ID не может быть пустым");
            return;
        }

        // Преобразуем текст в число
        Integer id = Integer.parseInt(idText);
        // Получаем текст из остальных полей ввода
        String title = titleField.getText();
        String author = authorField.getText();
        String location = locationField.getText();
        // Получаем состояние флажков
        boolean issued = issuedCheckBox.isSelected();
        boolean readingRoom = readingRoomCheckBox.isSelected();

        // Создаем объект книги
        Book book = new Book(id, title, author, location, issued, readingRoom);

        // Отправляем POST-запрос к сервису для добавления книги
        Response response = client.target("http://localhost:8080/JavaLab/rest/book/add")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON));

        // Проверяем статус ответа
        if (response.getStatus() == 201) {
            // Выводим сообщение об успешном добавлении
            resultArea.setText("Книга успешно добавлена: " + response.readEntity(String.class));
        } else {
            // Выводим сообщение об ошибке
            resultArea.setText("Ошибка: " + response.getStatus());
        }
    }
}