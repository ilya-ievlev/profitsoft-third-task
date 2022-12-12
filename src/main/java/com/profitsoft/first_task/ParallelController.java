package com.profitsoft.first_task;

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

    // TODO: 12.12.22 create util to check time of algorithm execution and pass it to console
    // TODO: 12.12.22 refactor fields and code, exceptions
    // TODO: 12.12.22 refactor exceptions, method names, file paths
    public static List<Person> run(File inputFileFolder) throws ExecutionException, InterruptedException {
        ConcurrentHashMap<String, Boolean> processedFilesMap = new ConcurrentHashMap<>();
        fillInputFilesMap(inputFileFolder, processedFilesMap);
        List<Person> generalPersonList = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        int numberOfFiles = inputFileFolder.listFiles().length;
        CompletableFuture<List<Person>>[] completableFuturesArray = new CompletableFuture[numberOfFiles];
        for (int i = 0; i < numberOfFiles; i++) {
            CompletableFuture<List<Person>> completableFuture = CompletableFuture.supplyAsync(() -> {
                for (Map.Entry<String, Boolean> entry : processedFilesMap.entrySet()) {
                    if (checkIfFileIsRead(entry.getKey(), processedFilesMap)) {
                        continue;
                    }
                    return StaxParserXmlToJava.parseData((new File(entry.getKey())));
                }
                return null; // TODO: 11.12.22 change this thing
            }, executorService);
            completableFuturesArray[i] = completableFuture;
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.DAYS); // TODO: 12.12.22 create good termination method from oracle
        for (CompletableFuture<List<Person>> listCompletableFuture : completableFuturesArray) {
            generalPersonList.addAll(listCompletableFuture.get());
        }
        return generalPersonList;
    }

    private static synchronized boolean checkIfFileIsRead(String filePath, Map<String, Boolean> processedFilesMap) {
        boolean isRead = processedFilesMap.get(filePath);
        if (isRead) {
            return true;
        }
        processedFilesMap.put(filePath, true);
        return false;
    }

    private static void fillInputFilesMap(File inputFileFolder, Map<String, Boolean> processedFilesMap) {
        File[] fileList = inputFileFolder.listFiles();
        for (File file : fileList) {
            try {
                processedFilesMap.put(file.getCanonicalPath(), INITIAL_FILE_READ_STATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
