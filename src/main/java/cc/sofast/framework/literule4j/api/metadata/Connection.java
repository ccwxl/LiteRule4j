package cc.sofast.framework.literule4j.api.metadata;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * 连接
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class Connection {
    private String fromId;
    private String toId;
    @JsonDeserialize(using = ConnectionTypeDeserializer.class)
    private ConnectionType type;

    public static class ConnectionTypeDeserializer extends JsonDeserializer<ConnectionType> {

        @Override
        public ConnectionType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            String text = p.getText();
            return of(text);
        }

        public static ConnectionType of(String text) {
            ConnectionType connectionType = CommonConnectionType.of(text);
            if (connectionType != null) {
                return connectionType;
            }
            connectionType = DynamicStringConnectionType.of(text);
            return connectionType;
        }
    }
}
