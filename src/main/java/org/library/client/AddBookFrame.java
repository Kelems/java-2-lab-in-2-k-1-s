package org.library.client;

import org.library.domain.Book;

import javax.swing.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookFrame extends JFrame {
    private Client client;
    private JTextField idField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField locationField;
    private JCheckBox issuedCheckBox;
    private JCheckBox readingRoomCheckBox;
    private JButton addButton;
    private JTextArea resultArea;

    public AddBookFrame() {
        // Создаем клиента для отправки HTTP-запросов
        client = ClientBuilder.newClient();
        initUI();
    }

    private void initUI() {
        setTitle("Добавить книгу в 'базу' - 2 лабораторная работа - Струков Д.М.");
        setSize(600, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        // Создаем текстовые поля и флажки для ввода данных о книге
        idField = new JTextField();
        titleField = new JTextField();
        authorField = new JTextField();
        locationField = new JTextField();
        // чекбоксы
        issuedCheckBox = new JCheckBox("Арендуется");
        readingRoomCheckBox = new JCheckBox("Только в читальном зале");

        addButton = new JButton("Добавить");
        resultArea = new JTextArea();
        resultArea.setEditable(false);

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
        panel.add(new JLabel("Результат внесения:"));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Добавляем книгу
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
    }

    private void addBook() {
        // Получаем текст из поля ввода ID
        String idText = idField.getText();
        // Проверяем, что поле ID не пустое
        if (idText.isEmpty()) {
            resultArea.setText("ID не может быть пустым");
            return;
        }
        // Собираем данные
        Integer id = Integer.parseInt(idText);
        String title = titleField.getText();
        String author = authorField.getText();
        String location = locationField.getText();
        boolean issued = issuedCheckBox.isSelected();
        boolean readingRoom = readingRoomCheckBox.isSelected();

        // Закрепляем данные
        Book book = new Book(id, title, author, location, issued, readingRoom);

        // Отправляем POST-запрос к сервису для добавления книги
        Response response = client.target("http://localhost:8080/JavaLab/rest/book/add")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON));

        // Проверяем статус ответа
        if (response.getStatus() == 201) {
            resultArea.setText("Книга успешно добавлена: " + response.readEntity(String.class));
        } else {
            resultArea.setText("Ошибка: " + response.getStatus());
        }
    }
}