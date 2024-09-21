package backend.academy;

import backend.academy.exceptions.NotAvailableException;
import backend.academy.exceptions.StorageNotInitializedException;
import backend.academy.gameProcess.MainCore;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@UtilityClass
public class Main {
    public static void main(String[] args) {
        try {
            MainCore mainCore = MainCore
                .instance(new MainCore.IO(new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8)),
                        new InputStreamReader(System.in, StandardCharsets.UTF_8)),
                    "ru");
            mainCore.start();
        } catch (StorageNotInitializedException | NotAvailableException e) {
            log.error("Error occurred: {}", e.getMessage());
        }
    }
}
