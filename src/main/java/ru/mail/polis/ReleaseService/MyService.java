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

    private static final String RESPONSE = "ONLINE";
    private static final String PREFIX = "id=";

    private static final int PREFIX_LENGTH = 3;
    private static final int HTTP_OK = 200;
    private static final int HTTP_CREATED = 201;
    private static final int HTTP_ACCEPTED = 202;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_NOT_FOUND = 404;
    private static final int HTTP_METHOD_NOT_ALLOWED = 405;

    public MyService(int port, @NotNull final MyDAO dao) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);

        this.server.createContext("/v0/status",
                httpExchange -> {
                    httpExchange.sendResponseHeaders(HTTP_OK, RESPONSE.length());
                    httpExchange.getResponseBody().write(RESPONSE.getBytes());
                    httpExchange.close();
                });

        this.server.createContext("/v0/entity",
                httpExchange -> {
                    try {
                        final String query = httpExchange.getRequestURI().getQuery();
                        if (!query.startsWith(PREFIX)) {
                            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                            httpExchange.close();
                            return;
                        }

                        final String id = query.substring(PREFIX_LENGTH);
                        if ("".equals(id)) {
                            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                            httpExchange.close();
                            return;
                        }

                        switch (httpExchange.getRequestMethod()) {
                            case "GET":
                                try {
                                    final byte[] getValue = dao.get(id);
                                    httpExchange.sendResponseHeaders(HTTP_OK, getValue.length);
                                    httpExchange.getResponseBody().write(getValue);

                                } catch (NoSuchElementException | IOException e) {
                                    httpExchange.sendResponseHeaders(HTTP_NOT_FOUND, 0);
                                }
                                break;

                            case "DELETE":
                                dao.delete(id);
                                httpExchange.sendResponseHeaders(HTTP_ACCEPTED, 0);
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
                                httpExchange.sendResponseHeaders(HTTP_CREATED, 0);
                                break;

                            default:
                                httpExchange.sendResponseHeaders(HTTP_METHOD_NOT_ALLOWED, 0);
                                break;
                        }

                    } catch (Exception e) {
                       // e.printStackTrace();
                        httpExchange.sendResponseHeaders(HTTP_NOT_FOUND, 0);

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
