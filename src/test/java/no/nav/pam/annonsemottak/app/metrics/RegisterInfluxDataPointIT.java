package no.nav.pam.annonsemottak.app.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class RegisterInfluxDataPointIT {

    private static ServerSocket serverSocket;
    private MeterRegistry meterRegistry = mock(MeterRegistry.class);

    private final String expectedJsonBeforeTimestamp = "{" +
            "\"name\":\"annonsemottak-events\"," +
            "\"type\":\"metric\"," +
            "\"handlers\":[\"events_nano\"]," +
            "\"output\":\"annonsemottak.ads.collected.v2.new,application=pam-annonsemottak,cluster=dev-fss,namespace=default,origin=SOME_WEBPAGE,source=DEXI counter=50i ";

    private final String expectedJsonAfterTimestamp = ",\"status\":0}";

    @BeforeClass
    public static void init() throws IOException {
        serverSocket = new ServerSocket(0);
    }

    @AfterClass
    public static void teardown() throws IOException {
        serverSocket.close();
    }

    @Before
    public void setUp() {
    }

    private String readFromSocket() throws IOException {
        serverSocket.setSoTimeout(1000);
        try (Socket socket = serverSocket.accept()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return reader.lines().collect(Collectors.joining(""));
        }
    }

    @Test
    public void send_event() throws IOException {
        when(meterRegistry.counter(anyString(), any(String.class)))
                .thenReturn(mock(Counter.class));

        SensuClient sensuClient = new SensuClient("localhost", serverSocket.getLocalPort());
        InfluxMetricReporter influxMetricReporter = new InfluxMetricReporter(sensuClient);
        AnnonseMottakProbe probe = new AnnonseMottakProbe(influxMetricReporter, meterRegistry);

        probe.newAdPoint(50L, "DEXI", "SOME_WEBPAGE");

        String jsonString = readFromSocket();

        assertThat(jsonString).startsWith(expectedJsonBeforeTimestamp);
        assertThat(jsonString).endsWith(expectedJsonAfterTimestamp);

    }
}
