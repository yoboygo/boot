package ml.idream.ioc;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ac =  new ApplicationContext();
        Book book = (Book) ac.getBean("book");
        book.setId(1L);
        book.setName("你好！");
        System.out.println("-" + book.getId() + "-" + book.getName());
    }
}
