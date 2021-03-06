public class Book {
    private int id;
    private String title;
    private String author;
    private String ISBN;
    private int price;
    private String category;
    private String description;

    public Book() {   }

    public Book(int id, String title, String author, String ISBN, int price, String category, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.price = price;
        this.category = category;
        this.description = description;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ID: " + id +
                ", Title: " + title +
                ", Author: " + author +
                ", ISBN: " + ISBN +
                ", Price: $" + price +
                ", Category: " + category +
                ", Description: " + description +
                "}";
    }
}
