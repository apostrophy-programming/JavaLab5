package lab.io;

import lab.model.*;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Обрабатывает ввод данных от пользователя с консоли.
 * Содержит методы для чтения различных типов данных с валидацией.
 * При некорректном вводе запрашивает повторный ввод до получения правильного значения.
 *
 * @author Max
 */
public class InputManager {
    private final Scanner scanner;

    /**
     * Конструктор, инициализирует сканер System.in.
     */
    public InputManager() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Читает объект Vehicle с консоли.
     * @param isUpdate если true, id и дата не запрашиваются (генерируются автоматически)
     * @param existingId для update можно передать id, чтобы не генерировать новый
     * @return новый объект Vehicle
     */
    public Vehicle readVehicle(boolean isUpdate, Long existingId) {
        String name = readString("Введите name (не может быть пустым): ", false, true);

        System.out.println("Введите coordinates:");
        Integer x = readInt("  x (целое число, не null): ", false, null, null);
        Long y = readLong("  y (целое число > -289, не null): ", false, -289L, null);

        Coordinates coordinates = new Coordinates(x, y);

        Integer enginePower = readInt("Введите enginePower (целое > 0, может быть пустым): ", true, 1, null);
        float capacity = readFloat("Введите capacity (число > 0): ", false, 0.0f, null);

        System.out.println("Доступные VehicleType: CAR, DRONE, SUBMARINE, SHIP");
        VehicleType type = readEnum("Введите type (одно из значений): ", VehicleType.class, false);

        System.out.println("Доступные FuelType: GASOLINE, MANPOWER, PLASMA, ANTIMATTER (можно оставить пустым)");
        FuelType fuelType = readEnum("Введите fuelType: ", FuelType.class, true);

        LocalDate creationDate = isUpdate ? null : LocalDate.now();

        return new Vehicle(null, name, coordinates, creationDate, enginePower, capacity, type, fuelType);
    }

    /**
     * Читает строку с консоли.
     *
     * @param prompt   приглашение к вводу
     * @param nullable разрешено ли null (пустая строка)
     * @param nonEmpty требуется ли непустое значение (игнорируется, если nullable == true)
     * @return введённая строка или null, если nullable разрешён и введена пустая строка
     */
    public String readString(String prompt, boolean nullable, boolean nonEmpty) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                if (nullable) return null;
                System.out.println("Ошибка: строка не может быть пустой.");
                continue;
            }
            if (nonEmpty && line.isEmpty()) {
                System.out.println("Ошибка: строка не может быть пустой.");
                continue;
            }
            return line;
        }
    }

    /**
     * Читает целое число (Integer) с консоли.
     *
     * @param prompt   приглашение к вводу
     * @param nullable разрешено ли null
     * @param min      минимальное допустимое значение (исключительно, если не null)
     * @param max      максимальное допустимое значение (исключительно, если не null)
     * @return введённое число или null
     */
    public Integer readInt(String prompt, boolean nullable, Integer min, Integer max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty() && nullable) return null;
            try {
                int val = Integer.parseInt(line);
                if (min != null && val <= min) {
                    System.out.println("Ошибка: значение должно быть больше " + min);
                    continue;
                }
                if (max != null && val >= max) {
                    System.out.println("Ошибка: значение должно быть меньше " + max);
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    /**
     * Читает длинное целое число (Long) с консоли.
     *
     * @param prompt   приглашение к вводу
     * @param nullable разрешено ли null
     * @param min      минимальное допустимое значение (исключительно, если не null)
     * @param max      максимальное допустимое значение (исключительно, если не null)
     * @return введённое число или null
     */
    public Long readLong(String prompt, boolean nullable, Long min, Long max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty() && nullable) return null;
            try {
                long val = Long.parseLong(line);
                if (min != null && val <= min) {
                    System.out.println("Ошибка: значение должно быть больше " + min);
                    continue;
                }
                if (max != null && val >= max) {
                    System.out.println("Ошибка: значение должно быть меньше " + max);
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    /**
     * Читает число с плавающей точкой (Float) с консоли.
     *
     * @param prompt   приглашение к вводу
     * @param nullable разрешено ли null
     * @param min      минимальное допустимое значение (исключительно, если не null)
     * @param max      максимальное допустимое значение (исключительно, если не null)
     * @return введённое число или null
     */
    public Float readFloat(String prompt, boolean nullable, Float min, Float max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty() && nullable) return null;
            try {
                float val = Float.parseFloat(line);
                if (min != null && val <= min) {
                    System.out.println("Ошибка: значение должно быть больше " + min);
                    continue;
                }
                if (max != null && val >= max) {
                    System.out.println("Ошибка: значение должно быть меньше " + max);
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число.");
            }
        }
    }

    /**
     * Читает значение перечисления (enum) с консоли.
     *
     * @param prompt     приглашение к вводу
     * @param enumClass  класс перечисления
     * @param nullable   разрешено ли null (пустая строка)
     * @param <T>        тип перечисления
     * @return константа перечисления или null
     */
    public <T extends Enum<T>> T readEnum(String prompt, Class<T> enumClass, boolean nullable) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim().toUpperCase();
            if (line.isEmpty() && nullable) return null;
            try {
                return Enum.valueOf(enumClass, line);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: допустимые значения: " + java.util.Arrays.toString(enumClass.getEnumConstants()));
            }
        }
    }


}
