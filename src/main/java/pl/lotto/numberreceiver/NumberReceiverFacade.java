package pl.lotto.numberreceiver;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
public class NumberReceiverFacade {

    private final TicketDtoRepository repository;
    private final NextDrawDateCalculator drawDateCalculator;
    private final NumberValidator numberValidator;
    private final LotteryIdGenerable lotteryIdGenerator;

    NumberReceiverFacade(NextDrawDateCalculator drawDateCalculator, NumberValidator numberValidator, LotteryIdGenerable lotteryIdGenerator, TicketDtoRepository repository) {
        this.drawDateCalculator = drawDateCalculator;
        this.numberValidator = numberValidator;
        this.lotteryIdGenerator = lotteryIdGenerator;
        this.repository = repository;
    }

    public InputNumbersResponse inputNumbers(List<Integer> numbersFromUser) {
        ValidationResult validationResult = numberValidator.validate(numbersFromUser);
        if (validationResult.isValid()) {
            LocalDateTime nextSaturday = drawDateCalculator.calculateNextDrawDate();
            String lotteryId = UUID.randomUUID().toString();

            Ticket ticket = repository.save(new Ticket(lotteryId, nextSaturday, numbersFromUser));
            return new InputNumbersResponse(new TicketDto(ticket.lotteryId(), ticket.drawDate()), validationResult.message());
        } else {
            return new InputNumbersResponse(new TicketDto(null, LocalDateTime.now()), validationResult.message());
        }
    }

    Optional<List<TicketDto>> retrieveAllUserTicketsForNextDrawDate() {
        LocalDateTime nextDrawDate = drawDateCalculator.calculateNextDrawDate();
        List<Ticket> tickets = repository.findAllByDrawDate(nextDrawDate);
        if (tickets.isEmpty()) return Optional.empty();
        return Optional.of(tickets.stream()
                .map(ticket -> new TicketDto(ticket.lotteryId(), ticket.drawDate()))
                .collect(Collectors.toList()));


    }

    public LocalDateTime getNextDrawingDate() {
        return drawDateCalculator.calculateNextDrawDate();
    }

    public List<Ticket> getPlayedTicketDtoForGivenDate(LocalDateTime date) {
        return repository.findAllByDrawDate(date);
    }
}
