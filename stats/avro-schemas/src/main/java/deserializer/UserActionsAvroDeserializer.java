package deserializer;

import ru.practicum.ewm.stats.avro.UserActionAvro;

public class UserActionsAvroDeserializer extends BaseAvroDeserializer<UserActionAvro> {
    public UserActionsAvroDeserializer() {
        super(UserActionAvro.getClassSchema());
    }
}