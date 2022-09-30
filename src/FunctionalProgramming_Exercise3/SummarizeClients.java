package FunctionalProgramming_Exercise3;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public final class SummarizeClients {
    public static void main(String[] args) {
        summerizeFor();
        summerizeStream();    }

    public static void summerizeStream(){
        List<UdacisearchClient> clientList= ClientStore.getClients();

        int numClients = clientList.size();
        int totalQuarterlySpend = clientList.stream()
                .mapToInt(UdacisearchClient::getQuarterlyBudget)
                .sum();

        double averageBudget = clientList.stream()
                        .mapToDouble(UdacisearchClient::getQuarterlyBudget)
                                .average()
                                        .orElse(0.0);

        //Comparator
        long nextExpirationID = clientList.stream()
                .min(Comparator.comparing(UdacisearchClient::getContractEnd))
                .map(UdacisearchClient::getId)
                .orElse(-1L);

        List<ZoneId> representedZoneIds = clientList.stream()
                .flatMap(customer->customer.getTimeZones().stream())
                .distinct()
                .collect(Collectors.toList());


        Map<Year, Long> contractsPerYear = clientList.stream()
                                .collect(Collectors.groupingBy(
                                        SummarizeClients::getContractYear, Collectors.counting()));


        System.out.println("Num clients: " + numClients);
        System.out.println("Total quarterly spend: " + totalQuarterlySpend);
        System.out.println("Average budget: " + averageBudget);
        System.out.println("ID of next expiring contract: " + (nextExpirationID));
        System.out.println("Client time zones: " + representedZoneIds);
        System.out.println("Contracts per year: " + contractsPerYear);
    }
    public static void summerizeFor(){
        int numClients = 0;
        int totalQuarterlySpend = 0;
        UdacisearchClient nextExpiration = null;
        Set<ZoneId> representedZoneIds = new HashSet<>();
        Map<Year, Integer> contractsPerYear = new HashMap<>();

        for (UdacisearchClient client : ClientStore.getClients()) {
            numClients++;
            totalQuarterlySpend += client.getQuarterlyBudget();
            if (nextExpiration == null || client.getContractStart().plus(client.getContractLength())
                    .isBefore(nextExpiration.getContractStart().plus(nextExpiration.getContractLength()))) {
                nextExpiration = client;
            }
            for (ZoneId zone : client.getTimeZones()) {
                representedZoneIds.add(zone);
            }
            contractsPerYear.compute(getContractYear(client), (k, v) -> (v == null) ? 1 : v + 1);
        }

        System.out.println("Num clients: " + numClients);
        System.out.println("Total quarterly spend: " + totalQuarterlySpend);
        System.out.println("Average budget: " + (double) totalQuarterlySpend / numClients);
        System.out.println("ID of next expiring contract: " + (nextExpiration == null ? -1 : nextExpiration.getId()));
        System.out.println("Client time zones: " + representedZoneIds);
        System.out.println("Contracts per year: " + contractsPerYear);

    }
    private static Year getContractYear(UdacisearchClient client) {
        LocalDate contractDate =
                LocalDate.ofInstant(client.getContractStart(), client.getTimeZones().get(0));
        return Year.of(contractDate.getYear());
    }
}
