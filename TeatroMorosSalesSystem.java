/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package teatromorossalessystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author gustavo.dominguez
 */
public class TeatroMorosSalesSystem {
    static Scanner scanner = new Scanner(System.in);
 
    static final String THEATER_NAME = "Teatro Moro";
    static final String LABEL_RESERVATION_ID = "Reserva ID: ";
    static final String LABEL_CLIENT_NAME = "Cliente: ";
    static final String LABEL_CLIENT_AGE = "Edad: ";
    static final String LABEL_TICKET_TYPE = "Tipo: ";
    static final String LABEL_SEAT_NUMBER = "Numero: ";
    static final String LABEL_FINAL_PRICE = "Precio final: ";
    static final String LABEL_TRANSACTION_ID = "Transaccion ID: ";

    static boolean[] vipSeatsAvailability = new boolean[10];
    static boolean[] palcoSeatsAvailability = new boolean[10];
    static boolean[] plateaAltaSeatsAvailability = new boolean[5];
    static boolean[] plateaBajaSeatsAvailability = new boolean[5];
    static boolean[] gallerySeatsAvailability = new boolean[5];
    static int saleCount = 0;
    static ArrayList<String> reservationList = new ArrayList<>();
    static LinkedList<String> transactionsHistory = new LinkedList<>();
    

    static class Person {
        String name;
        int age;
        boolean isFemale;
        boolean isStudent;

        public Person(String name, int age, boolean isFemale, boolean isStudent) {
            this.name = name;
            this.age = age;
            this.isFemale = isFemale;
            this.isStudent = isStudent;
        }
    }

    static class Discounts {
        public static double calculateDiscounts(Person person) {
            if (person.age >= 65) return 0.25;
            if (person.age < 12) return 0.10;
            if (person.isFemale) return 0.20;
            if (person.isStudent) return 0.15;
            return 0.0;
        }
    }
    
    /*
        * ============================
        * Pruebas realizadas al sistema
        * ============================
        * Este sistema fue probado manualmente mediante la interacción completa con el menú principal. 
        * Las pruebas cubrieron los siguientes escenarios clave para validar su correcto funcionamiento:
        * 
        * Venta de entradas:
        *    - Selección correcta de tipo de asiento y número.
        *    - Asientos marcados como ocupados tras confirmación.
        *    - Aplicación correcta de descuentos por edad, género y condición de estudiante.
        *
        * Validaciones de entrada:
        *    - Edad restringida a valores numéricos válidos (máx. 100).
        *    - Género limitado a opciones 'f' (femenino) o 'm' (masculino), con reintento si se ingresa otro valor.
        *    - Validación binaria de condición de estudiante (s/n), con reintento si el valor es inválido.
        * 
        * Gestión de reservas:
        *    - Visualización de todas las reservas existentes.
        *    - Modificación de una reserva con actualización del nombre, edad y descuentos.
        *    - Eliminación de una reserva con liberación correcta del asiento correspondiente.
        * 
        * Generación de boletas:
        *    - Boleta generada correctamente por cada transacción confirmada.
        *    - Boletas no impresas para reservas eliminadas.
        * 
        * Integridad de datos:
        *    - Coherencia entre las reservas, las transacciones y el estado de los asientos.
        *    - Se asegura sincronización entre los datos de cliente y los registros de venta.
        *
        * El sistema ha sido validado en múltiples flujos de uso, incluyendo operaciones exitosas y entradas erróneas
    */
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Sistema Teatro Moro ===");
            System.out.println("1. Venta de entrada");
            System.out.println("2. Ver reservas");
            System.out.println("3. Modificar una reserva");
            System.out.println("4. Eliminar una reserva");
            System.out.println("5. Generar boletas");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int option = readInt();

            switch (option) {
                case 1 -> sellTicket();
                case 2 -> viewReservations();
                case 3 -> modifyReservation();
                case 4 -> deleteReservation();
                case 5 -> generateReceipts();
                case 6 -> {
                    System.out.println("Gracias por su visita.");
                    running = false;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    static void sellTicket() {
        System.out.println("Elija el tipo de entrada:");
        System.out.println("1. VIP ($30.000)");
        System.out.println("2. Palco ($20.000)");
        System.out.println("3. Platea Baja ($15.000)");
        System.out.println("4. Platea Alta ($10.000)");
        System.out.println("5. Galería ($5.000)");

        int option = readInt();
        double price = 0;
        String type = "";
        boolean[] availability;

        switch (option) {
            case 1 -> { price = 30000; type = "VIP"; availability = vipSeatsAvailability; }
            case 2 -> { price = 20000; type = "Palco"; availability = palcoSeatsAvailability; }
            case 3 -> { price = 15000; type = "Platea Baja"; availability = plateaBajaSeatsAvailability; }
            case 4 -> { price = 10000; type = "Platea Alta"; availability = plateaAltaSeatsAvailability; }
            case 5 -> { price = 5000; type = "Galería"; availability = gallerySeatsAvailability; }
            default -> { System.out.println("Opción inválida"); return; }
        }

        int seat = selectSeat(availability);
        Person p = collectClientData();
        double discount = Discounts.calculateDiscounts(p);
        double finalPrice = price * (1 - discount);

        availability[seat] = true;
        saleCount++;
        String reservation = 
                LABEL_RESERVATION_ID + saleCount 
                + ", " + LABEL_CLIENT_NAME  + p.name
                + ", " + LABEL_CLIENT_AGE + p.age
                + ", " + LABEL_TICKET_TYPE + type
                + ", " + LABEL_SEAT_NUMBER + seat
                + ", " + LABEL_FINAL_PRICE + finalPrice;
 
        reservationList.add(reservation);
      
        String transaction = LABEL_TRANSACTION_ID + saleCount + "," + type + "," + price + "," + discount + "," + finalPrice;
        transactionsHistory.add(transaction);
  
        System.out.println("Reserva confirmada. Total: $" + (int)finalPrice);
    }

    static void viewReservations() {
        if (reservationList.isEmpty()) System.out.println("No hay reservas.");
        else reservationList.forEach(System.out::println);
    }

    
    static void modifyReservation() {
        System.out.print("Ingrese el ID de la reserva a modificar: ");
        int targetId = readInt();
        
        for(int i = 0; i < reservationList.size(); i++) {
            if(reservationList.get(i).startsWith("Reserva ID: " + targetId)) {
                String targetReservation = reservationList.get(i);
                System.out.print("Ingrese los nuevos datos de la reserva: ");
                Person person = collectClientData();
                String reservationTicketType = extractTypeFromReservationString(targetReservation);
                int reservationSeatNumber = Integer.parseInt(extractSeatNumberFromReservationString(targetReservation));
                double price = getBasePrice(reservationTicketType);
                double discount = Discounts.calculateDiscounts(person);
                double finalPrice = price * (1 - discount);
                
                
                String modifiedReservation = LABEL_RESERVATION_ID + targetId
                        + ", " + LABEL_CLIENT_NAME + person.name
                        + ", " + LABEL_CLIENT_AGE + person.age
                        + ", " + LABEL_TICKET_TYPE + reservationTicketType
                        + ", " + LABEL_SEAT_NUMBER + reservationSeatNumber
                        + ", " + LABEL_FINAL_PRICE + finalPrice;
 
                reservationList.set(i, modifiedReservation);
                updateTransactionById(targetId, reservationTicketType, price, discount, finalPrice);
                System.out.println("Reserva modificada exitosamente.");
                return;
            }
        }
        System.out.println("Reserva no encontrada.");
    }
    
    static void updateTransactionById(int transactionId, String ticketType, double basePrice, double discount, double finalPrice) {
        for (int j = 0; j < transactionsHistory.size(); j++) {
            if (transactionsHistory.get(j).startsWith(LABEL_TRANSACTION_ID + transactionId + ",")) {
                String updatedTransaction = LABEL_TRANSACTION_ID + transactionId + "," + ticketType + "," + basePrice + "," + discount + "," + finalPrice;
                transactionsHistory.set(j, updatedTransaction);
                break;
            }
        }
    }
    
    static void deleteReservation() {
        System.out.print("Ingrese el ID de la reserva a eliminar: ");
        int targetId = readInt();
        for (int i = 0; i < reservationList.size(); i++) {
            if(reservationList.get(i).startsWith(LABEL_RESERVATION_ID + targetId)) {
                String reservationToDelete = reservationList.get(i);
                String ticketType = extractTypeFromReservationString(reservationToDelete);
                int reservationSeatNumber = Integer.parseInt(extractSeatNumberFromReservationString(reservationToDelete));
                releaseSeat(ticketType, reservationSeatNumber);
                reservationList.remove(i);
                System.out.println("Reserva eliminada.");
                return;
            }
        }
        System.out.println("ID no encontrado.");
    }

    static void generateReceipts() {
        if (reservationList.isEmpty()) {
            System.out.println("No hay reservas para generación de boletas");
            return;
        }

        for (String reservation : reservationList) {
            String transactionId = extractIdFromReservationString(reservation);
            String matchedTransaction = null;

            for (String transaction : transactionsHistory) {
                if (transaction.contains(LABEL_TRANSACTION_ID + transactionId + ",")) {
                    matchedTransaction = transaction;
                    break;
                }
            }

            if (matchedTransaction != null) {
                String[] transaction = matchedTransaction.split(",");
                String location = transaction[1];
                String basePrice = transaction[2];
                double discountInPercent = Double.parseDouble(transaction[3]) * 100;
                String finalPrice = transaction[4];

                System.out.println("----------------------------------------");
                System.out.println("              " + THEATER_NAME);
                System.out.println("----------------------------------------");
                System.out.println("Numero de transacción: " + transactionId);
                System.out.println("Ubicación: " + location);
                System.out.println("Costo base: " + basePrice);
                System.out.println("Descuento Aplicado: " + discountInPercent + "%");
                System.out.println("Costo Final: $" + finalPrice);
                System.out.println("----------------------------------------");
                System.out.println("Gracias por su visita al " + THEATER_NAME);
                System.out.println("----------------------------------------\n");
            }
        }
    }


    static int selectSeat(boolean[] availability) {
        for (int i = 0; i < availability.length; i++) {
            System.out.println("Asiento " + i + ": " + (availability[i] ? "Ocupado" : "Libre"));
        }
        int seat = readInt();
        while (seat < 0 || seat >= availability.length || availability[seat]) {
            System.out.print("Inválido o ocupado. Intente nuevamente: ");
            seat = readInt();
        }
        return seat;
    }

    static Person collectClientData() {
        System.out.print("Nombre: ");
        String name = scanner.nextLine().trim();

        System.out.print("Edad: ");
        int age = readInt();
        while (age < 0 || age > 100) {
            System.out.print("Edad inválida. Ingrese un valor entre 0 y 100: ");
            age = readInt();
        }

        String genderInput;
        boolean isFemale = false;
        while (true) {
            System.out.print("¿Sexo? (f para femenino / m para masculino): ");
            genderInput = scanner.nextLine().trim().toLowerCase();
            if (genderInput.equals("f")) {
                isFemale = true;
                break;
            } else if (genderInput.equals("m")) {
                isFemale = false;
                break;
            } else {
                System.out.println("Entrada inválida. Por favor escriba 'f' o 'm'.");
            }
        }

        String studentInput;
        boolean isStudent = false;
        while (true) {
            System.out.print("¿Es estudiante? (s/n): ");
            studentInput = scanner.nextLine().trim().toLowerCase();
            if (studentInput.equals("s")) {
                isStudent = true;
                break;
            } else if (studentInput.equals("n")) {
                isStudent = false;
                break;
            } else {
                System.out.println("Entrada inválida. Por favor escriba 's' o 'n'.");
            }
        }

        return new Person(name, age, isFemale, isStudent);
    }

    static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Entrada inválida. Intente otra vez: ");
            }
        }
    }
    
    static String extractIdFromReservationString(String reservation) { 
        int startIndex = reservation.indexOf(LABEL_RESERVATION_ID) + LABEL_RESERVATION_ID.length();
        int endIndex = reservation.indexOf(", " + LABEL_CLIENT_NAME);
        return reservation.substring(startIndex, endIndex);
    }
    
    static String extractTypeFromReservationString(String reservation) { 
        int startIndex = reservation.indexOf(LABEL_TICKET_TYPE) + LABEL_TICKET_TYPE.length();
        int endIndex = reservation.indexOf(", " + LABEL_SEAT_NUMBER);
        return reservation.substring(startIndex, endIndex);
    }
    
    static String extractSeatNumberFromReservationString(String reservation) {
        int startIndex = reservation.indexOf(LABEL_SEAT_NUMBER) + LABEL_SEAT_NUMBER.length();
        int endIndex = reservation.indexOf(", " + LABEL_FINAL_PRICE);
        return reservation.substring(startIndex, endIndex);
    }

    static void releaseSeat(String type, int seatNum) {
        switch (type) {
            case "VIP" -> vipSeatsAvailability[seatNum] = false;
            case "Palco" -> palcoSeatsAvailability[seatNum] = false;
            case "Platea Baja" -> plateaBajaSeatsAvailability[seatNum] = false;
            case "Platea Alta" -> plateaAltaSeatsAvailability[seatNum] = false;
            case "Galería" -> gallerySeatsAvailability[seatNum] = false;
        }
    }

    static double getBasePrice(String type) {
        return switch (type) {
            case "VIP" -> 30000;
            case "Palco" -> 20000;
            case "Platea Baja" -> 15000;
            case "Platea Alta" -> 10000;
            case "Galería" -> 5000;
            default -> 10000;
        };
    }
}
