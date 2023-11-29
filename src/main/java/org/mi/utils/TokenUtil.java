package org.mi.utils;

import org.mi.model.Token;
import org.mi.service.TokenProvider;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

public class TokenUtil {
    private static Token cachedToken;
    private static ReentrantLock lock = new ReentrantLock();
    private static TokenProvider tokenProvider = new TokenProvider();

    public static Token getToken() {
        lock.lock();
        try {
            if (cachedToken == null || LocalDateTime.ofInstant(cachedToken.getExpirationTime().toInstant(), ZoneId.systemDefault())
                    .isAfter(LocalDateTime.now())) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<Token> future = executor.submit(tokenProvider::getToken);
                return future.get(15, TimeUnit.SECONDS);
            } else if (LocalDateTime.ofInstant(cachedToken.getExpirationTime().toInstant(), ZoneId.systemDefault())
                    .isAfter(LocalDateTime.now().plusSeconds(30))) {
                Callable<Boolean> updateT = () -> {
                    LocalDateTime now = LocalDateTime.now();
                    cachedToken.refresh();
                    while (LocalDateTime.ofInstant(cachedToken.getExpirationTime().toInstant(), ZoneId.systemDefault())
                            .isAfter(now.plusSeconds(30))) {
                        Thread.sleep(1000);
                    }
                    return true;
                };
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<Boolean> future = executor.submit(updateT);
                if (future.get(15, TimeUnit.SECONDS)) {
                    return cachedToken;
                }
            } else {
                return cachedToken;
            }
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            cachedToken = null;
        } finally {
            lock.unlock();
        }
        return cachedToken;
    }
}
