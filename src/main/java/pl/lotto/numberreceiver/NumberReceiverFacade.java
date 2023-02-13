package pl.lotto.numberreceiver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    InputNumbersResponse inputNumbers(List<Integer> numbersFromUser) {
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

    List<TicketDto> retrieveAllUserTicketsForNextDrawDate() {
        LocalDateTime nextDrawDate = drawDateCalculator.calculateNextDrawDate();
        List<Ticket> tickets = repository.findAllByDrawDate(nextDrawDate);
        return tickets.stream()
                .map(ticket -> new TicketDto(ticket.lotteryId(), ticket.drawDate()))
                .collect(Collectors.toList());
    }

    public LocalDate getNextDrawingDate() {
        return LocalDate.from(drawDateCalculator.calculateNextDrawDate());
    }
}
