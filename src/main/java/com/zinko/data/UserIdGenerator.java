package com.zinko.data;

import com.zinko.service.exception.ConsoleAppException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.File;
import java.io.IOException;


@Getter
@Setter
public class UserIdGenerator {
    private static Long lastExistId;


    static {
        File fileReader = new File("File.txt");
        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(fileReader)) {
            String line = reader.readLine();
            if(line!=null) {
                String[] split = line.split(" \\| ", 2);
                lastExistId = Long.parseLong(split[0]);
            } else {
                lastExistId=0L;
            }
        } catch (IOException e) {
            throw new ConsoleAppException("I/O error occurs");
        }
    }

    public static Long getNewUserId() {
        return lastExistId++;
    }
}
