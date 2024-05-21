package com.example.oopservllab3;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@WebServlet(name = "bookServlet", value = "/books")
public class BookServlet extends HttpServlet {
    private static final String FILE_PATH = "C:\\Users\\grine\\IdeaProjects\\OOPServlLab3\\src\\main\\java\\com\\example\\oopservllab3\\books.json";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String booksJSON = readFromFile();
        response.getWriter().write(booksJSON);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder jsonRequest = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonRequest.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка при чтении json");
            return;
        }

        // Преобразование JSON-строки в JSONObject
        JSONObject newBooksJson = new JSONObject(jsonRequest.toString());

        // Добавление нового автомобиля в список
        JSONArray booksArray = new JSONArray(readFromFile());
        booksArray.put(newBooksJson);

        // Запись обновленного списка автомобилей в файл
        writeToFile(booksArray.toString());

        // Отправка обновленного списка автомобилей
        response.getWriter().write(booksArray.toString());
    }

    private String readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    private void writeToFile(String booksJson) {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            fileWriter.write(booksJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}