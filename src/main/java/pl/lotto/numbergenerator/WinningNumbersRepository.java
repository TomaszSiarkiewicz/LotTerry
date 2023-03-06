package pl.lotto.numbergenerator;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface WinningNumbersRepository extends MongoRepository<WinningNumbers, String> {
//    WinningNumbers save(WinningNumbers winningNumbers);
    Optional<WinningNumbers> findByDate(LocalDateTime now);
}
