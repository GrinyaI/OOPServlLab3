document.addEventListener('DOMContentLoaded', function () {
    const bookList = document.getElementById('booksList');

    // Загрузка списка автомобилей при загрузке страницы
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:8081/OOPServlLab3_war_exploded/books', true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            const books = JSON.parse(xhr.responseText);
            displayBooks(books);
        } else {
            console.error('Произошла ошибка при загрузке списка автомобилей:', xhr.statusText);
        }
    };

    xhr.send();

    function displayBooks(books) {
        let tableHTML = `
        <tbody>
    `;

        books.forEach(function (book) {
            tableHTML += `
            <tr>
                <td>${book.Title}</td>
                <td>${book.Author}</td>
                <td>${book.Genre}</td>
                <td>${book.Publishing}</td>
                <td>${book.ISBN}</td>
            </tr>
        `;
        });

        tableHTML += `
        </tbody>
    `;

        bookList.innerHTML = tableHTML;
    }
});