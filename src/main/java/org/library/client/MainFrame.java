package org.library.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("2 лабораторная работа - Струков Д.М.");   // Заголовок окна
        setSize(400, 400);                      // Размер окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);            // Кнопка закрытия
        setLocationRelativeTo(null);                        // Положение окна (в центре экрана)

        // Создаем кнопки для открытия соответствующих окон
        JButton searchTitleButton = new JButton("Поиск по названию");
        JButton searchAuthorButton = new JButton("Поиск по автору");
        JButton searchByIdButton = new JButton("Поиск по ID");
        JButton addBookButton = new JButton("Добавить книгу");

        // Создаем панель для размещения кнопок
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new GridLayout(4, 1));

        // Добавляем кнопки на панель
        panel.add(searchTitleButton);
        panel.add(searchAuthorButton);
        panel.add(searchByIdButton);
        panel.add(addBookButton);

        // Добавляем слушатели событий для кнопок
        // Окно поиска по названию
        searchTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchByTitleFrame().setVisible(true);
            }
        });

        // Окно поиска по автору
        searchAuthorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchByAuthorFrame().setVisible(true);
            }
        });
        // окно поиска по ID
        searchByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchByIdFrame().setVisible(true);
            }
        });

        // окно добавления книги
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddBookFrame().setVisible(true);
            }
        });


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