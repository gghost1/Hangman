package backend.academy;

import backend.academy.Exceptions.NotAvailableException;
import backend.academy.Exceptions.StorageNotInitializedException;
import backend.academy.GameProcess.MainCore;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@UtilityClass
public class Main {
    public static void main(String[] args) {
        try {
            MainCore mainCore = MainCore
                .instance(new MainCore.IO(new PrintWriter(System.out), new InputStreamReader(System.in)),
                    "ru");
            mainCore.start();
        } catch (StorageNotInitializedException | NotAvailableException e) {
            log.error(e.getMessage());
        }
    }
}
