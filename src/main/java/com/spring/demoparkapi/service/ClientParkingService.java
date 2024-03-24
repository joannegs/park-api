package com.spring.demoparkapi.service;

import com.spring.demoparkapi.entity.Client;
import com.spring.demoparkapi.entity.ClientParkingSpot;
import com.spring.demoparkapi.entity.ParkingSpot;
import com.spring.demoparkapi.repository.ClientParkingSpotRepository;
import com.spring.demoparkapi.util.ClientParkingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClientParkingService {
    private final ClientParkingSpotService clientParkingSpotService;
    private final ClientService clientService;
    private final ParkingSpotService parkingSpotService;
    private final ClientParkingSpotRepository clientParkingSpotRepository;

    @Transactional
    public void checkIn(ClientParkingSpot clientParkingSpot) {
        Client client = clientService.findByCpf(clientParkingSpot.getClient().getCpf());
        clientParkingSpot.setClient(client);

        ParkingSpot parkingSpot = parkingSpotService.getAvaliableParkingSpot();
        parkingSpot.setStatus(ParkingSpot.StateParkingSpot.TAKEN);
        clientParkingSpot.setParkingSpot(parkingSpot);
        clientParkingSpot.setCheckinDate(LocalDateTime.now());
        clientParkingSpot.setReceipt(ClientParkingUtil.generateReceipt());

        clientParkingSpotRepository.save(clientParkingSpot);
    }

    @Transactional
    public ClientParkingSpot checkOut(String receipt) {
        ClientParkingSpot clientParkingSpot = clientParkingSpotService.getByReceipt(receipt);
        LocalDateTime checkoutDate = LocalDateTime.now();

        BigDecimal price = ClientParkingUtil.calculateParkingPrice(clientParkingSpot.getCheckinDate(), checkoutDate);
        clientParkingSpot.setPrice(price);

        long times = clientParkingSpotService.getParkingTimes(clientParkingSpot.getClient().getCpf());

        BigDecimal discount = ClientParkingUtil.calculateDiscount(price, times);
        clientParkingSpot.setDiscount(discount);
        clientParkingSpot.setCheckoutDate(checkoutDate);
        clientParkingSpot.getParkingSpot().setStatus(ParkingSpot.StateParkingSpot.AVAILABLE);

        return clientParkingSpotService.save(clientParkingSpot);
    }
}
