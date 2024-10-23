package org.library.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public MainFrame() {
        // Устанавливаем заголовок окна
        setTitle("2 лабораторная работа - Струков Д.М.");
        // Устанавливаем размер окна
        setSize(300, 200);
        // Устанавливаем действие при закрытии окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Устанавливаем положение окна по центру экрана
        setLocationRelativeTo(null);

        // Создаем панель для размещения кнопок
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        // Создаем кнопки для открытия соответствующих окон
        JButton searchTitleButton = new JButton("Поиск по названию");
        JButton searchAuthorButton = new JButton("Поиск по автору");
        JButton addBookButton = new JButton("Добавить книгу");
        JButton searchByIdButton = new JButton("Поиск по ID");

        // Добавляем слушатели событий для кнопок
        searchTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Открываем окно поиска по названию
                new SearchByTitleFrame().setVisible(true);
            }
        });

        searchAuthorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Открываем окно поиска по автору
                new SearchByAuthorFrame().setVisible(true);
            }
        });

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Открываем окно добавления книги
                new AddBookFrame().setVisible(true);
            }
        });

        searchByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Открываем окно поиска по ID
                new SearchByIdFrame().setVisible(true);
            }
        });

        // Добавляем кнопки на панель
        panel.add(searchTitleButton);
        panel.add(searchAuthorButton);
        panel.add(addBookButton);
        panel.add(searchByIdButton);

        // Добавляем панель на главное окно
        getContentPane().add(panel);
    }

    public static void main(String[] args) {
        // Запускаем главное окно в потоке обработки событий
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }
}