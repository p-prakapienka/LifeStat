package by.restrictor.lifestat.service;

import by.restrictor.lifestat.model.Balance;
import by.restrictor.lifestat.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    public Balance save(Balance balance) {
        Balance original = load();
        original.setCardBalance(balance.getCardBalance());
        original.setCash(balance.getCash());

        return balanceRepository.save(original);
    }

    public Balance load() {
        return balanceRepository.findAll().stream().findFirst().orElse(Balance.EMPTY);
    }

}
