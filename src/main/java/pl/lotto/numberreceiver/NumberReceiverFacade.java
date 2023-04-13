package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import pl.lotto.numberreceiver.dto.InputNumbersResponseDto;
import pl.lotto.numberreceiver.dto.TicketDto;

public class NumberReceiverFacade {

    private final TicketDtoRepository repository;
    private final NextDrawDateCalculator drawDateCalculator;
    private final NumberValidator numberValidator;


    NumberReceiverFacade(NextDrawDateCalculator drawDateCalculator, NumberValidator numberValidator , TicketDtoRepository repository) {
        this.drawDateCalculator = drawDateCalculator;
        this.numberValidator = numberValidator;
        this.repository = repository;
    }

    public InputNumbersResponseDto inputNumbers(List<Integer> numbersFromUser) {
        ValidationResult validationResult = numberValidator.validate(numbersFromUser);
        if (validationResult.isValid()) {
            LocalDateTime nextDrawDate = drawDateCalculator.calculateNextDrawDate();
            String lotteryId = UUID.randomUUID().toString();

            Ticket ticket = repository.save(new Ticket(lotteryId, nextDrawDate, numbersFromUser));
            return new InputNumbersResponseDto(new TicketDto(ticket.lotteryId(), ticket.drawDate()), validationResult.message());
        } else {
            return new InputNumbersResponseDto(new TicketDto(null, LocalDateTime.now()), validationResult.message());
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

    public TicketDto findById(String id) {
        Ticket ticket = repository.findById(id).orElseThrow(() -> new RuntimeException("ticket not found"));
        return new TicketDto(ticket.lotteryId(), ticket.drawDate());
    }
}
