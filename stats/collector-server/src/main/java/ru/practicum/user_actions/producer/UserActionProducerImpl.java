package ru.practicum.user_actions.producer;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.practicum.configuration.ProducerConfig;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.ewm.stats.proto.UserActionProto;
import ru.practicum.user_actions.mapper.AvroMapper;
import ru.practicum.user_actions.utility.ProducerActivityTimer;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserActionProducerImpl implements UserActionProducer {
    private final Object monitor = new Object();

    private final ProducerConfig producerConfig;
    private Producer<String, SpecificRecordBase> producer;

    @Override
    public void collectUserAction(UserActionProto userActionProto) {
        UserActionAvro message = AvroMapper.convertToAvro(userActionProto);

        synchronized (monitor) { // Самодельный таймер до закрытия продюсера. Если нет активности, продюсер закроется
            // через n миллисекунд (задается в application.yaml). Учитывая, что в кафку данные отправляются пачкой, то
            // это не замедляет работу модуля
            initializeProducerAndRunActivityTimer();

            ProducerRecord<String, SpecificRecordBase> record =
                    new ProducerRecord<>(producerConfig.getTopics().get("user_actions"), message);

            producer.send(record);
        }

    }

    private void initializeProducerAndRunActivityTimer() {
        if (ProducerActivityTimer.getNecessityNewProducer()) {
            producer = producerConfig.getKafkaProducer();
        }

        ProducerActivityTimer.startActivityTimerProducer(producer,
                producerConfig.getTimeUntilClosingKafkaProducerMs(),
                monitor);
    }

    @PreDestroy
    private void interruptThreadForTrackingProducerActivityAndCloseProducer() {
        ProducerActivityTimer.stopActivityTimerProducer();

        if (producer != null) {
            producer.flush();
            producer.close();
        }
    }
}
