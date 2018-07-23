package presentation.employee;

import business.book.Book;
import business.common.ALibraryUser;
import business.employee.Employee;
import business.user.User;
import control.book.BookController;
import control.employee.EmployeeController;
import control.exception.KeyNotFoundException;
import control.user.UserController;
import presentation.Library;

import java.util.Optional;
import java.util.Scanner;

public class EmployeeView {

    private static final UserController userController = new UserController();
    private static final EmployeeController employeeController = new EmployeeController();
    private static final BookController bookController = new BookController();
    public static Scanner scValue;

    public static void employeeView() throws KeyNotFoundException {
        int choose;

        do {
            System.out.println("1.Dodaj uzytkownika");
            System.out.println("2.Dodaj pracownika");
            System.out.println("3.Pokaz uzytkownikow");
            System.out.println("4.Pokaz pracownikow");
            System.out.println("5.Edytuj ksiazke");
            System.out.println("6.Wróc do głównego menu");
            System.out.println("7.Koniec");

            Scanner scChoose = new Scanner(System.in);
            choose = scChoose.nextInt();

            switch (choose) {
                case 1:
                    addUser();
                    break;
                case 2:
                    addEmployee();
                    break;
                case 3:
                    System.out.println(userController.getAll());
                    break;
                case 4:
                    System.out.println(employeeController.getAll());
                    break;
                case 5:
                    System.out.println("Podaj ISBN ksiazki, którą chcesz edytować:");
                    scValue = new Scanner(System.in);
                    String isbn = scValue.nextLine();
                    Optional<Book> optionalBook = bookController.getById(isbn);
                    optionalBook.ifPresent(bookController::merge);
                    break;
                case 6:
                    Library.main(null);
                    break;
                case 7:
                    System.out.println("Do Widzenia!");
                    System.exit(-1);
                    break;
                default:
                    System.out.println("Nie ma takiej operacji! Wybierz operacje 1-8");
            }
        } while (true);
    }

    private static void addUser() {
        ALibraryUser libraryUser = createCommonProperties(User.class);

        System.out.println("Podaj liczbe wypozyczonych ksiazek:");
        scValue = new Scanner(System.in);
        int numberOfBorrows = scValue.nextInt();
        ((User) libraryUser).setNumberOfBorrowBooks(numberOfBorrows);

        userController.persist((User) libraryUser);
    }

    private static void addEmployee() {
        ALibraryUser libraryUser = createCommonProperties(Employee.class);

        System.out.println("Podaj stanowisko:");
        scValue = new Scanner(System.in);
        String position = scValue.nextLine();
        ((Employee) libraryUser).setPosition(position);

        employeeController.persist((Employee) libraryUser);
    }

    private static ALibraryUser createCommonProperties(Class aClass) {
        ALibraryUser libraryUser;

        if (aClass == User.class)
            libraryUser = new User();
        else
            libraryUser = new Employee();

        System.out.println("Podaj pesel:");
        scValue = new Scanner(System.in);
        String employeePesel = scValue.nextLine();
        libraryUser.setPesel(employeePesel);

        System.out.println("Podaj Imie:");
        scValue = new Scanner(System.in);
        String employeeFirstName = scValue.nextLine();
        libraryUser.setFirstName(employeeFirstName);

        System.out.println("Podaj nazwisko:");
        scValue = new Scanner(System.in);
        String employeeLastName = scValue.nextLine();
        libraryUser.setLastName(employeeLastName);

        System.out.println("Podaj wiek:");
        scValue = new Scanner(System.in);
        int employeeAge = scValue.nextInt();
        libraryUser.setAge(employeeAge);

        return libraryUser;
    }
}
