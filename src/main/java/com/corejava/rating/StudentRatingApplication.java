package com.corejava.rating;

public class StudentRatingApplication {


    private static PrintService printService;
    private static ComputeService computeService;

    public static void main(String... args) {
        printService = new PrintService();
        computeService = new ComputeService(printService);
        computeService.startProcess();
    }


}
