package com.splat.client;

import com.splat.common.AccountService;
import org.apache.log4j.Logger;

import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputHandler {
    private static Logger LOG = Logger.getLogger(InputHandler.class);
    private int from, to;

    public void handleInput() throws RemoteException, NotBoundException {
        String name = "Service";
        Registry registry;
        registry = LocateRegistry.getRegistry("localhost", 1099);
        AccountService service = (AccountService) registry.lookup(name);
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Enter number of readers to call getAmount(id) method (rCount).");
        String rCount = "";
        while (!(checkCounts(rCount))) {
            System.out.println("it must be not negative integer number: ");
            rCount = scanner.next();
        }
        System.out.println("Enter number of writers to call addAmount(id, value) method (wCount).");
        String wCount = "";
        while (!(checkCounts(wCount))) {
            System.out.println("it must be not negative integer number: ");
            wCount = scanner.next();
        }
        System.out.println("Enter range of id numbers, for example 100-1000");
        String idList = "";
        while (!checkRange(idList)) {
            System.out.println("integer numbers, right one greater then left one: ");
            idList = scanner.next();
        }
        //Starting readers and writers
        ClientsInputManager mgr = new ClientsInputManager(toInt(rCount), toInt(wCount), from, to, service);
        mgr.execute();
        System.out.println("enter letter `s` if you want to get server load statistics;\n" +
                "enter `r` if you want to reset statistics;\n" +
                "enter `e` if you want to stop client execution");
        String dispatch = "";
        while (!dispatch.equals("e") && service != null) {
            dispatch = scanner.next();
            switch (dispatch) {
                case "s":
                    long[] l;
                    l = service.getStats();
                    System.out.println("Measurement interval: "+l[0]+"\n" +
                            " Number of queries: " + l[1] +
                            "\naverage server load: " + l[2] + " query/second;");
                    break;
                case "r":
                    service.resetStatistics();
                    System.out.println("Statistics reset, enter `s` to get new one;");
                    break;
                case "e":
                    System.out.println("Client shutting down...");
                    break;
                default:
                    System.out.println("Enter `s`, `r` and `e` are only allowed for input:");
                    break;
            }
        }
        mgr.stop();
        scanner.close();
        System.out.println("done");
        System.exit(0);

    }
    private boolean checkCounts(String s) {
        if (Pattern.matches("\\d+", s)) {
            if (toInt(s) >= 0) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRange(String s) {
        if (Pattern.matches("(\\d+)-(\\d+)", s)) {
            String[] nums = s.split("-");
            from = toInt(nums[0]);
            to = toInt(nums[1]);
            if (from < to) {
                return true;
            }
        }
        return false;
    }
    private int toInt(String s) {
        return Integer.parseInt(s);
    }
}
