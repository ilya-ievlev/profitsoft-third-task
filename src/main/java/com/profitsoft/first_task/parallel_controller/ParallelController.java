package com.profitsoft.first_task.parallel_controller;

import com.profitsoft.first_task.models.Person;
import com.profitsoft.first_task.parsers.StaxParserXmlToJava;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ParallelController {
    private static final boolean INITIAL_FILE_READ_STATE = false;
    private static final String POOL_DID_NOT_TERMINATE = "Pool did not terminate";

    public static List<Person> parallelReadFiles(File inputFileFolder) throws ExecutionException, InterruptedException, IOException {
        Map<String, Boolean> processedFilesMap = new ConcurrentHashMap<>();
        fillInputFilesMap(inputFileFolder, processedFilesMap);
        List<Person> generalPersonList = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        int numberOfFiles = inputFileFolder.listFiles().length;
        CompletableFuture<List<Person>>[] completableFuturesArray = new CompletableFuture[numberOfFiles];
        for (int i = 0; i < numberOfFiles; i++) {
            CompletableFuture<List<Person>> completableFuture = CompletableFuture.supplyAsync(() -> {
                List<Person> personList = null;
                for (Map.Entry<String, Boolean> entry : processedFilesMap.entrySet()) {
                    if (checkIfFileIsRead(entry.getKey(), processedFilesMap)) {
                        continue;
                    }
                    personList = StaxParserXmlToJava.parseData((new File(entry.getKey())));
                    break;
                }
                return personList;
            }, executorService);
            completableFuturesArray[i] = completableFuture;
        }
        shutdownExecutor(executorService);
        for (CompletableFuture<List<Person>> listCompletableFuture : completableFuturesArray) {
            generalPersonList.addAll(listCompletableFuture.get());
        }
        return generalPersonList;
    }

    private static void shutdownExecutor(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(30, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(30, TimeUnit.MINUTES))
                    System.err.println(POOL_DID_NOT_TERMINATE);
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private static synchronized boolean checkIfFileIsRead(String filePath, Map<String, Boolean> processedFilesMap) {
        boolean isRead = processedFilesMap.get(filePath);
        if (isRead) {
            return true;
        }
        processedFilesMap.put(filePath, true);
        return false;
    }

    private static void fillInputFilesMap(File inputFileFolder, Map<String, Boolean> processedFilesMap) throws IOException {
        File[] fileList = inputFileFolder.listFiles();
        for (File file : fileList) {
                processedFilesMap.put(file.getPath(), INITIAL_FILE_READ_STATE);
        }
    }
}
