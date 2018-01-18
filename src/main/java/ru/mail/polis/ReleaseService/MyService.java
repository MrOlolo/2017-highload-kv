package ru.mail.polis.ReleaseService;

import com.sun.net.httpserver.HttpServer;
import org.jetbrains.annotations.NotNull;
import ru.mail.polis.KVService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.NoSuchElementException;

/**
 * Created by Ololo on 16.10.2017.
 */

public class MyService implements KVService {
    @NotNull
    private final HttpServer server;

    private static final String response = "ONLINE";

    public MyService(int port, @NotNull final MyDAO dao) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);

        this.server.createContext("/v0/status",
                httpExchange -> {
                    httpExchange.sendResponseHeaders(200, response.length());
                    httpExchange.getResponseBody().write(response.getBytes());
                    httpExchange.close();
                });

        this.server.createContext("/v0/entity",
                httpExchange -> {
                    try {
                        final String query = httpExchange.getRequestURI().getQuery();
                        if (!query.startsWith("id=")) {
                            httpExchange.sendResponseHeaders(400, 0);
                            httpExchange.close();
                            return;
                        }

                        final String id = query.substring(3);
                        if ("".equals(id)) {
                            httpExchange.sendResponseHeaders(400, 0);
                            httpExchange.close();
                            return;
                        }

                        switch (httpExchange.getRequestMethod()) {
                            case "GET":
                                try {
                                    final byte[] getValue = dao.get(id);
                                    httpExchange.sendResponseHeaders(200, getValue.length);
                                    httpExchange.getResponseBody().write(getValue);

                                } catch (NoSuchElementException | IOException e) {
                                    httpExchange.sendResponseHeaders(404, 0);
                                }
                                break;

                            case "DELETE":
                                dao.delete(id);
                                httpExchange.sendResponseHeaders(202, 0);
                                break;

                            case "PUT":
                                ByteArrayOutputStream os = new ByteArrayOutputStream();
                                InputStream is = httpExchange.getRequestBody();
                                byte[] buffer = new byte[4096];
                                int len;
                                while((len = is.read(buffer))>0) {
                                    os.write(buffer, 0, len);
                                }
                                final byte[] putValue = os.toByteArray();
                                dao.upsert(id, putValue);
                                httpExchange.sendResponseHeaders(201, 0);
                                break;

                            default:
                                httpExchange.sendResponseHeaders(405, 0);
                                break;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        httpExchange.sendResponseHeaders(404, 0);

                    } finally {
                        httpExchange.close();
                    }
                });
    }

    @Override
    public void start() {
        this.server.start();
    }

    @Override
    public void stop() {
        this.server.stop(0);
    }

}
