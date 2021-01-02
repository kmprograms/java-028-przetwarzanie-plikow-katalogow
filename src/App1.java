import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

public class App1 {
    public static void main(String[] args) throws IOException {


        String DIR = "C:/Users/Krzysztof/OneDrive/Desktop/data";
        String DIR2 = "C:/Users/Krzysztof/OneDrive/Desktop/data2";
        String DIR3 = "C:/Users/Krzysztof/OneDrive/Desktop/data3";
        String DIR4 = "C:/Users/Krzysztof/OneDrive/Desktop/data4";
        String DIR5 = "C:/Users/Krzysztof/OneDrive/Desktop/data5";
        String FILE = "C:/Users/Krzysztof/OneDrive/Desktop/f1.txt";
        String FILE2 = "C:/Users/Krzysztof/OneDrive/Desktop/f2.txt";

        // 1. Sprawdzanie czy istnieje katalog lub plik

        System.out.println("--------------------------------- 1 -------------------------------");

        Path dir = Paths.get(DIR);
        System.out.println(Files.exists(dir));

        Path file = Paths.get(FILE);
        System.out.println(Files.exists(file));

        Path dir2 = Paths.get(DIR2);
        System.out.println(Files.notExists(dir2));

        Path file2 = Paths.get(FILE2);
        System.out.println(Files.notExists(file2));

        // 2. Sprawdzenie czy dany zasob jest plikiem czy katalogiem

        System.out.println("--------------------------------- 2 -------------------------------");

        System.out.println(Files.isRegularFile(file));
        System.out.println(Files.isRegularFile(dir));
        System.out.println(Files.isDirectory(file));
        System.out.println(Files.isDirectory(dir));

        // 3. Sprawdzanie innych wlasciwosci

        System.out.println("--------------------------------- 3 -------------------------------");

        System.out.println(Files.isReadable(file));
        System.out.println(Files.isExecutable(file));
        System.out.println(Files.isSameFile(file, file2)); // wymaga obslugi IOException

        // 4. Tworzenie zasobow

        // Trzeba uwazac zeby podawana lokalizacja dla pliku istniala poniewaz jezeli nie istnieje
        // skonczy sie to bledem

        String filename = "myFile.txt";
        Path newFile = Paths.get(DIR + "/" + filename);
        Files.createFile(newFile); // jezeli plik istnieje to wyjatek

        String directoryName = "myDir";
        Path newDir = Paths.get(DIR + "/" + directoryName);
        Files.createDirectory(newDir); // jezeli katalog istnieje to wyjatek

        // Mozesz tez uzyc createDirectories i wtedy jezeli nie bylo takiego katalogu jak w sciezce po prostu utworzy nowy
        // a potem w nim osadzi ten ktory chcemy utworzyc
        Path newDir3 = Paths.get(DIR3 + "/" + directoryName);
        // Files.createDirectories(newDir3);

        // Mozemy jeszcze tworzyc TEMPORARY FILES. Ten mechanizm pozwala nam generowac w aplikacji pliki z pewnym
        // prefixem oraz suffixem dzieki czemu mozemy stosowac pewien szablon tworzenia plikow.
        String prefix = "data_";
        String suffix = ".txt";
        Path newTempFile = Paths.get(DIR + "/");
        Files.createTempFile(newTempFile, prefix, suffix); // zostanie utworzony plik z zadanym przedrostkiem i przyrostkiem
        // w okreslonej lokalizacji a reszta nazwy tego pliku bedzie wygenerowana liczba


        // 5. Usuwanie zasobow

        // Nalezy podac prawidlowa sciezke poniewaz w przeciwnym razie bedzie wyjatek
        Files.delete(newFile);

        // Chyba ze zastosujemy ponizsza metode, ktora najpierw sprawdza czy mamy plik
        Files.deleteIfExists(newFile);

        // Kiedy chcesz usuwac katalog upewnij sie ze jest pusty poniewaz inaczej dostaniesz wyjatek
        Files.delete(newDir);


        // 6. Kopiowanie i przenoszenie plikow

        // Na poczaek ustalanie sciezki pliku na podstawie istniejacego obiektu Path
        String sourceFilename = "f1.txt";
        Path sourceFile = dir.resolve(sourceFilename);
        System.out.println(sourceFile);

        String destinationFilename = "f2.txt";
        Path destinationFile = dir.resolve(destinationFilename);
        System.out.println(destinationFile);

        // trzeci argument pozwala nadpisywac zawartosc pliku jezeli istnieje
        // tutaj mamy opsi wszystkich atrybutow:
        // https://docs.oracle.com/javase/7/docs/api/java/nio/file/StandardCopyOption.html
        // Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);

        // Mozesz tez przeniesc plik
        // Files.move(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);


        // 7. Zapisywanie / odczyt do pliku i z pliku

        String message = "TO JEST PEWIEN WIERSZ OD ŻANETY: " + LocalDateTime.now();

        Path path = Paths.get(DIR + "/f3.txt");
        byte[] messageAsBytes = message.getBytes(StandardCharsets.UTF_8);
        // https://docs.oracle.com/javase/7/docs/api/java/nio/file/StandardOpenOption.html
        Files.write(path, List.of(message), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        // Files.write(path, List.of(message), StandardCharsets.UTF_8, StandardOpenOption.APPEND);

        System.out.println("----------------------- READ 1 ----------------------");
        Files.lines(path).forEach(System.out::println);

        System.out.println("----------------------- READ 2 ----------------------");
        System.out.println(Files.readAllLines(path));

    }
}
