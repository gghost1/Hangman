package backend.academy;

import backend.academy.Exceptions.StorageNotInitializedException;
import backend.academy.GameProcess.FrontEnd.MainCoreDisplay;
import backend.academy.GameProcess.MainCore;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;


@Slf4j public class Main {
    public static void main(String[] args) {
        MainCore mainCore = MainCore.instance(new MainCore.IO(new PrintWriter(System.out), new InputStreamReader(System.in)));
        try {
            mainCore.start();
        } catch (StorageNotInitializedException e) {
            log.error(e.getMessage());
        }
    }
}
