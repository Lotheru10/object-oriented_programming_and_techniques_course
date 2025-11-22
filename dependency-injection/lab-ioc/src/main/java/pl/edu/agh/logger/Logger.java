package pl.edu.agh.logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class Logger {

    private final DateFormat dateFormat;
    private final Set<IMessageSerializer> registeredSerializers;
    private final PrintWriter writer;

    @Inject
    public Logger() {
        this.dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        this.registeredSerializers = new HashSet<>();
        try {
            this.writer = new PrintWriter(new FileWriter("persistence.log", true));
        } catch (IOException e) {
            throw new RuntimeException("Cannot open persistence.log", e);
        }
    }

    public void registerSerializer(IMessageSerializer messageSerializer) {
        registeredSerializers.add(messageSerializer);
    }

    public void log(String message) {
        log(message, null);
    }

    public void log(String message, Throwable error) {
        String formattedMessage = dateFormat.format(new Date())
                + ": " + message + (error != null ? error.toString() : "");

        for (IMessageSerializer messageSerializer : registeredSerializers) {
            messageSerializer.serializeMessage(formattedMessage);
        }

        writer.println(formattedMessage);
        writer.flush();
    }
}
